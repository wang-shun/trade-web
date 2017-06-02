package com.centaline.trans.mgr.service;

import com.centaline.trans.mgr.entity.ToSupDocu;

public interface ToSupDocuService {

	void saveToSupDocu(ToSupDocu toSupDocu);
	
	void updateToSupDocu(ToSupDocu toSupDocu);
	
	ToSupDocu findByCaseCode(String caseCode);
}
