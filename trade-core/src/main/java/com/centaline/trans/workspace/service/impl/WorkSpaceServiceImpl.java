package com.centaline.trans.workspace.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.workspace.entity.LoanStaDetails;
import com.centaline.trans.workspace.entity.OrgCount;
import com.centaline.trans.workspace.entity.Rank;
import com.centaline.trans.workspace.entity.SimpleLoanAgent;
import com.centaline.trans.workspace.entity.UserLightCount;
import com.centaline.trans.workspace.entity.WorkLoad;
import com.centaline.trans.workspace.entity.WorkSpace;
import com.centaline.trans.workspace.repository.WorkSpaceMapper;
import com.centaline.trans.workspace.service.WorkSpaceService;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
	@Autowired
	private WorkSpaceMapper mapper;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Override
	@Cacheable(value="WorkSpaceServiceImpl.countLight", key="#work")
	public int countLight(WorkSpace work) {
		return mapper.countLight(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanAgent", key="#workSpace")
	public Map staLoanAgent(WorkSpace workSpace) {
		return mapper.staLoanAgent(workSpace);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staSignCount", key="#work")
	public Integer staSignCount(WorkSpace work) {
		return mapper.staSignCount(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staReceiveCount", key="#work")
	public Integer staReceiveCount(WorkSpace work) {
		return mapper.staReceiveCount(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staTransferCount", key="#work")
	public Integer staTransferCount(WorkSpace work) {
		return mapper.staTransferCount(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanApplyCount", key="#work")
	public Integer staLoanApplyCount(WorkSpace work) {
		return mapper.staLoanApplyCount(work);
	}
	
	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staCloseCount", key="#work")
	public Integer staCloseCount(WorkSpace work) {
		return mapper.staCloseCount(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staEvaFee", key="#work")
	public Map staEvaFee(WorkSpace work) {
		return mapper.staEvaFee(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.getRank", key="#work")
	public Integer getRank(WorkSpace work) {
		return mapper.getRank(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.topRankList", key="#work")
	public List<Rank> topRankList(WorkSpace work) {
		return mapper.topRankList(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.workloadManagerBackoffice", key="#work")
	public List<WorkLoad> workloadManagerBackoffice(WorkSpace work) {
		return mapper.workloadManagerBackoffice(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.workloadConsultantBackoffice", key="#work")
	public List<WorkLoad> workloadConsultantBackoffice(WorkSpace work) {
		return mapper.workloadConsultantBackoffice(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanApply", key="#work")
	public List<LoanStaDetails> staLoanApply(WorkSpace work) {
		return mapper.staLoanApply(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanSign", key="#work")
	public List<LoanStaDetails> staLoanSign(WorkSpace work) {
		return mapper.staLoanSign(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanRelease", key="#work")
	public List<LoanStaDetails> staLoanRelease(WorkSpace work) {
		return mapper.staLoanRelease(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanAgentLoanAmount", key="#work")
	public Double staLoanAgentLoanAmount(WorkSpace work) {
		return mapper.staLoanAgentLoanAmount(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanAgentSignAmount", key="#work")
	public Double staLoanAgentSignAmount(WorkSpace work) {
		return mapper.staLoanAgentSignAmount(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.getLightListGroupByOrg", key="#orgId")
	public List<OrgCount> getLightListGroupByOrg(String orgId) {
		return mapper.getLightListGroupByOrg(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.rLightList", key="#orgId")
	public List<UserLightCount> rLightList(String orgId) {
		return mapper.rLightList(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.topRankList1", key="#work")
	public List<Rank> topRankList1(WorkSpace work) {
		return mapper.topRankList1(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staUnallocateRank", key="#orgId")
	public List<Rank> staUnallocateRank(String orgId) {
		return mapper.staUnallocateRank(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staSignRank", key="#orgId")
	public List<Rank> staSignRank(String orgId) {
		return mapper.staSignRank(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staHouseTranRank", key="#orgId")
	public List<Rank> staHouseTranRank(String orgId) {
		return mapper.staHouseTranRank(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staCpfLoanRank", key="#orgId")
	public List<Rank> staCpfLoanRank(String orgId) {
		return mapper.staCpfLoanRank(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staBusinessOrComLoanRank", key="#orgId")
	public List<Rank> staBusinessOrComLoanRank(String orgId) {
		return mapper.staBusinessOrComLoanRank(orgId);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.listNewLoanAgent", key="#orgId")
	public List<SimpleLoanAgent> listNewLoanAgent(String orgId) {
		List<SimpleLoanAgent> r=mapper.listNewLoanAgent(orgId);
		if(r!=null&&!r.isEmpty()){
			for (SimpleLoanAgent simpleLoanAgent : r) {
				Dict d= uamBasedataService.findDictByTypeAndCode("yu_serv_cat_code_tree", simpleLoanAgent.getLoanSevCode());
				if(d!=null){
					simpleLoanAgent.setLoanSevCode(d.getName());
				}
			}
		}
		
		return mapper.listNewLoanAgent(orgId);
	}
	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staLoanAgentTransferRate", key="#work")
	public Double staLoanAgentTransferRate(WorkSpace work) {
		return mapper.staLoanAgentTransferRate(work);
	}

	@Override
	@Cacheable(value="WorkSpaceServiceImpl.staEvaFeeCount", key="#work")
	public Double staEvaFeeCount(WorkSpace work) {
		return mapper.staEvaFeeCount(work);
	}}
