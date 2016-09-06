package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToRcMortgage;

public interface ToRcMortgageMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcMortgage record);

    int insertSelective(ToRcMortgage record);

    ToRcMortgage selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcMortgage record);

    int updateByPrimaryKey(ToRcMortgage record);
}