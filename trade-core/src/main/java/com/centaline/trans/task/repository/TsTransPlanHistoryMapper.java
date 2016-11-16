package com.centaline.trans.task.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TsTransPlanHistory;
import com.centaline.trans.task.vo.TransPlanVO;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;
@MyBatisRepository
public interface TsTransPlanHistoryMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTransPlanHistory record);

    int insertSelective(TsTransPlanHistory record);

    TsTransPlanHistory selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTransPlanHistory record);

    int updateByPrimaryKey(TsTransPlanHistory record);
    
    List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO);

	List<TsTransPlanHistoryVO> queryTtsTransPlanHistorys(TsTransPlanHistoryVO tsTransPlanHistoryVO);
}