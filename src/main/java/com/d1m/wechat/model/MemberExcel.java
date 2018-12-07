package com.d1m.wechat.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.d1m.wechat.domain.enumeration.MemberSubscribeScene;
import com.d1m.wechat.model.enums.MemberProfileStatus;
import com.d1m.wechat.model.enums.Sex;

import java.util.Date;

/**
 * Created by jone.wang on 2018/12/5.
 * Description:
 */
public class MemberExcel {


    @Excel(name = "no")
    private Integer id;

    @Excel(name = "nickname")
    private String nickname;

    @Excel(name = "gender")
    private Sex gender;

    @Excel(name = "mobile")
    private String mobile;

    @Excel(name = "province")
    private String province;

    @Excel(name = "city")
    private String city;

    @Excel(name = "subscribe.status")
    private Integer subscribeStatus;

    @Excel(name = "subscribe.at", databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private Date subscribeAt;

    @Excel(name = "unsubscribe.at", databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private Date unsubscribeAt;

    @Excel(name = "subscribe.scene")
    private MemberSubscribeScene subscribeScene;

    @Excel(name = "customer.service.open.id")
    private String openId;

    @Excel(name = "unionid")
    private String unionId;

    @Excel(name = "tag")
    private String tag;

    @Excel(name = "lang")
    private Byte lang;

    @Excel(name = "group.message.sent")
    private Integer groupMessageSent;

    @Excel(name = "bind.status")
    private MemberProfileStatus bindStatus;

    @Excel(name = "bind.at", databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private Date bindAt;

    @Excel(name = "bind.province")
    private String bindProvince;

    @Excel(name = "bind.city")
    private String bindCity;

    @Excel(name = "bind.county")
    private String bindCounty;

    @Excel(name = "bind.mobile")
    private String bindMobile;

    @Excel(name = "bind.gender")
    private Sex bindGender;

    @Excel(name = "bind.birthday", databaseFormat = "yyyy-MM-dd")
    private Date bindBirthday;

    @Excel(name = "remarks")
    private String remarks;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(Integer subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public Date getSubscribeAt() {
        return subscribeAt;
    }

    public void setSubscribeAt(Date subscribeAt) {
        this.subscribeAt = subscribeAt;
    }

    public Date getUnsubscribeAt() {
        return unsubscribeAt;
    }

    public void setUnsubscribeAt(Date unsubscribeAt) {
        this.unsubscribeAt = unsubscribeAt;
    }

    public MemberSubscribeScene getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(MemberSubscribeScene subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Byte getLang() {
        return lang;
    }

    public void setLang(Byte lang) {
        this.lang = lang;
    }

    public Integer getGroupMessageSent() {
        return groupMessageSent;
    }

    public void setGroupMessageSent(Integer groupMessageSent) {
        this.groupMessageSent = groupMessageSent;
    }

    public Date getBindAt() {
        return bindAt;
    }

    public void setBindAt(Date bindAt) {
        this.bindAt = bindAt;
    }

    public String getBindProvince() {
        return bindProvince;
    }

    public void setBindProvince(String bindProvince) {
        this.bindProvince = bindProvince;
    }

    public String getBindCity() {
        return bindCity;
    }

    public void setBindCity(String bindCity) {
        this.bindCity = bindCity;
    }

    public String getBindCounty() {
        return bindCounty;
    }

    public void setBindCounty(String bindCounty) {
        this.bindCounty = bindCounty;
    }

    public String getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile;
    }

    public Date getBindBirthday() {
        return bindBirthday;
    }

    public void setBindBirthday(Date bindBirthday) {
        this.bindBirthday = bindBirthday;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public MemberProfileStatus getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(MemberProfileStatus bindStatus) {
        this.bindStatus = bindStatus;
    }

    public Sex getBindGender() {
        return bindGender;
    }

    public void setBindGender(Sex bindGender) {
        this.bindGender = bindGender;
    }
}
