package com.yj.im.project.nettyServer;

import com.alibaba.fastjson.JSON;
import com.yj.im.project.dao.ChatUserGroupDao;
import com.yj.im.project.dao.mongoDB.ChatFriendMsgNotReadDao;
import com.yj.im.project.dao.mongoDB.ChatGroupMsgNotReadyDao;
import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.entity.ChatSysRead;
import com.yj.im.project.entity.mongoEntity.ChatGroupMsgNotReady;
import com.yj.im.project.entity.mongoEntity.ChatMyFriendMsgNotRead;
import com.yj.im.project.entity.pojo.JwtUser;
import com.yj.im.project.service.ChatRecordService;
import com.yj.im.project.service.ChatSysReadService;
import com.yj.im.project.util.RSA.JwtUtils;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.SpringUtil;
import com.yj.im.project.util.contants.RedisKeyConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.time.LocalDateTime;
import java.util.*;

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //用户建立连接后的用户信息
    public static Hashtable<String, JwtUser> userInfoMap = new Hashtable<>();

    //用户消息处理服务
    ChatRecordService chatRecordService = SpringUtil.getBean(ChatRecordService.class);

    //系统级消息处理服务
    ChatSysReadService chatSysReadService = SpringUtil.getBean(ChatSysReadService.class);

    //群消息处理MongoDB
    ChatGroupMsgNotReadyDao chatGroupMsgReadyDao = SpringUtil.getBean(ChatGroupMsgNotReadyDao.class);

    //好友消息是否未读MongoDB
    ChatFriendMsgNotReadDao chatFriendMsgNotReadDao = SpringUtil.getBean(ChatFriendMsgNotReadDao.class);

    //用户所在的群相关dao层
    ChatUserGroupDao chatUserGroupDao = SpringUtil.getBean(ChatUserGroupDao.class);

    //redis
    RedisOperator redisOperator = SpringUtil.getBean(RedisOperator.class);


    /**
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {


        String content = textWebSocketFrame.text();
        BaseNettyServer.nettyLog().info("接收到的数据: " + content);
        Message message = JSON.parseObject(content, Message.class);
        switch (message.getType()) {
            //处理客户端链接的消息
            case 0:   //表示连接
                //建立用户和通道之间的关系
                JwtUser userInfo = JwtUtils.getUserInfoByToken(message.getExt().toString());
                UserChannelMap.put(userInfo.getId(), channelHandlerContext.channel());
                BaseNettyServer.nettyLog().info("用户名:" + userInfo.getUsername() + "建立了连接！！！");
                UserChannelMap.print();
                userInfoMap.put("im" + userInfo.getId(), userInfo);
                message.setType(0);
                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message.setChatRecord(message.getChatRecord().setUserId(userInfo.getId())))));
                break;

            case 1:  //表示发送消息
                //将消息保存到数据库
                Date data = new Date();
                JwtUser jwtUser = userInfoMap.get("im" + message.getChatRecord().getUserId());
                ChatRecord chatRecord = message.getChatRecord()
                        .setStatus(1)
                        .setHasRead(0)
                        .setHasDelete(0)
                        .setImg(jwtUser.getUserImg())
                        .setNickName(jwtUser.getUsername())
                        .setCreateTime(data)
                        .setUpdateTime(data);
                chatRecordService.insertToMq(chatRecord);
                //查看此好友是否在线，如果在线就将消息发送给此好友
                //1.根据好友id,查询此通道是否存在
                Channel channel = UserChannelMap.getChannel(chatRecord.getRecipientId());
                if (channel != null) {
                    //存在的话发消息给他
                    ChatMyFriendMsgNotRead cmgod = chatFriendMsgNotReadDao.queryOne(new ChatMyFriendMsgNotRead()
                            .setUserId(message.getChatRecord().getUserId())
                            .setFriendId(message.getChatRecord().getRecipientId()));
                    message.setType(2);
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message.setChatRecord(message.getChatRecord().setMongoId(cmgod != null ? cmgod.getId() : "")))));
                } else {
                    String mongoId = UuidUtil.getTimeBasedUuid().toString();
                    //用户不在线 未读消息+1 并且储存当前过来的消息
                    Long userId = message.getChatRecord().getUserId();
                    ChatMyFriendMsgNotRead chatMyFriendMsgNotRead = chatFriendMsgNotReadDao.queryOne(new ChatMyFriendMsgNotRead()
                            .setUserId(userId)
                            .setFriendId(message.getChatRecord().getRecipientId()));
                    //MongoDB查出来
                    if (chatMyFriendMsgNotRead == null) {
                        //没有就新增一条,此时map里面可能没有好友的信息所以不存头像
                        chatFriendMsgNotReadDao.save(new ChatMyFriendMsgNotRead()
                                .setUserId(userId)
                                .setFriendId(message.getChatRecord().getRecipientId())
                                .setMsgNotReadyCount(1)
                                .setId(mongoId)
                                .setOutMsg(message.getChatRecord().getMessage())
                                .setOutTime(LocalDateTime.now()));
                    } else {
                        //有就修改
                        chatFriendMsgNotReadDao.updateFirst(chatMyFriendMsgNotRead, new ChatMyFriendMsgNotRead()
                                .setOutMsg(message.getChatRecord().getMessage())
                                .setMsgNotReadyCount(chatMyFriendMsgNotRead.getMsgNotReadyCount() + 1).setOutTime(LocalDateTime.now()));
                    }
                }
                break;
            case 2:  //接收消息
                //将消息设置为已读 (暂时不做  功能已有但是太消耗性能)
                if (message.getChatRecord().getSysMsgType() != null) {
                    //存在即为读取系统消息
                    chatSysReadService.updateByTypeAndIdToReadCount(new ChatSysRead()
                            .setReadType(message.getChatRecord().getSysMsgType())
                            .setUserId(message.getChatRecord().getUserId()));
                } else {
                    //确认用户好友消息
                    chatRecordService.updateByUserAndRecordId(new ChatRecord()
                            .setUserId(message.getChatRecord().getUserId())
                            .setRecipientId(message.getChatRecord().getRecipientId())
                            .setHasRead(0)
                            .setUpdateTime(new Date()));
                }
                break;
            case 3: //检测心跳
                //接收心跳信息
                BaseNettyServer.nettyLog().info("接收到心跳消息" + JSON.toJSONString(message));
                message.setExt("收到啦谢谢你 ！！！");
                message.setType(3);
                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                break;

            case 4://群消息发送
                Set<Long> users = new HashSet<Long>();//假装是redis取出来的群组ids
                users.add(1L);
                users.add(2L);
                users.add(3L);
                //然后向所在的群发送消息
                for (Long user : users) {
                    String nickName = redisOperator.get(RedisKeyConstants.IM_GROUP_GET_NICKNAME_BY_ID + message.getChatRecord().getRecipientId()).toString();
                    if (nickName == null) {
                        nickName = chatUserGroupDao.selectByGroupIdAndUserIdGetRemarks(message.getChatRecord().getRecipientId(), user);
                    }

                    Channel cs = UserChannelMap.getChannel(user);
                    if (cs != null) {
                        //当前用户在线
                        message.setType(4);
                        cs.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message.getChatRecord().setNickName(nickName))));
                    } else {
                        //用户不在线 未读消息+1 并且储存当前过来的消息
                        ChatGroupMsgNotReady chatGroupMsgReady = chatGroupMsgReadyDao.queryOne(new ChatGroupMsgNotReady().setUserId(user).setGroupId(message.getChatRecord().getRecipientId()));
                        //MongoDB查出来
                        if (chatGroupMsgReady == null) {
                            //没有就新增一条
                            chatGroupMsgReadyDao.save(new ChatGroupMsgNotReady()
                                    .setUserId(user)
                                    .setMsgNotReadyCount(1)
                                    .setNickName(nickName)
                                    .setGroupId(message.getChatRecord().getRecipientId())
                                    .setId(UuidUtil.getTimeBasedUuid().toString())
                                    .setOutMsg(message.getChatRecord().getMessage())
                                    .setOutTime(LocalDateTime.now()));
                        } else {
                            //有就修改
                            chatGroupMsgReadyDao.updateFirst(chatGroupMsgReady, new ChatGroupMsgNotReady()
                                    .setOutMsg(message.getChatRecord().getMessage())
                                    .setMsgNotReadyCount(chatGroupMsgReady.getMsgNotReadyCount() + 1).setOutTime(LocalDateTime.now()));
                        }
                    }
                }
                //群消息
                //mq存消息
                chatRecordService.chatGroupInsertMq(message.getChatRecord());

                break;
            case 5:
                //确认群消息
                ChatGroupMsgNotReady query = new ChatGroupMsgNotReady().setUserId(message.getChatRecord().getUserId()).setGroupId(message.getChatRecord().getRecipientId());
                chatGroupMsgReadyDao.updateFirst(query, new ChatGroupMsgNotReady().setMsgNotReadyCount(0));
                break;
            default:
                //没有成功匹配视为异常操作
                channelHandlerContext.channel()
                        .writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(new Message()
                                .setType(500)
                                .setExt("未能匹配到相关操作"))));
                BaseNettyServer.nettyLog().error("未能匹配到相关操作");

        }

    }

    //当有新的客户端连接服务器之后,就会自动调用这个方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    //出现异常是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //根据通道id取消用户和通道的关系
        UserChannelMap.removeByChannelId(ctx.channel().id().asLongText());
        ctx.channel().close();
        BaseNettyServer.nettyLog().warn("出现异常.....关闭通道!");
    }


    //当客户端关闭链接时关闭通道
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        BaseNettyServer.nettyLog().info("关闭通道");
        UserChannelMap.removeByChannelId(ctx.channel().id().asLongText());
        ctx.channel().close();
        UserChannelMap.print();
    }

    public static void toAllUser(Message text) {
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(text)));
        }
    }

}
