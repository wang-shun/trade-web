package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaRefund;
@MyBatisRepository
public interface ToEvaRefundMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaRefund record);

    int insertSelective(ToEvaRefund record);

    ToEvaRefund selectByPrimaryKey(Long pkid);
    
    ToEvaRefund selectByCaseCode(String caseCode);

    int updateByPrimaryKeySelective(ToEvaRefund record);

    int updateByPrimaryKey(ToEvaRefund record);
    
    /***
     * 根据评估单号查询退费信息
     * @param evaCode
     * @return
     * @author jinwl6
     */
    ToEvaRefund selectByEvaCode(String evaCode);
}