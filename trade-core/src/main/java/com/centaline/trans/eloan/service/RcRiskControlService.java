package com.centaline.trans.eloan.service;

import java.util.List;
import com.centaline.trans.eloan.entity.RcRiskControl;

public interface RcRiskControlService {
	List<RcRiskControl> getRcRiskControlByProperty(String riskType, String eloanCode);
	
	int deleteReferRiskControlByProperty(String riskType,Long pkId);
}
