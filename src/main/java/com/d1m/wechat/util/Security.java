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
	
	public static String encrypt(String input, String key) {
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new String(Base64.encodeBase64(crypted));
	}

	public static String decrypt(String input, String key) {
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new String(output);
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String data = "hCitdHPhwKNRnI5bNl3ZiQzF3N8Mj%2FIBFaz2TYQgGLinaEf7Im0LEh%2BAnUsKk9cJbLRtzuxzkJU1dzTja7TTVubw6QYOeLwxNUkzQfTxgY%2FTPv19FqkWJ6wUlMFKLrIlEd%2F%2BlAvLIjHA3DVxOJcs%2F7OrGnVmyyXZvmJnqftMS9Hk9K9V3o7%2BUsNCRuz4ST8FZYbm8oxgA3%2FgRcIJ26WSLriXQU9W6IKDfGC8S6lEgyaijVAnfcflv0nVKy05zKh16jK%2BhGnDTjZ%2FXoSJXlL9rBjiG2zWSCHCvJDwC4TTGWU%3D";
		String data = "oWjsd+bk5/xTr+tmA4xMtg==";
		System.out.println(Security.decrypt(data, "aes_key_d1m_2018"));
	}
}