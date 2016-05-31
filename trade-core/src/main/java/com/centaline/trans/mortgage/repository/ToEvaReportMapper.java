package com.centaline.trans.mortgage.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToEvaReport;

@MyBatisRepository
public interface ToEvaReportMapper {
    int insert(ToEvaReport record);

    int insertSelective(ToEvaReport record);
    
    List<ToEvaReport> findToEvaReportByEvaCode(ToEvaReport toEvaReport);
    
    int update(ToEvaReport toEvaReport);

	ToEvaReport findFinalComByCaseCode(String caseCode);
	
	ToEvaReport findToEvaReportById(Long pkid);
	
	ToEvaReport findByProcessId(String evaProcessId);
}