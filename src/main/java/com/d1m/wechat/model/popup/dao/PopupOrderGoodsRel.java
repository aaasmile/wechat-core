package com.d1m.wechat.model.popup.dao;

import java.util.Date;
import javax.persistence.*;

@Table(name = "popup_order_goods_rel")
public class PopupOrderGoodsRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "goods_id")
    private Long goodsId;

    private String sku;

    private Integer quantity;

    /**
     * 单位：分
     */
    private Integer price;

    private Long points;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime = new Date();

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
     * @return goods_id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * @return quantity
     */
    public Integer getSum() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setSum(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取单位：分
     *
     * @return price - 单位：分
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置单位：分
     *
     * @param price 单位：分
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getPoints() {
        return points;
    }


    public void setPoints(Long points) {
        this.points = points;
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}