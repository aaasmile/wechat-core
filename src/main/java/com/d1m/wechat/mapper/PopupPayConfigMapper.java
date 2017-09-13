
package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.dao.PopupPayConfig;
import com.d1m.wechat.util.MyMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface PopupPayConfigMapper extends MyMapper<PopupPayConfig> {
    List<PopupPayConfig> selectInfoByWechatId(@Param("wechatId") Integer var1);
}
