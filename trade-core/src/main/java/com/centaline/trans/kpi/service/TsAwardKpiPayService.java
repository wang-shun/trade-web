package com.centaline.trans.kpi.service;

import java.util.List;

import com.centaline.trans.kpi.entity.TsAwardKpiPay;

public interface TsAwardKpiPayService {
	
    List<TsAwardKpiPay> getTsAwardKpiPayByProperty(TsAwardKpiPay record);
    
    int updateTsAwardKpiPayStatus(TsAwardKpiPay record);
}
