package com.d1m.wechat.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class Security {

    /**
     * 数据加密
     *
     * @param input 明文输入
     * @param key   加密key
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
            log.error(e.getMessage(), e);
            return null;
        }
        return new String(Base64.encodeBase64(crypted));
    }

    /**
     * 数据解密
     *
     * @param input 待解密数据
     * @param key   密钥
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
            log.error(e.getMessage(), e);
            return null;
        }
        return new String(output);
    }

    public static void main(String[] args) {
        String key = "national";
        String data = "{\"instno\":\"1469159232347\"}";
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        System.out.println(Security.encrypt(data, key));
        System.out.println(Security.decrypt("Qrb7tbmaC+R0J4Aqnd+5j61BqU+TSsLKRe6vXt+VMC2rCwrmj3Kld+WxnNb5FKvhlNB9lhsZ6hnb916MNTVxM2uPGRym11Jb3c5CGhwVwZ9hlTo/77bePz68sLKwMbnTTyO+w1d12elj7rU4JF0i7ht3ChohWjqJHY0KIRxXiYTyKcMAyzOG6Nz3s8GEsjc0x9zkXm7c17rQDEY0P8sfYqLdCCt+kIMCD1CiUi/LtyyJWnxKr7d+82UmEY7FLRLemVteXFdyiWbdIeVYgesu9FT9MC9Us9pmJgioMjzcw4r0q2oCgMPekDTFZ/CgMy5Hm7jO78o/cVv2yj839RlyoJTohIegfk/TJL9iG5YJZA0+mzpXNYABmE+8yXjuPmVcKURl1yJ3wvt+AkZSYUaKWgl5cM6ChYrpXWoP0tyQZK2HQol8I83Il44glelW0hH2WLJ8GIbLDofdI8SQ0UrqKJn+KbtWCY3rGRJJHjFUKJ9VNN4lfmvqQE87XnSkAZtD", key));

    }
}
