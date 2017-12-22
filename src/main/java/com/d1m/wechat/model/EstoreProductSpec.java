package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Getter
@Setter
@Table(name = "estore_product_spec")
public class EstoreProductSpec {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 货号、商品编号
     */
    private String sku;

    /**
     * 规格类型（0：统一规格；1：多规格）
     */
    @Column(name = "spec_type")
    private Byte specType;

    /**
     * 规格值（JSON格式）
     */
    @Column(name = "spec_value")
    private JSONObject specValue;

    /**
     * 市场价，单位：元
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 实际销售价格，单位：元
     */
    private BigDecimal price;

    /**
     * 积分数
     */
    private Integer point;

    /**
     * 库存数
     */
    private Integer stock;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 体积
     */
    private Double volume;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 状态（1：正常；0：删除）
     */
    private Byte status;

    @Column(name = "product_id")
    private Long productId;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;


}