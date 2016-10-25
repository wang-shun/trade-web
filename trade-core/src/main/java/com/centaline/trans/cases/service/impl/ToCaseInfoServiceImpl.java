package com.centaline.trans.cases.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToEvaReportService;

@Service
public class ToCaseInfoServiceImpl implements ToCaseInfoService {

	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private TsFinOrgService tsFinOrgService;
	@Autowired
	private ToEvaReportService toEvaReportService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToEvaFeeRecordService toEvaFeeRecordService;

	/****
	 * 查询案件详情
	 * 
	 * @param caseCode
	 * @return
	 */
	@Override
	public CaseDetailShowVO getCaseDetailShowVO(String caseCode, ToMortgage toMortgage) {
		CaseDetailShowVO reVo = new CaseDetailShowVO();

		ToCaseInfo toCaseInfo = findToCaseInfoByCaseCode(caseCode);
		ToCase te = toCaseService.findToCaseByCaseCode(caseCode);
		if (null != te) {
			reVo.setCaseProperty(te.getCaseProperty());
		}

		// 物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		if (toPropertyInfo != null) {
			reVo.setPropertyAddress(toPropertyInfo.getPropertyAddr());
		}
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
			List<User> mcList = uamUserOrgService.getUserByOrgIdAndJobCode(agentUser.getOrgId(),
					TransJobs.TFHJL.getCode());
			if (mcList != null && mcList.size() > 0) {

				User mcUser = mcList.get(0);
				reVo.setMcId(mcUser.getId());
				reVo.setMcName(mcUser.getRealName());
				reVo.setMcMobile(mcUser.getMobile());
			}
		}

		// 交易顾问
		User consultUser = uamUserOrgService.getUserById(te.getLeadingProcessId());
		if (consultUser != null) {
			reVo.setCpId(consultUser.getId());
			reVo.setCpName(consultUser.getRealName());
			reVo.setCpMobile(consultUser.getMobile());
		}
		// 助理
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(consultUser.getOrgId(),
				TransJobs.TJYZL.getCode());
		if (asList != null && asList.size() > 0) {
			User assistUser = asList.get(0);
			reVo.setAsId(assistUser.getId());
			reVo.setAsName(assistUser.getRealName());
			reVo.setAsMobile(assistUser.getMobile());
		}
		// 贷款流失类型
		String loanLostType = tsFinOrgService.getLoanLostTypeValue(caseCode);
		if (loanLostType != null) {
			reVo.setLoanLostType(loanLostType);
		} else {
			reVo.setLoanLostType("");
		}

		if (toMortgage != null) {
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
			ToEvaReport evaReport = toEvaReportService.findFinalComByCaseCode(caseCode);
			if (evaReport != null && !StringUtils.isEmpty(evaReport.getFinOrgCode())) {
				TsFinOrg reportCom = tsFinOrgService.findBankByFinOrg(evaReport.getFinOrgCode());
				reVo.setEvaName(reportCom.getFinOrgName());
			}
			// 评估费金额
			ToEvaFeeRecord evaFeeReport = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(caseCode);
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
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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

		return reVo;
	}

	@Override
	public int queryCountCasesByUserId(String userId) {
		// TODO Auto-generated method stub
		Integer reInt = toCaseInfoMapper.queryCountCasesByUserId(userId);
		return reInt == null ? 0 : reInt;
	}

	@Override
	public int queryCountMonthCasesByUserId(String userId) {
		// TODO Auto-generated method stub
		Integer reInt = toCaseInfoMapper.queryCountMonthCasesByUserId(userId);
		return reInt == null ? 0 : reInt;
	}

	@Override
	public int queryCountUnTransCasesByUserId(String userId) {
		// TODO Auto-generated method stub
		Integer reInt = toCaseInfoMapper.queryCountUnTransCasesByUserId(userId);
		return reInt == null ? 0 : reInt;
	}

	@Override
	public int updateByPrimaryKey(ToCaseInfo record) {
		// TODO Auto-generated method stub
		return toCaseInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToCaseInfo findToCaseInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
	}

	@Override
	public ToCaseInfoCountVo countToCaseInfoById(String userId) {
		return toCaseInfoMapper.countToCaseInfoById(userId);
	}

	/**
	 * 功能：交易单编号查询
	 * 
	 * @author zhangxb16
	 */
	@Override
	public String findcaseCodeByctmCode(String ctmCode) {
		String caseCode = toCaseInfoMapper.findcaseCodeByctmCode(ctmCode);
		return caseCode;
	}

	@Override
	public ToCaseInfoCountVo getToCaseInfoCountAll() {

		return toCaseInfoMapper.getToCaseInfoCountAll();
	}

	@Override
	public ToCaseInfoCountVo countToCaseInfoAll() {
		// TODO Auto-generated method stub
		// return toCaseInfoMapper.;
		return null;
	}

	@Override
	public ToCaseInfoCountVo countToCaseInfoByOrgId(String orgId, String startDate, String endDate) {

		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setStartDate(startDate);
		toCase.setEndDate(endDate);
		return toCaseMapper.countToCaseInfoByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCaseInfoListByOrgId(String orgId) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		return toCaseMapper.countToCaseInfoListByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCaseInfoListByOrgList(List<String> orgList) {

		return toCaseMapper.countToCaseInfoListByOrgList(orgList);
	}

	@Override
	public int countToCaseInfoByOrgList(List<String> strArrayList, String startDate, String endDate) {
		return toCaseMapper.countToCaseInfoByOrgList(strArrayList, startDate, endDate);
	}

	@Override
	public int initCountToCaseInfoByOrgId(String orgId, String createTime) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setTime(createTime);
		return toCaseMapper.initCountToCaseInfoByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCaseInfoListByIdList(List<String> idList) {

		return toCaseMapper.countToCaseInfoListByIdList(idList);
	}

	@Override
	public int countToCaseInfoByIdList(List<String> idList, String startDate, String endDate) {

		return toCaseMapper.countToCaseInfoByIdList(idList, startDate, endDate);
	}

	/**
	 * 功能：根据ctm 推送过来的编号到 T_TG_GUEST_INFO 表中去查询，是否已经存在
	 * 
	 * @author zhangxb16
	 */
	@Override
	public int isExistCaseCode(String caseCode) {
		int existcasecode = toCaseInfoMapper.isExistCaseCode(caseCode);
		return existcasecode;
	}

	@Override
	public Integer updateByTargetCode(Map<String, Object> param) {
		if (param == null || param.isEmpty()) {
			return 0;
		}
		return toCaseInfoMapper.updateByTragertCode(param);
	}

	@Override
	public Integer exportCTMCase(String ctmCode) {
		return toCaseInfoMapper.exportCTMCase(ctmCode);
	}
}
