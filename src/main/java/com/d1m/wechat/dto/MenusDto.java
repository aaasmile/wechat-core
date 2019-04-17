package com.d1m.wechat.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenusDto {

    private Integer key;

    private String title;

    private List<MenusDto> children;
}
