package com.centaline.trans.eloan.service;

import java.util.List;
import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;

/**
 * 自办评估/自办贷款申请信息借口
 * @author wblujian
 *
 */
public interface ToSelfAppInfoService {

	
	public String addSelfAppInfo(ToSelfAppInfo toSelfAppInfo);

	public ToSelfAppInfo getAppInfoByCaseCode(String caseCode);

	public List<ToAppRecordInfo> getAppRecordInfo(String appInfoId);

	public ToSelfAppInfo getAppInfoByCCAICode(String ccaiCode,String type);

	public int saveBatchToAppRecordInfo(List<ToAppRecordInfo> listRecord);
}
