package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_product_image")
public class EstoreProductImage {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片类型（0：产品图片；1：规格图片）
     */
    private Byte type;

    /**
     * 顺序，由小到大
     */
    private Integer seq;

    /**
     * 产品ID
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品规格ID，和产品ID二选一
     */
    @Column(name = "product_spec_id")
    private Long productSpecId;

    @Column(name = "material_id")
    private Long materialId;

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
     * 获取图片类型（0：产品图片；1：规格图片）
     *
     * @return type - 图片类型（0：产品图片；1：规格图片）
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置图片类型（0：产品图片；1：规格图片）
     *
     * @param type 图片类型（0：产品图片；1：规格图片）
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取顺序，由小到大
     *
     * @return seq - 顺序，由小到大
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置顺序，由小到大
     *
     * @param seq 顺序，由小到大
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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
     * 获取产品规格ID，和产品ID二选一
     *
     * @return product_spec_id - 产品规格ID，和产品ID二选一
     */
    public Long getProductSpecId() {
        return productSpecId;
    }

    /**
     * 设置产品规格ID，和产品ID二选一
     *
     * @param productSpecId 产品规格ID，和产品ID二选一
     */
    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    /**
     * @return material_id
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
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