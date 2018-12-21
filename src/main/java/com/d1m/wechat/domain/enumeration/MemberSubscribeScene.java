package com.d1m.wechat.domain.enumeration;

/**
 * Created by jone.wang on 2018/12/4.
 * Description:
 */
public enum MemberSubscribeScene {
    ADD_SCENE_SEARCH("公众号搜索"),
    ADD_SCENE_ACCOUNT_MIGRATION("公众号迁移"),
    ADD_SCENE_PROFILE_CARD("名片分享"),
    ADD_SCENE_QR_CODE("扫描二维码"),
    ADD_SCENE_PROFILE_LINK("图文页内名称点击"),
    ADD_SCENE_PROFILE_ITEM("图文页右上角菜单"),
    ADD_SCENE_PAID("支付后关注"),
    ADD_SCENE_OTHERS("其他");

    private String value;

    MemberSubscribeScene(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
