package com.centaline.trans.transplan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
@MyBatisRepository
public interface TtsTransPlanHistoryBatchMapper {
    int insert(TtsTransPlanHistoryBatch record);

    int insertSelective(TtsTransPlanHistoryBatch record);
    
    int updateTtsTransPlanHistoryBatchMapper(TtsTransPlanHistoryBatch record);
}