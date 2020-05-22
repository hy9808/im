package com.yj.im.project.rabbitMq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yj.im.project.dao.mongoDB.ChatGroupMongoDao;
import com.yj.im.project.entity.mongoEntity.ChatGroupMessage;
import com.yj.im.project.entity.ChatRecord;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ImGroupMqListener implements ChannelAwareMessageListener {

    private static final Logger log = LoggerFactory.getLogger(ImGroupMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ChatGroupMongoDao chatGroupMongoDao;


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            ChatRecord ChatRecord = objectMapper.readValue(body, ChatRecord.class);
            ChatGroupMessage chatGroupMessage = new ChatGroupMessage()
                    .setMessage(ChatRecord.getMessage())
                    .setToGroupId(ChatRecord.getRecipientId())
                    .setUserId(ChatRecord.getUserId())
                    .setMsgType(ChatRecord.getMsgType())
                    .setId(UuidUtil.getTimeBasedUuid().toString()).setDelete(1).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
            //放入MongoDB
            chatGroupMongoDao.save(chatGroupMessage);
            channel.basicAck(deliveryTag, false);//立刻回复消息
        } catch (Exception e) {
            log.error("消息体解析异常：", e.fillInStackTrace());
            channel.basicReject(deliveryTag, false);
        }
    }

}
