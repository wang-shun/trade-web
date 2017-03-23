package com.centaline.trans.bizwarn.repository;

import java.util.Map;

import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface BizWarnInfoMapper {

	int updateStatusInMortgageSelect(BizWarnInfo bizWarnInfo);

	int getAllBizwarnCountByTeam(String userLoginName);

	int getAllBizwarnCountByDistinct(String currentOrgId);

	int insert(BizWarnInfo bizWarnInfo);

	int insertSelective(BizWarnInfo record);
	
	BizWarnInfo  selectBizWarnInfoByMap(Map<String,Object> map);

	int updateByCaseCode(BizWarnInfo bizWarnInfo);

	int deleteByCaseCode(String caseCode);

	BizWarnInfo selectByCaseCode(String caseCode);

	int updateStatusByCaseCode(BizWarnInfo bizWarnInfo);

}