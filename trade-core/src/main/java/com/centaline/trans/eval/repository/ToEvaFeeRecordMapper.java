package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
@MyBatisRepository
public interface ToEvaFeeRecordMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaFeeRecord record);

    int insertSelective(ToEvaFeeRecord record);

    ToEvaFeeRecord selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaFeeRecord record);

    int updateByPrimaryKey(ToEvaFeeRecord record);
    
    ToEvaFeeRecord findToEvaFeeRecordByCaseCode(String caseCode);

}