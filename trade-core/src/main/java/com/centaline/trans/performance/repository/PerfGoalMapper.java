package com.centaline.trans.performance.repository;

import java.util.Date; 

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.performance.entity.PerfGoal;
@MyBatisRepository
public interface PerfGoalMapper {
	int insert(PerfGoal record);

	int insertSelective(PerfGoal record);

	/**
	 * 批量更新业绩
	 * 
	 * @param pkIds
	 * @param goalPerf
	 * @return
	 */
	int batchUpdateSetGoal(@Param("pkids") long[] pkIds, @Param("goalPerf") Double goalPerf,
			@Param("status") String status);

	/**
	 * 批量插入
	 * @param mainId
	 * @param uojIds
	 * @param goalPerf
	 * @param belongMonth
	 * @param status
	 * @return
	 */
	int batchInsertByUojId(@Param("mainId")Long mainId,@Param("uojIds") String[] uojIds, @Param("goalPerf") Double goalPerf,
			@Param("belongMonth") Date belongMonth, @Param("status") String status);

	/**
	 * 
	 * @param orgId
	 * @return
	 */
	int getNotSetCount(@Param("orgId") String orgId,@Param("belongMonth")Date belongMonth);
	/**
	 * 提交目标业绩
	 * @param orgId
	 * @param currentMonthDiff
	 * @return
	 */
	int commitPerfGoal(@Param("orgId")String orgId,@Param("belongMonth")Date belongMonth);
}