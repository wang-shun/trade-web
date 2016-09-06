package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToRcMortgageInfo;

public interface ToRcMortgageInfoMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcMortgageInfo record);

    int insertSelective(ToRcMortgageInfo record);

    ToRcMortgageInfo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcMortgageInfo record);

    int updateByPrimaryKey(ToRcMortgageInfo record);
}