package com.d1m.wechat.model.enums;

public enum State {

  STEP_1("任务失败", -1),
  STEP0("上传文件中", 0),
  STEP1("上传文件完成", 1),
  STEP2("任务准备中", 2),
  STEP3("任务准备完成", 3),
  STEP4("文件读取中", 4),
  STEP5("文件读取完成", 5),
  STEP6("数据处理中", 6),
  STEP7("数据处理完成", 7),
  STEP8("文件写入中", 8),
  STEP9("文件写入成功", 9);

  private String name;
  private Integer value;

  private State(String name, Integer value) {
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
