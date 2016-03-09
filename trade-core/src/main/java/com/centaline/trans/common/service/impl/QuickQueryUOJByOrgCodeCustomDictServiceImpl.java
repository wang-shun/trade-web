/**
 * Shanghai Centalinehink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;

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
 * @version $Id: QuickQueryUOJByOrgCodeCustomDictServiceImpl.java, v 0.1 2015年8月14日 上午10:52:23 tanmy1 Exp $
 */
public class QuickQueryUOJByOrgCodeCustomDictServiceImpl implements CustomDictService {

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
    @Cacheable(value = "QuickQueryUOJByOrgCodeCustomDictServiceImpl", key = "#root.target.getDeptId()+'/'+#root.target.getJobCode()+'/'+#root.target.getProp()+'/'+#key")
    public String getValue(String key) {
    	if(StringUtils.isEmpty(key)){
    		return "";
    	}
    	Org keyOrg = uamUserOrgService.getOrgByCode(key);
    	
    	if(keyOrg==null) {
    		return "";
    	}
    	
    	List<User> userOrgJobs = new ArrayList<User>();
    	if(StringUtils.isBlank(deptId)) {
    		userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(keyOrg.getId(), jobCode);
    	} else if("BUSIARORBUSISWZ".equals(deptId)) {
    		Org org = uamUserOrgService.getParentOrgByDepHierarchy(keyOrg.getId(), "BUSIAR");
			userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), "JQYJL");
			// 如果区域经理不存在，则取区域总监
    		if(userOrgJobs == null) {
    			Org org2 = uamUserOrgService.getParentOrgByDepHierarchy(keyOrg.getId(), "BUSISWZ");
    			userOrgJobs = uamUserOrgService.getUserByOrgIdAndJobCode(org2.getId(), "JQYZJ");
    		}
    	} else {
			Org org = uamUserOrgService.getParentOrgByDepHierarchy(keyOrg.getId(), deptId);
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
    
}
