package com.centaline.trans.award.web;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
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
	 * @desc:分批次个人奖金明细
	 */
	@RequestMapping(value = "/personBonusCollect")
	public String personBonusCollect(HttpServletRequest request) {
		
		
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		return "award/personBonusCollect";
	}
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:分批次案件奖金发放情况统计列表
	 */
	@RequestMapping(value = "/awardCaseCollect")
	public String awardCaseCollect(HttpServletRequest request) {
		
		
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
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
			tsAwardCaseCentalService.jumpToNewBonus(request);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求页面跳转异常，请稍后再试！");
		}		
		return "award/newBonus";
	}
	

}
