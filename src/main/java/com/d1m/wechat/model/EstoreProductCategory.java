package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_product_category")
public class EstoreProductCategory {

    /**
     * 产品销售ID
     */
    @Column(name = "product_sale_id")
    private Long productSaleId;

    /**
     * 分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

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
     * 获取分类ID
     *
     * @return category_id - 分类ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类ID
     *
     * @param categoryId 分类ID
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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