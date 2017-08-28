package com.d1m.wechat.test;

import javax.annotation.Resource;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml", "classpath:wechat-servlet.xml"})
@ActiveProfiles("test")
public abstract class SpringMVCTest {

    @Resource
    private WebApplicationContext wac;
    @Resource
    private SecurityManager securityManager;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        ThreadContext.bind(securityManager);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
