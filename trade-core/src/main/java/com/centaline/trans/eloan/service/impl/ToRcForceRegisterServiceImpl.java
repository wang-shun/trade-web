package com.centaline.trans.eloan.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcForceRegister;
import com.centaline.trans.eloan.repository.RcRiskControlMapper;
import com.centaline.trans.eloan.repository.ToRcForceRegisterMapper;
import com.centaline.trans.eloan.service.ToRcForceRegisterService;
import com.centaline.trans.eloan.vo.ToRcForceRegisterVO;
@Service
public class ToRcForceRegisterServiceImpl implements ToRcForceRegisterService {

	@Autowired
	private ToRcForceRegisterMapper toRcForceRegisterMapper;
	
	@Autowired
	private RcRiskControlMapper rcRiskControlMapper;

	@Override
	public ToRcForceRegisterVO getRcForceRegisterByProperty(String riskType, String eloanCode) {
		ToRcForceRegisterVO toRcForceRegisterVO = new ToRcForceRegisterVO();
		
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setRiskType(riskType);
		rcRiskControl.setEloanCode(eloanCode);
		
		List<RcRiskControl>  rcRiskControlList = rcRiskControlMapper.getRiskControlByProperty(rcRiskControl);
		List<ToRcForceRegister> forceRegisterList = toRcForceRegisterMapper.getRcForceRegisterByProperty(rcRiskControl);
		if(CollectionUtils.isNotEmpty(forceRegisterList)){
			toRcForceRegisterVO.setToRcForceRegister(forceRegisterList.get(0));
		}
		if(CollectionUtils.isNotEmpty(rcRiskControlList)){
			toRcForceRegisterVO.setRcRiskControl(rcRiskControlList.get(0));
		}
		return toRcForceRegisterVO;
	}



}
