package com.d1m.wechat.service;

import com.d1m.wechat.model.MemberQrcodeInvited;
import com.d1m.wechat.pamametermodel.QrcodeModel;

import java.util.List;

public interface MemberQrcodeInvitedService extends IService<MemberQrcodeInvited> {

    public int create(MemberQrcodeInvited memberQrcodeInvited);

    public List<MemberQrcodeInvited> getListOrderByCreatedAt(Integer wechatId, Integer memberId);
}