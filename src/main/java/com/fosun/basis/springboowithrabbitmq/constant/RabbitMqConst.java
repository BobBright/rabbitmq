package com.fosun.basis.springboowithrabbitmq.constant;

/**
 * @author: Christ
 * @date: 2019/8/22 11:45
 * @desc:
 */

public class RabbitMqConst {

    //队列名称
    //****==================订单延时队列=======================*****//
    //订单延时队列
    public final String DELAY_QUEUE_NAME = "delay-queue-orderOverTime";
    //订单延时消费队列
    public final String CONSUME_QUEUE_NAME = "consume-queue-orderOverTime";
    //订单延时队列死信交换的交换器名称
    public final String EXCHANGENAME = "exchange-orderOverTime";
    //订单延时队列死信的交换器路由key
    public final String ROUTINGKEY = "routingKey-orderOverTime";

}
