package com.d1m.wechat.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeiXinUtils {

	private static Logger log = LoggerFactory.getLogger(WeiXinUtils.class);

	public static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String createNoncestr(int length) {
		String chars = RANDOM_STR;
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String createNoncestr() {
		String chars = RANDOM_STR;
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static Map<String, Object> getJsSignMap(String jsApiTicket,
			String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		String nonce_str = createNoncestr();
		String timestamp = getTimeStamp();
		String str = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		String signature = DigestUtils.shaHex(str);
		map.put("url", url);
		map.put("jsapi_ticket", jsApiTicket);
		map.put("nonceStr", nonce_str);
		map.put("timestamp", timestamp);
		map.put("signature", signature);
		return map;
	}

    public static String getSignature(String... params) {
        Arrays.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return DigestUtils.shaHex(StringUtils.join(params));
    }

    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        return signature != null && signature.equals(getSignature(timestamp, nonce, token));
    }

    public static String UTF_8 = "UTF-8";
    public static Charset CHARSET_UTF_8 = Charset.forName(UTF_8);

    public static String decryptMsg(String encodingAesKey, String encryptText, String appid) {
        byte[] original = decrypt(encodingAesKey, encryptText);
        // 分离16位随机字符串,网络字节序和AppId
        //[16,20)字节是xml长度
        int xmlLength = ByteBuffer.wrap(original, 16, 4).getInt();
        String xmlContent = new String(original, 20, xmlLength, CHARSET_UTF_8);
        String fromAppid = new String(original, 20 + xmlLength, original.length - (20 + xmlLength), CHARSET_UTF_8);

        // appid不相同的情况
        if (!fromAppid.equals(appid)) {
            throw new RuntimeException("微信消息解密失败: appid[" + fromAppid + ", " + appid + "]不匹配");
        }
        return xmlContent;
    }

    private static byte[] decrypt(String encodingAesKey, String encryptText) {
        try {
            byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(encryptText);
            byte[] original = cipher.doFinal(encrypted);
            return PKCS7Padding.unpad(original);
        } catch (Exception e) {
            log.error("微信消息解密失败", e);
            throw new RuntimeException("微信消息解密失败", e);
        }
    }

    private static String wrap(String message, String appId) {
        String randomStr = createNoncestr();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET_UTF_8);
        byte[] textBytes = message.getBytes(CHARSET_UTF_8);
        byte[] networkBytesOrder = ByteBuffer.allocate(4).putInt(textBytes.length).array();
        byte[] appidBytes = appId.getBytes(CHARSET_UTF_8);

        ByteBuffer byteBuffer = ByteBuffer.allocate(randomStrBytes.length + networkBytesOrder.length + textBytes.length + appidBytes.length);
        // randomStr + networkBytesOrder + text + appid
        byteBuffer.put(randomStrBytes);
        byteBuffer.put(networkBytesOrder);
        byteBuffer.put(textBytes);
        byteBuffer.put(appidBytes);

        return new String(byteBuffer.array(), CHARSET_UTF_8);
    }

    public static String encryptMsg(String encodingAesKey, String message, String appid, String token) {
        // 加密
        String encrypt = encrypt(encodingAesKey, wrap(message, appid));
        String timeStamp = getTimeStamp();
        String nonce = createNoncestr();
        String signature = getSignature(token, timeStamp, nonce, encrypt);
        // 生成发送的xml
        String result = generate(encrypt, signature, timeStamp, nonce);
        log.info("发送给平台的XML是: {}", result);
        return result;
    }

    /**
     * 对明文进行加密.
     *
     * @param text 需要加密的明文
     * @return 加密后base64编码的字符串
     */
    private static String encrypt(String encodingAesKey, String text) {
        byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            //补位
            byte[] padded = PKCS7Padding.pad(text.getBytes(UTF_8));
            // 加密
            byte[] encrypted = cipher.doFinal(padded);

            // 使用BASE64对加密后的字符串进行编码
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("微信消息加密失败", e);
        }
    }

    /**
     * 生成xml消息
     *
     * @param encrypt   加密后的消息密文
     * @param signature 安全签名
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return 生成的xml字符串
     */
    public static String generate(String encrypt, String signature, String timestamp, String nonce) {
        String format = "<xml><Encrypt><![CDATA[%1$s]]></Encrypt><MsgSignature><![CDATA[%2$s]]></MsgSignature><TimeStamp>%3$s</TimeStamp><Nonce><![CDATA[%4$s]]></Nonce></xml>";
        return String.format(format, encrypt, signature, timestamp, nonce);
    }

    static class PKCS7Padding {
        private static int BLOCK_SIZE = 32;

        /**
         * 获得对明文进行补位填充的字节.
         *
         * @param origin 需要进行填充补位操作的明文字节
         * @return 补齐用的字节数组
         */
        static byte[] pad(byte[] origin) {
            // 计算需要填充的位数
            int amountToPad = BLOCK_SIZE - (origin.length % BLOCK_SIZE);
            if (amountToPad == 0) {
                amountToPad = BLOCK_SIZE;
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate(origin.length + amountToPad).put(origin);
            for (int index = 0; index < amountToPad; index++) {
                byteBuffer.put((byte) amountToPad);
            }
            return byteBuffer.array();
        }

        /**
         * 删除解密后明文的补位字符
         *
         * @param decrypted 解密后的明文
         * @return 删除补位字符后的明文
         */
        static byte[] unpad(byte[] decrypted) {
            int pad = (int) decrypted[decrypted.length - 1];
            if (pad < 1 || pad > BLOCK_SIZE) {
                pad = 0;
            }
            return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
        }

    }

}
