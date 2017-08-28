package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "reply_words")
public class ReplyWords {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 自动回复ID
     */
    @Column(name = "reply_id")
    private Integer replyId;

    /**
     *  关键词
     */
    @Column(name = "reply_word")
    private String replyWord;

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
     * 获取自动回复ID
     *
     * @return reply_id - 自动回复ID
     */
    public Integer getReplyId() {
        return replyId;
    }

    /**
     * 设置自动回复ID
     *
     * @param replyId 自动回复ID
     */
    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    /**
     * 获取 关键词
     *
     * @return reply_word -  关键词
     */
    public String getReplyWord() {
        return replyWord;
    }

    /**
     * 设置 关键词
     *
     * @param replyWord  关键词
     */
    public void setReplyWord(String replyWord) {
        this.replyWord = replyWord == null ? null : replyWord.trim();
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