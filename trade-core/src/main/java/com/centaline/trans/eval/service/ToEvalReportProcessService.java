package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvalReportProcess;
/***
 * 
 * @author wbwangxj
 *
 */
public interface ToEvalReportProcessService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalReportProcess record);

    int insertSelective(ToEvalReportProcess record);

    ToEvalReportProcess selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalReportProcess record);

    int updateByPrimaryKey(ToEvalReportProcess record);
    
    ToEvalReportProcess findToEvalReportProcessByCaseCode(String caseCode);
}
