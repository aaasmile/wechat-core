package com.d1m.wechat.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Security {
	
	private static final Logger log = LoggerFactory.getLogger(Security.class);

    /**
     * 数据加密
     *
     * @param input 明文输入
     * @param key   加密key  长度只能是【16, 24, 32】其中一个
     * @return 密文
     */
    public static String encrypt(String input, String key) {
        byte[] crypted;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            log.error("加密失败", e);
            return null;
        }
        return new String(Base64.encodeBase64(crypted));
    }

    /**
     * 数据解密
     *
     * @param input 待解密数据
     * @param key   解密key  长度只能是【16, 24, 32】其中一个
     * @return 明文
     */
    public static String decrypt(String input, String key) {
        byte[] output;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
        } catch (Exception e) {
            log.error("解密失败", e);
            return null;
        }
        return new String(output);
    }

}