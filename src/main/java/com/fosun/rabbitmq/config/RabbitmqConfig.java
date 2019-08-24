package com.fosun.rabbitmq.config;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fosun.rabbitmq.config.RabbitmqConstant.*;
import static com.fosun.rabbitmq.config.RabbitmqConstant.CONSUME_ORDER_QUEUE_NAME;

/**
 * @author: Christ
 * @date: 2019/8/24 12:41
 * @desc:
 */

@Slf4j
@Configuration
@Order(3)
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback{


    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.host}")
    private String addresses;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;
    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publisherReturns;


    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //设置集群方式
        //connectionFactory.setAddresses(this.addresses);
        //设置点单模式
        connectionFactory.setHost(this.host);
        connectionFactory.setPort(this.port);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setPublisherConfirms(this.publisherConfirms);
        connectionFactory.setPublisherReturns(this.publisherReturns);
        return connectionFactory;
    }


    @Bean
    public Connection orderConnection(ConnectionFactory connectionFactory) {
        return connectionFactory.createConnection();
    }


    @Bean
    public Channel delayOrderChannel(Connection orderConnection) {

        Channel delayOrderChannel = orderConnection.createChannel(false);

        Map<String,Object> args = new HashMap<>();
        //configure DEAD LETTER EXCHANGE
        args.put(DEAD_LETTER_EXCHANGE_KEY, RabbitmqConstant.EXCHANGE_CONSUME_ORDER_NAME);
        //configure DEAD LETTER EXCHANGE ROUTINE KEY
        args.put(DEAD_LETTER_ROUTINE_KEY, RabbitmqConstant.ROUTINE_KEY_CONSUME_ORDER_NAME);
        try {
            delayOrderChannel.queueDeclare(RabbitmqConstant.DELAY_ORDER_QUEUE_NAME, true, false, false, args);
        } catch (IOException e) {
            log.error("create delay order channel failed {}",e);
        }

        return delayOrderChannel;
    }



    @Bean
    public Channel consumeOrderChannel(Connection orderConnection) {

        Channel consumeOrderChannel = orderConnection.createChannel(false);

        try {
            //create an exchange
            consumeOrderChannel.exchangeDeclare(EXCHANGE_CONSUME_ORDER_NAME,"direct");

            //create a queue
            consumeOrderChannel.queueDeclare(CONSUME_ORDER_QUEUE_NAME,true,false,false,null);

            //bind queue to exchange with routine key
            consumeOrderChannel.queueBind(CONSUME_ORDER_QUEUE_NAME, EXCHANGE_CONSUME_ORDER_NAME,ROUTINE_KEY_CONSUME_ORDER_NAME);

            //最多接受条数 0为无限制，每次消费消息数(根据实际场景设置)，true=作用于整channel,false=作用于具体的消费者
            consumeOrderChannel.basicQos(0,10,false);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Consumer consumer = new DefaultConsumer(consumeOrderChannel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                try {
                    ConsumeMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                consumeOrderChannel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        try {
            consumeOrderChannel.basicConsume(CONSUME_ORDER_QUEUE_NAME,false,consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consumeOrderChannel;
    }



    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//注意：一定要prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        //exchange 根据路由键匹配不到对应的queue时，将会调用basic.return返回给生产者
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(this);
        return rabbitTemplate;
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    /**
     *
     * 方法描述:
     * 业务逻辑说明: TODO(总结性的归纳方法业务逻辑)
     * @param msg 消费消息（订单号，或特定格式json字符串）
     * @throws InterruptedException
     */
    public void ConsumeMessage(String msg) throws InterruptedException {
        Thread.sleep(50);//模拟业务逻辑处理
        log.info("处理到期消息时间=="+System.currentTimeMillis());
        log.info("删除订单 order-number  ==  "+msg);
    }



    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回调ID {}",correlationData);
        if (ack) {
            log.info("成功");
        }else{
            log.info("失败 {}",cause);
        }
    }
}
