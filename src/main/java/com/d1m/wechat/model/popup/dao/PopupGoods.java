package com.d1m.wechat.model.popup.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

@Table(name = "popup_goods")
public class PopupGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 商品图片集，json格式：[{bigImg:'',img:''},]
     */
    @Column(name = "img_urls")
    private JSONObject imgUrls;

    /**
     * 简称
     */
    private String name;

    /**
     * 价格，单位：分
     */
    private Integer price;

    /**
     * 库存数量
     */
    private Integer count;

    /**
     * 限购数量
     */
    @Column(name = "limit_count")
    private Integer limitCount;

    /**
     * 商品描述
     */
    @Column(name = "`desc`")
    private String desc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 商品状态：默认0下架，1在售
     */
    private Byte status;

    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 商品类型：默认1实物
     */
    private Byte type;

    /**
     * 货号，spu概念
     */
    @Column(name = "goods_no")
    private String goodsNo;

    @Column(name = "safety_stock")
    private Integer safetyStock;

    /**
     * 所需积分
     */
    private Integer points;

    private String sku;

    /**
     * 购买权限
     */
    private String permit;

    /**
     * 是否打开子sku
     */
    @Column(name = "shade_open")
    private Byte shadeOpen;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 排序
     */
    @Column(name = "sort_best_sell")
    private Integer sortBestSell;

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
     * @return wechat_id
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * @param wechatId
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取商品图片集，json格式：[{bigImg:'',img:''},]
     *
     * @return img_urls - 商品图片集，json格式：[{bigImg:'',img:''},]
     */
    public JSONObject getImgUrls() {
        return imgUrls;
    }

    /**
     * 设置商品图片集，json格式：[{bigImg:'',img:''},]
     *
     * @param imgUrls 商品图片集，json格式：[{bigImg:'',img:''},]
     */
    public void setImgUrls(JSONObject imgUrls) {
        this.imgUrls = imgUrls == null ? null : imgUrls;
    }

    /**
     * 获取简称
     *
     * @return name - 简称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置简称
     *
     * @param name 简称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取价格，单位：分
     *
     * @return price - 价格，单位：分
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置价格，单位：分
     *
     * @param price 价格，单位：分
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取库存数量
     *
     * @return count - 库存数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置库存数量
     *
     * @param count 库存数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取限购数量
     *
     * @return limit_count - 限购数量
     */
    public Integer getLimitCount() {
        return limitCount;
    }

    /**
     * 设置限购数量
     *
     * @param limitCount 限购数量
     */
    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    /**
     * 获取商品描述
     *
     * @return desc - 商品描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置商品描述
     *
     * @param desc 商品描述
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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

    /**
     * 获取商品状态：默认0新建，1在售，-1下架
     *
     * @return status - 商品状态：默认0新建，1在售，-1下架
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置商品状态：默认0新建，1在售，-1下架
     *
     * @param status 商品状态：默认0新建，1在售，-1下架
     */
    public void setStatus(Byte status) {
        this.status = status == null ? 0 : status;
    }

    /**
     * @return category_id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取商品类型：默认1实物
     *
     * @return type - 商品类型：默认1实物
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置商品类型：默认1实物
     *
     * @param type 商品类型：默认1实物
     */
    public void setType(Byte type) {
        this.type = type == null ? 0 : type;
    }

    /**
     * 获取货号，spu概念
     *
     * @return goods_no - 货号，spu概念
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置货号，spu概念
     *
     * @param goodsNo 货号，spu概念
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo == null ? null : goodsNo.trim();
    }

    /**
     * @return safety_stock
     */
    public Integer getSafetyStock() {
        return safetyStock;
    }

    /**
     * @param safetyStock
     */
    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    /**
     * 获取所需积分
     *
     * @return points - 所需积分
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 设置所需积分
     *
     * @param points 所需积分
     */
    public void setPoints(Integer points) {
        this.points = points;
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
     * 获取购买权限
     *
     * @return permit - 购买权限
     */
    public String getPermit() {
        return permit;
    }

    /**
     * 设置购买权限
     *
     * @param permit 购买权限
     */
    public void setPermit(String permit) {
        this.permit = permit;
    }

    /**
     * 获取是否打开子sku
     *
     * @return shade_open - 是否打开子sku
     */
    public Byte getShadeOpen() {
        return shadeOpen;
    }

    /**
     * 设置是否打开子sku
     *
     * @param shadeOpen 是否打开子sku
     */
    public void setShadeOpen(Byte shadeOpen) {
        this.shadeOpen = shadeOpen;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取排序
     *
     * @return sortBestSell - 排序
     */
    public Integer getSortBestSell() {
        return sortBestSell;
    }

    /**
     * 设置排序
     *
     * @param sortBestSell 排序
     */
    public void setSortBestSell(Integer sortBestSell) {
        this.sortBestSell = sortBestSell;
    }


}