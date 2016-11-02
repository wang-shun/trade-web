package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.ToReminderList;

public interface ToReminderListService {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToReminderList record);

    int insertSelective(ToReminderList record);

    ToReminderList selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToReminderList record);

    int updateByPrimaryKey(ToReminderList record);
	public List<ToReminderList> queryToReminderLists(ToReminderList toReminderList);
}
