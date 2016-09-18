package com.centaline.trans.eloan.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcForceRegister;
import com.centaline.trans.eloan.entity.ToRcMortgage;
import com.centaline.trans.eloan.entity.ToRcMortgageCard;
import com.centaline.trans.eloan.entity.ToRcMortgageInfo;
import com.centaline.trans.eloan.repository.RcRiskControlMapper;
import com.centaline.trans.eloan.repository.ToRcForceRegisterMapper;
import com.centaline.trans.eloan.repository.ToRcMortgageCardMapper;
import com.centaline.trans.eloan.repository.ToRcMortgageInfoMapper;
import com.centaline.trans.eloan.repository.ToRcMortgageMapper;
import com.centaline.trans.eloan.service.ToRcMortgageService;
import com.centaline.trans.eloan.vo.ToRcForceRegisterVO;
import com.centaline.trans.eloan.vo.ToRcMortgageCardVO;
import com.centaline.trans.eloan.vo.ToRcMortgageVO;

@Service
public class ToRcMortgageServiceImpl implements ToRcMortgageService {
	@Autowired
	private RcRiskControlMapper rcRiskControlMapper;
	@Autowired
	private ToRcMortgageCardMapper toRcMortgageCardMapper;
	@Autowired
	private ToRcMortgageMapper toRcMortgageMapper;
	@Autowired
	private ToRcMortgageInfoMapper toRcMortgageInfoMapper;
	@Autowired
	private ToRcForceRegisterMapper toRcForceRegisterMapper;

