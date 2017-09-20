package com.d1m.wechat.util;


import com.d1m.wechat.component.FileUploadConfig;

import java.util.ResourceBundle;

public class FileUploadConfigUtil {

	private static FileUploadConfigUtil CONFIG = null;
    private static ResourceBundle RB = null;
    private static final String CONFIG_FILE = "fileupload";

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

    private FileUploadConfigUtil() {
        RB = ResourceBundle.getBundle(CONFIG_FILE);
    }

    public String getValue(Integer wechatId, String key) {
        return FileUploadConfig.getValue(wechatId, key);
    }

    public String getValue(String key) {
        return (RB.getString(key));
    }

}
