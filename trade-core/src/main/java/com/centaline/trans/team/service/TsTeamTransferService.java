package com.centaline.trans.team.service;

import com.centaline.trans.team.entity.TsTeamTransfer;

public interface TsTeamTransferService {
	
	 TsTeamTransfer getTsTeamTransferByProperty(TsTeamTransfer record);
	 
	 int updateByPrimaryKeySelective(TsTeamTransfer record);
	 
	 int insertSelective(TsTeamTransfer record);

}
