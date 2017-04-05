package com.centaline.trans.common.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.common.repository.OrgMapper;
import com.centaline.trans.common.service.OrgService;
import com.centaline.trans.common.vo.OrgVO;

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	OrgMapper orgMapper;
	
	@Override
	public List<OrgVO> getOrgListByDept(String dept) {
		return orgMapper.getOrgListByDept(dept);
	} 

}
