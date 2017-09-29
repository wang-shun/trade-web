package com.centaline.trans.eval.service;

import java.util.List;

import com.centaline.trans.eval.entity.ToEvaSettleUpdateLog;

/***
 * 
 * @author wbwangxj
 *  评估结算修改操作日志
 */
public interface ToEvaSettleUpdateLogService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaSettleUpdateLog record);

    int insertSelective(ToEvaSettleUpdateLog record);

    ToEvaSettleUpdateLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaSettleUpdateLog record);

    int updateByPrimaryKey(ToEvaSettleUpdateLog record);
    
    int updateEvaLogByCaseCode(ToEvaSettleUpdateLog record);
    
    List<ToEvaSettleUpdateLog> selectUpdateLogByCaseCode(String caseCode);
}
