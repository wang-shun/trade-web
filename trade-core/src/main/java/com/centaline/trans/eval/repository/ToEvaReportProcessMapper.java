package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaReportProcess;

@MyBatisRepository
public interface ToEvaReportProcessMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaReportProcess record);

    int insertSelective(ToEvaReportProcess record);

    ToEvaReportProcess selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaReportProcess record);

    int updateByPrimaryKey(ToEvaReportProcess record);
    
    ToEvaReportProcess selectToEvaReportProcessByCaseCode(String caseCode);
}