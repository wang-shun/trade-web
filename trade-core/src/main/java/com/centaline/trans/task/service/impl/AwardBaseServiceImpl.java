package com.centaline.trans.task.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ActHiTaskinst;
import com.centaline.trans.task.entity.AwardBase;
import com.centaline.trans.task.entity.AwardBaseConfig;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.repository.ActHiTaskinstMapper;
import com.centaline.trans.task.repository.AwardBaseConfigMapper;
import com.centaline.trans.task.repository.AwardBaseMapper;
import com.centaline.trans.task.service.AwardBaseService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;

@Service
public class AwardBaseServiceImpl implements AwardBaseService {
	@Autowired
	private ActHiTaskinstMapper actHiTaskinstMapper;
	@Autowired
	private AwardBaseMapper awardBaseMapper;
	@Autowired
	private AwardBaseConfigMapper awardBaseConfigMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private TsTeamPropertyService teamPropertyService;

	/**
	 * 奖金计算入口
	 */
	@Override
	public void doAwardCalculate(ToHouseTransfer toHouseTransfer, String processInstanceId) {
		List<AwardBaseConfig> list = awardBaseConfigMapper.getConsultantConfig();
		List<ActHiTaskinst> tasks = actHiTaskinstMapper.getConsultantTask(getValueList(list, "srvItemCode"),
				processInstanceId);
		
		Set<AwardBase> awardSet = getConsultantAwradSet(list, tasks);
		Set<String> orgsArr = getValueList(tasks, "orgId");
		
		getAwardToSet(orgsArr, TransJobs.TJYZL.getCode(), awardSet);
		getAwardToSet(orgsArr, TransJobs.TZJL.getCode(), awardSet);
		getAwardToSet(orgsArr, TransJobs.TZJ.getCode(), awardSet);
		getAwardToSet(orgsArr,TransJobs.TSJYZG.getCode(), awardSet);
		getAwardToSet(orgsArr, TransJobs.TJYZG.getCode(), awardSet);
		
		Iterator<AwardBase>abs= awardSet.iterator();
		while (abs.hasNext()) {
			AwardBase ab=abs.next();
			Job j=uamUserOrgService.getJobByCode(ab.getJobCode());
			ab.setJobId(j.getId());
			ab.setCreateTime(new Date());
			ab.setCaseCode(toHouseTransfer.getCaseCode());
			if (TransJobs.TJYGW.getCode().equals(ab.getJobCode())) {
				continue;
			}
			AwardBaseConfig conf = getAwardBaseConfig(ab);
			if(conf==null){//找不到该人员配置，从分金人员中移除
				abs.remove();
				continue;
			}
			ab.setBaseAmount(conf.getSrvFee());
			ab.setConfigId(conf.getPkId());
			
		}
		//先删除奖金数据
		awardBaseMapper.deleteByCaseCode(toHouseTransfer.getCaseCode());
		// 插入奖金数据
		batchInsert(awardSet);
	}
	
	/**
	 * 插入奖金奖金数据
	 * 
	 * @param abs
	 */
	private void batchInsert(Set<AwardBase> abs) {
		for (AwardBase awardBase : abs) {
			awardBaseMapper.insert(awardBase);
		}
	}

	/**
	 * 获得交易顾问所得奖金
	 * 
	 * @param confs
	 * @param tasks
	 * @return
	 */
	private Set<AwardBase> getConsultantAwradSet(List<AwardBaseConfig> confs, List<ActHiTaskinst> tasks) {
		Set<AwardBase> awardSet = new HashSet<>();
		for (ActHiTaskinst task : tasks) {
			Org org = uamUserOrgService.getOrgById(task.getOrgId());
			AwardBase ab = new AwardBase(task.getUserId(),TransJobs.TJYGW.getCode(), task.getOrgId(), org.getOrgCode());
			AwardBaseConfig conf = getByDfKey(confs, task.getTaskDefKey());
			ab.setConfigId(conf.getPkId());
			ab.setBaseAmount(conf.getSrvFee());
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
			// 交易顾问奖金在这里这作处理
		} else if (TransJobs.TJYZG.getCode().equals(ab.getJobCode())) {
			List<UserOrgJob> uoj = uamUserOrgService.getUserOrgJobByUserIdAndJobCode(ab.getParticipant(),
					ab.getJobCode());
			conf.setTeamNoEnd(uoj.size());
		} else if (TransJobs.TSJYZG.getCode().equals(ab.getJobCode())) {
			TsTeamProperty ttp = teamPropertyService.findTeamPropertyByTeamCode(ab.getOrgCode());
			List<UserOrgJob> uoj = uamUserOrgService.getUserOrgJobByUserIdAndJobCode(ab.getParticipant(),
					ab.getJobCode());
			conf.setTeamNoEnd(uoj.size());
			if (ttp != null) {
				conf.setTeamProperty(ttp.getTeamProperty());
			}
		} else if (TransJobs.TZJ.getCode().equals(ab.getJobCode())) {
			Integer orgSize = awardBaseMapper.getDirectorOrgSize(ab.getParticipant(), ab.getJobCode());
			conf.setTeamNoEnd(orgSize);
		} else if (TransJobs.TZJL.getCode().equals(ab.getJobCode())) {
			Integer orgSize = awardBaseMapper.getGeneralManagerOrgSize(ab.getParticipant(), ab.getJobCode());
			conf.setTeamNoEnd(orgSize);
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
	private void getAwardToSet(Set<String> orgsArr, String jobCode, Set<AwardBase> awardSet) {
		for (String orgId : orgsArr) {
			Org org=null;
			if(TransJobs.TZJ.getCode().equals(jobCode)){
				org= uamUserOrgService.getParentOrgByDepHierarchy(orgId, DepTypeEnum.TYCQY.getCode());
			}else if(TransJobs.TZJL.getCode().equals(jobCode))
			{
				org= uamUserOrgService.getParentOrgByDepHierarchy(orgId, DepTypeEnum.TYCZB.getCode());
			}else{
				org= uamUserOrgService.getOrgById(orgId);
			}
			if(org==null)continue;
			List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), jobCode);
			
			if (users != null && !users.isEmpty()) {
				for (User user : users) {
					awardSet.add(new AwardBase(user.getId(), jobCode, org.getId(), org.getOrgCode()));
				}
			}
		}
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
			Set<String> ar = new HashSet<>();
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
		return new HashSet<>();

	}

}
