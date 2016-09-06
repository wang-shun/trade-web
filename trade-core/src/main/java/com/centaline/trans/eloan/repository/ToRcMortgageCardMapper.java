package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToRcMortgageCard;

public interface ToRcMortgageCardMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcMortgageCard record);

    int insertSelective(ToRcMortgageCard record);

    ToRcMortgageCard selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcMortgageCard record);

    int updateByPrimaryKey(ToRcMortgageCard record);
}