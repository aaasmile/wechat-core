package com.d1m.wechat.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfigure
 *
 * @author f0rb on 2017-08-07.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigure /*extends WebMvcConfigurerAdapter implements EnvironmentAware */{
	private static final Logger log = LoggerFactory.getLogger(SwaggerConfigure.class);
    private ApiInfo getApiInfo() {
	    return new ApiInfoBuilder()
	            .title("wechat-core APIs")
	            .termsOfServiceUrl("fake.wechat.d1m.cn")
	            .contact(new Contact("d1m","http://www.d1m.com","fake@d1m.com"))
	            .version("v0.0.1")
	            .description("微信后台管理模块")
	            .build();
    }
	
    @Bean
    public Docket swaggerSpringfox() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .groupName("test")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.d1m.wechat.controller.wx"))
//                .paths(regex("/.*"))
//                .paths(regex("/test/.*")) // and by paths
                .build();
//                .globalOperationParameters(setHeaderToken());
//                .security/Schemes(Collections.singletonList(oauth()));
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return swaggerSpringMvcPlugin;
    }
    
    
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
// 
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }
    
//	@Override
//	public void setEnvironment(Environment environment) {
//		 this.propertyResolver = new RelaxedPropertyResolver(environment,
//	                "swagger.");
//	}
//	
//	 private RelaxedPropertyResolver propertyResolver;
    
//    @Bean
//    SecurityScheme oauth() {
//        return new OAuthBuilder()
////                .name("OAuth2")
//        		.name("Shiro")
//                .scopes(scopes())
//                .grantTypes(grantTypes())
//                .build();
//    }
    
//	@Bean
//	List<GrantType> grantTypes() {
//		List<GrantType> grantTypes = new ArrayList<>();
//		TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(oAuthServerUri+"/oauth/authorize", clientId, clientSecret );
//        TokenEndpoint tokenEndpoint = new TokenEndpoint(oAuthServerUri+"/oauth/token", "token");
//        grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint));
//        return grantTypes;
//	}
//    
//	private List<AuthorizationScope> scopes() {
//		List<AuthorizationScope> list = new ArrayList();
//		list.add(new AuthorizationScope("read_scope","Grants read access"));
//		list.add(new AuthorizationScope("write_scope","Grants write access"));
//		list.add(new AuthorizationScope("admin_scope","Grants read write and delete access"));
//		return list;
//    }	
    
    
//    private List<Parameter> setHeaderToken() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name("X-Auth-Token")
//        	.description("token")
//        	.modelRef(new ModelRef("string"))
//        	.parameterType("header")
//        	.required(false).build();
//        pars.add(tokenPar.build());
//        return pars;
//    }
}
