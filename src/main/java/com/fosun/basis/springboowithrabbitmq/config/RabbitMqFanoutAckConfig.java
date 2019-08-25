package com.fosun.basis.springboowithrabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 /**
 * @author: Christ
 * @date: 2019/8/23 10:45
 * @desc:
 *
 *
 * @modifier:
 * @since:
 */
@Configuration
public class RabbitMqFanoutAckConfig {

    @Bean
    public Queue ackQueue() {
        return new Queue("ackQueue");
    }

    @Bean
    public Queue ackQueue2() {
        return new Queue("ackQueue2");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingAckQueue2Exchange(Queue ackQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(ackQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingAckQueue22Exchange(Queue ackQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(ackQueue2).to(fanoutExchange);
    }

    @Bean
    FanoutExchange invalidFanoutExchange() {
        return new FanoutExchange("invalidFanoutExchange");
    }
}