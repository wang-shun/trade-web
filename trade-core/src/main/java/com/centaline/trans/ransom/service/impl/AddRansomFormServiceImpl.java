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
			SessionUser user= uamSessionService.getSessionUser();
			String ransomCode = ar.get(0).getRansomCode();
			Date date = new Date();
			String userName = user.getUsername();
			
			ToRansomApplyVo applyVo = new ToRansomApplyVo();
			applyVo.setRansomCode(ransomCode);
			applyVo.setCreateTime(date);
			applyVo.setCreateUser(userName);
			applyVo.setUpdateTime(date);
			applyVo.setUpdateUser(userName);
			
			ToRansomSignVo signVo = new ToRansomSignVo();
			signVo.setRansomCode(ransomCode);
			signVo.setCreateUser(userName);
			signVo.setCreateTime(date);
			signVo.setUpdateUser(userName);
			signVo.setUpdateTime(date);
			
			ToRansomMortgageVo mortgageVo = new ToRansomMortgageVo();
			mortgageVo.setRansomCode(ransomCode);
			mortgageVo.setCreateUser(userName);
			mortgageVo.setCreateTime(date);
			mortgageVo.setUpdateUser(userName);
			mortgageVo.setUpdateTime(date);
			
			ToRansomCancelVo cancelVo = new ToRansomCancelVo();
			cancelVo.setRansomCode(ransomCode);
			cancelVo.setCreateUser(userName);
			cancelVo.setCancelTime(date);
			cancelVo.setUpdateUser(userName);
			cancelVo.setUpdateTime(date);
			
			ToRansomPermitVo permitVo = new ToRansomPermitVo();
			permitVo.setRansomCode(ransomCode);
			permitVo.setCreateUser(userName);
			permitVo.setCreateTime(date);
			permitVo.setUpdateUser(userName);
			permitVo.setUpdateTime(date);
			
			ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
			paymentVo.setRansomCode(ransomCode);
			paymentVo.setCreateTime(date);
			paymentVo.setCreateUser(userName);
			paymentVo.setUpdateTime(date);
			paymentVo.setUpdateUser(userName);
			
			ransomMapper.insertRansomApply(applyVo );
			ransomMapper.insertRansomSign(signVo);
			ransomMapper.insertRansomMortgage(mortgageVo);
			ransomMapper.insertRansomCancel(cancelVo);
			ransomMapper.insertRansomPermit(permitVo);
			ransomMapper.insertRansomPayment(paymentVo);
			addRansomFormMapper.insertRansomForm(ar);
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

	@Override
	public int insert(ToRansomTailinsVo ar) {
		return addRansomFormMapper.insert(ar);
	}

}
