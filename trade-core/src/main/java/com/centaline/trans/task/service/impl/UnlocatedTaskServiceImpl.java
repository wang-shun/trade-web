/**
 * 
 */
package com.centaline.trans.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.ToUnlocatedTask;
import com.centaline.trans.task.repository.ToUnlocatedTaskMapper;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.task.vo.ToUnlocatedTaskVo;

/**
 * @author jjm
 * 
 */
@Service
public class UnlocatedTaskServiceImpl implements UnlocatedTaskService {
	@Autowired
	private ToUnlocatedTaskMapper toUnlocatedTaskMapper;
	@Autowired
	private WorkFlowManager workFlowManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.centaline.trans.task.service.UnlocatedTaskService#insert(com.centaline
	 * .trans.task.entity.ToUnlocatedTask)
	 */
	@Override
	public int insert(ToUnlocatedTask unloactedTask) {
		return toUnlocatedTaskMapper.insert(unloactedTask);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.centaline.trans.task.service.UnlocatedTaskService#deleteByTaskId(
	 * java.lang.String)
	 */
	@Override
	public int deleteByTaskId(String taskId) {
		return toUnlocatedTaskMapper.deleteByTaskId(taskId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.centaline.trans.task.service.UnlocatedTaskService#deleteByPrimaryKey
	 * (java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String taskId) {
		return toUnlocatedTaskMapper.deleteByPrimaryKey(taskId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.centaline.trans.task.service.UnlocatedTaskService#listUnlocateTask
	 * (java.lang.String)
	 */
	@Override
	public List<ToUnlocatedTask> listUnlocateTask(String candidateId) {
		return toUnlocatedTaskMapper.listUnlocateTask(candidateId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.centaline.trans.task.service.UnlocatedTaskService#
	 * listUnlocateTaskByManager(com.centaline.trans.task.vo.ToUnlocatedTaskVo)
	 */
	@Override
	public List<ToUnlocatedTask> listUnlocateTaskByManager(ToUnlocatedTaskVo vo) {
		return toUnlocatedTaskMapper.listUnlocateTaskByManager(vo);
	}

	@Override
	public int doLocateTask(String candidateId, String taskId) {
		return doLocate(candidateId, taskId);
	}

	private int doLocate(String candidateId, String taskId ) {
		TaskOperate to = new TaskOperate(taskId, "claim");
		to.setAssignee(candidateId);
		ToUnlocatedTask ut=toUnlocatedTaskMapper.findByTaskId(taskId);
		toUnlocatedTaskMapper.deleteByTaskId(taskId);
		TaskVo reVo= workFlowManager.operaterTask(to);
		workFlowManager.doOptTaskPlan(ut.getTaskDfKey(),ut.getCaseCode());
		return 1;
	}

	@Override
	public int doGroupClaim(String candidateId, String taskId) {
		return doLocate(candidateId, taskId);
	}

	@Override
	public int deleteByInstCode(String instCode) {
		return toUnlocatedTaskMapper.deleteByInstCode(instCode);
	}

}
