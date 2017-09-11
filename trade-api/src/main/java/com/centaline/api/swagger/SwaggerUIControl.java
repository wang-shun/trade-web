package com.centaline.api.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Swagger-ui的请求路径
 * @author yinchao
 * @date 2017/9/6
 */
@Controller
public class SwaggerUIControl {
	@RequestMapping(value="/")
	public String index(){
		return "redirect:swagger-ui.html";
	}
}
