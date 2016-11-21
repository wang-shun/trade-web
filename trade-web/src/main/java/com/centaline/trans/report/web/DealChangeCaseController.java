package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.ToTradeChangedCaseService;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

/**
 * 交易计划变更控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping(value="/transplan")
public class DealChangeCaseController {
	
	@Resource
	ToTradeChangedCaseService toTradeChangedCaseService;
	
	@Resource
	TransplanServiceFacade transplanServiceFacade;
	

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
	public AjaxResponse<TtsReturnVisitRegistration> addReturnVisit(TtsReturnVisitRegistration ttsReturnVisitRegistration){
		AjaxResponse<TtsReturnVisitRegistration> response = new AjaxResponse<TtsReturnVisitRegistration>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = Calendar.getInstance().getTime();
			ttsReturnVisitRegistration.setCreateTime(sdf.format(date));
			ttsReturnVisitRegistration.setCrtTime(date);
			transplanServiceFacade.addReturnVisit(ttsReturnVisitRegistration);
			response.setContent(ttsReturnVisitRegistration);
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
	
	/**
	 * 查询回访跟进历史信息
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	@RequestMapping(value="queryReturnVisitHistorys")
	@ResponseBody
	public AjaxResponse<List<TtsReturnVisitRegistration>> queryReturnVisitHistorys(long batchId){
		AjaxResponse<List<TtsReturnVisitRegistration>> response = new AjaxResponse<List<TtsReturnVisitRegistration>>();
		List<TtsReturnVisitRegistration>  ttp = null;
		try{
			ttp =  transplanServiceFacade.queryReturnVisitRegistrations(batchId);
			response.setCode("400");
			response.setMessage("查询回访跟进历史成功！");
			response.setSuccess(true);
			response.setContent(ttp);
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("查询回访跟进历史失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	
}
