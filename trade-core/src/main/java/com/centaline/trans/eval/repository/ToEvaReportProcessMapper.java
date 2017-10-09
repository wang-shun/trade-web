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
    
    /**
     * 根据案件号和评估状态查询评估信息
     * @param caseCode
     * @return
     */
    ToEvaReportProcess selectToEvaReportProcessByCaseCodeAndStatus(String caseCode);
    
    /**
     * 根据ID更新评估流程信息
     * @param record
     * @return
     */
    int updateEvaReportByPkid(ToEvaReportProcess record);
}