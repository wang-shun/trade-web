package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;
import com.centaline.trans.common.service.KeyValueService;

public class QuickQueryGuestNameAndPhoneCustomDictServiceImpl implements CustomDictService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String sql = "select GUEST_NAME+','+GUEST_PHONE FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE = ? AND TRANS_POSITION = ?";
	
	@Autowired
	private KeyValueService keyValueService;
	
	private String transPosition;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@Cacheable(value="QuickQueryGuestNameAndPhoneCustomDictServiceImpl",key="#root.target.getTransPosition()+'/'+#key")
	public String getValue(String key) {
		if(logger.isDebugEnabled()){
			logger.debug("GuestInfo Dict : " + key);
		}
		List<String> guestNameAndPhoneList = jdbcTemplate.queryForList(sql, String.class, key, transPosition);
		return StringUtils.join(guestNameAndPhoneList, "/");
	}
	
	@Override
	@Cacheable(value="QuickQueryGuestNameAndPhoneCustomDictServiceImpl",key="#root.target.getTransPosition()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
			   
		keys = keyValueService.queryGuestInfoCustomDict(keys, transPosition);
		
		for (Map<String, Object> key : keys) {
			List<CaseReturnVisitRegistrationVO> returnVistiRegistrations = new ArrayList<CaseReturnVisitRegistrationVO>();
			Object CASE_CODE = key.get("CASE_CODE");
			if (CASE_CODE != null) {
				List<String> guestNameAndPhoneList = jdbcTemplate.queryForList(sql, String.class, CASE_CODE.toString(), transPosition);
				key.put("val", StringUtils.join(guestNameAndPhoneList, "/"));
			}
		}
		return keys;
	}
	

	public String getTransPosition() {
		return transPosition;
	}
	
	public void setTransPosition(String transPosition) {
		this.transPosition = transPosition;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
