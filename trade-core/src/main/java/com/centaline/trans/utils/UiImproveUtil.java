package com.centaline.trans.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * ui页面改版工具类
 * 
 * @author yinjf2
 *
 *
 */
public class UiImproveUtil {

	/**
	 * 获取页面版本参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getPageType(HttpServletRequest request) {
		String version = request.getParameter("v");

		if (version == null) {
			version = "";
		}

		return version;
	}
}
