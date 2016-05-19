package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.award.entity.AwardBaseEntity;
import com.centaline.trans.award.repository.AwardBaseEntityMapper;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.task.entity.ActHiTaskinst;
import com.centaline.trans.task.entity.AwardBase;
import com.centaline.trans.task.entity.AwardBaseConfig;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.repository.ActHiTaskinstMapper;
import com.centaline.trans.task.repository.AwardBaseConfigMapper;
import com.centaline.trans.task.service.AwardBaseService;

/**
 * @author dead on requirements;
 */
@Service
public class AwardBaseServiceImpl implements AwardBaseService {
	@Autowired
	private ActHiTaskinstMapper actHiTaskinstMapper;
	@Autowired
	private AwardBaseEntityMapper awardBaseMapper;
	@Autowired
	private AwardBaseConfigMapper awardBaseConfigMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired
	private ToCaseService toCaseService;
	// ****//
	static final Map<String, Set<String>> SRV_CODE_MAPPING;
	static final Set<String> ZBJR;
	static final Set<String> QZJD;
	static final String zbjr = "zbjr";
	static final String qzjd = "qzjd";
	static final Set<String> ALL;

	static {
		ZBJR = new HashSet<>();
		ZBJR.add("TransSign");
		ZBJR.add("ComLoanProcess");
		ZBJR.add("LoanClose");
		QZJD = new HashSet<>();
		QZJD.add("Guohu");
		QZJD.add("PSFSign");
		SRV_CODE_MAPPING = new HashMap<>();
		SRV_CODE_MAPPING.put("ZBJR", ZBJR);
		SRV_CODE_MAPPING.put("QZJD", QZJD);
		ALL = new HashSet<>(ZBJR);
		ALL.addAll(QZJD);
	}

	private int countManagerTeam(String userId) {
		return awardBaseMapper.countManagerTeam(userId);
	}

	/**
	 * 统计服务数量
	 * 
	 * @param tasks
	 * @return 需求变更 此方法暂时用不到
	 */
	@SuppressWarnings("unused")
	@Deprecated()
	private int[] countSrvCodeGroupByManagerSrv(List<ActHiTaskinst> tasks) {
		int zCount = 0, qCount = 0;
		for (ActHiTaskinst actHiTaskinst : tasks) {
			if (ZBJR.contains(actHiTaskinst.getTaskDefKey())) {
				zCount++;
			}
			if (QZJD.contains(actHiTaskinst.getTaskDefKey())) {
				qCount++;
			}
		}
		return new int[] { zCount, qCount };
	}

