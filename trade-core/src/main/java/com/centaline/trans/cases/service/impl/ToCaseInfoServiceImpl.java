package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;

@Service
public class ToCaseInfoServiceImpl implements ToCaseInfoService {

    @Autowired
    private ToCaseInfoMapper toCaseInfoMapper;
    @Autowired
    private ToCaseMapper toCaseMapper;

	@Override
	public int queryCountCasesByUserId(String userId) {
		// TODO Auto-generated method stub
		Integer reInt = toCaseInfoMapper.queryCountCasesByUserId(userId);
		return reInt==null?0:reInt;
	}

	@Override
	public int queryCountMonthCasesByUserId(String userId) {
		// TODO Auto-generated method stub
		Integer reInt =  toCaseInfoMapper.queryCountMonthCasesByUserId(userId);
		return reInt==null?0:reInt;
	}

	@Override
	public int queryCountUnTransCasesByUserId(String userId) {
		// TODO Auto-generated method stub
		Integer reInt =  toCaseInfoMapper.queryCountUnTransCasesByUserId(userId);
		return reInt==null?0:reInt;
	}

	@Override
	public int updateByPrimaryKey(ToCaseInfo record) {
		// TODO Auto-generated method stub
		return toCaseInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToCaseInfo findToCaseInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
	}

	@Override
	public ToCaseInfoCountVo countToCaseInfoById(String userId) {
		return toCaseInfoMapper.countToCaseInfoById(userId);
	}

	/**
	 * 功能：交易单编号查询
	 * @author zhangxb16
	 */
	@Override
	public String findcaseCodeByctmCode(String ctmCode) {
		String caseCode=toCaseInfoMapper.findcaseCodeByctmCode(ctmCode);
		return caseCode;
	}

	@Override
	public ToCaseInfoCountVo getToCaseInfoCountAll() {
		
		return toCaseInfoMapper.getToCaseInfoCountAll();
	}

	@Override
	public ToCaseInfoCountVo countToCaseInfoAll() {
		// TODO Auto-generated method stub
		//return toCaseInfoMapper.;
		return null;
	}

	@Override
	public ToCaseInfoCountVo countToCaseInfoByOrgId(String orgId,String startDate,String endDate) {

		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setStartDate(startDate);
		toCase.setEndDate(endDate);
		return toCaseMapper.countToCaseInfoByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCaseInfoListByOrgId(String orgId) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		return toCaseMapper.countToCaseInfoListByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCaseInfoListByOrgList(List<String> orgList) {
		
		return toCaseMapper.countToCaseInfoListByOrgList(orgList);
	}

	@Override
	public int countToCaseInfoByOrgList(
			List<String> strArrayList, String startDate, String endDate) {
		return toCaseMapper.countToCaseInfoByOrgList(strArrayList,startDate,endDate);
	}

	@Override
	public int initCountToCaseInfoByOrgId(String orgId,String createTime) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setTime(createTime);
		return toCaseMapper.initCountToCaseInfoByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToCaseInfoListByIdList(
			List<String> idList) {
		
		return toCaseMapper.countToCaseInfoListByIdList(idList);
	}

	@Override
	public int countToCaseInfoByIdList(List<String> idList, String startDate,
			String endDate) {
		
		return toCaseMapper.countToCaseInfoByIdList(idList,startDate,endDate);
	}

	
	/**
	 * 功能：根据ctm 推送过来的编号到 T_TG_GUEST_INFO 表中去查询，是否已经存在
	 * @author zhangxb16
	 */
	@Override
	public int isExistCaseCode(String caseCode) {
		int existcasecode=toCaseInfoMapper.isExistCaseCode(caseCode);
		return existcasecode;
	}

	
}
