package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.ImportCsvDto;
import com.d1m.wechat.dto.NofSendDto;
import com.d1m.wechat.mapper.MaterialCategoryMapper;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialCategory;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.model.enums.MaterialType;
import com.d1m.wechat.pamametermodel.MaterialCategoryEntity;
import com.d1m.wechat.service.MaterialCategoryService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.NofSendService;
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
public class NofSendServiceImpl implements NofSendService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialImageTextDetailMapper materialImageTextDetailMapper;

    @Override
    public int save(NofSendDto dto) {
        Material material = new Material();
        BeanUtils.copyProperties(dto, material);
        material.setCreatedAt(new Date());
        material.setMaterialType(MaterialType.IMAGE_TEXT.getValue());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setIsScrm(1);
        materialMapper.insert(material);

        List<ImageTextDto> list = dto.getItems();
        List<MaterialImageTextDetail> imageTextDetailList = new ArrayList<>();
        for (ImageTextDto importCsvDto : list) {
            MaterialImageTextDetail detail = new MaterialImageTextDetail();
            if (CollectionUtils.isNotEmpty(list)) {
                BeanUtils.copyProperties(importCsvDto, detail);
                detail.setMaterialId(material.getId());
                detail.setStatus((byte) 1);
                imageTextDetailList.add(detail);

            }
        }
        return materialImageTextDetailMapper.insertList(imageTextDetailList);
    }

    @Override
    public NofSendDto queryObject(String id) {
        NofSendDto nofSendDto = new NofSendDto();
        Material mc = materialMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(mc, nofSendDto);
        return nofSendDto;
    }


    @Override
    public void update(NofSendDto dto) {
        Material material = new Material();
        BeanUtils.copyProperties(dto, material);
        material.setCreatedAt(new Date());
        material.setMaterialType(MaterialType.IMAGE_TEXT.getValue());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setIsScrm(1);
        materialMapper.updateByPrimaryKey(material);

        List<ImageTextDto> list = dto.getItems();
        for (ImageTextDto importCsvDto : list) {
            MaterialImageTextDetail detail = new MaterialImageTextDetail();
            if (CollectionUtils.isNotEmpty(list)) {
                BeanUtils.copyProperties(importCsvDto, detail);
                detail.setMaterialId(material.getId());
                detail.setStatus((byte) 1);
                materialImageTextDetailMapper.updateByPrimaryKey(detail);
            }
        }
    }


    /**
     * 删除素材分类
     *
     * @param id
     */
    public void delete(String id) {

        materialMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<NofSendDto> queryList(Map<String, Object> map) {
        return materialMapper.queryList(map);
    }


}
