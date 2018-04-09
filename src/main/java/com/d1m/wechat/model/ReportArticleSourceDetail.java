package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ReportArticleSourceDetail 
 *
 * @author f0rb on 2017-12-27.
 */
@Entity
@Table(name = "report_article_source_detail ")
public class ReportArticleSourceDetail {

    @Column(name = "wechat_id")
    private Integer wechatId;

    @Column(name = "msgid")
    private String msgid;

    @Column(name = "title")
    private String title;

    @Column(name = "ref_date")
    private String refDate;

    @Column(name = "stat_date")
    private String statDate;

    @Column(name = "target_user")
    private Integer targetUser;

    @Column(name = "int_page_read_user")
    private Integer intPageReadUser;

    @Column(name = "int_page_read_count")
    private Integer intPageReadCount;

    @Column(name = "ori_page_read_user")
    private Integer oriPageReadUser;

    @Column(name = "ori_page_read_count")
    private Integer oriPageReadCount;

    @Column(name = "share_user")
    private Integer shareUser;

    @Column(name = "share_count")
    private Integer shareCount;

    @Column(name = "add_to_fav_user")
    private Integer addToFavUser;

    @Column(name = "add_to_fav_count")
    private Integer addToFavCount;

    @Column(name = "int_page_from_session_read_user")
    private Integer intPageFromSessionReadUser;

    @Column(name = "int_page_from_session_read_count")
    private Integer intPageFromSessionReadCount;

    @Column(name = "int_page_from_hist_msg_read_user")
    private Integer intPageFromHistMsgReadUser;

    @Column(name = "int_page_from_hist_msg_read_count")
    private Integer intPageFromHistMsgReadCount;

    @Column(name = "int_page_from_feed_read_user")
    private Integer intPageFromFeedReadUser;

    @Column(name = "int_page_from_feed_read_count")
    private Integer intPageFromFeedReadCount;

    @Column(name = "int_page_from_friends_read_user")
    private Integer intPageFromFriendsReadUser;

    @Column(name = "int_page_from_friends_read_count")
    private Integer intPageFromFriendsReadCount;

    @Column(name = "int_page_from_other_read_user")
    private Integer intPageFromOtherReadUser;

    @Column(name = "int_page_from_other_read_count")
    private Integer intPageFromOtherReadCount;

    @Column(name = "feed_share_from_session_user")
    private Integer feedShareFromSessionUser;

    @Column(name = "feed_share_from_session_cnt")
    private Integer feedShareFromSessionCnt;

    @Column(name = "feed_share_from_feed_user")
    private Integer feedShareFromFeedUser;

    @Column(name = "feed_share_from_feed_cnt")
    private Integer feedShareFromFeedCnt;

    @Column(name = "feed_share_from_other_user")
    private Integer feedShareFromOtherUser;

    @Column(name = "feed_share_from_other_cnt")
    private Integer feedShareFromOtherCnt;

    @Column(name = "created_at")
    private Date createdAt;

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRefDate() {
		return refDate;
	}

	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public Integer getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(Integer targetUser) {
		this.targetUser = targetUser;
	}

	public Integer getIntPageReadUser() {
		return intPageReadUser;
	}

	public void setIntPageReadUser(Integer intPageReadUser) {
		this.intPageReadUser = intPageReadUser;
	}

	public Integer getIntPageReadCount() {
		return intPageReadCount;
	}

	public void setIntPageReadCount(Integer intPageReadCount) {
		this.intPageReadCount = intPageReadCount;
	}

	public Integer getOriPageReadUser() {
		return oriPageReadUser;
	}

	public void setOriPageReadUser(Integer oriPageReadUser) {
		this.oriPageReadUser = oriPageReadUser;
	}

	public Integer getOriPageReadCount() {
		return oriPageReadCount;
	}

	public void setOriPageReadCount(Integer oriPageReadCount) {
		this.oriPageReadCount = oriPageReadCount;
	}

	public Integer getShareUser() {
		return shareUser;
	}

