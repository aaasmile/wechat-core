package com.d1m.wechat.pamametermodel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditEventForwardModel {

    private Integer id;

    private Integer status;

    private String userUuid;

    private List<Integer> eventIds;
}
