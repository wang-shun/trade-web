package com.centaline.trans.performance.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.performance.repository.PerfGoalMapper;
import com.centaline.trans.performance.service.PerfGoalService;
import com.centaline.trans.performance.vo.PerfGoalVo;

@Service
public class PerfGoalServiceImpl implements PerfGoalService {
	@Autowired
	private PerfGoalMapper perfGoalMapper;
	/**
	 * 获得某月第一天
	 * @param currentMonthDiff
	 * @return
	 */
	private Date getBelongMonth(int currentMonthDiff) {
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MONTH, currentMonthDiff);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		return c1.getTime();
	}
	/**
	 * 设置业绩目标
	 */
	@Override
	public int setPerfGoal(PerfGoalVo vo) {
		int result = 0;
		if (vo.getPkids() != null && vo.getPkids().length > 0) {
			result += perfGoalMapper.batchUpdateSetGoal(vo.getPkids(), vo.getPerfGoal(), "0");
		}
		if (vo.getUojIds() != null && vo.getUojIds().length > 0) {
			result += perfGoalMapper.batchInsertByUojId(vo.getUojIds(), vo.getPerfGoal(),
					getBelongMonth(vo.getCurrentMonthDiff()), "0");
		}
		return result;
	}
	/**
	 * 提交目标业绩
	 */
	@Override
	public int commitPerfGoal(PerfGoalVo vo) {
		return perfGoalMapper.commitPerfGoal(vo.getOrgId(), vo.getCurrentMonthDiff());
	}
	/**
	 * 查询未设定目标人员数
	 */
	@Override
	public int getNotSetCount(PerfGoalVo vo) {
		return perfGoalMapper.getNotSetCount(vo.getOrgId(), vo.getCurrentMonthDiff());
	}

}
