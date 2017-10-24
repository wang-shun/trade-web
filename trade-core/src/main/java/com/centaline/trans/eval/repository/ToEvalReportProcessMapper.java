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
    
    ToEvalReportProcess findToEvalReportProcessByEvalCode(String evalCode);
    
    int ToEvalReportProcess(ToEvalReportProcess toEvalReportProcess);
    
    /**
     * 
     * @param toEvalReportProcess
     * @return
     * @author jinwl6
     */
    int updateChangeInfoByEvalCode(ToEvalReportProcess toEvalReportProcess);
    
    /**
     * 
     * @param toEvalReportProcess
     * @return
     * @author jinwl6
     */
    int updateStatusByEvalCode(ToEvalReportProcess toEvalReportProcess);
    
    /**
     * 根据案件号和评估状态查询评估信息
     * @param caseCode
     * @return
     * @author jinwl6
     */
    ToEvalReportProcess selectToEvaReportProcessByCaseCodeAndStatus(String caseCode);
    
    /**
     * 根据ID更新评估流程信息
     * @param record
     * @return
     * @author jinwl6
     */
    int updateEvaReportByPkid(ToEvalReportProcess record);
    
    int updateEvaReportByEvaCode(ToEvalReportProcess record);
    
    /**
     * 根据条件删除评估单
     * @param record
     * @return
     * @author jinwl6
     */
    int deleteToEvalReportProcessByEvalCode(ToEvalReportProcess record);
}