package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;

public class QuickQuerySrvsDictCustomDictServiceImpl implements CustomDictService{

	@Autowired
	private UamBasedataService uamBasedataService;
	
	private String dictType;
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if (CollectionUtils.isNotEmpty(keys)) {
			 for(Map<String, Object> m:keys){
				 //得到所有的参数
				 if(m.get("LOAN_REQ")!=null) {
					 String loanReq = m.get("LOAN_REQ").toString();
					 if("否".equals(loanReq)) {
						 m.put(CustomDictService.DICTVALCOL, " 一次性付款");
					 } else if("是".equals(loanReq)) {
						 if(m.get("MORT_TYPE")!=null) {
							 String mortType = m.get("MORT_TYPE").toString();
							 m.put(CustomDictService.DICTVALCOL, getValues(mortType));
						 }
					 } else {
						 m.put(CustomDictService.DICTVALCOL, "");
					 }
				 }
				 
			 }
			 
			return keys;
		}
		return null;
	}
	
	/*@Override
	@Cacheable(value="QuickQuerySrvsDictCustomDictServiceImpl",key="#root.target.getDictType()+'/'+#key")
	public String getValue(String key) {
		if(StringUtils.isEmpty(key))return null;
		String[] strs = key.split("/");
		StringBuffer reStrs = new StringBuffer();
		for(String s:strs){
			String value = uamBasedataService.getDictValue(dictType, s);
			reStrs.append(value);
			reStrs.append("/");
		} 
		reStrs.deleteCharAt(reStrs.length()-1); 
		return reStrs.toString();
	}*/
	
	private String getValues(String key) {
		if(StringUtils.isEmpty(key))return null;
		String[] strs = key.split("/");
		StringBuffer reStrs = new StringBuffer();
		for(String s:strs){
			String value = uamBasedataService.getDictValue(dictType, s);
			reStrs.append(value);
			reStrs.append("/");
		} 
		reStrs.deleteCharAt(reStrs.length()-1); 
		return reStrs.toString();
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
