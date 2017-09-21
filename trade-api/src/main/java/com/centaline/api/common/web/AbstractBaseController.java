package com.centaline.api.common.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础Controller
 * 提供一些公用的方法
 * @author yinchao
 * @date 2017/9/21
 */
public abstract class AbstractBaseController {
	/**
	 * 根据请求 获取正确的客户端地址
	 * @param request
	 * @return
	 */
	protected String getHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		return SecurityUtils.getSubject().getSession().getHost();
	}
}
