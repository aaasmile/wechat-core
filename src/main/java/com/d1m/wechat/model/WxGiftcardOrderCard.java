package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "wx_giftcard_order_card")
public class WxGiftcardOrderCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wechat_id")
    private Integer wechatId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "card_id")
    private String cardId;

    private Long price;

    private String code;

    @Column(name = "default_gifting_msg")
    private String defaultGiftingMsg;

    @Column(name = "background_pic_url")
    private String backgroundPicUrl;

    @Column(name = "outer_img_id")
    private String outerImgId;

    @Column(name = "accepter_openid")
    private String accepterOpenid;

    private Boolean valid;

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
     * @return wechat_id
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * @param wechatId
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * @return card_id
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId
     */
    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    /**
     * @return price
     */
    public Long getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return default_gifting_msg
     */
    public String getDefaultGiftingMsg() {
        return defaultGiftingMsg;
    }

    /**
     * @param defaultGiftingMsg
     */
    public void setDefaultGiftingMsg(String defaultGiftingMsg) {
        this.defaultGiftingMsg = defaultGiftingMsg == null ? null : defaultGiftingMsg.trim();
    }

    /**
     * @return background_pic_url
     */
    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    /**
     * @param backgroundPicUrl
     */
    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl == null ? null : backgroundPicUrl.trim();
    }

    /**
     * @return outer_img_id
     */
    public String getOuterImgId() {
        return outerImgId;
    }

    /**
     * @param outerImgId
     */
    public void setOuterImgId(String outerImgId) {
        this.outerImgId = outerImgId == null ? null : outerImgId.trim();
    }

    /**
     * @return accepter_openid
     */
    public String getAccepterOpenid() {
        return accepterOpenid;
    }

    /**
     * @param accepterOpenid
     */
    public void setAccepterOpenid(String accepterOpenid) {
        this.accepterOpenid = accepterOpenid == null ? null : accepterOpenid.trim();
    }

    /**
     * @return valid
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * @param valid
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}