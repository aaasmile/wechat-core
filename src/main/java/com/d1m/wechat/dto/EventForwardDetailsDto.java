package com.d1m.wechat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventForwardDetailsDto {

    private Integer id;

    private Integer thirdPartyId;
    private String interfaceId;
    private String userUuid;
    private Integer status;
    private Date createAt;
    private Date updateAt;

    private List<Integer> eventIds;

    private String thirdPartyName;
    private String interfaceName;
}
