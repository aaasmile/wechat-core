package com.d1m.wechat.model;

public class Exceptions {

  private String id;
  private String schedulerId;
  private String exception;
  private String createdTime;

  public String getId() {
    return id;
  }

  public String getSchedulerId() {
    return schedulerId;
  }

  public String getException() {
    return exception;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setSchedulerId(String schedulerId) {
    this.schedulerId = schedulerId;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  @Override
  public String toString() {
    return "Exceptions{" +
        "id='" + id + '\'' +
        ", schedulerId='" + schedulerId + '\'' +
        ", exception='" + exception + '\'' +
        ", createdTime='" + createdTime + '\'' +
        '}';
  }
}
