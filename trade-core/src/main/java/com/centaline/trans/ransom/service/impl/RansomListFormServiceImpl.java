package com.centaline.trans.ransom.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.vo.ToRansomVo;
import com.centaline.trans.utils.DateUtil;

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
	@Autowired
	private UamSessionService uamSessionService;
	
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

	@Override
	public boolean updateRansomCaseInfo(ToRansomCaseVo caseVo) {
		
		return ransomListFormMapper.updateRansomCaseByRansomCode(caseVo);
	}

	@Override
	public boolean updateRansomTailinsInfo(ToRansomTailinsVo tailinsVo) {
		
		return ransomListFormMapper.updateRansomTailinsByRansomCode(tailinsVo);
	}

	@Override
	public ToRansomVo getRansomPlanInfo(String ransomCode) {
		
		try {
			ToRansomVo ransomVo = new ToRansomVo();
			ransomVo = ransomListFormMapper.getRansomInfoByRansomCode(ransomCode);
			
			return ransomVo;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	@Override
	public int updateRansomApplyInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomApplyVo applyVo = new ToRansomApplyVo();
		
		applyVo.setRansomCode(ransomVo.getRansomCode());
		applyVo.setApplyTime(ransomVo.getApplyTime());
		applyVo.setRemark(ransomVo.getApplyRemake());
		applyVo.setUpdateUser(user.getUsername());
		applyVo.setUpdateTime(new Date());
		
		return ransomListFormMapper.updateRansomApplyInfoByRansomCode(applyVo);
	}

	@Override
	public int updateRansomInterviewInfo(ToRansomVo ransomVo) {

		SessionUser user= uamSessionService.getSessionUser();
		ToRansomSignVo signVo = new ToRansomSignVo();
		
		signVo.setRansomCode(ransomVo.getRansomCode());
		signVo.setSignTime(ransomVo.getInterviewTime());
		signVo.setRemark(ransomVo.getInterviewRemake());
		signVo.setUpdateUser(user.getUsername());
		signVo.setUpdateTime(new Date());
		
		return ransomListFormMapper.updateRansomInterviewInfoByRansomCode(signVo );
	}

	@Override
	public int updateRansomRepayInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomMortgageVo mortgageVo = new ToRansomMortgageVo();
		
		mortgageVo.setRansomCode(ransomVo.getRansomCode());
		mortgageVo.setMortgageTime(ransomVo.getRepayTime());
		mortgageVo.setRemark(ransomVo.getRepayRemake());
		mortgageVo.setUpdateUser(user.getUsername());
		mortgageVo.setUpdateTime(new Date());
		
		return ransomListFormMapper.updateRansomRepayInfoByRansomCode(mortgageVo);
	}

	@Override
	public int updateRansomCancelInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomCancelVo cancelVo = new ToRansomCancelVo();
		
		cancelVo.setRansomCode(ransomVo.getRansomCode());
		cancelVo.setCancelTime(ransomVo.getCancelTime());
		cancelVo.setRemark(ransomVo.getCancelRemake());
		cancelVo.setUpdateUser(user.getUsername());
		cancelVo.setUpdateTime(new Date());
		
		return ransomListFormMapper.updateRansomCancelInfoByRansomCode(cancelVo);
	}

	@Override
	public int updateRansomRedeemInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomPermitVo permitVo = new ToRansomPermitVo();
		
		permitVo.setRansomCode(ransomVo.getRansomCode());
		permitVo.setRedeemTime(ransomVo.getRedeemTime());
		permitVo.setRemark(ransomVo.getRedeemRemake());
		permitVo.setUpdateUser(user.getUsername());
		permitVo.setUpdateTime(new Date());
		
		return ransomListFormMapper.updateRansomRedeemInfoByRansomCode(permitVo);
	}

	@Override
	public int updateRansomPaymentInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
		
		paymentVo.setRansomCode(ransomVo.getRansomCode());
		paymentVo.setPaymentTime(ransomVo.getPaymentTime());
		paymentVo.setRemark(ransomVo.getRepayRemake());
		paymentVo.setUpdateUser(user.getUsername());
		paymentVo.setUpdateTime(new Date());
		
		return ransomListFormMapper.updateRansomPaymentInfoByRansomCode(paymentVo);
	}

}
