package com.centaline.trans.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.team.entity.TsTeamTransfer;
import com.centaline.trans.team.repository.TsTeamScopeMapper;
import com.centaline.trans.team.repository.TsTeamTransferMapper;
import com.centaline.trans.team.service.TsTeamTransferService;

@Service
public class TsTeamTransferServiceImpl implements TsTeamTransferService {
	
	@Autowired
	private TsTeamTransferMapper tsTeamTransferMapper;

	@Override
	public TsTeamTransfer getTsTeamTransferByProperty(TsTeamTransfer record) {
		return tsTeamTransferMapper.getTsTeamTransferByProperty(record);
	}

	@Override
	public int updateByPrimaryKeySelective(TsTeamTransfer record) {
		return tsTeamTransferMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertSelective(TsTeamTransfer record) {
		return tsTeamTransferMapper.insertSelective(record);
	}

}
