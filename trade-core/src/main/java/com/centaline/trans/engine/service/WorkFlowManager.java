package com.centaline.trans.engine.service;

import java.util.List;
import java.util.Map;

import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.ExecuteGet;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.SignalEvent;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;

public interface WorkFlowManager {

	RestVariable getVar(String processInstanceId, String variableName);

	/**
	 * 流程启动方法
	 * 
	 * @param process
	 * @return
	 */
	public abstract StartProcessInstanceVo startWorkFlow(ProcessInstance process);

	/**
	 * 案件分配流程启动
	 * 
	 * @param process
	 * @return
	 */
	public abstract StartProcessInstanceVo startCaseWorkFlow(ProcessInstance process, String userName, String caseCode);

	public abstract StartProcessInstanceVo startCaseWorkFlow2(ProcessInstance process, String userName,
			String authUserName);

	/**
	 * 查询task方法
	 * 
	 * @param tq
	 * @return
	 */
	public abstract PageableVo listTasks(TaskQuery tq);

	/**
	 * 查询task方法
	 * 
	 * @param tq
	 * @return
	 */
	public abstract PageableVo listHistTasks(TaskHistoricQuery tq);

	public abstract TaskVo operaterTask(TaskOperate taskOperate);

	public void signalEventReceived(SignalEvent event);

	/**
	 * 扶起或唤醒流程
	 * 
	 * @param processInstanceId
	 * @param activate
	 */

	void activateOrSuspendProcessInstance(String processInstanceId, boolean activate);


	/**
	 * 删除一条流程
	 * 
	 * @param processInstanceId
	 */
	void deleteProcess(String processInstanceId);

	TaskVo getTask(String taskId);

	public Boolean submitTask(List<RestVariable> variables, String taskId, String processInstanceId, String caseowner,
			String caseCode);

	void claim(List<RestVariable> variables, String taskId, String processInstanceId, String caseowner,
			String caseCode);

	StartProcessInstanceVo startCaseWorkFlow1(ProcessInstance process, String caseCode, String caseOwner);

	void reclaim(String taskId, String assignee);

	TaskVo updateTask(TaskVo vo);

	void doOptTaskPlan(String tsakDfkey, String caseCode);

	ExecutionVo executeAction(ExecuteAction action);

	PageableVo getExecute(ExecuteGet executeGet);

}