package com.centaline.trans.task.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.task.entity.ToTax;

public interface ToTaxService {

	public boolean saveToTax(ToTax toTax);
	
	public ToTax findToTaxByCaseCode(String caseCode);

	AjaxResponse saveAndSubmitTax(ToTax toTax, String taskId, String processInstanceId,String partCode);
}
