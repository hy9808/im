package com.yj.im.project.controller;

import com.yj.im.project.service.ChatGroupService;
import com.yj.im.project.util.Asp.TokenRecognition;
import com.yj.im.project.util.RSA.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 群组基础表(ChatGroup)表控制层
 *
 * @author makejava
 * @since 2020-04-13 18:02:42
 */
@RestController
@RequestMapping("/api")
@Api(tags = "群")
public class ChatGroupController {
    /**
     * 服务对象
     */
    @Resource
    private ChatGroupService chatGroupService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    /*@GetMapping("selectOne")
    public ChatGroup selectOne(Long id) {
        return this.chatGroupService.queryById(id);
    }*/

    /**
     *
     *通过用户id查询所有群
     * @return 所有群的对象
     */
    @TokenRecognition
    @ApiOperation("查询该用户所有群")
    @RequestMapping(value = "searchAllGroup", method = {RequestMethod.POST, RequestMethod.GET})
    public Object searchAllGroup(){
        Long UserId = JwtUtils.getUserInfo().getId();
        return  this.chatGroupService.queryAllGroup(UserId);
    }

    @ApiOperation("修改群组名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName", value = "群组名", required = true, paramType = "query", dataType = " string"),
            @ApiImplicitParam(name = "id", value = "群组id", required = true, paramType = "query", dataType = " integer")
    })
    @RequestMapping(value = "/chatGroup/updateGroupName", method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateGroupName(HttpServletRequest request, String groupName, Long id){
       return this.chatGroupService.updateGroupName(groupName,id);
    }

    /**
     * 创建群
     * @param ids 群主选择进群的好友id
     * @param nickName  群主的昵称
     * @return
     */
    @TokenRecognition
    @RequestMapping(value = "/createGroupChat", method = {RequestMethod.GET, RequestMethod.POST})
    public Object createGroupChat(@RequestBody List<Long> ids, @RequestParam("nickName") String nickName) {
        Long createUserId = JwtUtils.getUserInfo().getId();
        return chatGroupService.createGroupChatByIds(ids, createUserId, nickName);
    }



}