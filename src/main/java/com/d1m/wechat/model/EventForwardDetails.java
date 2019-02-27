package com.d1m.wechat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "event_forward_details")
public class EventForwardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_forward_id")
    private Integer eventForwardId;

    @Column(name = "event_id")
    private Integer eventId;

    public EventForwardDetails() {}

    public EventForwardDetails(Integer eventForwardId, Integer eventId) {
        this.eventForwardId = eventForwardId;
        this.eventId = eventId;
    }

    public EventForwardDetails(Integer eventForwardId) {
        this.eventForwardId = eventForwardId;
    }
}
