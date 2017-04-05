package com.centaline.trans.mgr.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mgr.entity.TsSup;

@MyBatisRepository
public interface TsSupMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsSup record);

    int insertSelective(TsSup record);

    TsSup selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsSup record);

    int updateByPrimaryKey(TsSup record);
    
    List<TsSup> findTsSupByFinOrgCode(@Param("finOrgCode") String finOrgCode);
    
    TsSup findSupByFinOrgCodeAndSupCat(@Param("finOrgCode") String finOrgCode,@Param("supCat") String supCat,@Param("pkid") Long pkid);
}