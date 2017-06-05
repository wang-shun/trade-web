package com.centaline.trans.performance.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.performance.entity.PerfGoalMain;
import com.centaline.trans.performance.enums.PerfGoalStatus;
import com.centaline.trans.performance.repository.PerfGoalMainMapper;
import com.centaline.trans.performance.repository.PerfGoalMapper;
import com.centaline.trans.performance.service.PerfGoalService;
import com.centaline.trans.performance.vo.PerfGoalVo;

@Service
public class PerfGoalServiceImpl implements PerfGoalService {
	@Autowired
	private PerfGoalMapper perfGoalMapper;
	@Autowired
	private PerfGoalMainMapper perfGoalMainMapper;
	/**
	 * 设置业绩目标
	 */
	@Override
	public int setPerfGoal(PerfGoalVo vo) {
		PerfGoalMain perfGoalMain = perfGoalMainMapper.selectByBelongMonthAndDistrict(vo.getBelongMonth(),
				vo.getOrgId());
		if (perfGoalMain == null) {
			perfGoalMain = new PerfGoalMain();
			perfGoalMain.setBelongMonth(vo.getBelongMonth());
			perfGoalMain.setDistrict(vo.getOrgId());
			perfGoalMain.setStatus(PerfGoalStatus.UNSUBMITTED.getCode());
			perfGoalMainMapper.insertSelective(perfGoalMain);
		}
		if (PerfGoalStatus.SUBMITTED.getCode().equals(perfGoalMain.getStatus())) {/* 业绩目标已经提交不能继续修改 */
			throw new BusinessException("业绩目标已经提交,不能再作修改");
		}

		int result = 0;
		if (vo.getPkids() != null && vo.getPkids().length > 0) {
			result += perfGoalMapper.batchUpdateSetGoal(vo.getPkids(), vo.getPerfGoal(),
					PerfGoalStatus.UNSUBMITTED.getCode());
		}
		if (vo.getUojIds() != null && vo.getUojIds().length > 0) {
			result += perfGoalMapper.batchInsertByUojId(perfGoalMain.getPkid(), vo.getUojIds(), vo.getPerfGoal(),
					vo.getBelongMonth(), PerfGoalStatus.UNSUBMITTED.getCode());
		}
		return result;
	}

	/**
	 * 提交目标业绩
	 */
	@Override
	public int commitPerfGoal(PerfGoalVo vo) {
		PerfGoalMain perfGoalMain = perfGoalMainMapper.selectByBelongMonthAndDistrict(vo.getBelongMonth(),
				vo.getOrgId());
		if (perfGoalMain == null) {
			throw new BusinessException("数据错误，请联系管理员");
		}
		if (PerfGoalStatus.SUBMITTED.getCode().equals(perfGoalMain.getStatus())) {/* 业绩目标已经提交不能重复提交 */
			throw new BusinessException("业绩目标已经提交,请不要重复提交");
		}
		perfGoalMain.setStatus(PerfGoalStatus.SUBMITTED.getCode());
		perfGoalMainMapper.updateByPrimaryKeySelective(perfGoalMain);
		return perfGoalMapper.commitPerfGoal(vo.getOrgId(), vo.getBelongMonth());
	}

	/**
	 * 查询未设定目标人员数
	 */
	@Override
	public int getNotSetCount(PerfGoalVo vo) {
		return perfGoalMapper.getNotSetCount(vo.getOrgId(), vo.getBelongMonth());
	}
	/**
	 * 获得主表状态
	 */
	@Override
	public String getMainStatus(PerfGoalVo vo){
		PerfGoalMain perfGoalMain = perfGoalMainMapper.selectByBelongMonthAndDistrict(vo.getBelongMonth(),
				vo.getOrgId());
		if(perfGoalMain!=null){
			return perfGoalMain.getStatus();
		}
		return null;
	}

}
