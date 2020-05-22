package com.yj.im.project.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yj.im.project.entity.ChatFriend;
import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.entity.ChatSysMsg;
import com.yj.im.project.entity.pojo.JwtUser;
import com.yj.im.project.nettyServer.ChatHandler;
import com.yj.im.project.nettyServer.Message;
import com.yj.im.project.service.ChatSysMsgService;
import com.yj.im.project.util.Asp.TokenRecognition;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RSA.JwtUtils;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * im聊天的总后台管理接口
 *
 * @author makejava
 * @since 2020-04-07 09:38:24
 */
@RestController
@RequestMapping("/im")
@Api(tags = "聊天室管理后台接口")
public class ChatImController {

    @Autowired
    ChatSysMsgService chatSysMsgService;

    /*
     * 用户搜索好友
     * @param name   查询条件
     * @param userId 用户id
     * @return list 多条数据
     * */
    @ApiOperation("向所有用户发送消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "发送内容", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "消息类型(0.系统消息 1.订单消息)", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/toAllUser", method = {RequestMethod.POST, RequestMethod.GET})
    public Object toAllUser(HttpServletRequest request, String msg, Integer type) {
        Message message = new Message();
        message.setType(2);
        message.setChatRecord(new ChatRecord().setSysMsgType(type).setMessage(msg));
        if (msg != null && type != null) {
            //向所有用户发送消息,并且将消息存进数据库
            ChatHandler.toAllUser(message);
            chatSysMsgService.insert(new ChatSysMsg().setSysMsgType(type).setMsgType(0).setCreateTime(new Date()).setStatus(1).setMessage(msg));
        }
        return null;
    }

    /*
     * 用户搜索好友
     * @param name   查询条件
     * @param userId 用户id
     * @return list 多条数据
     * */
    @ApiOperation("向所有用户发送消息")
    @RequestMapping(value = "/testToken", method = {RequestMethod.POST, RequestMethod.GET})
    public Object testToken(HttpServletRequest request) {
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功");
    }


}
