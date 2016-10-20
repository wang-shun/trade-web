package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 交易计划变更控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping(value="/report")
public class DealChangeCaseController {
	
	/**
	 * 交易计划变更案件列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="dealChangeCaseList")
	public String dealChangeCaseList(Model model){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("defaultDate", sdf.format(Calendar.getInstance().getTime()));
		return "report/dealChangeList";
	}
	
	
}
