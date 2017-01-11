package com.centaline.trans.report.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.centaline.trans.common.enums.AppTypeEnum;

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
	@Autowired
	private UamPermissionService uamPermissionService;
	
	@RequestMapping(value = "charts")
	public String queryCharts(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/case_baseinfo_charts";
		
	}
	
	@RequestMapping(value = "echartsData1")
	public String queryChart1(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData1";		
	}
	
	@RequestMapping(value = "echartsData2")
	public String queryChart2(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData2";	
	}
	
	@RequestMapping(value = "echartsData3")
	public String queryChart3(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData3";
	}
	
	@RequestMapping(value = "echartsData4")
	public String queryChart4(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData4";
	}
	
	@RequestMapping(value = "echartsData5")
	public String queryChart5(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData5";
	}
	
	@RequestMapping(value = "echartsData6")
	public String queryChart6(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData6";
	}
	
	@RequestMapping(value = "echartsData7")
	public String queryChart7(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData7";
	}
	
	@RequestMapping(value = "echartsData8")
	public String queryChart8(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsData8";
	}
}
