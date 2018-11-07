package com.d1m.wechat.dto;

/**
 * @program: wechat-core
 * @Date: 2018/11/6 16:18
 * @Author: Liu weilin
 * @Description:
 */
public class MemberStatsCounts {

    private Integer fileId;
    private Integer failCount;
    private Integer successCount;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
}
