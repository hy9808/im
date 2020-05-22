package com.yj.im.project.rabbitMq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.service.ChatRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImMqListener implements ChannelAwareMessageListener {

    private static final Logger log = LoggerFactory.getLogger(ImMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatRecordService chatRecordService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            ChatRecord entity = objectMapper.readValue(body, ChatRecord.class);
            chatRecordService.insert(entity);
            channel.basicAck(deliveryTag, false);//立刻回复消息
        } catch (Exception e) {
            log.error("消息体解析异常：", e.fillInStackTrace());
            channel.basicReject(deliveryTag, false);
        }
    }
}
