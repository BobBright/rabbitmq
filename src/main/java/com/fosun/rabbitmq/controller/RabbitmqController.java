package com.fosun.rabbitmq.controller;

import com.fosun.rabbitmq.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Christ
 * @date: 2019/8/24 17:25
 * @desc:
 */

@RestController
public class RabbitmqController {

    @Autowired
    private MsgProducer msgProducer;

    @GetMapping("/send")
    public String send() {
        try {
            msgProducer.sendMessage("send",30000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("/send2")
    public String send2() {
        try {
            msgProducer.sendMessage2("send 2",30000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
