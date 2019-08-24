package com.fosun.rabbitmq.producer;

import com.fosun.rabbitmq.config.RabbitmqConstant;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Christ
 * @date: 2019/8/24 12:50
 * @desc:
 */

@Slf4j
@Component
public class MsgProducer  {


    private RabbitTemplate rabbitTemplate;

    @Autowired
    private  Channel delayOrderChannel;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
//        this.rabbitTemplate.setConfirmCallback(this);
    }


    /**
     * 方法描述: 发送延迟订单处理消息
     * @param msg 消息内容 （订单号或者json格式字符串）
     * @param overTime 消息存活时间
     * @throws Exception
     */
    public void sendMessage(String msg,Long overTime) throws Exception {


        rabbitTemplate.setExchange("");
        rabbitTemplate.setRoutingKey(RabbitmqConstant.DELAY_ORDER_QUEUE_NAME);
        MessageProperties properties = new MessageProperties();
        properties.setExpiration(Long.toString(10000L));
        log.info("start {}",System.currentTimeMillis());
        Message message = new Message(msg.getBytes(),properties);
        rabbitTemplate.send(message);
    }

    public void sendMessage2(String msg,Long overTime) throws Exception {

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .expiration(overTime.toString()) //设置消息存活时间（毫秒）
                .build();
        delayOrderChannel.basicPublish("", RabbitmqConstant.DELAY_ORDER_QUEUE_NAME, properties, msg.getBytes("UTF-8"));
    }

    public void sendMsg(String content) throws IOException {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("",RabbitmqConstant.DELAY_ORDER_QUEUE_NAME,content,correlationId);
    }

}
