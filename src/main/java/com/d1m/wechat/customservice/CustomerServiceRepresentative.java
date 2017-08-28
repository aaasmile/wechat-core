package com.d1m.wechat.customservice;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

/**
 * CustomerServiceRepresentative
 *
 * @author f0rb on 2017-03-16.
 */
@Getter
@Setter
public class CustomerServiceRepresentative {

    private Integer id;

    private String photoUrl;

    private String nickname;

    private String account;

    private AtomicInteger currentCustomerCount = new AtomicInteger(0);

}
