package com.d1m.wechat.schedule;

import com.d1m.wechat.configure.FeignClientConfiguration;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * SchedulerRestService
 *
 * @author stoney.liu on 2017-07-04.
 */
@FeignClient(
        name = "wechat-scheduler",
        configuration = FeignClientConfiguration.class
)
public interface SchedulerRestService {

    @RequestMapping(value = "/api/addJob", method = RequestMethod.POST, consumes = "application/json")
    ReturnT<String> addJob(@RequestBody Map<String,Object> jobMap);
}
