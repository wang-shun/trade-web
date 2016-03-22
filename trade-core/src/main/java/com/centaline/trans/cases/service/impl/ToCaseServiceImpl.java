package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
@Transactional
public class ToCaseServiceImpl implements ToCaseService {

	@Autowired
	private ToCaseMapper toCaseMapper;

	@Autowired
	private ToSpvService toSpvService;
	@Autowired
	
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToCaseInfoService toCaseInfoService;

	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired
	private WorkFlowManager workflowManager;
	@Autowired
	private ToCloseService toCloseService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToWorkFlowService workflowService;
	@Autowired
	private TgServItemAndProcessorService serItemAndProcessorServce;

	@Override
	public int updateByPrimaryKey(ToCase record) {
		// TODO Auto-generated method stub
		return toCaseMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToCase findToCaseByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toCaseMapper.findToCaseByCaseCode(caseCode);
	}

	@Override
	public ToCase selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toCaseMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public ToCaseInfoCountVo queryConutCase(ToCase toCase) {
		return toCaseMapper.queryConutCase(toCase);
	}

	@Override
	public int updateByCaseCodeSelective(ToCase record) {
		return toCaseMapper.updateByCaseCodeSelective(record);
	}

	@Override
	public ToCaseInfoCountVo queryConutCaseByOrg(ToCase toCase) {
		return toCaseMapper.queryConutCaseByOrg(toCase);
	}

	@Override
	public List<ToCase> getOrgId() {
		return toCaseMapper.getOrgId();
	}

	@Override
	public List<ToCaseInfoCountVo> getCaseCount() {
		// int jds = 0;
		// int qys = 0;
		// int ghs = 0;
		// int jas = 0;
		// String createTime = "";
		String orgId = null;
		// List<ToCaseInfoCountVo> countList = new ArrayList<>();
		// ToCaseInfoCountVo toCaseInfoCount = new ToCaseInfoCountVo();
		// List<List<ToCaseInfoCountVo>> toCaseInfoCountVoLists =
		// getToCaseInfoCountListByOrgId(orgId);
		// model.addAttribute("toCaseInfoCountVoList", toCaseInfoCountVoLists);
		// for (List<ToCaseInfoCountVo> list : toCaseInfoCountVoLists) {
		// for (ToCaseInfoCountVo toCaseInfoCountVo : list) {
		// if(null != toCaseInfoCountVo.getCountJDS()){
		// jds = toCaseInfoCountVo.getCountJDS();
		// createTime = toCaseInfoCountVo.getCreateTime();
		// }else if(null != toCaseInfoCountVo.getCountQYS()){
		// qys = toCaseInfoCountVo.getCountQYS();
		// }else if(null != toCaseInfoCountVo.getCountGHS()){
		// ghs = toCaseInfoCountVo.getCountGHS();
		// }else if(null != toCaseInfoCountVo.getCountJAS()){
		// jas = toCaseInfoCountVo.getCountJAS();
		// }
		// }
		// }

		// toCaseInfoCount.setCountJDS(jds);
		// toCaseInfoCount.setCountQYS(qys);
		// toCaseInfoCount.setCountGHS(ghs);
		// toCaseInfoCount.setCountJAS(jas);
		// toCaseInfoCount.setCreateTime(createTime);
		// countList.add(toCaseInfoCount);

		// 接单数
		List<ToCaseInfoCountVo> toCaseInfoCountList = toCaseInfoService.countToCaseInfoListByOrgId(orgId);

		// ,签约
		List<ToCaseInfoCountVo> toSignCountList = toSpvService.countToSignListByOrgId(orgId);
		// ,过户
		List<ToCaseInfoCountVo> toHouseTransferCountList = toHouseTransferService
				.countToHouseTransferListByOrgId(orgId);
		// ,结案
		List<ToCaseInfoCountVo> toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);

		// return countList;
		return null;
	}

	// 跟进orgId查询统计数据
	private List<List<ToCaseInfoCountVo>> getToCaseInfoCountListByOrgId(String orgId) {

		// 接单数
		List<ToCaseInfoCountVo> toCaseInfoCountList = toCaseInfoService.countToCaseInfoListByOrgId(orgId);

		// ,签约
		List<ToCaseInfoCountVo> toSignCountList = toSpvService.countToSignListByOrgId(orgId);
		// ,过户
		List<ToCaseInfoCountVo> toHouseTransferCountList = toHouseTransferService
				.countToHouseTransferListByOrgId(orgId);
		// ,结案
		List<ToCaseInfoCountVo> toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);

		List<List<ToCaseInfoCountVo>> toCaseInfoCountLists = new ArrayList<>();
		toCaseInfoCountLists.add(toCaseInfoCountList);
		toCaseInfoCountLists.add(toSignCountList);
		toCaseInfoCountLists.add(toHouseTransferCountList);
		toCaseInfoCountLists.add(toCloseCountList);

		return toCaseInfoCountLists;
	}

	@Override
	public int insertSelective(ToCase record) {
		// TODO Auto-generated method stub
		return toCaseMapper.insertSelective(record);
	}

	@Override
	public List<ToOrgVo> getOrgIdAllByDep(String dep) {

		return toCaseMapper.getOrgIdAll(dep);
	}

	@Override
	public int getRedcountByOrgList(List<String> orgIdList, String strNum, String endNum) {
		return toCaseMapper.getRedcountByOrgList(orgIdList, strNum, endNum);
	}

	@Override
	public int getRedcountByIdList(List<String> idList, String strNum, String endNum) {
		return toCaseMapper.getRedcountByIdList(idList, strNum, endNum);
	}

	@Override
	public int updateByPrimaryKeySelective(ToCase record) {
		// TODO Auto-generated method stub
		return toCaseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int orgChange(String caseCode, String orgId) {

		List<User> managerUsers = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, TransJobs.TJYZG.getCode());
		if (managerUsers.size() == 0)
			return 0;
		User managerUser = managerUsers.get(0);

		// 案件信息更新
		ToCase toCase = findToCaseByCaseCode(caseCode);
		toCase.setLeadingProcessId(managerUser.getId());
		toCase.setOrgId(orgId);
		 updateByPrimaryKey(toCase);
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		toCaseInfo.setRequireProcessorId(managerUser.getId());
		toCaseInfoService.updateByPrimaryKey(toCaseInfo);
		List<String> instCode = workflowService.queryInstCodesByCaseCode(caseCode);
		workflowService.deleteByCaseCode(caseCode);
		serItemAndProcessorServce.deleteByPrimaryCaseCode(caseCode);
		for (String icStr : instCode) {
			unlocatedTaskService.deleteByInstCode(icStr);
			workflowManager.deleteProcess(icStr);
		}
		return 1;
	}

	
}
