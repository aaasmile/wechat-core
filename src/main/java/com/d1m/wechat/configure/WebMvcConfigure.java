package com.d1m.wechat.configure;

import com.d1m.wechat.web.AnnotationMappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebappConfig
 *
 * @author f0rb on 2017-04-12.
 */
@Configuration
@Import(SwaggerConfigure.class)
public class WebMvcConfigure extends WebMvcConfigurerAdapter {

    //public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //    super.configureMessageConverters(converters);
    //    FastJsonHttpMessageConverter4 fastJsonConverter = new FastJsonHttpMessageConverter4();
    //
    //    fastJsonConverter.setSupportedMediaTypes(Arrays.asList(
    //            MediaType.APPLICATION_JSON_UTF8,
    //            new MediaType("text/html", "charset=UTF-8")
    //    ));
    //    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    //    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    //    fastJsonConfig.setFeatures(Feature.DisableCircularReferenceDetect);
    //    fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.WriteDateUseDateFormat);
    //    fastJsonConverter.setFastJsonConfig(fastJsonConfig);
    //    converters.add(fastJsonConverter);
    //
    //    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    //    stringConverter.setSupportedMediaTypes(Arrays.asList(
    //            new MediaType("text/plain", "charset=UTF-8")
    //    ));
    //    converters.add(stringConverter);
    //
    //}
    //

    /**
     * 请求拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LocaleChangeInterceptor());
    }
    //
    //@Bean
    //public ServletContextListener configCleanupContextListener() {
    //    return new CleanupContextListener();
    //}

    /**
     * 资源文件服务
     *
     * @param registry
     * @author Owen Jia
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");


        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public AnnotationMappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
            ObjectMapper objectMapper) {
        final AnnotationMappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new AnnotationMappingJackson2HttpMessageConverter(objectMapper);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(new MediaType("application", "*+json")));
        return mappingJackson2HttpMessageConverter;
    }
}
