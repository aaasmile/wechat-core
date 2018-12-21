package com.d1m.wechat.service;

import com.d1m.wechat.configure.FeignClientConfiguration;
import com.d1m.wechat.model.CustomRequestBody;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: wechat-core
 * @Date: 2018/12/21 10:19
 * @Author: Liu weilin
 * @Description:
 */
@FeignClient(
 name = "social-wechat-api"
 , url = "http://social-wechat-api:10011/custom"
 ,configuration = FeignClientConfiguration.class)
public interface CustomerService {

    /*@RequestMapping(value = "sender/{wechatId}", method = RequestMethod.POST)
    String sender(@RequestBody CustomRequestBody customRequestBody, @PathVariable Integer wechatId) ;*/
}
