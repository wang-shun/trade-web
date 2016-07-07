package com.centaline.trans.task.service;

import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.TlTaskReassigntLog;

public interface TlTaskReassigntLogService {
	int record(TlTaskReassigntLog log);

	int record(TaskVo task, String newAssignee, String caseCode);
}
