package com.fosun.basis.rabbitmq.christ_05_faire_dispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 17:02
 * @desc:
 */

public class FireNewTask {

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
        // 公平转发
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        // 发送消息
        for (int i = 10; i >0; i--) {
            String message = "Liang:" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
