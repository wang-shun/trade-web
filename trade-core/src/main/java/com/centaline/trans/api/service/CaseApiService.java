package com.centaline.trans.api.service;

import com.centaline.trans.api.vo.ApiCaseInfo;
import com.centaline.trans.api.vo.ApiRebateReportInfo;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.task.entity.ToSign;

/**
 * 用于获取案件扩展信息的接口
 *
 * @author yinchao
 * @date 2017/9/14
 */
public interface CaseApiService {
	/**
	 * 根据CCAI 成交报告ID 获取业绩记录和收费情况
	 * @param ccaiCode
	 * @return
	 */
	ApiCaseInfo getApiCaseInfo(String ccaiCode);

	/**
	 * 将网签信息 同步到CCAI中
	 * @param info 需要同步的网签信息
	 * @return data返回错误信息
	 */
	ApiResultData SyncNetSign(ToSign info);

	/**
	 * 获取CCAI返利报告详情信息
	 * @param reportCode 返利报告编号 -F* 的
	 * @return
	 */
	ApiRebateReportInfo getApiRebateReportInfo(String reportCode);

}
