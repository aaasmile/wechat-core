package com.d1m.wechat.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @program: wechat-core
 * @Date: 2018/9/21 17:01
 * @Author: Liu weilin
 * @Description: 常量定义
 */
public class ConstantsUtil {

    /**用户关注的渠道来源（中文）**/
    public static final Map<String,String> subscribeSceneMap_CN  = new HashMap<String, String>(){{
        put("ADD_SCENE_SEARCH","公众号搜索");
        put("ADD_SCENE_ACCOUNT_MIGRATION","公众号迁移");
        put("ADD_SCENE_PROFILE_CARD","名片分享");
        put("ADD_SCENE_QR_CODE","扫描二维码");
        put("ADD_SCENEPROFILE","LINK 图文页内名称点击");
        put("ADD_SCENE_PROFILE_ITEM","ITEM 图文页右上角菜单");
        put("ADD_SCENE_PAID","支付后关注");
        put("ADD_SCENE_OTHERS","其他");
    }};

    /**用户关注的渠道来源（英文）**/
    public static final Map<String,String> subscribeSceneMap_EN  = new HashMap<String, String>(){{
        put("ADD_SCENE_SEARCH","Search Official Account");
        put("ADD_SCENE_ACCOUNT_MIGRATION","Migrate Official Account");
        put("ADD_SCENE_PROFILE_CARD","Share Card");
        put("ADD_SCENE_QR_CODE","Scan QR Code");
        put("ADD_SCENEPROFILE","Click the link in the graphic page");
        put("ADD_SCENE_PROFILE_ITEM","Items on the top right corner of the graphic page");
        put("ADD_SCENE_PAID","Follow after Paid");
        put("ADD_SCENE_OTHERS","Others");
    }};

    /**
     * 语言转换
     * @param key
     * @param lang
     * @return
     */
    public static String subscribeSceneChangeLanguage(String key,String lang){
        if(StringUtils.equals("1",lang)||StringUtils.equals("CN",lang)){
            return subscribeSceneMap_CN.get(key);
        }else {
            return subscribeSceneMap_EN.get(key);
        }
    }


}
