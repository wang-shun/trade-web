package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface VCaseTradeInfoMapper {

    VCaseTradeInfo queryCaseTradeInfoByCaseCode(String caseCode);
}