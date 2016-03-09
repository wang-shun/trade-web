package com.centaline.trans.common.repository;

import java.util.List;
import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.vo.OrgVO;

@MyBatisRepository
public interface OrgMapper {
	
	List<OrgVO> getOrgListByDept(String dept);
	
}
