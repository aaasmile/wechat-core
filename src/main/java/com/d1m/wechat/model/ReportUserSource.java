package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "report_user_source")
public class ReportUserSource {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 日期
     */
    private String date;

    /**
     * 其他合计-新增的用户数量
     */
    @Column(name = "new_user_0")
    private Integer newUser0;

    /**
     * 其他合计-取消关注的用户数量
     */
    @Column(name = "cancel_user_0")
    private Integer cancelUser0;

    /**
     * 公众号搜索-新增的用户数量
     */
    @Column(name = "new_user_1")
    private Integer newUser1;

    /**
     * 公众号搜索-取消关注的用户数量
     */
    @Column(name = "cancel_user_1")
    private Integer cancelUser1;

    /**
     * 名片分享-新增的用户数量
     */
    @Column(name = "new_user_17")
    private Integer newUser17;

    /**
     * 名片分享-取消关注的用户数量
     */
    @Column(name = "cancel_user_17")
    private Integer cancelUser17;

    /**
     * 扫描二维码-新增的用户数量
     */
    @Column(name = "new_user_30")
    private Integer newUser30;

    /**
     * 扫描二维码-取消关注的用户数量
     */
    @Column(name = "cancel_user_30")
    private Integer cancelUser30;

    /**
     * 图文页右上角菜单-新增的用户数量
     */
    @Column(name = "new_user_43")
    private Integer newUser43;

    /**
     * 图文页右上角菜单-取消关注的用户数量
     */
    @Column(name = "cancel_user_43")
    private Integer cancelUser43;

    /**
     * 支付后关注（在支付完成页）-新增的用户数量
     */
    @Column(name = "new_user_51")
    private Integer newUser51;

    /**
     * 支付后关注（在支付完成页）-取消关注的用户数量
     */
    @Column(name = "cancel_user_51")
    private Integer cancelUser51;

    /**
     * 图文页内公众号名称-新增的用户数量
     */
    @Column(name = "new_user_57")
    private Integer newUser57;

    /**
     * 图文页内公众号名称-取消关注的用户数量
     */
    @Column(name = "cancel_user_57")
    private Integer cancelUser57;

    /**
     * 公众号文章广告-新增的用户数量
     */
    @Column(name = "new_user_75")
    private Integer newUser75;

    /**
     * 公众号文章广告-取消关注的用户数量
     */
    @Column(name = "cancel_user_75")
    private Integer cancelUser75;

    /**
     * 朋友圈广告-新增的用户数量
     */
    @Column(name = "new_user_78")
    private Integer newUser78;

    /**
     * 朋友圈广告-取消关注的用户数量
     */
    @Column(name = "cancel_user_78")
    private Integer cancelUser78;

    /**
     * 总用户数量
     */
    @Column(name = "cumulate_user")
    private Integer cumulateUser;

    /**
     * 所属公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

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
     * 获取日期
     *
     * @return date - 日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置日期
     *
     * @param date 日期
     */
    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    /**
     * 获取其他合计-新增的用户数量
     *
     * @return new_user_0 - 其他合计-新增的用户数量
     */
    public Integer getNewUser0() {
        return newUser0;
    }

    /**
     * 设置其他合计-新增的用户数量
     *
     * @param newUser0 其他合计-新增的用户数量
     */
    public void setNewUser0(Integer newUser0) {
        this.newUser0 = newUser0;
    }

    /**
     * 获取其他合计-取消关注的用户数量
     *
     * @return cancel_user_0 - 其他合计-取消关注的用户数量
     */
    public Integer getCancelUser0() {
        return cancelUser0;
    }

    /**
     * 设置其他合计-取消关注的用户数量
     *
     * @param cancelUser0 其他合计-取消关注的用户数量
     */
    public void setCancelUser0(Integer cancelUser0) {
        this.cancelUser0 = cancelUser0;
    }

    /**
     * 获取公众号搜索-新增的用户数量
     *
     * @return new_user_1 - 公众号搜索-新增的用户数量
     */
    public Integer getNewUser1() {
        return newUser1;
    }

