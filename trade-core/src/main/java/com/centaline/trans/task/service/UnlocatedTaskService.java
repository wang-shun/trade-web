package com.centaline.trans.task.service;

import java.util.List;

import com.centaline.trans.task.entity.ToUnlocatedTask;
import com.centaline.trans.task.vo.ToUnlocatedTaskVo;

public interface UnlocatedTaskService {
	int insert(ToUnlocatedTask unloactedTask);

	int deleteByTaskId(String taskId);

	int deleteByPrimaryKey(String taskId);

	List<ToUnlocatedTask> listUnlocateTask(String candidateId);

	List<ToUnlocatedTask> listUnlocateTaskByManager(ToUnlocatedTaskVo vo);

	int doLocateTask(String candidateId, String taskId);

	int doGroupClaim(String candidateId, String taskId);
}
