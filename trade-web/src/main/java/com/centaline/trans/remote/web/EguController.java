package com.centaline.trans.remote.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.mortgage.vo.ToEvaReportVo;
import com.centaline.trans.remote.service.EguService;
import com.centaline.trans.remote.vo.BankSearchVo;
import com.centaline.trans.remote.vo.DisagreeApplyVo;
import com.centaline.trans.remote.vo.HouseInfoVo;
import com.centaline.trans.remote.vo.MortgageAttamentVo;
import com.centaline.trans.remote.vo.PricingConfirmVo;

/**
 * E估接口
 * @author zhxf
 *
 */
@Controller
@RequestMapping(value="/remote/egu")
public class EguController {
		
	Logger logger = LoggerFactory.getLogger(EguController.class);
	
	@Autowired
	private EguService eguService; 
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	
	@RequestMapping(value="/test")  
	@ResponseBody
    public AjaxResponse<String> test(String token, String code) throws ClientProtocolException, IOException {
		
		String url = "http://www.asscol.com/api/v1/110213-160504-007 /upload?token=de57ac3356536bb82fbab351552edf1d167a6595&case_id=ZY-AJ-201604-1014 &code=110213-160504-007 &files=W3sidHlwZSI6IjEiLCJmaWxlX2lkcyI6W3siZmlsZV9pZCI6IjhhODQ5M2Q0NTQzZGRkNmUwMTU0N2ZkYjliMTIxZGZhIiwiZmlsZV90eXBlIjoiYm1wIiwiYWRkX29yX2RlbCI6MX1dfSx7InR5cGUiOiIyIiwiZmlsZV9pZHMiOlt7ImZpbGVfaWQiOiI4YTg0OTNkNTUzOGM5YWM4MDE1NDdmZGI5ZjM1NmUyYiIsImZpbGVfdHlwZSI6ImpwZyIsImFkZF9vcl9kZWwiOjF9LHsiZmlsZV9pZCI6IjhhODQ5M2Q0NTM4Yzk4ODcwMTU0N2ZkYmJjODU3MDBmIiwiZmlsZV90eXBlIjoianBnIiwiYWRkX29yX2RlbCI6MX0seyJmaWxlX2lkIjoiOGE4NDkzZDQ1MzhjOTg4NzAxNTQ3ZmRiZDcwMzcwMTAiLCJmaWxlX3R5cGUiOiJqcGciLCJhZGRfb3JfZGVsIjoxfV19LHsidHlwZSI6IjMiLCJmaWxlX2lkcyI6W3siZmlsZV9pZCI6IjhhODQ5M2Q0NTQzZGRkNmUwMTU0N2ZkYmVlOWIxZGZiIiwiZmlsZV90eXBlIjoianBnIiwiYWRkX29yX2RlbCI6MX0seyJmaWxlX2lkIjoiOGE4NDkzZDU1MzhjOWFjODAxNTQ3ZmRjMGFiMDZlMmMiLCJmaWxlX3R5cGUiOiJqcGciLCJhZGRfb3JfZGVsIjoxfV19XQ==&nonce=4044&timestamp=1462435101984&un=qianll03";
		HttpResponse httpResponse = executeGet(url);
		return AjaxResponse.success();
    }
	
	private HttpResponse executeGet(String queryUrl)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		//HttpGet get = new HttpGet("http://stage.vcainfo.com/v1/" + queryUrl);
		HttpGet get = new HttpGet("http://www.asscol.com/api/v1/" + queryUrl);
		if(logger.isInfoEnabled()){
			logger.info("QueryEgu:"+"http://www.asscol.com/api/v1/" + queryUrl);
		}

		get.addHeader("vc-user-key","20918");
		return client.execute(get);
	}

	
	private HttpClient createHttpClient(){
		// 设置Base Auth验证信息
		CredentialsProvider provider = new BasicCredentialsProvider();
		SessionUser u = uamSessionService.getSessionUser();
		User user = uamUserOrgService.getUserById(u.getId());
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				user.getUsername(), user.getPassword());
		provider.setCredentials(AuthScope.ANY, credentials);
		// 创建HttpClient
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();
		return client;
	}
	/**
	 * 询价
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/assess")  
	@ResponseBody
    public AjaxResponse<String> assess(HouseInfoVo houseInfo, HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			eguService.assess(houseInfo);
			response.setMessage("询价申请成功！");
		}catch(BusinessException e){
			response.setSuccess(false);
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
		}catch(Exception e1){
			response.setSuccess(false);
			response.setMessage(e1.getMessage());
			e1.printStackTrace();
		}
		return response;
  
    }
	
	/**
	 * 询价价格异议申请
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/disagree")  
	@ResponseBody
    public AjaxResponse<String> disagree(DisagreeApplyVo disagreeApply, HttpServletRequest request,@PathVariable String code) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			eguService.disagree(disagreeApply);
			response.setMessage("发起异议成功！");
		}catch(Exception e){
			response.setMessage(e.getMessage());
			response.setSuccess(false);

		}
		return response;
  
    }
	
	/**
	 * 确认询价结果
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/confirm")  
	@ResponseBody
    public AjaxResponse<String> confirm(PricingConfirmVo pricingConfirm, HttpServletRequest request,@PathVariable String code) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{

			eguService.confirm(pricingConfirm);
			response.setMessage("确认询价结果成功！");
		}catch(Exception e){
			response.setMessage(e.getMessage());
			response.setSuccess(false);

		}
        return response;
  
    }

	/**
	 * 发起预估单
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/prereport")  
	@ResponseBody
    public AjaxResponse<String> prereport(ToEvaReportVo evaReport,MortgageAttamentVo mortgageAttament,  HttpServletRequest request,@PathVariable String code) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
				
			eguService.prereport(evaReport, mortgageAttament);
			response.setMessage("发起预估单成功！");
		}catch(Exception e){
			response.setMessage(e.getMessage());
			response.setSuccess(false);

		}
        return response;
    }
	
	/**
	 * 发起询价单
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/inquiryreport")  
	@ResponseBody
    public AjaxResponse<String> inquiryreport(ToEvaReportVo evaReport,MortgageAttamentVo mortgageAttament, HttpServletRequest request,@PathVariable String code) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
						
			eguService.inquiryreport(evaReport, mortgageAttament);
			response.setMessage("发起询价单成功！");
		}catch(Exception e){
			response.setMessage(e.getMessage());
			response.setSuccess(false);
		}
        return response;
  
    }
	
	/**
	 * 发起评估报告
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/report")  
	@ResponseBody
    public AjaxResponse<String> report(ToEvaReportVo evaReport,MortgageAttamentVo mortgageAttament, HttpServletRequest request,@PathVariable String code) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{		
			
			eguService.report(evaReport, mortgageAttament);
			response.setMessage("发起评估报告成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
        return response;
  
    }
	
	/**
	 * 查询egu银行信息保存
	 * @param bankSearch
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bank")  
	@ResponseBody
    public AjaxResponse<String> bank(BankSearchVo bankSearch, HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{

			eguService.saveEguBank(bankSearch);
			response.setMessage("保存egu银行信息成功！");
		}catch(Exception e){
			response.setMessage(e.getMessage());
		}
		return response;
  
    }
	
}
