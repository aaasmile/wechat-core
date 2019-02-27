package com.d1m.wechat.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "event_forward")
public class EventForward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "third_party_id")
    private Integer thirdPartyId;

    @Column(name = "interface_id")
    private String interfaceId;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    public EventForward() {}

    public EventForward(Integer thirdPartyId, String interfaceId) {
        this.thirdPartyId = thirdPartyId;
        this.interfaceId = interfaceId;
    }

    public EventForward(Integer thirdPartyId, String interfaceId, String userUuid) {
        this.thirdPartyId = thirdPartyId;
        this.interfaceId = interfaceId;
        this.userUuid = userUuid;
    }

    public EventForward(Integer thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
}
