package com.centaline.trans.spv.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToCashFlow;

@MyBatisRepository
public interface ToCashFlowMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCashFlow record);

    int insertSelective(ToCashFlow record);

    ToCashFlow selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCashFlow record);

    int updateByPrimaryKey(ToCashFlow record);
    
    List<ToCashFlow> queryCashFlowsByCaseCode(String caseCode);
    
    List<ToCashFlow> findCashFlowByCaseCodeAndDirection(@Param("caseCode") String caseCode,@Param("flowDirection") String flowDirection);

}