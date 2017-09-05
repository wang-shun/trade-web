package com.centaline.trans.task.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.AuditCase;
import com.centaline.trans.task.entity.ToSign;

@MyBatisRepository
public interface AuditCaseMapper {
	
    List<AuditCase> getAuditCaseList(int month);
    
    AuditCase getValidCaseCount(int month);
}