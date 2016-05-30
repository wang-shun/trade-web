package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaFeeRecord;

public interface ToEvaFeeRecordService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaFeeRecord record);

    int insertSelective(ToEvaFeeRecord record);

    ToEvaFeeRecord selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaFeeRecord record);

    int updateByPrimaryKey(ToEvaFeeRecord record);
    
    ToEvaFeeRecord findToEvaFeeRecordByCaseCode(String caseCode);
}
