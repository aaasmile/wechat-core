package com.d1m.wechat.model.popup;

import com.d1m.wechat.model.popup.dao.PopupGoods;
import com.d1m.wechat.model.popup.dao.PopupGoodsSku;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
public class PopupGoodsEntity {

    private PopupGoods goods;

    private List<PopupGoodsSku> liGoodsSku;
}
