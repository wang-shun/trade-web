package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
/***
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToEvalReportProcessMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalReportProcess record);

    int insertSelective(ToEvalReportProcess record);

    ToEvalReportProcess selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalReportProcess record);

    int updateByPrimaryKey(ToEvalReportProcess record);
    
    ToEvalReportProcess findToEvalReportProcessByCaseCode(String caseCode);
}