	@Override
	public int saveRcMortgageCard(ToRcMortgageCardVO toRcMortgageCardVO) {
		int i = 0;
		if(toRcMortgageCardVO.getRcRiskControl().getPkid()==null) {
			rcRiskControlMapper.insertSelective(toRcMortgageCardVO.getRcRiskControl());
			ToRcMortgageCard toRcMortgageCard = toRcMortgageCardVO.getToRcMortgageCard();
			toRcMortgageCard.setRcId(toRcMortgageCardVO.getRcRiskControl().getPkid());
			toRcMortgageCardMapper.insertSelective(toRcMortgageCard);
			
			for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageCardVO.getToRcMortgageInfoList()) {
				toRcMortgageInfo.setRiskControlId(toRcMortgageCardVO.getRcRiskControl().getPkid());
				i+= toRcMortgageInfoMapper.insertSelective(toRcMortgageInfo);
			}
		} else {
			rcRiskControlMapper.updateByPrimaryKeySelective(toRcMortgageCardVO.getRcRiskControl());
			// 更新押卡表
			ToRcMortgageCard toRcMortgageCard = toRcMortgageCardVO.getToRcMortgageCard();
			toRcMortgageCard.setRcId(toRcMortgageCardVO.getRcRiskControl().getPkid());
			toRcMortgageCardMapper.updateMortgageCardByRcId(toRcMortgageCard);
			// 更新抵押物品基本信息
			toRcMortgageInfoMapper.deleteToRcMortgageInfoByRcId(toRcMortgageCardVO.getRcRiskControl().getPkid());
			for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageCardVO.getToRcMortgageInfoList()) {
				toRcMortgageInfo.setRiskControlId(toRcMortgageCardVO.getRcRiskControl().getPkid());
				i+= toRcMortgageInfoMapper.insertSelective(toRcMortgageInfo);
			}
		}
		return i;
	}
	
	@Override
	public int saveRcMortgage(ToRcMortgageVO toRcMortgageVO) {
		int i = 0;
		if(toRcMortgageVO.getRcRiskControl().getPkid()==null) {
			rcRiskControlMapper.insertSelective(toRcMortgageVO.getRcRiskControl());
			
			ToRcMortgage toRcMortgage = toRcMortgageVO.getToRcMortgage();
			toRcMortgage.setRcId(toRcMortgageVO.getRcRiskControl().getPkid());
			toRcMortgageMapper.insertSelective(toRcMortgage);
			
			for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageVO.getToRcMortgageInfoList()) {
				toRcMortgageInfo.setRiskControlId(toRcMortgageVO.getRcRiskControl().getPkid());
				i+= toRcMortgageInfoMapper.insertSelective(toRcMortgageInfo);
			}
		} else {
			rcRiskControlMapper.updateByPrimaryKeySelective(toRcMortgageVO.getRcRiskControl());
			// 更新押卡表
			ToRcMortgage toRcMortgage = toRcMortgageVO.getToRcMortgage();
			toRcMortgage.setRcId(toRcMortgageVO.getRcRiskControl().getPkid());
			toRcMortgageMapper.updateMortgageByRcId(toRcMortgage);
			// 更新抵押物品基本信息
			toRcMortgageInfoMapper.deleteToRcMortgageInfoByRcId(toRcMortgageVO.getRcRiskControl().getPkid());
			for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageVO.getToRcMortgageInfoList()) {
				toRcMortgageInfo.setRiskControlId(toRcMortgageVO.getRcRiskControl().getPkid());
				i+= toRcMortgageInfoMapper.insertSelective(toRcMortgageInfo);
			}
		}
		return i;
	}
	
	@Override
	public int saveToRcForceRegister(ToRcForceRegisterVO toRcForceRegisteVO) {
		int i = 0;
		if(toRcForceRegisteVO.getRcRiskControl().getPkid()==null) {
			i += rcRiskControlMapper.insertSelective(toRcForceRegisteVO.getRcRiskControl());
			
			ToRcForceRegister toRcForceRegister = toRcForceRegisteVO.getToRcForceRegister();
			toRcForceRegister.setRcId(toRcForceRegisteVO.getRcRiskControl().getPkid());
			i += toRcForceRegisterMapper.insertSelective(toRcForceRegister);
		} else {
			rcRiskControlMapper.updateByPrimaryKeySelective(toRcForceRegisteVO.getRcRiskControl());
			ToRcForceRegister toRcForceRegister = toRcForceRegisteVO.getToRcForceRegister();
			toRcForceRegisterMapper.updateRcForceRegisterByRcId(toRcForceRegister);
		}
		return i;
	}

	@Override
	public ToRcMortgageCardVO getRcMortgageCardInfoByProperty(String riskType, String eloanCode) {
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setRiskType(riskType);
		rcRiskControl.setEloanCode(eloanCode);
		
		List<RcRiskControl>  rcRiskControlList = rcRiskControlMapper.getRiskControlByProperty(rcRiskControl);
		List<ToRcMortgageCard> toRcMortgageCardList  = toRcMortgageCardMapper.getMortgageCardByProperty(rcRiskControl);
		List<ToRcMortgageInfo> toRcMortgageInfoList = toRcMortgageInfoMapper.getToRcMortgageInfoByProperty(rcRiskControl);
		
		ToRcMortgageCardVO toRcMortgageCardVO = new ToRcMortgageCardVO();
		if(CollectionUtils.isNotEmpty(toRcMortgageCardList)){
			toRcMortgageCardVO.setToRcMortgageCard(toRcMortgageCardList.get(0));
		}
		if(CollectionUtils.isNotEmpty(rcRiskControlList)){
			toRcMortgageCardVO.setRcRiskControl(rcRiskControlList.get(0));
		}
		toRcMortgageCardVO.setToRcMortgageInfoList(toRcMortgageInfoList);
		
		return toRcMortgageCardVO;
	}
	
	@Override
	public ToRcMortgageVO getMortgageByProperty(String riskType, String eloanCode) {
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setRiskType(riskType);
		rcRiskControl.setEloanCode(eloanCode);
		
		List<RcRiskControl>  rcRiskControlList = rcRiskControlMapper.getRiskControlByProperty(rcRiskControl);
		List<ToRcMortgage> toRcMortgageList  = toRcMortgageMapper.getMortgageByProperty(rcRiskControl);
		List<ToRcMortgageInfo> toRcMortgageInfoList = toRcMortgageInfoMapper.getToRcMortgageInfoByProperty(rcRiskControl);
		
		ToRcMortgageVO toRcMortgageVO = new ToRcMortgageVO();
		if(CollectionUtils.isNotEmpty(toRcMortgageList)){
			toRcMortgageVO.setToRcMortgage(toRcMortgageList.get(0));
		}
		if(CollectionUtils.isNotEmpty(rcRiskControlList)){
			toRcMortgageVO.setRcRiskControl(rcRiskControlList.get(0));
		}
		toRcMortgageVO.setToRcMortgageInfoList(toRcMortgageInfoList);
		
		return toRcMortgageVO;
	}


}
