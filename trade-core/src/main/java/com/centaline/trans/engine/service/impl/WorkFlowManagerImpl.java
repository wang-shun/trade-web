package com.centaline.trans.engine.service.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aist.uam.auth.remote.UamSessionService;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.ExecuteGet;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.SignalEvent;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.FindUserLogic;
import com.centaline.trans.engine.service.TaskSubmitLogService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.entity.ToUnlocatedTask;
import com.centaline.trans.task.entity.TsTaskPlanSet;
import com.centaline.trans.task.service.TaskPlanSetService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.service.TsTaskDelegateService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.utils.BeanToMapUtils;

@Component
public class WorkFlowManagerImpl implements WorkFlowManager {
	@Autowired
	private WorkFlowEngine engine;
	@Autowired
	private TsTaskDelegateService tsTaskDelegateService;
	@Autowired
	private FindUserLogic findUserLogic;
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private TaskPlanSetService taskPlanSetService;
	@Autowired
	private UamSessionService uamSesstionService;
	@Autowired
	private TaskSubmitLogService taskSubmitLogService;
	static Map<String, String> claimByActivitMap;
	static {
		claimByActivitMap = new HashMap<>();
		claimByActivitMap.put("ComLoan_Process", "ComLoan_Process:6:712521");
		claimByActivitMap.put("LoanLost_Process", "LoanLost_Process:3:712524");
		claimByActivitMap.put("PSFLoan_Process", "PSFLoan_Process:3:712530");
		claimByActivitMap.put("offline_eva", "offline_eva:3:712527");
		claimByActivitMap.put("service_change", "service_change:6:712533");
		claimByActivitMap.put("serviceRestart", "serviceRestart:9:805003");
		claimByActivitMap.put("operation_process", "operation_process:57:712545");
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.centaline.trans.engine.core.impl.WorkFowManager#startWorkFlow(com
	 * .centaline.trans.engine.bean.ProcessInstance)
	 */
	@Override
	public StartProcessInstanceVo startWorkFlow(ProcessInstance process) {
		StartProcessInstanceVo obj = (StartProcessInstanceVo) engine
				.RESTfulWorkFlow(WorkFlowConstant.START_PROCESS_INSTANCE_KEY, StartProcessInstanceVo.class, process);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.centaline.trans.engine.core.impl.WorkFowManager#listTasks(com.
	 * centaline .trans.engine.bean.TaskQuery)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageableVo listTasks(TaskQuery tq) {
		Map<String, String> map = null;
		try {
			map = BeanToMapUtils.convertBean(tq);
		} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}
		PageableVo vo = (PageableVo) engine.RESTfulWorkFlow(WorkFlowConstant.TASK_LIST_KEY, PageableVo.class, map);
		convertPageableData(vo, TaskVo.class);
		setTasksGroups(vo.getData());
		return vo;
	}

	/**
	 * 设置task group信息
	 * 
	 * @param tasks
	 */
	private void setTasksGroups(List<TaskVo> tasks) {
		for (TaskVo taskVo : tasks) {
			String group = getCandidateGroupByTaskId(taskVo.getId() + "");
			taskVo.setGroup(group);
		}
	}

	/**
	 * 根据taskId 返回Candidategroup
	 * 
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getCandidateGroupByTaskId(String taskId) {
		Map map = new HashMap<String, String>();
		map.put("taskId", taskId);

		List<JSONObject> objs = (List<JSONObject>) engine
				.RESTfulWorkFlow(WorkFlowConstant.GET_ALL_IDENTITYLINKS_FOR_GROUP_KEY, List.class, map, null);
		String tempGrop = "";
		for (JSONObject jsonObject : objs) {
			if (WorkFlowConstant.IDENTITYLINKS_TYPE_CANDIDATE.equals(jsonObject.get("type"))) {
				tempGrop += (jsonObject.getString("group") + "-");
			}
		}
		if (tempGrop.length() > 1) {
			tempGrop = tempGrop.substring(0, tempGrop.length() - 1);
		}
		return tempGrop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.centaline.trans.engine.core.impl.WorkFowManager#operaterTask(com.
	 * centaline.trans.engine.bean.TaskOperate)
	 */
	@Override
	public TaskVo operaterTask(TaskOperate taskOperate) {

		TaskVo vo = (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.TASK_OPERATE_KEY, TaskVo.class, taskOperate, null);
		return vo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void convertPageableData(PageableVo vo, Class cls) {
		if (vo == null || vo.getData() == null || vo.getData().isEmpty()) {
			return;
		}
		List tempList = new ArrayList<>();
		for (Object obj : vo.getData()) {
			if (obj instanceof JSONObject) {
				tempList.add(JSONObject.parseObject(((JSONObject) obj).toJSONString(), cls));
			}
		}
		vo.setData(tempList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StartProcessInstanceVo startCaseWorkFlow(ProcessInstance process, String userName, String caseCode) {

		StartProcessInstanceVo sPVo = this.startWorkFlow(process);
		if (isClaimByActivitVersion(process.getProcessDefinitionId())) {
			return sPVo;
		}
		TaskQuery tq = new TaskQuery(sPVo.getId(), true);
		PageableVo pageableVo = this.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		for (Object taskObj : taskList) {
			TaskVo vo = (TaskVo) taskObj;
			TaskOperate taskOperate = new TaskOperate(vo.getId().toString(), "claim");
			taskOperate.setAssignee(userName);
			TaskVo reVo = operaterTask(taskOperate);
			sPVo.setActiveTaskId(vo.getId() + "");
			doOptTaskPlan(vo.getTaskDefinitionKey(), caseCode);
		}
		return sPVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StartProcessInstanceVo startCaseWorkFlow2(ProcessInstance process, String userName, String authUserName) {

		StartProcessInstanceVo sPVo = null;
		try {
			engine.setAuthUserName(authUserName);
			sPVo = this.startWorkFlow(process);
			if (isClaimByActivitVersion(process.getProcessDefinitionId())) {
				return sPVo;
			}
			TaskQuery tq = new TaskQuery(sPVo.getId(), true);
			PageableVo pageableVo = this.listTasks(tq);
			List<T> taskList = pageableVo.getData();
			for (Object taskObj : taskList) {
				TaskVo vo = (TaskVo) taskObj;
				TaskOperate taskOperate = new TaskOperate(vo.getId().toString(), "claim");
				taskOperate.setAssignee(userName);
				TaskVo reVo = operaterTask(taskOperate);

			}
		} finally {
			engine.setAuthUserName(null);
		}

		return sPVo;
	}

	@Override
	public StartProcessInstanceVo startCaseWorkFlow1(ProcessInstance process, String caseCode, String caseOwner) {
		StartProcessInstanceVo sPVo = this.startWorkFlow(process);
		if (isClaimByActivitVersion(process.getProcessDefinitionId())) {
			return sPVo;
		}

		TaskQuery tq = new TaskQuery(sPVo.getId(), true);
		PageableVo pageableVo = this.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		for (Object taskObj : taskList) {
			TaskVo vo = (TaskVo) taskObj;
			TaskOperate taskOperate = new TaskOperate(vo.getId().toString(), "claim");

			String owner = findUserLogic.findWorkFlowUser(vo.getGroup(), caseOwner,
					tgServItemAndProcessorService.findServiceMap(caseCode), vo.getTaskDefinitionKey(), sPVo.getId());
			if (StringUtils.isBlank(owner) || owner.contains("-")) {
				handleUnAlocation(owner, caseCode, sPVo.getId(), vo.getGroup(), vo.getId() + "",
						vo.getTaskDefinitionKey(), vo.getName());
				continue;
			}
			// do claim
			taskOperate.setAssignee(owner);
			TaskVo reVo = operaterTask(taskOperate);
			doOptTaskPlan(vo.getTaskDefinitionKey(), caseCode);

		}

		return sPVo;
	}

	@Override
	public void claimByInstCode(String instCode, String caseCode, String caseowner) {
		if (isClaimByActivit(instCode)) {
			return;
		}
		TaskQuery tq = new TaskQuery(instCode, true);
		PageableVo pageableVo = this.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		for (Object taskObj : taskList) {
			TaskVo vo = (TaskVo) taskObj;
			if (StringUtils.isBlank(vo.getAssignee())) {
				TaskOperate taskOperate1 = new TaskOperate(vo.getId().toString(), "claim");

				/**/
				String owner = findUserLogic.findWorkFlowUser(vo.getGroup(), caseowner,
						tgServItemAndProcessorService.findServiceMap(caseCode), vo.getTaskDefinitionKey(),
						instCode);
				if (StringUtils.isBlank(owner) || owner.contains("-")) {
					handleUnAlocation(owner, caseCode, instCode, vo.getGroup(), vo.getId() + "",
							vo.getTaskDefinitionKey(), vo.getName());
					continue;
				}
				// do claim
				taskOperate1.setAssignee(owner);
				TaskVo reVo = this.operaterTask(taskOperate1);
				doOptTaskPlan(vo.getTaskDefinitionKey(), caseCode);
				String agentUser = tsTaskDelegateService.getTaskAgent(owner);
				if (!StringUtils.isBlank(agentUser)) {
					// +-do delegate
					TaskOperate taskOperate2 = new TaskOperate(vo.getId().toString(), "delegate");
					taskOperate2.setAssignee(agentUser);
					this.operaterTask(taskOperate2);
				}
			}
		}
	}

	@Override
	public void doOptTaskPlan(String tsakDfkey, String caseCode) {
		TsTaskPlanSet planSet = taskPlanSetService.getAutoTsTaskPlanSetByPartCode(tsakDfkey);
		if (planSet == null)
			return;
		ToTransPlan plan = new ToTransPlan();
		plan.setCaseCode(caseCode);
		plan.setPartCode(tsakDfkey);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, planSet.getPlanDays());
		plan.setEstPartTime(cal.getTime());
		toTransPlanService.updateTransPlan(plan);
	}

	@Override
	public Boolean submitTask(List<RestVariable> variables, String taskId, String processInstanceId, String caseowner,
			String caseCode) {
		TaskVo task = getTask(taskId);
		String loginUser = uamSesstionService.getSessionUser().getUsername();
		if (!loginUser.equals(task.getAssignee())) {
			throw new WorkFlowException("您不是当前任务的经办人，不能提交该任务！");
		}

		TaskOperate taskOperate;
		if (WorkFlowEnum.WSPENDING.getCode().equals(task.getDelegationState())) {
			taskOperate = new TaskOperate(taskId, WorkFlowEnum.WRESOLVE.getCode());
			this.operaterTask(taskOperate);
		}
		taskOperate = new TaskOperate(taskId, "complete");
		taskOperate.setVariables(variables);
		taskSubmitLogService.record(task);//记录提交日志
		this.operaterTask(taskOperate);

		
			
		
		if (isClaimByActivit(processInstanceId)) {
			return true;
		}
		TaskQuery tq = new TaskQuery(task.getProcessInstanceId(), true);
		PageableVo pageableVo = this.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		for (Object taskObj : taskList) {
			TaskVo vo = (TaskVo) taskObj;
			if (StringUtils.isBlank(vo.getAssignee())) {
				TaskOperate taskOperate1 = new TaskOperate(vo.getId().toString(), "claim");

				/**/
				String owner = findUserLogic.findWorkFlowUser(vo.getGroup(), caseowner,
						tgServItemAndProcessorService.findServiceMap(caseCode), vo.getTaskDefinitionKey(),
						processInstanceId);
				if (StringUtils.isBlank(owner) || owner.contains("-")) {
					handleUnAlocation(owner, caseCode, processInstanceId, vo.getGroup(), vo.getId() + "",
							vo.getTaskDefinitionKey(), vo.getName());
					continue;
				}
				// do claim
				taskOperate1.setAssignee(owner);
				TaskVo reVo = this.operaterTask(taskOperate1);
				doOptTaskPlan(vo.getTaskDefinitionKey(), caseCode);
				String agentUser = tsTaskDelegateService.getTaskAgent(owner);
				if (!StringUtils.isBlank(agentUser)) {
					// +-do delegate
					TaskOperate taskOperate2 = new TaskOperate(vo.getId().toString(), "delegate");
					taskOperate2.setAssignee(agentUser);
					this.operaterTask(taskOperate2);
				}
			}
		}

		return true;
	}

	@Override
	public void signalEventReceived(SignalEvent event) {
		Object obj = engine.RESTfulWorkFlow(WorkFlowConstant.SIGNAL_RECEIVED_KEY, String.class, event);
		System.out.println("signalEventReceived return is" + obj);
	}

	@Override
	public void claim(List<RestVariable> variables, String taskId, String processInstanceId, String caseowner,
			String caseCode) {

		TaskQuery tq = new TaskQuery(processInstanceId, true);
		PageableVo pageableVo = this.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		for (Object taskObj : taskList) {
			TaskVo vo = (TaskVo) taskObj;
			if (StringUtils.isBlank(vo.getAssignee())) {
				TaskOperate taskOperate1 = new TaskOperate(vo.getId().toString(), "claim");

				/**/
				String owner = findUserLogic.findWorkFlowUser(vo.getGroup(), caseowner,
						tgServItemAndProcessorService.findServiceMap(caseCode), vo.getTaskDefinitionKey(),
						processInstanceId);
				if (StringUtils.isBlank(owner) || owner.contains("-")) {
					handleUnAlocation(owner, caseCode, processInstanceId, vo.getGroup(), vo.getId() + "",
							vo.getTaskDefinitionKey(), vo.getName());
					continue;
				}
				// do claim
				taskOperate1.setAssignee(owner);
				TaskVo reVo = this.operaterTask(taskOperate1);
				doOptTaskPlan(vo.getTaskDefinitionKey(), caseCode);
			}
		}
	}

	private void handleUnAlocation(String owner, String caseCode, String instCode, String taskJobCode, String taskId,
			String taskDfKey, String taskName) {
		String candidateIds[];
		if (StringUtils.isBlank(owner)) {
			candidateIds = new String[] { null };
		} else {
			candidateIds = owner.split("-");
		}
		for (String candidateId : candidateIds) {
			ToUnlocatedTask ut = new ToUnlocatedTask();
			ut.setCandidateId(candidateId);
			ut.setCaseCode(caseCode);
			ut.setCreateTime(new Date());
			ut.setInstCode(instCode);
			ut.setTaskId(taskId);
			ut.setTaskJobCode(taskJobCode);
			ut.setTaskDfKey(taskDfKey);
			unlocatedTaskService.insert(ut);
		}

		/*
		 * private String caseCode; private String instCode; private String
		 * taskJobCode; private Date crateTime; private String candidateId;
		 * private String taskId;
		 */
	}

	@Override
	public void activateOrSuspendProcessInstance(String processInstanceId, boolean activate) {

		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		vars.put("action", activate ? "activate" : "suspend");
		engine.RESTfulWorkFlow(WorkFlowConstant.ACTIVATE_OR_SUSPEND_PROCESS_KEY, String.class, vars, null);

	}

	@Override
	public void deleteProcess(String processInstanceId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		try {
			engine.RESTfulWorkFlow(WorkFlowConstant.DELETE_PROCESS_INSTANCE_KEY, Map.class, vars, null);
		} catch (WorkFlowException e) {
			if (!e.getMessage().contains("statusCode[404]"))
				throw e;
			e.printStackTrace();
		}
	}

	@Override
	public RestVariable getVar(String processInstanceId, String variableName) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		vars.put("variableName", variableName);
		return (RestVariable) engine.RESTfulWorkFlow(WorkFlowConstant.GET_PROCESS_VARIABLES_KEY, RestVariable.class,
				vars, null);
	}

	@Override
	public TaskVo getTask(String taskId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("taskId", taskId);
		return (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_ATASK_KEY, TaskVo.class, vars, null);
	}

	@Override
	public StartProcessInstanceVo getHistoryInstances(String processInstanceId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		return (StartProcessInstanceVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_HIS_INSTANCES_KEY, StartProcessInstanceVo.class, vars, null);
	}

	@Override
	public TaskVo getHistoryTask(String taskId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("taskId", taskId);
		return (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_HIS_TASK_KEY, TaskVo.class, vars, null);
	}

	@Override
	public TaskVo updateTask(TaskVo vo) {
		Map<String, String> vars = new HashMap<>();
		vars.put("taskId", vo.getId() + "");
		TaskVo nVo = new TaskVo();
		nVo.setAssignee(vo.getAssignee());
		nVo.setDelegationState(vo.getDelegationState());
		nVo.setDescription(vo.getDescription());
		nVo.setDueDate(vo.getDueDate());
		nVo.setName(vo.getName());
		nVo.setOwner(vo.getOwner());
		nVo.setParentTaskId(vo.getParentTaskId());
		nVo.setPriority(vo.getPriority());
		return (TaskVo) engine.RESTfulWorkFlow(WorkFlowConstant.PUT_UPDATE_TASK_KEY, TaskVo.class, nVo, vars);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public PageableVo listHistTasks(TaskHistoricQuery tq) {
		Map<String, String> map = null;
		try {
			map = BeanToMapUtils.convertBean(tq);
		} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}
		PageableVo vo = (PageableVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_HISTORY_TASK_KEY, PageableVo.class,
				map);
		convertPageableData(vo, TaskVo.class);

		return vo;
	}

	@Override
	public ExecutionVo executeAction(ExecuteAction action) {
		return (ExecutionVo) engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
	}

	@Override
	public PageableVo<?> getExecute(ExecuteGet executeGet) {
		Map<String, String> map = null;
		try {
			map = BeanToMapUtils.convertBean(executeGet);
		} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}
		PageableVo<?> vo = (PageableVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_EXECUTE_KEY, PageableVo.class,
				map);
		convertPageableData(vo, ExecutionVo.class);
		return vo;
	}

	@Override
	public void setAssginee(String processInstanceId, String caseowner, String caseCode) {
		if (isClaimByActivit(processInstanceId)) {
			return;
		}
		TaskQuery tq = new TaskQuery(processInstanceId, true);
		PageableVo pageableVo = this.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		for (Object taskObj : taskList) {
			TaskVo vo = (TaskVo) taskObj;
			if (StringUtils.isBlank(vo.getAssignee())) {
				TaskOperate taskOperate1 = new TaskOperate(vo.getId().toString(), "claim");

				/**/
				String owner = findUserLogic.findWorkFlowUser(vo.getGroup(), caseowner,
						tgServItemAndProcessorService.findServiceMap(caseCode), vo.getTaskDefinitionKey(),
						processInstanceId);
				if (StringUtils.isBlank(owner) || owner.contains("-")) {
					handleUnAlocation(owner, caseCode, processInstanceId, vo.getGroup(), vo.getId() + "",
							vo.getTaskDefinitionKey(), vo.getName());
					continue;
				}
				// do claim
				taskOperate1.setAssignee(owner);
				TaskVo reVo = this.operaterTask(taskOperate1);
				doOptTaskPlan(vo.getTaskDefinitionKey(), caseCode);
				String agentUser = tsTaskDelegateService.getTaskAgent(owner);
				if (!StringUtils.isBlank(agentUser)) {
					// +-do delegate
					TaskOperate taskOperate2 = new TaskOperate(vo.getId().toString(), "delegate");
					taskOperate2.setAssignee(agentUser);
					this.operaterTask(taskOperate2);
				}
			}
		}
	}

	@Override
	public RestVariable setVariableByProcessInsId(String processInstanceId, String variableName,
			RestVariable restVariable) {
		Map<String, String> vars = new HashMap<>();
		vars.put("variableName", variableName);
		vars.put("processInstanceId", processInstanceId);
		// vars.put("name", variableName);
		vars.put("type", restVariable.getType());
		vars.put("value", restVariable.getValue() == null ? "" : restVariable.getValue().toString());
		restVariable.setName(variableName);
		return (RestVariable) engine.RESTfulWorkFlow(WorkFlowConstant.PUT_VARIABLE_KEY, RestVariable.class, restVariable, vars);
	}
	
	private boolean isClaimByActivit(String processInstanceId) {
		StartProcessInstanceVo process = getHistoryInstances(processInstanceId);
		String dfId = process.getProcessDefinitionId();
		return isClaimByActivitVersion(dfId);
	}
public static void main(String[] args) {
	System.out.println(isClaimByActivitVersion("operation_process:49:695144"));
}
	private static boolean isClaimByActivitVersion(String dfId) {
		String key = dfId.substring(0, dfId.indexOf(":"));
		String versionFlag = claimByActivitMap.get(key);
		if (versionFlag == null) {
			return true;
		}
		if (versionFlag.compareTo(dfId) >= 0) {
			return false;
		}
		return true;
	}
}
