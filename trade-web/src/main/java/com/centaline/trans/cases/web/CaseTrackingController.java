package com.centaline.trans.cases.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("case")
@Controller
public class CaseTrackingController {
	/**
	 * 案件追踪
	 * @return
	 */
	@RequestMapping("tracking")
	public String tracking() {
		return "/case/taskTracking2";
	}
	/*@RequestMapping("tracking2")
	public String tracking2() {
		return "/case/taskTracking2";
	}*/
}
