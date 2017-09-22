package com.centaline.api.swagger;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
	@Bean
	public Docket config() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host(host)
				.select()
				//指定了 Swagger 的扫描包名， 假如不指定此项， 在 Spring Boot 项目中， 会生成 base-err-controller 的 api 接口项。
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.build()
				.apiInfo(apiInfo());
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

}
