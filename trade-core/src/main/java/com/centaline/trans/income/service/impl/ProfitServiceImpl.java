package com.centaline.trans.income.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.income.entity.TsIncomeStatistics;
import com.centaline.trans.income.entity.TsOrTemplate;
import com.centaline.trans.income.entity.TsOverriding;
import com.centaline.trans.income.repository.TsIncomeStatisticsMapper;
import com.centaline.trans.income.repository.TsOrTemplateMapper;
import com.centaline.trans.income.repository.TsOverridingMapper;
import com.centaline.trans.income.service.ProfitService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.utils.DateUtil;

@Service
public class ProfitServiceImpl implements ProfitService {

	@Autowired
	private TsIncomeStatisticsMapper tsincomeStatisticsMapper;

	@Autowired
	private TsOrTemplateMapper tsorTemplateMapper;

	@Autowired
	private TgServItemAndProcessorMapper tgservItemAndProcessorMapper;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired
	private TsOverridingMapper tsoverridingMapper;

	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private TsTeamPropertyService teamPropertyService;
	@Autowired
	private UamUserOrgService uamUserOrgSerivce;

	/**
	 * 功能：根据jobCode 得到jobid
	 */
	public String SelectJobByJobCode(String jobCode) {
		String jobid = null;
		Job jb = uamUserOrgService.getJobByCode(jobCode);
		if (null != jb) {
			jobid = jb.getId();
		}
		return jobid;
	}

	/**
	 * 功能：找出交易顾问所在组的 客户经理[consultant]
	 */
	public String selectConsultantByguwen(String orgId, String jobCode) {
		String consultantId = null;
		String jobId = SelectJobByJobCode(jobCode);
		List<User> userList = uamUserOrgService.getUserByOrgIdAndJobId(orgId,
				jobId);
		if (null != userList && userList.size() > 0) {
			consultantId = userList.get(0).getId();
		}
		return consultantId;
	}

	/**
	 * 功能：找出交易顾问所在组的 交易主管[Manager]
	 */
	public String selectManagerByguwen(String orgId, String jobCode) {
		String managerId = null;
		String jobId = SelectJobByJobCode(jobCode);
		List<User> userList = uamUserOrgService.getUserByOrgIdAndJobId(orgId,
				jobId);
		if (null != userList && userList.size() > 0) {
			managerId = userList.get(0).getId();
		}
		return managerId;
	}

	/**
	 * 功能：找出交易顾问所在组的 总监[director]
	 */
	public String selectDirectorBy(String orgId, String jobCode) {
		String directorId = null;
		Org oo = uamUserOrgService.getParentOrgByDepHierarchy(orgId,
				"yucui_district");
		if (null != oo) {
			String jobId = SelectJobByJobCode(jobCode);
			List<User> userList = uamUserOrgService.getUserByOrgIdAndJobId(
					oo.getId(), jobId);
			if (null != userList && userList.size() > 0) {
				directorId = userList.get(0).getId();
			}
		}

		return directorId;
	}

	/**
	 * 功能：找出交易顾问所在组的 助理[TeamAssistant]
	 */
	public String selectTeamAssistant(String orgId, String jobCode) {
		String teamassistantId = null;
		String jobId = SelectJobByJobCode(jobCode);
		List<User> userList = uamUserOrgService.getUserByOrgIdAndJobId(orgId,
				jobId);
		if (null != userList && userList.size() > 0) {
			teamassistantId = userList.get(0).getId();
		}

		return teamassistantId;
	}

	/**
	 * 功能：找出交易顾问所在组的 总经理[GeneralManager]
	 */
	public String selectGeneralManager(String orgId, String jobCode) {
		String generalmanagerId = null;
		String jobId = SelectJobByJobCode(jobCode);
		List<User> userList = uamUserOrgService.getUserByJobId(jobId);
		if (null != userList && userList.size() > 0) {
			generalmanagerId = userList.get(0).getId();
		}

		return generalmanagerId;
	}

