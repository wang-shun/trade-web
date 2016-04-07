package com.centaline.trans.task.service;

import java.util.List;

import com.centaline.trans.task.entity.TsPrResearchMap;



public interface TsPrResearchMapService {
	
	void saveTsPrResearchMap(TsPrResearchMap tsPrResearchMap);

	TsPrResearchMap findById(Long pkid);

	void deleteTsPrResearchMap(Long pkid);
	
	List<TsPrResearchMap> getTsPrResearchMapByProperty(TsPrResearchMap tsPrResearchMap);
	
}