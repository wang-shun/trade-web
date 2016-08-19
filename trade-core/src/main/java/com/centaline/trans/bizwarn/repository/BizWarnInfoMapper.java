package com.centaline.trans.bizwarn.repository;

import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface BizWarnInfoMapper {
	
	int updateStatusInMortgageSelect(BizWarnInfo bizWarnInfo);
	
	int getAllBizwarnCount();

    int insert(BizWarnInfo bizWarnInfo);

    int insertSelective(BizWarnInfo record);
    
    int updateByCaseCode(BizWarnInfo bizWarnInfo);
    
    int deleteByCaseCode(String caseCode);
    
    BizWarnInfo selectByCaseCode(String caseCode);
    
    int updateStatusByCaseCode(BizWarnInfo bizWarnInfo);

}