	/**
	 * 功能：找出总监和总经理下面有多少个组
	 */
	public int selectJobHowGroup(String orgId, String depHierarchy) {
		Org oo = uamUserOrgService.getParentOrgByDepHierarchy(orgId,
				depHierarchy);
		List<Org> orgList = null;
		int directorgroupCount = 0;
		if (null != oo) {
			orgList = uamUserOrgService.getOrgByDepHierarchy(oo.getId(),
					"yucui_team");
			directorgroupCount = orgList.size();
		}

		return directorgroupCount;
	}

	/**
	 * 功能：收益 作者：zhangxb16
	 */
	@Override
	public int profitOperate(TsIncomeStatistics ts) {
		int overInsert = 0;

		// 2 根据查询到的
		// 服务编码[srv_code]和案件编号[case_code]到服务表[T_TG_SERV_ITEM_AND_PROCESSOR]中去查询交易顾问id[processor_id]
		String caseCode = ts.getCaseCode();
		String srvCode = ts.getIncomeCat();

		TgServItemAndProcessor tp = new TgServItemAndProcessor();
		tp.setCaseCode(caseCode);
		tp.setSrvCode(srvCode);
		TgServItemAndProcessor tsip = tgservItemAndProcessorMapper
				.selectServItem(tp);

		if (null != tsip) {
			// 3 根据用户id 去找 manager -> 总监 -> 总经理
			String consultantId = null;
			String managerId = null;
			String directorId = null;
			String generalmanagerId = null;
			String teamassistantId = null;
			if (null != tsip) {
				consultantId = selectConsultantByguwen(tsip.getOrgId(),
						"consultant"); // 根据交易顾问找【客户经理】
				managerId = selectManagerByguwen(tsip.getOrgId(), "Manager"); // 根据交易顾问找【交易主管】
				directorId = selectDirectorBy(tsip.getOrgId(), "director"); // 根据交易顾问找【总监】
				generalmanagerId = selectGeneralManager(tsip.getOrgId(),
						"GeneralManager");// 根据交易顾问找【总经理】
				teamassistantId = selectTeamAssistant(tsip.getOrgId(),
						"TeamAssistant");// 根据交易顾问找【助理】
			}

			// 查询总监下面有多少个组
			int directorgroupCount = selectJobHowGroup(tsip.getOrgId(),
					"yucui_district");

			// 查询总经理下面有多少个组
			int generalgroupCount = selectJobHowGroup(tsip.getOrgId(),
					"yucui_headquarter");

			// 4 找出 manager -> 总监 -> 总经理 他们对应的利率
			String jobstr = "consultant,Manager,director,GeneralManager,TeamAssistant";
			String joblei = null;
			String[] jobType = jobstr.split(",");
			// 循环数组
			for (int h = 0; h < jobType.length; h++) {
				joblei = jobType[h];

				TsOrTemplate tte = new TsOrTemplate();
				tte.setRoleCode(joblei);
				tte.setServCat(srvCode);

				if (joblei.equals("director")) { // 总监
					tte.setTeamNoStart(directorgroupCount);
				}
				if (joblei.equals("GeneralManager")) { // 总经理
					tte.setTeamNoStart(generalgroupCount);
				}

				// 根据userid 获取用户真实姓名
				String realname = null;
				String orgId = null;
				User ur = null;
				if (joblei.equals("consultant")) {
					ur = uamUserOrgService.getUserById(consultantId);
				}
				if (joblei.equals("Manager")) {
					ur = uamUserOrgService.getUserById(managerId);
				}
				if (joblei.equals("director")) {
					ur = uamUserOrgService.getUserById(directorId);
				}
				if (joblei.equals("GeneralManager")) {
					ur = uamUserOrgService.getUserById(generalmanagerId);
				}
				if (joblei.equals("TeamAssistant")) {
					ur = uamUserOrgService.getUserById(teamassistantId);
				}
				if (null != ur) {
					realname = ur.getRealName();
					orgId = ur.getOrgId();
				}

				List<TsOrTemplate> ortempList = tsorTemplateMapper
						.selectByroleCodeandServcat(tte);
				if (null != ortempList && ortempList.size() > 0) {
					for (int k = 0; k < ortempList.size(); k++) {
						BigDecimal baifen = new BigDecimal(0.01).setScale(2,
								RoundingMode.HALF_UP);
						BigDecimal per = new BigDecimal(ortempList.get(k)
								.getPercentage()).multiply(baifen).setScale(2,
								RoundingMode.HALF_UP); // 利率
						BigDecimal incomeamount = ts.getIncomeAmount(); // 金额

						// 金额 = 为模板表中的利率 * 收入统计表中的金额 [并对金额进行四舍五入]
						BigDecimal money = per.multiply(incomeamount).setScale(
								2, RoundingMode.HALF_UP);

						// 5 往T_TS_OVERRIDING 表中写入记录
						TsOverriding overrind = new TsOverriding();
						overrind.setCaseCode(ts.getCaseCode()); // 案件编号
						overrind.setIncomeId(ts.getPkid()); // 收入统计表中的pkid
						overrind.setOrOwnerName(realname); // 用户的真实姓名
						if (joblei.equals("consultant")) {
							overrind.setOrOwnerId(consultantId); // 用户的userid
						}
						if (joblei.equals("Manager")) {
							overrind.setOrOwnerId(managerId); // 用户的userid
						}
						if (joblei.equals("director")) {
							overrind.setOrOwnerId(directorId); // 用户的userid
						}
						if (joblei.equals("GeneralManager")) {
							overrind.setOrOwnerId(generalmanagerId); // 用户的userid
						}
						if (joblei.equals("TeamAssistant")) {
							overrind.setOrOwnerId(teamassistantId); // 用户的userid
						}
						overrind.setOrPar(String.valueOf(per)); // 利率
						overrind.setOrAmount(money.toString());
						overrind.setImportNo(ts.getImportNo()); // 导入批次
						overrind.setOrOwnerJobId(joblei); // jobCode
						overrind.setOrOwnerOrgId(orgId); // orgId

						overInsert = tsoverridingMapper.insert(overrind);
					}
				}
			}
		}

		return overInsert;
	}

