package com.fosun.basis.rabbitmq.christ_003_workqueue_autoack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 14:57
 * @desc: 消息应答
 */

public class AckNewTask {


    public static void main(String[] args) throws IOException, TimeoutException {

        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();

        //配置工厂
        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        //创建连接
        Connection connection = factory.newConnection();

        //创建频道
        Channel channel = connection.createChannel();
        //指定队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

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
