package com.centaline.trans.eval.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.utils.UiImproveUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.service.ToEvaCommPersonAmountService;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.eval.service.ToEvaInvoiceService;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
import com.centaline.trans.task.entity.ToApproveRecord;



/**
 * @author xiefei1
 * @since 2017年9月25日 上午11:19:52 
 * @description 评估费发票管理及调佣对象，金额
 */
@Controller
@RequestMapping(value = "/eval")
public class EvalInvoiceController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired(required =true)
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToEvaCommissionChangeService toEvaCommissionChangeService;
	@Autowired
	private UamPermissionService uamPermissionService;
	@Autowired
	private ToEvaCommPersonAmountService toEvaCommPersonAmountService;
	@Autowired
	private ToEvaInvoiceService toEvaInvoiceService;
	@Autowired
	private WorkFlowManager workFlowManager;
	/**
	 * @since:2017年10月20日 下午2:53:56
	 * @description:跳转领取发票页面
	 * @author:xiefei1
	 * @param request
	 * @param model
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value = "collectInvoice")
	public String toCollectInvoice(HttpServletRequest request,Model model,String caseCode) {
		ToEvaInvoice toEvaInvoice = null;
		List<ToEvaInvoice> toEvaInvoiceList = toEvaInvoiceService.selectByCaseCodeWithEvaPointer(caseCode);
		if(toEvaInvoiceList.size()>0){
			 toEvaInvoice = toEvaInvoiceList.get(0);			
			 model.addAttribute("toEvaInvoice", toEvaInvoice);
		}
		EvalChangeCommVO evalChangeCommVO = toEvaCommPersonAmountService.getFullEvalChangeCommVO(caseCode);
		model.addAttribute("evalChangeCommVO", evalChangeCommVO);
		model.addAttribute("caseCode", caseCode);
		return "eval/collectInvoice";
	}
	/**
	 * @since:2017年10月19日 下午7:41:48
	 * @description:提交开具发票
	 * @author:xiefei1
	 * @param request
	 * @param model
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value = "submitIssueInvoice")
	@ResponseBody
	public AjaxResponse<String> submitIssueInvoice(ToEvaInvoice toEvaInvoice,String processInstanceId,String taskId,String caseCode) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		SessionUser user = uamSessionService.getSessionUser();
		List<RestVariable> variables = new ArrayList<RestVariable>();
			try{
				toEvaInvoiceService.updateByPrimaryKeySelective(toEvaInvoice);
				workFlowManager.submitTask(variables, taskId, processInstanceId, user.getId(), caseCode);
			}
			catch(Exception e){
				response.setSuccess(false);
				response.setMessage(e.getMessage());
				logger.error("保存失败！"+e.getCause());
			}					
		return response;
	}
	
	/**
	 * 
	 * @since:2017年10月19日 下午7:16:41
	 * @description:获取CCAI发票信息
	 * @author:xiefei1
	 * @param request
	 * @param toEvaCommissionChange
	 * @return
	 */
	@RequestMapping(value = "getInvoiceInfo")
	@ResponseBody
	public ToEvaInvoice getInvoiceInfo(String evaCode) {		
		return toEvaInvoiceService.updateLinkInvoice(evaCode);
	}

	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "submitWarrantManagerInvoiceAudit")
	@ResponseBody
	public AjaxResponse<String> submitWarrantManagerInvoiceAudit(HttpServletRequest request,Model model,String caseCode,
			String partCode,String content,String status,String taskId,String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();		
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setPartCode(partCode);
		toApproveRecord.setCaseCode(caseCode);
		toApproveRecord.setContent(content);
		toApproveRecord.setCaseCode(caseCode);
		//1是通过，0是没通过
		//notApprove为空表示通过，不为空表示没通过
		if(status.contains("1")){
			toApproveRecord.setNotApprove(null);			
		}else{
			toApproveRecord.setNotApprove("没通过");
		}
		AjaxResponse<String> response = new AjaxResponse<String>();
		
		try{
		toEvaInvoiceService.updateEvalInvoiceSubmit(toApproveRecord, taskId, processInstanceId);
			}catch(Exception e){
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		logger.error("保存失败！"+e.getCause());
	}
	return response;

	}
	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "submitInvoiceAudit")
	@ResponseBody
	public AjaxResponse<String> submitInvoiceAudit(EvalChangeCommVO evalChangeCommVO,
			ToEvaCommissionChange toEvaCommissionChange,HttpServletRequest request,Model model,String caseCode,
			String partCode,String content,String status) {
		SessionUser user = uamSessionService.getSessionUser();		
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setPartCode(partCode);
		toApproveRecord.setCaseCode(caseCode);
		toApproveRecord.setContent(content);
		toApproveRecord.setCaseCode(caseCode);
		//1是通过，0是没通过
		//notApprove为空表示通过，不为空表示没通过
		if(status.contains("1")){
			toApproveRecord.setNotApprove(null);			
		}else{
			toApproveRecord.setNotApprove("没通过");
		}
		AjaxResponse<String> response = new AjaxResponse<String>();
		
		try{
		toEvaInvoiceService.updateEvalInvoiceApproveRecord(toApproveRecord);
		//只要有同名的属性都会同时分配给这两个对象
		toEvaCommPersonAmountService.saveEvalChangeCommVO(evalChangeCommVO,toEvaCommissionChange);
			}catch(Exception e){
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		logger.error("保存失败！"+e.getCause());
	}
	return response;

	}
	/**
	 * @since:2017年10月13日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "submitIssueInvoiceChangeComm")
	@ResponseBody
	public AjaxResponse<String> submitIssueInvoiceChangeComm(EvalChangeCommVO evalChangeCommVO,ToEvaCommissionChange toEvaCommissionChange, HttpServletRequest request,Model model,String caseCode) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//只要有同名的属性都会同时分配给这两个对象
		toEvaCommPersonAmountService.saveEvalChangeCommVO(evalChangeCommVO,toEvaCommissionChange);
		}
		catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    	}
		return response;
	}
	/**
	 * 
	 * @since:2017年10月18日 下午5:40:38
	 * @description:跳转关联评估单页面
	 * @author:xiefei1
	 * @param request
	 * @param model
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value = "invoiceConnectEval")
	public String toInvoiceConnectEval(HttpServletRequest request,Model model,String caseCode) {
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		model.addAttribute("caseCode", caseCode);
		request.setAttribute("ctx", ctx);
		return "eval/invoiceConnectEval";
	}
	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "invoiceAudit")
	public String toInvoiceAudit(HttpServletRequest request,Model model,String caseCode) {
		ToEvaInvoice toEvaInvoice = toEvaInvoiceService.selectByCaseCodeWithEvalCompany(caseCode);
		model.addAttribute("toEvaInvoice", toEvaInvoice);
		model.addAttribute("caseCode", caseCode);
		return "eval/invoiceAudit";
	}
	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "invoiceApply")
	public String toInvoiceApply(HttpServletRequest request,Model model,String caseCode) {
		model.addAttribute("caseCode", caseCode);
		return "eval/invoiceApply";
	}
	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "issueInvoice")
	public String toIssueInvoice(HttpServletRequest request,Model model,String caseCode) {
		ToEvaInvoice toEvaInvoice = null;
		List<ToEvaInvoice> list = toEvaInvoiceService.selectByCaseCodeWithEvaPointer(caseCode);
		if(list.size()>0){
			 toEvaInvoice = list.get(0);
			 model.addAttribute("toEvaInvoice", toEvaInvoice);
		}
		model.addAttribute("caseCode", caseCode);
		return "eval/issueInvoice";
	}
	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 跳转页面
	 * @author:xiefei1
	 */
	@RequestMapping(value = "issueInvoiceChangeComm")
	public String toIssueInvoiceChangeComm(HttpServletRequest request,Model model,String caseCode) {
		EvalChangeCommVO evalChangeCommVO = toEvaCommPersonAmountService.getFullEvalChangeCommVOFromDB(caseCode);
		model.addAttribute("caseCode", caseCode);
		model.addAttribute("evalChangeCommVO", evalChangeCommVO);
		return "eval/issueInvoiceChangeComm";
	}
	@RequestMapping(value = "save")
	@ResponseBody
	public HashMap<String, Object> saveEval(HttpServletRequest request,ToEvaCommissionChange toEvaCommissionChange) {
		int insertSelective = toEvaCommissionChangeService.insertSelective(toEvaCommissionChange);
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		if(insertSelective==1){
			resultMap.put("data", "success");
		}else{
			resultMap.put("data", "error");
		}
		return resultMap;
	}

	
	

	


}
