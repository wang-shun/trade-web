package com.centaline.trans.mgr.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.mgr.entity.TsAttendance;
import com.centaline.trans.mgr.repository.TsAttendanceMapper;
import com.centaline.trans.mgr.service.TsAttendanceService;

@Service
public class TsAttendanceServiceImpl implements TsAttendanceService{

	@Autowired
	private TsAttendanceMapper tsAttendanceMapper;
	@Autowired
	private UamSessionService uamSessionService;
	
	@Override
	public void saveTsAttendance(TsAttendance tsAttendance) {
		SessionUser sessionUser = uamSessionService.getSessionUser();
		tsAttendance.setStaffId(sessionUser.getId());
		tsAttendance.setAttendTime(new Date());
		tsAttendance.setOrgId(sessionUser.getServiceDepId());
		tsAttendanceMapper.insertSelective(tsAttendance);
		
	}
}
