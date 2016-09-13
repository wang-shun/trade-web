package com.centaline.trans.eloan.service;

import com.centaline.trans.eloan.vo.ToRcForceRegisterVO;
import com.centaline.trans.eloan.vo.ToRcMortgageCardVO;
import com.centaline.trans.eloan.vo.ToRcMortgageVO;

public interface ToRcMortgageService {
	int saveRcMortgageCard(ToRcMortgageCardVO toRcMortgageCardVO);
	
	ToRcMortgageCardVO getRcMortgageCardInfoByProperty(String riskType,String eloanCode);
	
	ToRcMortgageVO getMortgageByProperty(String riskType, String eloanCode);

	int saveRcMortgage(ToRcMortgageVO toRcMortgageVO);
	
	int saveToRcForceRegister(ToRcForceRegisterVO toRcForceRegisteVO);
}
