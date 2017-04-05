package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToServChangeHistroty;
@MyBatisRepository
public interface ToServChangeHistrotyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToServChangeHistroty record);

    int insertSelective(ToServChangeHistroty record);

    ToServChangeHistroty selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToServChangeHistroty record);

    int updateByPrimaryKeyWithBLOBs(ToServChangeHistroty record);

    int updateByPrimaryKey(ToServChangeHistroty record);
    
    List<ToServChangeHistroty> findToServChangeHistroty(ToServChangeHistroty record);
    
    List<ToServChangeHistroty> isBaoDan(String caseCode);
}