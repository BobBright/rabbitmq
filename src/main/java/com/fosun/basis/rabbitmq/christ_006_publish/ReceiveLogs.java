package com.fosun.basis.rabbitmq.christ_006_publish;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 17:26
 * @desc:
 */

public class ReceiveLogs {

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接
        ConnectionFactory factory = new ConnectionFactory();

        // 设置 RabbitMQ 的主机名
        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        //创建connection
        Connection connection = factory.newConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //指定一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //创建一个非持久的、唯一的、自动删除的队列
        String queueName = channel.queueDeclare().getQueue();

        //绑定交换器和队列
        //Exchange.BindOk exchangeBind(String destination, String source, String routingKey)
        //参数1 queue：队列名
        //参数2 exchange：交换器名
        //参数3 routinekey： 路由器名
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        final Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