	/**
	 * 奖金计算入口
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void doAwardCalculate(ToHouseTransfer toHouseTransfer, String processInstanceId) {

		if (awardBaseMapper.countAward(toHouseTransfer.getCaseCode()) > 0) {

			return;
		}

		List<AwardBaseConfig> list = awardBaseConfigMapper.getConsultantConfig();
		List<ActHiTaskinst> tasks = actHiTaskinstMapper.getConsultantTask(getValueList(list, "srvItemCode"),
				processInstanceId);

		List<AwardBase> awardList = getConsultantAwradSet(list, tasks);
		Set<String> districtId = getValueList(awardList, "districtId");
		Set<String> orgsArr = getValueList(tasks, "orgId");
		ToCase caseDetails = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
		Set<String> mOrgs = new HashSet<>();// 主办组别
		mOrgs.add(caseDetails.getOrgId());

		Map<String, Integer> qzjdMap = groupSrvByOrg(tasks, QZJD);
		Map<String, Integer> allMap = groupSrvByDistrictId(awardList);// <区域Id,任务数>
		// 有多少权证金融的任务
		Integer countQzjd = qzjdMap.values().stream().reduce(0, Integer::sum);
		Integer countAll = allMap.values().stream().reduce(0, Integer::sum);
		// 非主办组别
		Set<String> nmOrgs = new HashSet<>(qzjdMap.keySet());

		// 助理
		addToList(awardList, getAwardToList(mOrgs, TransJobs.TJYZL.getCode()));
		// 总经理
		addToList(awardList, getAwardToList(orgsArr, TransJobs.TZJL.getCode()));
		// 总监
		List<AwardBase> dManager = getAwardToList(districtId, TransJobs.TZJ.getCode());
		// 计算总监SrvPart
		calculateSrvPart(allMap, dManager, countAll);
		// 将总管数据添加到集合
		addToListB(awardList, dManager);
		// 主办主管
		List<AwardBase> mManager = getAwardToList(mOrgs, TransJobs.TJYZG.getCode());
		setSrvCode(mManager, zbjr);// 设置对应的SrvCode
		// 协办主管
		List<AwardBase> nmManager = getAwardToList(nmOrgs, TransJobs.TJYZG.getCode());
		setSrvCode(nmManager, qzjd);// 设置对应的SrvCode
		// 计算协办主管SrvPart
		calculateSrvPart(qzjdMap, nmManager, countQzjd);
		// 合并主管数据
		mManager.addAll(nmManager);
		// 将主管数据添加到集合中(这里要考虑前台主管为同一人应有两条数据，但同时也要考虑岗位优先级规则)
		addToListB(awardList, mManager);
		// 设置AwardBase共同字段
		setAwardBaseCommInfo(awardList, toHouseTransfer.getCaseCode());
		// 插入奖金数据
		batchInsert(awardList);
	}

	/**
	 * 设置奖金基本字段
	 * 
	 * @param awardList
	 * @param caseCode
	 */
	private void setAwardBaseCommInfo(List<AwardBase> awardList, String caseCode) {
		Iterator<AwardBase> abs = awardList.iterator();
		while (abs.hasNext()) {
			AwardBase ab = abs.next();
			Job j = uamUserOrgService.getJobByCode(ab.getJobCode());
			ab.setJobId(j.getId());
			ab.setCreateTime(new Date());
			ab.setCaseCode(caseCode);
			ab.setPaid("0");
			if (TransJobs.TJYGW.getCode().equals(ab.getJobCode())) {
				continue;
			}
			AwardBaseConfig conf = getAwardBaseConfig(ab);
			if (conf == null) {// 找不到该人员配置，从分金人员中移除
				abs.remove();
				continue;
			}
			ab.setSrvCode(conf.getSrvItemCode());
			ab.setBaseAmount(dToB(conf.getSrvFee()));
			ab.setConfigId(conf.getPkId());

		}
	}

	/**
	 * Double转Decimal
	 * 
	 * @param d
	 * @return
	 */
	private BigDecimal dToB(Double d) {
		return d == null ? null : new BigDecimal(d);
	}

	/**
	 * 将l2的元素添加到l1重复元素则不添加
	 * 
	 * @param l1
	 * @param l2
	 */
	private void addToList(List<AwardBase> l1, List<AwardBase> l2) {
		l2.forEach(x -> {
			if (!l1.contains(x)) {
				l1.add(x);
			}
		});
	}

	/**
	 * 将l2的元素添加到l1中,在l1中重复元素不添加在l2已经重复则依然添加
	 * 
	 * @param l1
	 * @param l2
	 */
	private void addToListB(List<AwardBase> l1, List<AwardBase> l2) {
		List<AwardBase> t = new ArrayList<>();
		l2.stream().filter(x -> (!l1.contains(x))).forEach(c -> {
			t.add(c);
		});
		l1.addAll(t);
	}

	/**
	 * 设置SrvCode
	 * 
	 * @param l
	 * @param srvCode
	 */
	private void setSrvCode(List<AwardBase> l, String srvCode) {
		if (l != null)
			l.forEach(x -> x.setSrvCode(srvCode));
	}

	/**
	 * 插入奖金奖金数据
	 * 
	 * @param abs
	 */
	private void batchInsert(List<AwardBase> abs) {
		for (AwardBase awardBase : abs) {
			awardBaseMapper.insertSelective(awardBase);
		}
	}

