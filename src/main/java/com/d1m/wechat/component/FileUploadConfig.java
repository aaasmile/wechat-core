package com.d1m.wechat.component;

import java.io.File;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.d1m.wechat.service.ConfigService;

@Component
@RefreshScope
@PropertySource("classpath:fileupload.properties")
public class FileUploadConfig {
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadConfig.class);
			
    private static Properties fileUploadProp;

    private static String uploadUrl;
    private static String uploadPath;

    private static ConfigService configService;
    @Autowired
    public FileUploadConfig(ConfigService configService) {
        FileUploadConfig.configService = configService;
    }

    public static String getValue(Integer wechatId, String key) {
        if(wechatId==null){
            return fileUploadProp.getProperty(key);
        }
        String value = configService.getConfigValue(wechatId,"INIT",key);
        if(StringUtils.isBlank(value)){
            value = fileUploadProp.getProperty(key);
        }
        return value;
    }

    public static String createUploadUrl(String... args) {
        //url的分隔符是/
        return uploadUrl + StringUtils.join(args, "/");
    }

    public static String createUploadPath(String... args) {
        //文件目录的分隔符跟操作系统有关, 用File.separator
        return uploadPath + StringUtils.join(args, File.separator);
    }

    public static String getUploadUrl() {
        return uploadUrl;
    }

    @Resource
    public void setFileUploadProp(Properties fileUploadProp) {
        FileUploadConfig.fileUploadProp = fileUploadProp;
    }

    @Value("${upload_url_base}")
    void setUploadUrl(String uploadUrl) {
        FileUploadConfig.uploadUrl = uploadUrl.endsWith("/") ? uploadUrl : uploadUrl + "/";
        fileUploadProp.setProperty("upload_url_base", FileUploadConfig.uploadUrl.substring(0, FileUploadConfig.uploadUrl.length() - 1));
    }

    @Value("${upload_path}")
    void setUploadPath(String uploadPath) {
        FileUploadConfig.uploadPath = uploadPath.endsWith(File.separator) ? uploadPath : uploadPath + File.separator;
        fileUploadProp.setProperty("upload_path", FileUploadConfig.uploadPath.substring(0, FileUploadConfig.uploadPath.length() - 1));
    }

    @Value("${oauth_redirect_url}")
    void setOauthRedirectUrl(String oauthRedirectUrl) {
        fileUploadProp.setProperty("oauth_redirect_url", oauthRedirectUrl);
    }

    @Value("${member_default_avatar_path}")
    void setMemberDefaultAvatarPath(String memberDefaultAvatarPath) {
        fileUploadProp.setProperty("member_default_avatar_path", memberDefaultAvatarPath);
    }

    @Value("${running.env}")
    public void setCurrentEnv(String env) {
        fileUploadProp.setProperty("running.env", env);
    }

    @Value("${httpPath}")
    public void setHttpPath(String httpPath) {
        fileUploadProp.setProperty("httpPath", httpPath);
    }
}
