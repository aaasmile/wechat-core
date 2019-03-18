package com.d1m.wechat.model;

import com.d1m.wechat.model.enums.InterfaceType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "interface_secret")
public class InterfaceSecret {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 第三方名称(interface_brand)表id
     */
    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 接口类型； 0 DCRM主动推送(专用接口)，1 第三方拉取  2 DCRM主动推送(事件转发接口)
     */
    private InterfaceType type;

    /**
     * KEY  uuid生成32位
     */
    @Column(name = "app_key")
    private String key;

    /**
     * secret 生成16位
     */
    @Column(name = "app_secret")
    private String secret;

    /**
     * 创建时间
     */
    @Column(name ="create_at")
    private Date createAt;

}
