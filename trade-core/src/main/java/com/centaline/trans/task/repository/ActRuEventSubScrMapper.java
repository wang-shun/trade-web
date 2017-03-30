package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ActRuEventSubScr;
@MyBatisRepository
public interface ActRuEventSubScrMapper {
	
    ActRuEventSubScr selectByPrimaryKey(String id);
   
    List<ActRuEventSubScr>listBySelective(ActRuEventSubScr record);
}