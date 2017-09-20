package com.d1m.wechat.model.popup;

import javax.persistence.*;
import java.util.Date;

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

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取政府区域编码
     *
     * @return code - 政府区域编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置政府区域编码
     *
     * @param code 政府区域编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return name_zh
     */
    public String getNameZh() {
        return nameZh;
    }

    /**
     * @param nameZh
     */
    public void setNameZh(String nameZh) {
        this.nameZh = nameZh == null ? null : nameZh.trim();
    }

    /**
     * @return name_en
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * @param nameEn
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    /**
     * 获取父级id
     *
     * @return pid - 父级id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父级id
     *
     * @param pid 父级id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}