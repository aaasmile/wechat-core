package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "estore_product")
public class EstoreProduct {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 预留：格式化的扩展属性，JSON格式
     */
    @Column(name = "ext_attr")
    private JSONObject extAttr;

    /**
     * 规格类型（0：统一规格；1：多规格）
     */
    @Column(name = "spec_type")
    private Byte specType;

    /**
     * 规格元数据（多规格使用，JSON格式）
     */
    @Column(name = "spec_meta")
    private JSONObject specMeta;

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

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 商品描述
     */
    private String description;

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
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取商品编码
     *
     * @return code - 商品编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置商品编码
     *
     * @param code 商品编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取预留：格式化的扩展属性，JSON格式
     *
     * @return ext_attr - 预留：格式化的扩展属性，JSON格式
     */
    public JSONObject getExtAttr() {
        return extAttr;
    }

    /**
     * 设置预留：格式化的扩展属性，JSON格式
     *
     * @param extAttr 预留：格式化的扩展属性，JSON格式
     */
    public void setExtAttr(JSONObject extAttr) {
        this.extAttr = extAttr == null ? null : extAttr;
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
     * 获取规格元数据（多规格使用，JSON格式）
     *
     * @return spec_meta - 规格元数据（多规格使用，JSON格式）
     */
    public JSONObject getSpecMeta() {
        return specMeta;
    }

    /**
     * 设置规格元数据（多规格使用，JSON格式）
     *
     * @param specMeta 规格元数据（多规格使用，JSON格式）
     */
    public void setSpecMeta(JSONObject specMeta) {
        this.specMeta = specMeta == null ? null : specMeta;
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

    /**
     * 获取商品描述
     *
     * @return description - 商品描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置商品描述
     *
     * @param description 商品描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}