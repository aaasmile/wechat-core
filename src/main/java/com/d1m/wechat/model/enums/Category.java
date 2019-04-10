package com.d1m.wechat.model.enums;

public enum Category {

  TAG("打标签", 0),
  MEMBER_EXPORT("用户导出", 1);

  private String name;
  private Integer value;

  private Category(String name, Integer value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Integer getValue() {
    return value;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}
