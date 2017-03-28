package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcMortgage;
@MyBatisRepository
public interface ToRcMortgageMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcMortgage record);

    int insertSelective(ToRcMortgage record);

    ToRcMortgage selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcMortgage record);
    
    int updateMortgageByRcId(ToRcMortgage record);

    int updateByPrimaryKey(ToRcMortgage record);
    
    List<ToRcMortgage> getMortgageByProperty(RcRiskControl rcRiskControl);
}