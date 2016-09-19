package com.centaline.trans.engine.service;

import com.centaline.trans.engine.vo.TaskVo;

public interface TaskSubmitLogService {
	void record(TaskVo task);

	void record(String taskId);
}