	public void setShareUser(Integer shareUser) {
		this.shareUser = shareUser;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getAddToFavUser() {
		return addToFavUser;
	}

	public void setAddToFavUser(Integer addToFavUser) {
		this.addToFavUser = addToFavUser;
	}

	public Integer getAddToFavCount() {
		return addToFavCount;
	}

	public void setAddToFavCount(Integer addToFavCount) {
		this.addToFavCount = addToFavCount;
	}

	public Integer getIntPageFromSessionReadUser() {
		return intPageFromSessionReadUser;
	}

	public void setIntPageFromSessionReadUser(Integer intPageFromSessionReadUser) {
		this.intPageFromSessionReadUser = intPageFromSessionReadUser;
	}

	public Integer getIntPageFromSessionReadCount() {
		return intPageFromSessionReadCount;
	}

	public void setIntPageFromSessionReadCount(Integer intPageFromSessionReadCount) {
		this.intPageFromSessionReadCount = intPageFromSessionReadCount;
	}

	public Integer getIntPageFromHistMsgReadUser() {
		return intPageFromHistMsgReadUser;
	}

	public void setIntPageFromHistMsgReadUser(Integer intPageFromHistMsgReadUser) {
		this.intPageFromHistMsgReadUser = intPageFromHistMsgReadUser;
	}

	public Integer getIntPageFromHistMsgReadCount() {
		return intPageFromHistMsgReadCount;
	}

	public void setIntPageFromHistMsgReadCount(Integer intPageFromHistMsgReadCount) {
		this.intPageFromHistMsgReadCount = intPageFromHistMsgReadCount;
	}

	public Integer getIntPageFromFeedReadUser() {
		return intPageFromFeedReadUser;
	}

	public void setIntPageFromFeedReadUser(Integer intPageFromFeedReadUser) {
		this.intPageFromFeedReadUser = intPageFromFeedReadUser;
	}

	public Integer getIntPageFromFeedReadCount() {
		return intPageFromFeedReadCount;
	}

	public void setIntPageFromFeedReadCount(Integer intPageFromFeedReadCount) {
		this.intPageFromFeedReadCount = intPageFromFeedReadCount;
	}

	public Integer getIntPageFromFriendsReadUser() {
		return intPageFromFriendsReadUser;
	}

	public void setIntPageFromFriendsReadUser(Integer intPageFromFriendsReadUser) {
		this.intPageFromFriendsReadUser = intPageFromFriendsReadUser;
	}

	public Integer getIntPageFromFriendsReadCount() {
		return intPageFromFriendsReadCount;
	}

	public void setIntPageFromFriendsReadCount(Integer intPageFromFriendsReadCount) {
		this.intPageFromFriendsReadCount = intPageFromFriendsReadCount;
	}

	public Integer getIntPageFromOtherReadUser() {
		return intPageFromOtherReadUser;
	}

	public void setIntPageFromOtherReadUser(Integer intPageFromOtherReadUser) {
		this.intPageFromOtherReadUser = intPageFromOtherReadUser;
	}

	public Integer getIntPageFromOtherReadCount() {
		return intPageFromOtherReadCount;
	}

	public void setIntPageFromOtherReadCount(Integer intPageFromOtherReadCount) {
		this.intPageFromOtherReadCount = intPageFromOtherReadCount;
	}

	public Integer getFeedShareFromSessionUser() {
		return feedShareFromSessionUser;
	}

	public void setFeedShareFromSessionUser(Integer feedShareFromSessionUser) {
		this.feedShareFromSessionUser = feedShareFromSessionUser;
	}

	public Integer getFeedShareFromSessionCnt() {
		return feedShareFromSessionCnt;
	}

	public void setFeedShareFromSessionCnt(Integer feedShareFromSessionCnt) {
		this.feedShareFromSessionCnt = feedShareFromSessionCnt;
	}

	public Integer getFeedShareFromFeedUser() {
		return feedShareFromFeedUser;
	}

	public void setFeedShareFromFeedUser(Integer feedShareFromFeedUser) {
		this.feedShareFromFeedUser = feedShareFromFeedUser;
	}

	public Integer getFeedShareFromFeedCnt() {
		return feedShareFromFeedCnt;
	}

	public void setFeedShareFromFeedCnt(Integer feedShareFromFeedCnt) {
		this.feedShareFromFeedCnt = feedShareFromFeedCnt;
	}

	public Integer getFeedShareFromOtherUser() {
		return feedShareFromOtherUser;
	}

	public void setFeedShareFromOtherUser(Integer feedShareFromOtherUser) {
		this.feedShareFromOtherUser = feedShareFromOtherUser;
	}

	public Integer getFeedShareFromOtherCnt() {
		return feedShareFromOtherCnt;
	}

	public void setFeedShareFromOtherCnt(Integer feedShareFromOtherCnt) {
		this.feedShareFromOtherCnt = feedShareFromOtherCnt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}