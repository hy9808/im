package com.yj.im.project.config;


import com.yj.im.project.rabbitMq.ImGroupMqListener;
import com.yj.im.project.rabbitMq.ImMqListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * mqBean工厂
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    /*@Autowired
    private ConnectionFactory connectionFactory;*/

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    //TODO: DirectExchange+routingKey -> Queue 队列绑定  普通消息队列

    @Bean
    public DirectExchange receiptExchange() {
        return new DirectExchange(env.getProperty("mq.second.queue.exchange"), true, false);
    }

    @Bean
    public Queue receiptQueue() {
        return new Queue(env.getProperty("mq.second.queue.name"), true);
    }

    @Bean
    public Binding receiptBinding() {
        return BindingBuilder.bind(receiptQueue()).to(receiptExchange()).with(env.getProperty("mq.second.queue.routingKey"));
    }

    //TODO: DirectExchange+routingKey -> Queue 队列绑定  群消息队列

    @Bean
    public DirectExchange receiptExchange2() {
        return new DirectExchange(env.getProperty("mq.customer.group.exchange"), true, false);
    }

    @Bean
    public Queue receiptQueue2() {
        return new Queue(env.getProperty("mq.customer.group.queue.name"), true);
    }

    @Bean
    public Binding receiptBinding2() {
        return BindingBuilder.bind(receiptQueue2()).to(receiptExchange2()).with(env.getProperty("mq.customer.group.routingKey"));
    }


    //TODO: 队列消息绑定 由容器管理队列与消息的生命周期 普通消息监听器

    @Autowired
    private ImMqListener imMqListener;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(5);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //手动确认消费

        container.setQueues(receiptQueue());
        container.setMessageListener(imMqListener);
        return container;
    }

    //TODO: 队列消息绑定 由容器管理队列与消息的生命周期 群消息监听器

    @Autowired
    private ImGroupMqListener imGroupMqListener;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer2() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(5);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //手动确认消费

        container.setQueues(receiptQueue2());
        container.setMessageListener(imGroupMqListener);
        return container;
    }
}
