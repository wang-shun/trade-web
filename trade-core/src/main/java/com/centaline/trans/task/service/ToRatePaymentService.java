package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.ToRatePayment;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wbzhouht
 * 缴税
 */
public interface ToRatePaymentService {
	public boolean saveRatePayment(ToRatePayment toRatePayment);
	public ToRatePayment qureyToRatePayment(String caseCode);
	public Boolean sumbitRatePayment (HttpServletRequest request,ToRatePayment toRatePayment, String taskId, String processInstanceId)throws Exception;
}
