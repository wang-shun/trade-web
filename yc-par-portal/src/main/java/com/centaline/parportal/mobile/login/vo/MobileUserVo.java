package com.centaline.parportal.mobile.login.vo;

import com.aist.uam.auth.remote.vo.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * 手机用户VO
 * 
 * @author jjm and yumin3
 * 
 */
public class MobileUserVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8722076991394291162L;

	private String empId;
	private String mobile;
	private String realName;
	private String userName;
	private String password;
	
	private String serviceDepId;
	private String serviceDepName;
	
	private String serviceJobId;
	private String serviceJobName;
	private String serviceJobCode;
	
    /**  大区    */
    private String busibaId;

    /**  战区    */
    private String busiwzId;

    /**  片区   */
    private String busiarId;

    /**  店组   */
    private String busigrpId;

    private Shop   shop;
    
    private Long subgrpId;

    private String busiswzId;

    public String getBusibaId() {
		return busibaId;
	}

	public void setBusibaId(String busibaId) {
		this.busibaId = busibaId;
	}

	public String getBusiwzId() {
		return busiwzId;
	}

	public void setBusiwzId(String busiwzId) {
		this.busiwzId = busiwzId;
	}

	public String getBusiarId() {
		return busiarId;
	}

	public void setBusiarId(String busiarId) {
		this.busiarId = busiarId;
	}

	public String getBusigrpId() {
		return busigrpId;
	}

	public void setBusigrpId(String busigrpId) {
		this.busigrpId = busigrpId;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Long getSubgrpId() {
		return subgrpId;
	}

	public void setSubgrpId(Long subgrpId) {
		this.subgrpId = subgrpId;
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServiceDepId() {
		return serviceDepId;
	}

	public void setServiceDepId(String serviceDepId) {
		this.serviceDepId = serviceDepId;
	}

	public String getServiceDepName() {
		return serviceDepName;
	}

	public void setServiceDepName(String serviceDepName) {
		this.serviceDepName = serviceDepName;
	}

	public String getServiceJobId() {
		return serviceJobId;
	}

	public void setServiceJobId(String serviceJobId) {
		this.serviceJobId = serviceJobId;
	}

	public String getServiceJobName() {
		return serviceJobName;
	}

	public void setServiceJobName(String serviceJobName) {
		this.serviceJobName = serviceJobName;
	}

	public String getServiceJobCode() {
		return serviceJobCode;
	}

	public void setServiceJobCode(String serviceJobCode) {
		this.serviceJobCode = serviceJobCode;
	}

    public String getBusiswzId() {
        return busiswzId;
    }

    public void setBusiswzId(String busiswzId) {
        this.busiswzId = busiswzId;
    }
    
    
    //MobileUser后添加属性   yumin3 2015-07-31
    //Start/////////////////////////////////
    /** 员工编号：针对正式员工和临时员工，HR/OA编号为必填 */
    private String             employeeCode;

    /** 员工类型 */
    private String             employeeType;

    /** 性别  */
    private String             sex;

    /** email */
    private String             email;

    /** 服务部门代码 */
    private String             serviceDepCode;

    /** 部门分类 */
    private String             serviceDepType;

    /** 组织类型 */
    private String             serviceDepOrgType;

    /** 部门层次 */
    private String             serviceDepHierarchy;

    /** 渠道属性 */
    private String             serviceDepChannel;

    /** 地址 */
    private String             serviceDepAddress;

    /** 电话 */
    private String             serviceDepPhone;

    /** 传真 */
    private String             serviceDepFax;

    /** 所在部门是否直营 */
    private String             serviceDepIsOwned;

    /** 服务公司ID */
    private String             serviceCompanyId;

    /** 服务公司代码 */
    private String             serviceCompanyCode;

    /** 服务公司名 */
    private String             serviceCompanyName;

    /** 地址 */
    private String             serviceCompanyAddress;

    /** 电话 */
    private String             serviceCompanyPhone;

    /** 传真 */
    private String             serviceCompanyFax;

    /** 所在公司是否直营 */
    private String             serviceCompanyIsOwned;

    /** 用户角色列表 保存角色CODE*/
    private List<String>       roles;

    /** 用户权限列表 ,保存资源代码RESOURCE_CODE*/
    private List<String>       permissions;

    /** casSessionId */
    private String             ssoSessionId;

    /** 可管理组织 */
    private String             adminOrg;
    
    /**  营业部    */
    private String             busiDepId;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServiceDepCode() {
		return serviceDepCode;
	}

	public void setServiceDepCode(String serviceDepCode) {
		this.serviceDepCode = serviceDepCode;
	}

	public String getServiceDepType() {
		return serviceDepType;
	}

	public void setServiceDepType(String serviceDepType) {
		this.serviceDepType = serviceDepType;
	}

	public String getServiceDepOrgType() {
		return serviceDepOrgType;
	}

	public void setServiceDepOrgType(String serviceDepOrgType) {
		this.serviceDepOrgType = serviceDepOrgType;
	}

	public String getServiceDepHierarchy() {
		return serviceDepHierarchy;
	}

	public void setServiceDepHierarchy(String serviceDepHierarchy) {
		this.serviceDepHierarchy = serviceDepHierarchy;
	}

	public String getServiceDepChannel() {
		return serviceDepChannel;
	}

	public void setServiceDepChannel(String serviceDepChannel) {
		this.serviceDepChannel = serviceDepChannel;
	}

	public String getServiceDepAddress() {
		return serviceDepAddress;
	}

	public void setServiceDepAddress(String serviceDepAddress) {
		this.serviceDepAddress = serviceDepAddress;
	}

	public String getServiceDepPhone() {
		return serviceDepPhone;
	}

	public void setServiceDepPhone(String serviceDepPhone) {
		this.serviceDepPhone = serviceDepPhone;
	}

	public String getServiceDepFax() {
		return serviceDepFax;
	}

	public void setServiceDepFax(String serviceDepFax) {
		this.serviceDepFax = serviceDepFax;
	}

	public String getServiceDepIsOwned() {
		return serviceDepIsOwned;
	}

	public void setServiceDepIsOwned(String serviceDepIsOwned) {
		this.serviceDepIsOwned = serviceDepIsOwned;
	}

	public String getServiceCompanyId() {
		return serviceCompanyId;
	}

	public void setServiceCompanyId(String serviceCompanyId) {
		this.serviceCompanyId = serviceCompanyId;
	}

	public String getServiceCompanyCode() {
		return serviceCompanyCode;
	}

	public void setServiceCompanyCode(String serviceCompanyCode) {
		this.serviceCompanyCode = serviceCompanyCode;
	}

	public String getServiceCompanyName() {
		return serviceCompanyName;
	}

	public void setServiceCompanyName(String serviceCompanyName) {
		this.serviceCompanyName = serviceCompanyName;
	}

	public String getServiceCompanyAddress() {
		return serviceCompanyAddress;
	}

	public void setServiceCompanyAddress(String serviceCompanyAddress) {
		this.serviceCompanyAddress = serviceCompanyAddress;
	}

	public String getServiceCompanyPhone() {
		return serviceCompanyPhone;
	}

	public void setServiceCompanyPhone(String serviceCompanyPhone) {
		this.serviceCompanyPhone = serviceCompanyPhone;
	}

	public String getServiceCompanyFax() {
		return serviceCompanyFax;
	}

	public void setServiceCompanyFax(String serviceCompanyFax) {
		this.serviceCompanyFax = serviceCompanyFax;
	}

	public String getServiceCompanyIsOwned() {
		return serviceCompanyIsOwned;
	}

	public void setServiceCompanyIsOwned(String serviceCompanyIsOwned) {
		this.serviceCompanyIsOwned = serviceCompanyIsOwned;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getSsoSessionId() {
		return ssoSessionId;
	}

	public void setSsoSessionId(String ssoSessionId) {
		this.ssoSessionId = ssoSessionId;
	}

	public String getAdminOrg() {
		return adminOrg;
	}

	public void setAdminOrg(String adminOrg) {
		this.adminOrg = adminOrg;
	}

	public String getBusiDepId() {
		return busiDepId;
	}

	public void setBusiDepId(String busiDepId) {
		this.busiDepId = busiDepId;
	}

    //End////////////////////////////////////////////////////////////////////////
    
}
