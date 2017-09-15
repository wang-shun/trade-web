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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.cases.service.VCaseTradeInfoService;
import com.centaline.trans.cases.vo.CaseAssistantVO;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.cases.vo.ChangeTaskAssigneeVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToServChangeHistroty;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.common.enums.SubscribeModuleType;
import com.centaline.trans.common.enums.SubscribeType;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
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
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.entity.ToPropertyResearchVo;
import com.centaline.trans.task.service.ActRuTaskService;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.UnlocatedTaskService;
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
	@Autowired
	private ProcessInstanceService processInstanceService;

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
	ToCaseParticipantMapper toCaseParticipantMapper;

	@Autowired
	private ActRuTaskService actRuTaskService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;

	/**
	 * 页面初始化
	 * 
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
		} // 过户时间
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
	public String caseDetail(Long caseId,ServletRequest request) {
		if (caseId == null){
			return "case/caseList";
		}
		
		/** 基本信息 **/ 
		CaseDetailShowVO reVo = new CaseDetailShowVO();
		ToCase toCase = toCaseService.selectByPrimaryKey(caseId);
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());
		// add zhangxb16 2016-2-22 功能
		ToCase te = toCaseService.findToCaseByCaseCode(toCase.getCaseCode());
		if (null != te) {
			reVo.setCaseProperty(te.getCaseProperty());
		}
		
		SessionUser sessionUser = uamSessionService.getSessionUser();
		//是否关注
		boolean isSubscribe = toModuleSubscribeService.checkIsSubscribe(toCase.getCaseCode(), uamSessionService.getSessionUser().getId(), SubscribeModuleType.CASE.getValue(),SubscribeType.COLLECTION.getValue());
		
		/**物业信息**/ 
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		
		/** 经纪人 **/ 		
		User agentUser = null;
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
			List<User> mcList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(agentUser.getOrgId(),TransJobs.TFHJL.getCode());
			if (mcList != null && mcList.size() > 0) {
				User mcUser = mcList.get(0);
				reVo.setMcId(mcUser.getId());
				reVo.setMcName(mcUser.getRealName());
				reVo.setMcMobile(mcUser.getMobile());
			}
			//分行秘书
			List<User> msList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(agentUser.getOrgId(), TransJobs.TFHMS.getCode());
			if(msList !=null && msList.size()>0){
				User msUser = msList.get(0);
				reVo.setMsId(msUser.getId());
				reVo.setMsName(msUser.getRealName());
				reVo.setMsMobile(msUser.getMobile());
			}
		}
		
		/** 权证 **/
		List<ToCaseParticipant> caseParticipants = toCaseParticipantMapper.selectByCaseCode(toCase.getCaseCode());
		for(ToCaseParticipant pant :caseParticipants){
			if("warrant".equals(pant.getPosition())){
				reVo.setWarName(pant.getRealName());
				reVo.setWarMobile(pant.getMobile());
			}else if("loan".equals(pant.getPosition())){
				reVo.setLoanName(pant.getRealName());
				reVo.setLoanMobile(pant.getMobile());
			}
		}
		// 助理
		User assistant = uamUserOrgService.getUserById(toCase.getAssistantId());
		if(assistant != null){
			reVo.setAsId(assistant.getId());
			reVo.setAsName(assistant.getRealName());
			reVo.setAsMobile(assistant.getMobile());
		}
		/** 内勤 **/ 
		/*List<User> nqList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(toCase.getOrgId(), TransJobs.TNQZL.getCode());
		if(nqList != null && nqList.size() > 0){
			User nqUser = nqList.get(0);
			reVo.setAsId(nqUser.getId());
			reVo.setAsName(nqUser.getRealName());
			reVo.setAsMobile(nqUser.getMobile());
		}*/
		
		/** 买卖家 **/
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
			

		/** 工作流 **/ 
		ToWorkFlow inWorkFlow = new ToWorkFlow();
		inWorkFlow.setBusinessKey("operation_process");
		inWorkFlow.setCaseCode(toCase.getCaseCode());
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
		
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
					request.setAttribute("toApproveRecord", toApproveRecord2.getContent());
				}else{
					request.setAttribute("toApproveRecord","");
				}
	    	}else{
	    		request.setAttribute("toApproveRecord","");
	    	}
        }else{
        	request.setAttribute("toApproveRecord","");
        	
        }
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		/** 交易信息 **/
		VCaseTradeInfo caseInfo = vCaseTradeInfoService.queryCaseTradeInfoByCaseCode(toCase.getCaseCode());

		// 业务单创建时间
		if (caseInfo.getCreateTime() != null) {
			String createTime = format.format(caseInfo.getCreateTime());
			reVo.setCreateTime(createTime);
		}
		// 接单时间
		if (caseInfo.getResDate() != null) {
			String resDate = format.format(caseInfo.getResDate());
			reVo.setResDate(resDate);
		} 
		// 房屋性质
		if (caseInfo.getHouseProperty() != null) {
			String houseProperty = uamBasedataService.getDictValue(TransDictEnum.TFWXZ.getCode(),
					caseInfo.getHouseProperty());
			reVo.setHouseProperty(houseProperty);
		}
		// 购房年数
		if (caseInfo.getHoldYear() != null) {
			String holdYear = uamBasedataService.getDictValue(TransDictEnum.TGFNS.getCode(), caseInfo.getHoldYear());
			reVo.setHoldYear(holdYear);
		}
		// 唯一住房
		if (caseInfo.getIsUniqueHome() != null) {
			String isUniqueHome = uamBasedataService.getDictValue(TransDictEnum.TWYZF.getCode(),
					caseInfo.getIsUniqueHome());
			reVo.setIsUniqueHome(isUniqueHome);
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
		// 审税时间
		if (caseInfo.getTaxTime() != null) {
			String taxTime = format.format(caseInfo.getTaxTime());
			reVo.setTaxTime(taxTime);
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
		// 还款方式
		if (caseInfo.getCloseType() != null) {
			String closeType = uamBasedataService.getDictValue(TransDictEnum.THKFS.getCode(), caseInfo.getCloseType());
			reVo.setCloseType(closeType);
		}
		// 还款时间
		if (caseInfo.getLoanCloseCode() != null) {
			String loanCloseCode = format.format(caseInfo.getLoanCloseCode());
			reVo.setLoanCloseCode(loanCloseCode);
		}   
		// 结案时间
		if (caseInfo.getCloseTime() != null) {
			String closeTime = format.format(caseInfo.getCloseTime());
			reVo.setCloseTime(closeTime);
		}
		
		/** 贷款信息 **/
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
			
			// 网签时间
			if (toMortgage.getSignDate() != null) {
				String signDate = format.format(toMortgage.getSignDate());
				reVo.setSignDate(signDate);
			}
			// 批贷时间
			if (toMortgage.getApprDate() != null) {
				String apprDate = format.format(toMortgage.getApprDate());
				reVo.setApprDate(apprDate);
			}
			// 主贷人
			if (null != toMortgage.getCustCode()) {
				TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
				if (null != guest) {
					reVo.setBuyerWork(guest.getWorkUnit());
					reVo.setMortBuyer(guest.getGuestName());
				}
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
		}

		// 金融服务信息
		/*List<LoanAgent> toLoanAgents = toLoanAgentService.selectByCaseCode(toCase.getCaseCode());
		List<ToLoanAgentVO> toLoanAgentVOs = new ArrayList<ToLoanAgentVO>();
		List<ToLoanAgentVO> toEloanCaseVOs = new ArrayList<ToLoanAgentVO>();
		// E+金融
		ToEloanCase eloanCase = new ToEloanCase();
		eloanCase.setCaseCode(toCase.getCaseCode());
		List<ToEloanCase> toEloanCases = toEloanCaseService.getToEloanCaseListByProperty(eloanCase);
		if (toEloanCases.size() > 0) {
			
			for (ToEloanCase toEloanCase : toEloanCases) {
				ToLoanAgentVO toEloanCaseVO = new ToLoanAgentVO();
				// 贷款服务编码
				if (!StringUtils.isEmpty(toEloanCase.getLoanSrvCode())) {
					String loanSrvName = uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(),
							toEloanCase.getLoanSrvCode());
					toEloanCaseVO.setLoanSrvName(loanSrvName);
				}
				// 贷款机构
				if (toEloanCase.getFinOrgCode() != null) {
					TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(toEloanCase.getFinOrgCode());
					if (tsFinOrg != null && !StringUtils.isEmpty(tsFinOrg.getFinOrgName())) {
						toEloanCaseVO.setFinOrgName(tsFinOrg.getFinOrgName());
					}
				}
				// 申请时间
				if (toEloanCase.getApplyTime() != null) {
					String applyTime = format.format(toEloanCase.getApplyTime());
					toEloanCaseVO.setApplyTime(applyTime);
				}
				// 确认时间
				if (toEloanCase.getApplyConfTime() != null) {
					String formatTime = format.format(toEloanCase.getApplyConfTime());
					toEloanCaseVO.setConfirmTime(formatTime);
				}
				// 面签时间
				if (toEloanCase.getSignTime() != null) {
					String formatTime = format.format(toEloanCase.getSignTime());
					toEloanCaseVO.setSignTime(formatTime);
				}
				// 申请状态
				if (toEloanCase.getApplyConfTime()!= null) {
					toEloanCaseVO.setApplyStatusName("已确认");
				} else {
					toEloanCaseVO.setApplyStatusName("待确认");
				}
				if(!StringUtils.isBlank(toEloanCase.getLoanerName())){
					toEloanCaseVO.setLoanerName(toEloanCase.getLoanerName());
				}
				if(!StringUtils.isBlank(toEloanCase.getLoanerPhone())){
					toEloanCaseVO.setLoanerName(toEloanCase.getLoanerPhone());
				}
				// 放款时间
				List<ToEloanRel> eloanRels = toEloanRelService.getEloanRelByEloanCode(toEloanCase.getEloanCode());
				// 确认状态
				if (toEloanCase.getApplyTime()!=null) {
					toEloanCaseVO.setConfirmStatusName("申请");
				} if(toEloanCase.getSignTime()!=null){
					toEloanCaseVO.setConfirmStatusName("面签");
				}if(eloanRels.size()>0){
					toEloanCaseVO.setConfirmStatusName("放款");
				}
				//放款金额
				BigDecimal releaseAmount=new BigDecimal(0);
				for (ToEloanRel eloanRel : eloanRels) {
					if (eloanRel.getReleaseTime() != null) {
						String formatTime = format.format(eloanRel.getReleaseTime());
						toEloanCaseVO.setReleaseTime(formatTime);
					}
					if(eloanRel.getConfirmStatus().equals("1")){
						releaseAmount=releaseAmount.add(eloanRel.getReleaseAmount());
						toEloanCaseVO.setReleaseAmount(releaseAmount.toString());
					}
				}
				toEloanCaseVOs.add(toEloanCaseVO);
			}

		}
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
		}*/
	
//		TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(sessionUser.getServiceDepCode());
//		boolean isBackTeam = false;
		boolean isCaseOwner=false;
		boolean isNewFlow=false;
		boolean isCaseManager=false;
		//查询登录人的工作组，前台or后台
//		if (tp != null) {
//			isBackTeam = "yu_back".equals(tp.getTeamProperty());
//		}
		//判断是否为自己的案件
		if(sessionUser.getId().equals(toCase.getLeadingProcessId())){
			isCaseOwner=true;
		}
		
		if(toWorkFlow!=null &&"operation_process:34:620096".compareTo(toWorkFlow.getProcessDefinitionId())<=0){
			isNewFlow=true;
		}
	
		if(isCaseOwner&&TransJobs.TJYZG.getCode().equals(sessionUser.getServiceJobCode())){
			isCaseManager=true;
		}
		
		String jobId = uamSessionService.getSessionUser().getServiceJobId();
		List<Resource> resourcelist = uamPermissionService.getResourceByJobId(jobId);
		request.setAttribute("resourcelist", resourcelist);


		/** 案件重启中对信息管理员开放 ff80808158af2e600158b98c82340049 ***/
//		if(null != sessionUser){
//			if(StringUtils.equals(sessionUser.getServiceJobCode(), "COXXGLY")){
//				request.setAttribute("serviceJobType", "Y");
//			}else{
//				request.setAttribute("serviceJobType", "N");
//			}
//		}

		request.setAttribute("isCaseManager", isCaseManager);
		request.setAttribute("serivceDefId", sessionUser.getServiceDepId());
		request.setAttribute("loanReqType", loanReqType);
		request.setAttribute("isNewFlow", isNewFlow);
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		
//		request.setAttribute("isBackTeam", isBackTeam);
		request.setAttribute("isCaseOwner", isCaseOwner);
		request.setAttribute("toCase", toCase);
		request.setAttribute("toCaseInfo", toCaseInfo);
		request.setAttribute("toPropertyInfo", toPropertyInfo);
		request.setAttribute("toWorkFlow", toWorkFlow);
		request.setAttribute("toMortgage", toMortgage);
		request.setAttribute("caseDetailVO", reVo);
		request.setAttribute("caseInfo", caseInfo);
		//收费情况，接口提出
		JSONObject chargeInfo = new JSONObject();
		request.setAttribute("chargeInfo", chargeInfo);

		
/*		request.setAttribute("toSpv", toSpv);
		request.setAttribute("cashFlows", cashFlows);*/
		/*request.setAttribute("toLoanAgents", toLoanAgents);
		request.setAttribute("toEloanCases", toEloanCases);
		request.setAttribute("toLoanAgentVOs", toLoanAgentVOs);
		request.setAttribute("toEloanCaseVOs", toEloanCaseVOs);
		request.setAttribute("toEloanCaseVOs", toEloanCaseVOs);cyx*/
		request.setAttribute("isSubscribe", isSubscribe);
		return "case/caseDetail_new";
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
	 * 
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getTransPlanByCaseCode")
	@ResponseBody
	public List<ToTransPlan> getTransPlanByCaseCode(String caseCode, ServletRequest request) {
		List<ToTransPlan> plans = transplanServiceFacade.queryPlansByCaseCode(caseCode);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		if (plans != null && plans.size() > 0) {
			for (ToTransPlan plan : plans) {
				if (!StringUtils.isEmpty(plan.getPartCode())) {
					String partNameString = uamBasedataService.getDictValue(TransDictEnum.TPARTCODE.getCode(),
							plan.getPartCode());
					plan.setPartName(partNameString);
				}
				if (plan.getEstPartTime() != null)
					plan.setEstPartTimeStr(format.format(plan.getEstPartTime()));
			}
		}

		return plans;
	}

	/**
	 * 变更责任人
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/changeLeadingUser")
	@ResponseBody
	public AjaxResponse<?> changeLeadingUser(String instCode, String caseCode, String userId,
			HttpServletRequest request) {
		// 案件信息更新
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		String origUserId = toCase.getLeadingProcessId();
		User u = uamUserOrgService.getUserById(origUserId);
		User u_ = uamUserOrgService.getUserById(userId);

		TgServItemAndProcessor record = new TgServItemAndProcessor();
		record.setPreProcessorId(toCase.getLeadingProcessId());
		toCase.setLeadingProcessId(userId);
		int reToCase = toCaseService.updateByPrimaryKey(toCase);
		
		
		record.setProcessorId(userId);
		record.setOrgId(u_.getOrgId());
		record.setCaseCode(caseCode);
		record.setPreProcessorId(origUserId);
		record.setPreOrgId(u.getOrgId());
		
		tgServItemAndProcessorService.updateByCaseCode(record);

		// 更新流程引擎  变更流程的责任人，待办里面也是根据案件的执行人来查询的
		if (!StringUtils.isBlank(instCode)) {
			String variableName = "caseOwner";
			RestVariable restVariable = new RestVariable();
			restVariable.setType("string");
			restVariable.setValue(u_.getUsername());
			try{
				workFlowManager.setVariableByProcessInsId(instCode, variableName, restVariable);
			}catch(WorkFlowException e){
				if(404!=e.getStatusCode()){
					throw e;
				}
			}
			TaskQuery tq = new TaskQuery();
			tq.setProcessInstanceId(instCode);
			tq.setFinished(false);
			tq.setAssignee(u.getUsername());
			List<TaskVo> tasks = workFlowManager.listTasks(tq).getData();
			tasks.addAll(getNonMainWorkflowByAssignee(caseCode,u.getUsername()));//商贷、流失、公积金
			updateWorkflow(userId, tasks, caseCode);
		}
		if (reToCase == 0)
			return AjaxResponse.fail("案件基本表更新失败！");
		
		return AjaxResponse.success("变更成功！");
	}
	/**
	 * 查询指定Assignee的非主流程的任务项
	 * @param caseCode
	 * @param assignee
	 * @return
	 */
	private List<TaskVo> getNonMainWorkflowByAssignee(String caseCode,String assignee){
		List<TaskVo> result=new ArrayList<>(1);
		ToWorkFlow wfQueryBean=new ToWorkFlow();
		wfQueryBean.setCaseCode(caseCode);
		wfQueryBean.setBizCodes(Arrays.asList("ComLoan_Process","LoanLost_Process","PSFLoan_Process"));
		List<ToWorkFlow>subWF= toWorkFlowService.queryToWorkFlowByCaseCodeBusKeys(wfQueryBean);
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
	
	
	public void updateWorkflow(String userId, List<TaskVo> tasks,String caseCode) {
		if (tasks != null && !tasks.isEmpty()) {
			User u = uamUserOrgService.getUserById(userId);
			for (TaskVo taskVo : tasks) {
				taskReassingtLogService.record(taskVo, u.getUsername(), caseCode);
				taskVo.setAssignee(u.getUsername());
				workFlowManager.updateTask(taskVo);
			}
		}

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
	private boolean isChanged(List<String> arrs1, String arrs2[]) {
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
			if (isChanges[i].equals("true")) {
				ToTransPlan oldPlan = transplanServiceFacade.selectByPrimaryKey(Long.parseLong(estIds[i]));
				if (oldPlan == null || oldPlan.getPkid() == null)
					return AjaxResponse.fail("未找到交易计划！");
				TsTransPlanHistory hisRecord = new TsTransPlanHistory();
				ToTransPlan record = new ToTransPlan();
				try {
					//add by zhoujp添加一条交易计划变更历史批次信息
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
				if (reInt1 == 0)
					return AjaxResponse.fail("交易计划历史记录更新失败！");
				int reInt = transplanServiceFacade.updateByPrimaryKeySelective(record);
				if (reInt == 0)
					return AjaxResponse.fail("交易计划更新失败！");
			}
		}

		return AjaxResponse.success("变更成功！");
	}

	/**
	 * 案件挂起
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/casePause")
	@ResponseBody
	public AjaxResponse<?> casePause(String caseCode, HttpServletRequest request) {
		// 案件状态查询
		AjaxResponse response = new AjaxResponse();
		boolean isSus = false;
		ToCase record = new ToCase();
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		record.setCaseCode(caseCode);
		if (toCase.getCaseProperty().equals(CasePropertyEnum.TPZT.getCode())) {
			record.setCaseProperty(CasePropertyEnum.TPGQ.getCode());
		} else if (toCase.getCaseProperty().equals(CasePropertyEnum.TPGQ.getCode())) {
			record.setCaseProperty(CasePropertyEnum.TPZT.getCode());
			isSus = true;
		}

		List<TaskVo> taskVos = actRuTaskService.getRuTask(caseCode);
		Map<String,Boolean> taskInstCode = new HashMap<>();
		for(TaskVo task : taskVos){
			if(WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
				response.setMessage("该案件有流程重启任务，等待主管审批");
				response.setSuccess(false);
				return response;
			}
			if(!WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
				taskInstCode.put(task.getInstCode(), task.getSuspended());
			}
		}
		for (String instCode : taskInstCode.keySet()) {
			if(!com.alibaba.druid.util.StringUtils.isEmpty(instCode)){
				if(isSus){//恢复状态
					if(taskInstCode.get(instCode)){//如果是挂起状态  true变成非挂起状态
						workFlowManager.activateOrSuspendProcessInstance(instCode, true);
					}
				}else{
					if(!taskInstCode.get(instCode)){//如果不是挂起状态  false变成挂起状态
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
	
	
	@RequestMapping(value="caseBaodan")
	@ResponseBody
	public AjaxResponse<?> caseBaodan(String caseCode){
		ToWorkFlow record = new ToWorkFlow();
		record.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		record.setCaseCode(caseCode);
		List<ToWorkFlow> wfls = toWorkFlowService.queryActiveToWorkFlowByCaseCode(record);
		//工作流数据更新：爆单
		if(wfls != null && wfls.size() > 0){
			for(ToWorkFlow toWorkFlow : wfls){
				toWorkFlow.setStatus(WorkFlowStatus.BAODAN.getCode());
				toWorkFlowService.updateByPrimaryKey(toWorkFlow);
			}
			for(ToWorkFlow toWorkFlow : wfls){
    			try {
    				unlocatedTaskService.deleteByInstCode(toWorkFlow.getInstCode());
            		workFlowManager.deleteProcess(toWorkFlow.getInstCode());
				} catch (WorkFlowException e) {
					if(!e.getMessage().contains("statusCode[404]"))throw e;
					return AjaxResponse.fail(e.getMessage());
				}
    		}
		}
		/** 案件性质更新 **/
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		toCase.setCaseProperty(CasePropertyEnum.TPBD.getCode());
		int res = toCaseService.updateByPrimaryKey(toCase);
		if(res == 0){
			return AjaxResponse.fail("更新案件失败!");
		}
		
		return AjaxResponse.success();
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
}
