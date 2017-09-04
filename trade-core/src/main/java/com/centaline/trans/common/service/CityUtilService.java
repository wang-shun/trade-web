package com.centaline.trans.common.service;

import com.aist.uam.auth.remote.vo.SessionUser;

/**
 * 城市工具类接口
 * 定义一些城市差异的公共方法
 * @author yinchao
 * @date 2017/9/4
 */
public interface CityUtilService {
	/**
	 * 获取当前登录用户所使用的岗位是否为后台角色
	 * @param currentUser 当前登录用户
	 * @return true为是 false为否
	 */
	boolean userIsBackRole(SessionUser currentUser);
	/**
	 * 获取当前登录用户所使用的岗位是否为前台角色
	 * @param currentUser 当前登录用户
	 * @return true为是 false为否
	 */
	boolean userIsFrontRole(SessionUser currentUser);

	/**
	 * 获取当前系统配置的城市编码
	 * @return
	 */
	String getCityCode();
}
