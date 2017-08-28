package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "area_info")
public class AreaInfo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 地区码
     */
    private Integer code;

    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 中文名称
     */
    @Column(name = "c_name")
    private String cName;

    /**
     * 英文名称
     */
    @Column(name = "e_name")
    private String eName;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取地区码
     *
     * @return code - 地区码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置地区码
     *
     * @param code 地区码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取父级ID
     *
     * @return parent_id - 父级ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级ID
     *
     * @param parentId 父级ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取中文名称
     *
     * @return c_name - 中文名称
     */
    public String getcName() {
        return cName;
    }

    /**
     * 设置中文名称
     *
     * @param cName 中文名称
     */
    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    /**
     * 获取英文名称
     *
     * @return e_name - 英文名称
     */
    public String geteName() {
        return eName;
    }

    /**
     * 设置英文名称
     *
     * @param eName 英文名称
     */
    public void seteName(String eName) {
        this.eName = eName == null ? null : eName.trim();
    }
}