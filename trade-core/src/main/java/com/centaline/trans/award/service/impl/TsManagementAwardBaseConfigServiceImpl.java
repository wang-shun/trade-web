package com.centaline.trans.award.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.award.entity.AwardBaseEntity;
import com.centaline.trans.award.entity.TsConsultantAwardBaseConfig;
import com.centaline.trans.award.entity.TsManagementAwardBaseConfig;
import com.centaline.trans.award.repository.AwardBaseEntityMapper;
import com.centaline.trans.award.repository.TsManagementAwardBaseConfigMapper;
import com.centaline.trans.award.service.TsManagementAwardBaseConfigService;
import com.centaline.trans.utils.DateUtil;

@Service
public class TsManagementAwardBaseConfigServiceImpl implements TsManagementAwardBaseConfigService {
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	TsManagementAwardBaseConfigMapper tsManagementAwardBaseConfigMapper;
	@Autowired
	AwardBaseEntityMapper awardBaseEntityMapper;
	
	
	@Override
	public int insertSelective(TsManagementAwardBaseConfig tsManagementAwardBaseConfig) {
		
		if(null == tsManagementAwardBaseConfig){
			throw new BusinessException("添加管理人员信息参数有误！");
		}
		int k = 0;
		//新增之前先查看是否存在
		TsManagementAwardBaseConfig awardBaseInfo = tsManagementAwardBaseConfigMapper.findAwardManagerInfoByUserIdAndOrgId(tsManagementAwardBaseConfig);
		if(null != awardBaseInfo){
			
			throw new BusinessException("添加管理人员信息已存在，请核实！");			
		
		}else{
			Calendar cd = Calendar.getInstance();
			User  user = uamUserOrgService.getUserById(tsManagementAwardBaseConfig.getUserId());
			tsManagementAwardBaseConfig.setEmployCode(user.getEmployeeCode());
			cd.add(Calendar.MONTH, 0);
			cd.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
			tsManagementAwardBaseConfig.setBelongMonth(cd.getTime());
			tsManagementAwardBaseConfig.setIsComfirm("0");
			k = tsManagementAwardBaseConfigMapper.insertSelective(tsManagementAwardBaseConfig);
		}
		return k;

	}

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		return tsManagementAwardBaseConfigMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsManagementAwardBaseConfig tsManagementAwardBaseConfig) {
		return tsManagementAwardBaseConfigMapper.updateByPrimaryKeySelective(tsManagementAwardBaseConfig);
	}

	@Override
	public int updateBaseConfigByPrimaryKey(TsConsultantAwardBaseConfig tsConsultantAwardBaseConfig,String belongMonth) {
		if(null == tsConsultantAwardBaseConfig || null == belongMonth || "".equals(belongMonth)){
			throw new BusinessException("修改基础环节奖金参数有误！");
		}
		//保证当前批次计件奖金数据一致
		AwardBaseEntity awardBase =  new AwardBaseEntity();
		awardBase.setBelongMonth(DateUtil.strToFullDate(belongMonth));
		awardBase.setSrvCode(tsConsultantAwardBaseConfig.getSrvItemCode());
		awardBase.setBaseAmount(tsConsultantAwardBaseConfig.getSrvFee());
		awardBaseEntityMapper.updateBySrvcodeAndBelongMonth(awardBase);
		
		return tsManagementAwardBaseConfigMapper.updateBaseConfigByPrimaryKey(tsConsultantAwardBaseConfig);
	}

}
