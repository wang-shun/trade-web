package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.TsPrResearchMap;



public interface TsPrResearchMapService {
	
	void saveTsPrResearchMap(TsPrResearchMap tsPrResearchMap);

	TsPrResearchMap findById(Long pkid);

	void deleteTsPrResearchMap(Long pkid);
	
}