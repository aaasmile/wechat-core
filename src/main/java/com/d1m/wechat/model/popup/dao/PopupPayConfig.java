
package com.d1m.wechat.model.popup.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Table(name = "popup_pay_config")
public class PopupPayConfig {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "create_time"
    )
    private Date createTime;
    @Column(
            name = "update_time"
    )
    private Date updateTime;
    @Column(
            name = "icon_url"
    )
    private String iconUrl;
    private String name;
    private String config;
    private String type;
    private Short open;
    @Column(
            name = "wechat_id"
    )
    Integer wechatId;
    @Column(
            name = "company_id"
    )
    Integer companyId;

}
