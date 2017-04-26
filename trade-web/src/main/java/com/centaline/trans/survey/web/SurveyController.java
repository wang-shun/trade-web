package com.centaline.trans.survey.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.survey.service.SurveyService;

@Controller
@RequestMapping("survey")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	//页面
	@RequestMapping("/list")
	public String list(){
		return "";
	}
	
	@RequestMapping("/detail")
	public String detail(){
		return "";
	}
	
	@RequestMapping("/edit")
	public String edit(){
		return "";
	}
	
	//操作
	@RequestMapping("dispatch")
	public AjaxResponse<String> dispatch(){
		return new AjaxResponse<String>();
	}
	
	@RequestMapping("pass")
	public AjaxResponse<String> surveyPass(){
		return new AjaxResponse<String>();
	}
	
	@RequestMapping("reject")
	public AjaxResponse<String> surveyReject(){
		return new AjaxResponse<String>();
	}
	
	@RequestMapping("submitEdit")
	public AjaxResponse<String> submitEdit(){
		return new AjaxResponse<String>();
	}

}
