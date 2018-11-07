package com.d1m.wechat.service.impl;

import com.d1m.wechat.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * Created by jone.wang on 2018/8/17.
 * Description:
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async
    @Override
    public void asyncInvoke(@NotNull AsyncService.AsyncExec asyncExec) {
        asyncExec.exec();
    }


}
