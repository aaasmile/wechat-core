package com.d1m.wechat.service.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d1m.wechat.migrate.MigrateResult;
import com.d1m.wechat.service.MigrateService;
import com.d1m.wechat.test.WechatTest;

/**
 * MigrateServiceImplTest
 *
 * @author f0rb on 2016-12-22.
 */
public class MigrateServiceImplTest extends WechatTest {
	
	private static final Logger log = LoggerFactory.getLogger(MigrateServiceImplTest.class);
	
    @Resource
    private MigrateService migrateService;

    @BeforeClass
    public static void init() {
        wechatId = 1;
    }

    @Test
    public void migrateUser() throws Exception {
        MigrateResult migrateResult = migrateService.migrateUser(wechatId);
        log.info("更新会员信息: {}", JSON.toJSONString(migrateResult));
    }

    @Test
    public void migrateOpenId() throws Exception {
        MigrateResult migrateResult = migrateService.migrateOpenId(wechatId, null);
        log.info("同步openid: {}", JSON.toJSONString(migrateResult));
    }
}