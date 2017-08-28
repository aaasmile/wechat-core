package com.d1m.wechat.service.engine.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.d1m.wechat.client.model.request.WxArticleMessage;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.service.BusinessService;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.engine.api.IApi;

@Service
public class LbsOutletApi implements IApi {
    private static Logger log = LoggerFactory.getLogger(LbsOutletApi.class);

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ConfigService configService;

    @Override
    public void handle(Integer wechatId, Conversation conversation,
                       List<Member> members) {
        //设置经纬度查询条件
        Double lng = conversation.getLocationY();
        Double lat = conversation.getLocationX();
        int size = 3;
        String sizeStr = configService.getConfigValue(wechatId,"LBS","OUTLET_SIZE");
        if(sizeStr!=null){
            try {
                size = Integer.parseInt(sizeStr);
            }catch (Exception e){
                log.error("parse outlet size error",e);
            }
        }
        List<BusinessDto> list = businessService.searchByLngLat(wechatId,lng,lat,size);
        for(Member member:members){
            sendMessage(wechatId,member,list);
        }
    }

    private void sendMessage(Integer wechatId,Member member,List<BusinessDto> list){
        try {
            List<WxArticleMessage> articles = new ArrayList<>();
            String firstTitle = configService.getConfigValue(wechatId, "LBS","FIRST_TITLE");
            String firstPic = configService.getConfigValue(wechatId, "LBS","FIRST_PIC");
            String firstUrl = configService.getConfigValue(wechatId, "LBS","FIRST_URL");
            if(StringUtils.isNotBlank(firstPic)&&StringUtils.isNotBlank(firstUrl)){
                WxArticleMessage first = new WxArticleMessage();
                first.setTitle(firstTitle);
                first.setPicUrl(firstPic);
                first.setUrl(firstUrl);
                articles.add(first);
            }
            for (BusinessDto business : list) {
                WxArticleMessage article = new WxArticleMessage();
                BigDecimal distance = business.getDistance();
                if(distance!=null){
                    distance = distance.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP) ;
                }else{
                    distance = new BigDecimal(0);
                }
                article.setTitle(business.getBusinessName()+"【距您"+distance.doubleValue()+"公里】");
                article.setDescription(business.getIntroduction());
                //设置图片
                List<String> plist = business.getPhotoList();
                if(plist!=null&&plist.size()>0){
                    article.setPicUrl(plist.get(0));
                }else{
                    //设置默认
                    article.setPicUrl("http://placehold.it/320x200");
                }
                String outlet_url = configService.getConfigValue(wechatId,"LBS","OUTLET_URL");
                //设置前端访问页面
                article.setUrl(outlet_url+business.getId());
                articles.add(article);
            }

            WechatClientDelegate.sendCustomMessage(wechatId, member.getOpenId(), "news", articles);
        } catch (Exception e) {
            log.error("LbsOutletApi send msg error:"+e.getMessage());
        }
    }
}