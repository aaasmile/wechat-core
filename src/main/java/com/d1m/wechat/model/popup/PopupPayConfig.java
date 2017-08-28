
package com.d1m.wechat.model.popup;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "popup_pay_config"
)
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

    public PopupPayConfig() {
    }

    public Integer getWechatId() {
        return this.wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Short getOpen() {
        return this.open;
    }

    public void setOpen(Short open) {
        this.open = open;
    }
}
