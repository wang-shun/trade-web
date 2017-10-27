package com.centaline.trans.evaPricing.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	 * 新增案件保存启动申请流程
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	public AjaxResponse<String> saveEvaPricing(ToEvaPricingVo ToEvaPricingVo, ServletRequest request){
		//申请，内勤回复
		SessionUser user = uamSessionService.getSessionUser();
		String userId = user.getId();
		ToEvaPricingVo.setAriserId(userId);//申请人
		AjaxResponse<String> result = new AjaxResponse<String>();

		try{
			result = evaPricingService.insertEvaPricing(ToEvaPricingVo);
		} catch(Exception e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}
	
	/**
	 * 查看询价明细
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingDetail")
	public String evaPricingDetail(@RequestParam(value="PKID",required=true)Long PKID,
									@RequestParam(value="instCode",required=true)String instCode, Model model, ServletRequest request){
		if(PKID == null){
			return "";
		}
		
		ToEvaPricingVo toEvaPricingVo =  evaPricingService.findEvaPricingDetailByPKID(PKID,null);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		request.setAttribute("instCode", instCode);
		
		return "evaPricing/evaPricingDetail";
	}
	
	/**
	 * 询价详情操作
	 * @param taskId
	 * @param instCode
	 * @param isValid
	 * @param reason
	 * @return
	 */
	@RequestMapping("evaPricingDetailSubmit")
	@ResponseBody
	public AjaxResponse<String> evaPricingDetailSubmit(ToEvaPricingVo toEvaPricingVo){
		AjaxResponse<String> result = new AjaxResponse<String>();
		
		try{
			//询价更新
			result = evaPricingService.updateEvaPricingDetail(toEvaPricingVo);
		} catch(Exception e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}
	
	/**
	 * 回复页面
	 * @param businessKey 流程启动时的bussinessKey参数：evaCode
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingEnter")
	public String evaPricingEnter(String businessKey,Model model, ServletRequest request){

		ToEvaPricingVo toEvaPricingVo =  evaPricingService.findEvaPricingDetailByPKID(null,businessKey);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		
		return "evaPricing/evaPricingEnter";
	}
	
	/**
	 * 回复提交保存
	 * @param toEvaPricingVo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingEnterSubmit")
	@ResponseBody
	public AjaxResponse<String> evaPricingEnterSubmit(ToEvaPricingVo toEvaPricingVo ,Model model, ServletRequest request){
		AjaxResponse<String> result = new AjaxResponse<String>();
		try{
			evaPricingService.updateEvaPricing(toEvaPricingVo);
		} catch(Exception e){
			result.setSuccess(false);
			result.setMessage("数据更新失败");
			return result;
		}
		
		return result;
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
	 * 获取评估公司
	 * @return
	 */
	@RequestMapping(value="getEvaFinOrg")
	@ResponseBody
	public AjaxResponse<List<Map<String,String>>> getEvaFinOrg(){
		AjaxResponse<List<Map<String,String>>> result = new AjaxResponse<List<Map<String,String>>>();
		
		List<Map<String,String>> lis = evaPricingService.queryEvaFinOrg();
		result.setContent(lis);
		return result;
	}
	
	/**
	 * 询价关联案件
	 * @param pkid
	 * @param caseCode
	 * @return
	 */
	@RequestMapping("evaPricingRelation")
	@ResponseBody
	public AjaxResponse<String> evaPricingRelation(long pkid,String caseCode,String addr){
		
		return evaPricingService.updatEevalRelation(pkid, caseCode,addr);
	}
	
	/**
	 * 询价代办任务
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingTaskList")
	public String evaPricingTaskList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("userId", user.getId());

		return "evaPricing/evaPricingTaskList";
	}
	
}
