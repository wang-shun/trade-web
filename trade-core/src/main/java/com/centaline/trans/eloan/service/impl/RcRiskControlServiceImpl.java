package com.centaline.trans.eloan.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.repository.RcRiskControlMapper;
import com.centaline.trans.eloan.service.RcRiskControlService;

@Service
public class RcRiskControlServiceImpl implements RcRiskControlService {

	@Autowired
	private RcRiskControlMapper rcRiskControlMapper;

	@Override
	public List<RcRiskControl> getRcRiskControlByProperty(String riskType, String eloanCode) {
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setRiskType(riskType);
		rcRiskControl.setEloanCode(eloanCode);
		return rcRiskControlMapper.getRiskControlByProperty(rcRiskControl);
	}

	@Override
	public int deleteReferRiskControlByProperty(String riskType, Long pkId) {
		RcRiskControl rcRiskControl = new RcRiskControl();
		rcRiskControl.setPkid(pkId);
		rcRiskControl.setRiskType(riskType);
		return rcRiskControlMapper.deleteReferRiskControlByProperty(rcRiskControl);
	}

}
