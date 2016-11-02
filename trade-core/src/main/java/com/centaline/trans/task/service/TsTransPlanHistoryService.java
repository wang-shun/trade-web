package com.centaline.trans.task.service;

import java.util.List;

import com.centaline.trans.task.entity.TsTransPlanHistory;
import com.centaline.trans.task.vo.TransPlanVO;

public interface TsTransPlanHistoryService {
	int deleteByPrimaryKey(Long pkid);

    int insert(TsTransPlanHistory record);

    int insertSelective(TsTransPlanHistory record);

    TsTransPlanHistory selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTransPlanHistory record);

    int updateByPrimaryKey(TsTransPlanHistory record);
    
    List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO);
}
