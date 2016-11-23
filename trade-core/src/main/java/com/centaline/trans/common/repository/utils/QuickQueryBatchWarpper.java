package com.centaline.trans.common.repository.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*** 加工快速查询字段结果，批次处理类 */
public class QuickQueryBatchWarpper {
	/*单批次查询实现*/
	private BatchQuery batchQuery;
	/*批次查询行数,默认每批次处理5000条*/
	private int batchSize = 5000;
	
	public QuickQueryBatchWarpper(BatchQuery batchQuery){
		this.batchQuery = batchQuery;
	}
	public QuickQueryBatchWarpper(BatchQuery batchQuery,int batchSize){
		this.batchQuery = batchQuery;
		this.batchSize  = batchSize;
	}

	/*** 转换查询结果列表，加工列表中字段值 */
	public <T> List<T> batchWarp(List<T> rows){
		if(rows==null ||rows.isEmpty()){
			return rows;
		}
		//子列表开始下标
		int fromIndex = 0;
		//子列表最大下标
		int lastIndex = rows.size()-1;
		
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
	/***单批次字段处理逻辑接口**/
	public static interface BatchQuery {
		public <T> List<T> query(List<T> resultSet);
	}
	
	//新建单批次字段处理逻辑
	private static QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery(){
		public <T> List<T> query(List<T> resultSet) {
			
			return resultSet;
		}
	},1000);
	
	public static void main(String[] args) {
		//customDict传入参数，public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys)
		List<Map<String, Object>> keys = null;

		//调用批次字段处理逻辑，获取字段处理后结果
		List<Map<String, Object>> keys2 = batchWarpper.batchWarp(keys);
		
	}
}

