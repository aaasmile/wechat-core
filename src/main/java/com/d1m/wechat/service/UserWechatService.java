package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserWechat;

public interface UserWechatService extends IService<UserWechat> {
    List<User> listWechatUsers(Integer wechatId);
}
