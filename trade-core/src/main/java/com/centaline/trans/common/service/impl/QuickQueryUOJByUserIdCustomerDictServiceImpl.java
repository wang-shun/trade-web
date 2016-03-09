package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;

public class QuickQueryUOJByUserIdCustomerDictServiceImpl implements CustomDictService{
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	private String prop;
	

	@Override
	@Cacheable(value="QuickQueryUOJByUserIdCustomerDictServiceImpl",key="#root.target.getProp()+'/'+#key")
	public String getValue(String key) {
		
		List<UserOrgJob> uojList = uamUserOrgService.getUserOrgJobByUserId(key);
		if("orgName".equals(prop)){
			List<String> orgNameList = new ArrayList<String>();
			for(UserOrgJob uoj : uojList){
				orgNameList.add(uoj.getOrgName());
			}
			return StringUtils.join(orgNameList.toArray(),"/");
		}
		return key;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}
	
	public String getProp() {
		return prop;
	}
	
	public static void main(String[] args){
		System.out.println(StringUtils.join(new String[]{"a"}, "/") );
	}
}
