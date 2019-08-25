package com.fosun.basis.rabbitmq.christ_004_queuedurable;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;
import static com.fosun.basis.rabbitmq.BasisConstant.PASSWORD;

/**
 * @author: Christ
 * @date: 2019/8/21 17:00
 * @desc:
 */

public class DurableNewTask {

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
        // 指定一个队列
        String queueName = "durable_queue_name";
        channel.queueDeclare(queueName, true, false, false, null);
        // 发送消息
        for (int i = 0; i < 10; i++) {
            String message = "Liang:" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();


    }
}
