package com.centaline.trans.award.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.award.entity.TsManagementAwardBaseConfig;
import com.centaline.trans.award.repository.TsManagementAwardBaseConfigMapper;
import com.centaline.trans.award.service.TsManagementAwardBaseConfigService;

@Service
public class TsManagementAwardBaseConfigServiceImpl implements TsManagementAwardBaseConfigService {
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	TsManagementAwardBaseConfigMapper tsManagementAwardBaseConfigMapper;
	
	@Override
	public int insertSelective(TsManagementAwardBaseConfig tsManagementAwardBaseConfig) {
		
		Calendar cd = Calendar.getInstance();
		User  user = uamUserOrgService.getUserById(tsManagementAwardBaseConfig.getUserId());
		tsManagementAwardBaseConfig.setEmployCode(user.getEmployeeCode());
		cd.add(Calendar.MONTH, 0);
		cd.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		tsManagementAwardBaseConfig.setBelongMonth(cd.getTime());
		tsManagementAwardBaseConfig.setIsComfirm("0");
		return tsManagementAwardBaseConfigMapper.insertSelective(tsManagementAwardBaseConfig);
	}

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		return tsManagementAwardBaseConfigMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsManagementAwardBaseConfig tsManagementAwardBaseConfig) {
		return tsManagementAwardBaseConfigMapper.updateByPrimaryKeySelective(tsManagementAwardBaseConfig);
	}

}
