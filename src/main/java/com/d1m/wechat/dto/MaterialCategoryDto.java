package com.d1m.wechat.dto;

import com.d1m.wechat.util.PageUtils;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: wechat-core
 * @Date: 2018/12/8 16:49
 * @Author: Liu weilin
 * @Description:
 */
public class MaterialCategoryDto  extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 素材名称
     */
    private String name;


    /**
     * 公众号ID
     */
    private Integer wechatId;

    /**
      * 创建人
     */
    private Integer createdBy;

    /**
     * 最后更新人
     */
    private Integer lasteUpdatedBy;

    /**
     * 图文数量
     */
    private Integer newsCount;


    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLasteUpdatedBy() {
        return lasteUpdatedBy;
    }

    public void setLasteUpdatedBy(Integer lasteUpdatedBy) {
        this.lasteUpdatedBy = lasteUpdatedBy;
    }
}
