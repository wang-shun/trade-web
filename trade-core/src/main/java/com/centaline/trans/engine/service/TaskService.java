package com.centaline.trans.engine.service;

import java.util.Map;

import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;

/**
 * 流程任务处理接口.
 * 
 * @author jimmy
 * 
 */
public interface TaskService {
	/**
	 * 任务签收
	 * 
	 * @param taskId
	 * @param assignee
	 * @return
	 */
	void claim(String taskId, String assignee);

	/**
	 * 任务签收
	 * 
	 * @param taskId
	 * @param assignee
	 * @param vars
	 * @return
	 */
	void claim(String taskId, String assignee, Map<String, Object> vars);

	/**
	 * 任务完成
	 * 
	 * @param taskId
	 * @return
	 */
	void complete(String taskId);

	/**
	 * 任务完成
	 * 
	 * @param taskId
	 * @param vars
	 * @return
	 */
	void complete(String taskId, Map<String, Object> vars);

	/**
	 * 任务委派
	 * 
	 * @param taskId
	 * @param assignee
	 * @return
	 */
	void delegate(String taskId, String assignee);

	/**
	 * 任务委派
	 * 
	 * @param taskId
	 * @param assignee
	 * @param vars
	 * @return
	 */
	void delegate(String taskId, String assignee, Map<String, Object> vars);

	/**
	 * 撤消委托
	 * 
	 * @param taskId
	 * @return
	 */
	void resolve(String taskId);

	/**
	 * 撤消委托
	 * 
	 * @param taskId
	 * @param vars
	 * @return
	 */
	void resolve(String taskId, Map<String, Object> vars);

	/**
	 * 任务提交
	 * 
	 * @param taskId
	 * @return
	 */
	void submitTask(String taskId);

	/**
	 * 任务提交
	 * 
	 * @param taskId
	 * @param vars
	 * @return
	 */
	void submitTask(String taskId, Map<String, Object> vars);

	/**
	 * 根据taskId获得一个TaskVo对象
	 * 
	 * @param taskId
	 * @return
	 */
	TaskVo getTask(String taskId);

	/**
	 * 查询任务
	 * 
	 * @param processInstanceId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, Boolean finished);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active,
			String assignee);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, String assignee);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, Boolean finished, String assignee);

	@SuppressWarnings("rawtypes")
	PageableVo listTasks(String processInstanceId, Boolean finished, Boolean processFinished, String assignee);

	/**
	 * 查询历史任务(包括Ru表中的任务)
	 * 
	 * @param processInstanceId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, Boolean finished);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished, Boolean active,
			String assignee);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, String assignee);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, Boolean finished, String assignee);

	@SuppressWarnings("rawtypes")
	PageableVo listHistTasks(String processInstanceId, Boolean finished, Boolean processFinished, String assignee);

	void updateAssignee(String taskId, String assignee);

	TaskVo getHistTask(String taskId);

}
