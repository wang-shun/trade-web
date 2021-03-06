/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.taskList.web;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.common.enums.*;
import com.centaline.trans.task.entity.ToRatePayment;
import com.centaline.trans.task.service.*;
import com.centaline.trans.task.vo.MortgageToSaveVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.cases.service.CaseRecvService;
import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.VCaseTradeInfoService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.cases.vo.CaseRecvVO;
import com.centaline.trans.cases.vo.EditCaseDetailVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.eloan.service.ToCloseLoanService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mortgage.entity.MortStep;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.UiImproveUtil;


@Controller
@RequestMapping(value="/task")
public class TaskController {

	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	@Autowired
	private SignService signService;/*签约*/
	@Autowired
	private TransplanServiceFacade transplanServiceFacade;/*填写交易计划*/
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
	private ToRatePaymentService ratePaymentService;/*缴税*/
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

	@Autowired
	private ToMortgageTosaveService toMortgageTosaveService;
	
	@Autowired(required = true)
	TsFinOrgService tsFinOrgService;
	@Autowired(required = true)
	ToEvaFeeRecordService toEvaFeeRecordService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	VCaseTradeInfoService vCaseTradeInfoService;
	@Autowired
	private CaseRecvService caseRecvService;
	
    @RequestMapping(value="/{taskitem}")   
    public String taskPageRoute(Model model, HttpServletRequest request,@PathVariable String taskitem,
    		String caseCode, String taskId, String instCode,String source) {
    	request.setAttribute("taskId", taskId);
    	request.setAttribute("processInstanceId", instCode);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("taskitem", taskitem);
		request.setAttribute("source", source);
		
		ToCase c = toCaseService.findToCaseByCaseCode(caseCode);
		request.setAttribute("afterTimeFlag", false);
		if(c != null) {
			CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(c.getPkid());
			if(c.getCreateTime()!=null){
				request.setAttribute("afterTimeFlag", c.getCreateTime().after(new Date(1467302399999l)));
			}
			//税费卡
			int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
			if ( cou >0) {
				caseBaseVO.setLoanType("30004005");
			}
			request.setAttribute("caseBaseVO", caseBaseVO);
		}
		
		// 根据caseCode 去查询 ctmCode  作者：zhangxb16
		String ctmCode=null;
		ToCaseInfo caseinfo=tocaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if(null!=caseinfo){
			ctmCode=caseinfo.getCtmCode();
		}
		if(PartCodeEnum.RF.getCode().equals(taskitem)){//接单跟进
			if(null!=caseCode&&caseCode!=""){
				CaseRecvVO caseRecvVO = caseRecvService.selectFullCaseRecvVO(caseCode);
				model.addAttribute("caseRecvVO", caseRecvVO);			
			}
			
			App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
			String ctx = app.genAbsoluteUrl();
			request.setAttribute("ctx", ctx);
			//接单跟进新写，未与下面task开一致
			return "task/taskCaseRecvFollow";
		}
		else if(PartCodeEnum.WQ.getCode().equals(taskitem)) {//签约，读取数据
    		getAccesoryList(request, taskitem);
    		request.setAttribute("transSign", signService.qureyGuestInfo(caseCode));
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		
    	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
    	    model.addAttribute("imgweb", app.genAbsoluteUrl());
    	} else if(PartCodeEnum.JYJH.getCode().equals(taskitem)) {//填写交易计划
    		/*getAccesoryList(request, taskitem);
    		RestVariable dy = workFlowManager.getVar(instCode, "LoanCloseNeed");抵押
    		
    		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");公积金
    		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");自办
    		RestVariable com = workFlowManager.getVar(instCode, "ComLoanNeed");贷款
    		boolean dk =  ((boolean)(psf==null?false:psf.getValue())||(boolean)(self==null?false:self.getValue())||(boolean)(com==null?false:com.getValue()));
    		request.setAttribute("dy", dy==null?false:dy.getValue());
    		request.setAttribute("dk", dk);
    		request.setAttribute("transPlan", transplanServiceFacade.findTransPlanByCaseCode(caseCode));*/
    		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
    		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
    		if (cou > 0) {
    			caseBaseVO.setLoanType("30004005");
    		}
    		request.setAttribute("source", source);
    		request.setAttribute("caseBaseVO", caseBaseVO);
    		/*获取案件信息，用以判断贷款类型 by wbzhouht*/
    		ToCaseInfo toCaseInfo=toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
    		if(null!=toCaseInfo){
    			request.setAttribute("toCaseInfo",toCaseInfo);
    		}
    		request.setAttribute("transPlan",
    				transplanServiceFacade.findTransPlanByCaseCode(caseCode));
    		
    	}
    	//已废弃,天津使用接单跟进
    	/*else if("FirstFollow".equals(taskitem)) {//首次跟进
    		initApproveRecord(request, caseCode, "0");*//*无效审批*//*
    		request.setAttribute("ctmCode", ctmCode);
        	request.setAttribute("firstFollow", firstFollowService.queryFirstFollow(caseCode));
    	}*/
		//已废弃，天津使用缴税
		/*else if("PurchaseLimit".equals(taskitem)){*//*查限购*//*
    		getAccesoryList(request, taskitem);
    		request.setAttribute("purchaseLimit", toPurchaseLimitSearchService.findToPlsByCaseCode(caseCode));
    	} else if("Pricing".equals(taskitem)){*//*核价*//*
    		getAccesoryList(request, taskitem);
    		request.setAttribute("pricing", toPricingService.qureyPricing(caseCode));
    	} else if("TaxReview".equals(taskitem)){*//*审税*//*
    		getAccesoryList(request, taskitem);
    		request.setAttribute("taxReview", toTaxService.findToTaxByCaseCode(caseCode));
    	}*/
    	else if (PartCodeEnum.JS.getCode().equals(taskitem)){/*缴税*/
			getAccesoryList(request,taskitem);
			ToRatePayment ratePayment=ratePaymentService.qureyToRatePayment(caseCode);
			if(ratePayment!=null){
				User user=uamUserOrgService.getUserById(ratePayment.getAutualOperatorId());
				if (user!=null){
					ratePayment.setAutualOperatorName(user.getRealName());//根据操作人的id查询操作人的姓名
				}
			}
			request.setAttribute("ratePayment",ratePayment);
		} else if(PartCodeEnum.GH.getCode().equals(taskitem)){/*过户*/
        	/*过户申请信息*/
        	initApproveRecord(request, caseCode, "2");
        	//获取附件信息
    		getAccesoryListGuoHu(request, taskitem, caseCode);
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		//贷款信息
    		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCodeOnlyOne(caseCode);
    		if(toMortgage!=null){
				request.setAttribute("toMortgage", toMortgage);
			}
			//自办贷款信息
			MortgageToSaveVO mortgageToSaveVO=toMortgageTosaveService.selectByCaseCode(caseCode);
			if (mortgageToSaveVO!=null){
				request.setAttribute("mortgageToSaveVO",mortgageToSaveVO);
			}
			//获取经济人陪同原因
			Dict dict = uamBasedataService.findDictByType("accompany_reason");
			if(dict!=null){
				request.setAttribute("accompanyReason", dict.getChildren());
			}
    	} else if(PartCodeEnum.DKJQ.getCode().equals(taskitem)) {/*贷款结清*/
    		request.setAttribute("loanClose", toCloseLoanService.qureyToCloseLoan(caseCode));
    	}else if(PartCodeEnum.ZHDK.getCode().equals(taskitem)){//组合贷款 by wbzhouht
			CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
			int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
			if ( cou >0) {
				caseBaseVO.setLoanType("30004005");
			}
			request.setAttribute("source", source);
			request.setAttribute("caseBaseVO", caseBaseVO);
			toAccesoryListService.getAccesoryLists(request, taskitem);
			MortStep mortStep = new MortStep();
			mortStep.setCaseCode(caseCode);
			Integer[] step = mortStepService.getMortStep(caseCode);
			request.setAttribute("step", step[0]);
			return "task"+UiImproveUtil.getPageType(request)+"/taskComAndPSFLoanProcess";
		}

    	else if(PartCodeEnum.GJJSQ.getCode().equals(taskitem)) {/*纯公积金贷款申请*/
    		getAccesoryList(request, taskitem);
    		ToTransPlan toTransPlan = new ToTransPlan();
    		toTransPlan.setPartCode(taskitem);
    		toTransPlan.setCaseCode(caseCode);
    		request.setAttribute("toTransPlan", transplanServiceFacade.findTransPlan(toTransPlan));
    		request.setAttribute("apply", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));//--
    	} else if(PartCodeEnum.GJJQY.getCode().equals(taskitem)) {/*纯公积金贷款签约*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("PSFSign", psfSignService.queryPSFSignNoBlank(caseCode));
    	} else if(PartCodeEnum.CGJJSP.getCode().equals(taskitem)) {/*纯公积金贷款审批*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("PSFApprove", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));//--30016003
    	} else if(PartCodeEnum.LZ.getCode().equals(taskitem)) {/*领证*/
    		request.setAttribute("tgpb", toGetPropertyBookService.queryToGetPropertyBook(caseCode));
    	} else if(PartCodeEnum.FK.getCode().equals(taskitem)) {/*放款*/
    		/*RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");公积金
    		boolean tz = !(boolean)(psf==null?false:psf.getValue());
    		getAccesoryList(request, taskitem);
    		ToMortgage mortgage=toMortgageService.findToMortgageByCaseCodeOnlyOne(caseCode);
    		//公积金的话无他证送抵时间
    		if("30016003".equals(mortgage.getMortType())&&"1".equals(mortgage.getIsDelegateYucui())) {
    			tz = false;
    		}
    		request.setAttribute("tz", tz);
    		request.setAttribute("loanRelease", mortgage);*/
    		//数据库表T_TO_ACCESORY_LIST需加相应数据：放款所需附件
        	toAccesoryListService.getAccesoryList(request, taskitem);
        	CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
            request.setAttribute("source", source);
            request.setAttribute("caseBaseVO", caseBaseVO);
            request.setAttribute("caseCode", caseCode);
            ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
            if(mortgage == null) {
            	MortgageToSaveVO toSaveVO = toMortgageTosaveService.selectByCaseCode(caseCode);
            	mortgage = new ToMortgage();
            	mortgage.setCaseCode(caseCode);
            	mortgage.setLendDate(toSaveVO.getLendDate());
            	mortgage.setRemark(toSaveVO.getRemark());
            }

            request.setAttribute("loanRelease", mortgage);
    	} else if(PartCodeEnum.ZBDKSP.getCode().equals(taskitem)) {/*SelfLoanApprove 自办贷款审批*/
    		ToMortgage mortgage =toMortgageService.findToSelfLoanMortgage(caseCode);
    		request.setAttribute("SelfLoan", mortgage);
    		if(mortgage!=null && mortgage.getCustCode()!=null){
    			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
				if(null !=guest){
				request.setAttribute("custCompany",guest.getWorkUnit());
				request.setAttribute("custName",guest.getGuestName());
				}
			};
    	}else if(PartCodeEnum.AJDK.getCode().equals(taskitem)){
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
    	//新增交易顾问派单 流程
    	}else if(PartCodeEnum.PD.getCode().equals(taskitem)){
    		
    		ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
    		request.setAttribute("mortgage", mortgage);
    	
    	}else if(PartCodeEnum.DKLS.getCode().equals(taskitem)){ /*贷款流失申请*/
    		getAccesoryList(request, taskitem);   		
    		
    		/*贷款流失审批 添加流失原因*/
    		Dict dict = uamBasedataService.findDictByType("loanlost_not_approve");    		
    		if(dict!=null){    				
    				request.setAttribute("loanLostApplyReasons", dict.getChildren());
    		}  
    		/**这里应该和自办贷款一样*/
    		ToMortgage mortgage =toMortgageService.findToSelfLoanMortgage(caseCode);
    		request.setAttribute("mortgage", mortgage);
    		if(mortgage!=null && mortgage.getCustCode()!=null){
    			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
				if(null !=guest){
				request.setAttribute("custCompany",guest.getWorkUnit());
				request.setAttribute("custName",guest.getGuestName());
				}
			};
        	/*贷款流失初始信息*/
        	initApproveRecord(request, caseCode, "1");
        	ToApproveRecord r=new ToApproveRecord();
        	r.setCaseCode(caseCode);
        	r.setPartCode("LoanlostApply");
        	r.setProcessInstance(instCode);
        	request.setAttribute("toApproveRecord", toApproveRecordService.queryToApproveRecord(r));
    	}else if(PartCodeEnum.ZBPG.getCode().equals(taskitem)){
    		getAccesoryList(request, taskitem);
    		request.setAttribute("toEguPricing", toEguPricingService.findIsFinalEguPricing(caseCode));	
    	} else if(PartCodeEnum.XXPG.getCode().equals(taskitem)) {/*线下评估报告发起*/
    		request.setAttribute("OfflineEva", offlineEvaService.queryOfflineEvaVO(instCode));
    		request.setAttribute("evaReport", toEvaReportService.findByProcessId(instCode));
    	} else if(PartCodeEnum.SPFS.getCode().equals(taskitem) ||
                PartCodeEnum.SPFS1.getCode().equals(taskitem) || PartCodeEnum.SPFS2.getCode().equals(taskitem) || PartCodeEnum.SPFS3.getCode().equals(taskitem)) {
    		request.setAttribute("caseDetail", loanlostApproveService.queryCaseInfo(caseCode,"LoanlostApply",instCode));
    		
    		ToMortgage mortgage= toMortgageService.findToSelfLoanMortgage(caseCode);			
			if(mortgage!=null && mortgage.getCustCode()!=null){
				TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
				if(null !=guest){
				request.setAttribute("custCompany",guest.getWorkUnit());
				request.setAttribute("custName",guest.getGuestName());
				}
			};
    		String approveType = "1";/*流失审批*/
    		initApproveRecord(request, caseCode, approveType);  		
    		
    	} else if(PartCodeEnum.JASP.getCode().equals(taskitem) ||
                PartCodeEnum.JASP1.getCode().equals(taskitem) || PartCodeEnum.JASP2.getCode().equals(taskitem)) {/*结案审批*/
    		initApproveRecord(request, caseCode, "3");
    	} else if(PartCodeEnum.WXSP.getCode().equals(taskitem)) {
    		initApproveRecord(request, caseCode, "0");/*无效审批*/
    	} else if(PartCodeEnum.GHSP.getCode().equals(taskitem)) {
    		initApproveRecord(request, caseCode, "2");/*过户审批*/
    		
    		//交易信息
    		VCaseTradeInfo caseInfo = vCaseTradeInfoService.queryCaseTradeInfoByCaseCode(caseCode);
    		//贷款信息
    		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
    		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCodeOnlyOne(caseCode);
    		CaseDetailShowVO reVo  = getCaseDetailShowVO(caseCode,toMortgage);
    		request.setAttribute("toMortgage", toMortgage);
    		request.setAttribute("caseDetailVO", reVo);
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		request.setAttribute("caseInfo", caseInfo);
    		request.setAttribute("toCaseInfo", toCaseInfo);
    		Dict dict = uamBasedataService.findDictByType("guohu_not_approve");
    		if(dict!=null){
        		request.setAttribute("notApproves", dict.getChildren());
    		}
    	} else if(PartCodeEnum.JA.getCode().equals(taskitem)) {/*结案审批，验证数据是否正确*/
    		/*initApproveRecord(request, caseCode, "3");
    		getAccesoryListCaseClose(request, caseCode);
    		
    		EditCaseDetailVO editCaseDetailVO=editCaseDetailService.queryCaseDetai(caseCode);
    		
    		request.setAttribute("editCaseDetailVO", editCaseDetailVO);
    		request.setAttribute("loanReq", editCaseDetailVO.getLoanReq());*/
    		SessionUser user=uamSessionService.getSessionUser();
    		request.setAttribute("source", source);
    		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
    		request.setAttribute("caseBaseVO", caseBaseVO);
    		request.setAttribute("approveType", "3");
    		request.setAttribute("operator", user != null ? user.getId():"");
    		toAccesoryListService.getAccesoryListCaseClose(request, caseCode);
    		EditCaseDetailVO editCaseDetailVO=editCaseDetailService.queryCaseDetai(caseCode);
    		if(editCaseDetailVO != null && StringUtils.isNotBlank(String.valueOf(editCaseDetailVO.getMpkid()))) {
    			ToApproveRecord tar = new ToApproveRecord();
    			tar.setCaseCode(caseCode);
    			tar.setPartCode(OldActivitiFormKey.CaseCloseFirstApprove.getTaskDefinitionKey());
    			tar.setProcessInstance(String.valueOf(request.getAttribute("processInstanceId")));
    			List<ToApproveRecord> tarList = toApproveRecordService.queryToApproveRecords(tar);
    			if(tarList != null && tarList.size() > 0) {
    				request.setAttribute("notFirstTimeSubmit", 1);
    			}
    		}
    		request.setAttribute("editCaseDetailVO", editCaseDetailVO);
    		request.setAttribute("loanReq", editCaseDetailVO.getLoanReq());
    	} else if(PartCodeEnum.FWX.getCode().equals(taskitem)) {/*服务项变更*/
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
    	} else if(PartCodeEnum.FWXSP.getCode().equals(taskitem)) {/*服务项审批*/
    		initApproveRecord(request, caseCode, "4");
    		request.setAttribute("newxm", serviceChangeService.qureyServChangeHistrotyInfo(caseCode).get("newServChange"));
    		request.setAttribute("delxm", serviceChangeService.qureyServChangeHistrotyInfo(caseCode).get("delServChange"));
    	}else if(PartCodeEnum.ZJJG.getCode().equals(taskitem)) {/*资金监管解除申请*/
    		getAccesoryList(request, taskitem);
        	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);

