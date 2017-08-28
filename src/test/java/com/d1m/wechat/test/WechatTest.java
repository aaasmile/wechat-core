package com.d1m.wechat.test;

import java.io.IOException;
import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;

import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.wechatclient.WechatClientDelegate;

/**
 * WechatTest
 *
 * @author f0rb on 2016-12-22.
 */
public class WechatTest extends SpringTest {

    public static Integer wechatId = 1;

    @Resource
    private WechatService wechatService;

    @Before
    public void before() throws IOException {
        Wechat wechat = wechatService.getById(wechatId);
        WechatClientDelegate.create(wechat.getId(), wechat.getAppid(), wechat.getAppscret());
    }

    @After
    public void after() throws IOException {
        WechatClientDelegate.destroy(wechatId);
    }

}
