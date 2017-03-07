/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * # 过户后面的环节不做合流提示  #
 * @author hejf10 
 * @date	2017年3月7日10:27:25
 */
public class QuickQueryTaskNameTypeServiceImpl implements CustomDictService{
    
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object NAME= key.values().iterator().next();
			String NAME_ = NAME.toString();
			if(
				StringUtils.equals(NAME_,"过户审批")||
				StringUtils.equals(NAME_,"过户")||
				StringUtils.equals(NAME_,"过户信息修改")||
				StringUtils.equals(NAME_,"发起报告类评估")||
				StringUtils.equals(NAME_,"领证")||
				StringUtils.equals(NAME_,"放款")||
				StringUtils.equals(NAME_,"结案归档")||
				StringUtils.equals(NAME_,"结案审核")||
				StringUtils.equals(NAME_,"主管审批") ){
				key.put("val", false);
			}else{
				key.put("val", true);
			}
		}
		return keys;
	}
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
