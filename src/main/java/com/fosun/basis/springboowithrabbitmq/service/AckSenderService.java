package com.fosun.basis.springboowithrabbitmq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fosun.basis.springboowithrabbitmq.domain.MessageObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author: Christ
 * @date: 2019/8/22 18:46
 * @desc:
 */

@Slf4j
@Component
public class AckSenderService implements ReturnCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * if the msg didn't be delivery to the queue , the returnMessage is invoked
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode,
                                String replyText, String exchange, String routingKey) {

        log.info("AckSenderService returnedMessage " + message.toString() + " === " + replyCode + " === " + replyText + " === " + exchange +"routine key "+routingKey);

    }

    public void send() {
            final String now = LocalDateTime.now(ZoneId.systemDefault()).toString();
            //设置返回回调
            rabbitTemplate.setReturnCallback(this);
            //设置确认返回
            //消息没有投递到exchange中
            rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {

                if (ack) {
                    log.info("消息发送成功");
                }else{
                    log.info("消息发送失败 cause {} ，correlationData {}",cause,correlationData);
                }

            });

            rabbitTemplate.convertAndSend("fanoutExchange","ackQueue",now);
    }

    public void sendedReturnBack() {
        final String now = LocalDateTime.now(ZoneId.systemDefault()).toString();
        //设置返回回调
        rabbitTemplate.setReturnCallback(this);
        //设置确认返回
        //消息没有投递到exchange中
        rabbitTemplate.convertAndSend("invalidFanoutExchange","ackQueue",now);
    }


    /**
     * 发送对象，序列化处理
     */
    public void sendObj() {
        MessageObj messageObj = new MessageObj();
        messageObj.setId(1111);
        messageObj.setName("kinson");
        messageObj.setAck(Boolean.TRUE);

        try {
            rabbitTemplate.convertAndSend("ackQueue2", objectMapper.writeValueAsString(messageObj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
