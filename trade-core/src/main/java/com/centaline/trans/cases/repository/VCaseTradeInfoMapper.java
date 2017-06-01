package com.centaline.trans.cases.repository;

import java.util.Date;

import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface VCaseTradeInfoMapper {

    VCaseTradeInfo queryCaseTradeInfoByCaseCode(String caseCode);
    
	Date selectTransSignSubTime(String caseCode);
	
	Date selectGuohuSubTime(String caseCode);
	
	Date selectGuohuPassTime(String caseCode);
}