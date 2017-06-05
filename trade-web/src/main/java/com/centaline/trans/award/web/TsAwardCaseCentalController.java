package com.centaline.trans.award.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.award.entity.TsAwardKpiPay;
import com.centaline.trans.award.service.KpiSrvCaseService;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.award.service.TsAwardKpiPayDetailService;
import com.centaline.trans.award.service.TsAwardKpiPayService;
import com.centaline.trans.award.service.TsKpiPsnMonthService;


@Controller
@RequestMapping(value = "/newAward")
public class TsAwardCaseCentalController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private TsKpiPsnMonthService tsKpiPsnMonthService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSesstionService;
	@Autowired
	private KpiSrvCaseService kpiSrvCaseService;
	@Autowired
	private TsAwardKpiPayDetailService tsAwardKpiPayDetailService;
	@Autowired
	private TsAwardKpiPayService tsAwardKpiPayService;	
	@Autowired
	TsAwardCaseCentalService 	tsAwardCaseCentalService;
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:分批次 个人奖金发放汇总
	 */
	@RequestMapping(value = "/personBonusCollect")
	public String personBonusCollect(HttpServletRequest request) {
		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}
		return "award/personBonusCollect";
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
			}				
		}catch (Exception e) {
			e.printStackTrace();			
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
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
	@RequestMapping(value = "/bonusConfiguration")
	public String bonusConfiguration(HttpServletRequest request) {
		
		try{
			tsAwardCaseCentalService.jumpToNewBonusJsp(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}		
		return "award/newMethodStep/managerStep0";
	}
	
}
