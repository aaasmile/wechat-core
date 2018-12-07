package com.d1m.wechat.configure;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jone.wang on 2018/12/6.
 * Description:
 */
@Configuration
public class XxlJobConfig {

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private Integer port;

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.logpath}")
    private String logpath;


    @Bean
    @ConditionalOnExpression("${enable.xxl-job:true}")
    public XxlJobExecutor xxlJobExecutor() {
        final XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setIp(ip);
        xxlJobExecutor.setPort(port);
        xxlJobExecutor.setAppName(appName);
        xxlJobExecutor.setAdminAddresses(adminAddresses);
        xxlJobExecutor.setLogPath(logpath);
        return xxlJobExecutor;
    }
}
