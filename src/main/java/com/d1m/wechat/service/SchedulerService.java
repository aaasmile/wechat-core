package com.d1m.wechat.service;

import com.d1m.wechat.configure.FeignClientConfiguration;
import com.d1m.wechat.model.Scheduler;
import com.d1m.wechat.model.SchedulerVO;
import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${crm.service.url}",name = "crm-scheduler", configuration = FeignClientConfiguration.class)
public interface SchedulerService {

  @RequestMapping(value = "/api/v3/crm-scheduler/scheduler/create", method = RequestMethod.POST)
  String create(Scheduler scheduler);

  @RequestMapping(value = "/api/v3/crm-scheduler/scheduler/updateState", method = RequestMethod.POST)
  String updateState(Scheduler scheduler);

  @RequestMapping(value = "/api/v3/crm-scheduler/scheduler/list", method = RequestMethod.POST)
  List<Scheduler> list(SchedulerVO schedulerVO);

  @RequestMapping(value = "/api/v3/crm-scheduler/scheduler/count", method = RequestMethod.POST)
  Integer count(SchedulerVO schedulerVO);

}