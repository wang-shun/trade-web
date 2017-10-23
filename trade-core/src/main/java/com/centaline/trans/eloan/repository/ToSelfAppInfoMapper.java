package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;

/**
 * 
 * @author wblujian
 *
 */
@MyBatisRepository
public interface ToSelfAppInfoMapper {

	Long insertSelfAppInfo(ToSelfAppInfo toSelfAppInfo);

	ToSelfAppInfo getAppInfoByCaseCode(String caseCode,String type);

	ToSelfAppInfo getAppInfoByCCAICode(String ccaiCode,String type);

	void updateByPrimaryKeySelective(ToSelfAppInfo toSelfAppInfo);

	ToSelfAppInfo getAppInfoByCaseCo(String caseCode);

}