	/**
	 * @param task
	 * @param qzjd
	 * @return
	 */
	private Map<String, Integer> groupSrvByOrg(List<ActHiTaskinst> tasks, Set<String> qzjd) {
		Map<String, Integer> result = new HashMap<>();
		for (ActHiTaskinst task : tasks) {
			if (qzjd.contains(task.getTaskDefKey())) {
				String orgId = task.getOrgId();
				if (result.containsKey(orgId)) {
					result.put(orgId, result.get(orgId) + 1);
				} else {
					result.put(orgId, 1);
				}
			}
		}
		return result;
	}

	/**
	 * @param task
	 * @param qzjd
	 * @return
	 */
	private Map<String, Integer> groupSrvByDistrictId(List<AwardBase> awards) {
		Map<String, Integer> result = new HashMap<>();
		for (AwardBase awardBase : awards) {
			String orgId = awardBase.getDistrictId();
			if (result.containsKey(orgId)) {
				result.put(orgId, result.get(orgId) + Integer.valueOf(awardBase.getSrvPartIn().toString()));
			} else {
				result.put(orgId, Integer.valueOf(awardBase.getSrvPartIn().toString()));
			}
		}
		return result;
	}

	/**
	 * 计算分配比率
	 * 
	 * @param orgSrvCountMap
	 * @param nmManager
	 * @param tCount
	 */
	private void calculateSrvPart(Map<String, Integer> orgSrvCountMap, List<AwardBase> nmManager, Integer tCount) {
		nmManager.forEach(x -> {
			x.setSrvPartTotal(dToB(tCount.doubleValue()));
			x.setSrvPartIn(dToB(Double.valueOf(orgSrvCountMap.get(x.getOrgId()).intValue())));
		});
	}

	/**
	 * 获得交易顾问所得奖金
	 * 
	 * @param confs
	 * @param tasks
	 * @return
	 */
	private List<AwardBase> getConsultantAwradSet(List<AwardBaseConfig> confs, List<ActHiTaskinst> tasks) {
		List<AwardBase> awardSet = new ArrayList<AwardBase>();
		for (ActHiTaskinst task : tasks) {
			Org district = uamUserOrgService.getParentOrgByDepHierarchy(task.getOrgId(), DepTypeEnum.TYCQY.getCode());
			AwardBase ab = new AwardBase(task.getUserId(), TransJobs.TJYGW.getCode(), task.getOrgId());
			AwardBaseConfig conf = getByDfKey(confs, task.getTaskDefKey());
			ab.setConfigId(conf.getPkId());
			ab.setBaseAmount(dToB(conf.getSrvFee()));
			ab.setGuohuTime(new Date());
			ab.setDistrictId(district.getId());
			ab.setTeamId(task.getOrgId());
			ab.setSrvPartTotal(dToB(1d));
			ab.setSrvPartIn(dToB(1d));
			ab.setSrvCode(conf.getSrvItemCode());
			awardSet.add(ab);
		}
		return awardSet;
	}

	/**
	 * 获得交易顾问对应的奖金分配配制
	 * 
	 * @param confs
	 * @param dfKey
	 * @return
	 */
	private AwardBaseConfig getByDfKey(List<AwardBaseConfig> confs, String dfKey) {
		for (AwardBaseConfig conf : confs) {
			if (dfKey.equals(conf.getSrvItemCode())) {
				return conf;
			}
		}
		return null;
	}

	/**
	 * 获得各类岗位应分配奖金配置
	 * 
	 * @param ab
	 * @return
	 */
	private AwardBaseConfig getAwardBaseConfig(AwardBase ab) {
		AwardBaseConfig conf = new AwardBaseConfig();
		conf.setJobCode(ab.getJobCode());

		if (TransJobs.TJYZL.getCode().equals(ab.getJobCode())) {
			// 没有其它要设置的项
		} else if (TransJobs.TJYGW.getCode().equals(ab.getJobCode())) {
			// 交易顾问奖金不在这里这作处理
		} else if (TransJobs.TJYZG.getCode().equals(ab.getJobCode())) {
			conf.setSrvItemCode(ab.getSrvCode());// 这里需要区分主办和协办
			Integer orgSize = this.countManagerTeam(ab.getParticipant());
			conf.setTeamNoEnd(orgSize);
		} else if (TransJobs.TZJ.getCode().equals(ab.getJobCode())
				|| TransJobs.TZJL.getCode().equals(ab.getJobCode())) {
			// 总经理和总监根据所带组
			Integer orgSize = this.countManagerTeam(ab.getParticipant());
			conf.setTeamNoEnd(orgSize);
			// conf.setSrvItemCode(ab.getSrvCode());
		}
		List<AwardBaseConfig> confs = awardBaseConfigMapper.getConfig(conf);
		if (confs != null && !confs.isEmpty()) {
			return confs.get(0);
		}
		return null;

	}

