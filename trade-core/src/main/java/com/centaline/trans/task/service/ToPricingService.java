package com.centaline.trans.task.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.task.entity.ToPricing;

public interface ToPricingService {
	
	public boolean saveToPricing(ToPricing toPricing);
	
	public ToPricing qureyPricing(String caseCode);

	AjaxResponse saveAndSubmitPricing(ToPricing toPricing,String taskId,String processInstanceId);

}
