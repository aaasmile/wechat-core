package com.d1m.wechat.model.popup.dao;

import javax.persistence.*;

@Table(name = "popup_order_extra_attr")
public class PopupOrderExtraAttr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "gift_content")
    private String giftContent;

    @Column(name = "remark")
    private String remark;

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
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return gift_content
     */
    public String getGiftContent() {
        return giftContent;
    }

    /**
     * @param giftContent
     */
    public void setGiftContent(String giftContent) {
        this.giftContent = giftContent == null ? null : giftContent.trim();
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}