package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.ToServChangeHistroty;

public interface ToServChangeHistrotyService {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToServChangeHistroty record);

    int insertSelective(ToServChangeHistroty record);

    ToServChangeHistroty selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToServChangeHistroty record);

    int updateByPrimaryKeyWithBLOBs(ToServChangeHistroty record);

    int updateByPrimaryKey(ToServChangeHistroty record);
    
    List<ToServChangeHistroty> queryServChangeHistroy(String caseCode);
}