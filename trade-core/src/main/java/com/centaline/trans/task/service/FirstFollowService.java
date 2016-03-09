package com.centaline.trans.task.service;

import com.centaline.trans.task.vo.FirstFollowVO;

public interface FirstFollowService {

	public boolean saveFirstFollow(FirstFollowVO firstFollowVO);
	
	public FirstFollowVO queryFirstFollow(String caseCode);
	
	int isExistCasecode(String caseCode);
}
