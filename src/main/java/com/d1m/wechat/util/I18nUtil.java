package com.d1m.wechat.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.*;

public class I18nUtil {

    public static String getMessage(String key, Locale locale) {
        String value = AppContextUtils.getContext().getMessage(key, null, locale);
        if (Objects.equals(value, key) && key != null && key.contains("_")) {//尝试将key的_转为.再找一次
            value = AppContextUtils.getContext().getMessage(key.replaceAll("_", "."), null, locale);
        }
        return value == null ? key : value;
    }

    public static String getMessage(String key) {
        return Optional.ofNullable(LocaleContextUtils.getLocale())
                .map(locale -> getMessage(key, locale))
                .orElseGet(() -> getMessage(key, Locale.CHINA));
    }

    public static List<String> getMessage(List<String> keys, Locale locale) {
        List<String> values = new ArrayList<String>();
        for (String key : keys) {
            values.add(getMessage(key, locale));
        }
        return values;
    }

    public static String[] getMessage(String[] keys, Locale locale) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        String[] values = new String[keys.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = getMessage(keys[i], locale);
        }
        return values;
    }

    public static void main(String[] args) throws Exception {
        String content = "<p><iframe class=\"video_iframe\" data-vidtype=\"2\" data-cover=\"http%3A%2F%2Fvpic.video.qq.com%2F93997838%2Fb1356o225qd.png\" allowfullscreen=\"\" frameborder=\"0\" data-ratio=\"0.5\" data-w=\"432\" data-src=\"https://v.qq.com/iframe/preview.html?width=500&amp;height=375&amp;auto=0&amp;vid=b1356o225qd\"></iframe></p><p><br  /></p>";
        String uploadPath = "/data/attached/dev";
        String uploadUrlBase = "http://dev.wechat.d1m.cn/attached/dev";
        String str = replacePicUrlFromContent(content, uploadPath, uploadUrlBase);
        System.out.println(str);
    }

    public static String replacePicUrlFromContent(String content, String uploadPath, String uploadUrlBase) {
        String picDomain = "https://";
        String[] list = content.split(picDomain);
        StringBuffer contentB = new StringBuffer();
        for(int i = 0; i < list.length; i++) {
            String oldContent = list[i];
            if(oldContent.indexOf("mmbiz.qpic.cn") < 0) {
                if(oldContent.indexOf(".com/") > -1) {
                    oldContent = picDomain + oldContent;
                }
                contentB.append(oldContent);
                continue;
            }
            oldContent = picDomain + oldContent;
            String oldUrl = oldContent.substring(0, oldContent.indexOf("\""));
            System.out.println("oldUrl---" + oldUrl);
            try {
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpeg";
                String newUrl = uploadUrlBase + "/" + fileName;
                System.out.println("newUrl---" + newUrl);
                URL source = new URL(oldUrl);
                File file = new File(uploadPath + "/" + fileName);
                FileUtils.copyURLToFile(source, file);
                contentB.append(oldContent.replace(oldUrl, newUrl));
            } catch(Exception e) {
                contentB.append(oldContent);
            }
        }
        return contentB.toString();
    }

}
