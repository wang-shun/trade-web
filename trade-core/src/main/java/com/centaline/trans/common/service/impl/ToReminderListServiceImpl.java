package com.centaline.trans.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.ToReminderList;
import com.centaline.trans.common.repository.ToReminderListMapper;
import com.centaline.trans.common.service.ToReminderListService;

@Service
public class ToReminderListServiceImpl implements ToReminderListService {

	@Autowired
	private ToReminderListMapper toReminderListMapper;

	@Override
	public List<ToReminderList> queryToReminderLists(ToReminderList toReminderList) {
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toReminderListMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToReminderList record) {
		// TODO Auto-generated method stub
		return toReminderListMapper.insert(record);
	}

	@Override
	public int insertSelective(ToReminderList record) {
		// TODO Auto-generated method stub
		return toReminderListMapper.insertSelective(record);
	}

	@Override
	public ToReminderList selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toReminderListMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToReminderList record) {
		// TODO Auto-generated method stub
		return toReminderListMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToReminderList record) {
		// TODO Auto-generated method stub
		return toReminderListMapper.updateByPrimaryKey(record);
	}
}
