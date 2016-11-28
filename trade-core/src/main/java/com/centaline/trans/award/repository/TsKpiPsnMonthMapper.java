package com.centaline.trans.award.repository;

import java.util.Date;
import java.util.List;

import com.centaline.trans.award.entity.TsKpiPsnMonth;
import com.centaline.trans.common.MyBatisRepository;
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
    
    void getPMonthKpiStastic(Date belongMonth);
    
    int deleteTsKpiPsnMonthByProperty(TsKpiPsnMonth record);
}