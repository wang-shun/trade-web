package com.centaline.trans.team.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.enumdata.DepTypeHierarchy;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.team.entity.TsTeamScopeAr;
import com.centaline.trans.team.entity.TsTeamScopeGrp;
import com.centaline.trans.team.entity.TsTeamScopeTarget;
import com.centaline.trans.team.repository.TsTeamScopeArMapper;
import com.centaline.trans.team.repository.TsTeamScopeGrpMapper;
import com.centaline.trans.team.repository.TsTeamScopeTargetMapper;
import com.centaline.trans.team.service.TsTeamScopeTargetService;

@Service
public class TsTeamScopeTargetServiceImpl implements TsTeamScopeTargetService {

	@Autowired
	private TsTeamScopeTargetMapper tsTeamScopeTargetMapper;

	@Autowired
	private UamUserOrgService UamUserOrgService;

	@Autowired
	private TsTeamScopeArMapper tsTeamScopeArMapper;

	@Autowired
	private TsTeamScopeGrpMapper tsTeamScopeGrpMapper;

	@Override
	public List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record) {
		return tsTeamScopeTargetMapper.getTeamScopeTargetListByProperty(record);
	}

	@Override
	public String addCaseMapping(String salesOrgId, String yuTeamCode) {

		Org salesOrg = UamUserOrgService.getComapnyByOrgId(salesOrgId);

		if (salesOrg == null || StringUtils.isBlank(salesOrg.getDepHierarchy().trim())) {
			return "请选择正确的片区或店租！";
		}

		Org yuTeamOrg = UamUserOrgService.getOrgByCode(yuTeamCode);
		if (yuTeamOrg == null || !DepTypeEnum.TYCTEAM.getCode().equals(yuTeamOrg.getDepHierarchy().trim())) {
			return "请选择正确的誉萃组！";
		}

		if (DepTypeHierarchy.BUSIAR.getCode().equals(salesOrg.getDepHierarchy())) {
			
			
			TsTeamScopeAr ar = new TsTeamScopeAr();
			ar.setArCode(salesOrg.getOrgCode());
			ar.setArName(salesOrg.getOrgName());
			ar.setIsResponseTeam("1");
			ar.setYuTeamCode(yuTeamOrg.getOrgCode());
			ar.setYuTeamName(yuTeamOrg.getOrgName());
			ar.setUpdateTime(new Date());
			
			List<TsTeamScopeAr> list = tsTeamScopeArMapper.getTsTeamScopeArListByProperty(ar);
			if(list != null && !list.isEmpty()){
				return "配置已经存在！片区："+salesOrg.getOrgName() + " 誉萃组：" +yuTeamOrg.getOrgName();
			}
			tsTeamScopeArMapper.insert(ar);
			tsTeamScopeTargetMapper.updateGrpMap();
		}

		if (DepTypeHierarchy.BUSIGRP.getCode().equals(salesOrg.getDepHierarchy())) {
			TsTeamScopeGrp grp = new TsTeamScopeGrp();
			grp.setGrpCode(salesOrg.getOrgCode());
			grp.setGrpName(salesOrg.getOrgName());
			grp.setIsResponseTeam("1");
			grp.setYuTeamCode(yuTeamOrg.getOrgCode());
			grp.setYuTeamName(yuTeamOrg.getOrgName());
			grp.setUpdateTime(new Date());

			Org arOrg = UamUserOrgService.getParentOrgByDepHierarchy(salesOrg.getId(),
					DepTypeHierarchy.BUSIAR.getCode());
			grp.setArCode(arOrg.getOrgCode());
			grp.setArName(arOrg.getOrgName());
			
			List<TsTeamScopeGrp> list = tsTeamScopeGrpMapper.getTsTeamScopeGrpListByProperty(grp);
			if(list != null && !list.isEmpty()){
				return "配置已经存在！店组："+salesOrg.getOrgName() + " 誉萃组：yuTeamOrg.getOrgName()";
			}
			
			tsTeamScopeGrpMapper.insert(grp);
			tsTeamScopeTargetMapper.updateGrpMap();
		}
		
		return "";
	}

}
