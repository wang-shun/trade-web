package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToReminderList;

@MyBatisRepository
public interface ToReminderListMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToReminderList record);

    int insertSelective(ToReminderList record);

    ToReminderList selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToReminderList record);

    int updateByPrimaryKey(ToReminderList record);
    
    List<ToReminderList> queryToReminderLists(ToReminderList record);
}