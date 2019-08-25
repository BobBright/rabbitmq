package com.fosun.basis.springboowithrabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Christ
 * @date: 2019/8/20 15:55
 * @desc: 原文链接 https://blog.csdn.net/lx1309244704/article/details/81945325
 */

@Configuration
public class RabbitMqConfig {


    @Bean
    public Queue christQueue() {
        //Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)

        //参数1: name：队列的名称

        //参数2 durable：是否持久化。
        //默认情况下,队列把消息存储在内存中，若RabbitMQ挂掉，消息会全部丢失。
        // 如果durable = true，当RabbitMQ重新启动，会从erlang的Mnesia数据库中，重新读取该部分数据。

        //参数3 exclusive：排外的。
        //作用1：当connection.close()该队列会自动销毁。
        //作用2：定义该队列为私有的private。 -->扩展：如果不排外，会出现两个consumer同时访问该队列queue。如果exclusive=true,
        //会对当前访问的队列进行加锁，如果强行访问，会爆出
        //com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=405,
        // reply-text=RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'queue_name' in vhost '/', class-id=50, method-id=20)
        //一版情况下，一个队列只能有一个consumer访问场景下。


        //参数4 autoDelete：是否自动删除。当最后一个consumer断开连接，该队列是否自动删除。可以通过RabbitMQ Manager查看某个队列的consumer个数，
        //当consumers = 0时，会自动删除

        //参数5:传递的参数
        return new Queue("christ-queue",false,true,true,null);
    }


    @Bean
    public Queue christQueue2() {
        return new Queue("christ-queue2",false,true,true,null);
    }
}