	@Override
	public void doProcess() {
		List<TsIncomeStatistics> incomeList = tsincomeStatisticsMapper
				.selectWaitingProcessing();
		for (TsIncomeStatistics tsIncomeStatistics : incomeList) {
			try {
				processOne(tsIncomeStatistics);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void processOne(TsIncomeStatistics incomeSta) {

		TsIncomeStatistics ts = tsincomeStatisticsMapper
				.selectByFinCaseCode(incomeSta.getFinCaseCode());
		String finCode = ts == null ? null : ts.getFinCode();
		if (StringUtils.isBlank(finCode)) {// 没有fincode新生成 有的话设置IsChange为1
			Org org = uamUserOrgSerivce.getOrgById(incomeSta.getOrgId());
			TsTeamProperty teamPro = teamPropertyService
					.findTeamPropertyByTeamCode(org.getOrgCode());
			
			String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
			String month = dateStr.substring(0, 6);
			
			finCode = uamBasedataService.nextSeqVal("CJBH_CODE",
					teamPro.getFinTeamCode(), month);
		} else {
			Date belongDay = ts.getIncomeBelongDay();
			if (belongDay != null) {
				Date now = new Date();
				if (belongDay.getYear() != now.getYear()
						|| belongDay.getMonth() != now.getMonth()) {
					incomeSta.setIsChange("1");
				}
			}
		}
		incomeSta.setIncomeBelongDay(new Date());
		incomeSta.setFinCode(finCode);
		tsincomeStatisticsMapper.updateByPrimaryKeySelective(incomeSta);
		/**
		 * 分佣处理
		 */
		profitOperate(incomeSta);
	}
}
