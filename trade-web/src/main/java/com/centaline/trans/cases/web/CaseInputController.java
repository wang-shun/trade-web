package com.centaline.trans.cases.web;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.team.service.TsTeamScopeTargetService;
import com.centaline.trans.utils.DateUtil;


/**
 * @author  hejf
 * @description  自录单列表相关
 * @date  2017年4月11日10:45:58
 */
@Controller
@RequestMapping(value = "/newCase")
public class CaseInputController {
	
	@Autowired(required = true)
	CaseMergeService caseMergeService;	
	
	/**
	 * 页面跳转 
	 * @author hejf10
	 * @param request
	 * @return
	 */

	@RequestMapping(value="input")
	public String inputCaseInfo(Model model, ServletRequest request){
		
		return "case/newCaselist";
	}	
	/**
	 * 自录单列表详细
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value="inputt")
	@ResponseBody
	public AjaxResponse<?> updateMergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<Boolean> response = new AjaxResponse<>();
		try{
			//toCaseService.updateMergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			/**response.setMessage(e.getMessage()+"异常："+sOut);**/
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		if(StringUtils.equals(caseInfo.getType(), "1")){response.setContent(true);}
		if(StringUtils.equals(caseInfo.getType(), "0")){response.setContent(false);}
		return response;
	}	
}
