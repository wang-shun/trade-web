package com.centaline.trans.kpi.repository;

import com.centaline.trans.kpi.entity.TsKpiSrvCase;

public interface TsKpiSrvCaseMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsKpiSrvCase record);

    int insertSelective(TsKpiSrvCase record);

    TsKpiSrvCase selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsKpiSrvCase record);

    int updateByPrimaryKey(TsKpiSrvCase record);
}