package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.RcRiskControl;
@MyBatisRepository
public interface RcRiskControlMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(RcRiskControl record);

    int insertSelective(RcRiskControl record);

    RcRiskControl selectByPrimaryKey(Long pkid);
    
    List<RcRiskControl> getRiskControlByProperty(RcRiskControl record);

    int updateByPrimaryKeySelective(RcRiskControl record);

    int updateByPrimaryKey(RcRiskControl record);
}