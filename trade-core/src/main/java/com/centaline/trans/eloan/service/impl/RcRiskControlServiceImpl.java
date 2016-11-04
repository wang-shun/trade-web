package com.centaline.trans.eloan.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcMortgageInfo;
import com.centaline.trans.eloan.repository.RcRiskControlMapper;
import com.centaline.trans.eloan.repository.ToRcMortgageInfoMapper;
import com.centaline.trans.eloan.service.RcRiskControlService;
import com.centaline.trans.material.entity.MmMaterialItem;
import com.centaline.trans.material.repository.MmMaterialItemMapper;

@Service
public class RcRiskControlServiceImpl implements RcRiskControlService {

	@Autowired
	private RcRiskControlMapper rcRiskControlMapper;
	
	@Autowired
	private ToRcMortgageInfoMapper toRcMortgageInfoMapper;
	
	@Autowired
	private MmMaterialItemMapper mmMaterialItemMapper;

	@Override
	public List<RcRiskControl> getRcRiskControlByProperty(String riskType, String eloanCode) {
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setRiskType(riskType);
		rcRiskControl.setEloanCode(eloanCode);
		return rcRiskControlMapper.getRiskControlByProperty(rcRiskControl);
	}

	@Override
	public int deleteReferRiskControlByProperty(String riskType, Long pkId,String eloanCode) {
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setEloanCode(eloanCode);
		rcRiskControl.setPkid(pkId);
		rcRiskControl.setRiskType(riskType);
		
		// 如果该物品待入库，需要删除物品管理中待入库的物品
		List<ToRcMortgageInfo> mortgageInfoList = toRcMortgageInfoMapper.getToRcMortgageInfoByProperty(rcRiskControl);
		for(ToRcMortgageInfo toRcMortgageInfo : mortgageInfoList) {
			if(StringUtils.isNotBlank(toRcMortgageInfo.getMortgageCode())) {
				MmMaterialItem mmMaterialItem = new MmMaterialItem();
				mmMaterialItem.setItemCode(toRcMortgageInfo.getMortgageCode());
				mmMaterialItemMapper.deleteWillStorageMmByProperty(mmMaterialItem);
			}
		}
		return rcRiskControlMapper.deleteReferRiskControlByProperty(rcRiskControl);
	}

}
