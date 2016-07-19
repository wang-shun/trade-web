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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;

/**
 * 
 * @author tanmy1
 * @version $Id: QuickQueryUserOrgJobCustomDictServiceImpl.java, v 0.1 2015年8月14日 上午10:52:23 tanmy1 Exp $
 */
public class QuickQueryUserOrgJobCustomDictServiceImpl implements CustomDictService {

    private final Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UamUserOrgService uamUserOrgService;

    private String            jobCode;

    private String            prop;
    
    // 组织层级
    private String         deptId; 

    /** 
     * @see com.aist.common.quickQuery.service.CustomDictService#getValue(java.lang.String)
     */
    @Override
    @Cacheable(value = "QuickQueryUserOrgJobCustomDictServiceImpl", key = "#root.target.getDeptId()+'/'+#root.target.getJobCode()+'/'+#root.target.getProp()+'/'+#key")
    public String getValue(String key) {
    	if(StringUtils.isEmpty(key)){
    		return "";
    	}
    	List<User> userOrgJobs = new ArrayList<User>();
    	if(StringUtils.isBlank(deptId)) {
    		userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(key, jobCode);
    	} else {
    		
			Org org = uamUserOrgService.getParentOrgByDepHierarchy(key, deptId);
			if(org==null){
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
	@Cacheable(value = "QuickQueryUserOrgJobCustomDictServiceImpl", key = "#root.target.getDeptId()+'/'+#root.target.getJobCode()+'/'+#root.target.getProp()+'/'+#key")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			if(key!=null){
				List<User> userOrgJobs = new ArrayList<User>();
		    	if(StringUtils.isBlank(deptId)) {
		    		userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(key.toString(), jobCode);
		    	} else {
		    		
					Org org = uamUserOrgService.getParentOrgByDepHierarchy(key.toString(), deptId);
					if(org==null){
						val = "";
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
		                val = "";
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
     * @param jobCode value to be assigned to property jobCode
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
     * @param prop value to be assigned to property prop
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
	
	public static void main(String args[]){
		List<Map<String, Object>> keys = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("val", 123);
		keys.add(map);
		for(Map<String, Object> keyer:keys){
			String key = keyer.values().iterator().next().toString();
			System.out.println(key);
			
		}
	}
    
}
