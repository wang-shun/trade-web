package com.centaline.trans.property.service;

import java.util.List;

import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

public interface ToPropertyResearchService {
	int recordProperty(ToPropertyResearchVo vo);

	int insertSelective(ToPropertyResearch record);

	ToPropertyResearch getToPropertyResearchsByPrCode(String caseCode);

	ToPropertyResearch getToPropertyResearchsByPkid(Long pkId);

	boolean hasMapping(String districtCode);

	List<User> getZLList(String disId);
	
	String getEnvironment();
}
