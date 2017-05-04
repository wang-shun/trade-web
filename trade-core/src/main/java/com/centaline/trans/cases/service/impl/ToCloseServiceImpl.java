package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.repository.ToCloseMapper;
import com.centaline.trans.cases.service.ToCloseService;
@Service
public class ToCloseServiceImpl implements ToCloseService{

	@Autowired
    private ToCloseMapper toCloseMapper;
    
	@Override
	public ToCaseInfoCountVo countToCloseById(String userId) {
		
		return toCloseMapper.countToCloseById(userId);
	}

	@Override
	public ToCaseInfoCountVo queryCountToCloseById(ToCase toCase) {
		return toCloseMapper.queryCountToCloseById(toCase);
	}

	@Override
	public ToCaseInfoCountVo countToCloseByOrgId(String orgId,String startDate,String endDate) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setStartDate(startDate);
		toCase.setEndDate(endDate);
		return toCloseMapper.countToCloseByOrgId(toCase);
	}

	@Override
	public ToCaseInfoCountVo queryCountToCloseByOrg(ToCase toCase) {
		
		return toCloseMapper.queryCountToCloseByOrg(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCloseListByOrgId(String orgId) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		return toCloseMapper.countToCloseListByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCloseListByOrgList(
			List<String> orgList) {
		return toCloseMapper.countToCloseListByOrgList(orgList);
	}

	@Override
	public int countToCloseByOrgList(List<String> orgList, String startDate,
			String endDate) {
		return toCloseMapper.countToCloseByOrgList(orgList,startDate,endDate);
	}

	@Override
	public int initCountToCloseByOrgId(String orgId,String createTime) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setTime(createTime);
		return toCloseMapper.initCountToCloseByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCloseListByIdList(List<String> idList) {
		return toCloseMapper.countToCloseListByIdList(idList);
	}

	@Override
	public int countToCloseByIdList(List<String> idList, String startDate,
			String endDate) {
		return toCloseMapper.countToCloseByIdList(idList,startDate,endDate);
	}

}
