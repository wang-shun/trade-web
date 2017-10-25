package com.centaline.trans.ransom.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;
import com.centaline.trans.ransom.repository.RansomChangeMapper;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomChangeService;

/**
 * 赎楼修改implement
 * @author wbwumf
 *
 *2017年10月25日
 */
@Service
@Transactional
public class RansomChangeServiceImpl implements RansomChangeService {

	@Autowired
	private RansomMapper ransomMapper;
	@Autowired
	private RansomChangeMapper ransomChangeMapper;
	@Autowired
	private RansomListFormMapper ransomListFormMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	
	@Override
	public void updateRansomApplyInfo(ToRansomApplyVo applyVo) {
		
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomSubmitVo submitVo = new ToRansomSubmitVo();
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		
		applyVo.setUpdateTime(new Date());
		applyVo.setUpdateUser(user.getId());
		applyVo.setRansomCode(applyVo.getRansomCode());
		
		submitVo.setApplyOrgCode(applyVo.getApplyOrgCode());
		submitVo.setUpdateUser(user.getId());
		submitVo.setRansomCode(applyVo.getRansomCode());
		
		planVo.setPartCode(applyVo.getPartCode());
		planVo.setEstPartTime(applyVo.getPlanSignTime());
		planVo.setUpdateUser(user.getId());
		planVo.setRansomCode(applyVo.getRansomCode());
		
		ransomChangeMapper.updateRansomApplyInfo(applyVo);
		ransomMapper.updateRansomInfo(submitVo );
		ransomMapper.updateRansomPlanTime(planVo );
	}

	@Override
	public void updateRansomSignInfo(ToRansomSignVo signVo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		
		planVo.setPartCode(signVo.getPartCode());
		planVo.setEstPartTime(signVo.getPlanPayloanTime());
		planVo.setUpdateUser(user.getId());
		planVo.setRansomCode(signVo.getRansomCode());
		
		signVo.setRansomCode(signVo.getRansomCode());
		signVo.setPlanPayloanTime(signVo.getPlanPayloanTime());
		signVo.setSignMoney((signVo.getSignMoney()).multiply(new BigDecimal(10000.00)));
		signVo.setInterest(signVo.getInterest());
		signVo.setIsEntrust(signVo.getIsEntrust());
		signVo.setUpdateTime(new Date());
		signVo.setUpdateUser(user.getId());
		
		ransomChangeMapper.updateRansomSignInfo(signVo);
		ransomMapper.updateRansomPlanTime(planVo );
	}

	@Override
	public void updatePayloanInfo(ToRansomSubmitVo subVo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomMortgageVo mortgageVo = new ToRansomMortgageVo();
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		List<ToRansomPlanVo> list = new ArrayList<ToRansomPlanVo>();
		
		mortgageVo.setRansomCode(subVo.getRansomCode());
		mortgageVo.setMortgageTime(subVo.getMortgageTime());
		mortgageVo.setRepayLoanMoney((subVo.getRepayLoanMoney()).multiply(new BigDecimal(10000.00)));
		mortgageVo.setUpdateTime(new Date());
		mortgageVo.setUpdateUser(user.getId());
		
		planVo.setRansomCode(subVo.getRansomCode());
		planVo.setEstPartTime(subVo.getCancelDiyaTime());
		planVo.setUpdateTime(new Date());
		planVo.setUpdateUser(user.getId());
		
		ransomChangeMapper.updatePayloanInfo(mortgageVo);
		ransomListFormMapper.updateListPlanTimeInfoByRansomCode(list );
	}

}
