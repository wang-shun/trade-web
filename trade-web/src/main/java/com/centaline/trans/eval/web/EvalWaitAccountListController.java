package com.centaline.trans.eval.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.eval.service.ToEvalSettleService;
import com.centaline.trans.eval.vo.EvalAccountShowVO;

/**
 * 
 * <p>
 * Project: 评估待结算
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * 
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value = "eval/settle")
public class EvalWaitAccountListController {

	@Autowired(required = true)
	ToEvalReportProcessService toEvalReportProcessService;
	@Autowired(required = true)
	ToEvalSettleService toEvalSettleService;
	@Autowired(required = true)
	ToEvalRebateService toEvalRebateService;


	/**
	 * 页面初始化
	 * 
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/evalWaitEndList")
	public String evalWaitEndList() {

		return "eval/settle/evalWaitEndList";
	}

	/**
	 * 
	 * 批量审批
	 * 
	 * @param 
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/majorAppro")
	public String majorAppro( HttpServletRequest request) {
		

		return  "eval/settle/majorAppro";
	}
	/**
	 * 
	 * 转向新增结算单
	 * 
	 * @param 
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/newEndForm")
	public String newEndForm(String[] caseCodes,  Model model, HttpServletRequest request) {
		for (String caseCode : caseCodes) {
			System.out.println(caseCode);
			model.addAttribute("caseCode", caseCode);
		}
		return  "eval/settle/newEndForm";
	}
	
	@RequestMapping("/newEndForm2")
	public String newEndForm2(String caseCode,Model model) {
		model.addAttribute("caseCode", caseCode);
		try {
			//AjaxResponse<EvalAccountShowVO> result = new AjaxResponse<>();
			if(caseCode != null) {
				EvalAccountShowVO evalAccountShowVO  = new EvalAccountShowVO();
				ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
				//System.out.println(format.format(toEvalReportProcess.getApplyDate()));
				evalAccountShowVO.setApplyDate(format.format(toEvalReportProcess.getApplyDate()));
				evalAccountShowVO.setFinOrgId(toEvalReportProcess.getFinOrgId());
				evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
				evalAccountShowVO.setCaseCode(toEvalReportProcess.getCaseCode());
				evalAccountShowVO.setEvaCode(toEvalReportProcess.getEvaCode());
				//评估值
				evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
				
				//结算费用
				ToEvalSettle toEvalSettle =  toEvalSettleService.findToCaseByCaseCode(caseCode);
				evalAccountShowVO.setSettleFee(toEvalSettle.getSettleFee());
				
				//贷款权证
				//评估费实收金额
				ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
				evalAccountShowVO.setEvalRealCharges(toEvalRebate.getEvalRealCharges());
				model.addAttribute("evalVO", evalAccountShowVO);
			}
//			result.setSuccess(true);
//			result.setMessage("关联成功!");
		} catch (Exception e) {
//			result.setSuccess(true);
//			result.setMessage("关联失败!");
		}
		return  "eval/settle/newEndForm";
	}
	
	/**新增结算费用*/
	@RequestMapping(value = "newEndFee")
	@ResponseBody
	public AjaxResponse<?> newEndFee(HttpServletRequest request,@RequestBody ToEvalSettle toEvalSettle) {
		
		int count = toEvalSettleService.newSettleFeeByCaseCode(toEvalSettle);
		if (count > 0) {
			ToEvalSettle record = new ToEvalSettle();
			record.setStatus(String.valueOf(0));//0:未提交
			record.setCaseCode(toEvalSettle.getCaseCode());
			System.out.println(toEvalSettle.getCaseCode());
			int cout2 = toEvalSettleService.updateByCaseCode(record);
			if(cout2>0) {
				return  AjaxResponse.success("新增结算费用成功！");
			}else {
				return AjaxResponse.fail("新增结算费用失败！");
			}
			//return  AjaxResponse.success("新增结算费用成功！");
		} 
//		else {
//			return AjaxResponse.fail("新增结算费用失败！");
//		}
		return AjaxResponse.fail("新增结算费用失败！");
	}
	
	/**新增结算关联案件表单显示页*/
	@RequestMapping("/associatedShowForm")
	public ModelAndView associatedShowForm(String caseCode,  Model model, HttpServletRequest request) {
		/*try {
			//AjaxResponse<EvalAccountShowVO> result = new AjaxResponse<>();
			if(caseCode != null) {
				EvalAccountShowVO evalAccountShowVO  = new EvalAccountShowVO();
				ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
				//System.out.println(format.format(toEvalReportProcess.getApplyDate()));
				evalAccountShowVO.setApplyDate(format.format(toEvalReportProcess.getApplyDate()));
				evalAccountShowVO.setFinOrgId(toEvalReportProcess.getFinOrgId());
				evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
				evalAccountShowVO.setCaseCode(toEvalReportProcess.getCaseCode());
				evalAccountShowVO.setEvaCode(toEvalReportProcess.getEvaCode());
				//评估值
				evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
				
				//结算费用
				ToEvalSettle toEvalSettle =  toEvalSettleService.findToCaseByCaseCode(caseCode);
				evalAccountShowVO.setSettleFee(toEvalSettle.getSettleFee());
				
				//贷款权证
				//评估费实收金额
				ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
				evalAccountShowVO.setEvalRealCharges(toEvalRebate.getEvalRealCharges());
				model.addAttribute("evalVO", evalAccountShowVO);
			}
//			result.setSuccess(true);
//			result.setMessage("关联成功!");
		} catch (Exception e) {
//			result.setSuccess(true);
//			result.setMessage("关联失败!");
		}*/

		return new ModelAndView("redirect:newEndForm2?caseCode="+caseCode);
	}
	
	/**评估结算修改显示页*/
	@RequestMapping(value = "evalEndUpdate")
	public String evalEndUpdate(Long pkid, Model model, HttpServletRequest request,String caseCode) {
		if(caseCode != null) {
			EvalAccountShowVO evalAccountShowVO  = new EvalAccountShowVO();
			ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
			//System.out.println(format.format(toEvalReportProcess.getApplyDate()));
			evalAccountShowVO.setApplyDate(format.format(toEvalReportProcess.getApplyDate()));
			evalAccountShowVO.setFinOrgId(toEvalReportProcess.getFinOrgId());
			evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
			
			ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
			evalAccountShowVO.setEvalRealCharges(toEvalRebate.getEvalRealCharges());
			model.addAttribute("evalVO", evalAccountShowVO);
		}
		
		return  "eval/settle/evalEndUpdate";
	}
	
	/**
	 * 
	 * 修改结算费用
	 * 
	 * @param 
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping(value = "evalEndUpdateFee")
	public AjaxResponse<?> evalEndUpdateFee(HttpServletRequest request,String caseCode,BigDecimal settleFee) {
		if(caseCode != null) {
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettle.setSettleFee(settleFee);
			toEvalSettleService.updateSettleFeeByCaseCode(toEvalSettle);
		}
		
		return  AjaxResponse.success("案件信息修改成功！");
	}
	/**
	 * 无需结算列表
	 * @param 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "noEndEvalList")
	public String noEndEvalList(Long pkid, HttpServletRequest request,Model model,String settleNotReason) {
		
		if(pkid != null) {
			//toEvalSettleService.insertSelective(record);
			//ToEvalSettle toEvalSettle = toEvalSettleService.findToCaseByCaseCode(caseCode);
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setStatus(String.valueOf(2));//2；无需结算状态
			toEvalSettle.setPkid(pkid);
			toEvalSettle.setSettleNotReason(settleNotReason);
			//toEvalSettle.setCaseCode(caseCode);
			toEvalSettleService.updateByPrimaryKeySelective(toEvalSettle);
			//System.out.println(settleNotReason);
			//model.addAttribute("caseCode", caseCode);
		}
		
		return  "eval/settle/noEndEvalList";
	}
	
	/**
	 * 结算列表
	 * @param 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "evalEndList")
	public String evalEndList(HttpServletRequest request,Model model) {

		return  "eval/settle/evalEndList";
	}
	
	/**重新结算*/
	@RequestMapping("/reEvalEndList")
	public String reEvalEndList(HttpServletRequest request,String[] caseCodes,Model model) {
		for (String caseCode : caseCodes) {
			//System.out.println(caseCode);
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setStatus(String.valueOf(3));//3:未结算状态
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettle.setSettleNotReason("");
			toEvalSettleService.updateByCaseCode(toEvalSettle);
			model.addAttribute("caseCode", caseCode);
		}

		return  "redirect:evalEndList";
	}
	
	/**批量结算*/
	@RequestMapping("/batEnd")
	public String batEnd(HttpServletRequest request,String[] caseCodes,Model model) {
		for (String caseCode : caseCodes) {
			//System.out.println(caseCode);
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setStatus(String.valueOf(4));//3:未结算状态
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettle.setSettleTime(new Date());
			//System.out.println(new Date());
			toEvalSettleService.updateByCaseCode(toEvalSettle);
			model.addAttribute("caseCode", caseCode);
		}

		return  "redirect:evalEndList";
	}
	
	@RequestMapping("/associatedEvalList")
	public String associatedEvalList(HttpServletRequest request) {
		

		return  "eval/settle/associatedEvalList";
	}

	@RequestMapping(value = "/reNewAccount")
	public AjaxResponse<?> reNewAccount(String [] caseCodes, HttpServletRequest request) {
		System.out.println(caseCodes);
		for(String caseC : caseCodes) {
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setStatus(String.valueOf(3));
			toEvalSettle.setCaseCode(caseC);
			toEvalSettleService.updateByCaseCode(toEvalSettle);
		}
		
		return AjaxResponse.success("案件信息重新结算成功！");
	}
	
	/*@RequestMapping(value="updateWarnListTime")
	@ResponseBody
	public AjaxResponse<LoanAgent> batchUpdateExportTime(Model model, ServletRequest request,String idStr){
		AjaxResponse<LoanAgent> result = new AjaxResponse<>();
		try {
			String[] pkidArr = idStr.split(",");
			for(String pkId : pkidArr) {
				LoanAgent loanAgent = new LoanAgent();
				loanAgent.setPkid(Long.parseLong(pkId));
				loanAgent.setLastExceedExportTime(new Date());
			    loanAgentService.updateLoanAgent(loanAgent);
			}
			result.setSuccess(true);
			result.setMessage("处理成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("处理失败!");
		}
		return result;
	}*/
}
