package com.yj.im.project.controller;

import com.yj.im.project.entity.ChatFriendGrouping;
import com.yj.im.project.entity.pojo.JwtUser;
import com.yj.im.project.service.ChatFriendGroupingService;
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
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (ChatFriendGrouping)表控制层
 *
 * @author makejava
 * @since 2020-04-07 09:38:50
 */
@RestController
@RequestMapping("/api")
@Api(tags = "好友分组")
public class ChatFriendGroupingController {
    /**
     * 服务对象
     */
    @Resource
    private ChatFriendGroupingService chatFriendGroupingService;
    @Resource
    private  RedisOperator redisOperator;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
   /* @GetMapping("selectOne")
    public ChatFriendGrouping selectOne(Long id) {
        return this.chatFriendGroupingService.queryById(id);
    }*/

    /**
     * 通过主键查询单条数据
     *
     *
     * @return 集合数据
     */
    @TokenRecognition
    @ApiOperation("查询用户下所有分组")
    @RequestMapping(value = "/searchGroup", method = {RequestMethod.POST, RequestMethod.GET})
    public  Object selectAllById(HttpServletRequest request){
        Long userId = JwtUtils.getUserInfo().getId();
        String keyName ="group" + userId;
        List<ChatFriendGrouping> lists = (List<ChatFriendGrouping>) redisOperator.get(keyName);
        if(lists==null){
            lists = this.chatFriendGroupingService.queryAllById(userId);
            if(lists.size()>0){
                redisOperator.setForTimeMS(keyName, lists, RedisSessionUtil.SESSION_THE_MIN);
                return  new CommonResult(ResultConstantsEnum.RESULT_OK,"成功",lists);
            }
            return  new CommonResult(ResultConstantsEnum.RESULT_OK,"未创建分组");
        }

        return new CommonResult(ResultConstantsEnum.RESULT_OK,"成功",lists);
    }

    @ApiOperation("根据分组id删除用户的分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendGroupId", value = "分组id", defaultValue = "1", required = true, paramType = "query", dataType = " integer"),
    })
    @RequestMapping(value = "/deleteGroup", method = {RequestMethod.POST, RequestMethod.GET})
    public Object deleteGroup(HttpServletRequest request,int friendGroupId){
        return this.chatFriendGroupingService.deleteGroup(friendGroupId);
    }

    @ApiOperation("根据分组id修改用户分组名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendGroupId", value = "分组id",  required = true, paramType = "query", dataType = " integer"),
            @ApiImplicitParam(name="groupName", value = "分组名",required = true,paramType = "query", dataType = " string")
    })
    @RequestMapping(value = "/updateGroupName", method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateGroupName(HttpServletRequest request,int friendGroupId,String groupName){
        return this.chatFriendGroupingService.updateGroupName(friendGroupId,groupName);
    }
    @TokenRecognition
    @ApiOperation("创建分组")
    @ApiImplicitParams({
          @ApiImplicitParam(name="groupName", value = "分组名",required = true,paramType = "query", dataType = " string")
    })
    @RequestMapping(value = "/addGroup", method = {RequestMethod.POST, RequestMethod.GET})
    public Object addGroup(HttpServletRequest request,String groupName){
        Long userId= JwtUtils.getUserInfo().getId();
        return  this.chatFriendGroupingService.addGroup(userId,groupName);
    }
}