package com.d1m.wechat.domain.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.d1m.wechat.model.enums.MemberTagDataStatus;
import org.apache.commons.lang.StringUtils;

@Table(name = "`member_tag_data`")
public class MemberTagData {
    @Id
    @Column(name = "`data_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    /**
     * 上传文件编号
     */
    @Column(name = "`file_id`")
    private Integer fileId;

    @Column(name = "`open_id`")
    private String openId;

    @Column(name = "`wechat_id`")
    private Integer wechatId;

    @Column(name = "`tag`")
    private String tag;

    /**
     * 问题标签
     */
    @Column(name = "`error_tag`")
    private String errorTag;

    /**
     * 原始标签
     */
    @Column(name = "`original_tag`")
    private String originalTag;

    /**
     * 状态（0 未处理，4 处理中，5 处理成功，6 处理失败）
     */
    @Column(name = "`status`")
    private MemberTagDataStatus status;

    /**
     * 数据检查状态
     * (1 正常，0 有问题)
     */
    @Column(name = "`check_status`")
    private Boolean checkStatus;

    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`error_msg`")
    private String errorMsg;

    /**
     * 乐观锁版本号
     */
    @Column(name = "`version`")
    private Integer version;

    @Column(name = "`created_at`")
    private Timestamp createdAt;

    @Column(name = "`finish_time`")
    private Timestamp finishTime;

    public MemberTagData(Integer dataId, Integer fileId, String openId, Integer wechatId, String tag, String errorTag,
                         String originalTag, MemberTagDataStatus status, Boolean checkStatus, String remark, String errorMsg,
                         Integer version, Timestamp createdAt, Timestamp finishTime) {
        super();
        this.dataId = dataId;
        this.fileId = fileId;
        this.openId = openId;
        this.wechatId = wechatId;
        this.tag = tag;
        this.errorTag = errorTag;
        this.originalTag = originalTag;
        this.status = status;
        this.checkStatus = checkStatus;
        this.remark = remark;
        this.errorMsg = errorMsg;
        this.version = version;
        this.createdAt = createdAt;
        this.finishTime = finishTime;
    }

    public MemberTagData() {
        super();
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getErrorTag() {
        return errorTag;
    }

    public void setErrorTag(String errorTag) {
        this.errorTag = errorTag;
    }

    public String getOriginalTag() {
        return originalTag;
    }

    public void setOriginalTag(String originalTag) {
        this.originalTag = originalTag;
    }

    public MemberTagDataStatus getStatus() {
        return status;
    }

    public void setStatus(MemberTagDataStatus status) {
        this.status = status;
    }

    public Boolean getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public static class Builder {
        private Integer dataId;
        private Integer fileId;
        private String openId;
        private Integer wechatId;
        private String tag;
        private String errorTag;
        private String originalTag;
        private MemberTagDataStatus status;
        private Boolean checkStatus;
        private String remark;
        private String errorMsg;
        private Integer version;
        private Timestamp createdAt;
        private Timestamp finishTime;

        public Builder dataId(Integer dataId) {
            this.dataId = dataId;
            return this;
        }

        public Builder fileId(Integer fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder wechatId(Integer wechatId) {
            this.wechatId = wechatId;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder errorTag(String errorTag) {
            this.errorTag = errorTag;
            return this;
        }

        public Builder originalTag(String originalTag) {
            this.originalTag = originalTag;
            return this;
        }

        public Builder status(MemberTagDataStatus status) {
            this.status = status;
            return this;
        }

        public Builder checkStatus(Boolean checkStatus) {
            this.checkStatus = checkStatus;
            return this;
        }

        public Builder remark(String remark) {
            this.remark = remark;
            return this;
        }

        public Builder errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder finishTime(Timestamp finishTime) {
            this.finishTime = finishTime;
            return this;
        }

        public MemberTagData build() {
            return new MemberTagData(this);
        }
    }

    private MemberTagData(Builder builder) {
        this.dataId = builder.dataId;
        this.fileId = builder.fileId;
        this.openId = builder.openId;
        this.wechatId = builder.wechatId;
        this.tag = builder.tag;
        this.errorTag = builder.errorTag;
        this.originalTag = builder.originalTag;
        this.status = builder.status;
        this.checkStatus = builder.checkStatus;
        this.remark = builder.remark;
        this.errorMsg = builder.errorMsg;
        this.version = builder.version;
        this.createdAt = builder.createdAt;
        this.finishTime = builder.finishTime;
    }

    @Override
    public String toString() {
        return "MemberTagData [dataId=" + dataId + ", fileId=" + fileId + ", openId=" + openId + ", wechatId="
         + wechatId + ", tag=" + tag + ", errorTag=" + errorTag + ", originalTag=" + originalTag + ", status="
         + status + ", checkStatus=" + checkStatus + ", remark=" + remark + ", errorMsg=" + errorMsg
         + ", version=" + version + ", createdAt=" + createdAt + ", finishTime=" + finishTime + "]";
    }

    public boolean checkNull() {
        if (StringUtils.isEmpty(this.getOpenId())) {
            return false;
        }
        if (StringUtils.isEmpty(this.getOriginalTag())) {
            return false;
        }

        if (this.getDataId() == null) {
            return false;
        }

        if (this.getFileId() == null) {
            return false;
        }

        return true;
    }

}