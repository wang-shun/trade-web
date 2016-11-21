package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCloseApplyAudit;

@MyBatisRepository
public interface ToSpvCloseApplyAuditMapper {
    int deleteByPrimaryKey(Long pkid);
    
    int deleteByApplyId(String applyId);

    int insert(ToSpvCloseApplyAudit record);

    int insertSelective(ToSpvCloseApplyAudit record);

    ToSpvCloseApplyAudit selectByPrimaryKey(Long pkid);
    
    List<ToSpvCloseApplyAudit> selectBySpvCloseApplyCode(String spvCloseApplyCode);

    int updateByPrimaryKeySelective(ToSpvCloseApplyAudit record);

    int updateByPrimaryKey(ToSpvCloseApplyAudit record);
}