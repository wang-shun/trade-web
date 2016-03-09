package com.centaline.trans.remote.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
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
