package com.centaline.trans.ransom.web;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 赎楼单列表控制器
 * @author wbwumf
 *
 */
@Controller
@RequestMapping(value = "/ransomList")
public class RansomListController {
	
	/**
	 * 页面跳转 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="*/{keyFlag}")
	public String caseProcess(Model model, ServletRequest request,@PathVariable String keyFlag){
		model.addAttribute("flag",keyFlag);
		return "ransom/" + keyFlag;
	}

//	/**
//	 * 新建赎楼单提交时返回列表详情页
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="ransomDetails")
//	public String ransomDetails(Model model, ServletRequest request){
//		return "/ransom/ransomDetail";
//	}
//	
//	/**
//	 * 跳转修改赎楼单详情
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="ransomDetailUpdate")
//	public String ransomDetailUpdate(Model model, ServletRequest request){
//		return "/ransom/ransomDetailUpdate";
//	}
//	
//	/**
//	 * 赎楼单详情修改跳转赎楼详情页
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="detailUpdate")
//	public String detailUpdate(Model model, ServletRequest request){
//		return "/ransom/ransomDetail";
//	}
}
