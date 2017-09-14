
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.PopupCountryAreaMapper;
import com.d1m.wechat.mapper.PopupPayConfigMapper;
import com.d1m.wechat.model.popup.dao.PopupCountryArea;
import com.d1m.wechat.model.popup.dao.PopupPayConfig;
import com.d1m.wechat.service.IPopupPayService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopupPayServiceImpl implements IPopupPayService {
    private static final Logger log = LoggerFactory.getLogger(PopupPayServiceImpl.class);
    @Autowired
    PopupPayConfigMapper popupPayConfigMapper;
    @Autowired
    PopupCountryAreaMapper popupCountryAreaMapper;

    public PopupPayServiceImpl() {
    }

    public boolean insertPopupPayConfig(PopupPayConfig model) {
        int state = this.popupPayConfigMapper.insertSelective(model);
        return state > 0;
    }

    public List<PopupCountryArea> queryCountryArea(){
        return popupCountryAreaMapper.selectAll();
    }

    public boolean updatePopupPayConfig(PopupPayConfig model) {
        int state = this.popupPayConfigMapper.updateByPrimaryKeySelective(model);
        return state > 0;
    }

    public List<PopupPayConfig> queryPopupPayConfig(int wechatId) {
        List<PopupPayConfig> results = this.popupPayConfigMapper.selectInfoByWechatId(wechatId);
        return results;
    }
}
