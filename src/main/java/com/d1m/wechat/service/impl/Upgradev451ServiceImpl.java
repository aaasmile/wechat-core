package com.d1m.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.service.Upgradev451Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class Upgradev451ServiceImpl implements Upgradev451Service {

    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private MaterialImageTextDetailMapper materialImageTextDetailMapper;
    @Autowired
    private DcrmImageTextDetailMapper dcrmImageTextDetailMapper;
    @Autowired
    private ActionEngineMapper actionEngineMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void move2dcrm() {
        final Example materialExample = new Example(Material.class);
        materialExample.createCriteria().andCondition("modify_at != ifnull(last_push_at, now())");
        List<Material> materialList = materialMapper.selectByExample(materialExample);
        for(int i = 0; i < materialList.size(); i++) {
            Material materialR = materialList.get(i);
            final Example materialImageTextDetailExample = new Example(MaterialImageTextDetail.class);
            materialImageTextDetailExample.createCriteria().andEqualTo("materialId",materialR.getId());
            List<MaterialImageTextDetail> materialImageTextDetailList = materialImageTextDetailMapper.selectByExample(materialImageTextDetailExample);
            for(int j = 0; j < materialImageTextDetailList.size(); j++) {
                //插入dcrmImageTextDetail数据
                MaterialImageTextDetail materialImageTextDetailR = materialImageTextDetailList.get(j);
                Material coverMaterial = materialMapper.selectByPrimaryKey(materialImageTextDetailR.getMaterialCoverId());
                DcrmImageTextDetail dcrmImageTextDetail = new DcrmImageTextDetail();
                dcrmImageTextDetail.setTitle(materialImageTextDetailR.getTitle());
                dcrmImageTextDetail.setSummary(materialImageTextDetailR.getSummary());
                dcrmImageTextDetail.setContent(materialImageTextDetailR.getContent());
                dcrmImageTextDetail.setWechatId(materialImageTextDetailR.getWechatId());
                dcrmImageTextDetail.setCreatedAt(new Date());
                dcrmImageTextDetail.setCreatedBy(1);
                dcrmImageTextDetail.setStatus((byte) 1);
                dcrmImageTextDetail.setMaterialCoverId(coverMaterial.getId());
                dcrmImageTextDetail.setUrl(materialImageTextDetailR.getUrl());
                dcrmImageTextDetailMapper.insert(dcrmImageTextDetail);
                //逻辑删除数据
                materialImageTextDetailR.setStatus((byte) 0);
                materialImageTextDetailMapper.updateByPrimaryKey(materialImageTextDetailR);
            }
        }
    }

    @Override
    public void upgradeMenu() {
        List<Menu> menuList = menuMapper.selectAll();
        if(menuList == null || menuList.isEmpty()) {
            return;
        }
        for( int i = 0; i < menuList.size(); i++) {
            Menu menuQ = menuList.get(i);
            Material materialR = materialMapper.selectByPrimaryKey(menuQ.getMenuKey());

            final Example materialImageTextDetailExample = new Example(Material.class);
            materialImageTextDetailExample.createCriteria().andEqualTo("materialId", materialR.getId()).andEqualTo("status",(byte) 1);
            List<MaterialImageTextDetail> materialImageTextDetailList = materialImageTextDetailMapper.selectByExample(materialImageTextDetailExample);
            if(materialImageTextDetailList == null || materialImageTextDetailList.isEmpty()) {
                continue;
            }
            MaterialImageTextDetail materialImageTextDetailR = materialImageTextDetailList.get(0);

            menuQ.setUrl("21");
            menuQ.setMenuKey(materialImageTextDetailR.getId());
            menuMapper.updateByPrimaryKey(menuQ);
        }

        for( int j = 0; j < menuList.size(); j++) {
            Menu menuQ = menuList.get(j);
            Material materialR = materialMapper.selectByPrimaryKey(menuQ.getMenuKey());

            final Example dcrmImageTextDetailExample = new Example(Material.class);
            dcrmImageTextDetailExample.createCriteria().andEqualTo("materialId", materialR.getId());
            List<DcrmImageTextDetail> dcrmImageTextDetailList = dcrmImageTextDetailMapper.selectByExample(dcrmImageTextDetailExample);
            if(dcrmImageTextDetailList == null || dcrmImageTextDetailList.isEmpty()) {
                continue;
            }
            DcrmImageTextDetail dcrmImageTextDetailR = dcrmImageTextDetailList.get(0);

            menuQ.setUrl("22");
            menuQ.setMenuKey(dcrmImageTextDetailR.getId());
            menuMapper.updateByPrimaryKey(menuQ);
        }
    }
    @Override
    public void upgradeActionEngine() {
        List<ActionEngine> actionEngineList = actionEngineMapper.selectAll();
        for(int i = 0; i < actionEngineList.size(); i++) {
            ActionEngine actionEngineR = actionEngineList.get(i);
            if(actionEngineR == null || StringUtils.isEmpty(actionEngineR.getEffect())) {
                continue;
            }
            JSONArray jsonArray = JSONArray.parseArray(actionEngineR.getEffect());
            for(int j = 0; j < jsonArray.size(); j++) {
                JSONObject dcrmObj = jsonArray.getJSONObject(j);
                if(dcrmObj.containsKey("value") && dcrmObj.containsKey("code") && "201".equals(dcrmObj.getString("code"))) {
                    String materialIdS = dcrmObj.getString("value").replace("[","").replace("]","");
                    Integer materialId = Integer.valueOf(materialIdS);
                    Material materialR = materialMapper.selectByPrimaryKey(materialId);

                    final Example dcrmImageTextDetailExample = new Example(Material.class);
                    dcrmImageTextDetailExample.createCriteria().andEqualTo("materialId", materialR.getId());
                    List<DcrmImageTextDetail> dcrmImageTextDetailList = dcrmImageTextDetailMapper.selectByExample(dcrmImageTextDetailExample);
                    if(dcrmImageTextDetailList != null && !dcrmImageTextDetailList.isEmpty()) {
                        DcrmImageTextDetail dcrmImageTextDetailR = dcrmImageTextDetailList.get(0);

                        dcrmObj.put("value","[" + dcrmImageTextDetailR.getId() + "]");
                    } else {
                        final Example materialImageTextDetailExample = new Example(Material.class);
                        materialImageTextDetailExample.createCriteria().andEqualTo("materialId", materialR.getId()).andEqualTo("status",(byte) 1);
                        List<MaterialImageTextDetail> materialImageTextDetailList = materialImageTextDetailMapper.selectByExample(materialImageTextDetailExample);
                        if(materialImageTextDetailList == null || materialImageTextDetailList.isEmpty()) {
                            continue;
                        }
                        MaterialImageTextDetail materialImageTextDetailR = materialImageTextDetailList.get(0);
                        dcrmObj.put("value","[" + materialImageTextDetailR.getId() + "]");
                    }
                }
            }
            actionEngineR.setEffect(jsonArray.toJSONString());
            actionEngineMapper.updateByPrimaryKey(actionEngineR);
        }
    }
}
