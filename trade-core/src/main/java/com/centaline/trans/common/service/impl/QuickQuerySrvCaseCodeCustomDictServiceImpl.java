package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;

public class QuickQuerySrvCaseCodeCustomDictServiceImpl implements CustomDictService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String sql = "select distinct SRV_CODE FROM sctrans.T_TG_SERV_ITEM_AND_PROCESSOR WHERE CASE_CODE = ?";

	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Override
	@Cacheable(value="QuickQuerySrvsDictCustomDictServiceImpl",key="#key")
	public String getValue(String key) {
		if(StringUtils.isEmpty(key)){
			return "";
		}
		
		List<String> srvStrs = jdbcTemplate.queryForList(sql, String.class, key);
		
		List<String> srvNames = new ArrayList<String>();

		for(String srvStr : srvStrs){
			String value = uamBasedataService.getDictValue("yu_serv_cat_code_tree", srvStr);
			srvNames.add(value);
		}
		return StringUtils.join(srvNames, "/");
	}
}
