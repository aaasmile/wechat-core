package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "offline_activity")
public class OfflineActivity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 离线活动编号
     */
    private String code;

    /**
     * 线下活动名称
     */
    private String name;

    /**
     * 线下活动描述
     */
    private String description;

    /**
     * 线下活动开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 线下活动结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 分析封面
     */
    @Column(name = "share_pic")
    private String sharePic;

    /**
     * 分析标题
     */
    @Column(name = "share_title")
    private String shareTitle;

    /**
     * 分享描述
     */
    @Column(name = "share_description")
    private String shareDescription;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 状态（0-删除，1-使用）
     */
    private Byte status;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

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
     * 获取离线活动编号
     *
     * @return code - 离线活动编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置离线活动编号
     *
     * @param code 离线活动编号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取线下活动名称
     *
     * @return name - 线下活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置线下活动名称
     *
     * @param name 线下活动名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取线下活动描述
     *
     * @return description - 线下活动描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置线下活动描述
     *
     * @param description 线下活动描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取线下活动开始时间
     *
     * @return start_date - 线下活动开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置线下活动开始时间
     *
     * @param startDate 线下活动开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取线下活动结束时间
     *
     * @return end_date - 线下活动结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置线下活动结束时间
     *
     * @param endDate 线下活动结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取分析封面
     *
     * @return share_pic - 分析封面
     */
    public String getSharePic() {
        return sharePic;
    }

    /**
     * 设置分析封面
     *
     * @param sharePic 分析封面
     */
    public void setSharePic(String sharePic) {
        this.sharePic = sharePic == null ? null : sharePic.trim();
    }

    /**
     * 获取分析标题
     *
     * @return share_title - 分析标题
     */
    public String getShareTitle() {
        return shareTitle;
    }

    /**
     * 设置分析标题
     *
     * @param shareTitle 分析标题
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
     * 获取状态（0-删除，1-使用）
     *
     * @return status - 状态（0-删除，1-使用）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态（0-删除，1-使用）
     *
     * @param status 状态（0-删除，1-使用）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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
}