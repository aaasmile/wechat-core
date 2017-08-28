package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "oauth_url")
public class OauthUrl {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 短地址
     */
    @Column(name = "short_url")
    private String shortUrl;

    /**
     * 授权作用域：snsapi_base，snsapi_userinfo
     */
    private String scope;

    /**
     * 处理全类名
     */
    @Column(name = "process_class")
    private String processClass;

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
     * 参数，Json对象
     */
    private String params;

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
     * 获取短地址
     *
     * @return short_url - 短地址
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * 设置短地址
     *
     * @param shortUrl 短地址
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl == null ? null : shortUrl.trim();
    }

    /**
     * 获取授权作用域：snsapi_base，snsapi_userinfo
     *
     * @return scope - 授权作用域：snsapi_base，snsapi_userinfo
     */
    public String getScope() {
        return scope;
    }

    /**
     * 设置授权作用域：snsapi_base，snsapi_userinfo
     *
     * @param scope 授权作用域：snsapi_base，snsapi_userinfo
     */
    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    /**
     * 获取处理全类名
     *
     * @return process_class - 处理全类名
     */
    public String getProcessClass() {
        return processClass;
    }

    /**
     * 设置处理全类名
     *
     * @param processClass 处理全类名
     */
    public void setProcessClass(String processClass) {
        this.processClass = processClass == null ? null : processClass.trim();
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
     * 获取参数，Json对象
     *
     * @return params - 参数，Json对象
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置参数，Json对象
     *
     * @param params 参数，Json对象
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }
}