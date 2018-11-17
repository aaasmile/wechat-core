package com.d1m.wechat.dto;

import com.d1m.wechat.domain.entity.MemberTagData;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: wechat-core
 * @Date: 2018/10/24 11:13
 * @Author: Liu weilin
 * @Description:
 */
public class TagDataBatchDto implements Serializable {
    private static final long serialVersionUID = -321442643366021518L;
    //总数量
    private Integer amount;
    private List<MemberTagData> tagsList;
    //租户标识
    private String tenant;
    //批次
    private Integer times;
    //剩余总数量
    private Integer remainAmount;
    //每批次执行数量
    private Integer batchSize;

    //执行方法
    private String method;
    //调度任务名称
    String taskName;

    Date runTask;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<MemberTagData> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<MemberTagData> tagsList) {
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getRunTask() {
        return runTask;
    }

    public void setRunTask(Date runTask) {
        this.runTask = runTask;
    }
}
