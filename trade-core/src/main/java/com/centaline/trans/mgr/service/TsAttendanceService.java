package com.centaline.trans.mgr.service;

import com.centaline.trans.mgr.entity.TsAttendance;

public interface TsAttendanceService {
	
	/**
	 * 保存考勤信息
	 * @param tsSup
	 */
	void saveTsAttendance(TsAttendance tsAttendance);
	
}