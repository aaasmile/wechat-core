package com.d1m.wechat.service.impl;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.InterfaceSecretMapper;
import com.d1m.wechat.model.InterfaceSecret;
import com.d1m.wechat.model.enums.InterfaceType;
import com.d1m.wechat.service.InterfaceSecretService;
import com.d1m.wechat.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class InterfaceSecretSrviceImpl implements InterfaceSecretService {

    private Logger log = LoggerFactory.getLogger(InterfaceSecretSrviceImpl.class);

    @Autowired
    private InterfaceSecretMapper interfaceSecretMapper;

    /**
     * 生成秘钥前需要查询是否存在相同的秘钥
     * @param brand
     * @param type
     */
    @Override
    public void createKeyAndSecret(String brand, InterfaceType type ,String name) throws WechatException {
        final InterfaceSecret is = new InterfaceSecret();
        is.setBrandId(Integer.parseInt(brand));
        is.setType(type);
        final int count = interfaceSecretMapper.selectCount(is);
        if(count >0){
            log.info("KEY和秘钥已经存在");
            return ;
        }
        //如果不存在需要生成
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        String oldSecret = MD5.MD5Encode(key + name);
        final String secret = oldSecret.substring(0, 16);
        is.setKey(key);
        is.setSecret(secret);
        is.setCreateAt(new Date());
        log.debug("添加的KEY:{}和Secret:{}",key ,secret);
        interfaceSecretMapper.insertSelective(is);



    }
}
