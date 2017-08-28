package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_tag_csv")
public class MemberTagCsv {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 原始文件名
     */
    @Column(name = "ori_file")
    private String oriFile;

    /**
     * 导入加会员标签文件
     */
    private String csv;

    /**
     * 异常信息文件
     */
    private String exception;

    /**
     * 处理状态（0-未处理，1-进行中，2-处理完成）
     */
    private Byte status;

    /**
     * 任务名称
     */
    private String task;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 任务执行时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 数据
     */
    private String data;

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
     * 获取原始文件名
     *
     * @return ori_file - 原始文件名
     */
    public String getOriFile() {
        return oriFile;
    }

    /**
     * 设置原始文件名
     *
     * @param oriFile 原始文件名
     */
    public void setOriFile(String oriFile) {
        this.oriFile = oriFile == null ? null : oriFile.trim();
    }

    /**
     * 获取导入加会员标签文件
     *
     * @return csv - 导入加会员标签文件
     */
    public String getCsv() {
        return csv;
    }

    /**
     * 设置导入加会员标签文件
     *
     * @param csv 导入加会员标签文件
     */
    public void setCsv(String csv) {
        this.csv = csv == null ? null : csv.trim();
    }

    /**
     * 获取异常信息文件
     *
     * @return exception - 异常信息文件
     */
    public String getException() {
        return exception;
    }

    /**
     * 设置异常信息文件
     *
     * @param exception 异常信息文件
     */
    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }

    /**
     * 获取处理状态（0-未处理，1-进行中，2-处理完成）
     *
     * @return status - 处理状态（0-未处理，1-进行中，2-处理完成）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置处理状态（0-未处理，1-进行中，2-处理完成）
     *
     * @param status 处理状态（0-未处理，1-进行中，2-处理完成）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取任务名称
     *
     * @return task - 任务名称
     */
    public String getTask() {
        return task;
    }

    /**
     * 设置任务名称
     *
     * @param task 任务名称
     */
    public void setTask(String task) {
        this.task = task == null ? null : task.trim();
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
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取任务执行时间
     *
     * @return created_at - 任务执行时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置任务执行时间
     *
     * @param createdAt 任务执行时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取数据
     *
     * @return data - 数据
     */
    public String getData() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}
