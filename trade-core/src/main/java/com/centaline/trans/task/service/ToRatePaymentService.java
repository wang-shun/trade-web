package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.ToRatePayment;

/**
 * @author wbzhouht
 * 缴税
 */
public interface ToRatePaymentService {
	public boolean saveRatePayment(ToRatePayment toRatePayment);
	public ToRatePayment qureyToRatePayment(String caseCode);
	 
}
