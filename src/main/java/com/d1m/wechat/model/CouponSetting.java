package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "coupon_setting")
public class CouponSetting {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动编号
     */
    private String grno;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 简称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 使用条件
     */
    private String conditions;

    /**
     * 渠道类型：10：微信渠道、20：其他渠道
     */
    private String channel;

    /**
     * 领礼类型：10：一般领礼、20：发票领礼、30：积分换礼、40：银行礼、50：补库存
     */
    private String type;

    /**
     * 赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折
     */
    @Column(name = "gift_type")
    private String giftType;

    /**
     * 领券开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 领券结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 核销开始时间
     */
    @Column(name = "validity_start_date")
    private Date validityStartDate;

    /**
     * 核销结束时间
     */
    @Column(name = "validity_end_date")
    private Date validityEndDate;

    /**
     * 折扣方式：10：扣减金额、20：折扣比例
     */
    @Column(name = "rebate_method")
    private String rebateMethod;

    /**
     * 折价券扣减金额/折扣比例
     */
    @Column(name = "rebate_sum")
    private Double rebateSum;

    /**
     * 优惠券总数
     */
    @Column(name = "coupon_sum")
    private Integer couponSum;

    /**
     * 折扣券时，门槛类型：20-金额门槛，30-积分门槛
     */
    @Column(name = "issue_limit_type")
    private String issueLimitType;

    /**
     * 门槛值
     */
    @Column(name = "issue_limit_value")
    private Double issueLimitValue;

    /**
     * 活动领取类型(0:非关注后领取,1:关注后领取)
     */
    @Column(name = "fetch_type")
    private Byte fetchType;

    /**
     * 中奖概率，取值0-100
     */
    @Column(name = "win_probability")
    private Integer winProbability;

    /**
     * 每人参与次数
     */
    @Column(name = "times_of_join")
    private Integer timesOfJoin;

    /**
     * 每人中奖次数上限
     */
    @Column(name = "times_of_win")
    private Integer timesOfWin;

    /**
     * 优惠券描述
     */
    @Column(name = "coupon_description")
    private String couponDescription;

    /**
     * 优惠券图片
     */
    @Column(name = "coupon_pic")
    private String couponPic;

    /**
     * 优惠券背景颜色
     */
    @Column(name = "coupon_bgcolor")
    private String couponBgcolor;

    /**
     * 优惠券标题背景颜色
     */
    @Column(name = "coupon_title_bgcolor")
    private String couponTitleBgcolor;

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
     * 创建人
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 修改人
     */
    @Column(name = "modify_by_id")
    private Integer modifyById;

    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 赠品信息
     */
    @Column(name = "pordInfos")
    private String pordinfos;

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
     * 获取活动编号
     *
     * @return grno - 活动编号
     */
    public String getGrno() {
        return grno;
    }

    /**
     * 设置活动编号
     *
     * @param grno 活动编号
     */
    public void setGrno(String grno) {
        this.grno = grno == null ? null : grno.trim();
    }

    /**
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取简称
     *
     * @return short_name - 简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置简称
     *
     * @param shortName 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * 获取使用条件
     *
     * @return conditions - 使用条件
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * 设置使用条件
     *
     * @param conditions 使用条件
     */
    public void setConditions(String conditions) {
        this.conditions = conditions == null ? null : conditions.trim();
    }

    /**
     * 获取渠道类型：10：微信渠道、20：其他渠道
     *
     * @return channel - 渠道类型：10：微信渠道、20：其他渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置渠道类型：10：微信渠道、20：其他渠道
     *
     * @param channel 渠道类型：10：微信渠道、20：其他渠道
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    /**
     * 获取领礼类型：10：一般领礼、20：发票领礼、30：积分换礼、40：银行礼、50：补库存
     *
     * @return type - 领礼类型：10：一般领礼、20：发票领礼、30：积分换礼、40：银行礼、50：补库存
     */
    public String getType() {
        return type;
    }

