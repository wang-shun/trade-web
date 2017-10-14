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
	private RansomListFormMapper ransomListFormMapper;
	
	@Override
	public int addRansomDetail(ToRansomCaseVo trco) {
		
		return ransomListFormMapper.addRansomDetail(trco);
	}

	@Override
	public ToRansomCaseVo getRansomCase(String caseCode) {
		return ransomListFormMapper.getRansomCase(caseCode);
	}

	@Override
	public int updateRansomDiscountinue(ToRansomCaseVo ransomCaseVo) {
		
		return ransomListFormMapper.updateRansomDiscountinue(ransomCaseVo);
	}

}