    		request.setAttribute("toSpv", toSpv);
    		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);

    		request.setAttribute("spvDeRecVo", spvDeRecVo);
    	}else if(PartCodeEnum.ZJJGSP.getCode().equals(taskitem)) {/*资金监管解除审批*/
    		getAccesoryList(request, taskitem);
    		
    		initApproveRecord(request, caseCode, "6");
    		
    		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
    		
    		request.setAttribute("spvDeRecVo", spvDeRecVo);
    	}else if(PartCodeEnum.FWCQ.getCode().equals(taskitem)||PartCodeEnum.FWXSP.getCode().equals(taskitem)){
    		initApproveRecord(request, caseCode, "7");
     		if(instCode == null && caseCode != null) {
        		ToWorkFlow toWorkFlow = new ToWorkFlow();
        		toWorkFlow.setCaseCode(caseCode);
        		toWorkFlow.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
        		instCode = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlow).getInstCode();
        		request.setAttribute("processInstanceId", instCode);
        	}
     		
    	}else if(ToAttachmentEnum.MORTGAGESELECT.getCode().equals(taskitem)){
    		ToTransPlan plan=new ToTransPlan();
    		plan.setCaseCode(caseCode);
    		plan.setPartCode("LoanRelease");//放款
    		request.setAttribute("loanReleasePlan", transplanServiceFacade.findTransPlan(plan));
    	}else if(PartCodeEnum.PGCQ.getCode().equals(taskitem) || PartCodeEnum.PGCQSP.getCode().equals(taskitem)){ //天津评估流程
    		initApproveRecord(request, caseCode, "10");
     		if(instCode == null && caseCode != null) {
        		ToWorkFlow toWorkFlow = new ToWorkFlow();
        		toWorkFlow.setBizCode(request.getParameter("evalCode"));
        		toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
        		instCode = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlow).getInstCode();
        		request.setAttribute("processInstanceId", instCode);
        	}
    	}
        return "task"+UiImproveUtil.getPageType(request)+"/task"+taskitem;
  
    }
    
    /****
	 *  查询案件详情
	 * 
	 *  @param caseCode
	 *  @return
	 */
	private CaseDetailShowVO getCaseDetailShowVO(String caseCode,ToMortgage toMortgage) {
		CaseDetailShowVO reVo = new CaseDetailShowVO();
		
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		ToCase te=toCaseService.findToCaseByCaseCode(caseCode);
		if(null!=te){
			reVo.setCaseProperty(te.getCaseProperty());
		}
		
		//物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		if(toPropertyInfo!=null){
			reVo.setPropertyAddress(toPropertyInfo.getPropertyAddr());
		}
		User agentUser=null;
		//经纪人
		if(!StringUtils.isBlank(toCaseInfo.getAgentCode())){
			agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		}
		if(agentUser!=null){
			reVo.setAgentId(agentUser.getId());
			reVo.setAgentName(agentUser.getRealName());
			reVo.setAgentMobile(agentUser.getMobile());
			reVo.setAgentOrgId(agentUser.getOrgId());
			reVo.setAgentOrgName(agentUser.getOrgName());
			//分行经理
			List<User> mcList = uamUserOrgService.getUserByOrgIdAndJobCode(agentUser.getOrgId(), TransJobs.TFHJL.getCode());
			if(mcList!=null && mcList.size()>0){

				User mcUser = mcList.get(0);
				reVo.setMcId(mcUser.getId());
				reVo.setMcName(mcUser.getRealName());
				reVo.setMcMobile(mcUser.getMobile());
			}
		}
		
		//交易顾问
		User consultUser = uamUserOrgService.getUserById(te.getLeadingProcessId());
		if(consultUser!=null){
			reVo.setCpId(consultUser.getId());
			reVo.setCpName(consultUser.getRealName());
			reVo.setCpMobile(consultUser.getMobile());
		}
		//助理
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(consultUser.getOrgId(), TransJobs.TJYZL.getCode());
		if(asList!=null && asList.size()>0){
			User assistUser = asList.get(0);
			reVo.setAsId(assistUser.getId());
			reVo.setAsName(assistUser.getRealName());
			reVo.setAsMobile(assistUser.getMobile());
		}
		//贷款流失类型 
		String loanLostType = toCloseLoanService.getLoanLostTypeValue(caseCode); 
		if(!StringUtils.isBlank(loanLostType)){
			reVo.setLoanLostType(loanLostType);
		}else{
			reVo.setLoanLostType("");
		}

		
		if(toMortgage!=null){
			//贷款类型
			if(!StringUtils.isEmpty(toMortgage.getMortType())){
				String mortTypeString = uamBasedataService.getDictValue(TransDictEnum.TDKLX.getCode(), toMortgage.getMortType());
				reVo.setMortTypeName(mortTypeString);
			}
	    	//放款方式
	    	if(toMortgage.getLendWay()!=null){				
	    		String lendWay = uamBasedataService.getDictValue(TransDictEnum.TLENDWAY.getCode(), toMortgage.getLendWay());
				reVo.setLendWay(lendWay);
	    	}
			//分行支行
			String finOrgCodeString = toMortgage.getFinOrgCode();
			if(!StringUtils.isEmpty(finOrgCodeString)){
				TsFinOrg bank=tsFinOrgService.findBankByFinOrg(finOrgCodeString);
				reVo.setBankName(bank.getFinOrgName());
				if(!StringUtils.isEmpty(bank.getFaFinOrgCode())){
					TsFinOrg faBank = tsFinOrgService.findBankByFinOrg(bank.getFaFinOrgCode());
					reVo.setParentBankName(faBank.getFinOrgName());
				}
			}
			//评估公司
			ToEvaReport evaReport = toEvaReportService.findFinalComByCaseCode(caseCode);
			if(evaReport!=null&& !StringUtils.isEmpty(evaReport.getFinOrgCode())){
				TsFinOrg reportCom = tsFinOrgService.findBankByFinOrg(evaReport.getFinOrgCode());
				reVo.setEvaName(reportCom.getFinOrgName());
			}
			//评估费金额
			ToEvaFeeRecord evaFeeReport = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(caseCode);
			if(evaFeeReport!=null&& evaFeeReport.getEvalFee()!=null){
				reVo.setEvaFee(evaFeeReport.getEvalFee());
			}
			//主贷人
			if(null !=toMortgage.getCustCode()){
				// update zhangxb16 2016-2-16 
				TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
				if(null !=guest){
					reVo.setBuyerWork(guest.getWorkUnit());
					reVo.setMortBuyer(guest.getGuestName());
				}
			}
			
			//签约时间
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(toMortgage.getSignDate()!=null){
				String signDate = format.format(toMortgage.getSignDate());
				reVo.setSignDate(signDate);
			}//批贷时间
			if(toMortgage.getApprDate()!=null){
				String apprDate = format.format(toMortgage.getApprDate());
				reVo.setApprDate(apprDate);
			}//他证送达时间
			if(toMortgage.getTazhengArrDate()!=null){
				String tazhengArrDate = format.format(toMortgage.getTazhengArrDate());
				reVo.setTazhengArrString(tazhengArrDate);
			}//放款时间
			if(toMortgage.getLendDate()!=null){
				String lendDate = format.format(toMortgage.getLendDate());
				reVo.setLendDate(lendDate);
			}//申请时间
			if(toMortgage.getPrfApplyDate()!=null){
				String applyDate = format.format(toMortgage.getPrfApplyDate());
				reVo.setPrfApplyDate(applyDate);
			}
		}
		
		
		return reVo;
	}
	
    
    @RequestMapping(value="/mobile/{taskitem}")   
    public String taskMobilePageRoute(Model model, HttpServletRequest request,@PathVariable String taskitem,
    		String caseCode, String taskId, String instCode) {
    	request.setAttribute("taskId", taskId);
    	request.setAttribute("processInstanceId", instCode);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("taskitem", taskitem);
		if("PurchaseLimit".equals(taskitem)){/*查限购*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("purchaseLimit", toPurchaseLimitSearchService.findToPlsByCaseCode(caseCode));
    	} else if("Pricing".equals(taskitem)){/*核价*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("pricing", toPricingService.qureyPricing(caseCode));
    	} else if("TaxReview".equals(taskitem)){/*审税*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("taxReview", toTaxService.findToTaxByCaseCode(caseCode));
    	}else if ("RatePayment".equals(taskitem)){
    		getAccesoryList(request,taskitem);
    		request.setAttribute("ratePayment",ratePaymentService.qureyToRatePayment(caseCode));
		} else if("Guohu".equals(taskitem)){/*过户*/
        	/*过户申请信息*/
        	initApproveRecord(request, caseCode, "2");
    		getAccesoryListGuoHu(request, taskitem, caseCode);
    		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
    		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
    		request.setAttribute("toMortgage", toMortgage);
    	} else if("PSFApply".equals(taskitem)) {/*纯公积金贷款申请*/
    		getAccesoryList(request, taskitem);
    		ToTransPlan toTransPlan = new ToTransPlan();
    		toTransPlan.setPartCode(taskitem);
    		toTransPlan.setCaseCode(caseCode);
    		request.setAttribute("toTransPlan", transplanServiceFacade.findTransPlan(toTransPlan));
    		request.setAttribute("apply", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));
    	} else if("PSFSign".equals(taskitem)) {/*纯公积金贷款签约*/
    		getAccesoryList(request, taskitem);
    		request.setAttribute("PSFSign", psfSignService.queryPSFSignNoBlank(caseCode));
    	}else if("ComLoanProcess".equals(taskitem)){ //商贷保存
    		getAccesoryLists(request, taskitem);
    	}  else if("HouseBookGet".equals(taskitem)) {/*领证*/
    		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");/*公积金*/
    		
    		// add zhangxb16 2016-2-22
    		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");/*自办*/
    		RestVariable com = workFlowManager.getVar(instCode, "ComLoanNeed");/*贷款*/
    		
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
    public String caseDetail(HttpServletRequest request, String caseCode,String taskitem) {//添加了taskitem, 用来获取环节编码 by wbzhouht
    	ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
    	String st = null;
    	if(toCase != null) {
    		st = "redirect:/case/caseDetail?&caseId="+toCase.getPkid()+"&partCode="+taskitem;
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
			if(psf && ("third_part_right_cert".equals(tal.getAccessoryCode()) || "new_house_book".equals(tal.getAccessoryCode()))) {/*公积金*/
				removeList.add(tal);
			}
			
			// add zhangxb16 2016-2-22
			if("third_part_right_cert".equals(tal.getAccessoryCode()) && psf==false && self==false && com==false){
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
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCodeOnlyOne(caseCode);
		for(ToAccesoryList tal:list) {
			if((toMortgage == null || toMortgage.getMortType() == null) && ("抵押登记表".equals(tal.getAccessoryName()) || "商贷利率页".equals(tal.getAccessoryName()))) {/*无贷款*/
				removeList.add(tal);
			} else if(toMortgage != null && "30016003".equals(toMortgage.getMortType()) && "商贷利率页".equals(tal.getAccessoryName())) {
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
        List<ToAttachment> list = toAttachmentService.findToAttachmentByCaseCode(caseCode);
        if(list!=null && list.size()>0){
            for(ToAttachment attachment :list){
                if("property_research".equals(attachment.getPartCode())){
                    continue;
                }
                if(!StringUtils.isEmpty(attachment.getPreFileCode())){
                    attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
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
