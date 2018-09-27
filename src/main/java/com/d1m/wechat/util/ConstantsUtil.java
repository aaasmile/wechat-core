package com.d1m.wechat.util;

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

    /**用户关注的渠道来源**/
    public static final Map<String,String> subscribeSceneMap  = new HashMap<String, String>(){{
        put("ADD_SCENE_SEARCH","公众号搜索");
        put("ADD_SCENE_ACCOUNT_MIGRATION","公众号迁移");
        put("ADD_SCENE_PROFILE_CARD","名片分享");
        put("ADD_SCENE_QR_CODE","扫描二维码");
        put("ADD_SCENEPROFILE","LINK 图文页内名称点击");
        put("ADD_SCENE_PROFILE_ITEM","ITEM 图文页右上角菜单");
        put("ADD_SCENE_PAID","支付后关注");
        put("ADD_SCENE_OTHERS","其他");
    }};
}
