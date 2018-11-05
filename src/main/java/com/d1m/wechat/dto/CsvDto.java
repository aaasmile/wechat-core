package com.d1m.wechat.dto;

import java.util.List;

/**
 * @program: wechat-core
 * @Date: 2018/10/30 4:39
 * @Author: Liu weilin
 * @Description:
 */
public class CsvDto {
    private String typeName;
    private String tag;
    private String openId;
    private List<?> list;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
