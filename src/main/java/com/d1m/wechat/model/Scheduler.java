package com.d1m.wechat.model;

public class Scheduler {

  private String id;
  private String wechatCode;
  private String name;
  private Integer category;
  private Integer state;
  private String describetion;
  private String uploadFile;
  private String rightFile;
  private String wrongFile;
  private String rightTable;
  private String wrongTable;
  private Integer line;
  private Integer successLine;
  private Integer failLine;
  private String createdTime;
  private String createdBy;
  private String updatedTime;

  public String getId() {
    return id;
  }

  public String getWechatCode() {
    return wechatCode;
  }

  public String getName() {
    return name;
  }

  public Integer getCategory() {
    return category;
  }

  public Integer getState() {
    return state;
  }

  public String getDescribetion() {
    return describetion;
  }

  public String getUploadFile() {
    return uploadFile;
  }

  public String getRightFile() {
    return rightFile;
  }

  public String getWrongFile() {
    return wrongFile;
  }

  public String getRightTable() {
    return rightTable;
  }

  public String getWrongTable() {
    return wrongTable;
  }

  public Integer getLine() {
    return line;
  }

  public Integer getSuccessLine() {
    return successLine;
  }

  public Integer getFailLine() {
    return failLine;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public String getUpdatedTime() {
    return updatedTime;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setWechatCode(String wechatCode) {
    this.wechatCode = wechatCode;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public void setDescribetion(String describetion) {
    this.describetion = describetion;
  }

  public void setUploadFile(String uploadFile) {
    this.uploadFile = uploadFile;
  }

  public void setRightFile(String rightFile) {
    this.rightFile = rightFile;
  }

  public void setWrongFile(String wrongFile) {
    this.wrongFile = wrongFile;
  }

  public void setRightTable(String rightTable) {
    this.rightTable = rightTable;
  }

  public void setWrongTable(String wrongTable) {
    this.wrongTable = wrongTable;
  }

  public void setLine(Integer line) {
    this.line = line;
  }

  public void setSuccessLine(Integer successLine) {
    this.successLine = successLine;
  }

  public void setFailLine(Integer failLine) {
    this.failLine = failLine;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public void setUpdatedTime(String updatedTime) {
    this.updatedTime = updatedTime;
  }

  @Override
  public String toString() {
    return "Scheduler{" +
        "id='" + id + '\'' +
        ", wechatCode='" + wechatCode + '\'' +
        ", name='" + name + '\'' +
        ", category=" + category +
        ", state=" + state +
        ", describetion='" + describetion + '\'' +
        ", uploadFile='" + uploadFile + '\'' +
        ", rightFile='" + rightFile + '\'' +
        ", wrongFile='" + wrongFile + '\'' +
        ", rightTable='" + rightTable + '\'' +
        ", wrongTable='" + wrongTable + '\'' +
        ", line=" + line +
        ", successLine=" + successLine +
        ", failLine=" + failLine +
        ", createdTime='" + createdTime + '\'' +
        ", createdBy='" + createdBy + '\'' +
        ", updatedTime='" + updatedTime + '\'' +
        '}';
  }
}
