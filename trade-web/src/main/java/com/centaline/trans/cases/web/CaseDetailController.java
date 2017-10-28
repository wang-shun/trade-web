package com.centaline.trans.cases.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centaline.trans.cases.repository.ToCaseRecvMapper;
import com.centaline.trans.cases.service.*;
import com.centaline.trans.common.enums.*;
import com.centaline.trans.task.entity.*;
import com.centaline.trans.task.service.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.Resource;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseParticipantService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.cases.service.VCaseTradeInfoService;
import com.centaline.trans.cases.vo.CaseAssistantVO;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.cases.vo.ChangeTaskAssigneeVO;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToServChangeHistroty;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToModuleSubscribeService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToServChangeHistrotyService;
import com.centaline.trans.eloan.entity.LoanAgent;
import com.centaline.trans.eloan.service.LoanAgentService;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.service.ToEloanRelService;
import com.centaline.trans.eloan.vo.ToLoanAgentVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.property.service.ToPropertyResearchService;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

/**
 * 
 * <p>
 * Project: 案件详情
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2015.
 * </p>
 * 
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value = "/case")
public class CaseDetailController {

	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	WorkFlowManager workFlowManager;
	@Autowired(required = true)
	ToMortgageService toMortgageService;
	@Autowired(required = true)
	TsFinOrgService tsFinOrgService;
	@Autowired(required = true)
	ToEvaReportService toEvaReportService;
	@Autowired(required = true)
	ToEvaFeeRecordService toEvaFeeRecordService;
	@Autowired(required = true)
	VCaseTradeInfoService vCaseTradeInfoService;
	@Autowired(required = true)
	ToSpvService toSpvService;
	@Autowired(required = true)
	ToPropertyService toPropertyService;
	@Autowired(required = true)
	TransplanServiceFacade transplanServiceFacade;
	@Autowired(required = true)
	ToServChangeHistrotyService toServChangeHistrotyService;
	@Autowired(required = true)
	UamTemplateService uamTemplateService;
	@Autowired(required = true)
	PropertyUtilsService propertyUtilsService;
	@Autowired
	UamBasedataService uamBasedataService;
	@Autowired
	private TsTeamPropertyService teamPropertyService;
	@Autowired(required = true)
	private ToHouseTransferService toHouseTransferService;

	@Autowired(required = true)
	private ToCloseService toCloseService;

	@Autowired(required = true)
	private LoanAgentService toLoanAgentService;
	@Autowired
	private ToPropertyResearchService toPropertyResarchService;
	@Autowired
	private TlTaskReassigntLogService taskReassingtLogService;

	@Autowired
	private BizWarnInfoService bizWarnInfoService;
	//获取流程实例id，用以启动流程
	@Autowired
	private ProcessInstanceService processInstanceService;
	//用来获取上级任务办理人
	@Autowired
	private AuditCaseService auditCaseService;
	// E+金融
	@Autowired
	private ToEloanRelService toEloanRelService;
	@Autowired
	private ToEloanCaseService toEloanCaseService;

	@Autowired
	UamPermissionService uamPermissionService;

	//关注
	@Autowired
	ToModuleSubscribeService toModuleSubscribeService;
	//审批记录
	@Autowired
	ToApproveRecordService toApproveRecordService;
	@Autowired
	CaseMergeService caseMergeService;
	@Autowired
	ToCaseParticipantService toCaseParticipantService;

	@Autowired
	private ActRuTaskService actRuTaskService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	
	@Autowired
	private EvaPricingService evaPricingService;
	
	@Autowired
	CaseApiService caseApiService;
	@Autowired
	private SignService signService;

	/**
	 * 判断交易计划时间 by wbzhouht
	 */
	//缴税
	@Autowired
	private ToRatePaymentService toRatePaymentService;
	//领证
	@Autowired
	private ToGetPropertyBookService toGetPropertyBookService;
	@Autowired
	private ToCaseRecvMapper toCaseRecvMapper;
	@Autowired
	private RansomMapper ransomMapper;
	

	/**
	 * 页面初始化
	 * @author wbzhouht
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseCodeDetail")
	public String caseDetail(String caseCode , ServletRequest request) {
		if (caseCode == null)
			return "case/caseList";
		CaseDetailShowVO reVo = new CaseDetailShowVO();
		// 基本信息
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());

		// add zhangxb16 2016-2-22 功能
		ToCase te = toCaseService.findToCaseByCaseCode(toCase.getCaseCode());
		if (null != te) {
			reVo.setCaseProperty(te.getCaseProperty());
		}

		// 物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		User agentUser = null;
		// 经纪人
		if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
			agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		}
		if (agentUser != null) {
			reVo.setAgentId(agentUser.getId());
			reVo.setAgentName(agentUser.getRealName());
			reVo.setAgentMobile(agentUser.getMobile());
			reVo.setAgentOrgId(agentUser.getOrgId());
			reVo.setAgentOrgName(agentUser.getOrgName());
			// 分行经理
			List<User> mcList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(agentUser.getOrgId(),
					TransJobs.TFHJL.getCode());
			if (mcList != null && mcList.size() > 0) {

				User mcUser = mcList.get(0);
				reVo.setMcId(mcUser.getId());
				reVo.setMcName(mcUser.getRealName());
				reVo.setMcMobile(mcUser.getMobile());
			}
		}

		// 交易顾问
		User consultUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		if (consultUser != null) {
			reVo.setCpId(consultUser.getId());
			reVo.setCpName(consultUser.getRealName());
			reVo.setCpMobile(consultUser.getMobile());
		}
		// 助理
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(toCase.getOrgId(), TransJobs.TJYZL.getCode());
		if (asList != null && asList.size() > 0) {
			User assistUser = asList.get(0);
			reVo.setAsId(assistUser.getId());
			reVo.setAsName(assistUser.getRealName());
			reVo.setAsMobile(assistUser.getMobile());
		}
		// 上下家
		List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		StringBuffer seller = new StringBuffer();
		StringBuffer sellerMobil = new StringBuffer();
		StringBuffer buyer = new StringBuffer();
		StringBuffer buyerMobil = new StringBuffer();
		for (TgGuestInfo guest : guestList) {
			if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
				seller.append(guest.getGuestName());
				sellerMobil.append(guest.getGuestPhone());
				seller.append("/");
				sellerMobil.append("/");
			} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
				buyer.append(guest.getGuestName());
				buyerMobil.append(guest.getGuestPhone());
				buyer.append("/");
				buyerMobil.append("/");
			}
		}

		if (guestList.size() > 0) {
			if (seller.length() > 1) {
				seller.deleteCharAt(seller.length() - 1);
				sellerMobil.deleteCharAt(sellerMobil.length() - 1);
			}

			if (buyer.length() > 1) {
				buyer.deleteCharAt(buyer.length() - 1);
				buyerMobil.deleteCharAt(buyerMobil.length() - 1);
			}
		}

		reVo.setSellerName(seller.toString());
		reVo.setSellerMobile(sellerMobil.toString());
		reVo.setBuyerMobile(buyerMobil.toString());
		reVo.setBuyerName(buyer.toString());
		// 合作顾问
		List<CaseDetailProcessorVO> proList = new ArrayList<CaseDetailProcessorVO>();
		TgServItemAndProcessor inProcessor = new TgServItemAndProcessor();
		inProcessor.setCaseCode(toCase.getCaseCode());
		inProcessor.setProcessorId(toCase.getLeadingProcessId());
		List<String> tgproList = tgServItemAndProcessorService.findProcessorsByCaseCode(inProcessor);
		for (String sp : tgproList) {
			if (StringUtils.isEmpty(sp) || "-1".equals(sp))
				continue;
			CaseDetailProcessorVO proVo = new CaseDetailProcessorVO();
			User processor = uamUserOrgService.getUserById(sp);
			proVo.setProcessorId(processor.getId());
			proVo.setProcessorName(processor.getRealName());
			proVo.setProcessorMobile(processor.getMobile());
			proList.add(proVo);
		}
		reVo.setProList(proList);

		// 服务项
		StringBuffer srvCodes = new StringBuffer();
		List<String> tgSrvList = tgServItemAndProcessorService.findSrvCatsByCaseCode(toCase.getCaseCode());

		for (String sp : tgSrvList) {
			if (!StringUtils.isEmpty(sp)) {
				srvCodes.append(sp);
				srvCodes.append(",");
			}

		}
		if (srvCodes.length() > 0)
			srvCodes.deleteCharAt(srvCodes.length() - 1);
		reVo.setSrvCodes(srvCodes.toString());

		// 工作流
		ToWorkFlow inWorkFlow = new ToWorkFlow();
		inWorkFlow.setBusinessKey("operation_process");
		inWorkFlow.setCaseCode(toCase.getCaseCode());
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 贷款信息
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(toCase.getCaseCode());
		String loanReqType="FullPay";
		if (toMortgage != null) {
			if("1".equals(toMortgage.getIsDelegateYucui())){
				if("30016003".equals(toMortgage.getMortType())){
					loanReqType="PSFLoan";
				}else{
					loanReqType="ComLoan";
				}
			}else{
				loanReqType="SelfLoan";
			}
			// 贷款类型
			if (!StringUtils.isEmpty(toMortgage.getMortType())) {
				String mortTypeString = uamBasedataService.getDictValue(TransDictEnum.TDKLX.getCode(),
						toMortgage.getMortType());
				reVo.setMortTypeName(mortTypeString);
			}
			// 放款方式
			if (toMortgage.getLendWay() != null) {
				String lendWay = uamBasedataService.getDictValue(TransDictEnum.TLENDWAY.getCode(),
						toMortgage.getLendWay());
				reVo.setLendWay(lendWay);
			}
			// 分行支行
			String finOrgCodeString = toMortgage.getFinOrgCode();
			if (!StringUtils.isEmpty(finOrgCodeString)) {
				TsFinOrg bank = tsFinOrgService.findBankByFinOrg(finOrgCodeString);
				reVo.setBankName(bank.getFinOrgName());
				if (!StringUtils.isEmpty(bank.getFaFinOrgCode())) {
					TsFinOrg faBank = tsFinOrgService.findBankByFinOrg(bank.getFaFinOrgCode());
					reVo.setParentBankName(faBank.getFinOrgName());
				}
			}
			// 评估公司
			ToEvaReport evaReport = toEvaReportService.findFinalComByCaseCode(toCase.getCaseCode());
			if (evaReport != null && !StringUtils.isEmpty(evaReport.getFinOrgCode())) {
				TsFinOrg reportCom = tsFinOrgService.findBankByFinOrg(evaReport.getFinOrgCode());
				reVo.setEvaName(reportCom.getFinOrgName());
			}
			// 评估费金额
			ToEvaFeeRecord evaFeeReport = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(toCase.getCaseCode());
			if (evaFeeReport != null && evaFeeReport.getEvalFee() != null) {
				reVo.setEvaFee(evaFeeReport.getEvalFee());
			}
			// 主贷人
			if (null != toMortgage.getCustCode()) {
				// update zhangxb16 2016-2-16
				TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
				if (null != guest) {
					reVo.setBuyerWork(guest.getWorkUnit());
					reVo.setMortBuyer(guest.getGuestName());
				}
			}

			// 签约时间
			if (toMortgage.getSignDate() != null) {
				String signDate = format.format(toMortgage.getSignDate());
				reVo.setSignDate(signDate);
			} // 批贷时间
			if (toMortgage.getApprDate() != null) {
				String apprDate = format.format(toMortgage.getApprDate());
				reVo.setApprDate(apprDate);
			} // 他证送达时间
			if (toMortgage.getTazhengArrDate() != null) {
				String tazhengArrDate = format.format(toMortgage.getTazhengArrDate());
				reVo.setTazhengArrString(tazhengArrDate);
			} // 放款时间
			if (toMortgage.getLendDate() != null) {
				String lendDate = format.format(toMortgage.getLendDate());
				reVo.setLendDate(lendDate);
			} // 申请时间
			if (toMortgage.getPrfApplyDate() != null) {
				String applyDate = format.format(toMortgage.getPrfApplyDate());
				reVo.setPrfApplyDate(applyDate);
			}
		}
		// 交易信息
		VCaseTradeInfo caseInfo = vCaseTradeInfoService.queryCaseTradeInfoByCaseCode(toCase.getCaseCode());

		// 派单时间
		if (caseInfo.getCreateTime() != null) {
			String createTime = format.format(caseInfo.getCreateTime());
			reVo.setCreateTime(createTime);
		}
		// 分单时间
		if (caseInfo.getResDate() != null) {
			String resDate = format.format(caseInfo.getResDate());
			reVo.setResDate(resDate);
		} // 签约时间
		if (caseInfo.getRealConTime() != null) {
			String realConTime = format.format(caseInfo.getRealConTime());
			reVo.setRealConTime(realConTime);
		} // 付款时间(首付)
		if (caseInfo.getPayTime1() != null) {
			String payTime1 = format.format(caseInfo.getPayTime1());
			reVo.setPayTime1(payTime1);
		} // 付款时间(二期)
		if (caseInfo.getPayTime2() != null) {
			String payTime2 = format.format(caseInfo.getPayTime2());
			reVo.setPayTime2(payTime2);
		} // 付款时间(尾款)
		if (caseInfo.getPayTime3() != null) {
			String payTime3 = format.format(caseInfo.getPayTime3());
			reVo.setPayTime3(payTime3);
		} // 付款时间(装修补偿)
		if (caseInfo.getPayTime4() != null) {
			String payTime4 = format.format(caseInfo.getPayTime4());
			reVo.setPayTime4(payTime4);
		} // 还款时间
		if (caseInfo.getLoanCloseCode() != null) {
			String loanCloseCode = format.format(caseInfo.getLoanCloseCode());
			reVo.setLoanCloseCode(loanCloseCode);
		}
		//缴税时间
		if(caseInfo.getPaymentTime()!=null){
			String paymentTime=format.format(caseInfo.getPaymentTime());
			reVo.setPaymentTime(paymentTime);
		}
		// 过户时间
		if (caseInfo.getRealHtTime() != null) {
			String realHtTime = format.format(caseInfo.getRealHtTime());
			reVo.setRealHtTime(realHtTime);
		} // 审税时间
		if (caseInfo.getTaxTime() != null) {
			String taxTime = format.format(caseInfo.getTaxTime());
			reVo.setTaxTime(taxTime);
		} // 核价时间
		if (caseInfo.getPricingTime() != null) {
			String pricingTime = format.format(caseInfo.getPricingTime());
			reVo.setPricingTime(pricingTime);
		} // 查限购时间
		if (caseInfo.getRealPlsTime() != null) {
			String realPlsTime = format.format(caseInfo.getRealPlsTime());
			reVo.setRealPlsTime(realPlsTime);
		} // 领证时间
		if (caseInfo.getRealPropertyGetTime() != null) {
			String realPropertyGetTime = format.format(caseInfo.getRealPropertyGetTime());
			reVo.setRealPropertyGetTime(realPropertyGetTime);
		} // 结案时间
		if (caseInfo.getCloseTime() != null) {
			String closeTime = format.format(caseInfo.getCloseTime());
			reVo.setCloseTime(closeTime);
		}
		// 房屋性质
		if (caseInfo.getHouseProperty() != null) {
			String houseProperty = uamBasedataService.getDictValue(TransDictEnum.TFWXZ.getCode(),
					caseInfo.getHouseProperty());
			reVo.setHouseProperty(houseProperty);
		}
		// 还款方式
		if (caseInfo.getCloseType() != null) {
			String closeType = uamBasedataService.getDictValue(TransDictEnum.THKFS.getCode(), caseInfo.getCloseType());
			reVo.setCloseType(closeType);
		}
		// 购房年数
		if (caseInfo.getHoldYear() != null) {
			String holdYear = uamBasedataService.getDictValue(TransDictEnum.TGFNS.getCode(), caseInfo.getHoldYear());
			reVo.setHoldYear(holdYear);
		} // 房屋性质
		if (caseInfo.getHouseProperty() != null) {
			String houseProperty = uamBasedataService.getDictValue(TransDictEnum.TFWXZ.getCode(),
					caseInfo.getHouseProperty());
			reVo.setHouseProperty(houseProperty);
		} // 唯一住房
		if (caseInfo.getIsUniqueHome() != null) {
			String isUniqueHome = uamBasedataService.getDictValue(TransDictEnum.TWYZF.getCode(),
					caseInfo.getIsUniqueHome());
			reVo.setIsUniqueHome(isUniqueHome);
		} // 户口情况
		if (caseInfo.getIsHukou() != null) {
			String isHukou = uamBasedataService.getDictValue(TransDictEnum.THKQK.getCode(), caseInfo.getIsHukou());
			reVo.setIsHukou(isHukou);
		} // 合同公证
		if (caseInfo.getIsConCert() != null) {
			String isConCert = uamBasedataService.getDictValue(TransDictEnum.THTGZ.getCode(), caseInfo.getIsConCert());
			reVo.setIsConCert(isConCert);
		} // 付款方式(首付)
		if (caseInfo.getPayType1() != null) {
			String payType1 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType1());
			reVo.setPayType1(payType1);
		} // 付款方式(二期)
		if (caseInfo.getPayType2() != null) {
			String payType2 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType2());
			reVo.setPayType2(payType2);
		} // 付款方式(尾款)
		if (caseInfo.getPayType3() != null) {
			String payType3 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType3());
			reVo.setPayType3(payType3);
		} // 付款方式(装修补偿)
		if (caseInfo.getPayType4() != null) {
			String payType4 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType4());
			reVo.setPayType4(payType4);
		}

	/*	// 房款监管信息
		ToSpv toSpv = toSpvService.queryToSpvByCaseCode(toCase.getCaseCode());
		// 房款进出账
		List<ToCashFlow> cashFlows = toSpvService.queryCashFlowsByCaseCode(toCase.getCaseCode());
		if (toSpv != null) {
			// 监管方式
			if (!StringUtils.isEmpty(toSpv.getSpvType())) {
				String spvType = uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(), toSpv.getSpvType());
				reVo.setSpvType(spvType);
			}
			// 监管签约时间
			if (toSpv.getSignTime() != null) {
				String signTime = format.format(toSpv.getSignTime());
				reVo.setSignTime(signTime);
			}
			// 监管机构
			if (toSpv.getSpvInsti() != null) {
				TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(toSpv.getSpvInsti());
				if (tsFinOrg != null && !StringUtils.isEmpty(tsFinOrg.getFinOrgName())) {
					reVo.setSpvInsti(tsFinOrg.getFinOrgName());
				}
			}
			// 进出账
			if (cashFlows != null && cashFlows.size() != 0) {
				for (ToCashFlow toCashFlow : cashFlows) {
					// 类型
					String flowTypeCode = toCashFlow.getCashFlowType();
					if (!StringUtils.isEmpty(flowTypeCode)) {
						String flowTypeValue = uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(),
								flowTypeCode);
						toCashFlow.setCashFlowTypeName(flowTypeValue);
					}
					// 时间
					if (toCashFlow.getFlowTime() != null) {
						String flowTimeString = format.format(toCashFlow.getFlowTime());
						toCashFlow.setFlowTimeStr(flowTimeString);
					}
					// 流水方向
					if (toCashFlow.getFlowDirection() != null) {
						String flowDirection = uamBasedataService.getDictValue(TransDictEnum.TLSFX.getCode(),
								toCashFlow.getFlowDirection());
						toCashFlow.setFlowDirection(flowDirection);
					}
				}
			}
		}*/

		// 金融服务信息
		List<LoanAgent> toLoanAgents = toLoanAgentService.selectByCaseCode(toCase.getCaseCode());
		List<ToLoanAgentVO> toLoanAgentVOs = new ArrayList<ToLoanAgentVO>();
		if (toLoanAgents.size() > 0) {
			for (LoanAgent toLoanAgent : toLoanAgents) {
				ToLoanAgentVO toLoanAgentVO = new ToLoanAgentVO();
				// 贷款服务编码
				if (!StringUtils.isEmpty(toLoanAgent.getLoanSrvCode())) {
					String loanSrvName = uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(),
							toLoanAgent.getLoanSrvCode());
					toLoanAgentVO.setLoanSrvName(loanSrvName);
				}
				// 贷款机构
				if (toLoanAgent.getFinOrgCode() != null) {
					TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(toLoanAgent.getFinOrgCode());
					if (tsFinOrg != null && !StringUtils.isEmpty(tsFinOrg.getFinOrgName())) {
						toLoanAgentVO.setFinOrgName(tsFinOrg.getFinOrgName());
					}
				}
				// 申请状态
				if (!StringUtils.isEmpty(toLoanAgent.getApplyStatus())) {
					String applyStatusName = uamBasedataService.getDictValue(TransDictEnum.TSQZT.getCode(),
							toLoanAgent.getApplyStatus());
					toLoanAgentVO.setApplyStatusName(applyStatusName);
				}
				// 确认状态
				if (!StringUtils.isEmpty(toLoanAgent.getConfirmStatus())) {
					String confirmStatusName = uamBasedataService.getDictValue(TransDictEnum.TSQZT.getCode(),
							toLoanAgent.getConfirmStatus());
					toLoanAgentVO.setConfirmStatusName(confirmStatusName);
				}
				// 确认时间
				if (toLoanAgent.getConfirmTime() != null) {
					String formatTime = format.format(toLoanAgent.getConfirmTime());
					toLoanAgentVO.setConfirmTime(formatTime);
				}
				// 超期导出时间
				if (toLoanAgent.getLastExceedExportTime() != null) {
					String lastExceedExportTime = format.format(toLoanAgent.getLastExceedExportTime());
					toLoanAgentVO.setLastExceedExportTime(lastExceedExportTime);
				}
				// 申请时间
				if (toLoanAgent.getApplyTime() != null) {
					String applyTime = format.format(toLoanAgent.getApplyTime());
					toLoanAgentVO.setApplyTime(applyTime);
				}
				// 面签时间
				if (toLoanAgent.getSignTime() != null) {
					String formatTime = format.format(toLoanAgent.getSignTime());
					toLoanAgentVO.setSignTime(formatTime);
				}
				// 放款时间
				if (toLoanAgent.getReleaseTime() != null) {
					String formatTime = format.format(toLoanAgent.getReleaseTime());
					toLoanAgentVO.setReleaseTime(formatTime);
				}
				// 对账时间
				if (toLoanAgent.getIncomeConfirmTime() != null) {
					String formatTime = format.format(toLoanAgent.getIncomeConfirmTime());
					toLoanAgentVO.setIncomeConfirmTime(formatTime);
				}
				// 结账时间
				if (toLoanAgent.getIncomeArriveTime() != null) {
					String formatTime = format.format(toLoanAgent.getIncomeArriveTime());
					toLoanAgentVO.setIncomeArriveTime(formatTime);
				}
				toLoanAgentVOs.add(toLoanAgentVO);
			}
		}
		
		                                                                                                                
		SessionUser sessionUser = uamSessionService.getSessionUser();		
		List<TaskVo> tasks = new ArrayList<TaskVo>();
		if (toWorkFlow != null) {
			List<String> insCodeList = toWorkFlowService.queryAllInstCodesByCaseCode(toCase.getCaseCode());
			for(String insCode : insCodeList) {
				TaskHistoricQuery tq = new TaskHistoricQuery();
				tq.setProcessInstanceId(insCode);
				tq.setFinished(true);
				
				List<TaskVo> taskList1 = taskDuplicateRemoval(workFlowManager.listHistTasks(tq).getData());
				tasks.addAll(taskList1);
			}
			// 本人做的任务
			List<TgServItemAndProcessor> myServiceCase = tgServItemAndProcessorService.findTgServItemAndProcessorByCaseCode(toCase.getCaseCode());
			request.setAttribute("myTasks",filterMyTask(myServiceCase,tasks)) ;
		}
		
		TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(sessionUser.getServiceDepCode());
		boolean isBackTeam = false;
		boolean isCaseOwner=false;
		boolean isNewFlow=false;
		boolean isCaseManager=false;
		
		if (tp != null) {
			isBackTeam = "yu_back".equals(tp.getTeamProperty());
		}
		
		if(sessionUser.getId().equals(toCase.getLeadingProcessId())){
			isCaseOwner=true;
		}
		if(toWorkFlow!=null &&"operation_process:34:620096".compareTo(toWorkFlow.getProcessDefinitionId())<=0){
			isNewFlow=true;
		}
		if(isCaseOwner&&TransJobs.TJYZG.getCode().equals(sessionUser.getServiceJobCode())){
			isCaseManager=true;
		}
		
		request.setAttribute("isCaseManager", isCaseManager);
		request.setAttribute("serivceDefId", sessionUser.getServiceDepId());
		request.setAttribute("loanReqType", loanReqType);
		request.setAttribute("isNewFlow", isNewFlow);
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		
		request.setAttribute("isBackTeam", isBackTeam);
		request.setAttribute("isCaseOwner", isCaseOwner);
		request.setAttribute("toCase", toCase);
		request.setAttribute("toCaseInfo", toCaseInfo);
		request.setAttribute("toPropertyInfo", toPropertyInfo);
		request.setAttribute("toWorkFlow", toWorkFlow);
		request.setAttribute("toMortgage", toMortgage);
		request.setAttribute("caseDetailVO", reVo);
		request.setAttribute("caseInfo", caseInfo);
