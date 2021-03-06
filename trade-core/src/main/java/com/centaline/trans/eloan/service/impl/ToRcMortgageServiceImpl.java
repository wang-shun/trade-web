package com.centaline.trans.eloan.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.service.GenerateBusinessCodeService;
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
import com.centaline.trans.eloan.util.CreateComment;
import com.centaline.trans.eloan.vo.ToRcForceRegisterVO;
import com.centaline.trans.eloan.vo.ToRcMortgageCardVO;
import com.centaline.trans.eloan.vo.ToRcMortgageVO;
import com.centaline.trans.material.entity.MmMaterialItem;
import com.centaline.trans.material.enums.MaterialStatusEnum;
import com.centaline.trans.material.repository.MmMaterialItemMapper;
import com.centaline.trans.material.service.MmMaterialItemService;

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
	@Autowired
	private GenerateBusinessCodeService generateBusinessCodeService;
	@Autowired
	private MmMaterialItemService mmMaterialItemService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private MmMaterialItemMapper mmMaterialItemMapper;
	
	public static final String ITEM_RESOURCE_RC = "riskControl";

	@Override
	public void saveRcMortgageCard(ToRcMortgageCardVO toRcMortgageCardVO) {
		if(toRcMortgageCardVO.getRcRiskControl().getPkid()==null) {
			rcRiskControlMapper.insertSelective(toRcMortgageCardVO.getRcRiskControl());
			ToRcMortgageCard toRcMortgageCard = toRcMortgageCardVO.getToRcMortgageCard();
			toRcMortgageCard.setRcId(toRcMortgageCardVO.getRcRiskControl().getPkid());
			toRcMortgageCardMapper.insertSelective(toRcMortgageCard);
			
			saveInfoAndTransferToMmItem(toRcMortgageCardVO.getToRcMortgageInfoList(),
					                    toRcMortgageCardVO.getRcRiskControl().getPkid(),
					                    toRcMortgageCardVO.getRcRiskControl().getCaseCode());
		} else {
			
			rcRiskControlMapper.updateByPrimaryKeySelective(toRcMortgageCardVO.getRcRiskControl());
			// 更新押卡表
			ToRcMortgageCard toRcMortgageCard = toRcMortgageCardVO.getToRcMortgageCard();
			toRcMortgageCard.setRcId(toRcMortgageCardVO.getRcRiskControl().getPkid());
			toRcMortgageCardMapper.updateMortgageCardByRcId(toRcMortgageCard);
			// 更新抵押物品基本信息
			/*toRcMortgageInfoMapper.deleteToRcMortgageInfoByRcId(toRcMortgageCardVO.getRcRiskControl().getPkid());
			for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageCardVO.getToRcMortgageInfoList()) {
				toRcMortgageInfo.setRiskControlId(toRcMortgageCardVO.getRcRiskControl().getPkid());
				i+= toRcMortgageInfoMapper.insertSelective(toRcMortgageInfo);
			}*/
			saveInfoAndTransferToMmItem(toRcMortgageCardVO.getToRcMortgageInfoList(),
					                    toRcMortgageCardVO.getRcRiskControl().getPkid(),
					                    toRcMortgageCardVO.getRcRiskControl().getCaseCode());
		}
	}
	
	private void saveInfoAndTransferToMmItem(List<ToRcMortgageInfo> toRcMortgageInfoList,Long riskControlId,String caseCode) {
		for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageInfoList) {
			if(toRcMortgageInfo.getPkid()==null) {
				toRcMortgageInfo.setMortgageCode(generateBusinessCodeService.createItemCode());
				toRcMortgageInfo.setRiskControlId(riskControlId);
				toRcMortgageInfoMapper.insertSelective(toRcMortgageInfo);
				
				MmMaterialItem mmMaterialItem = transferToMmItem(toRcMortgageInfo,caseCode);
				mmMaterialItem.setItemStatus(MaterialStatusEnum.STAY.getCode());
				mmMaterialItem.setIsDeleted("N");
				// 新增
				mmMaterialItemService.insertMaterialInfoFromSpv(mmMaterialItem);
			} else {
				if("Y".equals(toRcMortgageInfo.getIsWillDeleted())) {
					toRcMortgageInfoMapper.deleteByPrimaryKey(toRcMortgageInfo.getPkid());
				} else {
					toRcMortgageInfoMapper.updateByPrimaryKeySelective(toRcMortgageInfo);
					MmMaterialItem mmMaterialItem = transferToMmItem(toRcMortgageInfo,caseCode);

					// 修改
					mmMaterialItemService.updateMaterialInfoByItemCode(mmMaterialItem);
					//mmMaterialItemService.updateMaterialInfoByItemCode(mmMaterialItem.getItemCode());
				}
			}
		}
	}
	
	private MmMaterialItem transferToMmItem(ToRcMortgageInfo toRcMortgageInfo,String caseCode) {
		MmMaterialItem mmMaterialItem = new MmMaterialItem();
		mmMaterialItem.setCaseCode(caseCode);
		mmMaterialItem.setItemCode(toRcMortgageInfo.getMortgageCode());
		mmMaterialItem.setItemName(toRcMortgageInfo.getMortgageName());
		mmMaterialItem.setItemCategory(toRcMortgageInfo.getMortgageCategory());
		mmMaterialItem.setItemResource(ToRcMortgageServiceImpl.ITEM_RESOURCE_RC);
		mmMaterialItem.setItemManager(toRcMortgageInfo.getItemManager());
		mmMaterialItem.setItemBusinessRemark(CreateComment.createMmComment(toRcMortgageInfo));
		
		return mmMaterialItem;
	}
	
	@Override
	public void saveRcMortgage(ToRcMortgageVO toRcMortgageVO) {
		if(toRcMortgageVO.getRcRiskControl().getPkid()==null) {
			rcRiskControlMapper.insertSelective(toRcMortgageVO.getRcRiskControl());
			
			ToRcMortgage toRcMortgage = toRcMortgageVO.getToRcMortgage();
			toRcMortgage.setRcId(toRcMortgageVO.getRcRiskControl().getPkid());
			toRcMortgageMapper.insertSelective(toRcMortgage);
			
			saveInfoAndTransferToMmItem(toRcMortgageVO.getToRcMortgageInfoList(),
					toRcMortgageVO.getRcRiskControl().getPkid(),
					toRcMortgageVO.getRcRiskControl().getCaseCode());
		} else {
			rcRiskControlMapper.updateByPrimaryKeySelective(toRcMortgageVO.getRcRiskControl());
			// 更新押卡表
			ToRcMortgage toRcMortgage = toRcMortgageVO.getToRcMortgage();
			toRcMortgage.setRcId(toRcMortgageVO.getRcRiskControl().getPkid());
			toRcMortgageMapper.updateMortgageByRcId(toRcMortgage);
			// 更新抵押物品基本信息
			saveInfoAndTransferToMmItem(toRcMortgageVO.getToRcMortgageInfoList(),
					toRcMortgageVO.getRcRiskControl().getPkid(),
					toRcMortgageVO.getRcRiskControl().getCaseCode());
		}
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
		for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageInfoList) {
			String itemManager = toRcMortgageInfo.getItemManager();
			
			String itemCode = toRcMortgageInfo.getMortgageCode();
			MmMaterialItem record = new MmMaterialItem();
			record.setItemStatus(MaterialStatusEnum.INSTOCK.getCode());
			record.setItemCode(itemCode);
			List<MmMaterialItem> itemList = mmMaterialItemMapper.getMmMaterialItemListByProperty(record);
			if(CollectionUtils.isNotEmpty(itemList)) {
				toRcMortgageInfo.setIsStorage("Y");
			}
			if(StringUtils.isNotBlank(itemManager)) {
				SessionUser user = uamSessionService.getSessionUserById(itemManager);
				toRcMortgageInfo.setItemManagerName(user.getRealName());
			}
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
		for(ToRcMortgageInfo toRcMortgageInfo : toRcMortgageInfoList) {
			String itemManager = toRcMortgageInfo.getItemManager();
			if(StringUtils.isNotBlank(itemManager)) {
				SessionUser user = uamSessionService.getSessionUserById(itemManager);
				toRcMortgageInfo.setItemManagerName(user.getRealName());
			}
			
			String itemCode = toRcMortgageInfo.getMortgageCode();
			MmMaterialItem record = new MmMaterialItem();
			record.setItemStatus(MaterialStatusEnum.INSTOCK.getCode());
			record.setItemCode(itemCode);
			List<MmMaterialItem> itemList = mmMaterialItemMapper.getMmMaterialItemListByProperty(record);
			if(CollectionUtils.isNotEmpty(itemList)) {
				toRcMortgageInfo.setIsStorage("Y");
			}
		}
		toRcMortgageVO.setToRcMortgageInfoList(toRcMortgageInfoList);
		
		return toRcMortgageVO;
	}


}
