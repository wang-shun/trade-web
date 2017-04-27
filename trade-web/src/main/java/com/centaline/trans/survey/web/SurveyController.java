package com.centaline.trans.survey.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.survey.service.SurveyService;
import com.centaline.trans.survey.vo.SurveyVO;

@Controller
@RequestMapping("survey")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
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
			response.setMessage("操作失败!"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("signPass")
	public AjaxResponse<String> signPass(SurveyVO surveyVO){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
			//SurveyStatusEnum.SIGN_SURVEY_PASS;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("signResult",true));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("signReject")
	public AjaxResponse<String> signReject(SurveyVO surveyVO){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
			//SurveyStatusEnum.SIGN_SURVEY_REJECT;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("signResult",false));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("signFollow")
	public AjaxResponse<String> signFollow(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("transferPass")
	public AjaxResponse<String> transferPass(SurveyVO surveyVO){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
//			SurveyStatusEnum.TRANSFER_SURVEY_PASS;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("transferResult",true));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("transferReject")
	public AjaxResponse<String> transferReject(SurveyVO surveyVO){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
			//SurveyStatusEnum.TRANSFER_SURVEY_REJECT;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("transferResult",false));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("transferFollow")
	public AjaxResponse<String> transferFollow(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}

}
