package com.yj.im.project.controller;

import com.yj.im.project.entity.ChatFriend;
import com.yj.im.project.entity.ChatFriendRequest;
import com.yj.im.project.entity.pojo.DiaDistoryDo;
import com.yj.im.project.entity.pojo.JwtUser;
import com.yj.im.project.service.ChatFriendService;
import com.yj.im.project.service.SysUserService;
import com.yj.im.project.util.Asp.TokenRecognition;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RSA.JwtUtils;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.RedisSessionUtil;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (ChatFriend)表控制层
 *
 * @author makejava
 * @since 2020-04-07 09:38:24
 */
@RestController
@RequestMapping("/api")
@Api(tags = "好友列表展示")
public class ChatFriendController {
    /**
     * 服务对象
     */
    @Resource
    private ChatFriendService chatFriendService;

    @Autowired
    private SysUserService sysUserService;

    @Resource
    private RedisOperator redisOperator;



    /**
     * 通过主键查询单条数据
     *
     * @param
     * @return 单条数据
     */
    /*@RequestMapping("/selectOne")
    public ChatFriend selectOne(Long id) {
        return this.chatFriendService.queryById(id);
    }*/

    /*
     * 用户搜索好友
     * @param name   查询条件
     * @param userId 用户id
     * @return list 多条数据
     * */
    @TokenRecognition
    @ApiOperation("模糊查询该用户下好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户输入", required = true, paramType = "query", dataType = " string")
    })
    @RequestMapping(value = "/search", method = {RequestMethod.POST, RequestMethod.GET})
    public Object search(HttpServletRequest request, String name) {
        JwtUser userInfo = JwtUtils.getUserInfo();
        List<ChatFriend> lists = this.chatFriendService.queryAllLike(name, userInfo.getId());
        if (lists.size() > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "", lists);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "未查询到用户");
    }

    /*
     * 查询用户所有好友
     * @param userId 用户id
     * @return list 多条数据
     * */
    @TokenRecognition
    @ApiOperation("根据用户id查询用户所有好友")
    @RequestMapping(value = "/searchAll", method = {RequestMethod.POST, RequestMethod.GET})
    public Object searchAll(HttpServletRequest request) {
        Long userId = JwtUtils.getUserInfo().getId();
        String keyName = "chat_friend" + userId;
        List<ChatFriend> lists = (List<ChatFriend>) redisOperator.get(keyName);
        if (lists == null) {
            lists = this.chatFriendService.queryAllById(userId);
            if (lists.size() > 0) {
                redisOperator.setForTimeMS(keyName, lists, RedisSessionUtil.SESSION_MIN);
                return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "查询成功", lists);
            } else {
                return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "未查找到好友信息");
            }
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "查询成功", lists);
    }

    @TokenRecognition
    @ApiOperation("根据用户id查询该用户分组下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendGroupId", value = "分组id", required = true, paramType = "query", dataType = " integer")
    })
    @RequestMapping(value = "/searchGroupFriend", method = {RequestMethod.POST, RequestMethod.GET})
    public Object searchGroupFriend(HttpServletRequest request, int friendGroupId) {
        Long userId = JwtUtils.getUserInfo().getId();
        String keyName = "searchGroupFriend" + userId + friendGroupId;
        List<ChatFriend> lists = (List<ChatFriend>) redisOperator.get(keyName);
        if (lists == null) {
            lists = this.chatFriendService.queryByIdAndGroupId(userId, friendGroupId);
            if (lists.size() > 0) {
                redisOperator.setForTimeMS(keyName, lists, RedisSessionUtil.SESSION_MIN);
                return new CommonResult(ResultConstantsEnum.RESULT_OK, "", lists);
            } else {
                return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "错误");
            }
        }
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "", lists);
    }

    @TokenRecognition
    @ApiOperation("根据用户id查询该用户所有分组下的用户")
    @RequestMapping(value = "/searchAllGroupFriend", method = {RequestMethod.POST, RequestMethod.GET})
    public Object searchAllGroupFriend(HttpServletRequest request) {
        Long userId = JwtUtils.getUserInfo().getId();
        String keyName = "searchAllGroupFriend" + userId;
        List<ChatFriend> lists = (List<ChatFriend>) redisOperator.get(keyName);
        if (lists == null) {
            lists = (List<ChatFriend>) this.chatFriendService.queryAllGroupFriend(userId);
            if (lists.size() > 0) {
                redisOperator.setForTimeMS(keyName, lists, RedisSessionUtil.SESSION_MIN);
                return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "", lists);
            } else {
                return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "错误");
            }
        }
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "", lists);

    }

    @TokenRecognition
    @ApiOperation("修改用户好友分组 0表示将用户从分组中删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFriendId", value = "好友id", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "friendGroupId", value = "好友分组id", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/updateGroup", method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateGroup(HttpServletRequest request, Long userFriendId, int friendGroupId) {
        Long userId = JwtUtils.getUserInfo().getId();
        return this.chatFriendService.updateGroup(userId, userFriendId, friendGroupId);
    }

    @TokenRecognition
    @ApiOperation("删除用户好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFriendId", value = "好友id", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/deleteFriend", method = {RequestMethod.POST, RequestMethod.GET})
    public Object deleteFriend(HttpServletRequest request, Long userFriendId) {
        Long userId = JwtUtils.getUserInfo().getId();
        return this.chatFriendService.updateStatus(userId, userFriendId);
    }

    @TokenRecognition
    @ApiOperation("通讯录添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phones", value = "手机号", required = true, paramType = "query",
                    allowMultiple = true, dataType = "string")
    })
    @RequestMapping(value = "/getDialDistory", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult getDialDistory(HttpServletRequest request) {
        Long userId = JwtUtils.getUserInfo().getId();
//        List<DiaDistoryDo> list = JSON.parseArray(request.getAttribute("arrayList").toString(), DiaDistoryDo.class);
        List<DiaDistoryDo> list = new ArrayList<>();
        list.add(new DiaDistoryDo("andy", "15054645644"));
        list.add(new DiaDistoryDo("andy3", "56564654546"));
        list.add(new DiaDistoryDo("andy备注名", "32165498799"));
        list.add(new DiaDistoryDo("andy5", "23456789999"));


//        List<String> phones = new ArrayList<>();
//        for (DiaDistoryDo aDo : list) {
//            phones.add(aDo.getPhone());
//        }
        if (userId == null) {
            return new CommonResult(ResultConstantsEnum.PARAM_NULL, "没有匹配到参数");
        }
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", sysUserService.selectByUserIdAndPhoneList(userId, list));
    }

    @TokenRecognition
    @ApiOperation("通过朋友的id，自己的id修改朋友的备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "朋友的id", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "remarks", value = "朋友的备注", required = false, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/updateFriendReMarks", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult updateFriendReMarks(Long id, String remarks) {
        Long userId = JwtUtils.getUserInfo().getId();
        Long count = chatFriendService.updateFriendRemarks(id, userId, remarks);
        if (count > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", count);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
    }

    /**
     * sse轮询
     */
    @RequestMapping(value = "/push", method = {RequestMethod.GET, RequestMethod.POST})
    public void push(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        String data = "data:" + new Date() + "\n\n";
        while (true) {
            try {
                PrintWriter pw = response.getWriter();
                Thread.sleep(3000L);
                pw.write(data);
                pw.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

