package com.yj.im.project.controller;

import com.yj.im.project.entity.ChatFriendRequest;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import com.yj.im.project.service.ChatFriendRequestService;
import com.yj.im.project.service.SysUserService;
import com.yj.im.project.util.Asp.TokenRecognition;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RSA.JwtUtils;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (ChatFriendRequest)表控制层
 *
 * @author makejava
 * @since 2020-04-07 09:39:03
 */
@RestController
@RequestMapping("/api/chatFriendRequest")
public class ChatFriendRequestController {
    /**
     * 服务对象
     */
    @Autowired
    private ChatFriendRequestService chatFriendRequestService;

    @Autowired
    private SysUserService sysUserService;
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public ChatFriendRequest selectOne(Long id) {
        return this.chatFriendRequestService.queryById(id);
    }

    /**
     * 通过userId查询单条用户信息
     *
     * @param userId userId
     * @return
     */

    @ApiOperation("根据userId查找用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/selectByUserId", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult selectByUserId(Long userId) {
        ChatFriendVO chatFriendVO = chatFriendRequestService.selectByUserId(userId);
        if (!"".equals(chatFriendVO) && chatFriendVO != null) {
            //当对象不为空和已存在时
            //返回成功信息，返回一个VO对象
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "success", chatFriendVO);
        }
        return new CommonResult(ResultConstantsEnum.WARNING_NO_DATA, "没有找到该用户");
    }

    /**
     *
     * @param id 请求者id
     * @param nickName  接收者昵称
     * @param content 请求者留言
     * @return
     */
    @TokenRecognition
    @ApiOperation("根据请求者id查找是否为userId的好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "请求者id", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "content", value = "请求者留言", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "nickName", value = "接收者名称", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/addFriend", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult addFriend(Long id, String nickName, String content) {
        //从token中获取用户id和昵称
        Long userId = JwtUtils.getUserInfo().getId();

        int count = sysUserService.selectById(id);
        if (count >0){
            ChatFriendVO chatFriendVO = chatFriendRequestService.doesItExist(id, userId);
            if (chatFriendVO.getFlag() == 0) {
                //判断该用户是否好友，否的话进行添加操作
                if (!"".equals(chatFriendVO) && chatFriendVO != null) {
                    Long returnUserID = chatFriendRequestService.addNewFriend(id,nickName, userId, content);
                    if (returnUserID > 0) {
                        //操作完成数>0
                        return new CommonResult(ResultConstantsEnum.RESULT_OK, "success", returnUserID);
                    }
                    return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
                }
            }
            return new CommonResult(ResultConstantsEnum.EXISTS_YES, "该用户已存在", chatFriendVO);
        }
        return new CommonResult(ResultConstantsEnum.WARNING_NO_DATA, "该用户不存在");
    }

    /**
     * 根据接收者用户的id查找请求者的信息
     * @return
     */
    @TokenRecognition
    @ApiOperation("根据接收者用户id查找请求者的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "接收者用户id", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/selectRequestByUserId", method = {RequestMethod.GET, RequestMethod.POST})
    public Object selectRequestByUserId() {
        Long userId = JwtUtils.getUserInfo().getId();
        return chatFriendRequestService.selectRequestByUserId(userId);

    }

    /**
     * 接收者确认请求
     * @param chatFriendVO 请求者视图对象
     * @param remarks 接收者对请求者的备注
     * @param number 接收者的确认操作
     * @return
     */
    @TokenRecognition
    @ApiOperation("确认请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiverId", value = "接收者id", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "remarks", value = "备注", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "number", value = "操作", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/confirmRequest", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult confirmRequest(@ModelAttribute ChatFriendVO chatFriendVO, String remarks, Integer number) {
        Long receiverId = JwtUtils.getUserInfo().getId();
        Long count = chatFriendRequestService.comfirmRequest(chatFriendVO, receiverId, remarks, number);
        //如果是接收好友请求则返回
        if (count > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "success", count);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
    }

    @TokenRecognition
    @RequestMapping(value = "/selectFriendDetail" ,method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult selectFriendDetail(Long id){
        Long userId = JwtUtils.getUserInfo().getId();
        ChatFriendVO chatFriendVO = chatFriendRequestService.doesItExist(id, userId);
        if (!"".equals(chatFriendVO) && chatFriendVO != null) {
            return new CommonResult(ResultConstantsEnum.EXISTS_YES, "该用户已存在", chatFriendVO);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
    }
}