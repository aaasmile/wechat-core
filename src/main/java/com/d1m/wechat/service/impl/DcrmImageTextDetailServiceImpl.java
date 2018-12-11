package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.ImportCsvDto;
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.mapper.DcrmImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialCategoryMapper;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.model.DcrmImageTextDetail;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialCategory;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.model.enums.MaterialType;

import com.d1m.wechat.service.MaterialCategoryService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.DcrmImageTextDetailService;
import com.d1m.wechat.util.MapUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import groovy.lang.DelegatesTo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:26
 * @Author: Liu weilin
 * @Description:
 */
@Service
public class DcrmImageTextDetailServiceImpl implements DcrmImageTextDetailService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;


    @Autowired
    private DcrmImageTextDetailMapper dcrmImageTextDetailMapper;

    @Override
    public int save(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setCreatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.insert(detail);
    }

    @Override
    public DcrmImageTextDetailDto queryObject(Integer id) {
        DcrmImageTextDetailDto DcrmImageTextDetailDto = new DcrmImageTextDetailDto();
        DcrmImageTextDetail imageTextDetail = dcrmImageTextDetailMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(imageTextDetail, DcrmImageTextDetailDto);
        return DcrmImageTextDetailDto;
    }


    @Override
    public int update(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setCreatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.updateByPrimaryKey(detail);
    }


    @Override
    public PageInfo<DcrmImageTextDetailDto> queryList(QueryDto dto) {
        PageHelper.startPage(dto.getCurrPage(), dto.getPageSize());
        Map<String, Object> query = MapUtils.beanToMap(dto);
        List<DcrmImageTextDetailDto> list = dcrmImageTextDetailMapper.queryList(query);
        PageInfo<DcrmImageTextDetailDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


}
