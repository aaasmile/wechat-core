package com.d1m.wechat.model.popup.dao;

import javax.persistence.*;

@Table(name = "popup_goods_sku")
public class PopupGoodsSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String sku;

    @Column(name = "goods_id")
    private Long goodsId;

    private Integer stock;

    private String title;

    private String color;

    private Byte status;

    /**
     * sku的图片
     */
    @Column(name = "img_urls")
    private String imgUrls;

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
     * @return stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return title
     */
    public String getShade() {
        return title;
    }

    /**
     * @param title
     */
    public void setShade(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    /**
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取sku的图片
     *
     * @return img_urls - sku的图片
     */
    public String getImgUrls() {
        return imgUrls;
    }

    /**
     * 设置sku的图片
     *
     * @param imgUrls sku的图片
     */
    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls == null ? null : imgUrls.trim();
    }
}