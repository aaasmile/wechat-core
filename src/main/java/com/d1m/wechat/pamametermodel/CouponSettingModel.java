package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("优惠券设置")
public class CouponSettingModel extends BaseModel {
	
	@ApiModelProperty("主键ID")
	private Integer id;
	
	@ApiModelProperty("活动名称")
	private String name;
	
	@ApiModelProperty("活动名称")
	private String shortName;
	
	@ApiModelProperty("使用条件")
	private String conditions;
	
	@ApiModelProperty("优惠券背景颜色")
	private String couponBgcolor;
	
	@ApiModelProperty("优惠券标题背景颜色")
	private String couponTitleBgcolor;
	
	@ApiModelProperty("优惠券标题背景颜色")
	private Integer winProbability;

	@ApiModelProperty("每人参与次数")
	private Integer timesOfJoin;
	
	@ApiModelProperty("每人中奖次数上限")
	private Integer timesOfWin;
	
	@ApiModelProperty("优惠券描述")
	private String couponDescription;
	
	@ApiModelProperty("优惠券图片")
	private String couponPic;

	/** 门店类型 */
	private Byte type;

	private Byte fetchType;

	private Integer activityId;

	/** 类型 */
	private Byte activityType;

	/** 名称,编号 */
	private String query;

	private Integer memberId;

	private Integer wechatId;

	private String openId;

	private String endAt;

	private Integer couponSum;

	public Integer getActivityId() {
		return activityId;
	}

	public Byte getActivityType() {
		return activityType;
	}

	public String getConditions() {
		return conditions;
	}

	public String getCouponBgcolor() {
		return couponBgcolor;
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public String getCouponPic() {
		return couponPic;
	}

	public Integer getCouponSum() {
		return couponSum;
	}

	public String getCouponTitleBgcolor() {
		return couponTitleBgcolor;
	}

	public String getEndAt() {
		return endAt;
	}

	public Byte getFetchType() {
		return fetchType;
	}

	public Integer getId() {
		return id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}

	public String getOpenId() {
		return openId;
	}

	public String getQuery() {
		return query;
	}

	public String getShortName() {
		return shortName;
	}

	public Integer getTimesOfJoin() {
		return timesOfJoin;
	}

	public Integer getTimesOfWin() {
		return timesOfWin;
	}

	public Byte getType() {
		return type;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public Integer getWinProbability() {
		return winProbability;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public void setActivityType(Byte activityType) {
		this.activityType = activityType;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public void setCouponBgcolor(String couponBgcolor) {
		this.couponBgcolor = couponBgcolor;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public void setCouponPic(String couponPic) {
		this.couponPic = couponPic;
	}

	public void setCouponSum(Integer couponSum) {
		this.couponSum = couponSum;
	}

	public void setCouponTitleBgcolor(String couponTitleBgcolor) {
		this.couponTitleBgcolor = couponTitleBgcolor;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public void setFetchType(Byte fetchType) {
		this.fetchType = fetchType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setTimesOfJoin(Integer timesOfJoin) {
		this.timesOfJoin = timesOfJoin;
	}

	public void setTimesOfWin(Integer timesOfWin) {
		this.timesOfWin = timesOfWin;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public void setWinProbability(Integer winProbability) {
		this.winProbability = winProbability;
	}

}
