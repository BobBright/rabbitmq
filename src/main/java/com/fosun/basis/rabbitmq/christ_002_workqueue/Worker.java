package com.fosun.basis.rabbitmq.christ_002_workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.fosun.basis.rabbitmq.BasisConstant.*;

/**
 * @author: Christ
 * @date: 2019/8/21 14:22
 * @desc:
 * 我们发现，通过增加更多的工作程序就可以进行并行工作，并且接受的消息平均分配。
 * 注意的是，这种分配过程是一次性分配，并非一个一个分配。
 */

public class Worker {


    public static void main(String[] args) throws IOException, TimeoutException {

        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(HOST);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        //创建连接
        Connection connection = factory.newConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //指定队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + msg + "'");

                try {
                    doWork(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        //acknowledgment is covered below
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);


    }

    private static void doWork(String msg) throws InterruptedException {
        String[] taskArr = msg.split(":");
        TimeUnit.SECONDS.sleep(Long.valueOf(taskArr[1]));
    }

}
