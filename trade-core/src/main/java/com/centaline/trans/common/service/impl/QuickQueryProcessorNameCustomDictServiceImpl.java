package com.centaline.trans.common.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryProcessorNameCustomDictServiceImpl implements CustomDictService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String sql = 
			"(SELECT u.real_name FROM sctrans.T_TO_CASE c inner join sctrans.sys_user u on c.LEADING_PROCESS_ID = u.id              WHERE CASE_CODE = ?)"
		   +" UNION " 
		   +"(SELECT u.real_name FROM sctrans.T_TG_SERV_ITEM_AND_PROCESSOR p inner join sctrans.sys_user u on p.PROCESSOR_ID = u.id WHERE CASE_CODE = ?)";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Cacheable(value="QuickQueryProcessorNameCustomDictServiceImpl",key="#key")
	public String getValue(String key) {
		if(logger.isDebugEnabled()){
			logger.debug("ProcessorName : " + key);
		}
		List<String> processorNameList = jdbcTemplate.queryForList(sql, String.class, key,key);
		return StringUtils.join(processorNameList, "/");
	}

}
