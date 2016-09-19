package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcMortgageInfo;
@MyBatisRepository
public interface ToRcMortgageInfoMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcMortgageInfo record);

    int insertSelective(ToRcMortgageInfo record);

    ToRcMortgageInfo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcMortgageInfo record);

    int updateByPrimaryKey(ToRcMortgageInfo record);
    
    //金融编号和风控类型获取抵押物品
    List<ToRcMortgageInfo> getToRcMortgageInfoByProperty(RcRiskControl rcRiskControl);
    
    int deleteToRcMortgageInfoByRcId(Long riskControlId);
    
}