package com.centaline.trans.ransom.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.repository.AddRansomFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.AddRansomFormService;

@Service
@Transactional
public class AddRansomFormServiceImpl implements AddRansomFormService {

	@Autowired
	private AddRansomFormMapper addRansomFormMapper;
	@Autowired
	private RansomMapper ransomMapper;
	@Autowired
	private UamSessionService uamSessionService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Override
	public void addRansomForm(List<ToRansomFormVo> ar) {
		
		try {
			addRansomFormMapper.insertRansomForm(ar);
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

//	@Override
//	public int insert(ToRansomTailinsVo ar) {
//		return addRansomFormMapper.insert(ar);
//	}

}
