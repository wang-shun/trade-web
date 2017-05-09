package com.centaline.trans.satisfaction.repository;

import java.util.List;

import com.centaline.trans.satisfaction.entity.ToSatisfaction;

public interface ToSatisfactionMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSatisfaction record);

    int insertSelective(ToSatisfaction record);

    ToSatisfaction selectByPrimaryKey(Long pkid);
    
    List<ToSatisfaction> selectAll();

    int updateByPrimaryKeySelective(ToSatisfaction record);

    int updateByPrimaryKey(ToSatisfaction record);
}