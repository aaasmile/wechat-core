package com.d1m.wechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "wx_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxEvent {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "code")
    private String code;
}