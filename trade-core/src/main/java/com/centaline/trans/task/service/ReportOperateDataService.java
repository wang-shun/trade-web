package com.centaline.trans.task.service;


import java.util.List;
import java.util.Map;

import com.centaline.trans.task.entity.ReportOperateData;

public interface ReportOperateDataService {
	/**
	 * 贷款签约与过户对比
	 * @param year 年
	 * @author hejf10
	 * @Data 时间
	 * @return ReportOperateData
	 */
	List<List<ReportOperateData>> getReportOperateDataOne(int year);
	/**
	 * 过户数据
	 * @param year 年 
	 * @author hejf10
	 * @Data 时间
	 * @return ReportOperateData
	 */
	List<List<ReportOperateData>> getReportOperateDataTwo(int year);
	/**
	 * 签贷款数据
	 * @param year 年
	 * @author hejf10
	 * @Data 时间
	 * @return ReportOperateData
	 */
	List<List<ReportOperateData>> getReportOperateDataThree(int year);
}
