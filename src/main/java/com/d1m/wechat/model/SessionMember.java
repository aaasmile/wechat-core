package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "session_member")
public class SessionMember {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 报名门店ID
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 线下活动ID
     */
    @Column(name = "offline_activity_id")
    private Integer offlineActivityId;

    /**
     * 报名场次ID
     */
    @Column(name = "session_id")
    private Integer sessionId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机电话
     */
    private String phone;

    /**
     * 报名者OpenID
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 报名时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取报名门店ID
     *
     * @return business_id - 报名门店ID
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置报名门店ID
     *
     * @param businessId 报名门店ID
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取线下活动ID
     *
     * @return offline_activity_id - 线下活动ID
     */
    public Integer getOfflineActivityId() {
        return offlineActivityId;
    }

    /**
     * 设置线下活动ID
     *
     * @param offlineActivityId 线下活动ID
     */
    public void setOfflineActivityId(Integer offlineActivityId) {
        this.offlineActivityId = offlineActivityId;
    }

    /**
     * 获取报名场次ID
     *
     * @return session_id - 报名场次ID
     */
    public Integer getSessionId() {
        return sessionId;
    }

    /**
     * 设置报名场次ID
     *
     * @param sessionId 报名场次ID
     */
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取手机电话
     *
     * @return phone - 手机电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机电话
     *
     * @param phone 手机电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取报名者OpenID
     *
     * @return open_id - 报名者OpenID
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置报名者OpenID
     *
     * @param openId 报名者OpenID
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取报名时间
     *
     * @return created_at - 报名时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置报名时间
     *
     * @param createdAt 报名时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public Integer getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }
}