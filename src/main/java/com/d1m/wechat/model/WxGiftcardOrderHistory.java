package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wx_giftcard_order_history")
public class WxGiftcardOrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wechat_id")
    private Integer wechatId;

    private String event;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "page_id")
    private String pageId;

    @Column(name = "trans_id")
    private String transId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "pay_finish_time")
    private Date payFinishTime;

    /**
     * 全部金额，以分为单位
     */
    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "accepter_openid")
    private String accepterOpenid;

    @Column(name = "is_chat_room")
    private Boolean isChatRoom;

    @Column(name = "outer_str")
    private String outerStr;

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
     * @return event
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event
     */
    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return member_id
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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
     * @return page_id
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * @param pageId
     */
    public void setPageId(String pageId) {
        this.pageId = pageId == null ? null : pageId.trim();
    }

    /**
     * @return trans_id
     */
    public String getTransId() {
        return transId;
    }

    /**
     * @param transId
     */
    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return pay_finish_time
     */
    public Date getPayFinishTime() {
        return payFinishTime;
    }

    /**
     * @param payFinishTime
     */
    public void setPayFinishTime(Date payFinishTime) {
        this.payFinishTime = payFinishTime;
    }

    /**
     * 获取全部金额，以分为单位
     *
     * @return total_price - 全部金额，以分为单位
     */
    public Long getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置全部金额，以分为单位
     *
     * @param totalPrice 全部金额，以分为单位
     */
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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
     * @return is_chat_room
     */
    public Boolean getIsChatRoom() {
        return isChatRoom;
    }

    /**
     * @param isChatRoom
     */
    public void setIsChatRoom(Boolean isChatRoom) {
        this.isChatRoom = isChatRoom;
    }

    /**
     * @return outer_str
     */
    public String getOuterStr() {
        return outerStr;
    }

    /**
     * @param outerStr
     */
    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr == null ? null : outerStr.trim();
    }
}