    /**
     * 设置领礼类型：10：一般领礼、20：发票领礼、30：积分换礼、40：银行礼、50：补库存
     *
     * @param type 领礼类型：10：一般领礼、20：发票领礼、30：积分换礼、40：银行礼、50：补库存
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折
     *
     * @return gift_type - 赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折
     */
    public String getGiftType() {
        return giftType;
    }

    /**
     * 设置赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折
     *
     * @param giftType 赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折
     */
    public void setGiftType(String giftType) {
        this.giftType = giftType == null ? null : giftType.trim();
    }

    /**
     * 获取领券开始时间
     *
     * @return start_date - 领券开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置领券开始时间
     *
     * @param startDate 领券开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取领券结束时间
     *
     * @return end_date - 领券结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置领券结束时间
     *
     * @param endDate 领券结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取核销开始时间
     *
     * @return validity_start_date - 核销开始时间
     */
    public Date getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * 设置核销开始时间
     *
     * @param validityStartDate 核销开始时间
     */
    public void setValidityStartDate(Date validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    /**
     * 获取核销结束时间
     *
     * @return validity_end_date - 核销结束时间
     */
    public Date getValidityEndDate() {
        return validityEndDate;
    }

    /**
     * 设置核销结束时间
     *
     * @param validityEndDate 核销结束时间
     */
    public void setValidityEndDate(Date validityEndDate) {
        this.validityEndDate = validityEndDate;
    }

    /**
     * 获取折扣方式：10：扣减金额、20：折扣比例
     *
     * @return rebate_method - 折扣方式：10：扣减金额、20：折扣比例
     */
    public String getRebateMethod() {
        return rebateMethod;
    }

    /**
     * 设置折扣方式：10：扣减金额、20：折扣比例
     *
     * @param rebateMethod 折扣方式：10：扣减金额、20：折扣比例
     */
    public void setRebateMethod(String rebateMethod) {
        this.rebateMethod = rebateMethod == null ? null : rebateMethod.trim();
    }

    /**
     * 获取折价券扣减金额/折扣比例
     *
     * @return rebate_sum - 折价券扣减金额/折扣比例
     */
    public Double getRebateSum() {
        return rebateSum;
    }

    /**
     * 设置折价券扣减金额/折扣比例
     *
     * @param rebateSum 折价券扣减金额/折扣比例
     */
    public void setRebateSum(Double rebateSum) {
        this.rebateSum = rebateSum;
    }

    /**
     * 获取优惠券总数
     *
     * @return coupon_sum - 优惠券总数
     */
    public Integer getCouponSum() {
        return couponSum;
    }

    /**
     * 设置优惠券总数
     *
     * @param couponSum 优惠券总数
     */
    public void setCouponSum(Integer couponSum) {
        this.couponSum = couponSum;
    }

    /**
     * 获取折扣券时，门槛类型：20-金额门槛，30-积分门槛
     *
     * @return issue_limit_type - 折扣券时，门槛类型：20-金额门槛，30-积分门槛
     */
    public String getIssueLimitType() {
        return issueLimitType;
    }

    /**
     * 设置折扣券时，门槛类型：20-金额门槛，30-积分门槛
     *
     * @param issueLimitType 折扣券时，门槛类型：20-金额门槛，30-积分门槛
     */
    public void setIssueLimitType(String issueLimitType) {
        this.issueLimitType = issueLimitType == null ? null : issueLimitType.trim();
    }

    /**
     * 获取门槛值
     *
     * @return issue_limit_value - 门槛值
     */
    public Double getIssueLimitValue() {
        return issueLimitValue;
    }

    /**
     * 设置门槛值
     *
     * @param issueLimitValue 门槛值
     */
    public void setIssueLimitValue(Double issueLimitValue) {
        this.issueLimitValue = issueLimitValue;
    }

    /**
     * 获取活动领取类型(0:非关注后领取,1:关注后领取)
     *
     * @return fetch_type - 活动领取类型(0:非关注后领取,1:关注后领取)
     */
    public Byte getFetchType() {
        return fetchType;
    }

    /**
     * 设置活动领取类型(0:非关注后领取,1:关注后领取)
     *
     * @param fetchType 活动领取类型(0:非关注后领取,1:关注后领取)
     */
    public void setFetchType(Byte fetchType) {
        this.fetchType = fetchType;
    }

    /**
     * 获取中奖概率，取值0-100
     *
     * @return win_probability - 中奖概率，取值0-100
     */
    public Integer getWinProbability() {
        return winProbability;
    }

    /**
     * 设置中奖概率，取值0-100
     *
     * @param winProbability 中奖概率，取值0-100
     */
    public void setWinProbability(Integer winProbability) {
        this.winProbability = winProbability;
    }

    /**
     * 获取每人参与次数
     *
     * @return times_of_join - 每人参与次数
     */
    public Integer getTimesOfJoin() {
        return timesOfJoin;
    }

    /**
     * 设置每人参与次数
     *
     * @param timesOfJoin 每人参与次数
     */
    public void setTimesOfJoin(Integer timesOfJoin) {
        this.timesOfJoin = timesOfJoin;
    }

    /**
     * 获取每人中奖次数上限
     *
     * @return times_of_win - 每人中奖次数上限
     */
    public Integer getTimesOfWin() {
        return timesOfWin;
    }

    /**
     * 设置每人中奖次数上限
     *
     * @param timesOfWin 每人中奖次数上限
     */
    public void setTimesOfWin(Integer timesOfWin) {
        this.timesOfWin = timesOfWin;
    }

    /**
     * 获取优惠券描述
     *
     * @return coupon_description - 优惠券描述
     */
    public String getCouponDescription() {
        return couponDescription;
    }

    /**
     * 设置优惠券描述
     *
     * @param couponDescription 优惠券描述
     */
    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription == null ? null : couponDescription.trim();
    }

