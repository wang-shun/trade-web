package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.repository.KeyValueMapper;
import com.centaline.trans.common.service.KeyValueService;

@Service
public class KeyValueServiceImpl implements KeyValueService {
	private final int BATCH_COUNT = 1000;
	@Autowired
	private KeyValueMapper keyValueMapper;

	@Override
	public List<Map<String, Object>> queryToPropertyInfoByCaseCode(List<Map<String, Object>> keys, String dictType) {
		//批次开始下标
		int fromIndex  = 0;
		//批次结束下标
		int toIndex    = BATCH_COUNT;
		//列表最末下标
		int lastIndex  = keys.size() - 1;
		//最终结果列表
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		do{
			if(toIndex>lastIndex){
				toIndex = lastIndex;
			}
			//根据起始和结束下标获取批次
			List<Map<String, Object>> subList = keys.subList(fromIndex,toIndex);
			//进行批次数据处理
			List<Map<String, Object>> batchList = keyValueMapper.queryToPropertyInfoByCaseCode(subList, dictType);
			this.transfer(subList,batchList);

			//将处理好的结果保存到最终列表
			finalList.addAll(batchList);
			
			//修改下一批次开始下标
			fromIndex = toIndex;
			toIndex   = fromIndex + BATCH_COUNT;
		}while(toIndex<lastIndex);
		return finalList;
	}
	
	private void convert(List<Map<String, Object>> list,String key){
		for(Map<String, Object> map:list){
			Object val = map.get(key);
			map.put("val", val);
		}
	}
	
	@Override
	public List<Map<String, Object>> queryGuestInfoCustomDict(List<Map<String, Object>> keys, String dictType) {
		//批次开始下标
		int fromIndex  = 0;
		//批次结束下标
		int toIndex    = BATCH_COUNT;
		//列表最末下标
		int lastIndex  = keys.size() - 1;
		//最终结果列表
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		//批次数据列表
		do{
			if(toIndex>lastIndex){
				toIndex = lastIndex;
				System.out.println("toIndex = lastIndex;");
			}
			//根据起始和结束下标获取批次
			List<Map<String, Object>> subList = keys.subList(fromIndex,toIndex);
			System.out.println("fromIndex====="+fromIndex);
			System.out.println("toIndex====="+toIndex);
			System.out.println("subList.size====="+subList.size());
			System.out.println("subList====="+subList);
			//进行批次数据处理
			List<Map<String, Object>> batchList = keyValueMapper.queryGuestInfoCustomDict(subList, dictType);
			System.out.println("batchList.size====="+batchList.size());
			this.transfer(subList,batchList);

			//将处理好的结果保存到最终列表
			finalList.addAll(batchList);
			
			//修改下一批次开始下标
			fromIndex = toIndex;
			toIndex   = fromIndex + BATCH_COUNT;
		}while(toIndex<lastIndex);
		
		return finalList;
	}
	
	@Override
	public List<Map<String, Object>> queryGuestInfoPhoneCustomDict(List<Map<String, Object>> keys, String dictType) {
		
		//批次开始下标
		int fromIndex  = 0;
		//批次结束下标
		int toIndex    = BATCH_COUNT;
		//列表最末下标
		int lastIndex  = keys.size() - 1;
		//最终结果列表
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		//批次数据列表
		do{
			if(toIndex>lastIndex){
				toIndex = lastIndex;
			}
			//根据起始和结束下标获取批次
			List<Map<String, Object>> subList = keys.subList(fromIndex,toIndex);
			//进行批次数据处理
			List<Map<String, Object>> batchList = keyValueMapper.queryGuestInfoPhoneCustomDict(subList, dictType);
			this.transfer(subList,batchList);

			//将处理好的结果保存到最终列表
			finalList.addAll(subList);
			
			//修改下一批次开始下标
			fromIndex = toIndex;
			toIndex   = fromIndex + BATCH_COUNT;
		}while(toIndex<lastIndex);
		
		return finalList;
	}
	

	@Override
	public List<Map<String, Object>> queryProcessorNameCustomDict(List<Map<String, Object>> keys) {
		//批次开始下标
		int fromIndex  = 0;
		//批次结束下标
		int toIndex    = 0;
		//列表最末下标
		int lastIndex  = keys.size() - 1;
		//最终结果列表
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		//批次数据列表
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
		do{
			if(toIndex>lastIndex){
				toIndex = lastIndex;
			}
			//根据起始和结束下标获取批次
			List<Map<String, Object>> subList = keys.subList(fromIndex,toIndex);
			//进行批次数据处理
			batchList = keyValueMapper.queryProcessorNameCustomDict(subList);
			convert(batchList,"NAME");

			//将处理好的结果保存到最终列表
			finalList.addAll(batchList);
			
			//修改下一批次开始下标
			fromIndex = toIndex;
			toIndex   = fromIndex + BATCH_COUNT;
		}while(toIndex<lastIndex);
		
		return finalList;
	}
	
	public void transfer(List<Map<String, Object>> keys,List<Map<String, Object>> maps){
		if (keys != null && keys.size() > 0) {
			for (int i = 0; i < keys.size(); i++) {
				//keys.get(i).put("val", "");
				if (maps != null && maps.size() > 0) {
					for (int j = 0; j < maps.size(); j++) {
						if ( keys.get(i).values().iterator().next()!=null  && (keys.get(i).values().iterator().next()).equals(maps.get(j).get("CODE"))) {
							keys.get(i).put("val", maps.get(j).get("NAME"));
							break;
						}
					}
				}

			}
		}
	}


}
