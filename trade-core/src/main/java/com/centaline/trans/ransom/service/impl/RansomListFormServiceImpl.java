package com.centaline.trans.ransom.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.RansomPartEnum;
import com.centaline.trans.common.enums.RansomPartOrderEnum;
import com.centaline.trans.ransom.entity.RansomPartOrderVo;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.vo.ToRansomMoneyVo;
import com.centaline.trans.ransom.vo.ToRansomVo;
import com.centaline.trans.ransom.vo.VRansomFinishTaskVo;

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
	public ToRansomMoneyVo getRansomDetailMoneyInfo(String ransomCode) {
		List<ToRansomMoneyVo> moneyVoList = ransomListFormMapper.getRansomDetailMoneyInfo(ransomCode);
		//判断是否存在二抵  count = 1 :存在
		int count = ransomMapper.queryErdi(ransomCode);
		ToRansomMoneyVo moneyVo = new ToRansomMoneyVo();
		
		if(count == 0) {
			moneyVo = moneyVoList.get(0);
		}else {
			BigDecimal repayLoanMoney = new BigDecimal(0);
			for (ToRansomMoneyVo ransomMoneyVo : moneyVoList) {
				BigDecimal borrowerMoney = ransomMoneyVo.getBorrowerMoney();
				BigDecimal interViewMoney = ransomMoneyVo.getInterViewMoney();
				String interest = ransomMoneyVo.getInterest();
				String isEntrust = ransomMoneyVo.getIsEntrust();
				
				BigDecimal loanMoney = ransomMoneyVo.getRepayLoanMoney();
				if(loanMoney == null) {
					loanMoney = null;
				}else repayLoanMoney.add(loanMoney);
				
				
				moneyVo.setRansomCode(ransomMoneyVo.getRansomCode());
				moneyVo.setBorrowerMoney(borrowerMoney);
				moneyVo.setInterViewMoney(interViewMoney);
				moneyVo.setRepayLoanMoney(repayLoanMoney);
				moneyVo.setInterest(interest);
				moneyVo.setIsEntrust(isEntrust);
			}
			
			moneyVo.getRansomCode();
			moneyVo.getBorrowerMoney();
			moneyVo.getInterViewMoney();
			moneyVo.getRepayLoanMoney();
			moneyVo.getInterest();
			moneyVo.getIsEntrust();
			
		}
		
		return moneyVo;
	}

	@Override
	public ToRansomCaseVo getRansomCase(String caseCode, String ransomCode) {
		return ransomListFormMapper.getRansomCase(caseCode, ransomCode);
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
	public List<ToRansomTailinsVo> getTailinsInfoByCaseCode(String caseCode) {
		List<ToRansomTailinsVo> tailinsVo =  ransomMapper.getTailinsInfoByCaseCode(caseCode);
		return tailinsVo;
	}

	@Override
	public List<TgGuestInfo> getGuestInfo(String caseCode) {
		List<TgGuestInfo> guestInfo = ransomListFormMapper.getGuestInfo(caseCode);
		return guestInfo;
	}

	@Override
	public ToRansomCaseVo getRansomCase(String caseCode) {
		ToRansomCaseVo caseVo = ransomListFormMapper.getRansomCase(caseCode, null);
		return caseVo;
	}

	@Override
	public ToRansomFormVo getRansomPlanTimeInfo(String ransomCode) {
		ToRansomFormVo data =new ToRansomFormVo();
		List<ToRansomCaseVo> casePartStatus = ransomListFormMapper.getRansomStatusAndPart(ransomCode);
		List<ToRansomPlanVo> planTimes = ransomListFormMapper.getRansomPlanTime(ransomCode);
		
		data.setRansomStatus(casePartStatus.get(0).getRansomStatus());
		data.setCasePartStatus(casePartStatus);
		data.setPlanTimes(planTimes);
		
		List<RansomPartOrderVo> partOrders = new ArrayList<RansomPartOrderVo>();
		
		for(ToRansomCaseVo vo : casePartStatus) {
			if(vo.getPartCode() != null ) {
				partOrders.add(new RansomPartOrderVo(vo.getPartCode(),RansomPartOrderEnum.getOrder(vo.getPartCode())));
			}
		}
		data.setPartOrders(partOrders);
		int count = ransomMapper.queryErdi(ransomCode);
		//抵押数量
		if(count ==0) {
			data.setDiyaNum(1);
			data.setAllPartCodes(RansomPartEnum.getDiyaOne());
		}else {
			data.setDiyaNum(2);
			data.setAllPartCodes(RansomPartEnum.getDiyaTwo());
			data.setOnePartCodes(RansomPartEnum.getDiyaOne());
			data.setTwoPartCodes(RansomPartEnum.getDiyaTwoTwo());
		}
		
		return data;
	}

	@Override
	public List<String> getRansomPlanCodeInfo(String ransomCode) {
		
		return ransomListFormMapper.getPartCode(ransomCode);
	}
	
	@Override
	public int updateRansomPlanTimeInfo(ToRansomPlanVo ransomPlanVo) {
		return ransomListFormMapper.updateRansomPlanTimeInfoByRansomCode(ransomPlanVo);
	}

	@Override
	public int updateListPlanTimeInfo(List<ToRansomPlanVo> planList) {
		return ransomListFormMapper.updateListPlanTimeInfoByRansomCode(planList);
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
	public List<ToRansomVo> getRansomPlanInfo(String ransomCode) {
		
		try {
			List<ToRansomVo> ransomList = new ArrayList<ToRansomVo>();
			ransomList = ransomListFormMapper.getRansomInfoByRansomCode(ransomCode);
			
			return ransomList;
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
		applyVo.setUpdateUser(user.getId());
		applyVo.setUpdateTime(new Date());
		
		int res = ransomListFormMapper.updateRansomApplyInfoByRansomCode(applyVo);
		return res;
	}

	@Override
	public int updateRansomInterviewInfo(ToRansomVo ransomVo) {

		SessionUser user= uamSessionService.getSessionUser();
		ToRansomSignVo signVo = new ToRansomSignVo();
		
		signVo.setRansomCode(ransomVo.getRansomCode());
		signVo.setSignTime(ransomVo.getInterviewTime());
		signVo.setRemark(ransomVo.getInterviewRemake());
		signVo.setUpdateUser(user.getId());
		signVo.setUpdateTime(new Date());
		
		int res = ransomListFormMapper.updateRansomInterviewInfoByRansomCode(signVo );
		return res;
	}

	@Override
	public int updateRansomRepayInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomMortgageVo mortgageVo = new ToRansomMortgageVo();
		
		mortgageVo.setRansomCode(ransomVo.getRansomCode());
		mortgageVo.setMortgageTime(ransomVo.getRepayTime());
		mortgageVo.setRemark(ransomVo.getRepayRemake());
		mortgageVo.setUpdateUser(user.getId());
		mortgageVo.setUpdateTime(new Date());
		
		int res = ransomListFormMapper.updateRansomRepayInfoByRansomCode(mortgageVo);
		return res;
	}

	@Override
	public int updateRansomCancelInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomCancelVo cancelVo = new ToRansomCancelVo();
		
		cancelVo.setRansomCode(ransomVo.getRansomCode());
		cancelVo.setCancelTime(ransomVo.getCancelTime());
		cancelVo.setRemark(ransomVo.getCancelRemake());
		cancelVo.setUpdateUser(user.getId());
		cancelVo.setUpdateTime(new Date());
		
		int res = ransomListFormMapper.updateRansomCancelInfoByRansomCode(cancelVo);
		return res;
	}

	@Override
	public int updateRansomRedeemInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomPermitVo permitVo = new ToRansomPermitVo();
		
		permitVo.setRansomCode(ransomVo.getRansomCode());
		permitVo.setRedeemTime(ransomVo.getRedeemTime());
		permitVo.setRemark(ransomVo.getRedeemRemake());
		permitVo.setUpdateUser(user.getId());
		permitVo.setUpdateTime(new Date());
		
		int res = ransomListFormMapper.updateRansomRedeemInfoByRansomCode(permitVo);
		return res;
	}

	@Override
	public int insertRansomPlanTimeInfo(ToRansomPlanVo planVo) {
		
		return ransomListFormMapper.insertRansomPlanTimeVo(planVo);
	}

	@Override
	public int insertListRansomPlanTime(List<ToRansomPlanVo> planVo) {

		return ransomListFormMapper.insertListRansomPlanTime(planVo);
	}

	@Override
	public int updateRansomPaymentInfo(ToRansomVo ransomVo) {
		
		SessionUser user= uamSessionService.getSessionUser();
		ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
		
		paymentVo.setRansomCode(ransomVo.getRansomCode());
		paymentVo.setPaymentTime(ransomVo.getPaymentTime());
		paymentVo.setRemark(ransomVo.getRepayRemake());
		paymentVo.setUpdateUser(user.getId());
		paymentVo.setUpdateTime(new Date());
		
		int res = ransomListFormMapper.updateRansomPaymentInfoByRansomCode(paymentVo);
		return res;
	}

	
	@Override
	public VRansomFinishTaskVo getRansomTaskInfoByRansomCode(String ransomCode) {
		
		VRansomFinishTaskVo taskVo = ransomListFormMapper.getRansomTaskInfo(ransomCode);
		
		return taskVo;
	}

	@Override
	public int queryCountRansomsByUserId(String userId) {
		Integer reInt = ransomListFormMapper.queryCountRansomsByUserId(userId);
		return reInt == null ? 0 : reInt;
	}

	@Override
	public int queryCountMonthRansomsByUserId(String userId) {
		Integer reInt = ransomListFormMapper.queryCountMonthRansomsByUserId(userId);
		return reInt == null ? 0 : reInt;
	}

	@Override
	public List getUpdateRansomInfo(String caseCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
