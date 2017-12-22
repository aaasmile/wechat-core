package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_product_tag")
public class EstoreProductTag {

    /**
     * 产品销售ID
     */
    @Column(name = "product_sale_id")
    private Long productSaleId;

    /**
     * 标签ID
     */
    @Column(name = "tag_id")
    private Long tagId;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 获取产品销售ID
     *
     * @return product_sale_id - 产品销售ID
     */
    public Long getProductSaleId() {
        return productSaleId;
    }

    /**
     * 设置产品销售ID
     *
     * @param productSaleId 产品销售ID
     */
    public void setProductSaleId(Long productSaleId) {
        this.productSaleId = productSaleId;
    }

    /**
     * 获取标签ID
     *
     * @return tag_id - 标签ID
     */
    public Long getTagId() {
        return tagId;
    }

    /**
     * 设置标签ID
     *
     * @param tagId 标签ID
     */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
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