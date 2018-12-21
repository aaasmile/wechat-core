package com.d1m.wechat.util;

import cn.afterturn.easypoi.handler.inter.IExcelI18nHandler;
import com.d1m.wechat.exception.WechatException;

import java.util.Optional;

/**
 * Created by jone.wang on 2018/12/5.
 * Description:
 */
public class IExcelI18nHandlerImpl implements IExcelI18nHandler {
    @Override
    public String getLocaleName(String name) {
        return Optional.ofNullable(name)
                .map(I18nUtil::getMessage)
                .orElseThrow(() -> new WechatException(Message.CSV_OR_EXCEL_PARSER_FAIL));

    }
}