    /**
     * 获取优惠券图片
     *
     * @return coupon_pic - 优惠券图片
     */
    public String getCouponPic() {
        return couponPic;
    }

    /**
     * 设置优惠券图片
     *
     * @param couponPic 优惠券图片
     */
    public void setCouponPic(String couponPic) {
        this.couponPic = couponPic == null ? null : couponPic.trim();
    }

    /**
     * 获取优惠券背景颜色
     *
     * @return coupon_bgcolor - 优惠券背景颜色
     */
    public String getCouponBgcolor() {
        return couponBgcolor;
    }

    /**
     * 设置优惠券背景颜色
     *
     * @param couponBgcolor 优惠券背景颜色
     */
    public void setCouponBgcolor(String couponBgcolor) {
        this.couponBgcolor = couponBgcolor == null ? null : couponBgcolor.trim();
    }

    /**
     * 获取优惠券标题背景颜色
     *
     * @return coupon_title_bgcolor - 优惠券标题背景颜色
     */
    public String getCouponTitleBgcolor() {
        return couponTitleBgcolor;
    }

    /**
     * 设置优惠券标题背景颜色
     *
     * @param couponTitleBgcolor 优惠券标题背景颜色
     */
    public void setCouponTitleBgcolor(String couponTitleBgcolor) {
        this.couponTitleBgcolor = couponTitleBgcolor == null ? null : couponTitleBgcolor.trim();
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
     * 获取创建人
     *
     * @return creator_id - 创建人
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人
     *
     * @param creatorId 创建人
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取修改人
     *
     * @return modify_by_id - 修改人
     */
    public Integer getModifyById() {
        return modifyById;
    }

    /**
     * 设置修改人
     *
     * @param modifyById 修改人
     */
    public void setModifyById(Integer modifyById) {
        this.modifyById = modifyById;
    }

    /**
     * 获取修改时间
     *
     * @return modify_at - 修改时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置修改时间
     *
     * @param modifyAt 修改时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * 获取赠品信息
     *
     * @return pordInfos - 赠品信息
     */
    public String getPordinfos() {
        return pordinfos;
    }

    /**
     * 设置赠品信息
     *
     * @param pordinfos 赠品信息
     */
    public void setPordinfos(String pordinfos) {
        this.pordinfos = pordinfos == null ? null : pordinfos.trim();
    }
}