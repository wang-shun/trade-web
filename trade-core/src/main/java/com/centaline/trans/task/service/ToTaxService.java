package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.ToTax;

public interface ToTaxService {

	public boolean saveToTax(ToTax toTax);
	
	public ToTax findToTaxByCaseCode(String caseCode);
}
