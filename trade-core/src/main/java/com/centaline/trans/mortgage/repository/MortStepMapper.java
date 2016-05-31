package com.centaline.trans.mortgage.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.MortStep;

@MyBatisRepository
public interface MortStepMapper {
    int deleteByPrimaryKey(Long pkid);

    int insertSelective(MortStep record);

    MortStep selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MortStep record);
    
    MortStep findMortStep(MortStep mortStep);
    
    
    // 根据casecode 去删除
    int deleteCaseCode(String caseCode);
    
    // 根据 caseCode 到MORT_STEP 表中去查询是否存在记录
    int isExistCaseCode(String caseCode);
    
    List<MortStep>listByCaseCode(String caseCode);
    
}