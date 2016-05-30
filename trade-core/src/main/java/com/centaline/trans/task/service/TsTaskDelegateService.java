package com.centaline.trans.task.service;

import java.util.List;

import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.task.vo.UserPagebleVO;

public interface TsTaskDelegateService {
	/**
	 * 获得所有誉萃用户
	 * 
	 * @return
	 */
	public List<User> listYCUsers();

	/**
	 * 
	 * @param page
	 * @return
	 */
	public DatagridVO listUser(UserPagebleVO page);

	public int taskDelegate(String owner, String assignee);

	public int closeTaskDelegate(String owner, Long id);

	/**
	 * 根据执行人查询代办人
	 * 
	 * @param owner
	 * @return
	 */
	public String getTaskAgent(String owner);
}
