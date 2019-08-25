package com.fosun.basis.rabbitmq.christ_002_workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 14:08
 * @desc:
 */

public class NewTask {

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        //创建连接
        Connection connection = factory.newConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //指定消息队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //发送消息
        for (int i = 0; i <= 10; ++i) {
            String msg = "Christ:"+i;
            //参数1 exchange：交换器
            //参数2 routingKey 路由键
            //参数3 参数集合
            //参数4 消息体
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println(" [x] Sent '" + msg + "'");
        }

        //关闭频道和连接
        channel.close();
        connection.close();
    }
}
