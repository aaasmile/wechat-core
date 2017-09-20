package com.d1m.wechat.model.popup.dao;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

@Table(name = "popup_goods_sku")
public class PopupGoodsSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    private String sku;

    private Integer stock;

    private String shade;

    private String color;

    /**
     * 商品状态：1在售，0下架
     */
    private Byte status;

    /**
     * sku的图片
     */
    @Column(name = "img_urls")
    private JSONObject imgUrls;

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
     * @return shade
     */
    public String getShade() {
        return shade;
    }

    /**
     * @param shade
     */
    public void setShade(String shade) {
        this.shade = shade == null ? null : shade.trim();
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
     * 获取商品状态：1在售，0下架
     *
     * @return status - 商品状态：1在售，0下架
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置商品状态：1在售，0下架
     *
     * @param status 商品状态：1在售，0下架
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取sku的图片
     *
     * @return img_urls - sku的图片
     */
    public JSONObject getImgUrls() {
        return imgUrls;
    }

    /**
     * 设置sku的图片
     *
     * @param imgUrls sku的图片
     */
    public void setImgUrls(JSONObject imgUrls) {
        this.imgUrls = imgUrls == null ? null : imgUrls;
    }
}