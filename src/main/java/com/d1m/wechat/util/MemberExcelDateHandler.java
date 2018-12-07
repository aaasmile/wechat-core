package com.d1m.wechat.util;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.d1m.wechat.model.MemberExcel;
import com.d1m.wechat.model.enums.Language;

import java.util.Optional;

/**
 * Created by jone.wang on 2018/12/6.
 * Description: 拦截excel中表头的字段，对应转换
 */
public class MemberExcelDateHandler extends ExcelDataHandlerDefaultImpl<MemberExcel> {

    private String[] needHandlerFields = new String[]{
            "Subscribe Status", "Subscribe Scene", "Language", "语言", "绑定性别", "Bind Gender",
            "Gender", "Binding Status", "绑定状态", "性别", "关注状态", "渠道来源", "绑定市", "Bind City",
            "绑定省", "Bind Province", "绑定区", "Bind City", "绑定电话", "Bind Mobile", "绑定生日", "Bind Birthday"
    };


    @Override
    public String[] getNeedHandlerFields() {
        return needHandlerFields;
    }

    @Override
    public Object exportHandler(MemberExcel obj, String name, Object value) {

        return Optional.ofNullable(value).map(v -> {
            switch (name) {
                case "Subscribe Status":
                case "关注状态":
                    return obj.getSubscribeStatus() == null ? I18nUtil.getMessage("unsubscribe") : obj.getSubscribeStatus() == 1 ? I18nUtil.getMessage("subscribe") : I18nUtil.getMessage("cancel.subscribe");
                case "Subscribe Scene":
                case "渠道来源":
                    return obj.getSubscribeScene().getValue();
                case "gender":
                case "性别":
                    return obj.getGender().getName();
                case "Language":
                case "语言":
                    return Optional.ofNullable(Language.getByValue(obj.getLang())).map(Language::getName).orElse(Language.zh_CN.getName());
                case "Bind Gender":
                    return obj.getBindGender().getName();
                case "Binding Status":
                case "绑定状态":
                    return obj.getBindStatus().getName();
                default:
                    return value;
            }
        }).orElse(null);

    }
}
