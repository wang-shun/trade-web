package com.centaline.trans.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/app")
public class AppInfoController {
	
	@Value("${uam.permission.currentAppName}")
	private String appName;
	
	@RequestMapping(value = "getAppInfo.json")
	@ResponseBody
	public Map<String,Object> getAppInfo( HttpServletRequest request){
		
		ServletContext  sc = request.getSession().getServletContext();
		Map<String,Object> appInfo = new HashMap<String,Object>();
		appInfo.put("ctx", sc.getAttribute("ctx"));
		appInfo.put("_appCtxList", sc.getAttribute("_appCtxList"));
		appInfo.put("_domain", sc.getAttribute("_domain"));
		appInfo.put("_appCtx", sc.getAttribute("_appCtx"));
		
		return appInfo;
	}
}
