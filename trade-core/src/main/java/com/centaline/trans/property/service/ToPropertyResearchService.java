package com.centaline.trans.property.service;

import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

public interface ToPropertyResearchService {
	int recordProperty(ToPropertyResearchVo vo);

	int insertSelective(ToPropertyResearch record);

	ToPropertyResearch getToPropertyResearchsByPrCode(String caseCode);

	ToPropertyResearch getToPropertyResearchsByPkid(Long pkId);

	boolean hasMapping(String districtCode);

}
