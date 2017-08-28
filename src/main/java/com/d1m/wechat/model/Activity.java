package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Activity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动编号
     */
    private String code;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 活动结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 分享封面
     */
    @Column(name = "share_pic")
    private String sharePic;

    /**
     * 分享标题
     */
    @Column(name = "share_title")
    private String shareTitle;

    /**
     * 分享描述
     */
    @Column(name = "share_description")
    private String shareDescription;

    /**
     * 活动URL
     */
    @Column(name = "acty_url")
    private String actyUrl;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 状态(0:删除,1:使用)
     */
    private Byte status;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建人
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 修改人
     */
    @Column(name = "modify_by_id")
    private Integer modifyById;

    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 短地址ID
     */
    @Column(name = "oauth_url_id")
    private Integer oauthUrlId;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 活动H5页面
     */
    @Column(name = "acty_h5")
    private String actyH5;

    /**
     * 活动下线H5页面
     */
    @Column(name = "acty_offline_h5")
    private String actyOfflineH5;

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
     * 获取活动编号
     *
     * @return code - 活动编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置活动编号
     *
     * @param code 活动编号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取活动描述
     *
     * @return description - 活动描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置活动描述
     *
     * @param description 活动描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取活动开始时间
     *
     * @return start_date - 活动开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置活动开始时间
     *
     * @param startDate 活动开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取活动结束时间
     *
     * @return end_date - 活动结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置活动结束时间
     *
     * @param endDate 活动结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取分享封面
     *
     * @return share_pic - 分享封面
     */
    public String getSharePic() {
        return sharePic;
    }

    /**
     * 设置分享封面
     *
     * @param sharePic 分享封面
     */
    public void setSharePic(String sharePic) {
        this.sharePic = sharePic == null ? null : sharePic.trim();
    }

    /**
     * 获取分享标题
     *
     * @return share_title - 分享标题
     */
    public String getShareTitle() {
        return shareTitle;
    }

    /**
     * 设置分享标题
     *
     * @param shareTitle 分享标题
     */
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle == null ? null : shareTitle.trim();
    }

    /**
     * 获取分享描述
     *
     * @return share_description - 分享描述
     */
    public String getShareDescription() {
        return shareDescription;
    }

    /**
     * 设置分享描述
     *
     * @param shareDescription 分享描述
     */
    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription == null ? null : shareDescription.trim();
    }

    /**
     * 获取活动URL
     *
     * @return acty_url - 活动URL
     */
    public String getActyUrl() {
        return actyUrl;
    }

    /**
     * 设置活动URL
     *
     * @param actyUrl 活动URL
     */
    public void setActyUrl(String actyUrl) {
        this.actyUrl = actyUrl == null ? null : actyUrl.trim();
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取状态(0:删除,1:使用)
     *
     * @return status - 状态(0:删除,1:使用)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(0:删除,1:使用)
     *
     * @param status 状态(0:删除,1:使用)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取创建人
     *
     * @return creator_id - 创建人
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人
     *
     * @param creatorId 创建人
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取修改人
     *
     * @return modify_by_id - 修改人
     */
    public Integer getModifyById() {
        return modifyById;
    }

    /**
     * 设置修改人
     *
     * @param modifyById 修改人
     */
    public void setModifyById(Integer modifyById) {
        this.modifyById = modifyById;
    }

    /**
     * 获取修改时间
     *
     * @return modify_at - 修改时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置修改时间
     *
     * @param modifyAt 修改时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * 获取短地址ID
     *
     * @return oauth_url_id - 短地址ID
     */
    public Integer getOauthUrlId() {
        return oauthUrlId;
    }

    /**
     * 设置短地址ID
     *
     * @param oauthUrlId 短地址ID
     */
    public void setOauthUrlId(Integer oauthUrlId) {
        this.oauthUrlId = oauthUrlId;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取活动H5页面
     *
     * @return acty_h5 - 活动H5页面
     */
    public String getActyH5() {
        return actyH5;
    }

    /**
     * 设置活动H5页面
     *
     * @param actyH5 活动H5页面
     */
    public void setActyH5(String actyH5) {
        this.actyH5 = actyH5 == null ? null : actyH5.trim();
    }

    /**
     * 获取活动下线H5页面
     *
     * @return acty_offline_h5 - 活动下线H5页面
     */
    public String getActyOfflineH5() {
        return actyOfflineH5;
    }

    /**
     * 设置活动下线H5页面
     *
     * @param actyOfflineH5 活动下线H5页面
     */
    public void setActyOfflineH5(String actyOfflineH5) {
        this.actyOfflineH5 = actyOfflineH5 == null ? null : actyOfflineH5.trim();
    }
}