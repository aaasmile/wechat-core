package com.d1m.wechat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    private String title;
    private String tag;
    private String url;
}