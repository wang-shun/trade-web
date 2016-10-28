package com.centaline.trans.kpi.repository;

import com.centaline.trans.common.repository.ToPropertyResearchAttach;

public interface ToPropertyResearchAttachMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToPropertyResearchAttach record);

    int insertSelective(ToPropertyResearchAttach record);

    ToPropertyResearchAttach selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToPropertyResearchAttach record);

    int updateByPrimaryKey(ToPropertyResearchAttach record);
}