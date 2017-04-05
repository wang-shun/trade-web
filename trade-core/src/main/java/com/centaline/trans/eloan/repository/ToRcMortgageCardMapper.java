package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcMortgageCard;
@MyBatisRepository
public interface ToRcMortgageCardMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcMortgageCard record);

    int insertSelective(ToRcMortgageCard record);

    ToRcMortgageCard selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcMortgageCard record);
    
    int updateMortgageCardByRcId(ToRcMortgageCard record);

    int updateByPrimaryKey(ToRcMortgageCard record);
    
    List<ToRcMortgageCard> getMortgageCardByProperty(RcRiskControl rcRiskControl);
}