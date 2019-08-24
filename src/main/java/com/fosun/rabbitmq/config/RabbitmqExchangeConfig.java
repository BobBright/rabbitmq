package com.fosun.rabbitmq.config;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;



/**
 * @author: Christ
 * @date: 2019/8/24 12:51
 * @desc: 一个交换机可以绑定多个队列
 *
 */

@Configuration
public class RabbitmqExchangeConfig {

    @Autowired
    private RabbitAdmin rabbitAdmin;


//    @Bean
//    FanoutExchange contractFanoutExchange() {
//        FanoutExchange fanoutExchange = new FanoutExchange("com.exchange.fanout");
//        this.rabbitAdmin.declareExchange(fanoutExchange);
//        return fanoutExchange;
//    }
//
//    @Bean
//    TopicExchange contractTopicExchangeDurable() {
//        TopicExchange contractTopicExchange = new TopicExchange("com.exchange.topic");
//        this.rabbitAdmin.declareExchange(contractTopicExchange);
//        return contractTopicExchange;
//    }
//
//    @Bean
//    DirectExchange contractDirectExchange() {
//        DirectExchange contractDirectExchange = new DirectExchange("com.exchange.direct");
//        this.rabbitAdmin.declareExchange(contractDirectExchange);
//        return contractDirectExchange;
//    }
//
//    @Bean
//    Queue queueHello() {
//        Queue queue = new Queue("com.queue.notify.hello", true);
//        this.rabbitAdmin.declareQueue(queue);
//        return queue;
//    }





}
