package com.fosun.basis.rabbitmq.christ_006_publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 18:05
 * @desc:
 */

public class EmitLog {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();

        // 设置 RabbitMQ 的主机名
        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();

        //指定一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //发送消息
        String msg = "CHRIST-MSG LOG";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println(" [x] Sent '" + msg + "'");

        //关闭通道和连接
        channel.close();
        connection.close();

    }
}
