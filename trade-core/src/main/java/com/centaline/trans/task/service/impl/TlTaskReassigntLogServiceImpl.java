package com.centaline.trans.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.TlTaskReassigntLog;
import com.centaline.trans.task.repository.TlTaskReassigntLogMapper;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
@Service
public class TlTaskReassigntLogServiceImpl implements TlTaskReassigntLogService {
	@Autowired
	private TlTaskReassigntLogMapper mapper;
	@Override
	public int record(TaskVo task,String newAssignee,String caseCode) {
		TlTaskReassigntLog record=new TlTaskReassigntLog();
		 record.setCaseCode(caseCode);
		 record.setOriAssignee(task.getAssignee());
		 record.setNewAssignee(newAssignee);
		 record.setProcInstId(task.getProcessInstanceId());
		 record.setTaskDefKey(task.getTaskDefinitionKey());
		 record.setTaskId(String.valueOf(task.getId()));
		return mapper.insertSelective(record);
	}
	@Override
	public int record(TlTaskReassigntLog record) {
		return mapper.insertSelective(record);
	}
}
