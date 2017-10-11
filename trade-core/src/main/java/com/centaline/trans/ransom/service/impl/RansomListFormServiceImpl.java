package com.centaline.trans.ransom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomListFormService;

/**
 * 赎楼详情列表
 * @author wbwumf
 *
 */
@Service
@Transactional
public class RansomListFormServiceImpl implements RansomListFormService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RansomListFormMapper ransomListFormMapper;
	@Autowired
	private RansomMapper ransomMapper;
	
	
	@Override
	public int addRansomDetail(ToRansomCaseVo trco) {
		
		return ransomListFormMapper.addRansomDetail(trco);
	}

	@Override
	public ToRansomCaseVo getRansomCase(String caseCode) {
		return ransomListFormMapper.getRansomCase(caseCode);
	}

	@Override
	public int updateRansomDiscountinue(String caseCode) {
		
		return ransomListFormMapper.updateRansomDiscountinue(caseCode);
	}

	@Override
	public List getUpdateRansomInfo(String caseCode) {
		List list = new ArrayList();
		try {
			ToRansomTailinsVo tailinsVo =  ransomMapper.getTailinsInfoByCaseCode(caseCode);
			
			List<TgGuestInfo> guestInfo = ransomListFormMapper.getGuestInfo(caseCode);
			
			ToRansomCaseVo caseVo = ransomListFormMapper.getRansomCase(caseCode);
			String finOrgCode = tailinsVo.getFinOrgCode();
			TsFinOrg finOrg = ransomListFormMapper.getTsFinOrgIinfo(finOrgCode);
			
			list.add(tailinsVo);
			list.add(guestInfo);
			list.add(caseVo);
			list.add(finOrg);
			
			return list;
		} catch (Exception e) {
			list.add("数据信息查询错误！");
			logger.error("",e);
			return list;
		}
	}

	
}
