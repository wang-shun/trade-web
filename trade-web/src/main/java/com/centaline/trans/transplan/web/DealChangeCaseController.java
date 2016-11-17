package com.centaline.trans.transplan.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;

/**
 * 交易计划变更控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping(value="/transplan")
public class DealChangeCaseController {
	
	@Resource
	TransplanServiceFacade toTransplanOperateService;
	
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
		return "transplan/dealChangeList";
	}
	
	/**
	 * 新增案件回访
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	@RequestMapping(value="addReturnVisit")
	@ResponseBody
	public AjaxResponse<T> addReturnVisit(TtsReturnVisitRegistration ttsReturnVisitRegistration){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			toTransplanOperateService.addReturnVisit(ttsReturnVisitRegistration);
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
	 * 查询交易变更历史信息
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	@RequestMapping(value="queryTtsTransPlanHistorys")
	@ResponseBody
	public AjaxResponse<List<TsTransPlanHistoryVO>> queryTtsTransPlanHistorys(TsTransPlanHistoryVO tsTransPlanHistoryVO){
		AjaxResponse<List<TsTransPlanHistoryVO>> response = new AjaxResponse<List<TsTransPlanHistoryVO>>();
		List<TsTransPlanHistoryVO>  ttp = null;
		try{
			ttp =  toTransplanOperateService.queryTtsTransPlanHistorys(tsTransPlanHistoryVO);
			response.setCode("400");
			response.setMessage("查询交易变更历史成功！");
			response.setSuccess(true);
			response.setContent(ttp);
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("查询交易变更历史失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	
	
}