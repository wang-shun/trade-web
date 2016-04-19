package com.centaline.trans.kpi.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.kpi.entity.TsKpiPsnMonth;
@MyBatisRepository
public interface TsKpiPsnMonthMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsKpiPsnMonth record);

    int insertSelective(TsKpiPsnMonth record);

    TsKpiPsnMonth selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsKpiPsnMonth record);

    int updateByPrimaryKey(TsKpiPsnMonth record);
    
    List<TsKpiPsnMonth> getTsKpiPsnMonthListByPro(TsKpiPsnMonth record);
    
    int insertTsKpiPsnMonthList(List<TsKpiPsnMonth> list);
}