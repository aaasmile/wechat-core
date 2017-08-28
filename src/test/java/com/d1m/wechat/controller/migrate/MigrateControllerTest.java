package com.d1m.wechat.controller.migrate;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.test.SpringMVCTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 类描述
 *
 * @author Yuan Zhen on 2016-12-05.
 */
public class MigrateControllerTest extends SpringMVCTest {

    private static String contentType = "application/json;charset=UTF-8";
    private String username = "admin";

    @Before
    public void login() throws Exception {
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", "12345");
        mockMvc.perform(post("/user/login").content(data.toJSONString()).contentType(contentType))
               .andDo(print())//输出MvcResult到控制台
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.resultCode").value("1"))
               .andExpect(jsonPath("$.data.username").value(username))
        ;
    }

    @Test
    public void migrateOpenId() throws Exception {
        mockMvc.perform(get("/migrate/openid.json"))
               .andDo(print())//输出MvcResult到控制台
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$.resultCode").value("1"))
        ;
    }

    @Test
    public void migrateNews() throws Exception {
        mockMvc.perform(
                get("/migrate/material.json")
                .param("type", "news")
                .param("update", "true")
        )
               .andDo(print())//输出MvcResult到控制台
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$.resultCode").value("1"))
        ;
    }
}