package com.fosun.basis.springboowithrabbitmq.controller;

import com.fosun.basis.springboowithrabbitmq.service.AckSenderService;
import com.fosun.basis.springboowithrabbitmq.service.ConfirmService;
import com.fosun.basis.springboowithrabbitmq.service.TransactionService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author: Christ
 * @date: 2019/8/22 13:11
 * @desc:https://github.com/fujiangwei/springboot-learn/blob/master/springboot-rabbitmq/src/main/java/com/example/springbootrabbitmq/service/AckSenderService.java
 */

@RestController
public class SenderController {

    /**
     *默认类型
     */

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private AckSenderService ackSenderService;

    @Autowired
    private TransactionService txService;

    @Autowired
    private ConfirmService confirmService;


    /**
     * 单条消息发送
     * @return
     */
    @GetMapping("/send")
    public String send() {
        String msg = "Date:"+System.currentTimeMillis();
        //发送默认交换机对应的队列christ-queue
        amqpTemplate.convertAndSend("christ-queue",msg);
        return msg;
    }


    @GetMapping("/sendMore")
    public String sendMore(Integer count) {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            String msg = "sn : "+i+" ; msg : "+UUID.randomUUID().toString();
            amqpTemplate.convertAndSend("christ-queue",msg);
            result.add(msg);
        }

        return String.join("<br />",result);
    }

    @GetMapping("/sendMoreQueues")
    public String sendMoreQueue(Integer count) {

        List<String> result = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            String msg = "sn : "+i+" ; msg : "+UUID.randomUUID().toString();
            //发送消息到默认交换器中的christ-queue中
            amqpTemplate.convertAndSend("christ-queue",msg);
            //发送消息到默认交换器中的christ-queue2中
            amqpTemplate.convertAndSend("christ-queue2",msg);
            result.add(msg);
        }
        return String.join("<br />",result);
    }

    /**
     * Topic模式的exchange消息发送，此时topic.msg符合队列topic.msg和topic.msgs绑定的routingKey，
     * 故发送的消息会传送到队列topic.msg和topic.msgs
     *
     * @return
     */
    @GetMapping("/topicSend")
    public String topicSend() {
        String msg = " msg : "+UUID.randomUUID().toString();
        //发送到对应的topicExchange交换机对应的队列中topic queue
        //	void convertAndSend(String exchange, String routingKey, Object message) throws AmqpException;
        //参数1 exchange:交换机名称
        //参数2 routineKey：路由键
        //参数3 消息
        amqpTemplate.convertAndSend("topicExchange","topic.msg",msg);
        return msg;
    }


    /**
     * Topic模式的exchange消息发送，此时topic.msgs符合只符合topic.msgs绑定的routingKey，
     * 故发送的消息只传送到队列topic.msgs
     *
     * @return
     */
    @GetMapping("/topicSend2")
    public String topicSend2() {
        String msg = "msg : "+UUID.randomUUID().toString();
        amqpTemplate.convertAndSend("topicExchange","topic.msgs",msg);
        return msg;
    }

    /**
     * @return
     */
    @GetMapping(value = "ackSend")
    public String ackSend() {
        ackSenderService.send();
        return "ok";
    }

    /**
     * @return
     */
    @GetMapping(value = "sendedReturnBack")
    public String sendedReturnBack() {
        ackSenderService.sendedReturnBack();
        return "ok";
    }


    /**
     * @return
     */
    @GetMapping(value = "sendObj")
    public String sendObj() {
        ackSenderService.sendObj();

        return "ok";
    }

    /**
     * @return
     */
    @GetMapping(value = "txSend")
    public String txSend() throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {
        txService.publish();
        return "txSend";
    }

    /**
     * @return
     */
    @GetMapping(value = "txRec")
    public String txRec() throws InterruptedException, TimeoutException, IOException {
        txService.consume();
        return "txRec";
    }

    /**
     * @return
     */
    @GetMapping(value = "confirmSend")
    public String confirmSend() throws InterruptedException, TimeoutException, IOException {
        confirmService.publish();
        return "confirmSend";
    }

    /**
     * @return
     */
    @GetMapping(value = "confirmConsume")
    public String confirmConsume() throws InterruptedException, TimeoutException, IOException {
        confirmService.consume();
        return "confirmConsume";
    }

}