    /**
     * 设置公众号搜索-新增的用户数量
     *
     * @param newUser1 公众号搜索-新增的用户数量
     */
    public void setNewUser1(Integer newUser1) {
        this.newUser1 = newUser1;
    }

    /**
     * 获取公众号搜索-取消关注的用户数量
     *
     * @return cancel_user_1 - 公众号搜索-取消关注的用户数量
     */
    public Integer getCancelUser1() {
        return cancelUser1;
    }

    /**
     * 设置公众号搜索-取消关注的用户数量
     *
     * @param cancelUser1 公众号搜索-取消关注的用户数量
     */
    public void setCancelUser1(Integer cancelUser1) {
        this.cancelUser1 = cancelUser1;
    }

    /**
     * 获取名片分享-新增的用户数量
     *
     * @return new_user_17 - 名片分享-新增的用户数量
     */
    public Integer getNewUser17() {
        return newUser17;
    }

    /**
     * 设置名片分享-新增的用户数量
     *
     * @param newUser17 名片分享-新增的用户数量
     */
    public void setNewUser17(Integer newUser17) {
        this.newUser17 = newUser17;
    }

    /**
     * 获取名片分享-取消关注的用户数量
     *
     * @return cancel_user_17 - 名片分享-取消关注的用户数量
     */
    public Integer getCancelUser17() {
        return cancelUser17;
    }

    /**
     * 设置名片分享-取消关注的用户数量
     *
     * @param cancelUser17 名片分享-取消关注的用户数量
     */
    public void setCancelUser17(Integer cancelUser17) {
        this.cancelUser17 = cancelUser17;
    }

    /**
     * 获取扫描二维码-新增的用户数量
     *
     * @return new_user_30 - 扫描二维码-新增的用户数量
     */
    public Integer getNewUser30() {
        return newUser30;
    }

    /**
     * 设置扫描二维码-新增的用户数量
     *
     * @param newUser30 扫描二维码-新增的用户数量
     */
    public void setNewUser30(Integer newUser30) {
        this.newUser30 = newUser30;
    }

    /**
     * 获取扫描二维码-取消关注的用户数量
     *
     * @return cancel_user_30 - 扫描二维码-取消关注的用户数量
     */
    public Integer getCancelUser30() {
        return cancelUser30;
    }

    /**
     * 设置扫描二维码-取消关注的用户数量
     *
     * @param cancelUser30 扫描二维码-取消关注的用户数量
     */
    public void setCancelUser30(Integer cancelUser30) {
        this.cancelUser30 = cancelUser30;
    }

    /**
     * 获取图文页右上角菜单-新增的用户数量
     *
     * @return new_user_43 - 图文页右上角菜单-新增的用户数量
     */
    public Integer getNewUser43() {
        return newUser43;
    }

    /**
     * 设置图文页右上角菜单-新增的用户数量
     *
     * @param newUser43 图文页右上角菜单-新增的用户数量
     */
    public void setNewUser43(Integer newUser43) {
        this.newUser43 = newUser43;
    }

    /**
     * 获取图文页右上角菜单-取消关注的用户数量
     *
     * @return cancel_user_43 - 图文页右上角菜单-取消关注的用户数量
     */
    public Integer getCancelUser43() {
        return cancelUser43;
    }

    /**
     * 设置图文页右上角菜单-取消关注的用户数量
     *
     * @param cancelUser43 图文页右上角菜单-取消关注的用户数量
     */
    public void setCancelUser43(Integer cancelUser43) {
        this.cancelUser43 = cancelUser43;
    }

    /**
     * 获取支付后关注（在支付完成页）-新增的用户数量
     *
     * @return new_user_51 - 支付后关注（在支付完成页）-新增的用户数量
     */
    public Integer getNewUser51() {
        return newUser51;
    }

    /**
     * 设置支付后关注（在支付完成页）-新增的用户数量
     *
     * @param newUser51 支付后关注（在支付完成页）-新增的用户数量
     */
    public void setNewUser51(Integer newUser51) {
        this.newUser51 = newUser51;
    }

