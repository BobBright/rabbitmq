package com.fosun.basis.springboowithrabbitmq.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Christ
 * @date: 2019/8/23 10:50
 * @desc:
 */

@Data
public class MessageObj implements Serializable {

    private final static long serialVersionUID =5088697673359856350L;

    private Integer id;
    private String name;
    private boolean isAck;



}
