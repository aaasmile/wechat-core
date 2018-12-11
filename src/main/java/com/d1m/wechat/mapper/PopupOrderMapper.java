package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.PopupOrderFilter;
import com.d1m.wechat.model.popup.dao.PopupOrder;
import com.d1m.wechat.model.popup.PopupOrderList;
import com.d1m.wechat.util.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PopupOrderMapper extends MyMapper<PopupOrder> {

        List<PopupOrderList>  selectPopupOrderList(PopupOrderFilter orderFilter);
}