/*		request.setAttribute("toSpv", toSpv);
		request.setAttribute("cashFlows", cashFlows);*/
		request.setAttribute("toLoanAgentVOs", toLoanAgentVOs);
		request.setAttribute("toLoanAgents", toLoanAgents);
		return "case/caseDetail";
	}
	
	/**
	 * 页面初始化
	 * 
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseDetail")
	public String caseDetail(Long caseId,String partCode,HttpServletRequest request) {
		if (caseId == null){
			return "case/caseList";
		}
		/** 基本信息 **/ 
		CaseDetailShowVO reVo = new CaseDetailShowVO();
		ToCase toCase = toCaseService.selectByPrimaryKey(caseId);
		TsTransPlanHistory tsTransPlanHistory=new TsTransPlanHistory();
		if (partCode!=null){
			tsTransPlanHistory.setCaseCode(toCase.getCaseCode());
			tsTransPlanHistory.setAppverPartCode(partCode);
			request.setAttribute("partCode",partCode);
		}
		TsTransPlanHistory th=transplanServiceFacade.findTransPlanHistoryByCaseCode(tsTransPlanHistory);
		if (th!=null){
			if (th.getAuditResult().equals(0)){
				request.setAttribute("auditResult",true);
			}
		}
		SimpleDateFormat yearFort = new SimpleDateFormat("yyyy");
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());
		
		if(toCaseInfo.getPayType() != null){
			String payTypeName = uamBasedataService.getDictValue(TransDictEnum.FKFS.getCode(), toCaseInfo.getPayType());
			toCaseInfo.setPayType(payTypeName);
		}
		
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		toPropertyInfo.setFinishYearStr(toPropertyInfo.getFinishYear() ==null?
										null:yearFort.format(toPropertyInfo.getFinishYear()));
		
		// add zhangxb16 2016-2-22 功能
		ToCase te = toCaseService.findToCaseByCaseCode(toCase.getCaseCode());
		if (null != te) {
			reVo.setCaseProperty(te.getCaseProperty());
		}
		
		SessionUser sessionUser = uamSessionService.getSessionUser();
		

		/** 工作流 **/ 
		ToWorkFlow inWorkFlow = new ToWorkFlow();
		inWorkFlow.setBusinessKey("operation_process");
		inWorkFlow.setCaseCode(toCase.getCaseCode());
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
		
        
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		/** 交易信息 **/
		VCaseTradeInfo caseInfo = vCaseTradeInfoService.queryCaseTradeInfoByCaseCode(toCase.getCaseCode());

		// 业务单创建时间
		if (caseInfo.getCreateTime() != null) {
			String createTime = format.format(caseInfo.getCreateTime());
			reVo.setCreateTime(createTime);
		}
		//接单时间
		if(caseInfo.getRecvTime() != null){
			reVo.setResDate(format.format(caseInfo.getRecvTime()));
		}
		//买卖家
		List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		for (TgGuestInfo guest : guestList) {
			if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
				reVo.setSellerName(guest.getGuestName());
				reVo.setSellerMobile(guest.getGuestPhone());
			} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
				reVo.setBuyerName(guest.getGuestName());
				reVo.setBuyerMobile(guest.getGuestPhone());
			}
		}
		// 房屋类型
		if(toPropertyInfo.getPropertyType() != null){
			String propertyTypeName = uamBasedataService.getDictValue("30014", toPropertyInfo.getPropertyType());
			reVo.setPropertyTypeName(propertyTypeName);
		}	
		// 购房年数,现在存在物业信息中
		if (toPropertyInfo.getHoldYear() != null) {
			String holdYear = uamBasedataService.getDictValue(TransDictEnum.TGFNS.getCode(), toPropertyInfo.getHoldYear());
			reVo.setHoldYear(holdYear);
		}
		// 唯一住房,接单购房套数判断
		ToCaseRecv caseRecv =  toCaseRecvMapper.selectByPrimaryKey(toCase.getCaseCode());
		if(caseRecv != null){
			if("61000001".equals(caseRecv.getPurchaseHouseNo())){
				reVo.setIsUniqueHome("是");
			}else{
				reVo.setIsUniqueHome("否");
			}
		}
		
		//网签时间
		if (caseInfo.getRealConTime() != null) {
			String realConTime = format.format(caseInfo.getRealConTime());
			reVo.setRealConTime(realConTime);
		}
		// 付款方式(一期)
		if (caseInfo.getPayType1() != null) {
			String payType1 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType1());
			reVo.setPayType1(payType1);
		} 
		// 付款方式(二期)
		if (caseInfo.getPayType2() != null) {
			String payType2 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType2());
			reVo.setPayType2(payType2);
		} 
		// 付款方式(三期)
		if (caseInfo.getPayType3() != null) {
			String payType3 = uamBasedataService.getDictValue(TransDictEnum.TFKFS.getCode(), caseInfo.getPayType3());
			reVo.setPayType3(payType3);
		} 
		// 付款时间(一期)
		if (caseInfo.getPayTime1() != null) {
			String payTime1 = format.format(caseInfo.getPayTime1());
			reVo.setPayTime1(payTime1);
		} 
		// 付款时间(二期)
		if (caseInfo.getPayTime2() != null) {
			String payTime2 = format.format(caseInfo.getPayTime2());
			reVo.setPayTime2(payTime2);
		} 
		// 付款时间(三期)
		if (caseInfo.getPayTime3() != null) {
			String payTime3 = format.format(caseInfo.getPayTime3());
			reVo.setPayTime3(payTime3);
		}
		//缴税时间
		if(caseInfo.getPaymentTime()!=null){
			String paymentTime=format.format(caseInfo.getPaymentTime());
			reVo.setPaymentTime(paymentTime);
		}
		// 过户时间
		if (caseInfo.getRealHtTime() != null) {
			String realHtTime = format.format(caseInfo.getRealHtTime());
			reVo.setRealHtTime(realHtTime);
		}
		// 领证时间
		if (caseInfo.getRealPropertyGetTime() != null) {
			String realPropertyGetTime = format.format(caseInfo.getRealPropertyGetTime());
			reVo.setRealPropertyGetTime(realPropertyGetTime);
		}
		//卖方剩余贷款，还贷时间，还贷银行,赎楼
		/*List<ToRansomFormVo> tails = addRansomFormMapper.findTaiLinsInfoByCaseCode(toCase.getCaseCode());
		if(tails != null && tails.size() > 0){
			BigDecimal resMoney = tails.get(0).getRestMoney().add(
					tails.size() >1?(tails.get(1).getRestMoney()==null?BigDecimal.ZERO:tails.get(1).getRestMoney()):BigDecimal.ZERO
							);
			
			caseInfo.setUncloseMoney(resMoney);//剩余金额
			//还款时间
			reVo.setLoanCloseCode(tails.get(0).getRepayTime()==null?null:format.format(tails.get(0).getRepayTime()));
			//还款银行/合作机构
			caseInfo.setUpBank(tails.get(0).getComOrgName());
		} */ 
		// 结案时间
		if (caseInfo.getCloseTime() != null) {
			String closeTime = format.format(caseInfo.getCloseTime());
			reVo.setCloseTime(closeTime);
		}
		
		/** 贷款信息 **/
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCodeOnlyOne(toCase.getCaseCode());
		//贷款挽回
		String mortgageSaveSelf = toMortgageService.findMortgageSave(toCase.getCaseCode());
		if (toMortgage != null) {

			//贷款方式
			toMortgage.setMortType(uamBasedataService.getDictValue("30016", toMortgage.getMortType()));
			//自办代办
			reVo.setIsSelf("否");
			// 面签时间
			if (toMortgage.getSignDate() != null) {
				String signDate = format.format(toMortgage.getSignDate());
				reVo.setSignDate(signDate);
			}
			// 批贷时间
			if (toMortgage.getApprDate() != null) {
				String apprDate = format.format(toMortgage.getApprDate());
				reVo.setApprDate(apprDate);
			}

			// 放款方式
			if (toMortgage.getLendWay() != null) {
				String lendWay = uamBasedataService.getDictValue(TransDictEnum.TLENDWAY.getCode(),
						toMortgage.getLendWay());
				reVo.setLendWay(lendWay);
			}
			// 他证送达时间
			if (toMortgage.getTazhengArrDate() != null) {
				String tazhengArrDate = format.format(toMortgage.getTazhengArrDate());
				reVo.setTazhengArrString(tazhengArrDate);
			} 
			// 放款时间
			if (toMortgage.getLendDate() != null) {
				String lendDate = format.format(toMortgage.getLendDate());
				reVo.setLendDate(lendDate);
			}
			// 评估公司
			ToEvaReport evaReport = toEvaReportService.findFinalComByCaseCode(toCase.getCaseCode());
			if (evaReport != null && !StringUtils.isEmpty(evaReport.getFinOrgCode())) {
				TsFinOrg reportCom = tsFinOrgService.findBankByFinOrg(evaReport.getFinOrgCode());
				reVo.setEvaName(reportCom.getFinOrgName());
			}
			// 评估费金额
			ToEvaFeeRecord evaFeeReport = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(toCase.getCaseCode());
			if (evaFeeReport != null && evaFeeReport.getEvalFee() != null) {
				reVo.setEvaFee(evaFeeReport.getEvalFee());
			}
			// 分行支行
			String finOrgCodeString = toMortgage.getFinOrgCode();
			if (!StringUtils.isEmpty(finOrgCodeString)) {
				TsFinOrg bank = tsFinOrgService.findBankByFinOrg(finOrgCodeString);
				reVo.setBankName(bank.getFinOrgName());
				if (!StringUtils.isEmpty(bank.getFaFinOrgCode())) {
					TsFinOrg faBank = tsFinOrgService.findBankByFinOrg(bank.getFaFinOrgCode());
					reVo.setParentBankName(faBank.getFinOrgName());
				}
			}
		}else if(mortgageSaveSelf !=null){
			if("1".equals(mortgageSaveSelf) ){
				reVo.setIsSelf("是");
			}
		}

		// 金融服务信息,赎楼信息
		ToRansomFormVo ransomInfo =  ransomMapper.getRansomInfoByCaseCode(toCase.getCaseCode());
		
	
		boolean isCaseOwner=false;
		boolean isCaseManager=false;

		//判断是否为自己的案件
		if(sessionUser.getId().equals(toCase.getLeadingProcessId())){
			isCaseOwner=true;
		}
	
		if(isCaseOwner&&TransJobs.TJYZG.getCode().equals(sessionUser.getServiceJobCode())){
			isCaseManager=true;
		}
		
		String jobId = uamSessionService.getSessionUser().getServiceJobId();
		List<Resource> resourcelist = uamPermissionService.getResourceByJobId(jobId);
		request.setAttribute("resourcelist", resourcelist);


		/** 案件重启中对信息管理员开放 ff80808158af2e600158b98c82340049 ***/
		/*if(null != sessionUser){
			if(StringUtils.equals(sessionUser.getServiceJobCode(), "COXXGLY")){
				request.setAttribute("serviceJobType", "Y");
			}else{
				request.setAttribute("serviceJobType", "N");
			}
		}*/
		/** 公共信息 start **/
		/*getCaseBaseInfo(request, toCase.getCaseCode());*/
		/** 公共信息 end **/
		
		request.setAttribute("isCaseManager", isCaseManager);
		request.setAttribute("serivceDefId", sessionUser.getServiceDepId());
