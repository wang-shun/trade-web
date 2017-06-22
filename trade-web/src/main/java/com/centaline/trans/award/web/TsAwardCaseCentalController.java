package com.centaline.trans.award.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.award.entity.TsAwardKpiPay;
import com.centaline.trans.award.entity.TsKpiSrvCase;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.award.service.TsAwardKpiPayService;
import com.centaline.trans.utils.DateUtil;


@Controller
@RequestMapping(value = "/newAward")
public class TsAwardCaseCentalController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private TsAwardKpiPayService tsAwardKpiPayService;	
	@Autowired
	TsAwardCaseCentalService 	tsAwardCaseCentalService;
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:     个人奖金发放汇总
	 */
	@RequestMapping(value = "/personBonusCollect")
	public String personBonusCollect(HttpServletRequest request) {
		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}
		//return "award/personBonusCollect";
		return  "award/newMethodStep/managerStep5";
	}
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:分批次案件奖金发放情况统计列表（计件池列表  isActive = 1）
	 */
	@RequestMapping(value = "/awardCaseCollect")
	public String awardCaseCollect(HttpServletRequest request) {
		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}		
		return "award/awardCaseCollect";
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:分批次案件奖金明细统计列表
	 */
	@RequestMapping(value = "/newBonus")
	public String newBonus(HttpServletRequest request) {		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}		
		//return "award/newBonus";
		return  "award/newMethodStep/managerStep4";
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:分批次案件奖金明细统计列表
	 */
	@RequestMapping(value = "/newConsultantBonus")
	public String newConsultBonus(HttpServletRequest request) {		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}	
		
		return "award/newBonus";
		
	}
	
	
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-05
	 * 
	 * @desc:iframe控制计件流程（isActive = 1）
	 */
	@RequestMapping(value = "/newMethodAwardCollect")
	public String newMethodAwardCollect(HttpServletRequest request) {
		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}		
		return "award/newMethodAwardCollect";
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-05
	 * 
	 * @desc:iframe控制计件流程（isActive = 1）
	 */
	@RequestMapping(value = "/getInitPage")
	@ResponseBody
	public AjaxResponse<String>  getInitPage(HttpServletRequest request,TsAwardKpiPay tsAwardKpiPay) {
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			TsAwardKpiPay awardKpiPay = tsAwardCaseCentalService.getInitPage(request,tsAwardKpiPay);
			if(awardKpiPay != null){
				response.setSuccess(true);
				response.setContent(awardKpiPay.getAwardStep());
			}else{
				response.setSuccess(false);
				response.setContent("0");
			}				
		}catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("获取初始化页面信息异常，请稍后再试！");
		}		
		return response;
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-05
	 * 
	 * @desc:iframe控制计件流程（isActive = 1）
	 */
	@RequestMapping(value = "/updateAwardStep")
	@ResponseBody
	public AjaxResponse<String>  updateAwardStep(HttpServletRequest request,TsAwardKpiPay tsAwardKpiPay) {
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			int k = tsAwardCaseCentalService.updateAwardStep(request,tsAwardKpiPay);
			if(k > 0){
				response.setSuccess(true);
				response.setContent("当前步骤已更新！");
			}else{
				response.setSuccess(false);
				response.setContent("当前步骤更新失败！");
			}				
		}catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("更新步骤时出现异常，请稍后再试！");
		}		
		return response;
	}
	
	
	
	/*
	 * @author:zhuojp
	 * 
	 * @date:2017-06-06
	 * 
	 * @desc:iframe控制计件流程（isActive = 1）
	 */
	@RequestMapping(value = "/managerPiecework")
	public String managerPiecework(HttpServletRequest request ,Model model){
		
		model.addAttribute("belongMonth", request.getParameter("belongMonth"));
		return "award/newMethodStep/managerStep0";
	}
	
	
	/***
	 * 	金融产品
	 * @author hejf10
	 * @date 2017年6月6日17:18:52
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/monthKpiImport")
	public String monthKpiImport(HttpServletRequest request) {		
		return  "award/newMethodStep/managerStep2";
	}
	/***
	 * 	流失率
	 * @author hejf10
	 * @date 2017年6月6日17:18:52
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wastageRate")
	public String wastageRate(HttpServletRequest request) {		
		return  "award/newMethodStep/managerStep3";
	}
	
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:分批次案件奖金明细统计列表
	 */
	@RequestMapping(value = "/satis")
	public String satis(HttpServletRequest request) {		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}		
		//return "award/newBonus";
		return  "award/newMethodStep/managerStep1";
	}
	
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-09
	 * 
	 * @desc:分批次案件奖金明细统计列表
	 */
	@RequestMapping(value = "/isSycnSatis")
	@ResponseBody
	public AjaxResponse<String>  isSycnSatis(HttpServletRequest request,TsKpiSrvCase tsKpiSrvCase) {
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			int k = tsAwardCaseCentalService.isSycnSatis(request,tsKpiSrvCase);
			if(k > 0){
				response.setSuccess(true);
				response.setContent("当前计件月份满意度数据已同步！");
			}else{
				response.setSuccess(false);
				response.setContent("当前计件月份满意度数据未同步！");
			}				
		}catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("请求查询是否同步满意度异常，请稍后再试！");
		}		
		return response;
	}
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-09
	 * 
	 * @desc:更新最终表的计件状态、同步管理层人员的计件奖金至下批次
	 */
	@RequestMapping(value = "/updateTsAwardKpiPayStatusAndSyncManager")
	@ResponseBody
	public AjaxResponse<String> updateTsAwardKpiPayStatusAndSyncManager(HttpServletRequest request,	String belongMonth) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			//TODO更新前 先判断上个月的状态是否已经调整
			TsAwardKpiPay record = new TsAwardKpiPay();
			// 确认状态
			record.setStatus("1");
			record.setBelongMonth(DateUtil.strToFullDate(belongMonth));
			
			int count = tsAwardKpiPayService.updateTsAwardKpiPayStatusAndSyncManager(record);
			if (count > 0) {
				response.setSuccess(true);
			} else {
				response.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("数据提交异常，请稍后再试！");
		}
		return response;
	}
	
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-14
	 * 
	 * @desc:更新最终表的计件状态、同步管理层人员的计件奖金至下批次
	 */
	@RequestMapping(value = "/isSubmitAward")
	@ResponseBody
	public AjaxResponse<String> isSubmitAward(HttpServletRequest request,	String belongMonth) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			
			TsAwardKpiPay record = new TsAwardKpiPay();			
			record.setStatus("1");
			record.setBelongMonth(DateUtil.strToFullDate(belongMonth));		
			
			TsAwardKpiPay tsAwardKpiPay = tsAwardKpiPayService.getTsAwardKpiPayByStatus(record);
			if(tsAwardKpiPay != null){
				response.setSuccess(true);
			}else{
				response.setSuccess(false);
			}

		} catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("查看本批次计件奖金是否提交错误，请稍后再试！");
		}
		return response;
	}
	
	
	@RequestMapping(value = "/isShowSatButton")
	@ResponseBody
	public AjaxResponse<String> isShowSatButton(HttpServletRequest request,	String belongMonth) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			
			TsAwardKpiPay record = new TsAwardKpiPay();			
			record.setStatus("1");
			record.setBelongMonth(DateUtil.strToFullDate(belongMonth));		
			
			TsAwardKpiPay tsAwardKpiPay = tsAwardKpiPayService.getTsAwardKpiPayByStatus(record);
			if(tsAwardKpiPay != null){
				response.setSuccess(true);
			}else{
				response.setSuccess(false);
			}

		} catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("查看本批次计件奖金是否提交错误，请稍后再试！");
		}
		return response;
	}
}
