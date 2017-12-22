package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_product_sale")
public class EstoreProductSale {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上架状态（1：上架；0：下架）
     */
    @Column(name = "on_sale")
    private Byte onSale;

    /**
     * 是否包邮（1：包邮；0：不包邮，并选择运费模板）
     */
    @Column(name = "delivery_free")
    private Byte deliveryFree;

    /**
     * 不包邮时，需要选择运费模板
     */
    @Column(name = "delivery_tpl_id")
    private Long deliveryTplId;

    /**
     * 产品ID
     */
    @Column(name = "product_id")
    private Long productId;

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
     * 获取上架状态（1：上架；0：下架）
     *
     * @return on_sale - 上架状态（1：上架；0：下架）
     */
    public Byte getOnSale() {
        return onSale;
    }

    /**
     * 设置上架状态（1：上架；0：下架）
     *
     * @param onSale 上架状态（1：上架；0：下架）
     */
    public void setOnSale(Byte onSale) {
        this.onSale = onSale;
    }

    /**
     * 获取是否包邮（1：包邮；0：不包邮，并选择运费模板）
     *
     * @return delivery_free - 是否包邮（1：包邮；0：不包邮，并选择运费模板）
     */
    public Byte getDeliveryFree() {
        return deliveryFree;
    }

    /**
     * 设置是否包邮（1：包邮；0：不包邮，并选择运费模板）
     *
     * @param deliveryFree 是否包邮（1：包邮；0：不包邮，并选择运费模板）
     */
    public void setDeliveryFree(Byte deliveryFree) {
        this.deliveryFree = deliveryFree;
    }

    /**
     * 获取不包邮时，需要选择运费模板
     *
     * @return delivery_tpl_id - 不包邮时，需要选择运费模板
     */
    public Long getDeliveryTplId() {
        return deliveryTplId;
    }

    /**
     * 设置不包邮时，需要选择运费模板
     *
     * @param deliveryTplId 不包邮时，需要选择运费模板
     */
    public void setDeliveryTplId(Long deliveryTplId) {
        this.deliveryTplId = deliveryTplId;
    }

    /**
     * 获取产品ID
     *
     * @return product_id - 产品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
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