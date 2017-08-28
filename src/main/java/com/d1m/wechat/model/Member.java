package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Member {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * unionId
	 */
	@Column(name = "union_id")
	private String unionId;

	/**
	 * openId
	 */
	@Column(name = "open_id")
	private String openId;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 性别
	 */
	private Byte sex;

	/**
	 * 语言
	 */
	private Byte language;

	/**
	 * 城市
	 */
	private Integer city;

	/**
	 * 省份
	 */
	private Integer province;

	/**
	 * 国家
	 */
	private Integer country;

	/**
	 * 本地头像地址
	 */
	@Column(name = "local_head_img_url")
	private String localHeadImgUrl;

	/**
	 * 微信头像地址
	 */
	@Column(name = "head_img_url")
	private String headImgUrl;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 所属公众号ID
	 */
	@Column(name = "wechat_id")
	private Integer wechatId;

	/**
	 * 用户分组ID
	 */
	@Column(name = "member_group_id")
	private Integer memberGroupId;

	/**
	 * 活跃度
	 */
	private Byte activity;

	/**
	 * 本月群发数量
	 */
	@Column(name = "batchsend_month")
	private Integer batchsendMonth;

	/**
	 * 关注时间
	 */
	@Column(name = "subscribe_at")
	private Date subscribeAt;

	/**
	 * 取消关注时间
	 */
	@Column(name = "unsubscribe_at")
	private Date unsubscribeAt;

	/**
	 * 来源
	 */
	private Byte fromwhere;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否关注(1:关注,0:取消关注)
	 */
	@Column(name = "is_subscribe")
	private Boolean isSubscribe;

	/**
	 * 最后一次会话时间
	 */
	@Column(name = "last_conversation_at")
	private Date lastConversationAt;

	/**
	 * 来源
	 */
	private String source;

	/**
	 * 关键词
	 */
	private String keyword;

	/**
	 * 绑定状态(0:已解绑,1:已绑定)
	 */
	private Byte status;

	/**
	 * 卡号
	 */
	private String pmcode;

	/**
	 * 卡级别
	 */
	private String levels;

	/**
	 * 获取活跃度
	 *
	 * @return activity - 活跃度
	 */
	public Byte getActivity() {
		return activity;
	}

	/**
	 * 获取本月群发数量
	 *
	 * @return batchsend_month - 本月群发数量
	 */
	public Integer getBatchsendMonth() {
		return batchsendMonth;
	}

	/**
	 * 获取城市
	 *
	 * @return city - 城市
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * 获取国家
	 *
	 * @return country - 国家
	 */
	public Integer getCountry() {
		return country;
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
	 * 获取来源
	 *
	 * @return fromwhere - 来源
	 */
	public Byte getFromwhere() {
		return fromwhere;
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
	 * 获取主键ID
	 *
	 * @return id - 主键ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 获取是否关注(1:关注,0:取消关注)
	 *
	 * @return is_subscribe - 是否关注(1:关注,0:取消关注)
	 */
	public Boolean getIsSubscribe() {
		return isSubscribe;
	}

	/**
	 * 获取语言
	 *
	 * @return language - 语言
	 */
	public Byte getLanguage() {
		return language;
	}

	/**
	 * 获取最后一次会话时间
	 *
	 * @return last_conversation_at - 最后一次会话时间
	 */
	public Date getLastConversationAt() {
		return lastConversationAt;
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
	 * 获取用户分组ID
	 *
	 * @return member_group_id - 用户分组ID
	 */
	public Integer getMemberGroupId() {
		return memberGroupId;
	}

	/**
	 * 获取手机号
	 *
	 * @return mobile - 手机号
	 */
	public String getMobile() {
		return mobile;
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
	 * 获取openId
	 *
	 * @return open_id - openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * 获取省份
	 *
	 * @return province - 省份
	 */
	public Integer getProvince() {
		return province;
	}

	/**
	 * 获取备注
	 *
	 * @return remark - 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 获取性别
	 *
	 * @return sex - 性别
	 */
	public Byte getSex() {
		return sex;
	}

	/**
	 * 获取关注时间
	 *
	 * @return subscribeAt - 关注时间
	 */
	public Date getSubscribeAt() {
		return subscribeAt;
	}

	/**
	 * 获取unionId
	 *
	 * @return union_id - unionId
	 */
	public String getUnionId() {
		return unionId;
	}

	/**
	 * 获取取消关注时间
	 *
	 */
	public Date getUnsubscribeAt() {
		return unsubscribeAt;
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
	 * 设置活跃度
	 *
	 * @param activity
	 *            活跃度
	 */
	public void setActivity(Byte activity) {
		this.activity = activity;
	}

	/**
	 * 设置本月群发数量
	 *
	 * @param batchsendMonth
	 *            本月群发数量
	 */
	public void setBatchsendMonth(Integer batchsendMonth) {
		this.batchsendMonth = batchsendMonth;
	}

	/**
	 * 设置城市
	 *
	 * @param city
	 *            城市
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	/**
	 * 设置国家
	 *
	 * @param country
	 *            国家
	 */
	public void setCountry(Integer country) {
		this.country = country;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createdAt
	 *            创建时间
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 设置来源
	 *
	 * @param fromwhere
	 *            来源
	 */
	public void setFromwhere(Byte fromwhere) {
		this.fromwhere = fromwhere;
	}

	/**
	 * 设置微信头像地址
	 *
	 * @param headImgUrl
	 *            微信头像地址
	 */
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
	}

	/**
	 * 设置主键ID
	 *
	 * @param id
	 *            主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 设置是否关注(1:关注,0:取消关注)
	 *
	 * @param isSubscribe
	 *            是否关注(1:关注,0:取消关注)
	 */
	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	/**
	 * 设置语言
	 *
	 * @param language
	 *            语言
	 */
	public void setLanguage(Byte language) {
		this.language = language;
	}

	/**
	 * 设置最后一次会话时间
	 *
	 * @param lastConversationAt
	 *            最后一次会话时间
	 */
	public void setLastConversationAt(Date lastConversationAt) {
		this.lastConversationAt = lastConversationAt;
	}

	/**
	 * 设置本地头像地址
	 *
	 * @param localHeadImgUrl
	 *            本地头像地址
	 */
	public void setLocalHeadImgUrl(String localHeadImgUrl) {
		this.localHeadImgUrl = localHeadImgUrl == null ? null : localHeadImgUrl
				.trim();
	}

	/**
	 * 设置用户分组ID
	 *
	 * @param memberGroupId
	 *            用户分组ID
	 */
	public void setMemberGroupId(Integer memberGroupId) {
		this.memberGroupId = memberGroupId;
	}

	/**
	 * 设置手机号
	 *
	 * @param mobile
	 *            手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	/**
	 * 设置昵称
	 *
	 * @param nickname
	 *            昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	/**
	 * 设置openId
	 *
	 * @param openId
	 *            openId
	 */
	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	/**
	 * 设置省份
	 *
	 * @param province
	 *            省份
	 */
	public void setProvince(Integer province) {
		this.province = province;
	}

	/**
	 * 设置备注
	 *
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 设置性别
	 *
	 * @param sex
	 *            性别
	 */
	public void setSex(Byte sex) {
		this.sex = sex;
	}

	/**
	 * 设置关注时间
	 *
	 * @param subscribeAt
	 *            关注时间
	 */
	public void setSubscribeAt(Date subscribeAt) {
		this.subscribeAt = subscribeAt;
	}

	/**
	 * 设置unionId
	 *
	 * @param unionId
	 *            unionId
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId == null ? null : unionId.trim();
	}

	/**
	 * 设置取消关注时间
	 *
	 * @param unsubscribeAt
	 *            取消关注时间
	 */
	public void setUnsubscribeAt(Date unsubscribeAt) {
		this.unsubscribeAt = unsubscribeAt;
	}

	/**
	 * 设置所属公众号ID
	 *
	 * @param wechatId
	 *            所属公众号ID
	 */
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	/**
	 * 获取来源
	 *
	 * @return source - 来源
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 设置来源
	 *
	 * @param source
	 *            来源
	 */
	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	/**
	 * 获取关键词
	 *
	 * @return keyword - 关键词
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 设置关键词
	 *
	 * @param keyword
	 *            关键词
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	/**
	 * 获取绑定状态(0:已解绑,1:已绑定)
	 *
	 * @return status - 绑定状态(0:已解绑,1:已绑定)
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置绑定状态(0:已解绑,1:已绑定)
	 *
	 * @param status
	 *            绑定状态(0:已解绑,1:已绑定)
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取卡号
	 *
	 * @return pmcode - 卡号
	 */
	public String getPmcode() {
		return pmcode;
	}

	/**
	 * 设置卡号
	 *
	 * @param pmcode
	 *            卡号
	 */
	public void setPmcode(String pmcode) {
		this.pmcode = pmcode == null ? null : pmcode.trim();
	}

	/**
	 * 获取卡级别
	 *
	 * @return levels - 卡级别
	 */
	public String getLevels() {
		return levels;
	}

	/**
	 * 设置卡级别
	 *
	 * @param levels
	 *            卡级别
	 */
	public void setLevels(String levels) {
		this.levels = levels;
	}

	@Override
	public String toString() {
		return "Member{" + "id=" + id + ", unionId='" + unionId + '\''
				+ ", openId='" + openId + '\'' + ", nickname='" + nickname
				+ '\'' + ", sex=" + sex + ", language=" + language + ", city="
				+ city + ", province=" + province + ", country=" + country
				+ ", localHeadImgUrl='" + localHeadImgUrl + '\''
				+ ", headImgUrl='" + headImgUrl + '\'' + ", createdAt="
				+ createdAt + ", wechatId=" + wechatId + ", memberGroupId="
				+ memberGroupId + ", activity=" + activity
				+ ", batchsendMonth=" + batchsendMonth + ", subscribeAt="
				+ subscribeAt + ", unsubscribeAt=" + unsubscribeAt
				+ ", fromwhere=" + fromwhere + ", mobile='" + mobile + '\''
				+ ", remark='" + remark + '\'' + ", isSubscribe=" + isSubscribe
				+ ", lastConversationAt=" + lastConversationAt + ", source='"
				+ source + '\'' + ", keyword='" + keyword + '\'' + ", status='"
				+ status + '\'' + ", pmcode='" + pmcode + '\'' + ", levels='"
				+ levels + '\'' + '}';
	}
}