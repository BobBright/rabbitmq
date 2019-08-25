package com.fosun.basis.springboowithrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Christ
 * @date: 2019/8/22 13:27
 * @desc:
 */

@Slf4j
@Component
@RabbitListener(queues = {"christ-queue"})
public class CQConsumer2 {


    @RabbitHandler
    public void consume(String msg) {
        log.info("{} : christ-queue : {}",CQConsumer2.class.getName(),msg);
    }
}
