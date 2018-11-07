package com.d1m.wechat.service;

/**
 * Created by jone.wang on 2018/8/17.
 * Description: 异步方法接口，实现AsyncExec 执行任意代码块
 */
public interface AsyncService {

    void asyncInvoke(AsyncExec consumer);

    @FunctionalInterface
    interface AsyncExec {
        void exec();
    }
}
