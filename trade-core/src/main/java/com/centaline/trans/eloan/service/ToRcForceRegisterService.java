package com.centaline.trans.eloan.service;

import com.centaline.trans.eloan.vo.ToRcForceRegisterVO;

public interface ToRcForceRegisterService {
	ToRcForceRegisterVO getRcForceRegisterByProperty(String mortgageType,String eloanCode);
}
