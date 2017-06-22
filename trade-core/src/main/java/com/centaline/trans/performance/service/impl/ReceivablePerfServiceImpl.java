package com.centaline.trans.performance.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.common.enums.CommSubjectEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.performance.entity.ReceivablePerf;
import com.centaline.trans.performance.enums.ReceivablePerfForm;
import com.centaline.trans.performance.enums.ReceivablePerfStatus;
import com.centaline.trans.performance.repository.ReceivablePerfMapper;
import com.centaline.trans.performance.service.ReceivablePerfService;
import com.centaline.trans.performance.vo.ReceivablePerfVo;
import com.centaline.trans.utils.DateUtil;

/**
 * 生成应收业绩service
 * 
 * @author jjm
 *
 */
@Service
public class ReceivablePerfServiceImpl implements ReceivablePerfService {
	@Autowired
	private ReceivablePerfMapper receivablePerfMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamBasedataService uamBasedataService;
	/**
	 * 生成应收业绩
	 */
	@Override
	public void generatePerf(ReceivablePerfVo vo) {
		ReceivablePerf perf ;
		if (CommSubjectEnum.EVAL_FEE.getCode().equals(vo.getSubject())) {
			perf= generatePerfWithEva(vo);
			receivablePerfMapper.insertSelective(perf);
		}else if(CommSubjectEnum.SERVICE_FEE.getCode().equals(vo.getSubject())){
			perf=  generatePerfWithServiceFee(vo);
			receivablePerfMapper.insertSelective(perf);
		}
		
	}
	/**
	 * 生成服务业绩
	 * @param vo
	 * @return
	 */
	private ReceivablePerf generatePerfWithServiceFee(ReceivablePerfVo vo){
		return generatePerfAmountFixed(vo);
	}
	/**
	 * 生成评估费业绩
	 * @param vo
	 * @return
	 */
	private ReceivablePerf generatePerfWithEva(ReceivablePerfVo vo) {
		return generatePerfAmountFixed(vo);
	}
	/**
	 * 生成业绩  业绩金额固定=销售金额
	 * @param vo
	 * @return
	 */
	private ReceivablePerf generatePerfAmountFixed(ReceivablePerfVo vo)  {
		ReceivablePerf perf = new ReceivablePerf();
		setPerfComm(perf, vo);
		setManagementAndOrg(perf, vo);
		perf.setShareBase(vo.getSalesAmount());// 设置业绩基数,这里的基数直接等于评估费销售的差价
		perf.setShareTime(vo.getSharesTime());
		perf.setShareAmount(vo.getSalesAmount());
		perf.setSharesRate(new BigDecimal(1));// 拆分比例直接是1
		perf.setStatus(ReceivablePerfStatus.GENERATED.getCode());// 评估费过来的业绩直接是已经生成,因为不需要看合同
		return perf;
	}

	/**
	 * 设置应收业绩共同字段
	 * 
	 * @param perf
	 * @param vo
	 */
	private void setPerfComm(ReceivablePerf perf, ReceivablePerfVo vo) {
		//生成业绩编号//
		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String month = dateStr.substring(0, 6);
		String sharesCode = uamBasedataService.nextSeqVal("SHARES_CODE", month);
		
		perf.setSharesCode(sharesCode);
		perf.setCaseCode(vo.getCaseCode());
		perf.setBizCode(vo.getBizCode());
		perf.setBizPkid(vo.getBizPkid());
		perf.setIsDeleted(0);
		perf.setSubject(vo.getSubject());
		perf.setUserId(vo.getUserId());
		perf.setRoleType(vo.getRoleType());
		setManagementAndOrg(perf, vo);
	}

	/**
	 * 设置各层组织及管理人员
	 * 
	 * @param perf
	 * @param vo
	 */
	private void setManagementAndOrg(ReceivablePerf perf, ReceivablePerfVo vo) {
		String teamId = null;// 组ID
		String districtId = null;// 贵宾服务部ID
		String hqId = null;// 总部ID
		Org org = uamUserOrgService.getOrgById(vo.getOrgId());
		if (DepTypeEnum.TYCTEAM.getCode().equals(org.getDepHierarchy())) {//组别
			teamId = org.getId();
			Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(teamId, DepTypeEnum.TYCQY.getCode());
			districtId = districtOrg.getId();
			hqId = districtOrg.getParentId();
		}
		if (DepTypeEnum.TYCQY.getCode().equals(org.getDepHierarchy())) {//贵宾服务中心
			Org districtOrg = uamUserOrgService.getOrgById(org.getId());
			districtId = org.getId();
			hqId = districtOrg.getParentId();
		}
		if (DepTypeEnum.TYCZB.getCode().equals(org.getDepHierarchy())) { //誉萃总部
			hqId = org.getDepHierarchy();
		}
		if (teamId != null) {
			perf.setTeamId(teamId);
			perf.setManager(getLeaderUserIdByOrgIdAndJobCode(teamId, TransJobs.TJYZG.getCode()));
			perf.setSeniorManager(getLeaderUserIdByOrgIdAndJobCode(teamId, TransJobs.TSJYZG.getCode()));
			perf.setAssistant(getLeaderUserIdByOrgIdAndJobCode(teamId, TransJobs.TJYZL.getCode()));
		}
		if (districtId != null) {
			perf.setDistrict(districtId);
			perf.setDirector(getLeaderUserIdByOrgIdAndJobCode(districtId, TransJobs.TZJ.getCode()));
		}
		if (hqId != null) {
			perf.setCompany(hqId);
			perf.setGerneralManager(getLeaderUserIdByOrgIdAndJobCode(hqId, TransJobs.TZJL.getCode()));
		}
	}

	/**
	 * 根据OrgId和JobCode获得领导Id(uamUserOrgService中的方法会返回无效的和已经删除的领导)
	 * 
	 * @param orgId
	 * @param jobCode
	 * @return
	 */
	private String getLeaderUserIdByOrgIdAndJobCode(String orgId, String jobCode) {
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, jobCode);
		if (users == null || users.isEmpty())
			return null;
		if (users.size() == 1) {
			return users.get(0).getId();
		}
		for (User user : users) {
			List<UserOrgJob> uojs = uamUserOrgService.getUserOrgJobByUserIdAndJobCode(user.getId(), jobCode);
			for (UserOrgJob userOrgJob : uojs) {
				if (userOrgJob.getIsLeader() != null && userOrgJob.getIsLeader().intValue() == 1) {
					return user.getId();
				}
			}
		}
		return null;
	}
}
