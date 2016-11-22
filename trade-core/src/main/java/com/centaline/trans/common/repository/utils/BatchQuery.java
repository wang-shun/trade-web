package com.centaline.trans.common.repository.utils;

import java.util.List;

/*** 快速查询字段加工转换接口 */
public interface BatchQuery {
	public <T> List<T> query(List<T> resultSet);
}