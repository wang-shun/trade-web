package com.centaline.trans.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkFlowConstant {
	/**
	 * 定义http请求方式
	 */
	/* http post 请求 */
	public static final String HTTP_TYPE_POST = "POST";
	/* http get 请求 */
	public static final String HTTP_TYPE_GET = "GET";
	/* http put 请求 */
	public static final String HTTP_TYPE_PUT = "PUT";
	/* http delete 请求 */
	public static final String HTTP_TYPE_DELETE = "DELETE";
	/* 候选人 */
	public static final String IDENTITYLINKS_TYPE_CANDIDATE = "candidate";
	/**
	 * 启动流程
	 */
	public static final String START_PROCESS_INSTANCE_KEY = "start-process-instance";
	public static final String START_PROCESS_INSTANCE = HTTP_TYPE_POST
			+ "runtime/process-instances";
	/**
	 * 查询task
	 */
	public static final String TASK_LIST = HTTP_TYPE_GET + "runtime/tasks";
	public static final String TASK_LIST_KEY = HTTP_TYPE_GET + "list-tasks";
	/**
	 * Task操作
	 */
	public static final String TASK_OPERATE_KEY = "task-operate";
	public static final String TASK_OPERATE = HTTP_TYPE_POST
			+ "runtime/tasks/{taskId}";
	/**
	 * 获得流程group
	 */
	public static final String GET_ALL_IDENTITYLINKS_FOR_GROUP_KEY = "get-all-identitylinks-for-groups";
	public static final String GET_ALL_IDENTITYLINKS_FOR_GROUP = HTTP_TYPE_GET
			+ "runtime/tasks/{taskId}/identitylinks/groups";
	/**
	 * 抛出全局信号
	 */
	public static final String SIGNAL_RECEIVED_KEY = "signal-event-received";
	public static final String SIGNAL_RECEIVED = HTTP_TYPE_POST
			+ "runtime/signals";
	/**
	 * 挂起流程操作
	 */
	public static final String ACTIVATE_OR_SUSPEND_PROCESS_KEY = "activate-or-suspend-process";
	public static final String ACTIVATE_OR_SUSPEND_PROCESS = HTTP_TYPE_PUT
			+ "runtime/process-instances/{processInstanceId}";
	/**
	 * 执行execution action
	 */
	public static final String EXECUTE_EXECUTION_ACTION_KEY = "execute-execution-action";
	public static final String EXECUTE_EXECUTION_ACTION = HTTP_TYPE_PUT
			+ "runtime/executions/{executionId}";
	/**
	 * 删除指定流程
	 */
	public static final String DELETE_PROCESS_INSTANCE_KEY = "delete-process-instance";
	public static final String DELETE_PROCESS_INSTANCE = HTTP_TYPE_DELETE
			+ "runtime/process-instances/{processInstanceId}";
	/**
	 * 获得流程变量
	 */
	public static final String GET_PROCESS_VARIABLES_KEY = HTTP_TYPE_GET
			+ "get-process-variables";
	public static final String GET_PROCESS_VARIABLES = HTTP_TYPE_GET
			+ "runtime/process-instances/{processInstanceId}/variables/{variableName}";
	/**
	 * 获得一个Task
	 */
	public static final String GET_ATASK_KEY = "get-a-task";
	public static final String GET_ATASK = HTTP_TYPE_GET
			+ "runtime/tasks/{taskId}";

	public static final String GET_HISTORY_TASK_KEY = "get-history-task";
	public static final String GET_HISTORY_TASK = HTTP_TYPE_GET
			+ "history/historic-task-instances";
	
	public static final String PUT_UPDATE_TASK_KEY="update-task";
	public static final String PUT_UPDATE_TASK=HTTP_TYPE_PUT+"runtime/tasks/{taskId}";

	/**
	 * 返回成功状态
	 */
	public static final List<Integer> SCUESSFUL_STATUS_CODE = Arrays.asList(
			200, 202, 201, 204);
	/**
	 * 所有可选操作
	 */
	public static final Map<String, String> WORK_FLOW_OPREATE;
	static {
		WORK_FLOW_OPREATE = new HashMap<>();
		WORK_FLOW_OPREATE.put(START_PROCESS_INSTANCE_KEY,
				START_PROCESS_INSTANCE);
		WORK_FLOW_OPREATE.put(TASK_LIST_KEY, TASK_LIST);
		WORK_FLOW_OPREATE.put(TASK_OPERATE_KEY, TASK_OPERATE);
		WORK_FLOW_OPREATE.put(GET_ALL_IDENTITYLINKS_FOR_GROUP_KEY,
				GET_ALL_IDENTITYLINKS_FOR_GROUP);
		WORK_FLOW_OPREATE.put(SIGNAL_RECEIVED_KEY, SIGNAL_RECEIVED);
		WORK_FLOW_OPREATE.put(ACTIVATE_OR_SUSPEND_PROCESS_KEY,
				ACTIVATE_OR_SUSPEND_PROCESS);
		WORK_FLOW_OPREATE.put(EXECUTE_EXECUTION_ACTION_KEY,
				EXECUTE_EXECUTION_ACTION);
		WORK_FLOW_OPREATE.put(DELETE_PROCESS_INSTANCE_KEY,
				DELETE_PROCESS_INSTANCE);
		WORK_FLOW_OPREATE.put(GET_PROCESS_VARIABLES_KEY, GET_PROCESS_VARIABLES);
		WORK_FLOW_OPREATE.put(GET_ATASK_KEY, GET_ATASK);
		WORK_FLOW_OPREATE.put(GET_HISTORY_TASK_KEY, GET_HISTORY_TASK);
		WORK_FLOW_OPREATE.put(PUT_UPDATE_TASK_KEY, PUT_UPDATE_TASK);
	}
}
