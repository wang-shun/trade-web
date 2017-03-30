package com.centaline.parportal.mobile.login.service;

import javax.servlet.http.HttpServletRequest;

public interface MobileSecurityHandler {
  //long expTime = 1000 * 60 * 5l;  //5分钟
  //long expTime = 1000 * 60 * 60;  //60分钟
	long expTime = 1000 * 60 * 120;  //120分钟

	/**
	 * 校验restful请求
	 * 
	 * @param request
	 * @return
	 */
	boolean doCheckAuthorization(HttpServletRequest request);
}
