package com.d1m.wechat.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * @program: wechat-core
 * @Date: 2018/12/11 11:12
 * @Author: Liu weilin
 * @Description:
 */
@Data
public class QueryDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //搜索关键词
    private String search;

    private String name;

    private String materialCategoryId;

    //微信图文Id
    private String wxImageTextId;

    //每页记录数
    private int pageSize = 10;

    //当前页数
    private int pageNum = 1;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(String materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
