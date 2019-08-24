package com.fosun.rabbitmq.config;

/**
 * @author: Christ
 * @date: 2019/8/24 17:55
 * @desc:
 */

public class RabbitmqConstant {

    public final static String DELAY_ORDER_QUEUE_NAME = "queue-delay-order";

    public final static String CONSUME_ORDER_QUEUE_NAME = "queue-consume-order";

    public final static String EXCHANGE_CONSUME_ORDER_NAME = "exchange-consume-order";

    public final static String ROUTINE_KEY_CONSUME_ORDER_NAME = "rk.consume.order";

    public final static String DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";

    public final static String DEAD_LETTER_ROUTINE_KEY = "x-dead-letter-routing-key";





}
