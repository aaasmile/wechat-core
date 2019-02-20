package com.d1m.wechat.pamametermodel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddEventForwardModel {

    private Integer thirdPartyId;

    private Integer interfaceId;

    private String userUuid;

    private List<Integer> eventIds;
}
