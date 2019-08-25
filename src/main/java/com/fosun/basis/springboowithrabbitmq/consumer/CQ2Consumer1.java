package com.fosun.basis.springboowithrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Christ
 * @date: 2019/8/22 17:56
 * @desc:
 */

@Slf4j
@Component
@RabbitListener(queues = {"christ-queue2"})
public class CQ2Consumer1 {

    @RabbitHandler
    public void receiver(String msg) {
        log.info("{} : christ-queue2 : {}",CQ2Consumer1.class.getName(),msg);
    }
}
