package com.centaline.trans.award.service;

import java.util.List;

import com.centaline.trans.award.entity.TsAwardKpiPay;

public interface TsAwardKpiPayService {
	
    List<TsAwardKpiPay> getTsAwardKpiPayByProperty(TsAwardKpiPay record);
    
    int updateTsAwardKpiPayStatus(TsAwardKpiPay record);
}
