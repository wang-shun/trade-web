package com.centaline.trans.task.service;

import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.task.vo.OfflineEvaVO;

public interface OfflineEvaService {

	public OfflineEvaVO queryOfflineEvaVO(String processId);
	
	public Boolean saveEvaReport(ToEvaReport toEvaReport);
}
