package com.centaline.trans.workspace.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public int countLight(WorkSpace work) {
		return mapper.countLight(work);
	}

	@Override
	public Map staLoanAgent(WorkSpace workSpace) {
		return mapper.staLoanAgent(workSpace);
	}

	@Override
	public Integer staSignCount(WorkSpace work) {
		return mapper.staSignCount(work);
	}

	@Override
	public Integer staReceiveCount(WorkSpace work) {
		return mapper.staReceiveCount(work);
	}

	@Override
	public Integer staTransferCount(WorkSpace work) {
		return mapper.staTransferCount(work);
	}

	@Override
	public Integer staCloseCount(WorkSpace work) {
		return mapper.staCloseCount(work);
	}

	@Override
	public Map staEvaFee(WorkSpace work) {
		return mapper.staEvaFee(work);
	}

	@Override
	public Integer getRank(WorkSpace work) {
		return mapper.getRank(work);
	}

	@Override
	public List<Rank> topRankList(WorkSpace work) {
		return mapper.topRankList(work);
	}

	@Override
	public List<WorkLoad> workloadManagerBackoffice(WorkSpace work) {
		return mapper.workloadManagerBackoffice(work);
	}

	@Override
	public List<WorkLoad> workloadConsultantBackoffice(WorkSpace work) {
		return mapper.workloadConsultantBackoffice(work);
	}

	@Override
	public List<LoanStaDetails> staLoanApply(WorkSpace work) {
		return mapper.staLoanApply(work);
	}

	@Override
	public List<LoanStaDetails> staLoanSign(WorkSpace work) {
		return mapper.staLoanSign(work);
	}

	@Override
	public List<LoanStaDetails> staLoanRelease(WorkSpace work) {
		return mapper.staLoanRelease(work);
	}

	@Override
	public Double staLoanAgentLoanAmount(WorkSpace work) {
		return mapper.staLoanAgentLoanAmount(work);
	}

	@Override
	public Double staLoanAgentSignAmount(WorkSpace work) {
		return mapper.staLoanAgentSignAmount(work);
	}

	@Override
	public List<OrgCount> getLightListGroupByOrg(String orgId) {
		return mapper.getLightListGroupByOrg(orgId);
	}

	@Override
	public List<UserLightCount> rLightList(String orgId) {
		return mapper.rLightList(orgId);
	}

	@Override
	public List<Rank> topRankList1(WorkSpace work) {
		return mapper.topRankList1(work);
	}

	@Override
	public List<Rank> staUnallocateRank(String orgId) {
		return mapper.staUnallocateRank(orgId);
	}

	@Override
	public List<Rank> staSignRank(String orgId) {
		return mapper.staSignRank(orgId);
	}

	@Override
	public List<Rank> staHouseTranRank(String orgId) {
		return mapper.staHouseTranRank(orgId);
	}

	@Override
	public List<Rank> staCpfLoanRank(String orgId) {
		return mapper.staCpfLoanRank(orgId);
	}

	@Override
	public List<Rank> staBusinessOrComLoanRank(String orgId) {
		return mapper.staBusinessOrComLoanRank(orgId);
	}

	@Override
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
}
