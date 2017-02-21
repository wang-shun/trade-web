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
	
	/**********************新链接************************************/
	@RequestMapping(value = "guoHuReport")
	public String guoHuReportCharts(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/case_GuoHu_charts";
		
	}
	@RequestMapping(value = "signReport")
	public String signReportCharts(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/case_Sign_charts";
		
	}
	@RequestMapping(value = "echartsData1")
	public String queryChart1(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData1";		
	}
	
	@RequestMapping(value = "echartsData2")
	public String queryChart2(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData2";	
	}
	
	@RequestMapping(value = "echartsData3")
	public String queryChart3(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData3";
	}
	
	@RequestMapping(value = "echartsData4")
	public String queryChart4(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData4";
	}
	
	@RequestMapping(value = "echartsData5")
	public String queryChart5(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData5";
	}
	
	@RequestMapping(value = "echartsData6")
	public String queryChart6(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData6";
	}
	
	@RequestMapping(value = "echartsData7")
	public String queryChart7(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData7";
	}
	
	@RequestMapping(value = "echartsData8")
	public String queryChart8(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/echartsReport/echartsData8";
	}
	/***
	 * 一周数据统计报表
	 */
	@RequestMapping(value = "weeklyData")
	public String weeklyData(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/weeklyReport/elist";
		
	}
	@RequestMapping(value = "elist1")
	public String queryElist1(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/weeklyReport/elist1";		
	}
	
	@RequestMapping(value = "elist2")
	public String queryElist2(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/weeklyReport/elist2";	
	}
	
	@RequestMapping(value = "elist3")
	public String queryElist3(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/weeklyReport/elist3";
	}
	
	@RequestMapping(value = "elist4")
	public String queryElist4(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/weeklyReport/elist4";
	}
	/***
	 * 签约贷款统计报表
	 */
	@RequestMapping(value = "signLoan")
	public String signLoan(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/signLoan/signLoan";
		
	}
	@RequestMapping(value = "sign1")
	public String sign1(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/signLoan/sign1";		
	}
	
	@RequestMapping(value = "sign2")
	public String sign2(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		request.setAttribute("serviceDepId", "ff8080814f459a78014f45a73d820006");
		return "report/signLoan/sign2";	
	}
	
	@RequestMapping(value = "sign3")
	public String sign3(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/signLoan/sign3";
	}
	
	@RequestMapping(value = "sign4")
	public String sign4(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/signLoan/sign4";
	}
	@RequestMapping(value = "sign5")
	public String sign5(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/signLoan/sign5";
	}
	
	@RequestMapping(value = "sign6")
	public String sign6(Model model, ServletRequest request){
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "report/signLoan/sign6";
	}
}
