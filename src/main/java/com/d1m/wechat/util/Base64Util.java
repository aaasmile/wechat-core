package com.d1m.wechat.util;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by d1m on 2016/9/21.
 */
public class Base64Util {
    // 加密
    public static String getBase64(String str) {
        return Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
    }

    // 解密
    public static String getFromBase64(String base64String) {
        return new String(Base64.decodeBase64(base64String), StandardCharsets.UTF_8);
    }

//    public static void main(String[] args){
//        String tmp = "e30=";
//        System.out.println(Base64Util.getFromBase64(tmp));
//    }
}
