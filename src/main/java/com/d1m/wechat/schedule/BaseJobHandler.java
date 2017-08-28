package com.d1m.wechat.schedule;

import com.d1m.common.ds.TenantContext;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import java.util.Arrays;

/**
 * 定时任务基类
 * Created by Stoney.Liu on 2017/7/4.
 */
public abstract class BaseJobHandler extends IJobHandler {
    private static String prefix = "-d";
    @Override
    public ReturnT<String> execute(String... strings) throws Exception {
        // 适配多数据源
        if (strings!=null&&strings.length>0){
            String param = strings[0];
            if(param!=null&&param.startsWith(prefix)){
                TenantContext.setCurrentTenant(param.replaceAll(prefix,""));
                strings = Arrays.copyOfRange(strings,1,strings.length);
            }
        }
        return run(strings);
    }

    public abstract ReturnT<String> run(String... strings) throws Exception;
}
