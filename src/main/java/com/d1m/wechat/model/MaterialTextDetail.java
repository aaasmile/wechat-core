package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "material_text_detail")
public class MaterialTextDetail {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 素材ID
     */
    @Column(name = "material_id")
    private Integer materialId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 文字内容
     */
    private String content;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取素材ID
     *
     * @return material_id - 素材ID
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * 设置素材ID
     *
     * @param materialId 素材ID
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取文字内容
     *
     * @return content - 文字内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文字内容
     *
     * @param content 文字内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}