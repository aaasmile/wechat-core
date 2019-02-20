package com.d1m.wechat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "event_forward")
public class EventForward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "third_party_id")
    private Integer thirdPartyId;

    @Column(name = "interface_id")
    private Integer interfaceId;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    public EventForward() {}

    public EventForward(Integer thirdPartyId, Integer interfaceId, String userUuid) {
        this.thirdPartyId = thirdPartyId;
        this.interfaceId = interfaceId;
        this.userUuid = userUuid;
    }
}
