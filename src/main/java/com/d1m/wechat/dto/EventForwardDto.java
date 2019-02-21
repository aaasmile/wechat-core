package com.d1m.wechat.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EventForwardDto {
    //事件转发id
    private Integer id;
    //第三方
    private  String name;
    //接口名称
    private String  brand_name;
    //事件转发接口类型
    private int type;
    //转发接口描述
    private String description;
    //转发事件状态
    private int status;
   //更新时间
    private String  updateAt;

}
