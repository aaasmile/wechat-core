package com.d1m.wechat.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.d1m.wechat.domain.enumeration.MemberSubscribeScene;
import com.d1m.wechat.model.enums.MemberProfileStatus;
import com.d1m.wechat.model.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by jone.wang on 2018/12/5.
 * Description:
 */
@Getter
@Setter
public class MemberExcel {

    private Integer memberId;

    @Excel(name = "no", type = 10, orderNum = "0")
    private Integer id;

    @Excel(name = "nickname", orderNum = "1")
    private String nickname;

    @Excel(name = "gender", orderNum = "2")
    private Sex gender;

    @Excel(name = "bind.mobile", width = 12, orderNum = "23")
    private String mobile;

    @Excel(name = "province", orderNum = "4")
    private String province;

    @Excel(name = "city", orderNum = "5")
    private String city;

    @Excel(name = "subscribe.status", orderNum = "6")
    private Integer subscribeStatus;

    @Excel(name = "subscribe.at", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "7")
    private Date subscribeAt;

    @Excel(name = "unsubscribe.at", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "8")
    private Date unsubscribeAt;

    @Excel(name = "subscribe.scene", width = 14, orderNum = "9")
    private MemberSubscribeScene subscribeScene;

    @Excel(name = "qrcode.name", width = 32, orderNum = "10")
    private String qrcodeName;

    @Excel(name = "customer.service.open.id", width = 32, orderNum = "11")
    private String openId;

    @Excel(name = "unionid", width = 32, orderNum = "12")
    private String unionId;

    @Excel(name = "tag", width = 22, orderNum = "13")
    private String tag;

    @Excel(name = "lang", orderNum = "14")
    private Byte lang;

    @Excel(name = "group.message.sent", orderNum = "15")
    private Integer groupMessageSent;

    @Excel(name = "bind.status", orderNum = "16")
    private MemberProfileStatus bindStatus;

    @Excel(name = "bind.at", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "17")
    private Date bindAt;

    @Excel(name = "bind.name", width = 20, orderNum = "18")
    private String bindName;

    @Excel(name = "bind.province", orderNum = "19")
    private String bindProvince;

    @Excel(name = "bind.city", orderNum = "20")
    private String bindCity;

    @Excel(name = "bind.county", orderNum = "21")
    private String bindCounty;

    @Excel(name = "bind.address", orderNum = "22")
    private String bindAddress;

    @Excel(name = "mobile", orderNum = "3")
    private String bindMobile;

    @Excel(name = "bind.gender", orderNum = "24")
    private Sex bindGender;

    @Excel(name = "bind.birthday", width = 20, exportFormat = "yyyy-MM-dd", orderNum = "25")
    private Date bindBirthday;

    @Excel(name = "remarks", orderNum = "26")
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

    public String getBindAddress() {
        return bindAddress;
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }

    public String getQrcodeName() {
        return qrcodeName;
    }

    public void setQrcodeName(String qrcodeName) {
        this.qrcodeName = qrcodeName;
    }
}