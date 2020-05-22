package com.yj.im.project.controller;

import com.yj.im.project.entity.ChatUserGroup;
import com.yj.im.project.service.ChatUserGroupService;
import com.yj.im.project.util.Asp.TokenRecognition;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RSA.JwtUtils;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户和群关联表(ChatUserGroup)表控制层
 *
 * @author makejava
 * @since 2020-04-13 18:03:07
 */
@RestController
@RequestMapping("/api")
@Api(tags = "群用户")
public class ChatUserGroupController {
    /**
     * 服务对象
     */
    @Resource
    private ChatUserGroupService chatUserGroupService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ChatUserGroup selectOne(Long id) {
        return this.chatUserGroupService.queryById(id);
    }

    /**
     * 通过用户id和群组id查询群详情
     *
     * @param groupId 群组id
     * @return 对象列表
     */
    @TokenRecognition
    @ApiOperation("查询该群详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query", dataType = " integer")
    )
    @RequestMapping(value = "/queryAllUser", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryAllUser(Long groupId){
        Long userId = JwtUtils.getUserInfo().getId();
        return chatUserGroupService.queryAllUser(userId,groupId);
    }

    /**
     * 通过群组id和用户id退出群聊
     *
     * @param groupId 群组id
     * @return 影响行数
     */
    @TokenRecognition
    @ApiOperation("退出群聊")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query", dataType = " integer")
    )
    @RequestMapping(value = "/updateStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateStatus(Long groupId){
        Long userId = JwtUtils.getUserInfo().getId();
        return this.chatUserGroupService.updateStatus(userId,groupId);
    }

    /**
     * 修改数据
     *
     * @param chatUserGroup 实例对象
     * @return 影响行数
     */
    @TokenRecognition
    @ApiOperation("通过二维码加入群聊")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query", dataType = " integer")
    )
    @RequestMapping(value = "/insertByCrCode", method = {RequestMethod.POST, RequestMethod.GET})
    public  Object insertByCrCode(ChatUserGroup chatUserGroup){
        Long userId= JwtUtils.getUserInfo().getId();
        chatUserGroup.setUserId(userId);
        return this.chatUserGroupService.insertByCrCode(chatUserGroup);
    }
    /**
     * 判断是否是群员
     */
    @TokenRecognition
    @ApiImplicitParams(
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query", dataType = " integer")
    )
    @RequestMapping(value = "/tests", method = {RequestMethod.POST, RequestMethod.GET})
    public  Object check(Long groupId){
        Long userId= JwtUtils.getUserInfo().getId();
        return this.chatUserGroupService.check(userId,groupId);
    }

    /**
     * 修改群中个人备注
     * @param groupId
     * @param remarks
     * @return
     */
    @TokenRecognition
    @RequestMapping(value = "/rewriteRemarksById", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResult rewriteRemarksById(Long groupId, String remarks) {
        Long userId = JwtUtils.getUserInfo().getId();
        Long count = chatUserGroupService.rewriteRemarksById(groupId, userId, remarks);
        if (count > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", count);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "页面繁忙，请稍后重试");
    }

    /**
     * 群主踢人
     * @param id 被踢的人id
     * @param groupId
     * @return
     */
    @TokenRecognition
    @RequestMapping(value = "/KickMan", method = {RequestMethod.GET, RequestMethod.POST})
    public Object KickMan(Long id, Long groupId){
        Long userId = JwtUtils.getUserInfo().getId();
        return chatUserGroupService.kickManById(userId, id, groupId);

    }


}