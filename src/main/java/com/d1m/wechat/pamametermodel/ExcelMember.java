package com.d1m.wechat.pamametermodel;

import com.d1m.wechat.model.enums.Sex;
import com.d1m.wechat.util.I18nUtil;
import org.apache.poi.ss.usermodel.Row;

import java.util.Locale;

public class ExcelMember {

    private String nickname, gender, mobile, province, city, subscribe, subscribeat, bind, message_sent, tags, openid,
            unbund_at, created_at, created, bindat, subscribe_scene, qr_scene, qr_scene_str, unsubscribeat, unionid;

    private String lang;

    private String bindProvince;

    private String bindCity;

    private String bindCounty;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribeat() {
        return subscribeat;
    }

    public void setSubscribeat(String subscribeat) {
        this.subscribeat = subscribeat;
    }

    public String getUnsubscribeat() {
        return unsubscribeat;
    }

    public void setUnsubscribeat(String unsubscribeat) {
        this.unsubscribeat = unsubscribeat;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public String getMessage_sent() {
        return message_sent;
    }

    public void setMessage_sent(String message_sent) {
        this.message_sent = message_sent;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnbund_at() {
        return unbund_at;
    }

    public void setUnbund_at(String unbund_at) {
        this.unbund_at = unbund_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBindat() {
        return bindat;
    }

    public void setBindat(String bindat) {
        this.bindat = bindat;
    }

    public String getSubscribe_scene() {
        return subscribe_scene;
    }

    public void setSubscribe_scene(String subscribe_scene) {
        this.subscribe_scene = subscribe_scene;
    }

    public String getQr_scene() {
        return qr_scene;
    }

    public void setQr_scene(String qr_scene) {
        this.qr_scene = qr_scene;
    }

    public String getQr_scene_str() {
        return qr_scene_str;
    }

    public void setQr_scene_str(String qr_scene_str) {
        this.qr_scene_str = qr_scene_str;
    }

    @Override
    public String toString() {
        return "ExcelMember [nickname=" + nickname + ", gender=" + gender + ", mobile=" + mobile + ", province="
                + province + ", city=" + city + ", subscribe=" + subscribe + ", subscribeat=" + subscribeat + ", bind="
                + bind + ", message_sent=" + message_sent + ", tags=" + tags + ", openid=" + openid + ", unbund_at="
                + unbund_at + ", created_at=" + created_at + ", created=" + created + ", bindat=" + bindat
                + ", subscribe_scene=" + subscribe_scene + ", qr_scene=" + qr_scene + ", qr_scene_str=" + qr_scene_str
                + ", unsubscribeat=" + unsubscribeat + ", unionid=" + unionid + "]";
    }


    public static final String[] keys = {"no", "nickname", "gender", "mobile", "province", "city", "subscribe.status",
            "subscribe.at", "unsubscribe.at", "subscribe.scene", "customer.service.open.id", "unionid",
            "tag", "lang", "group.message.sent", "bind.status", "bind.at", "bind.province", "bind.city", "bind.county",
            "bind.mobile", "bind.gender", "bind.birthday", "remarks"};

    public static void fillTitles(Row titleRow, Locale locale) {
        String[] titleVal = I18nUtil.getMessage(keys, locale);
        for (int i = 0; i < titleVal.length; i++) {
            titleRow.createCell(i).setCellValue(titleVal[i]);
        }
    }

    /**
     * 全量导出
     *
     * @param dataRow
     * @param locale
     */
    public void fillRows(Row dataRow, Locale locale) {
        String attentionStatus = "subscribe";
        Byte sex = getGender() != null ? Byte.valueOf(gender) : Byte.valueOf("1");
        boolean isSubscribe = "1".equals(subscribe);
        int batchsendMonth = message_sent != null ? Integer.valueOf(message_sent) : 0;

        dataRow.createCell(1).setCellValue(nickname);
        if (sex != null) {
            dataRow.createCell(2).setCellValue(I18nUtil.getMessage(Sex.getByValue(sex).name().toLowerCase(), locale));
        }
        dataRow.createCell(3).setCellValue(mobile);
        dataRow.createCell(4).setCellValue(province);
        dataRow.createCell(5).setCellValue(city);

        if (!isSubscribe) {
            if (unsubscribeat != null) {
                attentionStatus = "cancel.subscribe";
            } else {
                attentionStatus = "unsubscribe";
            }
        }
        dataRow.createCell(6).setCellValue(I18nUtil.getMessage(attentionStatus, locale));

        if ("1".equals(bind)) {
            dataRow.createCell(7).setCellValue(I18nUtil.getMessage("bind", locale));
        } else {
            dataRow.createCell(7).setCellValue(I18nUtil.getMessage("unbind", locale));
        }
        if (subscribeat != null) {
            dataRow.createCell(8).setCellValue(subscribeat);
        }
        dataRow.createCell(9).setCellValue(batchsendMonth);
        dataRow.createCell(10).setCellValue(tags);
        dataRow.createCell(11).setCellValue(openid);
        if (bindat != null) {
            dataRow.createCell(12).setCellValue(bindat);
        }
        if (unsubscribeat != null) {
            dataRow.createCell(13).setCellValue(unsubscribeat);
        }
        if (unionid != null) {
            dataRow.createCell(14).setCellValue(unionid);
        }
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUnionid() {
        return unionid;
    }
}
