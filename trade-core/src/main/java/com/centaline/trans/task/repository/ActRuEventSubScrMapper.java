package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ActRuEventSubScr;
@MyBatisRepository
public interface ActRuEventSubScrMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActRuEventSubScr record);

    int insertSelective(ActRuEventSubScr record);

    ActRuEventSubScr selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActRuEventSubScr record);

    int updateByPrimaryKey(ActRuEventSubScr record);
    
    List<ActRuEventSubScr>listBySelective(ActRuEventSubScr record);
}