package com.centaline.trans.eval.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaSettleUpdateLog;
/**
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToEvaSettleUpdateLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaSettleUpdateLog record);

    int insertSelective(ToEvaSettleUpdateLog record);

    ToEvaSettleUpdateLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaSettleUpdateLog record);

    int updateByPrimaryKey(ToEvaSettleUpdateLog record);
    
    int updateEvaLogByCaseCode(ToEvaSettleUpdateLog record);
    
    List<ToEvaSettleUpdateLog> selectUpdateLogByCaseCode(String caseCode);
    
}