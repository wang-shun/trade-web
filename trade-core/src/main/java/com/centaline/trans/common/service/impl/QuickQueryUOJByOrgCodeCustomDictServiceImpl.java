/**
 * Shanghai Centalinehink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;

/**
 * 
 * @author tanmy1
 * @version $Id: QuickQueryUOJByOrgCodeCustomDictServiceImpl.java, v 0.1
 *          2015年8月14日 上午10:52:23 tanmy1 Exp $
 */
public class QuickQueryUOJByOrgCodeCustomDictServiceImpl implements CustomDictService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UamUserOrgService uamUserOrgService;

	private String jobCode;

	private String prop;

	// 组织层级
	private String deptId;

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	/**
	 * @see com.aist.common.quickQuery.service.CustomDictService#getValue(java.lang.String)
	 */
	@Override
	public String getValue(String key) {
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		Org keyOrg = uamUserOrgService.getOrgByCode(key);

		if (keyOrg == null) {
			return "";
		}

		List<User> userOrgJobs = new ArrayList<User>();
		if (StringUtils.isBlank(deptId)) {
			userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(keyOrg.getId(), jobCode);
		} else if ("BUSIARORBUSISWZ".equals(deptId)) {
			Org org = uamUserOrgService.getParentOrgByDepHierarchy(keyOrg.getId(), "BUSIAR");
			if (org == null) {
				return "";
			}
			userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), "JQYJL");
			// 如果区域经理不存在，则取区域总监
			if (CollectionUtils.isEmpty(userOrgJobs)) {
				Org org2 = uamUserOrgService.getParentOrgByDepHierarchy(keyOrg.getId(), "BUSISWZ");
				if (org2 == null) {
					return "";
				}
				userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org2.getId(), "JQYZJ");
			}
		} else {
			Org org = uamUserOrgService.getParentOrgByDepHierarchy(keyOrg.getId(), deptId);
			if (org == null) {
				return "";
			}
			userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), jobCode);
		}
		if (userOrgJobs != null && !userOrgJobs.isEmpty()) {
			User user = userOrgJobs.get(0);
			String value;
			try {
				value = BeanUtils.getProperty(user, prop);
			} catch (Exception e) {
				logger.error("prop:" + prop, e);
				return null;
			}

			return value;
		}

		return "";
	}

	@Override
	@Cacheable(value = "QuickQueryUOJByOrgCodeCustomDictServiceImpl", key = "#root.target.getDeptId()+'/'+#root.target.getJobCode()+'/'+#root.target.getProp()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> keyer : keys) {
			String val = "";
			Object key = keyer.get("AGENT_ORG_CODE");
			if (key != null) {

				String sql = "select top 1 id from sctrans.SYS_ORG where org_code = :code order by is_deleted asc, UPDATE_DATE desc";
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("code", key);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, paramMap);

				// Org keyOrg = uamUserOrgService.getOrgByCode(key.toString());

				if (list == null || list.isEmpty() || null == list.get(0) || null == list.get(0).get("id")) {
					keyer.put("val", val);
					continue;
				}
				String orgId = String.valueOf(list.get(0).get("id"));

				List<User> userOrgJobs = new ArrayList<User>();
				if (StringUtils.isBlank(deptId)) {
					userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, jobCode);
				} else if ("BUSIARORBUSISWZ".equals(deptId)) {

					StringBuffer deptSql = new StringBuffer();
					deptSql.append(" with cte as ( ");
					deptSql.append("     select * from sctrans.SYS_ORG where id = :id ");
					deptSql.append("     union all  ");
					deptSql.append(
							"     select o.* from sctrans.sys_org o inner join cte c on o.id = c.PARENT_ID ");
					deptSql.append(" )select id from cte c where c.DEP_HIERARCHY = 'BUSIAR' ");

					Map<String, Object> param = new HashMap<String, Object>();
					param.put("id", orgId);
					List<Map<String, Object>> orgList = jdbcTemplate.queryForList(deptSql.toString(), param);

					if (orgList == null || orgList.isEmpty() || null == orgList.get(0)
							|| null == orgList.get(0).get("id")) {
						keyer.put("val", val);
						continue;
					}

					String deptId = String.valueOf(orgList.get(0).get("id"));

					userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(deptId, "JQYJL");
					// 如果区域经理不存在，则取区域总监
					if (userOrgJobs == null) {
						Org org2 = uamUserOrgService.getParentOrgByDepHierarchy(orgId, "BUSISWZ");
						if (org2 == null) {
							keyer.put("val", val);
							continue;
						}
						userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org2.getId(), "JQYZJ");
					}
				} else {
					Org org = uamUserOrgService.getParentOrgByDepHierarchy(orgId, deptId);
					if (org == null) {
						keyer.put("val", val);
						continue;
					}
					userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), jobCode);
				}
				if (userOrgJobs != null && !userOrgJobs.isEmpty()) {
					User user = userOrgJobs.get(0);
					try {
						val = BeanUtils.getProperty(user, prop);
					} catch (Exception e) {
						logger.error("prop:" + prop, e);
					}
				}
			}

			keyer.put("val", val);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

	/**
	 * Setter method for property <tt>jobCode</tt>.
	 * 
	 * @param jobCode
	 *            value to be assigned to property jobCode
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * Getter method for property <tt>jobCode</tt>.
	 * 
	 * @return property value of jobCode
	 */
	public String getJobCode() {
		return jobCode;
	}

	/**
	 * Getter method for property <tt>prop</tt>.
	 * 
	 * @return property value of prop
	 */
	public String getProp() {
		return prop;
	}

	/**
	 * Setter method for property <tt>prop</tt>.
	 * 
	 * @param prop
	 *            value to be assigned to property prop
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
