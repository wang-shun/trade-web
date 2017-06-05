package com.centaline.trans.income.web;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.income.service.KpiCalculateService;


@Controller
@RequestMapping(value="/kpi")
public class KpiCalculateController {

	@Autowired
	private KpiCalculateService kpicalculateService;
	
	
	/**
	 * 功能：根据不同的角色去计算 kpi
	 * 作者：zhangxb16
	 * @param roleCode [角色名称]
	 * @param score [满意度评分]
	 * @param phoneRate [电话准确率]
	 * @param financialCount [金融产品]
	 * @param signedCount [组别月签约单数]
	 * @param outflowRate [流失率]
	 */
	@RequestMapping(value="calculateKpi", method={RequestMethod.POST})
	@ResponseBody
	public AjaxResponse calculateKpi(HttpServletRequest request, HttpServletResponse response){
		
		String roleCode=request.getParameter("roleCode");
		String score=request.getParameter("score");
		String phoneRate=request.getParameter("phoneRate");
		String financialCount=request.getParameter("financialCount");
		String signedCount=request.getParameter("signedCount");
		String outflowRate=request.getParameter("outflowRate");
		
		double kpi=0;
		String result=null;
		DecimalFormat df=new DecimalFormat("######0.0");  // 保留2位小数
		try{
			kpi=kpicalculateService.calculateKpi(roleCode, Double.parseDouble(score), Double.parseDouble(phoneRate), Integer.parseInt(financialCount), Integer.parseInt(signedCount), Double.parseDouble(outflowRate));
			result=df.format(kpi);
		}catch(BusinessException ex){
			return AjaxResponse.fail(ex.getMessage());
		}
		
		return AjaxResponse.successContent(result);
	}
	
	
	@RequestMapping("kpijsp")
	public String testkpi(){
		
		return "income/calculateKpi";
	}
	
}
