package com.centaline.trans.report.web;


import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.task.entity.ReportOperateData;
import com.centaline.trans.task.service.ReportOperateDataService;

/**
 * ClassName: ReportOperateDataController <br/> 
 * Description: 大数据报表<br/>  
 * date: 2017年2月28日10:57:55 <br/> 
 * 
 * @author hejf 
 * @version  
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/operateData")
public class ReportOperateDataController {
	@Autowired(required = true)
	private ReportOperateDataService reportOperateDataService;
	
	/**
	 * 贷款签约与过户对比
	 * @author hejf10
	 * @date 2017年3月1日11:45:50
	 * @param model
	 * @param orgId
	 * @param year
	 * @return ToCaseInfoCountVoList
	 */
	@RequestMapping(value="operateOne")
	public AjaxResponse operateOne(Model model, ServletRequest request,String orgId,int year)
	{
		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
		try{
			List<List<ReportOperateData>> voList = reportOperateDataService.getReportOperateDataOne(year);
			model.addAttribute("voList", voList);
			result.setSuccess(true);
		 }catch(Exception e){
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			throw e;
		 }
		return result;
	}
	/**
	 * 过户数据
	 * @author hejf10
	 * @date 2017年3月1日11:45:50
	 * @param model
	 * @param orgId
	 * @param year
	 * @return ToCaseInfoCountVoList
	 */
	@RequestMapping(value="operateTwo")
	public AjaxResponse operateTwo(Model model, ServletRequest request,String orgId,int year)
	{
		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
		try{
			List<List<ReportOperateData>> voList = reportOperateDataService.getReportOperateDataTwo(year);
			model.addAttribute("voList", voList);
			result.setSuccess(true);
		 }catch(Exception e){
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			throw e;
		 }
		return result;
	}
	/**
	 * 签贷款数据
	 * @author hejf10
	 * @date 2017年3月1日11:45:50
	 * @param model
	 * @param orgId
	 * @param year
	 * @return ToCaseInfoCountVoList
	 */
	@RequestMapping(value="operateThree")
	public AjaxResponse operateThree(Model model, ServletRequest request,String orgId,int year)
	{
		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
		try{
			List<List<ReportOperateData>> voList = reportOperateDataService.getReportOperateDataThree(year);
			model.addAttribute("voList", voList);
			result.setSuccess(true);
		 }catch(Exception e){
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			throw e;
		 }
		return result;
	}
	
}
