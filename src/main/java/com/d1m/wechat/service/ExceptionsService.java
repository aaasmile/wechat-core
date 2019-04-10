package com.d1m.wechat.service;

import com.d1m.wechat.configure.FeignClientConfiguration;
import com.d1m.wechat.model.Exceptions;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8080",name = "localhost", configuration = FeignClientConfiguration.class)
public interface ExceptionsService {

  @RequestMapping(value = "/api/v3/crm-scheduler/exceptions/create", method = RequestMethod.POST)
  String create(Exceptions exceptions);
}
