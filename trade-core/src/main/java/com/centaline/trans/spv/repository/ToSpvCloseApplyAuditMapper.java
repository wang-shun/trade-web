package com.centaline.trans.spv.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCloseApplyAudit;

@MyBatisRepository
public interface ToSpvCloseApplyAuditMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCloseApplyAudit record);

    int insertSelective(ToSpvCloseApplyAudit record);

    ToSpvCloseApplyAudit selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvCloseApplyAudit record);

    int updateByPrimaryKey(ToSpvCloseApplyAudit record);
}