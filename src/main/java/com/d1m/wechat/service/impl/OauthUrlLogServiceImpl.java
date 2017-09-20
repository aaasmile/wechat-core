package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.OauthUrlLogMapper;
import com.d1m.wechat.model.OauthUrlLog;
import com.d1m.wechat.service.OauthUrlLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stoney.Liu on 2017/9/19.
 */
@Slf4j
@Service
public class OauthUrlLogServiceImpl extends BaseService<OauthUrlLog> implements OauthUrlLogService{
    // 每批插入条数
    private static final int batch_size = 100;
    private static List<OauthUrlLog> tmpList = new ArrayList<OauthUrlLog>();

    @Autowired
    private OauthUrlLogMapper oauthUrlLogMapper;

    @Override
    public Mapper<OauthUrlLog> getGenericMapper() {
        return oauthUrlLogMapper;
    }

    @Override
    @Async("callerRunsExecutor")
    public synchronized void batchInsertLog(OauthUrlLog oauthUrlLog) {
        if(tmpList.size()>=batch_size){
            oauthUrlLogMapper.insertList(tmpList);
            tmpList.clear();
        }
        tmpList.add(oauthUrlLog);
    }
}
