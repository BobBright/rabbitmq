package com.fosun.basis.rabbitmq.christ_001_helloworld;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 9:52
 * @desc:
 */

@Slf4j
public class Receiver {



    public static void main(String[] args) throws IOException, TimeoutException {


        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //配置主机名
        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        //2.创建连接
        Connection connection = factory.newConnection();

        //3.创建频道
        Channel channel = connection.createChannel();


        //4.指定队列
        //参数1 queue：队列的名称
        //参数2 durable；是否可持久化
        //参数3 exclusive：仅创建者可以使用的私有队列，断开后自动删除
        //参数4 autoDelete: 当所有消费端关闭连接，是否自动删除队列
        //参数5 arguments: 参数列表
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //5.创建队列消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + msg + "'");
            }
        };

        //6.绑定消费者
        // basicConsume(String queue, boolean autoAck, Consumer callback)
        //参数1 queue:队列名
        //参数2 autoAck：是否自动Ack
        //参数3 callback：消费者对象的一个接口，用来配置回调
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }

}
