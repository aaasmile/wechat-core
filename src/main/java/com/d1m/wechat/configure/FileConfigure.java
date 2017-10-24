package com.d1m.wechat.configure;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * FileConfigure
 *
 * @author f0rb on 2017-04-13.
 */
@Configuration
public class FileConfigure {

    /**
     * 配置文件上传相关参数的文件
     *
     * @return Properties
     */
    @Bean
    public Properties fileUploadProp() throws IOException {
        return PropertiesLoaderUtils.loadProperties(new ClassPathResource("/fileupload.properties"));
    }
}
