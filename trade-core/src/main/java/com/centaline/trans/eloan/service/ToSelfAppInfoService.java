package com.centaline.trans.eloan.service;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;

/**
 * 自办评估/自办贷款申请信息借口
 * @author wblujian
 *
 */
public interface ToSelfAppInfoService {

	
	public void addSelfAppInfo(ToSelfAppInfo toSelfAppInfo);
}
