package com.centaline.trans.report.web;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: caseBaseInfoChartsController <br/> 
 * Description: 案件统计报表 (1~8)<br/>  
 * date: 2017年1月10日 下午4:50:30 <br/> 
 * 
 * @author gongjd 
 * @version  
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/report")
public class caseBaseInfoChartsController {
	
	@RequestMapping(value = "charts")
	public String queryCharts(Model model, ServletRequest request){
		
		return "report/case_baseinfo_charts";
		
	}
	
	@RequestMapping(value = "echartsData1")
	public String queryChart1(Model model, ServletRequest request){
		
		return "report/echartsData1";
		
	}
	

}
