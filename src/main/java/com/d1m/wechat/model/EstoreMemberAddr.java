package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_member_addr")
public class EstoreMemberAddr {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 省（避免复杂，直接用中文）
     */
    private String province;

    /**
     * 市（避免复杂，直接用中文）
     */
    private String city;

    /**
     * 区（避免复杂，直接用中文）
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 是否默认地址（1：默认；0：非默认）
     */
    @Column(name = "is_default")
    private Byte isDefault;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取省（避免复杂，直接用中文）
     *
     * @return province - 省（避免复杂，直接用中文）
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省（避免复杂，直接用中文）
     *
     * @param province 省（避免复杂，直接用中文）
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取市（避免复杂，直接用中文）
     *
     * @return city - 市（避免复杂，直接用中文）
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市（避免复杂，直接用中文）
     *
     * @param city 市（避免复杂，直接用中文）
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取区（避免复杂，直接用中文）
     *
     * @return district - 区（避免复杂，直接用中文）
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置区（避免复杂，直接用中文）
     *
     * @param district 区（避免复杂，直接用中文）
     */
    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取是否默认地址（1：默认；0：非默认）
     *
     * @return is_default - 是否默认地址（1：默认；0：非默认）
     */
    public Byte getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认地址（1：默认；0：非默认）
     *
     * @param isDefault 是否默认地址（1：默认；0：非默认）
     */
    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取微信ID
     *
     * @return wechat_id - 微信ID
     */
    public Long getWechatId() {
        return wechatId;
    }

    /**
     * 设置微信ID
     *
     * @param wechatId 微信ID
     */
    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }
}