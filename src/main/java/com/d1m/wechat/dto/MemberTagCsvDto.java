package com.d1m.wechat.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberTagCsvDto implements Serializable {
    private static final long serialVersionUID = -2095383359387582049L;
    /**
     * 主键ID
     */
    private Integer fileId;


    private String oriFile;

    /**
     * //处理状态（0-未处理，1-进行中，2-处理完成）
     * 状态(0 导入中，1 已导入，2 导入失败，3 处理中，4 处理成功，5 处理失败)
     */

    private Integer status;

    /**
     * 任务名称
     */
    private String task;

    /**
     * 公众号ID
     */
    private Integer wechatId;

    /**
     * 创建人ID
     */
    private Integer creatorId;

    /**
     * 任务执行时间
     */
    private Timestamp createdAt;

    /**
     * 源文件路径
     */
    private String sourceFilePath;

    /**
     * 异常信息文件路径
     */
    private String exceptionFilePath;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 行数
     */
    private Integer rows;

    /**
     * 文件格式
     */
    private String format;

    /**
     * 文件编码
     */
    private String encoding;

    private Timestamp lastUpdateTime;

    /**
     * 成功条数
     */
    private Integer successCount;

    /**
     * 失败条数
     */
    private Integer failCount;

    /**
     * 失败原因
     */
    private String errorMsg;

    private String remark;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getOriFile() {
        return oriFile;
    }

    public void setOriFile(String oriFile) {
        this.oriFile = oriFile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getExceptionFilePath() {
        return exceptionFilePath;
    }

    public void setExceptionFilePath(String exceptionFilePath) {
        this.exceptionFilePath = exceptionFilePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}