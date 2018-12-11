package com.d1m.wechat.dto;

import com.d1m.wechat.util.PageUtils;

import java.io.Serializable;

/**
 * @program: wechat-core
 * @Date: 2018/12/11 11:12
 * @Author: Liu weilin
 * @Description:
 */
public class QueryDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //搜索关键词
    private String search;

    private String name;

    private String materialTypeId;

    //每页记录数
    private int pageSize;

    //当前页数
    private int currPage;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(String materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
