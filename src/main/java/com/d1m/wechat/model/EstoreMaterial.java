package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_material")
public class EstoreMaterial {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题（图片alt或title）
     */
    private String title;

    /**
     * 标签，多个半角逗号分隔，方便筛选和分类
     */
    private String tag;

    /**
     * 素材网络路径
     */
    private String url;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取标题（图片alt或title）
     *
     * @return title - 标题（图片alt或title）
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题（图片alt或title）
     *
     * @param title 标题（图片alt或title）
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取标签，多个半角逗号分隔，方便筛选和分类
     *
     * @return tag - 标签，多个半角逗号分隔，方便筛选和分类
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签，多个半角逗号分隔，方便筛选和分类
     *
     * @param tag 标签，多个半角逗号分隔，方便筛选和分类
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * 获取素材网络路径
     *
     * @return url - 素材网络路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置素材网络路径
     *
     * @param url 素材网络路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取微信ID
     *
     * @return wechat_id - 微信ID
     */
    public Long getWechatId() {
        return wechatId;
    }

    /**
     * 设置微信ID
     *
     * @param wechatId 微信ID
     */
    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }
}