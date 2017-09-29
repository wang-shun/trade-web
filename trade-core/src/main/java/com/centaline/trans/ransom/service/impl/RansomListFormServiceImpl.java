package com.centaline.trans.ransom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.service.RansomListFormService;

/**
 * 赎楼详情列表
 * @author wbwumf
 *
 */
@Service
@Transactional
public class RansomListFormServiceImpl implements RansomListFormService {

	@Autowired
	private RansomListFormMapper rfm;
	
	@Override
	public int addRansomDetail(ToRansomCaseVo trco) {
		
		return rfm.addRansomDetail(trco);
	}

}
