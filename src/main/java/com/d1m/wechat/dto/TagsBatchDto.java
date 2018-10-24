package com.d1m.wechat.dto;

import com.d1m.wechat.model.MemberMemberTag;

import java.io.Serializable;
import java.util.List;

/**
 * @program: wechat-core
 * @Date: 2018/10/24 11:13
 * @Author: Liu weilin
 * @Description:
 */
public class TagsBatchDto implements Serializable {
    private static final long serialVersionUID = -321442643366021518L;
    //总数量
    private Integer amount;
    private List<MemberMemberTag> tagsList;
    //租户标识
    private String tenant;
    //批次
    private Integer times;
    //剩余总数量
    private Integer remainAmount;
    //每批次执行数量
    private Integer batchSize;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<MemberMemberTag> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<MemberMemberTag> tagsList) {
        this.tagsList = tagsList;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Integer remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }
}
