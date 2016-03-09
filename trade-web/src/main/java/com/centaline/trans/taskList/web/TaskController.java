/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.taskList.web;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.loan.service.ToCloseLoanService;
import com.centaline.trans.mortgage.entity.MortStep;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.service.FirstFollowService;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.service.OfflineEvaService;
import com.centaline.trans.task.service.PSFSignService;
import com.centaline.trans.task.service.ServiceChangeService;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.ToGetPropertyBookService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.ToPricingService;
import com.centaline.trans.task.service.ToPurchaseLimitSearchService;
import com.centaline.trans.task.service.ToTaxService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.team.service.TsTeamPropertyService;


@Controller
@RequestMapping(value="/task")
public class TaskController {

	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	@Autowired
	private SignService signService;/*签约*/
	@Autowired
	private ToTransPlanService toTransPlanService;/*填写交易计划*/
	@Autowired
	private ToPricingService toPricingService;/**核价*/
	@Autowired
	private ToPurchaseLimitSearchService toPurchaseLimitSearchService;/**查限购*/
	@Autowired
	private ToTaxService toTaxService;/**审税*/
	@Autowired
	private ToHouseTransferService toHouseTransferService;/**过户*/
	
	@Autowired
	private TsTeamPropertyService tsTeamPropertyService;/*组别属性*/

	@Autowired
	private ToAttachmentService toAttachmentService;
	@Autowired(required = true)
	private UamSessionService uamSessionService;/*用户信息*/
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;/*用户组织信息*/
	@Autowired
	private FirstFollowService firstFollowService;/*首次跟进*/
	@Autowired
	private ToCloseLoanService toCloseLoanService;/*贷款结清*/
	@Autowired
	private ToMortgageService toMortgageService;/*按揭贷款表*/
	@Autowired
	private PSFSignService psfSignService;/*纯公积金贷款签约*/
	@Autowired
	private ToGetPropertyBookService toGetPropertyBookService;/*领证*/
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private OfflineEvaService offlineEvaService;
	@Autowired
	private ToEvaReportService toEvaReportService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToEguPricingService toEguPricingService;
	
	@Autowired
	private EditCaseDetailService editCaseDetailService;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	
	@Autowired
	private ServiceChangeService serviceChangeService;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Autowired
	private ToSpvService toSpvService; 
	
	@Autowired
	private MortStepService mortStepService;
	
	@Autowired
	private ToCaseInfoService tocaseInfoService;
	
	@Autowired
    private UamPermissionService       uamPermissionService;
	
