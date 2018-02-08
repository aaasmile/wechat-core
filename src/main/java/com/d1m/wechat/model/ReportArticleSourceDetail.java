package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * ReportArticleSourceDetail 
 *
 * @author f0rb on 2017-12-27.
 */
@Getter
@Setter
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

}