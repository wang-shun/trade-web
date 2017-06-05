package com.centaline.trans.performance.service;

import com.centaline.trans.performance.vo.PerfGoalVo;

public interface PerfGoalService {
	/**
	 * 设置业绩目标
	 * 
	 * @param vo
	 */
	int setPerfGoal(PerfGoalVo vo);

	/**
	 * 提交业绩目标设定
	 * 
	 * @param vo
	 */
	int commitPerfGoal(PerfGoalVo vo);
	/**
	 * 
	 * @param vo
	 * @return
	 */
	int getNotSetCount(PerfGoalVo vo);
/**
 * 获得主表状态
 * @param vo
 * @return
 */
	String getMainStatus(PerfGoalVo vo);
}