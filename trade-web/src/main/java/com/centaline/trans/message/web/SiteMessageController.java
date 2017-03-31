/**
 * Shanghai Centalinehink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.message.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author jjm
 * @version $Id: SiteMessageController.java, v 0.1 2015年7月3日 下午1:32:33 jjm Exp $
 */
@Controller
@RequestMapping(value = "message/box")
public class SiteMessageController {
	/**
	 * 
	 * 打开站内消息列表
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/siteList.html", method = RequestMethod.GET)
	public String siteList() {
		return "message/siteList";
	}

	/**
	 * 
	 * 打开消息详情页面
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/siteDetail.html", method = RequestMethod.GET)
	public String siteDetail(Model model, String id) {

		// 更新消息状态为已读
		return "message/siteDetail";
	}
}
