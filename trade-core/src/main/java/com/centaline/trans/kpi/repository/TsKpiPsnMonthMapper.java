package com.centaline.trans.kpi.repository;

import com.centaline.trans.kpi.entity.TsKpiPsnMonth;

public interface TsKpiPsnMonthMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsKpiPsnMonth record);

    int insertSelective(TsKpiPsnMonth record);

    TsKpiPsnMonth selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsKpiPsnMonth record);

    int updateByPrimaryKey(TsKpiPsnMonth record);
}