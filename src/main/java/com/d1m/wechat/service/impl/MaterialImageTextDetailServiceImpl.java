package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.mapper.MaterialMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.MaterialImageTextDetailDto;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.service.MaterialImageTextDetailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MaterialImageTextDetailServiceImpl extends
 BaseService<MaterialImageTextDetail> implements
 MaterialImageTextDetailService {

    @Autowired
    private MaterialImageTextDetailMapper materialImageTextDetailMapper;

    @Autowired
    private MaterialMapper materialMapper;

    public void setMaterialImageTextDetailMapper(
     MaterialImageTextDetailMapper materialImageTextDetailMapper) {
        this.materialImageTextDetailMapper = materialImageTextDetailMapper;
    }

    @Override
    public Mapper<MaterialImageTextDetail> getGenericMapper() {
        return materialImageTextDetailMapper;
    }

    @Override
    public Page<MaterialImageTextDetailDto> search(Integer wechatId,
                                                   Integer materialId, String query, String sortName, String sortDir,
                                                   Integer pageNum, Integer pageSize, boolean queryCount) {
        PageHelper.startPage(pageNum, pageSize, queryCount);
        return materialImageTextDetailMapper.search(wechatId, materialId,
         query, sortName, sortDir);
    }

}
