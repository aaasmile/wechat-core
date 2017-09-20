
package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.PopupPayConfig;
import com.d1m.wechat.util.MyMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PopupPayConfigMapper extends MyMapper<PopupPayConfig> {
    List<PopupPayConfig> selectInfoByWechatId(@Param("wechatId") Integer var1);
}
