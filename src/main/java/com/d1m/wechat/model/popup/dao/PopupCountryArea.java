package com.d1m.wechat.model.popup.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Table(name = "popup_country_area")
public class PopupCountryArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 政府区域编码
     */
    private String code;

    @Column(name = "name_zh")
    private String nameZh;

    @Column(name = "name_en")
    private String nameEn;

    /**
     * 父级id
     */
    private Integer pid;

    @Column(name = "update_time")
    private Date updateTime;

}