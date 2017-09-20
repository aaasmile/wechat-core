package com.d1m.wechat.model.popup.dao;

import javax.persistence.*;

@Table(name = "popup_delivery_addr")
public class PopupDeliveryAddr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    private Integer province;

    private Integer city;

    private Integer area;

    private String addr;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return member_id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return province
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * @return city
     */
    public Integer getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * @return area
     */
    public Integer getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * @return addr
     */
    public String getAddr() {
        return addr;
    }

    /**
     * @param addr
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    /**
     * @return receiver_name
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * @return receiver_phone
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * @param receiverPhone
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }
}