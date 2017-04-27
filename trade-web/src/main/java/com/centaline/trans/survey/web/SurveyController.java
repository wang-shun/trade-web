package com.centaline.trans.survey.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.survey.service.SurveyService;
import com.centaline.trans.survey.vo.SurveyVO;

@Controller
@RequestMapping("survey")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	//页面
	@RequestMapping("/list")
	public String list(Model model){
		List<SurveyVO> surveyVOList = surveyService.querySurveyVOList();
		model.addAttribute("surveyVOList", surveyVOList);
		return "";
	}
	
	@RequestMapping("/detail")
	public String detail(Model model, int surveyId){
		SurveyVO surveyVO = surveyService.querySurveyVOById(surveyId);
		model.addAttribute("survey", surveyVO);
		return "";
	}
	
	@RequestMapping("/edit")
	public String edit(Model model, int surveyId){
		SurveyVO surveyVO = surveyService.querySurveyVOById(surveyId);
		model.addAttribute("survey", surveyVO);
		return "";
	}
	
	//操作
	@RequestMapping("dispatch")
	public AjaxResponse<String> dispatch(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("pass")
	public AjaxResponse<String> surveyPass(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("reject")
	public AjaxResponse<String> surveyReject(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("submitEdit")
	public AjaxResponse<String> submitEdit(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}

}
