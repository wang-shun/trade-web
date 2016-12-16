package com.centaline.trans.cases.web;


import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;

import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvRecordedsVO;


/**
 * @author  zhuody
 * @description  自录案件相关操作
 * @date  2016-12-09
 */
@Controller
@RequestMapping(value = "/caseMerge")
public class CaseMergeController {

	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	WorkFlowManager workFlowManager;

	@Autowired(required = true)
	ToSpvService toSpvService;
	@Autowired(required = true)
	ToPropertyService toPropertyService;

	@Autowired(required = true)
	UamTemplateService uamTemplateService;
	@Autowired(required = true)
	PropertyUtilsService propertyUtilsService;
	@Autowired
	UamBasedataService uamBasedataService;

	
	
	/**
	 * 页面跳转 
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value="addCase")
	public String caseForChange(Model model, ServletRequest request){

		return "case/addCase";
	}	
	
	/**
	 * 自录案件 重复性判断
	 * 
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "inputCaseJudge")
	public String caseDetail(String propertyCode , ServletRequest request) {
		
		if(propertyCode != null && !"".equals(propertyCode)){
			List<ToPropertyInfo> toPropertyInfoList = toPropertyInfoService.getPropertyInfoByPropertyCode(propertyCode);
			//大于0  说明有重复案件信息，需要给出提示
			if(toPropertyInfoList.size() > 0){
				for(int i=0 ;i < toPropertyInfoList.size(); i++){
					ToCase toCase = toCaseService.findToCaseByCaseCode(toPropertyInfoList.get(i).getCaseCode() == null ? "" : toPropertyInfoList.get(i).getCaseCode());
					ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toPropertyInfoList.get(i).getCaseCode() == null ? "" : toPropertyInfoList.get(i).getCaseCode());
					
					//封装类 里面上下家信息如何显示;
				}		
			}
		}		
		
		return "case/caseDetail";
	}
	/**
	 * 案件记录
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseRecord")
	public String caseRecord(ServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("orgid", user.getServiceDepId());
		return "case/caseRecord";
	}
	@RequestMapping(value="mergeCase")
	@ResponseBody
	public AjaxResponse<?> mergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<?> response = new AjaxResponse<>();
		try{
			toCaseService.mergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
	        StackTraceElement[] trace = e.getStackTrace();
	        for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			response.setMessage(e.getMessage()+"异常："+sOut);
			e.printStackTrace();
		}
		return response;
	}	
	
	@RequestMapping(value="updateMergeCase")
	@ResponseBody
	public AjaxResponse<?> updateMergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<Boolean> response = new AjaxResponse<>();
		try{
			toCaseService.updateMergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			response.setMessage(e.getMessage()+"异常："+sOut);
			e.printStackTrace();
		}
		if(StringUtils.equals(caseInfo.getType(), "1")){response.setContent(true);}
		if(StringUtils.equals(caseInfo.getType(), "0")){response.setContent(false);}
		return response;
	}	
	
}
