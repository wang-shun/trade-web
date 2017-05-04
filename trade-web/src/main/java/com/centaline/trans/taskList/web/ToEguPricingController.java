package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;
import com.centaline.trans.mortgage.enums.Whether;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEguPropertyInfoService;

@Controller
@RequestMapping(value="/task")
public class ToEguPricingController {
	
	@Autowired
	private ToEguPricingService toEguPricingService;
		
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	
	@Autowired
	private ToEguPropertyInfoService toEguPropertyInfoService;
	
	@Autowired
	private TsFinOrgService tsFinOrgService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	/**
	 * 移动端询价申请页面
	 * @return
	 */
	@RequestMapping(value="/mobile/pricingApply")  
    public String pricingApply() {

		return "mobile/task/eguPricingApply";
    }
	
	/**
	 * 移动端询价列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mobile/eguPricingList")  
    public String eguPricingList(Model model) {
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("ariserId", user.getId());
		return "mobile/task/eguPricingList";
    }
	
	/**
	 * 移动端报告列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mobile/reportList")  
    public String reportList(Model model) {
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("ariserId", user.getId());
		return "mobile/task/reportList";
    }
	
	/**
	 * 移动端询价详情页面
	 * @param evaCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mobile/pricingInfo")  
    public String pricingInfo(String evaCode,Model model) {
		List<ToEguPricing> toEguPricingList = toEguPricingService.findToEguPricingByEvaCode(evaCode);
		if(CollectionUtils.isNotEmpty(toEguPricingList)){
			ToEguPricing toEguPricing = toEguPricingList.get(0);
			ToEguPropertyInfo toEguPropertyInfo = toEguPropertyInfoService.findByEvaCode(toEguPricing.getEvaCode());
			TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(toEguPricing.getFinOrgCode());
			if(tsFinOrg != null){
				toEguPricing.setFinOrgCode(tsFinOrg.getFinOrgName());
			}
			model.addAttribute("toEguPricing", toEguPricing);
			model.addAttribute("toEguPropertyInfo", toEguPropertyInfo);
			//getReportUploadList(model);
			model.addAttribute("taskitem", "ToEvaReport");
			model.addAttribute("caseCode", toEguPricing.getCaseCode());
			

		}

		return "mobile/task/eguPricingInfo";
    }
	
	/**
	 * 接受询价结果
	 * @param evaCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/acceptPricingResult")  
	@ResponseBody
    public AjaxResponse<String> acceptPricingResult(Long pkid,HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			
			ToEguPricing toEguPricing = toEguPricingService.findById(pkid);
			ToEguPricing eguPricing = toEguPricingService.findIsFinalEguPricing(toEguPricing.getCaseCode());
			if(eguPricing != null){
				response.setSuccess(false);
				response.setMessage("该案件已有接受询价结果记录，请先取消后再接受新的询价结果！");
				return response;
			}
			toEguPricing.setIsFinal(Whether.IS.getCode());
			toEguPricingService.saveToEguPricing(toEguPricing);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	/**
	 * 取消接受询价结果
	 * @param pkid
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/cancelAccept")  
	@ResponseBody
    public AjaxResponse<String> cancelAccept(Long pkid,HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			
			ToEguPricing toEguPricing = toEguPricingService.findById(pkid);
			
			toEguPricing.setIsFinal(Whether.NO.getCode());
			toEguPricingService.saveToEguPricing(toEguPricing);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	/**
	 * 查询案件物业信息作为询价时的初始信息
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findToPropertyInfo")  
	@ResponseBody
    public AjaxResponse<ToPropertyInfo> findToPropertyInfo(String caseCode,HttpServletRequest request) {

		AjaxResponse<ToPropertyInfo> response = new AjaxResponse<ToPropertyInfo>();
		try{
			
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
			
			response.setContent(toPropertyInfo);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("查询物业信息失败！");
		}
        return response;
    }
	
	/**
	 * 绑定案件编号和评估编号
	 * @param toEguPricing
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bindEvaCode")  
	@ResponseBody
    public AjaxResponse<String> bindEvaCode(ToEguPricing toEguPricing,HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//如果选择接受询价结果，判断是否已接受过询价结果
			if("1".equals(toEguPricing.getIsFinal())){
				ToEguPricing eguPricing = toEguPricingService.findIsFinalEguPricing(toEguPricing.getCaseCode());
				if(eguPricing != null){
					response.setSuccess(false);
					response.setMessage("该案件已有接受询价结果记录，请先取消后再接受新的询价结果！");
					return response;
				}
			}
			toEguPricingService.bindEvaCode(toEguPricing);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	
	@Deprecated
    private void getReportUploadList(Model model) {

		List<ToAccesoryList> list = new ArrayList<ToAccesoryList>();
		List<Long> idList = new ArrayList<Long>();

		for(int i = 1;i < 4;i++){
			ToAccesoryList accesoryList = new ToAccesoryList();
			accesoryList.setPartCode("ToEvaReport");
			if(i == 1){
				accesoryList.setAccessoryName("身份证");
				accesoryList.setAccessoryCode("id_card");
			}
			if(i == 2){
				accesoryList.setAccessoryName("房产证");
				accesoryList.setAccessoryCode("house_card");
			}
			if( i == 3){
				accesoryList.setAccessoryName("购房合同");
				accesoryList.setAccessoryCode("buy_contract");
			}
			accesoryList.setPkid(new Long(i-4));
			idList.add(new Long(i-4));
			list.add(accesoryList);
		}
		model.addAttribute("accesoryList", list);
		model.addAttribute("idList", idList);

    } 
}
