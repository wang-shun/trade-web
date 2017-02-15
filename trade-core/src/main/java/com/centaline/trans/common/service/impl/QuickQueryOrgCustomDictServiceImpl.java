package com.centaline.trans.common.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;
import com.centaline.trans.utils.CollectionUtils;
import com.centaline.trans.utils.CollectionUtils.Converter;

public class QuickQueryOrgCustomDictServiceImpl implements CustomDictService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	private String prop;
	
	/**
	 * 新建批量查询-1批次1000条
	 */
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(
		new BatchQuery<Map<String, Object>>() {
			public List<Map<String, Object>> query(List<Map<String, Object>> keys) {
				// 设置条件参数
				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder[] joins = CollectionUtils.join(keys, new String[]{"','"}, new String[]{"ORG_ID"});
				String sql = "SELECT REAL_NAME,ORG_ID FROM sctrans.V_USER_ORG_JOB_ACTIVE WHERE ORG_ID in ('"+joins[0].toString()+"') AND JOB_CODE = 'Manager'";
				//根据Org_id 和 jobCode 查询主管 
				List<Map<String, Object>> managerList = jdbcTemplate.queryForList(sql, paramMap);
				CollectionUtils.merge(managerList, keys, new String[]{"ORG_ID"});
				//根据组织id查询组织名称
				CollectionUtils.convert(keys,converters);
				return keys;
			}

		}, 1000);
	
	@Override
	@Cacheable(value="QuickQueryOrgCustomDictServiceImpl",key="#root.target.getProp()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		return batchWarpper.batchWarp(keys);
	}
	
	private Map<String,Converter<String,Object>> converters = new HashMap<String,Converter<String,Object>>();
	public QuickQueryOrgCustomDictServiceImpl() {
		converters.put("ORG_NAME", new Converter<String,Object>(){
			public Object convert(Map<String,Object> to) {
				Object key = to.get("ORG_ID");
				if(null==key){
					return null;
				}
				try {
					Org u = uamUserOrgService.getOrgById(key.toString());
					key = BeanUtils.getProperty(u, prop);
				} catch (Exception e) {
					logger.error(e.getMessage());
					return null;
				} 
				return key;
			};
		});
		
	}
	
	
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}
}
