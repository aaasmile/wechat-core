package com.d1m.wechat.service.impl;

import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.mapper.MemberTagDataMapper;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.util.MyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
@Service
@Slf4j
public class MemberTagDataServiceImpl implements MemberTagDataService {

    @Autowired
    private MemberTagDataMapper memberTagDataMapper;

    @Override
    public MyMapper<MemberTagData> getMapper() {
        return this.memberTagDataMapper;
    }
}
