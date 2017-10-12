package com.centaline.trans.api.aop;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.apilog.service.ApiLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 使用AOP记录与CCAI的交互日志
 * @author yinchao
 * @date 2017/10/12
 */
@Aspect
@Component
public class ApiLogAspect {

	@Autowired
	private ApiLogService apiLogService;

	/**
	 * 记录所有跟CCAI的交互日志
	 * 代理RestTemplate，所以服务未开启是不会调用的，所以不用特殊判断服务是否开启来进行
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.springframework.web.client.RestTemplate.*(String ,..))")
	public Object writeApiLog(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String method = pjp.getSignature().getName();
		Object result = pjp.proceed();
		String url = (String) args[0];
		String responseBody;
		String requestParam = "";
		if ("getForObject".equals(method)) {
			requestParam = url.indexOf("?") != -1 ? url.substring(url.indexOf("?")+1) : "";
		} else if ("postForObject".equals(method)) {
			requestParam = JSONObject.toJSONString(args[1]);
		}
		if (result instanceof String) {
			responseBody = (String) result;
		} else {
			responseBody = JSONObject.toJSONString(result);
		}
		JSONObject jresult = JSONObject.parseObject(responseBody);
		apiLogService.apiLog("CCAI_" + method, url, requestParam, responseBody
				, jresult.containsKey("success") ? jresult.getBoolean("success") ? "0" : "1" : "E", jresult.containsKey("message") ? jresult.getString("message") : "");
		return result;
	}

}
