package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.ToTradeChangedCaseService;
import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;

/**
 * 交易计划变更控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping(value="/report")
public class DealChangeCaseController {
	
	@Resource
	ToTradeChangedCaseService toTradeChangedCaseService;
	
	/**
	 * 交易计划变更案件列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="dealChangeCaseList")
	public String dealChangeCaseList(Model model){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		//获取当前月最后一天
		Calendar ca = Calendar.getInstance();    
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		
		
		model.addAttribute("curMonthStart", sdf.format(c.getTime()));
		model.addAttribute("curMonthEnd", sdf.format(ca.getTime()));
		return "report/dealChangeList";
	}
	
	/**
	 * 新增案件回访
	 * @param caseReturnVisitRegistrationVO
	 * @return
	 */
	@RequestMapping(value="addReturnVisit")
	@ResponseBody
	public AjaxResponse<T> addReturnVisit(CaseReturnVisitRegistrationVO caseReturnVisitRegistrationVO){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			toTradeChangedCaseService.addReturnVisit(caseReturnVisitRegistrationVO);
			response.setCode("400");
			response.setMessage("案件回访处理成功！");
			response.setSuccess(true);
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("案件回访处理失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	
	
}
