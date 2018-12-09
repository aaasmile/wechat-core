package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.MaterialCategoryDto;
import com.d1m.wechat.mapper.MaterialCategoryMapper;
import com.d1m.wechat.model.MaterialCategory;

import com.d1m.wechat.service.MaterialCategoryService;
import com.d1m.wechat.util.MapUtils;
import com.d1m.wechat.util.Query;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:26
 * @Author: Liu weilin
 * @Description:
 */
@Service
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;

    @Override
    public int save(MaterialCategoryDto dto) {
        MaterialCategory materialCategory = new MaterialCategory();
        BeanUtils.copyProperties(dto,materialCategory);
        materialCategory.setCreatedAt(new Date());
        materialCategory.setDeleted("0");
        return materialCategoryMapper.add(materialCategory);
    }

    @Override
    public MaterialCategoryDto queryObject(String id) {
        MaterialCategoryDto MaterialCategoryDto = new MaterialCategoryDto();
        MaterialCategory mc = materialCategoryMapper.queryObject(id);
        BeanUtils.copyProperties(mc, MaterialCategoryDto);
        return MaterialCategoryDto;
    }


    @Override
    public int update(MaterialCategoryDto dto) {
        MaterialCategory materialCategory = new MaterialCategory();
        BeanUtils.copyProperties(dto,materialCategory);
        materialCategory.setLasteUpdatedAt(new Date());
        return materialCategoryMapper.update(materialCategory);
    }

    /**
     * 查询素材名称是否存在
     * @param name
     * @return
     */
    public MaterialCategory exitsName(String name){
        MaterialCategory materialCategory = new MaterialCategory();
        materialCategory.setName(name);
         return materialCategoryMapper.selectOne(materialCategory);
    }


    /**
     * 删除素材分类
     * @param id
     */
    public void delete(String id){

        materialCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<MaterialCategory> queryList(MaterialCategoryDto dto) {
        PageHelper.startPage(dto.getCurrPage(), dto.getPageSize());
        Map<String,Object> query = MapUtils.beanToMap(dto);
        List<MaterialCategory> list = materialCategoryMapper.queryList(query);
        PageInfo<MaterialCategory> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
