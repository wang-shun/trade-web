package com.centaline.trans.task.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ReportOperateData;
import com.centaline.trans.task.entity.ToHouseTransfer;

@MyBatisRepository
public interface ReportOperateDataMapper {
	/**
	 * 贷款签约与过户对比 过户数据（签贷款）
	 * @param year 年
	 * @author hejf10
	 * @Data 2017年3月2日14:54:08
	 * @return ReportOperateData
	 */
	List<ReportOperateData> getReportOperateData(@Param("type")int type );
	/**
	 * 贷款签约与过户对比 过户数据（签贷款）贷款签约数据
	 * @param year 年 
	 * @author hejf10
	 * @Data 2017年3月2日14:54:08
	 * @return ReportOperateData
	 */
	List<ReportOperateData> getReportOperateDataToMortSignDate(@Param("type")int type);
	/**
	 * 过户数据
	 * @param year 年
	 * @author hejf10
	 * @Data 2017年3月2日14:54:08
	 * @return ReportOperateData
	 */
	List<ReportOperateData> getReportOperateDataTwo(@Param("type")int type);
	/**
	 * 签贷款数据
	 * @param year 年 
	 * @author hejf10
	 * @Data 2017年3月2日14:54:08
	 * @return ReportOperateData
	 */
	List<ReportOperateData> getReportOperateDataThree(@Param("type")int type);
}