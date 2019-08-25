package com.fosun.basis.springboowithrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Christ
 * @date: 2019/8/22 18:24
 * @desc:
 */

@Configuration
public class RabbitTopicConfig {

    @Bean
    public Queue queueMsg() {
        return new Queue("topic.msg",false,true,true,null);
    }

    @Bean
    public Queue queueMsgs() {
        return new Queue("topic.msgs",false,true,true,null);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingQueue2Exchange(Queue queueMsg,TopicExchange topicExchange) {

         return BindingBuilder.bind(queueMsg).to(topicExchange).with("topic.msg");
    }

    @Bean
    public Binding bindingQueue2Exchange2(Queue queueMsgs, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMsgs).to(topicExchange).with("topic.#");
    }


}
