package com.fosun.basis.rabbitmq.christ_001_helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 9:52
 * @desc:
 */

public class Sender {


    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //配置主机名
        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        //3.创建连接
        Connection connection = factory.newConnection();

        //4.创建一个通道
        Channel channel = connection.createChannel();

        //5.指定一个队列

//        queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
//        Map<String, Object> arguments)
        //参数1 queue：队列的名称
        //参数2 durable；是否可持久化
        //参数3 exclusive：仅创建者可以使用的私有队列，断开后自动删除
        //参数4 autoDelete: 当所有消费端关闭连接，是否自动删除队列
        //参数5 arguments: 参数列表
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //6.发送消息
        String msg = "Hello World";

        // basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        // 参数1 exchange ：交换器
        // 参数2 routingKey ： 路由键
        // 参数3 props ： 消息的其他参数
        // 参数4 body ： 消息体
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println(" [x] Sended '" + msg + "'");
        //7.关闭频道
        channel.close();
        connection.close();

    }
}
