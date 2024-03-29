package com.d1m.wechat.util;

import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * EncryptUtil
 *
 */
public class EncryptUtil {

    private static final String DEFAULT_CRYPT_KEY = "D1MEncrypt";
    private static final String DES = "DES";
    private static final String DES_ECB = "DES";

    /**
     * 使用DEFAULT_CRYPT_KEY进行数据加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encrypt(String data) {
        return encrypt(data, DEFAULT_CRYPT_KEY);
    }

    /**
     * 使用DEFAULT_CRYPT_KEY进行数据解密密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decrypt(String data) throws Exception{
        return decrypt(data, DEFAULT_CRYPT_KEY);
    }

    /**
     * 数据加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static String encrypt(String data, String key) {
        key = StringUtils.rightPad(key, 8);// key至少为8位
        return byte2hex(encrypt(data.getBytes(), key.getBytes()));
    }

    /**
     * 数据解密
     *
     * @param data 密文
     * @param key  密钥
     * @return 明文
     */
    public static String decrypt(String data, String key) throws Exception{
        return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
    }

    /**
     * 加密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     */
    private static byte[] encrypt(byte[] src, byte[] key) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密匙数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES_ECB);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError(e);
        }
    }

    /**
     * 解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     */
    private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES_ECB);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            // 现在，获取数据并解密
            // 正式执行解密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 二进制转字符串
     *
     * @param b byte array
     * @return string
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        for (int n = 0; b != null && n < b.length; n++) {
            String temp = Integer.toHexString(b[n] & 0XFF);
            if (temp.length() == 1) {
                hs.append("0");
            }
            hs.append(temp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

}
