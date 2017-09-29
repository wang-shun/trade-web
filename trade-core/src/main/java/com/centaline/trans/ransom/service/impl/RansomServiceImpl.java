package com.centaline.trans.ransom.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomService;

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
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	
	@Override
	public ToRansomDetailVo getRansomDetail(String ransomCode) {
		ToRansomDetailVo detailVo = ransomMapper.getRansomDetailInfoByCode(ransomCode);
		User user = uamUserOrgService.getUserById(detailVo.getLeadingProcessId());
		detailVo.setLeadingProcessName(user.getRealName()); //经办人

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
	public int updateRansomCancel(String ransomCode, Integer diyaType, Date cancelTime) {
		
		SessionUser user = uamSessionService.getSessionUser();
		
		ToRansomCancelVo cancelVo = new ToRansomCancelVo();
		cancelVo.setRansomCode(ransomCode);
		cancelVo.setCancelTime(cancelTime);
		cancelVo.setDiyaType(diyaType.toString());
		cancelVo.setUpdateUser(user.getId());
		
		//判断一抵/二抵
		if(RansomDiyaEnum.CANCELDIYA_ONE.getCode().equals(diyaType.toString())){
			cancelVo.setPartCode(RansomDiyaEnum.CANCELDIYA_ONE.getPart());	
		}else if(RansomDiyaEnum.CANCELDIYA_TWO.getCode().equals(diyaType.toString())){
			cancelVo.setPartCode(RansomDiyaEnum.CANCELDIYA_TWO.getPart());
		}
		int result = ransomMapper.insertRansomCancel(cancelVo);
		if(result ==0){
			throw new BusinessException("注销抵押插入失败!");
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
		
		//判断一抵/二抵
		if(RansomDiyaEnum.RECEIVE_ONE.getCode().equals(diyaType.toString())){
			permitVo.setPartCode(RansomDiyaEnum.RECEIVE_ONE.getPart());	
		}else if(RansomDiyaEnum.RECEIVE_TWO.getCode().equals(diyaType.toString())){
			permitVo.setPartCode(RansomDiyaEnum.RECEIVE_TWO.getPart());
		}
		int result = ransomMapper.insertRansomPermit(permitVo);
		if(result ==0){
			throw new BusinessException("领取产证插入失败!");
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
		paymentVo.setPartCode(RansomPartEnum.PLAYCLEAR.getCode());
		
		int result = ransomMapper.insertRansomPayment(paymentVo);
		if(result ==0){
			throw new BusinessException("领取产证插入失败!");
		}
		return result;
	}
	
	

}
