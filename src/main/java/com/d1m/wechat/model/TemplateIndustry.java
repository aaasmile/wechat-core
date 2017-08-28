package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "template_industry")
public class TemplateIndustry {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 主营行业一级行业
     */
    @Column(name = "primary_first")
    private String primaryFirst;

    /**
     * 主营行业二级行业
     */
    @Column(name = "primary_second")
    private String primarySecond;

    /**
     * 副营行业一级行业
     */
    @Column(name = "secondary_first")
    private String secondaryFirst;

    /**
     * 副营行业二级行业
     */
    @Column(name = "secondary_second")
    private String secondarySecond;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

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
     * 获取主营行业一级行业
     *
     * @return primary_first - 主营行业一级行业
     */
    public String getPrimaryFirst() {
        return primaryFirst;
    }

    /**
     * 设置主营行业一级行业
     *
     * @param primaryFirst 主营行业一级行业
     */
    public void setPrimaryFirst(String primaryFirst) {
        this.primaryFirst = primaryFirst == null ? null : primaryFirst.trim();
    }

    /**
     * 获取主营行业二级行业
     *
     * @return primary_second - 主营行业二级行业
     */
    public String getPrimarySecond() {
        return primarySecond;
    }

    /**
     * 设置主营行业二级行业
     *
     * @param primarySecond 主营行业二级行业
     */
    public void setPrimarySecond(String primarySecond) {
        this.primarySecond = primarySecond == null ? null : primarySecond.trim();
    }

    /**
     * 获取副营行业一级行业
     *
     * @return secondary_first - 副营行业一级行业
     */
    public String getSecondaryFirst() {
        return secondaryFirst;
    }

    /**
     * 设置副营行业一级行业
     *
     * @param secondaryFirst 副营行业一级行业
     */
    public void setSecondaryFirst(String secondaryFirst) {
        this.secondaryFirst = secondaryFirst == null ? null : secondaryFirst.trim();
    }

    /**
     * 获取副营行业二级行业
     *
     * @return secondary_second - 副营行业二级行业
     */
    public String getSecondarySecond() {
        return secondarySecond;
    }

    /**
     * 设置副营行业二级行业
     *
     * @param secondarySecond 副营行业二级行业
     */
    public void setSecondarySecond(String secondarySecond) {
        this.secondarySecond = secondarySecond == null ? null : secondarySecond.trim();
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
}