//		request.setAttribute("loanReqType", loanReqType);
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		
		request.setAttribute("isCaseOwner", isCaseOwner);
		request.setAttribute("toCaseInfo", toCaseInfo);
		request.setAttribute("toWorkFlow", toWorkFlow);
		request.setAttribute("toMortgage", toMortgage);
		request.setAttribute("caseDetailVO", reVo);
		request.setAttribute("caseInfo", caseInfo);

		request.setAttribute("toCase", toCase);

		request.setAttribute("toPropertyInfo", toPropertyInfo);
		request.setAttribute("ransomInfo", ransomInfo);
		
		return "case/caseDetail_new";
	}
	
	/**
	 * 用户机构贷款/过户权证查询
	 * @param request
	 * @param caseCode
	 * @param type
	 * @return
	 * @throws ParseException
	 */
    @RequestMapping(value = "/getLoanOrWarrantList")
    @ResponseBody
    public List<VCaseDistributeUserVO> getLoanOrWarrantList(HttpServletRequest request, 
    									@RequestParam(value="caseCode",required=true)String caseCode,
    									@RequestParam(value="type",required=true)String type) throws ParseException{
    	List<VCaseDistributeUserVO> res = new ArrayList<VCaseDistributeUserVO>();
        // 获取当前用户
        SessionUser sessionUser = uamSessionService.getSessionUser();
//        ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
        //获取贷款/过户权证,根据案件负责人选择loan OR warrant
        //默认过户
        /*String leadingType = TransJobs.GHQZ.getCode();
        
        ToCaseParticipant partVo = new ToCaseParticipant();
        partVo.setCaseCode(caseCode);
		//案件参与人信息
		List<ToCaseParticipant> caseParticipants = toCaseParticipantService.findToCaseParticipantByCondition(partVo);
        for(ToCaseParticipant part : caseParticipants){
        	if(CaseParticipantEnum.LOAN.getCode().equals(part.getPosition())){
        		leadingType = TransJobs.DKQZ.getCode();
        		break;
        	}
        }*/
        //本身岗位
        String position = "";
        //另一权证岗位
        String otherPosition = "";
        String leadingType = "";
        //判断变更哪个权证类型
        if(CaseParticipantEnum.LOAN.getCode().equals(type)){
        	leadingType = TransJobs.DKQZ.getCode();
        	position = CaseParticipantEnum.LOAN.getCode();
        	otherPosition = CaseParticipantEnum.WARRANT.getCode();
        }else if(CaseParticipantEnum.WARRANT.getCode().equals(type)){
        	leadingType = TransJobs.GHQZ.getCode();
        	position = CaseParticipantEnum.WARRANT.getCode();
        	otherPosition = CaseParticipantEnum.LOAN.getCode();
        }
        
        //根据权证类型查询
        List<User> userList=new ArrayList<User>();
        userList = uamUserOrgService.getUserByOrgIdAndJobCode(sessionUser.getServiceDepId(), leadingType);
        //排除本身
        ToCaseParticipant partVo = new ToCaseParticipant();
        partVo.setCaseCode(caseCode);
        partVo.setPosition(position);
		//案件参与人信息
		List<ToCaseParticipant> caseParticipants = toCaseParticipantService.findToCaseParticipantByCondition(partVo);
		if(caseParticipants != null && caseParticipants.size() > 0){
			String userName = caseParticipants.get(0).getUserName();
	        for(User user : userList){
	        	if(userName.equals(user.getUsername())){
	        		userList.remove(user);
	        		break;
	        	}
	        }
		}

        //添加拦截，排除本身是另一岗位的可能性
        partVo.setPosition(otherPosition);
		//另一岗位同人
		List<ToCaseParticipant> caseParts = toCaseParticipantService.findToCaseParticipantByCondition(partVo);
        if(caseParts != null && caseParts.size() > 0){
        	String userName = caseParts.get(0).getUserName();
        	for(User user : userList){
        		if(userName.equals(user.getUsername())){
        			userList.remove(user);
        			break;
        		}
        	}
        }
        
        for (int i = 0; i < userList.size(); i++){
            VCaseDistributeUserVO vo = new VCaseDistributeUserVO();
            User user = userList.get(i);
            vo.setId(user.getId());
            vo.setMobile(user.getMobile());
            vo.setRealName(user.getRealName());
            int userCaseCount = toCaseInfoService.queryCountCasesByUserId(user.getId());
            int userCaseMonthCount = toCaseInfoService.queryCountMonthCasesByUserId(user.getId());
            int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user.getId());

            vo.setUserCaseCount(userCaseCount);
            vo.setUserCaseMonthCount(userCaseMonthCount);
            vo.setUserCaseUnTransCount(userCaseUnTransCount);
            //添加个权证区分
            vo.setType(TransJobs.getName(leadingType));
            String url = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/" + user.getEmployeeCode() + ".jpg";
            vo.setImgUrl(url);
            res.add(vo);
        }
        return res;
    }
    
    
	private List<TaskVo>filterMyTask(List<TgServItemAndProcessor>mySerivceItems,List<TaskVo>tasks){
		if (tasks == null || mySerivceItems == null || tasks.isEmpty() || mySerivceItems.isEmpty()) {
			return tasks;
		}
		
		Set<String>taskDfKeys=new HashSet<>();
		
		mySerivceItems.parallelStream().forEach(item->{
			Dict d =uamBasedataService.findDictByType(item.getSrvCode());
			if(d!=null&&d.getChildren()!=null){
				d.getChildren().parallelStream().forEach(sc->{
					if(!taskDfKeys.contains(sc.getCode())){
						taskDfKeys.add(sc.getCode());
					}
				});
			}
		});
		
		Iterator<TaskVo> it = tasks.iterator();
		while (it.hasNext()) {
			TaskVo task=it.next();
			if(!taskDfKeys.contains(task.getTaskDefinitionKey())){
				it.remove();
			}
		}
		return tasks;
	}
	private List<TaskVo> taskDuplicateRemoval(List<TaskVo> oList) {
		Map<String, TaskVo> hashMap = new HashMap<>();
	
		for (TaskVo taskVo : oList) {
			hashMap.put(taskVo.getTaskDefinitionKey(), taskVo);
		}
		List<TaskVo> result = new ArrayList<>(hashMap.values());
		return result;
	}

	/**
	 * 变更服务项 页面初始化
	 * 
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSrvsByCaseCode")
	@ResponseBody
	public AjaxResponse<?> getSrvsByCaseCode(String caseCode, ServletRequest request) {
		if (true)
			return null;
		// 服务项
		StringBuffer srvCodes = new StringBuffer();
		List<TgServItemAndProcessor> tgSrvList = tgServItemAndProcessorService
				.findTgServItemAndProcessorByCaseCode(caseCode);
		for (TgServItemAndProcessor sp : tgSrvList) {
			if (!StringUtils.isEmpty(sp.getSrvCode())) {
				srvCodes.append(sp.getSrvCat());
				srvCodes.append(",");
			}

		}
		if (srvCodes.length() > 0)
			srvCodes.deleteCharAt(srvCodes.length() - 1);
		return AjaxResponse.success(srvCodes.toString());
	}

	/**
	 * 信息管理员服务变更
	 * @param caseCode
	 * @param prItems
	 * @param request
	 * @param srvs
	 * @return
	 */
	@RequestMapping(value = "/saveSrvItemsForManager")
	@ResponseBody
	public AjaxResponse<?> saveSrvItemsForManager(String caseCode, String[] prItems,HttpServletRequest request,String[] srvs) {
		Boolean flag = true;
		List<String> srvsd = tgServItemAndProcessorService.findSrvCatsByCaseCode(caseCode);
		if (isChanged(srvsd, srvs)) {
			return AjaxResponse.fail("服务变更失败，请刷新页面后重试！");
		}
		List<String> oldSrvs = new ArrayList<String>();
		oldSrvs.addAll(srvsd);
		/*first:for(String strCode : oldSrvs){
			for (String s : prItems) {
				if(strCode.equals(s)){
					continue first;
				}
			}
			if(strCode.indexOf("30004010")!=-1||strCode.indexOf("30004001")!=-1){
				return AjaxResponse.fail("交易过户与商业贷款不允许在此取消");
			}
		}*/
		for (String s : prItems) {

			if (oldSrvs.contains(s)) {
				continue;
			}
			TgServItemAndProcessor record = new TgServItemAndProcessor();
			record.setCaseCode(caseCode);
			record.setSrvCat(s);
			Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(), record.getSrvCat());
			if (dict == null) {
				tgServItemAndProcessorService.insertSelective(record);
				continue;
			}
			List<Dict> listD = dict.getChildren();
			if (listD == null || listD.size() == 0) {
				record.setSrvCode(s);
				tgServItemAndProcessorService.insertSelective(record);
				continue;
			}
			for (Dict dictSon : listD) {
				record.setSrvCode(dictSon.getCode());
				if (tgServItemAndProcessorService.findTgServItemAndProcessor(record) == null) {
					int reInsert = tgServItemAndProcessorService.insertSelective(record);
					if (reInsert == 0)
						return AjaxResponse.fail("服务与经办人表更新失败！");
				}
			}
		}
		return AjaxResponse.success("变更成功！");
	}



	/**
	 * 交易计划变更 页面初始化
	 * @author wbzhouht
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getTransPlanByCaseCode")
	@ResponseBody
	public List<ToTransPlan> getTransPlanByCaseCode(String caseCode, ServletRequest request) {
		List<ToTransPlan> plans = transplanServiceFacade.queryPlansByCaseCode(caseCode);
		ToRatePayment toRatePayment=toRatePaymentService.qureyToRatePayment(caseCode);
		ToGetPropertyBook toGetPropertyBook=toGetPropertyBookService.findGetPropertyBookByCaseCode(caseCode);
		ToHouseTransfer toHouseTransfer=toHouseTransferService.findToGuoHuByCaseCode(caseCode);
		ToMortgage toMortgage=toMortgageService.findToMortgageByCaseCode2(caseCode);
		ToSign toSign=signService.findToSignByCaseCode(caseCode);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<ToTransPlan>transPlan=new ArrayList<ToTransPlan>();
		if (plans != null && plans.size() > 0) {
			for (ToTransPlan plan : plans) {
				if (!StringUtils.isEmpty(plan.getPartCode())) {
					String partNameString = uamBasedataService.getDictValue(TransDictEnum.TRANSPLANDATE.getCode(),
							plan.getPartCode());
					plan.setPartName(partNameString);
				}
				if (plan.getEstPartTime() != null) {
					plan.setEstPartTimeStr(format.format(plan.getEstPartTime()));
				}
				/**判断是否有已经走了的环节，走过的环节则不能修改时间 by wbzhouht*/
				if(toSign!=null){
					if(PartCodeEnum.WQ.getCode().equals(plan.getPartCode())){
						plan.setEdit(false);
					}
				}
				if(toRatePayment!=null){
					if(PartCodeEnum.JS.getCode().equals(plan.getPartCode())){
						plan.setEdit(false);
					}
				}
				if(toHouseTransfer!=null){
					if(PartCodeEnum.GH.getCode().equals(plan.getPartCode())){
						plan.setEdit(false);
					}
				}
				if(toMortgage!=null){
					if(PartCodeEnum.SDMQ.getCode().equals(plan.getPartCode())||PartCodeEnum.SDCPG.getCode().equals(plan.getPartCode())||
							PartCodeEnum.SDPD.getCode().equals(plan.getPartCode())||PartCodeEnum.GJJDK.getCode().equals(plan.getPartCode())||
							PartCodeEnum.GJJMQ.getCode().equals(plan.getPartCode())||PartCodeEnum.GJJPD.getCode().equals(plan.getPartCode())){
						plan.setEdit(false);
					}
				}
				if(toGetPropertyBook!=null){
					if(PartCodeEnum.LZ.getCode().equals(plan.getPartCode())){
						plan.setEdit(false);
					}
				}

				transPlan.add(plan);
			}
		}

		return transPlan;
	}

	/**
	 * 变更经办人
	 * 
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/changeLeadingUser")
	@ResponseBody
	public AjaxResponse<?> changeLeadingUser(String instCode, String caseCode, String userId,String chooseType, HttpServletRequest request) {
		/**
		 * TODO 如果贷款权证与过户权证同一人，更换权证时，可能会将另一岗位的任务责任人替换掉,原因是任务查询只能通过username
		 * 		变更时添加同一人的拦截,与产品确认，贷款权证与过户权证不可能出现为同一人的情况，但需注意此点
		 * @author wbcaiyx
		 * @date 2017/10/25
		 */
		
		//如果不是案件责任人，只修改案件参与人信息及案件流程中过户权证参数
		ToCaseParticipant param = new ToCaseParticipant();
		param.setCaseCode(caseCode);
		List<ToCaseParticipant> caseParts = toCaseParticipantService.findToCaseParticipantByCondition(param);
		
		String leadingVal = CaseParticipantEnum.WARRANT.getCode();
        for(ToCaseParticipant part : caseParts){
        	if(CaseParticipantEnum.LOAN.getCode().equals(part.getPosition())){
        		leadingVal = CaseParticipantEnum.LOAN.getCode();
        		break;
        	}
        }
        
        User u_new = uamUserOrgService.getUserById(userId);
		//权证经理
		User manager = new User();
		List<User> managerList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(u_new.getOrgId(),TransJobs.QZJL.getCode());
		if(managerList != null && managerList.size() > 0){
			manager = managerList.get(0);
		}else{
			return AjaxResponse.fail("该权证无法获取分行经理!");
		}
        //只是变更过户权证，不是责任人
		if(!leadingVal.equals(chooseType)){

			//更新案件分配人
			toCaseParticipantService.updateCaseParticipant(caseCode, u_new, manager,chooseType);
			if(!StringUtils.isBlank(instCode)){
				RestVariable restVariable = new RestVariable();
		        restVariable.setType("string");
		        restVariable.setValue(u_new.getUsername());
				workFlowManager.setVariableByProcessInsId(instCode, chooseType, restVariable);
				
				//拿过户权证的用户名查询存在的任务
				String userOldName = "";
				for(ToCaseParticipant part : caseParts){
		        	if(CaseParticipantEnum.WARRANT.getCode().equals(part.getPosition())){
		        		userOldName = part.getUserName();
		        		break;
		        	}
		        }
				TaskQuery tq = new TaskQuery();
				tq.setProcessInstanceId(instCode);
				tq.setFinished(false);
				//该权证的任务
				tq.setAssignee(userOldName);
				List<TaskVo> tasks =  new ArrayList<TaskVo>();		
				PageableVo pageVo =	workFlowManager.listTasks(tq);
				if(pageVo != null){
					tasks = pageVo.getData();
				}				
				//流程任务人变更
				updateWorkflow(userId, tasks, caseCode); 
			}
		}else{
			//变更的是责任人
			// 案件信息更新
			ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
			String origUserId = toCase.getLeadingProcessId();
			User u = uamUserOrgService.getUserById(origUserId);
			
			int reToCase = 0;
			try{
				//更新案件信息
				toCase.setLeadingProcessId(userId);
				reToCase = toCaseService.updateByPrimaryKey(toCase);
				//更新案件分配人
				toCaseParticipantService.updateCaseParticipant(caseCode, u_new, manager,chooseType);
			} catch(Exception e){
				return AjaxResponse.fail(e.getMessage());
			}
			if (reToCase == 0){
				return AjaxResponse.fail("案件基本表更新失败！");
			}
			/**
			 * 原码,no modify
			 */
			TgServItemAndProcessor record = new TgServItemAndProcessor();
			record.setPreProcessorId(toCase.getLeadingProcessId());
			record.setProcessorId(userId);
			record.setOrgId(u_new.getOrgId());
			record.setCaseCode(caseCode);
			record.setPreProcessorId(origUserId);
			record.setPreOrgId(u.getOrgId());	
			tgServItemAndProcessorService.updateByCaseCode(record);
	
			//流程变量更新,如果是贷款权证修改loan参数，如果是过户权证修改warrant参数
			if(!StringUtils.isBlank(instCode)){
				//接单参数receiver
				String receiveVal = "receiver";
				//经理
				String managerVal = "manager";
				
				ToCaseParticipant vo = new ToCaseParticipant();
				vo.setCaseCode(caseCode);
				/*List<ToCaseParticipant> caseParticipants = toCaseParticipantService.findToCaseParticipantByCondition(vo);
				String paramVal = CaseParticipantEnum.WARRANT.getCode();
		        for(ToCaseParticipant part : caseParticipants){
		        	if(CaseParticipantEnum.LOAN.getCode().equals(part.getPosition())){
		        		paramVal = CaseParticipantEnum.LOAN.getCode();
		        		break;
		        	}
		        }*/
		        RestVariable restVariable = new RestVariable();
		        restVariable.setType("string");
		        restVariable.setValue(u_new.getUsername());
		        try{
		        	//接单人员
		        	workFlowManager.setVariableByProcessInsId(instCode, receiveVal, restVariable);		
		        	//贷款权证or过户权证
					workFlowManager.setVariableByProcessInsId(instCode, chooseType, restVariable);
		        	//经理manager			
					restVariable.setValue(manager.getUsername());
		        	workFlowManager.setVariableByProcessInsId(instCode, managerVal, restVariable);
				}catch(WorkFlowException e){
					if(404!=e.getStatusCode()){
						throw e;
					}
				}
		        TaskQuery tq = new TaskQuery();
				tq.setProcessInstanceId(instCode);
				tq.setFinished(false);
				tq.setAssignee(u.getUsername());
				List<TaskVo> tasks =  new ArrayList<TaskVo>();		
				PageableVo pageVo =	workFlowManager.listTasks(tq);
				if(pageVo != null){
					tasks = pageVo.getData();
				}				
				//获取贷款流程(贷款权证)
				tasks.addAll(getNonMainWorkflowByAssignee(caseCode,u.getUsername()));
				//流程任务人变更
				updateWorkflow(userId, tasks, caseCode);        
			}
		}
		return AjaxResponse.success("变更成功！");
	}
	
	
	/**
	 * 查询指定办理人的任务项(贷款流程)
	 * @param caseCode
	 * @param assignee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TaskVo> getNonMainWorkflowByAssignee(String caseCode,String assignee){
		List<TaskVo> result=new ArrayList<TaskVo>();
		ToWorkFlow wfQueryBean=new ToWorkFlow();
		wfQueryBean.setCaseCode(caseCode);
		wfQueryBean.setBizCodes(Arrays.asList("ComLoan_Process","LoanLost_Process","PSFLoan_Process"));
		List<ToWorkFlow> subWF= toWorkFlowService.queryToWorkFlowByCaseCodeBusKeys(wfQueryBean);
		for (ToWorkFlow wf : subWF) {
			TaskQuery tq = new TaskQuery();
			tq.setProcessInstanceId(wf.getInstCode());
			tq.setFinished(false);
			tq.setAssignee(assignee);
			
			List<TaskVo> tks = workFlowManager.listTasks(tq).getData();
			if(tks!=null &&!tks.isEmpty()){
				result.addAll(tks);
			}
		}
		return result;
	}
	
	/**
	 * 流程办理人变更
	 * @param userId 新任务人id
	 * @param tasks 任务
	 * @param caseCode
	 */
	public void updateWorkflow(String userId, List<TaskVo> tasks,String caseCode) {
		if (tasks != null && !tasks.isEmpty()) {
			User u = uamUserOrgService.getUserById(userId);
			for (TaskVo taskVo : tasks) {
				//T_TL_TASK_REASSIGNT_LOG 插入日志
				taskReassingtLogService.record(taskVo, u.getUsername(), caseCode);
				taskVo.setAssignee(u.getUsername());
				//更新流程
				workFlowManager.updateTask(taskVo);
			}
		}
	}
	
	/**
	 * 变更责任人  默认责任人显示
	 * @author zhuody
	 * @Date 2016-12-02
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectLeandingPro")
	@ResponseBody
	public Map<String, Object> selectLeandingPro(HttpServletRequest request, HttpServletResponse response, String caseCode) {

		Map<String, Object> map = new HashMap<String, Object>();	
		
		if(caseCode !=null && !"".equals(caseCode)){
			ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
			User user = uamUserOrgService.getUserById(toCase.getLeadingProcessId()==null ? "":toCase.getLeadingProcessId());
			map.put("leadingProcessId", toCase.getLeadingProcessId()==null ? "":toCase.getLeadingProcessId());
			map.put("leadingProcessName", user.getRealName()==null ? "":user.getRealName());
		}		
		return map;
	}
	
	
	/**
	 * 变更责任人  For allUser
	 * @author zhuody
	 * @Date 2016-12-02
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/changeLeadingPro")
	@ResponseBody
	public AjaxResponse<?> changeLeadingPro(String caseCode, String userId,HttpServletRequest request) {
		ToCase toCase = new ToCase();
		ToWorkFlow toWorkFlow =  new ToWorkFlow();
		// 案件信息更新
		if(caseCode != null  && !"".equals(caseCode)){
			toCase = toCaseService.findToCaseByCaseCode(caseCode);
			
			// 工作流
			ToWorkFlow inWorkFlow = new ToWorkFlow();
			inWorkFlow.setBusinessKey("operation_process");
			inWorkFlow.setCaseCode(caseCode);
			toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
		}
		
		String origUserId = toCase.getLeadingProcessId();
		User u = uamUserOrgService.getUserById(origUserId);
		User u_ = uamUserOrgService.getUserById(userId);

		TgServItemAndProcessor record = new TgServItemAndProcessor();
		record.setPreProcessorId(origUserId);
		record.setPreOrgId(u.getOrgId());
		record.setProcessorId(userId);
		record.setOrgId(u_.getOrgId());
		toCase.setLeadingProcessId(userId);
		int reToCase = toCaseService.updateByPrimaryKey(toCase);
		
		User u1 = uamUserOrgService.getUserById(userId);
		record.setProcessorId(userId);
		record.setCaseCode(caseCode);
		
		tgServItemAndProcessorService.updateByCaseCode(record);

		// 更新流程引擎
		if (!StringUtils.isBlank(toWorkFlow.getInstCode())) {
			String variableName = "caseOwner";
			RestVariable restVariable = new RestVariable();
			restVariable.setType("string");
			restVariable.setValue(u1.getUsername());
			try{
				workFlowManager.setVariableByProcessInsId(toWorkFlow.getInstCode(), variableName, restVariable);
			}catch(WorkFlowException e){
				if(404!=e.getStatusCode()){
					throw e;
				}
			}
			
			TaskQuery tq = new TaskQuery();
			tq.setProcessInstanceId(toWorkFlow.getInstCode());
			tq.setFinished(false);
			tq.setAssignee(u.getUsername());
			List<TaskVo> tasks = workFlowManager.listTasks(tq).getData();
			updateWorkflow(userId, tasks, caseCode);
		}
		if (reToCase == 0)
			return AjaxResponse.fail("案件基本表更新失败！");

		return AjaxResponse.success("变更成功！");
	}

	private boolean containsDic(Dict dic, String dicCode) {
		if (StringUtils.isBlank(dicCode) || dic == null)
			return false;
		if (dicCode.equals(dic.getCode())) {
			return true;
		}
		for (Dict subDic : dic.getChildren()) {
			if (containsDic(subDic, dicCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 产调发起
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/startCasePrairses")
	@ResponseBody
	public AjaxResponse<?> startCasePrairses(String caseCode, String[] prItems, HttpServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
	
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		if (toPropertyInfo == null || toPropertyInfo.getPkid() == null)
			return AjaxResponse.fail("无法找到物业信息！");
		ToCaseInfo cInfo =toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if(cInfo==null){
			return AjaxResponse.fail("无法找到案件信息！");
		}
		ToPropertyResearchVo vo = new ToPropertyResearchVo();
		vo.setCaseCode(caseCode);
		
		//记录产调申请人的组织
		ToPropertyResearchVo pro = toPropertyInfoService.getPropertyDepInfoByuserDepId(user.getServiceDepId());
		//记录产调申请人的组织
		if(pro != null){
			vo.setPrApplyDepId(pro.getPrApplyDepId());
			vo.setPrApplyDepName(pro.getPrApplyDepName());
		}
		
		// 产调项目
		StringBuffer prCat = new StringBuffer();
		for (String prCode : prItems) {
			String prNameString = uamBasedataService.getDictValue(TransDictEnum.TCDXM.getCode(), prCode);
			prCat.append(prNameString);
			prCat.append("/");
		}
		prCat.deleteCharAt(prCat.length() - 1);
		vo.setPrCat(prCat.toString());

		// 秘书
		Org qyOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
		if (qyOrg != null) {
			vo.setDistrictId(qyOrg.getId());
		}
		vo.setDistrict(toPropertyInfo.getDistCode());
		
		vo.setAppliant(user.getId());
		vo.setPrApplyOrgId(user.getServiceDepId());
		vo.setPrApplyOrgName(user.getServiceDepName());
		vo.setPropertyAddr(toPropertyInfo.getPropertyAddr());
		vo.setAgentCode(cInfo.getAgentCode());
		int reInt=toPropertyResarchService.recordProperty(vo);
		
		if (reInt == 0)
			return AjaxResponse.fail("产调表更新失败！");

		return AjaxResponse.success("变更成功！");
	}

	/**
	 * 判断服务项是否有变更（不考虑重复项问题）
	 * 
	 * @param arrs1
	 * @param arrs2
	 * @return
	 */
	private boolean isChanged(List<String> arrs1, String[] arrs2) {
		if (((arrs1 == null || arrs1.size() == 0) && (arrs2 != null && arrs2.length != 0))
				|| (arrs2 == null || arrs2.length == 0) && (arrs1 != null && arrs1.size() != 0)
				|| arrs1.size() != arrs2.length) {// 长度不同
			return true;
		} else if (arrs1 == null || arrs1.size() == 0) {
			return false;
		}
		for (int i = 0; i < arrs2.length; i++) {
			if (!arrs1.contains(arrs2[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 变更服务项
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSrvItems")
	@ResponseBody
	public AjaxResponse<?> saveSrvItems(String caseCode, String[] prItems, Boolean isDel, Boolean isRes, String[] srvs,
			HttpServletRequest request) {

		SessionUser sessionUser = uamSessionService.getSessionUser();

		List<String> srvsd = tgServItemAndProcessorService.findSrvCatsByCaseCode(caseCode);
		if (isChanged(srvsd, srvs)) {
			return AjaxResponse.fail("服务变更失败，请刷新页面后重试！");
		}

		List<String> oldSrvs = new ArrayList<String>();
		oldSrvs.addAll(srvsd);

		// 删除项  相同的服务项则不变更
		for (String s : prItems) {
			if (srvsd.contains(s)) {
				srvsd.remove(s);
			}
		}

		// 交易过户服务 爆单
		if (isDel || isRes || srvsd.size() > 0) {
			ToWorkFlow selecToWorkFlow = new ToWorkFlow();
			selecToWorkFlow.setCaseCode(caseCode);
			selecToWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
			ToWorkFlow reWorkFlow = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(selecToWorkFlow);
			if (!(reWorkFlow == null || reWorkFlow.getPkid() == null)) {
				return AjaxResponse.fail("已发起服务变更申请，无法重复发起！");
			}
			
			Map<String,Object>vars=new HashMap<>();
		    User manager=uamUserOrgService.getLeaderUserByOrgIdAndJobCode(sessionUser.getServiceDepId(), "Manager");
		    vars.put("consultant", sessionUser.getUsername());
		    vars.put("Manager", manager.getUsername());
		    StartProcessInstanceVo pIVo =processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SRV_BUSSKEY.getCode()), caseCode, vars);
			
		    ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setCaseCode(caseCode);
			toWorkFlow.setBizCode(caseCode);
			toWorkFlow.setInstCode(pIVo.getId());
			toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
			toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
			toWorkFlow.setProcessOwner(sessionUser.getId());
			toWorkFlowService.insertSelective(toWorkFlow);

			AjaxResponse response = new AjaxResponse();
			//获取所有运行中的流程
			TaskQuery tq = new TaskQuery(pIVo.getId(), true);
			PageableVo pageableVo = workFlowManager.listTasks(tq);
			List<TaskVo> taskList = pageableVo.getData();
			if (taskList != null && taskList.size() > 0) {
				TaskVo taskVo = (TaskVo) taskList.get(0);
				response.setContent(taskVo.getId());
			}

			// 爆单
			if (isDel) {
				ToServChangeHistroty record = new ToServChangeHistroty();
				record.setCaseCode(caseCode);
				record.setChangeType("2");
				record.setProcessInstanceId(pIVo.getId());
				toServChangeHistrotyService.insertSelective(record);
				response.setMessage("爆单");
				response.setSuccess(true);
				return response;
				// 重启 &删除项
			} else {
				// add
				for (String s : prItems) {
					if (oldSrvs.contains(s)) {
						continue;
					}

					ToServChangeHistroty record = new ToServChangeHistroty();
					record.setCaseCode(caseCode);
					record.setChangeType("0");
					record.setProcessInstanceId(pIVo.getId());
					record.setServCode(s);
					toServChangeHistrotyService.insertSelective(record);
				}
				// delete
				for (String oldSrv : srvsd) {
					if (oldSrv.equals("")) {
						continue;
					}
					ToServChangeHistroty record = new ToServChangeHistroty();
					record.setCaseCode(caseCode);
					record.setChangeType("1");
					record.setProcessInstanceId(pIVo.getId());
					record.setServCode(oldSrv);
					toServChangeHistrotyService.insertSelective(record);
				}
				if (isRes) {
					response.setMessage("重启");
				} else {
					response.setMessage("删除");
				}
				response.setSuccess(true);
				return response;
			}
		} else {
			// 新增项
			for (String s : prItems) {
				if (oldSrvs.contains(s)) {
					continue;
				}
				TgServItemAndProcessor record = new TgServItemAndProcessor();
				record.setCaseCode(caseCode);
				record.setSrvCat(s);
				Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(), record.getSrvCat());
				if (dict == null) {
					record.setSrvCode(s);
					tgServItemAndProcessorService.insertSelective(record);
					continue;
				}
				List<Dict> listD = dict.getChildren();
				if (listD == null || listD.size() == 0) {
					record.setSrvCode(s);
					tgServItemAndProcessorService.insertSelective(record);
					continue;
				}
				for (Dict dictSon : listD) {
					record.setSrvCode(dictSon.getCode());
					if (tgServItemAndProcessorService.findTgServItemAndProcessor(record) == null) {
						int reInsert = tgServItemAndProcessorService.insertSelective(record);
						if (reInsert == 0)
							return AjaxResponse.fail("服务与经办人表更新失败！");
					}
				}
			}
			return AjaxResponse.success("变更成功！");
		}
	}

	/**
	 * 根据日期查询统计数据
	 * 
	 * @param model
	 * @param request
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "queryConutCaseByDate")
	@ResponseBody
	public ToCaseInfoCountVo queryConutCaseByDate(Model model, ServletRequest request, String createTime,
			String userId) {
		int jds = 0;
		int qys = 0;
		int ghs = 0;
		int jas = 0;
		ToCase toCase = new ToCase();
		ToCaseInfoCountVo toSignCount = null;

		ToCaseInfoCountVo toHouseTransferCount = null;
		ToCaseInfoCountVo toCloseCount = null;
		ToCaseInfoCountVo toCaseInfoCount = null;
		SessionUser sesseionUser;
		
		sesseionUser = uamSessionService.getSessionUser();
		List<Org> orgList = new ArrayList<>();
		String userOrgId = sesseionUser.getServiceDepId();
		if (TransJobs.TZJ.getCode().equals(sesseionUser.getServiceJobCode())) {
			try {
				uamSessionService.getSessionUserById(userId);
				Org userOrg = new Org();
				userOrg.setId(userOrgId);
				orgList.add(userOrg);
			} catch (Exception e) {
				Org orgGroup = uamUserOrgService.getOrgById(userId);
				orgList.add(orgGroup);
			}
		} else if (TransJobs.TJYZG.getCode().equals(sesseionUser.getServiceJobCode())) {
			Org team = this.uamUserOrgService.getOrgById(userOrgId);
			if (team != null) {
				orgList.add(team);
			}
		}

		toCase.setTime(createTime);

		if (TransJobs.TZJL.getCode().equals(sesseionUser.getServiceJobCode())) {// 总部
			orgList = uamUserOrgService.getOrgByParentId(userOrgId);
			for (Org org : orgList) {
				toCaseInfoCount = getToCaseInfoCountVoByOrgId(org.getId(), createTime);
				jds += toCaseInfoCount.getCountJDS();
				qys += toCaseInfoCount.getCountQYS();
				ghs += toCaseInfoCount.getCountGHS();
				jas += toCaseInfoCount.getCountJAS();
			}
			toCaseInfoCount.setCountJDS(jds);
			toCaseInfoCount.setCountQYS(qys);
			toCaseInfoCount.setCountGHS(ghs);
			toCaseInfoCount.setCountJAS(jas);

		} else if (TransJobs.TZJ.getCode().equals(sesseionUser.getServiceJobCode())) {// 区域
			try {
				uamSessionService.getSessionUserById(userId);
				for (Org org : orgList) {
					toCaseInfoCount = getToCaseInfoCountVoByOrgId(org.getId(), createTime);
					jds += toCaseInfoCount.getCountJDS();
					qys += toCaseInfoCount.getCountQYS();
					ghs += toCaseInfoCount.getCountGHS();
					jas += toCaseInfoCount.getCountJAS();
				}
			} catch (Exception e) {
				for (Org org : orgList) {
					toCaseInfoCount = getToCaseInfoCountByOrgId(org.getId(), createTime);
					jds += toCaseInfoCount.getCountJDS();
					qys += toCaseInfoCount.getCountQYS();
					ghs += toCaseInfoCount.getCountGHS();
					jas += toCaseInfoCount.getCountJAS();
				}
			}
			toCaseInfoCount.setCountJDS(jds);
			toCaseInfoCount.setCountQYS(qys);
			toCaseInfoCount.setCountGHS(ghs);
			toCaseInfoCount.setCountJAS(jas);

		} else if (TransJobs.TJYZG.getCode().equals(sesseionUser.getServiceJobCode())) {// 组别
			if (orgList.size() > 0) {
				for (Org org : orgList) {

					toCaseInfoCount = getToCaseInfoCountByOrgId(org.getId(), createTime);

					jds += toCaseInfoCount.getCountJDS();
					qys += toCaseInfoCount.getCountQYS();
					ghs += toCaseInfoCount.getCountGHS();
					jas += toCaseInfoCount.getCountJAS();
				}
			}
			toCaseInfoCount.setCountJDS(jds);
			toCaseInfoCount.setCountQYS(qys);
			toCaseInfoCount.setCountGHS(ghs);
			toCaseInfoCount.setCountJAS(jas);

		}
		try {
			SessionUser sessionU = uamSessionService.getSessionUserById(userId);
			if (sessionU.getServiceJobCode().equals(TransJobs.TJYGW.getCode())) {

				toCase.setLeadingProcessId(userId);
				// 接单数
				toCaseInfoCount = toCaseService.queryConutCase(toCase);
				// ,签约
				toSignCount = toSpvService.queryCountToSignById(toCase);
				// ,过户
				toHouseTransferCount = toHouseTransferService.queryCountToHouseTransferById(toCase);
				// ,结案
				toCloseCount = toCloseService.queryCountToCloseById(toCase);

				toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS() + toCloseCount.getCountJAS());
				toCaseInfoCount.setCountQYS(toSignCount.getCountQYS());
				toCaseInfoCount.setCountGHS(toHouseTransferCount.getCountGHS());
				toCaseInfoCount.setCountJAS(toCloseCount.getCountJAS());

			}
		} catch (Exception e) {
			return toCaseInfoCount;
		}

		return toCaseInfoCount;
	}

	/**
	 * 变更交易计划保存
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePlanItems")
	@ResponseBody
	public AjaxResponse<?> savePlanItems(String[] isChanges, String[] estIds, String[] estDates, String[] whyChanges,
			HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SessionUser user = uamSessionService.getSessionUser();
		TtsTransPlanHistoryBatch ttpb = new TtsTransPlanHistoryBatch();
		boolean isDeal = true;//是否处理
		for (int i = 0; i < isChanges.length; i++) {
			if ("true".equals(isChanges[i])) {
				ToTransPlan oldPlan = transplanServiceFacade.selectByPrimaryKey(Long.parseLong(estIds[i]));
				if (oldPlan == null || oldPlan.getPkid() == null)
					{
						return AjaxResponse.fail("未找到交易计划！");
					}
				TsTransPlanHistory hisRecord = new TsTransPlanHistory();
				ToTransPlan record = new ToTransPlan();
				try {
					//添加一条交易计划变更历史批次信息
					if(isDeal){
						ttpb.setCaseCode(oldPlan.getCaseCode());
						ttpb.setOldEstPartTime(oldPlan.getEstPartTime());
						ttpb.setNewEstPartTime(format.parse(estDates[i]));
						ttpb.setChangeReason(whyChanges[i]);
						ttpb.setPartCode(oldPlan.getPartCode());
						ttpb.setOperateFlag("0");//手工
						transplanServiceFacade.insertTtsTransPlanHistoryBatch(ttpb);
						isDeal = false;
					}
					hisRecord.setBatchId(ttpb.getPkid());
					// 插入历史记录
					hisRecord.setCaseCode(oldPlan.getCaseCode());
					hisRecord.setPartCode(oldPlan.getPartCode());
					hisRecord.setOldEstPartTime(oldPlan.getEstPartTime());
					hisRecord.setNewEstPartTime(format.parse(estDates[i]));
					hisRecord.setChangeTime(new Date());
					hisRecord.setChangerId(user.getId());
					hisRecord.setChangeReason(whyChanges[i]);

					record.setPkid(Long.parseLong(estIds[i]));
					record.setEstPartTime(format.parse(estDates[i]));
				} catch (ParseException e) {
					return AjaxResponse.fail("数据转换失败！");
				}
				int reInt1 = transplanServiceFacade.insertTsTransPlanHistorySelective(hisRecord);
				if (reInt1 == 0) {
					return AjaxResponse.fail("交易计划历史记录更新失败！");
				}
				int reInt = transplanServiceFacade.updateByPrimaryKeySelective(record);
				if (reInt == 0) {
					return AjaxResponse.fail("交易计划更新失败！");
				}
			}
		}

		return AjaxResponse.success("变更成功！");
	}

	/**
	 * 变更交易计划提交，启动变更审批流程，设置任务办理人为自己的上司，在变更历史表中和变更历史批次表中插入记录
	 * @author wbzhouht
	 * */
	@RequestMapping(value = "/startTransPlan")
	@ResponseBody
	public AjaxResponse<?> startTransPlan(String[] isChanges, String[] estIds, String[] estDates, String[] whyChanges,String partCode,String caseCode,
										  HttpServletRequest request){
		if(isChanges!=null&&isChanges.length>0){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		SessionUser sessionUser=uamSessionService.getSessionUser();
		//变更历史批次表
		TtsTransPlanHistoryBatch ttpb=new TtsTransPlanHistoryBatch();
		//是否处理
		boolean isDeal=true;
		ToTransPlan toTransPlan=new ToTransPlan();
		//启动交易变更审核流程
		Map<String,Object> vars=new HashMap<>();
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		toCaseParticipant.setCaseCode(caseCode);
		toCaseParticipant.setPosition("warrant");//交易计划变更只能是过户权证操作
		toCaseParticipant.setUserName(sessionUser.getUsername());
		//获取当前登录人的上级领导
		String userName=auditCaseService.getLeaderUserName(toCaseParticipant);
		if(userName!=null){
		vars.put("manager",userName);
		//获取流程实例id
		String processId=propertyUtilsService.getProcessDfId("TransPlanAppver");
		StartProcessInstanceVo processInstanceVo=processInstanceService.startWorkFlowByDfId(processId,caseCode,vars);
		/**插入工作流记录表*/
		ToWorkFlow wf = new ToWorkFlow();
		int n=0;
		if(processInstanceVo!=null) {
			wf.setBusinessKey("TransPlanAppver");
			wf.setCaseCode(caseCode);
			wf.setBizCode(caseCode);
			wf.setProcessOwner(sessionUser.getId());
			wf.setProcessDefinitionId(processInstanceVo.getProcessDefinitionId());
			wf.setInstCode(processInstanceVo.getId());
			wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
			n= toWorkFlowService.insertSelective(wf);
		}
		if(n>0){
			for (int i=0;i<isChanges.length;i++){
				//根据Pkid查询交易计划信息
				if("true".equals(isChanges[i])) {
					toTransPlan= transplanServiceFacade.selectByPrimaryKey(Long.parseLong(estIds[i]));
					if (toTransPlan==null||toTransPlan.getPkid()==null){
						return AjaxResponse.fail("未找到交易计划！");
					}
					//变更历史表
					TsTransPlanHistory tsTransPlanHistory=new TsTransPlanHistory();

					try {
						if(isDeal){
							//插入历史变更批次
							ttpb.setCaseCode(toTransPlan.getCaseCode());
							ttpb.setOldEstPartTime(toTransPlan.getEstPartTime());
							ttpb.setNewEstPartTime(format.parse(estDates[i]));
							ttpb.setChangeReason(whyChanges[i]);
							ttpb.setPartCode(toTransPlan.getPartCode());
							//手工
							ttpb.setOperateFlag("0");
							int res=transplanServiceFacade.insertTtsTransPlanHistoryBatch(ttpb);
							if(res==0){
								return AjaxResponse.fail("交易计划历史变更批次失败！");
							}
							isDeal = false;
						}
						tsTransPlanHistory.setBatchId(ttpb.getPkid());
						// 插入历史记录
						tsTransPlanHistory.setCaseCode(toTransPlan.getCaseCode());
						tsTransPlanHistory.setPartCode(toTransPlan.getPartCode());
						tsTransPlanHistory.setOldEstPartTime(toTransPlan.getEstPartTime());
						tsTransPlanHistory.setNewEstPartTime(format.parse(estDates[i]));
						tsTransPlanHistory.setChangeTime(new Date());
						tsTransPlanHistory.setChangerId(sessionUser.getId());
						tsTransPlanHistory.setChangeReason(whyChanges[i]);
						tsTransPlanHistory.setAppverPartCode(partCode);
						tsTransPlanHistory.setAuditResult(ToApproveRecordEnum.DEFUALT.getCode());//默认为待审核状态
						int res1=transplanServiceFacade.insertTsTransPlanHistorySelective(tsTransPlanHistory);
						if(res1==0){
							return AjaxResponse.fail("交易计划历史变更失败！");
						}
					}catch (ParseException e){
						return AjaxResponse.fail("数据转换失败！");
					}
				}
			}
			return AjaxResponse.success("操作成功");
		}else {
			return AjaxResponse.fail("提交失败！");
		}
		}else {
			return AjaxResponse.fail("您不是该案件的过户权证！");
		}
		}else {
			return AjaxResponse.fail("交易计划未做变更！");
		}
	}

	/**
	 * 案件挂起
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/casePause")
	@ResponseBody
	public AjaxResponse<String> casePause(String caseCode, HttpServletRequest request) {
		// 案件状态查询
		AjaxResponse<String> response = new AjaxResponse<String>();
		boolean isSus = false;
		ToCase record = new ToCase();
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		record.setCaseCode(caseCode);
		//在途—>挂起
		if (CasePropertyEnum.TPZT.getCode().equals(toCase.getCaseProperty())) {
			record.setCaseProperty(CasePropertyEnum.TPGQ.getCode());
		}else if (CasePropertyEnum.TPGQ.getCode().equals(toCase.getCaseProperty())) {//挂起->恢复在途
			record.setCaseProperty(CasePropertyEnum.TPZT.getCode());
			isSus = true;
		}
		//获取案件所有进行中流程任务
		List<TaskVo> taskVos = actRuTaskService.getRuTask(caseCode);
		Map<String,Boolean> taskInstCode = new HashMap<>();
		
		/**
		 *  检查是否有流程重启任务或报单流程及获取所有任务暂停状态 
		 */
		for(TaskVo task : taskVos){
			if(WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
				response.setMessage("该案件有流程重启任务，等待主管审批");
				response.setSuccess(false);
				return response;
			}if(WorkFlowEnum.CASE_STOP_PROCESS.getCode().equals(task.getBusiness_key())){
				response.setMessage("该案件有爆单任务，等待主管审批");
				response.setSuccess(false);
				return response;
			}else{
				//false:未暂停,true:暂停
				taskInstCode.put(task.getInstCode(), task.getSuspended());
			}
		}
		
		for (String instCode : taskInstCode.keySet()) {
			if(!com.alibaba.druid.util.StringUtils.isEmpty(instCode)){
				//恢复在途状态
				if(isSus){
					//如果是挂起状态  true,->恢复在途
					if(taskInstCode.get(instCode)){
						workFlowManager.activateOrSuspendProcessInstance(instCode, true);
					}
				}else{
					//如果是正常在途状态  false,->挂起状态
					if(!taskInstCode.get(instCode)){
						workFlowManager.activateOrSuspendProcessInstance(instCode, false);
					}
				}
			}
		}
		
		// 案件表更新
		toCaseService.updateByCaseCodeSelective(record);
		response.setContent(record.getCaseProperty());
		response.setMessage("变更成功！");
		response.setSuccess(true);
		return response;
	}

	// 跟进orgId查询统计数据
	private ToCaseInfoCountVo getToCaseInfoCountByOrgId(String orgId, String time) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setTime(time);
		// 接单数
		ToCaseInfoCountVo toCaseInfoCount = toCaseService.queryConutCaseByOrg(toCase);
		// ,签约
		ToCaseInfoCountVo toSignCount = toSpvService.queryCountToSignByOrg(toCase);
		// ,过户
		ToCaseInfoCountVo toHouseTransferCount = toHouseTransferService.queryCountToHouseTransferByOrg(toCase);
		// ,结案
		ToCaseInfoCountVo toCloseCount = toCloseService.queryCountToCloseByOrg(toCase);

		toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS() + toCloseCount.getCountJAS());
		toCaseInfoCount.setCountQYS(toSignCount.getCountQYS());
		toCaseInfoCount.setCountGHS(toHouseTransferCount.getCountGHS());
		toCaseInfoCount.setCountJAS(toCloseCount.getCountJAS());

		return toCaseInfoCount;
	}

	private ToCaseInfoCountVo getToCaseInfoCountVoByOrgId(String orgId, String createTime) {
		int countJDS = toCaseInfoService.initCountToCaseInfoByOrgId(orgId, createTime);
		int countQYS = toSpvService.initCountToSignByOrgId(orgId, createTime);
		int countGHS = toHouseTransferService.initCountToHouseTransferByOrgId(orgId, createTime);
		int countJAS = toCloseService.initCountToCloseByOrgId(orgId, createTime);
		ToCaseInfoCountVo toCaseInfoCountVo = new ToCaseInfoCountVo();
		toCaseInfoCountVo.setCountJDS(countJDS + countJAS);
		toCaseInfoCountVo.setCountQYS(countQYS);
		toCaseInfoCountVo.setCountGHS(countGHS);
		toCaseInfoCountVo.setCountJAS(countJAS);

		return toCaseInfoCountVo;
	}
	/**
	 * 变更交易计划
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeTaskAssignee")
	@ResponseBody
	public AjaxResponse<?> changeTaskAssignee(ChangeTaskAssigneeVO vo) {
		List<Integer> tasks=vo.getTaskIds();
		List<String> caseCode=vo.getCaseCodes();
		
		for (int i = 0; i < tasks.size(); i++) {
			toCaseService.changeTaskAssignee(caseCode.get(i), tasks.get(i)+"", vo.getUserId());
		}	
		return AjaxResponse.success("变更成功！");
	}
	
	
	/**
	 * 获取同组织下的所有交易助理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getAssistantInfo")
	@ResponseBody
	public Map<String, Object> getAssistantInfo(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		SessionUser us = uamSessionService.getSessionUser();
		String orgId = us.getServiceDepId();
		
		List<CaseAssistantVO> userList = toCaseInfoService.getAssistantInfo(orgId);
		if (userList != null && userList.size() > 0) {
			result.put("users", userList);
		}
		return result;
	}
	/**
	 * 变更交易助理
	 * @param tocase
	 * @return
	 */
	@RequestMapping(value="/changeAssistant")
	@ResponseBody
	public AjaxResponse<?> changeAssitant(ToCase tocase){
		int num = toCaseService.updateAssistant(tocase);
		if(num == 0){
			return AjaxResponse.fail("变更失败");
		}else{
			return AjaxResponse.success("变更成功");
		}
	}
	
	/**
	 * 检查案件是否已存在询价单
	 * @param caseCode
	 * @return
	 * @author wbcaiyx
	 */
	@RequestMapping(value="/checkEvaPricing")
	@ResponseBody
	public AjaxResponse<Boolean> checkEvaPricing(String caseCode){
		AjaxResponse<Boolean> result = new AjaxResponse<Boolean>();
		//检查案件 是否已存在状态非取消的询价申请单 
		boolean isExist = evaPricingService.queryInfoByCase(caseCode);
		result.setContent(isExist);
		return result;
	}
	
	/**
	 * 评估申请检查
	 * @param caseCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkEvalProcess")
	@ResponseBody
	public AjaxResponse<Integer> checkEvalProcess(String caseCode){
		if(com.alibaba.druid.util.StringUtils.isEmpty(caseCode)){
			return AjaxResponse.fail("无案件编号");
		}
		return evaPricingService.checkEvalProcess(caseCode);
	}
	
	/**
	 * 业绩记录/收费情况CCAI查询
	 * @param ccaiCode
	 * @return
	 * @author wbcaiyx
	 */
	@RequestMapping(value="/getPricingAndFee")
	@ResponseBody
	public AjaxResponse<ApiCaseInfo> getPricingAndFee(String ccaiCode){
		AjaxResponse<ApiCaseInfo> result = new AjaxResponse<ApiCaseInfo>();
		if(ccaiCode == null){
			return result;
		}
		ApiCaseInfo info = caseApiService.getApiCaseInfo(ccaiCode);
		result.setContent(info);
		return result; 
	}
	
	/**
	 * 获取案件公共部分
	 * @param request
	 * @param caseCode
	 */
	@RequestMapping(value="getCaseBaseInfo")
	@ResponseBody
	public CaseDetailShowVO getCaseBaseInfo(HttpServletRequest request, String caseCode){
		CaseDetailShowVO reVo = new CaseDetailShowVO();
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		reVo.setToCase(toCase);
		
		/** 爆单和无效原因 **/
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();	
		if("30003001".equals(toCase.getCaseProperty()) || "30003005".equals(toCase.getCaseProperty())){
        	ToWorkFlow workFlow = new ToWorkFlow();
        	workFlow.setCaseCode(toCase.getCaseCode());
        	workFlow.setBusinessKey("operation_process");
        	workFlow.setStatus("4");
        	ToWorkFlow toWorkFlow1= toWorkFlowService.queryToWorkFlowByCaseCodeAndBusinessKey(workFlow);				
    		if(toWorkFlow1!=null){
	        	toApproveRecordForItem.setProcessInstance(toWorkFlow1.getInstCode());
	    		toApproveRecordForItem.setCaseCode(toCase.getCaseCode());
				ToApproveRecord toApproveRecord2 = toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);
				if(toApproveRecord2 != null){
					reVo.setToApproveRecord(toApproveRecord2.getContent());
				}
	    	}
        }


		boolean isSubscribe = toModuleSubscribeService.checkIsSubscribe(toCase.getCaseCode(), uamSessionService.getSessionUser().getId(), SubscribeModuleType.CASE.getValue(),SubscribeType.COLLECTION.getValue());
		reVo.setSubscribe(isSubscribe);
		//物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		String propettyName = uamBasedataService.getDictValue("30014", toPropertyInfo.getPropertyType());
		toPropertyInfo.setPropertyTypeName(propettyName);
		reVo.setToPropertyInfo(toPropertyInfo);
		
		/** 买卖家 **/
		List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		for (TgGuestInfo guest : guestList) {
			if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
				reVo.setSellerName(guest.getGuestName());
				reVo.setSellerMobile(guest.getGuestPhone());
			} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
				reVo.setBuyerName(guest.getGuestName());
				reVo.setBuyerMobile(guest.getGuestPhone());
			}
		}
		
		ToCaseParticipant vo = new ToCaseParticipant();
		vo.setCaseCode(caseCode);
		//案件参与人信息
		List<ToCaseParticipant> caseParticipants = toCaseParticipantService.findToCaseParticipantByCondition(vo);
		
		for(ToCaseParticipant part :caseParticipants){
			/** 经纪人/经理 **/
			if(CaseParticipantEnum.AGENT.getCode().equals(part.getPosition())){
				reVo.setAgentName(part.getRealName());
				reVo.setAgentMobile(part.getMobile());
				reVo.setAgentGrpName(part.getGrpName());
				reVo.setMcName(part.getGrpMgrRealname());
				reVo.setMcMobile(part.getGrpMgrMobile());
			}
			/** 权证 **/
			else if(CaseParticipantEnum.WARRANT.getCode().equals(part.getPosition())){
				reVo.setWarName(part.getRealName());
				reVo.setWarMobile(part.getMobile());
			}else if(CaseParticipantEnum.LOAN.getCode().equals(part.getPosition())){
				reVo.setLoanName(part.getRealName());
				reVo.setLoanMobile(part.getMobile());
			}
			/** 秘书 **/
			else if(CaseParticipantEnum.SECRETARY.getCode().equals(part.getPosition())){
				reVo.setMsName(part.getRealName());
				reVo.setMsMobile(part.getMobile());
			}
			/** 内勤 **/
			else if(CaseParticipantEnum.ASSISTANT.getCode().equals(part.getPosition())){
				reVo.setAsName(part.getRealName());
				reVo.setAsMobile(part.getMobile());
			}
		}
		
		
		return reVo;
	}
}
