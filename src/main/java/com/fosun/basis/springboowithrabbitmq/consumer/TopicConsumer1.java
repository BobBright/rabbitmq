package com.fosun.basis.springboowithrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Christ
 * @date: 2019/8/22 18:32
 * @desc:
 */

@Slf4j
@Component
@RabbitListener(queues = {"topic.msg"})
public class TopicConsumer1 {

    @RabbitHandler
    public void consume(String msg) {

        log.info("{} : topic.msg : {}",TopicConsumer1.class.getName(),msg);
    }
}
