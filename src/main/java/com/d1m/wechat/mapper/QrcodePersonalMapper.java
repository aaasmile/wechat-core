package com.d1m.wechat.mapper;

import com.d1m.wechat.model.QrcodePersonal;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Var;

public interface QrcodePersonalMapper extends MyMapper<QrcodePersonal> {

    QrcodePersonal getQrcodeByScene(@Param("wechatId") Integer wechatId,@Param("scene") String scene);
}