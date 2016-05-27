package com.centaline.trans.workflow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.FindUserLogic;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;

@Controller
@RequestMapping(value = "/workflow")
public class WorkFlowTest {
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private FindUserLogic findUserLogic;
	@Autowired
	private PropertyUtilsService propertyUtilsService;

	@RequestMapping(value = "/start")
	@ResponseBody
	public StartProcessInstanceVo startWorkFlow() {
		List<RestVariable> vars = new ArrayList<>();
		vars.add(new RestVariable("applyUser", "jjm"));
		ProcessInstance process = new ProcessInstance(
				"operation_process:3:222710", "MyTest", vars);
		StartProcessInstanceVo vo = workFlowManager.startWorkFlow(process);
		return vo;
	}

	@RequestMapping(value = "/claimTask")
	@ResponseBody
	public TaskVo claimTask(String id, String username) {
		TaskOperate opt = new TaskOperate(id, "claim");
		opt.setAssignee(username);

		return workFlowManager.operaterTask(opt);
	}

	@RequestMapping(value = "/test")
	@ResponseBody
	public PageableVo test(TaskHistoricQuery tq) {

		return workFlowManager.listHistTasks(tq);
	}

	@RequestMapping(value = "/getVar")
	@ResponseBody
	public Object getVar(String id, String name) {

		return workFlowManager.getVar(id, name);
	}

	@RequestMapping(value = "/delegate")
	@ResponseBody
	public TaskVo delegate(String id, String username) {
		TaskOperate opt = new TaskOperate(id, "delegate");
		opt.setAssignee(username);

		return workFlowManager.operaterTask(opt);
	}

	@RequestMapping(value = "/executeExecutionAction")
	@ResponseBody
	public Map executeExecutionAction(String executionId) {
		return workFlowManager.executeExecutionAction(executionId, "signal");
	}

	@RequestMapping(value = "/deleteProcess")
	@ResponseBody
	public void deleteProcess(String processInstanceId) {
		workFlowManager.deleteProcess(processInstanceId);
	}

	@RequestMapping(value = "/resolve")
	@ResponseBody
	public TaskVo resolve(String id) {
		TaskOperate opt = new TaskOperate(id, "resolve");
		return workFlowManager.operaterTask(opt);
	}

	@RequestMapping(value = "/complete")
	@ResponseBody
	public TaskVo complete(String id) {
		TaskOperate opt = new TaskOperate(id, "complete");
		return workFlowManager.operaterTask(opt);
	}

	@RequestMapping(value = "/suspend")
	@ResponseBody
	public Object getDefVals(String processInstanceId, boolean activate) {
		workFlowManager.activateOrSuspendProcessInstance(processInstanceId,
				activate);
		return null;
	}

	@RequestMapping(value = "/getDefKey")
	@ResponseBody
	public Object getDefKey(String process) {
		return propertyUtilsService.getProcessDfId(process);
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public TaskVo queryTask(String id) {
		TaskQuery tq = new TaskQuery(id, true);
		return (TaskVo) workFlowManager.listTasks(tq).getData().get(0);
	}

	@RequestMapping(value = "/findUser")
	public void test() {
		Map<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("30004010", "ff8080814f491b78014f4a05a7d80022");
		System.err
				.println("findUserLogic.findWorkFlowUser:::::::::::::::::::::::::::::::"
						+ findUserLogic.findWorkFlowUser("consultant",
								"ff8080814f490473014f49c6f1e30009", serviceMap,
								"Guohu", ""));

		// ff8080814f490473014f49c6f1e30009
	}
}
