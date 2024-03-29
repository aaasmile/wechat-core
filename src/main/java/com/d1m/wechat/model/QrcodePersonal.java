package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="QrcodePersonal", description="个人二维码")
@Table(name = "qrcode_personal")
public class QrcodePersonal {
    /**
     * 主键ID
     */
	@ApiModelProperty("个人二维码ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公众号ID
     */
	@ApiModelProperty("公众号ID")
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 名称
     */
	@ApiModelProperty("名称")
    private String name;

    /**
     * 简介
     */
	@ApiModelProperty("简介")
    private String summary;

    /**
     * 二维码ticket
     */
	@ApiModelProperty("二维码ticket")
    private String ticket;

    /**
     * 二维码图片解析后的地址
     */
	@ApiModelProperty("二维码图片解析后的地址")
    @Column(name = "qrcode_url")
    private String qrcodeUrl;

    /**
     * 状态(0:删除,1:使用)
     */
	@ApiModelProperty("状态(0:删除,1:使用)")
    private Byte status;

    /**
     * 创建时间
     */
	@ApiModelProperty("创建时间")
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建用户ID
     */
	@ApiModelProperty("创建用户ID")
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)
     */
	@ApiModelProperty("二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)")
    @Column(name = "expire_seconds")
    private Integer expireSeconds;

    /**
     * 场景值ID
     */
	@ApiModelProperty("场景值ID")
    private String scene;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取简介
     *
     * @return summary - 简介
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置简介
     *
     * @param summary 简介
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    /**
     * 获取二维码ticket
     *
     * @return ticket - 二维码ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 设置二维码ticket
     *
     * @param ticket 二维码ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * 获取二维码图片解析后的地址
     *
     * @return qrcode_url - 二维码图片解析后的地址
     */
    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    /**
     * 设置二维码图片解析后的地址
     *
     * @param qrcodeUrl 二维码图片解析后的地址
     */
    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
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
     * 获取创建用户ID
     *
     * @return creator_id - 创建用户ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建用户ID
     *
     * @param creatorId 创建用户ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)
     *
     * @return expire_seconds - 二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)
     */
    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    /**
     * 设置二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)
     *
     * @param expireSeconds 二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)
     */
    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    /**
     * 获取场景值ID
     *
     * @return scene - 场景值ID
     */
    public String getScene() {
        return scene;
    }

    /**
     * 设置场景值ID
     *
     * @param scene 场景值ID
     */
    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }
}