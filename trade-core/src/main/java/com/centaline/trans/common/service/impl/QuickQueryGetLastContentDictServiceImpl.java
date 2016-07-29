package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;

public class QuickQueryGetLastContentDictServiceImpl implements CustomDictService{
	@Autowired
	private UamBasedataService uamBasedataService;
	private String partCode;
	
	@Override
	@Cacheable(value="QuickQueryGetLastContentDictServiceImpl",key="#root.target.getPartCode()+'/'+#rows")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> rows) {
		for(Map<String, Object> row:rows){
			Object objectContent = row.get("CONTENT");
			Object objectNOT_APPROVE = row.get("NOT_APPROVE");
			String lastContent = "";
			//添加复选框选择的原因
			if(objectNOT_APPROVE!=null){
				String[] reasons =  objectNOT_APPROVE.toString().split(",");
				for(String reason:reasons){
					Dict dict = uamBasedataService.findDictByTypeAndCode("guohu_not_approve",reason);
					if(dict!=null){
						if(lastContent.length()>0){
							lastContent+=";";
						}
						lastContent+=dict.getName();
					}
				}
			}
			//添加复选框选择的原因句号
			if(lastContent.length()>0){
				lastContent+="。  ";
			}
			//添加备注框内容
			if(objectContent!=null){
				lastContent+=objectContent.toString();
			}
			row.put("val",lastContent);
		}
		return rows;
	}
	
	public String getPartCode() {
		return partCode;
	}
	
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	
	/*返回true 说明用快速查询的方法  第一种方法是：非快速查询、第二种方法是快速查询*/
	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
