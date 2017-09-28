package com.centaline.api.swagger;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger 配置类
 * @author yinchao
 * @date 2017/9/5
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
	@Value("${swagger.host:localhost:8083}")
	String host;
	@Value("${trade.apikey.headerName:api-key}")
	private String keyHeaderName;
	@Bean
	public Docket config() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host(host)
				.select()
				//指定了 Swagger 的扫描包名， 假如不指定此项， 在 Spring Boot 项目中， 会生成 base-err-controller 的 api 接口项。
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.build()
				.enable(true)
				.apiInfo(apiInfo())
				//该处仅仅是定义，和再文档中进行显示，具体校验再Shiro的Filter中
				//配置安全上下文 定义需要权限验证的路径
				.securityContexts(Lists.newArrayList(securityContext()))
				//定义使用的KEY
				.securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("中原集团金融交易系统API在线文档")
				.description("主要用于提供给其他系统与交易系统进行通讯使用")
				.license("")
				.licenseUrl("http://unlicense.org")
				.termsOfServiceUrl("访问地址:http://trade.centaline.com.cn/trade-api")
				.version("v1")
				.contact(new Contact("交易系统", "http://trade.centaline.com.cn/trade-api", ""))
				.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/api/*"))
				.build();
	}
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		//该处SecurityReference 第一个参数对应header name
		return Lists.newArrayList(
				new SecurityReference(keyHeaderName, authorizationScopes));
	}
	private ApiKey apiKey() {
		//第一个参数对应header name
		return new ApiKey(keyHeaderName, "密钥", "header");
	}

}
