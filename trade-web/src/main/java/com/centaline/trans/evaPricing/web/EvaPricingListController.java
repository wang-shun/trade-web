package com.centaline.trans.evaPricing.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;

/**
 * 询价作业管理
 * @author wbcaiyx
 * 
 */
@Controller
@RequestMapping(value="/evaPricing")
public class EvaPricingListController {
	
	@Autowired(required=true)
	UamSessionService uamSessionService;
	@Autowired
	EvaPricingService evaPricingService;
	
	/**
	 * 初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String evaluateList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("userId", user.getId());
		return "evaPricing/evaPricingList";
	}
	
	/**
	 * 新增询价
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addNewEvaPricing")
	public String addEvaluate(@RequestParam(value="caseCode",required=false)String caseCode, ServletRequest request){	
		request.setAttribute("caseCode", caseCode);
		return "evaPricing/evaPricingAdd";
	}
	
	/**
	 * 查看询价明细
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingDetail")
	public String evaPricingDetail(@RequestParam(value="PKID",required=true)Long PKID, Model model, ServletRequest request){
		if(PKID == null){
			return "";
		}
		ToEvaPricingVo toEvaPricingVo =  evaPricingService.findEvaPricingDetailByPKID(PKID);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		
		return "evaPricing/evaPricingDetail";
	}
	
	/**
	 * 记录页面
	 * @param PKID
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingEnter")
	public String evaPricingEnter(@RequestParam(value="PKID",required=true)Long PKID, Model model, ServletRequest request){
		if(PKID == null){
			return "";
		}
		ToEvaPricingVo toEvaPricingVo =  evaPricingService.findEvaPricingDetailByPKID(PKID);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		
		return "evaPricing/evaPricingEnter";
	}
	
	/**
	 * 取消询价
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingCancel")
	public AjaxResponse<String> evaPricingCancel(@RequestParam(value="PKID",required=true)Long PKID, Model model, ServletRequest request){
		AjaxResponse<String> result = new AjaxResponse<String>();
		try{
			evaPricingService.cancelByPKID(PKID);
		} catch(BusinessException e){
			 result.setSuccess(false);
			 result.setMessage(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 新增案件保存
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	public AjaxResponse<String> saveEvaPricing(ToEvaPricingVo ToEvaPricingVo, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String userId = user.getId();
		ToEvaPricingVo.setAriserId(userId);//申请人
		AjaxResponse<String> result = new AjaxResponse<String>();
		try{
			evaPricingService.insertEvaPricing(ToEvaPricingVo);
		} catch(BusinessException e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="evaPricingEnterUpdate")
	public AjaxResponse<String> evaPricingEnterUpdate(ToEvaPricingVo ToEvaPricingVo ,Model model, ServletRequest request){
		AjaxResponse<String> result = new AjaxResponse<String>();
		try{
			evaPricingService.updateEvaPricing(ToEvaPricingVo);
		} catch(BusinessException e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

}
