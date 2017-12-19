package com.d1m.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MaterialMiniProgramMapper;
import com.d1m.wechat.model.MaterialMiniProgram;
import com.d1m.wechat.service.MaterialMiniProgramService;

@Service
public class MaterialMiniProgramServiceImpl extends BaseService<MaterialMiniProgram> implements MaterialMiniProgramService {

    @Resource
    private MaterialMiniProgramMapper materialMiniProgramMapper;

    @Override
    public Mapper<MaterialMiniProgram> getGenericMapper() {
        return materialMiniProgramMapper;
    }

    @Override
    public MaterialMiniProgram search(Integer wechatId, Integer materialId) {
        MaterialMiniProgram materialMiniProgram = new MaterialMiniProgram();
        materialMiniProgram.setWechatId(wechatId);
        materialMiniProgram.setMaterialId(materialId);
        return materialMiniProgramMapper.selectOne(materialMiniProgram);
    }


}
