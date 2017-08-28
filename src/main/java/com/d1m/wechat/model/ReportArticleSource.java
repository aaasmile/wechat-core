package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "report_article_source")
public class ReportArticleSource {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图文消息ID
     */
    private String msgid;

    /**
     *  图文消息标题
     */
    private String title;

    /**
     * 群发日期
     */
    @Column(name = "ref_date")
    private String refDate;

    /**
     * 图文页阅读人数
     */
    @Column(name = "page_user")
    private Integer pageUser;

    /**
     * 图文页阅读次数
     */
    @Column(name = "page_count")
    private Integer pageCount;

    /**
     * 原文页阅读人数
     */
    @Column(name = "ori_page_user")
    private Integer oriPageUser;

    /**
     * 原文页阅读次数
     */
    @Column(name = "ori_page_count")
    private Integer oriPageCount;

    /**
     * 收藏人数
     */
    @Column(name = "add_fav_user")
    private Integer addFavUser;

    /**
     * 收藏次数
     */
    @Column(name = "add_fav_count")
    private Integer addFavCount;

    /**
     * 分享人数
     */
    @Column(name = "share_user")
    private Integer shareUser;

    /**
     * 分享次数
     */
    @Column(name = "share_count")
    private Integer shareCount;

    /**
     * 所属公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

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
     * 获取图文消息ID
     *
     * @return msgid - 图文消息ID
     */
    public String getMsgid() {
        return msgid;
    }

    /**
     * 设置图文消息ID
     *
     * @param msgid 图文消息ID
     */
    public void setMsgid(String msgid) {
        this.msgid = msgid == null ? null : msgid.trim();
    }

    /**
     * 获取 图文消息标题
     *
     * @return title -  图文消息标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置 图文消息标题
     *
     * @param title  图文消息标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取群发日期
     *
     * @return ref_date - 群发日期
     */
    public String getRefDate() {
        return refDate;
    }

    /**
     * 设置群发日期
     *
     * @param refDate 群发日期
     */
    public void setRefDate(String refDate) {
        this.refDate = refDate == null ? null : refDate.trim();
    }

    /**
     * 获取图文页阅读人数
     *
     * @return page_user - 图文页阅读人数
     */
    public Integer getPageUser() {
        return pageUser;
    }

    /**
     * 设置图文页阅读人数
     *
     * @param pageUser 图文页阅读人数
     */
    public void setPageUser(Integer pageUser) {
        this.pageUser = pageUser;
    }

    /**
     * 获取图文页阅读次数
     *
     * @return page_count - 图文页阅读次数
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * 设置图文页阅读次数
     *
     * @param pageCount 图文页阅读次数
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 获取原文页阅读人数
     *
     * @return ori_page_user - 原文页阅读人数
     */
    public Integer getOriPageUser() {
        return oriPageUser;
    }

    /**
     * 设置原文页阅读人数
     *
     * @param oriPageUser 原文页阅读人数
     */
    public void setOriPageUser(Integer oriPageUser) {
        this.oriPageUser = oriPageUser;
    }

    /**
     * 获取原文页阅读次数
     *
     * @return ori_page_count - 原文页阅读次数
     */
    public Integer getOriPageCount() {
        return oriPageCount;
    }

    /**
     * 设置原文页阅读次数
     *
     * @param oriPageCount 原文页阅读次数
     */
    public void setOriPageCount(Integer oriPageCount) {
        this.oriPageCount = oriPageCount;
    }

    /**
     * 获取收藏人数
     *
     * @return add_fav_user - 收藏人数
     */
    public Integer getAddFavUser() {
        return addFavUser;
    }

    /**
     * 设置收藏人数
     *
     * @param addFavUser 收藏人数
     */
    public void setAddFavUser(Integer addFavUser) {
        this.addFavUser = addFavUser;
    }

    /**
     * 获取收藏次数
     *
     * @return add_fav_count - 收藏次数
     */
    public Integer getAddFavCount() {
        return addFavCount;
    }

    /**
     * 设置收藏次数
     *
     * @param addFavCount 收藏次数
     */
    public void setAddFavCount(Integer addFavCount) {
        this.addFavCount = addFavCount;
    }

    /**
     * 获取分享人数
     *
     * @return share_user - 分享人数
     */
    public Integer getShareUser() {
        return shareUser;
    }

    /**
     * 设置分享人数
     *
     * @param shareUser 分享人数
     */
    public void setShareUser(Integer shareUser) {
        this.shareUser = shareUser;
    }

    /**
     * 获取分享次数
     *
     * @return share_count - 分享次数
     */
    public Integer getShareCount() {
        return shareCount;
    }

    /**
     * 设置分享次数
     *
     * @param shareCount 分享次数
     */
    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    /**
     * 获取所属公众号ID
     *
     * @return wechat_id - 所属公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置所属公众号ID
     *
     * @param wechatId 所属公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
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
}