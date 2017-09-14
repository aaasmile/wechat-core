package com.d1m.wechat.mapper;

import com.d1m.wechat.model.MemberQrcodeInvited;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

public interface MemberQrcodeInvitedMapper extends MyMapper<MemberQrcodeInvited> {

    List<MemberQrcodeInvited> getListOrderByCreatedAt(MemberQrcodeInvited memberQrcodeInvited);
}