package com.yj.im.project.controller;

import com.alibaba.fastjson.JSON;
import com.yj.im.project.dao.mongoDB.ChatFriendMsgNotReadDao;
import com.yj.im.project.dao.mongoDB.ChatGroupMsgNotReadyDao;
import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.entity.SysUser;
import com.yj.im.project.entity.mongoEntity.ChatGroupMsgNotReady;
import com.yj.im.project.entity.mongoEntity.ChatMyFriendMsgNotRead;
import com.yj.im.project.entity.pojo.SubMessage;
import com.yj.im.project.nettyServer.Message;
import com.yj.im.project.nettyServer.UserChannelMap;
import com.yj.im.project.service.ChatRecordService;
import com.yj.im.project.service.SysUserService;
import com.yj.im.project.util.AliyunOssUtil;
import com.yj.im.project.util.Asp.TokenRecognition;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RSA.JwtUtils;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.RedisSessionUtil;
import com.yj.im.project.util.contants.RedisKeyConstants;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 聊天记录表(ChatRecord)表控制层
 *
 * @author makejava
 * @since 2020-04-07 16:14:52
 */
@RestController
@Api(tags = "聊天相关接口")
@RequestMapping("/api")
public class ChatRecordController {
    /**
     * 服务对象
     */
    @Resource
    private ChatRecordService chatRecordService;

    @Resource
    AliyunOssUtil aliyunOssUtil;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    ChatFriendMsgNotReadDao chatMyFriendMsgNotRead;

    @Autowired
    ChatGroupMsgNotReadyDao chatGroupMsgNotReadyDao;

    @Autowired
    SysUserService sysUserService;


    /**
     * 通过用户id和对象用户查询聊天信息
     *
     * @param recipientId 对象用户
     * @return 聊天数据
     */
    @TokenRecognition
    @ApiOperation("根据id查找用户的聊天信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipientId", value = "对方userId", defaultValue = "1", required = true, paramType = "query", dataType = " long"),
    })

    @RequestMapping(value = "/queryAllById", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResult queryAllById(Long recipientId) {
        Long userId = JwtUtils.getUserInfo().getId();
        if (userId != null && recipientId != null) {
            String redisKey = RedisKeyConstants.IM_CHAT_RECORD + userId + recipientId;
            Object o = redisOperator.get(redisKey);
//            chatRecordService.updateByUserAndRecordId(new ChatRecord()
//                    .setUserId(userId)
//                    .setRecipientId(recipientId)
//                    .setHasRead(0)
//                    .setUpdateTime(new Date()));//进来默认之前所有消息都已读 (暂时不做  功能已有但是太消耗性能)
            if (o != null) {
                return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", o);
            } else {
                List<ChatRecord> chatRecords = this.chatRecordService.selectByUserIdAndRId(userId, recipientId);
                List<SubMessage> subMessages = new ArrayList<>();
                for (ChatRecord c : chatRecords) {
                    subMessages.add(new SubMessage()
                            .setUserId(c.getUserId())
                            .setMessage(c.getMessage())
                            .setCreateTime(c.getCreateTime())
                            .setMsgType(c.getMsgType()));
                }
                redisOperator.setForTimeMS(redisKey, subMessages, RedisSessionUtil.SESSION_IM_CHAT_RECORD);
                return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", subMessages);
            }
        }
        return new CommonResult(ResultConstantsEnum.PARAM_NULL, "操作错误");
    }


    /**
     * 用户发图片或者视频
     *
     * @param files 文件
     * @return 聊天数据
     */
    @TokenRecognition
    @ApiOperation("发送图片或者视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipientId", value = "对方userId", defaultValue = "1", required = true, paramType = "query", dataType = " long"),
            @ApiImplicitParam(name = "msgType", value = "消息类型(0.文字 1.图片,3.视频)',", defaultValue = "1", required = true, paramType = "query", dataType = " long"),
    })
    @RequestMapping(value = "/chatUpload", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResult queryAllById(@RequestParam("files") MultipartFile[] files, ChatRecord chatRecord) {
        chatRecord.setUserId(JwtUtils.getUserInfo().getId());
        try {
            Message message = new Message();
            Date data = new Date();

            message.setType(2);
            for (URL url : aliyunOssUtil.oosPut(files)) {
                chatRecord.setMessage(url.toString());
                message.setChatRecord(chatRecord);
                UserChannelMap.getChannel(chatRecord.getRecipientId()).writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));

                //立刻存储到数据库
                chatRecord.setHasRead(0);
                chatRecord.setHasDelete(0);
                chatRecord.setStatus(1);
                chatRecord.setCreateTime(data);
                chatRecord.setUpdateTime(data);
                chatRecordService.insert(chatRecord);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(ResultConstantsEnum.ERROR_FILE_UPLOAD, "文件上传失败");
        }
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功");
    }


    /**
     * 通过对方的id查询已读未读消息
     *
     * @return 聊天数据
     */
    @TokenRecognition
    @ApiOperation("刚上线调用的查看用户未在线时候的系统消息")
    @RequestMapping(value = "/queryByIdCountList", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResult queryByIdCountList() {
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", this.chatRecordService.selectByIdGetNotReadMsg(JwtUtils.getUserInfo().getId()));
    }

    /**
     * 用户上线通过用户id查找未读的好友消息与群消息
     *
     * @return 聊天数据
     */
    @TokenRecognition
    @ApiOperation("用户上线通过用户id查找未读的好友消息与群消息")
    @RequestMapping(value = "/queryByUserIdGetMsgList", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResult queryByUserIdGetMsgList() {
        Long userId = JwtUtils.getUserInfo().getId();
        //由于是用的是MongoDB所以这里这几写业务层了
        List<ChatMyFriendMsgNotRead> chatMyFriendMsgNotReads = chatMyFriendMsgNotRead.queryList(new ChatMyFriendMsgNotRead().setFriendId(userId));
        List<ChatGroupMsgNotReady> chatGroupMsgNotReadies = chatGroupMsgNotReadyDao.queryList(new ChatGroupMsgNotReady().setUserId(userId));//这里后期要改成群组id
        for (ChatMyFriendMsgNotRead myf : chatMyFriendMsgNotReads) {
            SysUser sysUser = sysUserService.queryById(myf.getUserId());
            myf.setImg(sysUser.getImage() == null ? myf.getImg() : sysUser.getImage());
            myf.setNickName(sysUser.getName()).setSubMsgType(2);
            chatGroupMsgNotReadies.add(new ChatGroupMsgNotReady(myf));
        }
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", chatGroupMsgNotReadies);
    }

    /**
     * 通过对方的id查询已读未读消息
     *
     * @return 聊天数据
     */
    @TokenRecognition
    @ApiOperation("通过系统信息id修改已读状态")
    @RequestMapping(value = "/updateSysMsgType", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResult updateSysMsgType(String id, Long readCount, Integer type) {
        if (id == null || id.equals("")) {
            return null;
        }
        return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", this.chatRecordService.updateSysMsgType(id, readCount, type));
    }
}