	/**
	 * 获得对应org下面对应岗位人员
	 * 
	 * @param orgsArr
	 * @param jobCode
	 * @param awardSet
	 */
	private List<AwardBase> getAwardToList(Set<String> orgsArr, String jobCode) {
		List<AwardBase> result = new ArrayList<>();
		Set<AwardBase> awardSet = null;
		for (String orgId : orgsArr) {
			Org org = null;
			Org district = uamUserOrgService.getParentOrgByDepHierarchy(orgId, DepTypeEnum.TYCQY.getCode());
			if (TransJobs.TZJ.getCode().equals(jobCode)) {
				org = district;
			} else if (TransJobs.TZJL.getCode().equals(jobCode)) {// 总经理这里要特殊处理
				org = uamUserOrgService.getParentOrgByDepHierarchy(orgId, DepTypeEnum.TYCZB.getCode());
				if (awardSet == null) {
					awardSet = new HashSet<>();
				}
			} else {
				org = uamUserOrgService.getOrgById(orgId);
			}
			if (org == null)
				continue;
			List<User> users=null;
			if(TransJobs.TZJ.getCode().equals(jobCode)||TransJobs.TZJL.getCode().equals(jobCode)||TransJobs.TJYZG.getCode().equals(jobCode)){//管理岗
				users = awardBaseMapper.getUserByJobCodeAndOrgId(jobCode, org.getId());
			}else{
				users = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, jobCode);
			}
			if (users != null && !users.isEmpty()) {
				for (User user : users) {
					AwardBase ab = new AwardBase(user.getId(), jobCode, orgId);
					
					/**
					 * 这里将SrvPart和SrvPartIn设置成1，后面会到所有不为100%的数据作处理
					 */
					ab.setSrvPartIn(dToB(1d));
					ab.setSrvPartTotal(dToB(1d));
					ab.setGuohuTime(new Date());
					if (!TransJobs.TZJL.getCode().equals(jobCode)) {// 除了总经理都要设置
						if (!TransJobs.TZJ.getCode().equals(jobCode)) {// 总监的话teamId不用设置
							ab.setTeamId(orgId);
						}
						ab.setDistrictId(district.getId());
						if (result == null) {
							result = new ArrayList<>();
						}
						result.add(ab);
					} else {
						awardSet.add(ab);
					}
				}
			}
		}
		if (TransJobs.TZJL.getCode().equals(jobCode)) {
			result = new ArrayList<>(awardSet);
		}
		return result;
	}

	/**
	 * 将list中的对应字段以String[]返回
	 * 
	 * @param list
	 * @param prop
	 * @return
	 */

	private Set<String> getValueList(@SuppressWarnings("rawtypes") List list, String prop) {
		if (list != null && !list.isEmpty()) {
			Set<String> ar = new HashSet<String>();
			for (int i = 0; i < list.size(); i++) {
				String value = null;
				try {
					value = BeanUtils.getProperty(list.get(i), prop);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ar.add(value);
			}
			return ar;
		}
		return new HashSet<String>();

	}

	/**
	 * 设置结案时候
	 */
	@Override
	public void setAwradCaseCloseDate(String caseCode, Date closeDate) {
		AwardBaseEntity e = new AwardBaseEntity();
		e.setCaseCode(caseCode);
		e.setCloseTime(closeDate);
		awardBaseMapper.setCloseDateByCaseCode(e);
	}

}
