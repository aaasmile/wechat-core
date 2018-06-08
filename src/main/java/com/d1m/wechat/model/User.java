package com.d1m.wechat.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户")
public class User implements Serializable {

    private static final long serialVersionUID = 8121060670969148764L;

    @Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", nickname=" + nickname + ", kfId=" + kfId + ", headImgUrl=" + headImgUrl + ", localHeadImgUrl="
				+ localHeadImgUrl + ", createdAt=" + createdAt + ", creatorId=" + creatorId + ", modifyAt=" + modifyAt
				+ ", pushAt=" + pushAt + ", wechatId=" + wechatId + ", position=" + position + ", mobile=" + mobile
				+ ", lastLoginAt=" + lastLoginAt + ", status=" + status + ", roleId=" + roleId + ", companyId="
				+ companyId + ", counter=" + counter + "]";
	}

	/**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 客服工号
     */
    @ApiModelProperty("客服工号")
    @Column(name = "kf_id")
    private String kfId;

    /**
     * 微信头像地址
     */
    @ApiModelProperty("微信头像地址")
    @Column(name = "head_img_url")
    private String headImgUrl;

    /**
     * 本地头像地址
     */
    @ApiModelProperty("本地头像地址")
    @Column(name = "local_head_img_url")
    private String localHeadImgUrl;

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
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 微信同步推送时间
     */
    @ApiModelProperty("微信同步推送时间")
    @Column(name = "push_at")
    private Date pushAt;

    /**
     * 公众号ID
     */
    @ApiModelProperty("公众号ID")
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 职位
     */
    @ApiModelProperty("职位")
    private String position;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty("最后一次登录时间")
    @Column(name = "last_login_at")
    private Date lastLoginAt;

    /**
     * 用户存在-1，用户删除-0
     */
    @ApiModelProperty("用户存在-1，用户删除-0")
    private Byte status;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 公司ID
     */
    @ApiModelProperty("公司ID")
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 登录失败计数器
     */
    @ApiModelProperty("登录失败计数器")
    private Integer counter;

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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取客服工号
     *
     * @return kf_id - 客服工号
     */
    public String getKfId() {
        return kfId;
    }

    /**
     * 设置客服工号
     *
     * @param kfId 客服工号
     */
    public void setKfId(String kfId) {
        this.kfId = kfId == null ? null : kfId.trim();
    }

    /**
     * 获取微信头像地址
     *
     * @return head_img_url - 微信头像地址
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * 设置微信头像地址
     *
     * @param headImgUrl 微信头像地址
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

    /**
     * 获取本地头像地址
     *
     * @return local_head_img_url - 本地头像地址
     */
    public String getLocalHeadImgUrl() {
        return localHeadImgUrl;
    }

    /**
     * 设置本地头像地址
     *
     * @param localHeadImgUrl 本地头像地址
     */
    public void setLocalHeadImgUrl(String localHeadImgUrl) {
        this.localHeadImgUrl = localHeadImgUrl == null ? null : localHeadImgUrl.trim();
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
     * 获取更新时间
     *
     * @return modify_at - 更新时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置更新时间
     *
     * @param modifyAt 更新时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * 获取微信同步推送时间
     *
     * @return push_at - 微信同步推送时间
     */
    public Date getPushAt() {
        return pushAt;
    }

    /**
     * 设置微信同步推送时间
     *
     * @param pushAt 微信同步推送时间
     */
    public void setPushAt(Date pushAt) {
        this.pushAt = pushAt;
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
     * 获取职位
     *
     * @return position - 职位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置职位
     *
     * @param position 职位
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取最后一次登录时间
     *
     * @return last_login_at - 最后一次登录时间
     */
    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * 设置最后一次登录时间
     *
     * @param lastLoginAt 最后一次登录时间
     */
    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * 获取用户存在-1，用户删除-0
     *
     * @return status - 用户存在-1，用户删除-0
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置用户存在-1，用户删除-0
     *
     * @param status 用户存在-1，用户删除-0
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取公司ID
     *
     * @return company_id - 公司ID
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置公司ID
     *
     * @param companyId 公司ID
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取登录失败计数器
     *
     * @return counter - 登录失败计数器
     */
    public Integer getCounter() {
        return counter;
    }

    /**
     * 设置登录失败计数器
     *
     * @param counter 登录失败计数器
     */
    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}