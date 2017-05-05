package com.centaline.trans.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionUtils {

	/** 把List<Map<K,V>> list中的值用符号separator连接成字符串
	 * @param List<Map<K,V>> list 待处理列表
	 * @param boolean allowNull是否允许连接null值
	 * @param String separator 值连接分割符
	 * @return Map<K,V> 连接后的各列字符串数据
	 * */
	public static <K,V> StringBuilder[] join(List<Map<K,V>> list,String[] separators,K[] keys,boolean allowNull){
		if(list == null){
			throw new NullPointerException("'List<Map<K,V>> list' is null.");
		}
		if(separators == null || separators.length==0){
			throw new NullPointerException("'String separator' is null.");
		}
		if(keys==null||keys.length==0){
			throw new NullPointerException("'K[] keys' is empty.");
		}
		
		StringBuilder[] joins = new StringBuilder[keys.length];
		for(int i=0;i<keys.length;i++){
			joins[i] = new StringBuilder();
		}
		
		for(Map<K,V> map:list){
			//遍历数据源，为每一个key添加值
			for(int i=0;i<keys.length;i++){
				K key = keys[i];
				V val = map.get(key);
				if(!allowNull && val == null){
					continue;
				}
				StringBuilder join =joins[i];
				if(join.length()>0){
					join.append(separators[i]);
				}
				join.append(val);
				joins[i] = join;
			}
		}
		return joins;
	}
	public static <K,V> StringBuilder[] join(List<Map<K,V>> list,String[] separators,K[] keys){
		return join(list,separators,keys,false);
	}
	
	
	public static interface Converter<K,V>{
		public V convert(Map<K,V> to);
	}
	/** 根据联合列的值比较，当列的值都相等时，将List<Map<K,V>> fromList中的数据合并到List<Map<K,V>> toList。
	 * @param List<Map<K,V>> fromList 合并源数据
	 * @param List<Map<K,V>> toList 合并后数据
	 * @param K[] joinKeys 需要比较数据的列，列表中子元素所有列的值都相等才会进行数据合并
	 * @return List<Map<K,V>>合并之后的列表
	 * */
	public static <K,V> List<Map<K,V>> merge(List<Map<K,V>> fromList,List<Map<K,V>> toList,K[] joinKeys){
		if(fromList == null){
			throw new NullPointerException("'List<Map<K,V>> fromList' is null.");
		}
		if(toList == null){
			throw new NullPointerException("'List<Map<K,V>> toList' is null.");
		}
		if(joinKeys==null || joinKeys.length==0){
			throw new NullPointerException("'K[] joinKeys' is empty.");
		}

		for(Map<K,V> to:toList){
			for(Map<K,V> from:fromList){
				boolean merge = true;
				//查找合并数据
				for(K joinKey:joinKeys){
					V fromValue = from.get(joinKey);
					V toValue   = to.get(joinKey);
					//值都为null，key对应的值相等。继续匹配下一个Key对应的value
					if(fromValue == null && toValue == null){
						continue;
					}
					//值有一个不为null，key对应的值不相等。
					if((fromValue ==null || toValue == null)){
						merge = false;
						break;
					}
					//值有都不为null，key对应的值不相等。
					if(!fromValue.equals(toValue)){
						merge = false;
						break;
					}
				}
				//joinKeys所对应的值都相等，合并数据
				if(merge){	
					to.putAll(from);
					break;
				}
			}
		}		
		return toList;
	}
	
	/** 计算list中map对应key的值*/
	public static <K,V> List<Map<K,V>> convert(List<Map<K,V>> list,Map<K,Converter<K,V>> converters){
		if(null==list || null ==converters){
			return list;
		}
		for(Map<K,V> map:list){
			for(Entry<K,Converter<K,V>> entry:converters.entrySet()){
				Converter<K,V> converter = entry.getValue();
				if(null !=converter){
					V value = converter.convert(map);
					map.put(entry.getKey(),value);
				}
			}
		}
		return list;
	}
	
	
	
	//测试方法
	public static void main(String[] args) {
		//from list，相当快速查询，列查询到数据
		List<Map<String,String>> fromList = new ArrayList<Map<String,String>>();
		Map<String,String> from = new HashMap<String,String>();
		from.put("USER_ID", "1");
		from.put("ORG_ID", "1");
		from.put("JOB_ID", "1");
		
		from.put("CASE_CODE", "ZY-ZL-201701-0036");
		from.put("ORG_NAME", "浦东");
		from.put("JOB_NAME", "交易顾问");
		fromList.add(from);
		
		from = new HashMap<String,String>();
		from.put("USER_ID", "2");
		from.put("ORG_ID", "2");
		from.put("JOB_ID", "2");
		
		from.put("CASE_CODE", "ZY-ZL-201701-0032");
		from.put("ORG_NAME", "浦东2");
		from.put("JOB_NAME", "交易顾问2");
		fromList.add(from);
		
		from = new HashMap<String,String>();
		from.put("USER_ID", "3");
		from.put("ORG_ID", "3");
		from.put("JOB_ID", "3");
		
		from.put("CASE_CODE", "ZY-ZL-201701-0033");
		from.put("ORG_NAME", "浦东3");
		from.put("JOB_NAME", "交易顾问3");
		fromList.add(from);

		
		from = new HashMap<String,String>();
		from.put("USER_ID", "1");
		from.put("ORG_ID", "1");
		from.put("JOB_ID", "1");
		
		from.put("CASE_CODE", "ZY-ZL-201701-0031");
		from.put("ORG_NAME", "浦东1");
		from.put("JOB_NAME", "交易顾问1");
		fromList.add(from);
		//to list，相当快速查询，主查询数据
		List<Map<String,String>> toList = new ArrayList<Map<String,String>>();
		Map<String,String> to  = new HashMap<String,String>();
		to.put("USER_ID", "1");
		to.put("ORG_ID", "1");
		to.put("JOB_ID", "1");
		
		to.put("CASE_CODE", "ZY-SH-201611-0450");
		to.put("ORG_NAME", "浦西");
		to.put("JOB_NAME", "总监");
		toList.add(to);
		
		//值转换类
		Map<String,Converter<String,String>> converters  = new HashMap<String,Converter<String,String>>();
		converters.put("CASE_CODE",new Converter<String,String>(){
			public String convert(Map<String,String> to){
				return "案件编号："+to.get("CASE_CODE");
			}
		});
		converters.put("JOB_NAME",new Converter<String,String>(){
			public String convert(Map<String,String> to){
				return "名字被改写了:"+to.hashCode();
			}
		});
		
		
		//////////////////////合并测试
		String[] joinKeys = new String[]{"USER_ID","ORG_ID","JOB_ID"};
		//值不做转换
		CollectionUtils.merge(fromList, toList, joinKeys);
		System.out.println(toList);
		//值做了转换
		CollectionUtils.merge(fromList, toList, joinKeys);
		CollectionUtils.convert(toList, converters);
		System.out.println(toList);

		//////////////////////分割测试
		String[] keys = new String[]{"CASE_CODE","JOB_NAME"};
		String[] separators = new String[]{"','",","};
		StringBuilder[] joins = CollectionUtils.join(fromList,separators, keys);
		System.out.println(joins[0]);
		System.out.println(joins[1]);
		
	}
	
}