    @RequestMapping(value="/{taskitem}")   
    public String taskPageRoute(Model model, HttpServletRequest request,@PathVariable String taskitem,
    		String caseCode, String taskId, String instCode) {
    	request.setAttribute("taskId", taskId);
    	request.setAttribute("processInstanceId", instCode);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("taskitem", taskitem);
		
		// 根据caseCode 去查询 ctmCode  作者：zhangxb16
		String ctmCode=null;
		ToCaseInfo caseinfo=tocaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if(null!=caseinfo){
			ctmCode=caseinfo.getCtmCode();
		}
		
    	if(taskitem.equals("TransSign")) {//签约，读取数据
    		getAccesoryList(request, taskitem);
    		request.setAttribute("transSign", signService.qureyGuestInfo(caseCode));
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		
    	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
    	    model.addAttribute("imgweb", app.genAbsoluteUrl());
    	} else if(taskitem.equals("TransPlanFilling")) {//填写交易计划
    		/*getAccesoryList(request, taskitem);*/
    		RestVariable dy = workFlowManager.getVar(instCode, "LoanCloseNeed");/*抵押*/
    		
    		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");/*公积金*/
    		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");/*自办*/
    		RestVariable com = workFlowManager.getVar(instCode, "ComLoanNeed");/*贷款*/
    		boolean dk =  ((boolean)(psf==null?false:psf.getValue())||(boolean)(self==null?false:self.getValue())||(boolean)(com==null?false:com.getValue()));
    		request.setAttribute("dy", dy==null?false:dy.getValue());
    		request.setAttribute("dk", dk);
    		request.setAttribute("transPlan", toTransPlanService.findTransPlanByCaseCode(caseCode));
    	} else if(taskitem.equals("FirstFollow")) {//首次跟进
    		initApproveRecord(request, caseCode, "0");/*无效审批*/
    		request.setAttribute("ctmCode", ctmCode);
        	request.setAttribute("firstFollow", firstFollowService.queryFirstFollow(caseCode));
    	} else if(taskitem.equals("PurchaseLimit")){/*查限购*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("purchaseLimit", toPurchaseLimitSearchService.findToPlsByCaseCode(caseCode));
    	} else if(taskitem.equals("Pricing")){/*核价*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("pricing", toPricingService.qureyPricing(caseCode));
    	} else if(taskitem.equals("TaxReview")){/*审税*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("taxReview", toTaxService.findToTaxByCaseCode(caseCode));
    	} else if(taskitem.equals("Guohu")){/*过户*/
        	/*过户申请信息*/
        	initApproveRecord(request, caseCode, "2");
    		getAccesoryListGuoHu(request, taskitem, caseCode);
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
    		request.setAttribute("toMortgage", toMortgage);
    	} else if(taskitem.equals("LoanClose")) {/*贷款结清*/
    		request.setAttribute("loanClose", toCloseLoanService.qureyToCloseLoan(caseCode));
    	} else if(taskitem.equals("PSFApply")) {/*纯公积金贷款申请*/
    		getAccesoryList(request, taskitem);
    		ToTransPlan toTransPlan = new ToTransPlan();
    		toTransPlan.setPartCode(taskitem);
    		toTransPlan.setCaseCode(caseCode);
    		request.setAttribute("toTransPlan", toTransPlanService.findTransPlan(toTransPlan));
    		request.setAttribute("apply", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));//--
    	} else if(taskitem.equals("PSFSign")) {/*纯公积金贷款签约*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("PSFSign", psfSignService.queryPSFSignNoBlank(caseCode));
    	} else if(taskitem.equals("PSFApprove")) {/*纯公积金贷款审批*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("PSFApprove", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));//--30016003
    	} else if(taskitem.equals("HouseBookGet")) {/*领证*/
    		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");/*公积金*/

    		// add zhangxb16 2016-2-22
    		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");/*自办*/
    		RestVariable com = workFlowManager.getVar(instCode, "ComLoanNeed");/*贷款*/
    		
//    		request.setAttribute("psf", (boolean)(psf==null?false:psf.getValue()));
    		getAccesoryListLingZheng(request, taskitem, (boolean)(psf==null?false:psf.getValue()), (boolean)(self==null?false:self.getValue()), (boolean)(com==null?false:com.getValue()));
    		request.setAttribute("tgpb", toGetPropertyBookService.queryToGetPropertyBook(caseCode));
    	} else if(taskitem.equals("LoanRelease")) {/*放款*/
    		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");/*公积金*/
//    		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");/*自办*/
//    		boolean tz =  ((boolean)(psf==null?false:psf.getValue())||(boolean)(self==null?false:self.getValue()));
    		request.setAttribute("tz", !(boolean)(psf==null?false:psf.getValue()));
    		getAccesoryList(request, taskitem);
    		request.setAttribute("loanRelease", toMortgageService.findToMortgageByCaseCode2(caseCode));
    	} else if(taskitem.equals("SelfLoanApprove")) {/*SelfLoanApprove 自办贷款审批*/
    		request.setAttribute("SelfLoan", toMortgageService.findToSelfLoanMortgage(caseCode));
    	}else if(taskitem.equals("ComLoanProcess")){
    		getAccesoryLists(request, taskitem);
    		MortStep mortStep = new MortStep();
    		mortStep.setCaseCode(caseCode);
    		Integer[] step = mortStepService.getMortStep(caseCode);
    		request.setAttribute("step", step[0]);
        	request.setAttribute("step1", step[1]);
    	
    		ToEguPricing toEguPricing = toEguPricingService.findIsFinalEguPricing(caseCode);
    		if(toEguPricing != null){
        		request.setAttribute("isMainLoanBank", toEguPricing.getIsMainLoanBank());
        		request.setAttribute("evaCode", toEguPricing.getEvaCode());
    		}else{
        		request.setAttribute("isMainLoanBank", "1");
        		request.setAttribute("evaCode", "");
    		}
    		
    	} else if(taskitem.equals("LoanlostApply")){/*贷款流失申请*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("mortgage", toMortgageService.findToMortgageByCaseCode2(caseCode));
        	/*贷款流失初始信息*/
        	initApproveRecord(request, caseCode, "1");
    	}else if(taskitem.equals("EvaReportArise")){
    		getAccesoryList(request, taskitem);
    		request.setAttribute("toEguPricing", toEguPricingService.findIsFinalEguPricing(caseCode));	
    	} else if(taskitem.equals("OfflineEva")) {/*线下评估报告发起*/
    		request.setAttribute("OfflineEva", offlineEvaService.queryOfflineEvaVO(instCode));
    		request.setAttribute("evaReport", toEvaReportService.findByProcessId(instCode));
    	} else if(taskitem.equals("LoanlostApproveManager") || 
    			taskitem.equals("LoanlostApproveDirector") || taskitem.equals("LoanlostApproveGeneralManager")) {
    		request.setAttribute("caseDetail", loanlostApproveService.queryCaseInfo(caseCode,"LoanlostApply"));
    		String approveType = "1";/*流失审批*/
    		initApproveRecord(request, caseCode, approveType);
    	} else if(taskitem.equals("CaseCloseThirdApprove") || 
    			taskitem.equals("CaseCloseFirstApprove") || taskitem.equals("CaseCloseSecondApprove")) {/*结案审批*/
    		initApproveRecord(request, caseCode, "3");
    	} else if(taskitem.equals("InvalidCaseApprove")) {
    		initApproveRecord(request, caseCode, "0");/*无效审批*/
    	} else if(taskitem.equals("GuohuApprove")) {
    		initApproveRecord(request, caseCode, "2");/*过户审批*/
    	} else if(taskitem.equals("CaseClose")) {/*结案审批，验证数据是否正确*/
    		initApproveRecord(request, caseCode, "3");
    		getAccesoryListCaseClose(request, caseCode);
    		request.setAttribute("editCaseDetailVO", editCaseDetailService.queryCaseDetai(caseCode));
    	} else if(taskitem.equals("ServiceChangeApply")) {/*服务项变更*/
    		if(instCode == null && caseCode != null) {
        		ToWorkFlow toWorkFlow = new ToWorkFlow();
        		toWorkFlow.setCaseCode(caseCode);
        		toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
        		instCode = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlow).getInstCode();
            	request.setAttribute("processInstanceId", instCode);
        	}
    		initApproveRecord(request, caseCode, "4");
    		request.setAttribute("list", serviceChangeService.queryDelServChangeHistroty(caseCode));
    		request.setAttribute("addServ", serviceChangeService.queryAddServChangeHistroty(caseCode));
    	} else if(taskitem.equals("ServiceChangeApprove")) {/*服务项审批*/
    		initApproveRecord(request, caseCode, "4");
    		request.setAttribute("newxm", serviceChangeService.qureyServChangeHistrotyInfo(caseCode).get("newServChange"));
    		request.setAttribute("delxm", serviceChangeService.qureyServChangeHistrotyInfo(caseCode).get("delServChange"));
    	}else if(taskitem.equals("spvOutApply")) {/*资金监管解除申请*/
    		getAccesoryList(request, taskitem);
        	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);

    		request.setAttribute("toSpv", toSpv);
    		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);

    		request.setAttribute("spvDeRecVo", spvDeRecVo);
    	}else if(taskitem.equals("spvOutApprove")) {/*资金监管解除审批*/
    		getAccesoryList(request, taskitem);
    		
    		initApproveRecord(request, caseCode, "6");
    		
    		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
    		
    		request.setAttribute("spvDeRecVo", spvDeRecVo);
    	}else if("serviceRestartApply".equals(taskitem)||"serviceRestartApprove".equals(taskitem)){
    		initApproveRecord(request, caseCode, "7");
     		if(instCode == null && caseCode != null) {
        		ToWorkFlow toWorkFlow = new ToWorkFlow();
        		toWorkFlow.setCaseCode(caseCode);
        		toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
        		instCode = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlow).getInstCode();
        		request.setAttribute("processInstanceId", instCode);
        	}
     		
    	}else if("MortgageSelect".equals(taskitem)){
    		
    	}
        return "task/task"+taskitem;
  
    }
    
    @RequestMapping(value="/mobile/{taskitem}")   
    public String taskMobilePageRoute(Model model, HttpServletRequest request,@PathVariable String taskitem,
    		String caseCode, String taskId, String instCode) {
    	request.setAttribute("taskId", taskId);
    	request.setAttribute("processInstanceId", instCode);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("taskitem", taskitem);
		if(taskitem.equals("PurchaseLimit")){/*查限购*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("purchaseLimit", toPurchaseLimitSearchService.findToPlsByCaseCode(caseCode));
    	} else if(taskitem.equals("Pricing")){/*核价*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("pricing", toPricingService.qureyPricing(caseCode));
    	} else if(taskitem.equals("TaxReview")){/*审税*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("taxReview", toTaxService.findToTaxByCaseCode(caseCode));
    	} else if(taskitem.equals("Guohu")){/*过户*/
        	/*过户申请信息*/
        	initApproveRecord(request, caseCode, "2");
    		getAccesoryListGuoHu(request, taskitem, caseCode);
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
    		request.setAttribute("toMortgage", toMortgage);
    	} else if(taskitem.equals("PSFApply")) {/*纯公积金贷款申请*/
    		getAccesoryList(request, taskitem);
    		ToTransPlan toTransPlan = new ToTransPlan();
    		toTransPlan.setPartCode(taskitem);
    		toTransPlan.setCaseCode(caseCode);
    		request.setAttribute("toTransPlan", toTransPlanService.findTransPlan(toTransPlan));
    		request.setAttribute("apply", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));
    	} else if(taskitem.equals("PSFSign")) {/*纯公积金贷款签约*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("PSFSign", psfSignService.queryPSFSignNoBlank(caseCode));
    	}else if(taskitem.equals("ComLoanProcess")){ //商贷保存
    		getAccesoryLists(request, taskitem);
    	}  else if(taskitem.equals("HouseBookGet")) {/*领证*/
    		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");/*公积金*/
    		
    		// add zhangxb16 2016-2-22
    		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");/*自办*/
    		RestVariable com = workFlowManager.getVar(instCode, "ComLoanNeed");/*贷款*/
    		
//    		request.setAttribute("psf", (boolean)(psf==null?false:psf.getValue()));
    		getAccesoryListLingZheng(request, taskitem, (boolean)(psf==null?false:psf.getValue()), (boolean)(self==null?false:self.getValue()), (boolean)(com==null?false:com.getValue()));
    		request.setAttribute("tgpb", toGetPropertyBookService.queryToGetPropertyBook(caseCode));
    	} 
        return "mobile/task/task"+taskitem;
  
    }
    
    /**
     * 审批记录初始化
     * @param request
     * @param taskitem
     * @param caseCode
     * @param approveType
     */
    private void initApproveRecord(HttpServletRequest request, String caseCode, String approveType) {
		SessionUser user = uamSessionService.getSessionUser();
    	ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setCaseCode(caseCode);
		toApproveRecord.setApproveType(approveType);
		toApproveRecord.setOperator(user.getId());
//		request.setAttribute("toApproveRecord", toApproveRecordService.queryToApproveRecord(toApproveRecord));
		request.setAttribute("approveType", approveType);
		request.setAttribute("operator", user != null ? user.getId():"");
    }
    
    /**
     * 页面导航栏链接跳转
     * @param request
     * @param caseCode
     * @return
     */
    @RequestMapping(value="caseDetail")
    public String caseDetail(HttpServletRequest request, String caseCode) {
    	ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
    	String st = null;
    	if(toCase != null) {
    		st = "redirect:/case/caseDetail?&caseId="+toCase.getPkid();
    	} else {
    		st = "redirect:/case/caseDetail";
    	}
    	return st;
    }
    
    /**
     * 读取上传附件备件表
     * @param request
     * @param taskitem
     */
    private void getAccesoryList(HttpServletRequest request, String taskitem) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
    }
    
    /**
     * 读取上传附件备件表-过户
     * @param request
     * @param taskitem
     */
    private void getAccesoryListLingZheng(HttpServletRequest request, String taskitem, boolean psf, boolean self, boolean com) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		List<ToAccesoryList> removeList = new ArrayList<ToAccesoryList>();
		/*根据需求调整附件上传项目*/
		for(ToAccesoryList tal:list) {
			if(psf && (tal.getAccessoryCode().equals("third_part_right_cert") || tal.getAccessoryCode().equals("new_house_book"))) {/*公积金*/
				removeList.add(tal);
			}
			
			// add zhangxb16 2016-2-22
			if(tal.getAccessoryCode().equals("third_part_right_cert") && psf==false && self==false && com==false){
				removeList.add(tal);
			}
		}
		for(ToAccesoryList tal:removeList) {
			list.remove(tal);
		}
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
    }
    
    /**
     * 读取上传附件备件表-过户
     * @param request
     * @param taskitem
     */
    private void getAccesoryListGuoHu(HttpServletRequest request, String taskitem, String caseCode) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		List<ToAccesoryList> removeList = new ArrayList<ToAccesoryList>();
		/*根据需求调整附件上传项目*/
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		for(ToAccesoryList tal:list) {
			if((toMortgage == null || toMortgage.getMortType() == null) && (tal.getAccessoryName().equals("抵押登记表") || tal.getAccessoryName().equals("商贷利率页"))) {/*无贷款*/
				removeList.add(tal);
			} else if(toMortgage != null && toMortgage.getMortType().equals("30016003") && tal.getAccessoryName().equals("商贷利率页")) {
				removeList.add(tal);
			}
		}
		for(ToAccesoryList tal:removeList) {
			list.remove(tal);
		}
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
    }
    
    /**
     * 读取上传附件备件表-结案
     * @param request
     * @param taskitem
     */
    private void getAccesoryListCaseClose(HttpServletRequest request, String caseCode) {
    	List<ToAttachment> attachments = toAttachmentService.findToAttachmentByCaseCode(caseCode);
		List<ToAccesoryList> list = new ArrayList<ToAccesoryList>();
		if(attachments!=null && attachments.size()>0){
			for(ToAttachment attachment:attachments){
				if(attachment.getPartCode().equals("property_research")){
					attachments.remove(attachment);
					continue;
				}
				if(!StringUtils.isEmpty(attachment.getPreFileCode())){
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
					ToAccesoryList itemAccesoryList = toAccesoryListService.findAccesoryByCode(attachment.getPreFileCode());
					boolean isHave = false;
					if(CollectionUtils.isNotEmpty(list)){
						for(ToAccesoryList item:list){
							if(item.getPkid() == itemAccesoryList.getPkid()){
								isHave = true;
								break;
							}
						}
						if(!isHave){
							list.add(itemAccesoryList);
						}
					}
				}
			}
		}
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
    } 
    
    /**
     * 读取上传附件备件表
     * @param request
     * @param taskitem
     */
    private void getAccesoryLists(HttpServletRequest request, String taskitem) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		toAccesoryList.setAccessoryCode("_letter_first");
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		List<Long> idList = new ArrayList<Long>();
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			
		}

		toAccesoryList.setAccessoryCode("_letter_sec");
		list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList1", list);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
		}
		request.setAttribute("idList", idList);
    }
    
}
