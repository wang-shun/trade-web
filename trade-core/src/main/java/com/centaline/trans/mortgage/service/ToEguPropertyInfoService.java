package com.centaline.trans.mortgage.service;

import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;

public interface ToEguPropertyInfoService {

	ToEguPropertyInfo saveToEguPropertyInfo(ToEguPropertyInfo toEguPropertyInfo);
	
	ToEguPropertyInfo findByEvaCode(String evaCode);
}
