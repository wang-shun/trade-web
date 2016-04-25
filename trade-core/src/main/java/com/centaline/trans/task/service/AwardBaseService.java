package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.ToHouseTransfer;

public interface AwardBaseService {
	void doAwardCalculate(ToHouseTransfer toHouseTransfer,String processInstanceId);
}
