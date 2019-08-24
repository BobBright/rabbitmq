package com.fosun.rabbitmq.producer;

import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;

/**
 * @author: Christ
 * @date: 2019/8/24 21:41
 * @desc:
 */

public class MsgProcessor implements MessagePostProcessor {

    private final Integer ttl;

    public MsgProcessor(Integer ttl) {
        this.ttl = ttl;
    }

    @Override
    public Message<?> postProcessMessage(Message<?> message) {
        message.getHeaders().put("expiration",ttl.toString());
        return message;
    }
}
