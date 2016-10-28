package com.centaline.trans.common.service;

import java.util.List;
import com.centaline.trans.common.vo.OrgVO;

public interface OrgService {
	
	List<OrgVO> getOrgListByDept(String dept);

}
