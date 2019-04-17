package com.d1m.wechat.dto;

import com.d1m.wechat.controller.menugroup.MenuGroupController;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClickMenuDto {

    private Integer key;

    private String title;

    private List<MenusDto> children;
}
