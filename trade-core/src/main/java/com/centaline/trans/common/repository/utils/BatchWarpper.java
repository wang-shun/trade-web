package com.centaline.trans.common.repository.utils;

import java.util.ArrayList;
import java.util.List;

/*** 快速查询字段加工批次转换类 */
public class BatchWarpper {
	/*单批次查询实现*/
	private BatchQuery batchQuery;
	/*批次查询行数,默认每批次处理5000条*/
	private int batchSize = 5000;
	
	public BatchWarpper(BatchQuery batchQuery){
		this.batchQuery = batchQuery;
	}
	public BatchWarpper(BatchQuery batchQuery,int batchSize){
		this.batchQuery = batchQuery;
		this.batchSize  = batchSize;
	}

	/*** 转换查询结果列表，加工列表中字段值 */
	public <T> List<T> batchWarp(List<T> rows){
		if(rows==null ||rows.isEmpty()){
			return rows;
		}
		//子列开始下标
		int fromIndex = 0;
		//子列最大下标
		int lastIndex   = rows.size()-1;
		
		//方法返回结果列表
		List<T> result = new ArrayList<T>();
		
		while(fromIndex<lastIndex){
			//获取子列表结束下标
			int toIndex = fromIndex + batchSize;
			if(toIndex > lastIndex){
				toIndex = lastIndex;
			}
			//得到子列表 
			List<T> subList    = rows.subList(fromIndex, toIndex);
			//得到子列表加工结果
			List<T> warpResult = batchQuery.query(subList);

			//把结果添加到方法返回结果列表
			result.addAll(warpResult);

			//更新子列表开始下标
			fromIndex = toIndex;
		}
		return result;
	}
}