    /**
     * 获取支付后关注（在支付完成页）-取消关注的用户数量
     *
     * @return cancel_user_51 - 支付后关注（在支付完成页）-取消关注的用户数量
     */
    public Integer getCancelUser51() {
        return cancelUser51;
    }

    /**
     * 设置支付后关注（在支付完成页）-取消关注的用户数量
     *
     * @param cancelUser51 支付后关注（在支付完成页）-取消关注的用户数量
     */
    public void setCancelUser51(Integer cancelUser51) {
        this.cancelUser51 = cancelUser51;
    }

    /**
     * 获取图文页内公众号名称-新增的用户数量
     *
     * @return new_user_57 - 图文页内公众号名称-新增的用户数量
     */
    public Integer getNewUser57() {
        return newUser57;
    }

    /**
     * 设置图文页内公众号名称-新增的用户数量
     *
     * @param newUser57 图文页内公众号名称-新增的用户数量
     */
    public void setNewUser57(Integer newUser57) {
        this.newUser57 = newUser57;
    }

    /**
     * 获取图文页内公众号名称-取消关注的用户数量
     *
     * @return cancel_user_57 - 图文页内公众号名称-取消关注的用户数量
     */
    public Integer getCancelUser57() {
        return cancelUser57;
    }

    /**
     * 设置图文页内公众号名称-取消关注的用户数量
     *
     * @param cancelUser57 图文页内公众号名称-取消关注的用户数量
     */
    public void setCancelUser57(Integer cancelUser57) {
        this.cancelUser57 = cancelUser57;
    }

    /**
     * 获取公众号文章广告-新增的用户数量
     *
     * @return new_user_75 - 公众号文章广告-新增的用户数量
     */
    public Integer getNewUser75() {
        return newUser75;
    }

    /**
     * 设置公众号文章广告-新增的用户数量
     *
     * @param newUser75 公众号文章广告-新增的用户数量
     */
    public void setNewUser75(Integer newUser75) {
        this.newUser75 = newUser75;
    }

    /**
     * 获取公众号文章广告-取消关注的用户数量
     *
     * @return cancel_user_75 - 公众号文章广告-取消关注的用户数量
     */
    public Integer getCancelUser75() {
        return cancelUser75;
    }

    /**
     * 设置公众号文章广告-取消关注的用户数量
     *
     * @param cancelUser75 公众号文章广告-取消关注的用户数量
     */
    public void setCancelUser75(Integer cancelUser75) {
        this.cancelUser75 = cancelUser75;
    }

    /**
     * 获取朋友圈广告-新增的用户数量
     *
     * @return new_user_78 - 朋友圈广告-新增的用户数量
     */
    public Integer getNewUser78() {
        return newUser78;
    }

    /**
     * 设置朋友圈广告-新增的用户数量
     *
     * @param newUser78 朋友圈广告-新增的用户数量
     */
    public void setNewUser78(Integer newUser78) {
        this.newUser78 = newUser78;
    }

    /**
     * 获取朋友圈广告-取消关注的用户数量
     *
     * @return cancel_user_78 - 朋友圈广告-取消关注的用户数量
     */
    public Integer getCancelUser78() {
        return cancelUser78;
    }

    /**
     * 设置朋友圈广告-取消关注的用户数量
     *
     * @param cancelUser78 朋友圈广告-取消关注的用户数量
     */
    public void setCancelUser78(Integer cancelUser78) {
        this.cancelUser78 = cancelUser78;
    }

    /**
     * 获取总用户数量
     *
     * @return cumulate_user - 总用户数量
     */
    public Integer getCumulateUser() {
        return cumulateUser;
    }

    /**
     * 设置总用户数量
     *
     * @param cumulateUser 总用户数量
     */
    public void setCumulateUser(Integer cumulateUser) {
        this.cumulateUser = cumulateUser;
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
     * 设置所属公众号ID
     *
     * @param wechatId 所属公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
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
}