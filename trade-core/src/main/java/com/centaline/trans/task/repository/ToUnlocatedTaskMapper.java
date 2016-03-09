package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToUnlocatedTask;
import com.centaline.trans.task.vo.ToUnlocatedTaskVo;

@MyBatisRepository
public interface ToUnlocatedTaskMapper {
	int insert(ToUnlocatedTask unloactedTask);

	int deleteByTaskId(String taskId);

	int deleteByPrimaryKey(String taskId);
	
	ToUnlocatedTask findByTaskId(String taskId);

	List<ToUnlocatedTask> listUnlocateTask(String candidateId);

	List<ToUnlocatedTask> listUnlocateTaskByManager(ToUnlocatedTaskVo vo);
}
