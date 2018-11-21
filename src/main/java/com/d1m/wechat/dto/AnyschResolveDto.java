package com.d1m.wechat.dto;

import com.d1m.wechat.domain.entity.MemberTagCsv;

import java.io.File;
import java.util.Date;

/**
 * @program: wechat-core
 * @Date: 2018/11/21 11:08
 * @Author: Liu weilin
 * @Description:
 */
public class AnyschResolveDto {

    String originalFilename;
    MemberTagCsv memberTagCsv;
    File targetFile;
    Date runTask;
    String tenant;

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public MemberTagCsv getMemberTagCsv() {
        return memberTagCsv;
    }

    public void setMemberTagCsv(MemberTagCsv memberTagCsv) {
        this.memberTagCsv = memberTagCsv;
    }

    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public Date getRunTask() {
        return runTask;
    }

    public void setRunTask(Date runTask) {
        this.runTask = runTask;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
