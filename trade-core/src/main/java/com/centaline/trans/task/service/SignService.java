package com.centaline.trans.task.service;

import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.vo.TransSignVO;



public interface SignService {

	public Boolean insertGuestInfo(TransSignVO transSignVO);
	
	public TransSignVO qureyGuestInfo(String caseCode);
	
	ToSign findToSignByCaseCode(String caseCode);

	Result2 submitSign(TransSignVO transSignVO);
	
}
