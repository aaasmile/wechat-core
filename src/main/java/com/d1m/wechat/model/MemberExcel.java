package com.d1m.wechat.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.d1m.wechat.domain.enumeration.MemberSubscribeScene;
import com.d1m.wechat.model.enums.MemberProfileStatus;
import com.d1m.wechat.model.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by jone.wang on 2018/12/5.
 * Description:
 */
@Getter
@Setter
public class MemberExcel {

    private Integer memberId;

    @Excel(name = "no", type = 10, orderNum = "0")
    private Integer id;

    @Excel(name = "nickname", orderNum = "1")
    private String nickname;

    @Excel(name = "gender", orderNum = "2")
    private Sex gender;

    @Excel(name = "bind.mobile", width = 12, orderNum = "22")
    private String mobile;

    @Excel(name = "province", orderNum = "4")
    private String province;

    @Excel(name = "city", orderNum = "5")
    private String city;

    @Excel(name = "subscribe.status", orderNum = "6")
    private Integer subscribeStatus;

    @Excel(name = "subscribe.at", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "7")
    private Date subscribeAt;

    @Excel(name = "unsubscribe.at", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "8")
    private Date unsubscribeAt;

    @Excel(name = "subscribe.scene", width = 14, orderNum = "9")
    private MemberSubscribeScene subscribeScene;

    @Excel(name = "customer.service.open.id", width = 32, orderNum = "10")
    private String openId;

    @Excel(name = "unionid", width = 32, orderNum = "11")
    private String unionId;

    @Excel(name = "tag", width = 22, orderNum = "12")
    private String tag;

    @Excel(name = "lang", orderNum = "13")
    private Byte lang;

    @Excel(name = "group.message.sent", orderNum = "14")
    private Integer groupMessageSent;

    @Excel(name = "bind.status", orderNum = "15")
    private MemberProfileStatus bindStatus;

    @Excel(name = "bind.at", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "16")
    private Date bindAt;

    @Excel(name = "bind.name", width = 20, orderNum = "17")
    private String bindName;

    @Excel(name = "bind.province", orderNum = "18")
    private String bindProvince;

    @Excel(name = "bind.city", orderNum = "19")
    private String bindCity;

    @Excel(name = "bind.county", orderNum = "20")
    private String bindCounty;

    @Excel(name = "bind.address", orderNum = "21")
    private String bindAddress;

    @Excel(name = "mobile", orderNum = "3")
    private String bindMobile;

    @Excel(name = "bind.gender", orderNum = "23")
    private Sex bindGender;

    @Excel(name = "bind.birthday", width = 20, exportFormat = "yyyy-MM-dd", orderNum = "24")
    private Date bindBirthday;

    @Excel(name = "remarks", orderNum = "25")
    private String remarks;
}
