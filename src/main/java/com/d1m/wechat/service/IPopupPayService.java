
package com.d1m.wechat.service;

import com.d1m.wechat.model.popup.dao.PopupCountryAreaDao;
import com.d1m.wechat.model.popup.dao.PopupPayConfigDao;

import java.util.List;

public interface IPopupPayService {
    boolean insertPopupPayConfig(PopupPayConfigDao var1);

    boolean updatePopupPayConfig(PopupPayConfigDao var1);

    List<PopupPayConfigDao> queryPopupPayConfig(int var1);

    List<PopupCountryAreaDao> queryCountryArea();
}
