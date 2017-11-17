package com.d1m.wechat.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

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
     * 获取货号、商品编号
     *
     * @return sku - 货号、商品编号
     */
    public String getSku() {
        return sku;
    }

    /**
     * 设置货号、商品编号
     *
     * @param sku 货号、商品编号
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * 获取规格类型（0：统一规格；1：多规格）
     *
     * @return spec_type - 规格类型（0：统一规格；1：多规格）
     */
    public Byte getSpecType() {
        return specType;
    }

    /**
     * 设置规格类型（0：统一规格；1：多规格）
     *
     * @param specType 规格类型（0：统一规格；1：多规格）
     */
    public void setSpecType(Byte specType) {
        this.specType = specType;
    }

    /**
     * 获取规格值（JSON格式）
     *
     * @return spec_value - 规格值（JSON格式）
     */
    public JSONObject getSpecValue() {
        return specValue;
    }

    /**
     * 设置规格值（JSON格式）
     *
     * @param specValue 规格值（JSON格式）
     */
    public void setSpecValue(JSONObject specValue) {
        this.specValue = specValue == null ? null : specValue;
    }

    /**
     * 获取市场价，单位：元
     *
     * @return market_price - 市场价，单位：元
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价，单位：元
     *
     * @param marketPrice 市场价，单位：元
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取实际销售价格，单位：元
     *
     * @return price - 实际销售价格，单位：元
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置实际销售价格，单位：元
     *
     * @param price 实际销售价格，单位：元
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取积分数
     *
     * @return point - 积分数
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * 设置积分数
     *
     * @param point 积分数
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * 获取库存数
     *
     * @return stock - 库存数
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存数
     *
     * @param stock 库存数
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取重量
     *
     * @return weight - 重量
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * 获取体积
     *
     * @return volume - 体积
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * 设置体积
     *
     * @param volume 体积
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取修改时间
     *
     * @return modify_at - 修改时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置修改时间
     *
     * @param modifyAt 修改时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * 获取状态（1：正常；0：删除）
     *
     * @return status - 状态（1：正常；0：删除）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态（1：正常；0：删除）
     *
     * @param status 状态（1：正常；0：删除）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * @return product_id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
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