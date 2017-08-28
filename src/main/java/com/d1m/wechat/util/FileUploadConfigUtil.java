package com.d1m.wechat.util;


import com.d1m.wechat.component.FileUploadConfig;

public class FileUploadConfigUtil {

	private static FileUploadConfigUtil CONFIG = null;

	public static FileUploadConfigUtil getInstance() {
        if (CONFIG == null) {
            synchronized (FileUploadConfigUtil.class) {
                if (CONFIG == null) {
                    CONFIG = new FileUploadConfigUtil();
                }
            }
        }
		return (CONFIG);
	}

	public String getValue(Integer wechatId, String key) {
        return FileUploadConfig.getValue(wechatId, key);
    }

}
