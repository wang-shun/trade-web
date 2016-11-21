package com.centaline.trans.transplan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import com.centaline.trans.transplan.vo.TransPlanVO;
@MyBatisRepository
public interface TsTransPlanHistoryMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTransPlanHistory record);

    int insertSelective(TsTransPlanHistory record);

    TsTransPlanHistory selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTransPlanHistory record);

    int updateByPrimaryKey(TsTransPlanHistory record);
    
    List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO);
}