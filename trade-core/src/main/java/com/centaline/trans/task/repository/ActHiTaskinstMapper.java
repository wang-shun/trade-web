package com.centaline.trans.task.repository;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ActHiTaskinst;

@MyBatisRepository
public interface ActHiTaskinstMapper {
	public List<ActHiTaskinst> getConsultantTask(@Param("taskDfs") Set<String> taskDfs, @Param("processInstanceId")String processInstanceId);
}
