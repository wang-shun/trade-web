package com.centaline.trans.team.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamTransfer;

@MyBatisRepository
public interface TsTeamTransferMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTeamTransfer record);

    int insertSelective(TsTeamTransfer record);

    TsTeamTransfer selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTeamTransfer record);

    int updateByPrimaryKey(TsTeamTransfer record);
    
    TsTeamTransfer getTsTeamTransferByProperty(TsTeamTransfer record);
}