package com.centaline.trans.eval.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.eval.entity.ToEvaSettleUpdateLog;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.service.ToEvaSettleUpdateLogService;
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
 * @author wangxj</a>
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
	@Autowired(required = true)
	ToEvaSettleUpdateLogService toEvaSettleUpdateLogService;
	
	/**
	 * 页面初始化
	 * 
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/evalWaitEndList")
	public String evalWaitEndList(String caseCode) {
		if(caseCode != null) {
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettleService.insertSelective(toEvalSettle);
		}
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
	public String majorAppro( HttpServletRequest request,String[] caseCodes) {
		if(caseCodes != null) {
			for (String caseCode : caseCodes) {
				//System.out.println(caseCode);
				ToEvalSettle toEvalSettle = new ToEvalSettle();
				toEvalSettle.setStatus(String.valueOf(5));//5:进入总监审批页状态
				toEvalSettle.setCaseCode(caseCode);
				toEvalSettleService.updateByCaseCode(toEvalSettle);
			}
		}
		
		return  "eval/settle/majorAppro";
	}
	
	/**
	 * 
	 * 审批不通过
	 * @param 
	 * @param caseCodes
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/majorNoAppro")
	public String majorNoAppro( HttpServletRequest request,String[] caseCodes,String rejectCause) {
		for (String caseCode : caseCodes) {
			//System.out.println(rejectCause);
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setStatus(String.valueOf(0));//0:修改状态，未提交
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettleService.updateByCaseCode(toEvalSettle);
		}

		return  "redirect:evalWaitEndList";
	}
	
	/**
	 * 
	 * 审批通过
	 * @param 
	 * @param caseCodes
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/majorIsAppro")
	public String majorIsAppro( HttpServletRequest request,String[] caseCodes) {
		for (String caseCode : caseCodes) {
			//System.out.println(caseCode);
			ToEvalSettle toEvalSettle = new ToEvalSettle();
			toEvalSettle.setStatus(String.valueOf(6));//6:修改状态，已提交财务审批中
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettleService.updateByCaseCode(toEvalSettle);
		}

		return  "redirect:evalWaitEndList";
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
	public String newEndForm(String[] caseCodes, Model model, HttpServletRequest request) {
		for (String caseCode : caseCodes) {
			//System.out.println(caseCode);
			model.addAttribute("caseCode", caseCode);
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
		} 
		return AjaxResponse.fail("新增结算费用失败！");
	}
	
	/**新增结算关联案件表单显示页*/
	@RequestMapping("/associatedShowForm")
	public AjaxResponse<EvalAccountShowVO> associatedShowForm(String caseCode,  Model model, HttpServletRequest request) {
			AjaxResponse<EvalAccountShowVO> result = new AjaxResponse<>();
			//int count = toEvalSettleService.findCaseCountByCaseCode(caseCode);
			try {
			if(caseCode != null) {
				EvalAccountShowVO evalAccountShowVO  = new EvalAccountShowVO();
				ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				//evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
				//System.out.println(format.format(toEvalReportProcess.getApplyDate()));
				evalAccountShowVO.setApplyDate(format.format(toEvalReportProcess.getApplyDate()));
				evalAccountShowVO.setFinOrgId(toEvalReportProcess.getFinOrgId());
				if(toEvalReportProcess.getIssueDate() != null) {
					evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
				}else {
					evalAccountShowVO.setIssueDate(String.valueOf(' '));
				}
				//evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
				evalAccountShowVO.setCaseCode(toEvalReportProcess.getCaseCode());
				if(toEvalReportProcess.getEvaCode() != null) {
					evalAccountShowVO.setEvaCode(toEvalReportProcess.getEvaCode());
				}else {
					evalAccountShowVO.setEvaCode(String.valueOf(' '));
				}
				
				//评估值
				if(toEvalReportProcess.getEvaPrice()!= null) {
					evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
				}else {
					evalAccountShowVO.setEvaPrice(new BigDecimal("0.00"));
				}
				
				//贷款权证
				//评估费实收金额
				ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
				//结算费用
				if(toEvalRebate.getEvaComAmount()!= null) {
					evalAccountShowVO.setEvalComAmount(toEvalRebate.getEvaComAmount());
				}else {
					evalAccountShowVO.setEvalComAmount(new BigDecimal("0.00"));
				}
				evalAccountShowVO.setEvalRealCharges(toEvalRebate.getEvalRealCharges());
				model.addAttribute("evalVO", evalAccountShowVO);
			}
			//result.setContent((EvalAccountShowVO) model);
			result.setSuccess(true);
			result.setMessage("关联成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("关联失败!");
		}
		return result;
	}
	
	
	
	/**评估结算修改显示页*/
	@RequestMapping(value = "evalEndUpdate")
	public String evalEndUpdate(Long pkid, Model model, HttpServletRequest request,String caseCode) {
		if(caseCode != null) {
			EvalAccountShowVO evalAccountShowVO  = new EvalAccountShowVO();
			ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
			evalAccountShowVO.setCaseCode(caseCode);
			//System.out.println(format.format(toEvalReportProcess.getApplyDate()));
			evalAccountShowVO.setApplyDate(format.format(toEvalReportProcess.getApplyDate()));
			evalAccountShowVO.setFinOrgId(toEvalReportProcess.getFinOrgId());
			if(toEvalReportProcess.getIssueDate() != null) {
				evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
			}else {
				evalAccountShowVO.setIssueDate(String.valueOf(' '));
			}
			
			ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
			evalAccountShowVO.setEvalRealCharges(toEvalRebate.getEvalRealCharges());
			//结算费用
			evalAccountShowVO.setEvalComAmount(toEvalRebate.getEvaComAmount());
			//List<ToEvaSettleUpdateLog> updateLogList = new ArrayList<>();
			//查询修改记录列表
			List<ToEvaSettleUpdateLog> toEvaSettleUpdateLogList = toEvaSettleUpdateLogService.selectUpdateLogByCaseCode(caseCode);
			/*if(CollectionUtils.isNotEmpty(toEvaSettleUpdateLogList)) {
				for (ToEvaSettleUpdateLog toEvaSettleUpdateLog : toEvaSettleUpdateLogList) {
					ToEvaSettleUpdateLog record = new ToEvaSettleUpdateLog();
					record.setUpdateReason(toEvaSettleUpdateLog.getUpdateReason());
					record.setUpdateTime(toEvaSettleUpdateLog.getUpdateTime());
					updateLogList.add(record);
				}
			}*/
			model.addAttribute("evalVO", evalAccountShowVO);
			model.addAttribute("updateLogList",toEvaSettleUpdateLogList);
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
	@ResponseBody
	public AjaxResponse<?> evalEndUpdateFee(HttpServletRequest request,@RequestBody EvalAccountShowVO evalAccountShowVO) {
		
		
		ToEvalSettle toEvalSettle = new ToEvalSettle();
		toEvalSettle.setCaseCode(evalAccountShowVO.getCaseCode());
		toEvalSettle.setSettleFee(evalAccountShowVO.getSettleFee());
		int count = toEvalSettleService.updateSettleFeeByCaseCode(toEvalSettle);
	    
		if(count>0) {
			//插入案件到记录表
			ToEvaSettleUpdateLog record = new ToEvaSettleUpdateLog();
			record.setCaseCode(evalAccountShowVO.getCaseCode());
			record.setUpdateTime(new Date());
			record.setUpdateReason(evalAccountShowVO.getUpdateReason());//修改原因
			//查询记录表是否有此案件
			/*List<ToEvaSettleUpdateLog> toEvaSettleUpdateLogList = toEvaSettleUpdateLogService.selectUpdateNumByCaseCode(evalAccountShowVO.getCaseCode());
			if(CollectionUtils.isNotEmpty(toEvaSettleUpdateLogList)) {
				ToEvaSettleUpdateLog log =  toEvaSettleUpdateLogList.get(0);
				if(log != null) {
					int x = log.getUpdateNum();
					int a = x+1;
					record.setUpdateNum(a);
				}
			}else{
				record.setUpdateNum(1);
			}*/
			
		    toEvaSettleUpdateLogService.insertSelective(record);
			
			return  AjaxResponse.success("结算费用修改成功！");
		}
		return  AjaxResponse.fail("结算费用修改失败！");
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
			toEvalSettle.setStatus(String.valueOf(4));//4:已结算状态
			toEvalSettle.setCaseCode(caseCode);
			toEvalSettle.setSettleTime(new Date());
			//System.out.println(new Date());
			toEvalSettleService.updateByCaseCode(toEvalSettle);
			model.addAttribute("caseCode", caseCode);
		}

		return  "redirect:evalEndList";
	}
	
	/**转向关联评估列表*/
	@RequestMapping("/associatedEvalList")
	public String associatedEvalList(HttpServletRequest request) {
		

		return  "eval/settle/associatedEvalList";
	}
	
	/**重新结算*/
	@RequestMapping(value = "/reNewAccount")
	@ResponseBody
	public AjaxResponse<?> reNewAccount(String [] caseCodes, HttpServletRequest request) {
		AjaxResponse<?> result = new AjaxResponse<>();
		//System.out.println(caseCodes);
		try {
			for(String caseC : caseCodes) {
				ToEvalSettle toEvalSettle = new ToEvalSettle();
				toEvalSettle.setStatus(String.valueOf(3));//3:未结算状态
				toEvalSettle.setCaseCode(caseC);
				toEvalSettleService.updateByCaseCode(toEvalSettle);
			}
			result.setSuccess(true);
			result.setMessage("重新结算成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("处理失败!");
		}
		
		return result;
	}
	
	/*@RequestMapping("/newEndForm2")
	public String newEndForm2(String caseCode,Model model) {
		
		return  "eval/settle/newEndForm";
	}*/
	
	/*@RequestMapping("/associatedShowForm")
	public ModelAndView associatedShowForm(String caseCode,  Model model, HttpServletRequest request) {
		

		return new ModelAndView("redirect:newEndForm2?caseCode="+caseCode);
	}*/
	
	
}
