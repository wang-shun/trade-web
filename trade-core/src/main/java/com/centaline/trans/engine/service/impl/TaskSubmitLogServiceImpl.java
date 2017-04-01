package com.centaline.trans.engine.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.engine.service.TaskSubmitLogService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.TlTaskSubmitLog;
import com.centaline.trans.task.repository.TlTaskSubmitLogMapper;

@Service
public class TaskSubmitLogServiceImpl implements TaskSubmitLogService {
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TlTaskSubmitLogMapper taskSubmitLogMapper;
	
	@Override
	public void record(TaskVo task) {
		SessionUser user = uamSessionService.getSessionUser();
		TlTaskSubmitLog taskSubmitLog = new TlTaskSubmitLog();
		taskSubmitLog.setExecutor(user.getUsername());
		taskSubmitLog.setExecutorId(user.getId());
		taskSubmitLog.setTaskId(task.getId());
		Map<String, Object> extProps = user.getExtProps();
		if (extProps != null) {
			if (extProps.get("teamId") != null) {
				taskSubmitLog.setTeamId(extProps.get("teamId").toString());
			}
			if (extProps.get("managerId") != null) {
				taskSubmitLog.setManagerId(extProps.get("managerId").toString());
			}
			if (extProps.get("districtId") != null) {
				taskSubmitLog.setDistrictId(extProps.get("districtId").toString());
			}
			if (extProps.get("directorId") != null) {
				taskSubmitLog.setDirectorId(extProps.get("directorId").toString());
			}
			if (extProps.get("hqId") != null) {
				taskSubmitLog.setHqId(extProps.get("hqId").toString());
			}
			if (extProps.get("generalManagerId") != null) {
				taskSubmitLog.setGeneralManagerId(extProps.get("generalManagerId").toString());
			}
			if (extProps.get("seniorManagerId") != null) {
				taskSubmitLog.setSeniorManagerId(extProps.get("seniorManagerId").toString());
			}
		}
		taskSubmitLogMapper.insertSelective(taskSubmitLog);

	}

	@Override
	public void record(String taskId) {
		//这里这样写是因为现在只记录taskId不需要记录其它信息，如果以后要记录其它信息的话直接改这里就好了
		//TaskVo task = taskService.getHistTask(taskId);
		TaskVo task=new TaskVo();
		task.setId(Long.valueOf(taskId));
		record(task);
	}

}
