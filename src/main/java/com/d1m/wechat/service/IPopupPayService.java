
package com.d1m.wechat.service;

import com.d1m.wechat.model.popup.PopupCountryArea;
import com.d1m.wechat.model.popup.PopupPayConfig;
import java.util.List;

public interface IPopupPayService {
    boolean insertPopupPayConfig(PopupPayConfig var1);

    boolean updatePopupPayConfig(PopupPayConfig var1);

    List<PopupPayConfig> queryPopupPayConfig(int var1);

    List<PopupCountryArea> queryCountryArea();
}
