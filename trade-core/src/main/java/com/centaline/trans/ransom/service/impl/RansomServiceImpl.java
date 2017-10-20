package com.centaline.trans.ransom.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.RansomDiyaEnum;
import com.centaline.trans.common.enums.RansomPartEnum;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.ransom.vo.ToRansomLinkVo;

/**
 * 赎楼service实现
 * @author wbcaiyx
 *
 */
@Service
@Transactional
public class RansomServiceImpl implements RansomService{

	@Autowired
	private RansomMapper ransomMapper;
	@Autowired
	private RansomListFormMapper ransomListFormMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	
	@Override
	public ToRansomDetailVo getRansomDetail(String ransomCode) {
		ToRansomDetailVo detailVo = ransomMapper.getRansomDetailInfoByCaseCode(ransomCode);
		
		if(detailVo != null){
			User financ = uamUserOrgService.getUserById(detailVo.getFinancial());
			if(financ !=null){
				detailVo.setFinancial(financ.getRealName());
				detailVo.setFinancialTel(financ.getMobile());
			}else{
				detailVo.setFinancial(null);
			}
			User user = uamUserOrgService.getUserById(detailVo.getLeadingProcessId());
			detailVo.setLeadingProcessName(user.getRealName()); //经办人
		}
		return detailVo;
	}
	
	@Override
	public ToRansomDetailVo getRansomDetail(String caseCode, String ransomCode) {
		ToRansomDetailVo detailVo = ransomMapper.getRansomDetailInfoByCodes(caseCode, ransomCode);
		if(detailVo != null){
			User financ = uamUserOrgService.getUserById(detailVo.getFinancial());
			if(financ !=null){
				detailVo.setFinancial(financ.getRealName());
				detailVo.setFinancialTel(financ.getMobile());
			}else{
				detailVo.setFinancial(null);
			}
			User user = uamUserOrgService.getUserById(detailVo.getLeadingProcessId());
			detailVo.setLeadingProcessName(user.getRealName()); //经办人
		}
		return detailVo;
	}

	@Override
	public List<ToRansomPlanVo> getPartPlanTime(String ransomCode) {
		List<ToRansomPlanVo> plans = ransomMapper.getRansomPlanByCode(ransomCode);
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		
		ToRansomPlanVo createPlan = new ToRansomPlanVo();
		createPlan.setPartName("受理");
		if(plans != null && plans.size() > 0){
			createPlan.setEstPartTimeStr(format.format(plans.get(0).getCreateTime()));
			createPlan.setCompleteTimeStr(format.format(plans.get(0).getCreateTime()));
		}
		plans.add(createPlan);
	
		for(ToRansomPlanVo plan : plans){
			String partName = RansomPartEnum.getName(plan.getPartCode());
			String table =RansomPartEnum.getTable(plan.getPartCode());
			String cloumn = RansomPartEnum.getCloumn(plan.getPartCode());
			Map<String,Date> data = ransomMapper.getCompleteTimeByCode(table, ransomCode, cloumn);
			plan.setPartName(partName);
			plan.setEstPartTimeStr(plan.getEstPartTime() == null ? null : format.format(plan.getEstPartTime()));
			plan.setCompleteTimeStr(data.get("completeTime") == null ? null : format.format(data.get("completeTime")));
		}
		return plans;
	}

