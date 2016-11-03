package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.ToPurchaseLimitSearch;

public interface ToPurchaseLimitSearchService {

	public boolean saveToPurchaseLimitSearch(ToPurchaseLimitSearch toPurchaseLimitSearch);
	
	public ToPurchaseLimitSearch findToPlsByCaseCode(String caseCode);
}
