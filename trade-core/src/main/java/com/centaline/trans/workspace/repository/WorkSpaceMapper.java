package com.centaline.trans.workspace.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.workspace.entity.LoanStaDetails;
import com.centaline.trans.workspace.entity.OrgCount;
import com.centaline.trans.workspace.entity.Rank;
import com.centaline.trans.workspace.entity.SimpleLoanAgent;
import com.centaline.trans.workspace.entity.UserLightCount;
import com.centaline.trans.workspace.entity.WorkLoad;
import com.centaline.trans.workspace.entity.WorkSpace;

@MyBatisRepository
public interface WorkSpaceMapper {
	public List<UserLightCount>rLightList(String orgId);
	public List<OrgCount> getLightListGroupByOrg(String orgId);
	public int countLight(WorkSpace work);

	public Map staLoanAgent(WorkSpace work);

	public Double staLoanAgentLoanAmount(WorkSpace work);

	public Double staLoanAgentSignAmount(WorkSpace work);
	
	public Double staLoanAgentTransferRate(WorkSpace work);

	public Map staEvaFee(WorkSpace work);

	public Integer staSignCount(WorkSpace work);

	public Integer staReceiveCount(WorkSpace work);

	public Integer staTransferCount(WorkSpace work);

	public Integer staCloseCount(WorkSpace work);

	public Integer getRank(WorkSpace work);

	public List<Rank> topRankList(WorkSpace work);
	public List<Rank> topRankList1(WorkSpace work);
	

	public List<WorkLoad> workloadManagerBackoffice(WorkSpace work);

	public List<WorkLoad> workloadConsultantBackoffice(WorkSpace work);

	public List<LoanStaDetails> staLoanApply(WorkSpace work);

	public List<LoanStaDetails> staLoanSign(WorkSpace work);

	public List<LoanStaDetails> staLoanRelease(WorkSpace work);
	public List<Rank> staUnallocateRank(String orgId);
	public List<Rank> staSignRank(String orgId);
	public List<Rank> staHouseTranRank(String orgId);
	public List<Rank> staCpfLoanRank(String orgId);
	public List<Rank> staBusinessOrComLoanRank(String orgId);
	public List<SimpleLoanAgent>listNewLoanAgent(String orgId);
}
