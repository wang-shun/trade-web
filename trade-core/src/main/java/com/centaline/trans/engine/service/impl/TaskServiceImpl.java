package com.centaline.trans.engine.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.service.TaskSubmitLogService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;

/**
 * 流程任务Service
 * 
 * @author jimmy
 *
 */
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private WorkFlowEngine engine;
	@Autowired
	private TaskSubmitLogService taskCompleteService;
	@Override
	public void claim(String taskId, String assignee) {
		 claim(taskId, assignee, null);
	}

	@Override
	public void claim(String taskId, String assignee, Map<String, Object> vars) {
		operaterTask(taskId, "claim", assignee, vars);
	}

	@Override
	public void complete(String taskId) {
		complete(taskId, null);
	}

	@Override
	public void complete(String taskId, Map<String, Object> vars) {
		taskCompleteService.record(taskId); 
		operaterTask(taskId, "complete", null, vars);
	}

	@Override
	public void delegate(String taskId, String assignee) {
		 delegate(taskId, assignee, null);
	}

	@Override
	public void delegate(String taskId, String assignee, Map<String, Object> vars) {
		 operaterTask(taskId, "delegate", assignee, vars);
	}

	@Override
	public void resolve(String taskId) {
		 resolve(taskId, null);
	}

	@Override
	public void resolve(String taskId, Map<String, Object> vars) {
		 operaterTask(taskId, "resolve", null, vars);
	}

	@Override
	public void submitTask(String taskId) {
		 submitTask(taskId);
	}

	@Override
	public void submitTask(String taskId, Map<String, Object> vars) {
		TaskVo task = getTask(taskId);
		if (WorkFlowEnum.WSPENDING.getCode().equals(task.getDelegationState())) {
			this.resolve(taskId);
		}
		this.complete(taskId, vars);
	}

	@Override
	public TaskVo getTask(String taskId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("taskId", taskId);
		return (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_ATASK_KEY, TaskVo.class, vars, null);
	}

	private TaskVo operaterTask(String taskId, String action, String assignee, Map<String, Object> vars) {
		TaskOperate taskOperate = new TaskOperate(taskId, action);
		taskOperate.setAssignee(assignee);
		taskOperate.setVariables(WorkFlowUtils.mapToVarList(vars));
		TaskVo vo = (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.TASK_OPERATE_KEY, TaskVo.class, taskOperate, null);
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId) {
		Boolean finished = null;
		return listTasks(processInstanceId, finished);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, Boolean finished) {
		Boolean processFinished = null;
		return listTasks(processInstanceId, finished, processFinished);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished) {
		Boolean active = null;
		return listTasks(processInstanceId, finished, processFinished, active);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active) {
		return listTasks(processInstanceId, finished, processFinished, active, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active,
			String assignee) {
		return listTasksBase(processInstanceId, finished, processFinished, active, assignee, false);
	}

	@SuppressWarnings("rawtypes")
	public PageableVo listTasksBase(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active,
			String assignee, boolean queryHistory) {
		Map<String, String> map = new HashMap<>();
		map.put("processInstanceId", processInstanceId);
		if(finished != null) {
			map.put("finished", String.valueOf(finished));
		}
		if(processFinished != null) {
			map.put("processFinished", String.valueOf(processFinished));
		}
		if(active != null) {
			map.put("active", String.valueOf(active));
		}
		if(StringUtils.isNotBlank(assignee)) {
			map.put("assignee", String.valueOf(assignee));
		}
		String optKey;
		if (queryHistory) {
			optKey = WorkFlowConstant.GET_HISTORY_TASK_KEY;
		} else {
			optKey = WorkFlowConstant.TASK_LIST_KEY;
		}
		PageableVo vo = (PageableVo) engine.RESTfulWorkFlow(optKey, PageableVo.class, map);
		WorkFlowUtils.convertPageableData(vo, TaskVo.class);
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, String assignee) {
		Boolean finished = null;
		return listTasks(processInstanceId, finished, assignee);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, Boolean finished, String assignee) {
		Boolean processFinished = null;
		return listTasks(processInstanceId, finished, processFinished, assignee);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished, String assignee) {
		Boolean active = null;
		return listTasks(processInstanceId, finished, processFinished, active, assignee);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId) {
		Boolean finished = null;
		return listHistTasks(processInstanceId, finished);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, Boolean finished) {
		Boolean processFinished = null;
		return listHistTasks(processInstanceId, finished, processFinished);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished) {
		Boolean active = null;
		return listHistTasks(processInstanceId, finished, processFinished, active);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished,
			Boolean active) {
		return listHistTasks(processInstanceId, finished, processFinished, active, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active,
			String assignee) {
		return listTasksBase(processInstanceId, finished, processFinished, active, assignee, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, String assignee) {
		Boolean finished = null;
		return listHistTasks(processInstanceId, finished, assignee);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, Boolean finished, String assignee) {
		Boolean processFinished = null;
		return listHistTasks(processInstanceId, finished, processFinished, assignee);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished,
			String assignee) {
		Boolean active = null;
		return listHistTasks(processInstanceId, finished, processFinished, active, assignee);
	}

	@Override
	public void updateAssignee(String taskId, String assignee) {
		Map<String, String> vars = new HashMap<>();
		vars.put("taskId", taskId);
		TaskVo nVo = new TaskVo();
		nVo.setAssignee(assignee);
		 engine.RESTfulWorkFlow(WorkFlowConstant.PUT_UPDATE_TASK_KEY, TaskVo.class, nVo, vars);
	}

	@Override
	public TaskVo getHistTask(String taskId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("taskId", taskId);
		return (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_HIS_TASK_KEY, TaskVo.class, vars, null);
	}
}