	@Override
	public int updateRansomApply(ToRansomSubmitVo submitVo) {
		
		SessionUser user = uamSessionService.getSessionUser();
		
		submitVo.setUpdateUser(user.getId());
		ToRansomApplyVo applyVo = new ToRansomApplyVo();
		applyVo.setRansomCode(submitVo.getRansomCode());
		applyVo.setPartCode(RansomPartEnum.APPLY.getCode());
		applyVo.setApplyUser(submitVo.getBorrowName());
		applyVo.setApplyTime(submitVo.getApplyTime());
		applyVo.setApplyOrgCode(submitVo.getApplyOrgCode());
		applyVo.setLoanOfficer(submitVo.getLoanOfficer());
		applyVo.setIsApply("1");
		applyVo.setUpdateUser(user.getId());
		applyVo.setCreateUser(user.getId());
		//申请数据插入
		int applyCount = ransomMapper.insertRansomApply(applyVo);

		if(applyCount ==0){
			throw new BusinessException("申请数据插入失败!");
		}
		//赎楼案件数据更新
		int count = ransomMapper.updateRansomInfo(submitVo);
		
		if(count ==0){
			throw new BusinessException("赎楼数据更新失败!");
		}
		//面签计划时间插入
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		planVo.setRansomCode(submitVo.getRansomCode());
		planVo.setCreateUser(user.getId());
		planVo.setUpdateUser(user.getId());
		planVo.setEstPartTime(submitVo.getPlanSignTime());
		planVo.setPartCode(RansomDiyaEnum.SING.getPart());
		//检查是否已有计划时间
		Integer isExistPlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), RansomDiyaEnum.SING.getPart());
		int signPlan = 0 ;
		if(isExistPlan != null &&isExistPlan > 0){
			signPlan = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			signPlan = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(signPlan ==0){
			throw new BusinessException("面签计划时间更新失败!");
		}
		return applyCount;
	}

	
	@Override
	public int queryErdiByRansomCode(String ransomCode) {
		
		return ransomMapper.queryErdi(ransomCode);
	}
	
	@Override
	public int updateRansomSign(ToRansomSubmitVo submitVo,int count) {
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomSignVo signVo = new ToRansomSignVo();
		signVo.setRansomCode(submitVo.getRansomCode());
		signVo.setPartCode(RansomPartEnum.SIGN.getCode());
		signVo.setSignTime(submitVo.getSignTime());
		signVo.setSignMoney(submitVo.getSignMoney() !=null ? submitVo.getSignMoney().multiply(new BigDecimal(10000)):null);
		signVo.setInterest(submitVo.getInterest());
		signVo.setIsEntrust(submitVo.getIsEntrust());
		signVo.setUpdateUser(user.getId());
		signVo.setCreateUser(user.getId());
		int result = ransomMapper.insertRansomSign(signVo);
		
		if(result ==0){
			throw new BusinessException("面签数据插入失败!");
		}
		//插入陪同还贷计划时间
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		planVo.setRansomCode(submitVo.getRansomCode());
		planVo.setCreateUser(user.getId());
		planVo.setUpdateUser(user.getId());
		planVo.setEstPartTime(submitVo.getPlanMortgageTime());
		planVo.setPartCode(RansomDiyaEnum.PAYLOAN_ONE.getPart());
		
		//检查是否已有计划时间
		Integer mort1Plan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), RansomDiyaEnum.PAYLOAN_ONE.getPart());
		int mort1 = 0 ;
		if(mort1Plan != null && mort1Plan > 0){
			mort1 = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			mort1 = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(mort1 ==0){
			throw new BusinessException("陪同还贷计划时间更新失败!");
		}
		
		
		if(count>0){
			planVo.setPartCode(RansomDiyaEnum.PAYLOAN_TWO.getPart());
			
			Integer mort2Plan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), RansomDiyaEnum.PAYLOAN_TWO.getPart());
			int mort2 = 0;
			if(mort2Plan != null && mort2Plan >0){
				mort2 = ransomMapper.updateRansomPlanTime(planVo);
			}else{
				mort2 = ransomMapper.insertRansomPlanTime(planVo);
			}
			if(mort2 ==0){
				throw new BusinessException("陪同还贷计划时间更新失败!");
			}
		}

		return result;
	}

	
	@Override
	public int updateRansomMortgage(ToRansomSubmitVo submitVo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomMortgageVo mortVo = new ToRansomMortgageVo();
		mortVo.setRansomCode(submitVo.getRansomCode());
		mortVo.setMortgageTime(submitVo.getMortgageTime());
		mortVo.setRepayLoanMoney(submitVo.getRepayLoanMoney()==null?null:submitVo.getRepayLoanMoney().multiply(new BigDecimal(10000)));
		mortVo.setDiyaType(submitVo.getDiyaType().toString());
		mortVo.setUpdateUser(user.getId());
		mortVo.setCreateUser(user.getId());
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			mortVo.setPartCode(RansomDiyaEnum.PAYLOAN_ONE.getPart());
		}else if(RansomDiyaEnum.PAYLOAN_TWO.getCode().equals(submitVo.getDiyaType().toString())){
			mortVo.setPartCode(RansomDiyaEnum.PAYLOAN_TWO.getPart());
		}
		int result = ransomMapper.insertRansomMortgage(mortVo);
		if(result ==0){
			throw new BusinessException("陪同还贷数据插入失败!");
		}
		
		//时间计划
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		planVo.setRansomCode(submitVo.getRansomCode());
		planVo.setCreateUser(user.getId());
		planVo.setUpdateUser(user.getId());
		//注销抵押计划时间
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.CANCELDIYA_ONE.getPart());
		}else if(RansomDiyaEnum.PAYLOAN_TWO.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.CANCELDIYA_TWO.getPart());
		}
		planVo.setEstPartTime(submitVo.getPlanCancelTime());
		
		Integer cancelPlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), planVo.getPartCode());
		int cancel = 0;
		if(cancelPlan != null && cancelPlan > 0){
			cancel = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			cancel = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(cancel ==0){
			throw new BusinessException("注销抵押计划时间更新失败!");
		}
		
		//领取产证计划时间
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.RECEIVE_ONE.getPart());
		}else if(RansomDiyaEnum.PAYLOAN_TWO.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.RECEIVE_TWO.getPart());
		}
		planVo.setEstPartTime(submitVo.getPlanPermitTime());
		Integer receivePlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), planVo.getPartCode());
		int receive = 0;
		if(receivePlan != null && receivePlan > 0){
			receive = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			receive = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(receive ==0){
			throw new BusinessException("领取产证计划时间更新失败!");
		}
		
		//回款结清计划时间
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.PAY_CLEAR.getPart());
			planVo.setEstPartTime(submitVo.getPlanPaymentTime());
			
			Integer clearPlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), planVo.getPartCode());
			int clear = 0;
			if(clearPlan != null &&clearPlan > 0){
				clear = ransomMapper.updateRansomPlanTime(planVo);
			}else{
				clear = ransomMapper.insertRansomPlanTime(planVo);
			}

			if(clear ==0){
				throw new BusinessException("回款结清计划时间更新失败!");
			}
		}
		
		return result;
	}
	
	@Override
	public int updateRansomCancel(String ransomCode, Integer diyaType, Date cancelTime) {
		
		SessionUser user = uamSessionService.getSessionUser();
		
		ToRansomCancelVo cancelVo = new ToRansomCancelVo();
		cancelVo.setRansomCode(ransomCode);
		cancelVo.setCancelTime(cancelTime);
		cancelVo.setDiyaType(diyaType.toString());
		cancelVo.setUpdateUser(user.getId());
		cancelVo.setCreateUser(user.getId());
		//判断一抵/二抵
		if(RansomDiyaEnum.CANCELDIYA_ONE.getCode().equals(diyaType.toString())){
			cancelVo.setPartCode(RansomDiyaEnum.CANCELDIYA_ONE.getPart());	
		}else if(RansomDiyaEnum.CANCELDIYA_TWO.getCode().equals(diyaType.toString())){
			cancelVo.setPartCode(RansomDiyaEnum.CANCELDIYA_TWO.getPart());
		}
		int result = ransomMapper.insertRansomCancel(cancelVo);
		if(result ==0){
			throw new BusinessException("注销抵押数据插入失败!");
		}
		return result;
	}

	@Override
	public int updateRansomPermit(String ransomCode, Integer diyaType,
			Date permitTime) {
		SessionUser user = uamSessionService.getSessionUser();
		
		ToRansomPermitVo permitVo = new ToRansomPermitVo();
		permitVo.setRansomCode(ransomCode);
		permitVo.setRedeemTime(permitTime);
		permitVo.setDiyaType(diyaType.toString());
		permitVo.setUpdateUser(user.getId());
		permitVo.setCreateUser(user.getId());
		//判断一抵/二抵
		if(RansomDiyaEnum.RECEIVE_ONE.getCode().equals(diyaType.toString())){
			permitVo.setPartCode(RansomDiyaEnum.RECEIVE_ONE.getPart());	
		}else if(RansomDiyaEnum.RECEIVE_TWO.getCode().equals(diyaType.toString())){
			permitVo.setPartCode(RansomDiyaEnum.RECEIVE_TWO.getPart());
		}
		int result = ransomMapper.insertRansomPermit(permitVo);
		if(result ==0){
			throw new BusinessException("领取产证数据插入失败!");
		}
		return result;
	}

	@Override
	public int updateRansomPayment(String ransomCode, Date paymentTime) {
		SessionUser user = uamSessionService.getSessionUser();
		
		ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
		paymentVo.setRansomCode(ransomCode);
		paymentVo.setPaymentTime(paymentTime);
		paymentVo.setUpdateUser(user.getId());
		paymentVo.setPartCode(RansomPartEnum.PAYCLEAR.getCode());
		paymentVo.setCreateUser(user.getId());
		int result = ransomMapper.insertRansomPayment(paymentVo);
		if(result ==0){
			throw new BusinessException("回款结清数据插入失败!");
		}		
		//完结赎楼单
		int count = ransomMapper.updateCaseStatusComplete(ransomCode);
		if(count ==0){
			throw new BusinessException("数据更新失败!");
		}	
		return result;
	}

	@Override
	public ToRansomTailinsVo getTailinsInfoByCaseCode(String caseCode) {
		ToRansomTailinsVo tailinsVo = ransomMapper.getTailinsInfoByCaseCode(caseCode);
		return tailinsVo;
	}

	@Override
	public ToRansomSignVo getInterviewInfo(String ransomCode) {
		ToRansomSignVo signVo = new ToRansomSignVo();
		signVo = ransomMapper.getInterviewInfoByRansomCode(ransomCode);
		return signVo;
	}

	@Override
	public ToRansomApplyVo getApplyInfo(String ransomCode) {
		ToRansomApplyVo applyVo = new ToRansomApplyVo();
		applyVo = ransomMapper.getApplyInfoByRansomCode(ransomCode);
		return applyVo;
	}

	@Override
	public ToRansomMortgageVo getMortgageInfo(String ransomCode) {
		ToRansomMortgageVo mortgageVo = new ToRansomMortgageVo();
		mortgageVo = ransomMapper.getMortgageInfoByRansomCode(ransomCode);
		return mortgageVo;
	}

	@Override
	public ToRansomCancelVo getCancelInfo(String ransomCode) {
		ToRansomCancelVo cancelVo = new ToRansomCancelVo();
		cancelVo = ransomMapper.getCancelInfoByRansomCode(ransomCode);
		return cancelVo;
	}

	@Override
	public ToRansomPermitVo getPermitInfo(String ransomCode) {
		ToRansomPermitVo permitVo = new ToRansomPermitVo();
		permitVo = ransomMapper.getPermitInfoByRansomCode(ransomCode);
		return permitVo;
	}

	@Override
	public ToRansomPaymentVo getPaymentInfo(String ransomCode) {
		ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
		paymentVo = ransomMapper.getPaymentInfoByRansomCode(ransomCode);
		return paymentVo;
	}

	@Override
	public ToRansomCaseVo getRansomCaseInfo(String caseCode) {
		ToRansomCaseVo caseVo = new ToRansomCaseVo();
		caseVo = ransomMapper.getRansomCaseInfoByCaseCode(caseCode);
		return caseVo;
	}

	@Override
	public ToRansomCaseVo getRansomInfoByRansomCode(String ransomCode) {
		ToRansomCaseVo caseVo = new ToRansomCaseVo();
		caseVo = ransomMapper.getRansomCaseInfoByRansomCode(ransomCode);
		return caseVo;
	}

	@Override
	public ToRansomLinkVo getRansomLinkInfo(String caseCode) {
		ToRansomLinkVo ransomLinkVo = new ToRansomLinkVo();
		ransomLinkVo = ransomListFormMapper.getRansomLinkInfoByCaseCode(caseCode);
		return ransomLinkVo;
	}

	@Override
	public boolean deleteRansomApplyByRansomCode(String ransomCode) {
		if(StringUtils.isNotBlank(ransomCode)) {
			return ransomMapper.deleteRansomApplyByRansomCode(ransomCode);
		}
		return false;
	}

	@Override
	public int updateRansomIsStart(String ransomCode) {
		
		return ransomMapper.updateRansomIsStart(ransomCode);
	}




}
