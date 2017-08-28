package com.d1m.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.UserWechatMapper;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserWechat;
import com.d1m.wechat.service.UserWechatService;

@Service
public class UserWechatServiceImpl extends BaseService<UserWechat> implements
        UserWechatService {

    @Autowired
    private UserWechatMapper userWechatMapper;

    @Override
    public Mapper<UserWechat> getGenericMapper() {
        return userWechatMapper;
    }

    @Override
    public List<User> listWechatUsers(Integer wechatId) {
        return userWechatMapper.listWechatUsers(wechatId);
    }
}
