package com.d1m.wechat.schedule.job;

import com.d1m.wechat.schedule.BaseJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/**
 * 任务Handler的一个Demo（Bean模式）
 *
 * 开发步骤：
 * 1、继承 “BaseJobHandler” ；
 * 2、装配到Spring，例如加 “@Component” 注解；
 * 3、加 “@JobHander” 注解，注解value值为新增任务生成的JobKey的值;多个JobKey用逗号分割;
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志，方便管理端查看日志；
 *
 * @author stoney.liu 2017-07-04 14:43:36
 */
@JobHander(value="demoJob")
@Component
public class DemoJob extends BaseJobHandler {

	@Override
	public ReturnT<String> run(String... strings) throws Exception {
		XxlJobLogger.log("DEMO-JOB, Hello World.");
		return ReturnT.SUCCESS;
	}
}
