package com.centaline.parportal.mobile.login.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.impl.UamSessionServiceClient;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.enumdata.DepTypeHierarchy;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.Menu;
import com.aist.uam.permission.remote.vo.Resource;
import com.aist.uam.permission.remote.vo.Role;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.parportal.mobile.login.service.MobileUserService;
import com.centaline.parportal.mobile.login.vo.MobileUserVo;

import jodd.util.StringUtil;

@Service
public class MobileUserServiceImpl implements MobileUserService {
    @Autowired
    private UamUserOrgService       uamUserOrgService;
    //    @Autowired
    //    private SalesUserOrgService     salesUserOrgService;

    @Autowired
    private UamPermissionService    uamPermissionService;

    @Autowired
    private UamUserOrgService       uamUserOrgServiceClient;

    @Autowired
    private UamSessionServiceClient uamSessionServiceClient;

    @Autowired
    @Value("#{systemProperties['clusternode']?:'defaultClusterNode'}")
    private String                  clusterNode;

    @Override
    public SessionUser getUserInfoByUsername(String userName) {
        User user = uamUserOrgService.getUserByUsername(userName);
        //SessionUser sessionUser=  uamSessionServiceClient.getSessionUserById(user.getId());
        SessionUser sessionUser = UTMUSER(user);
        String ssoSessionId = UUID.randomUUID() + "-APP-" + clusterNode;
        sessionUser.setSsoSessionId(ssoSessionId);
        return sessionUser;
    }

    @Override
    public SessionUser getUserByUsernameAndOrgJob(String userName, String orgId, String jobCode) {
        User user = uamUserOrgService.getUserByUsername(userName);
        SessionUser sessionUser = null;
        if (orgId == null || jobCode == null) {
            //sessionUser = uamSessionServiceClient.getSessionUserById(user.getId());
            sessionUser = UTMUSER(user);
        } else {
            sessionUser = uamSessionServiceClient.getSessionUserByUOJ(user.getId(), orgId, jobCode);
        }
        String ssoSessionId = UUID.randomUUID() + "-APP-" + clusterNode;
        sessionUser.setSsoSessionId(ssoSessionId);
        return sessionUser;
    }

    /**
     * user转换SessionUser  默认主岗
     * @param user
     * @return
     */
    private SessionUser UTMUSER(User user) {
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(user.getId());
        sessionUser.setUsername(user.getUsername());
        sessionUser.setEmployeeCode(user.getEmployeeCode());
        sessionUser.setEmployeeType(user.getEmployeeType());
        sessionUser.setEmail(user.getEmail());
        sessionUser.setMobile(user.getMobile());
        sessionUser.setRealName(user.getRealName());
        sessionUser.setSex(user.getSex());
        sessionUser.setAvatar(StringUtil.isEmpty(user.getAvatar()) ? "" : user.getAvatar());

        List<UserOrgJob> userOrgJobList = uamUserOrgServiceClient
            .getUserOrgJobByUserId(user.getId());
        if (userOrgJobList != null && !userOrgJobList.isEmpty()) {
            UserOrgJob mainUserOrgJob = findMainUserOrgJob(userOrgJobList);

            // 设置岗位信息
            Job job = uamUserOrgServiceClient.getJobById(mainUserOrgJob.getJobId());
            sessionUser.setServiceJobId(mainUserOrgJob.getJobId());
            sessionUser.setServiceJobName(job.getJobName());
            sessionUser.setServiceJobCode(job.getJobCode());

            // 设置部门信息
            Org dep = uamUserOrgServiceClient.getOrgById(mainUserOrgJob.getOrgId());
            if (dep != null) {
                sessionUser.setServiceDepId(mainUserOrgJob.getOrgId());
                sessionUser.setServiceDepName(dep.getOrgName());
                sessionUser.setServiceDepCode(dep.getOrgCode());
                sessionUser.setServiceDepAddress(dep.getAddress());
                sessionUser.setServiceDepChannel(dep.getChannel());
                sessionUser.setServiceDepFax(dep.getFax());
                sessionUser.setServiceDepHierarchy(dep.getDepHierarchy());
                sessionUser.setServiceDepIsOwned(dep.getIsOwned());
                sessionUser.setServiceDepPhone(dep.getPhone());
                sessionUser.setServiceDepType(dep.getDepType());
                sessionUser.setServiceDepOrgType(dep.getOrgType());
            } else {
                throw new BusinessException("service org is NOT existed!");
            }

            // 设置公司信息
            Org company = uamUserOrgServiceClient.getComapnyByOrgId(dep.getId());
            if (company != null) {
                sessionUser.setServiceCompanyId(company.getId());
                sessionUser.setServiceCompanyName(company.getOrgName());
                sessionUser.setServiceCompanyAddress(company.getAddress());
                sessionUser.setServiceCompanyFax(company.getFax());
                sessionUser.setServiceCompanyPhone(company.getPhone());
                sessionUser.setServiceCompanyCode(company.getOrgCode());
                sessionUser.setServiceCompanyIsOwned(company.getIsOwned());
                if (org.springframework.util.StringUtils.isEmpty(mainUserOrgJob.getAdminOrg())) {
                    sessionUser.setAdminOrg(company.getId());
                } else {
                    sessionUser.setAdminOrg(mainUserOrgJob.getAdminOrg());
                }
            }
        }

        // 大区
        Org busibaOrg = uamUserOrgServiceClient.getParentOrgByDepHierarchy(
            sessionUser.getServiceDepId(), DepTypeHierarchy.BUSIBA.getCode());
        String busibaId = busibaOrg == null ? null : busibaOrg.getId();
        sessionUser.setBusibaId(busibaId);

        // 战区
        Org busiwzOrg = uamUserOrgServiceClient.getParentOrgByDepHierarchy(
            sessionUser.getServiceDepId(), DepTypeHierarchy.BUSIWZ.getCode());
        String busiwzId = busiwzOrg == null ? null : busiwzOrg.getId();
        sessionUser.setBusiwzId(busiwzId);

        // 小战区
        Org busiswzOrg = uamUserOrgServiceClient.getParentOrgByDepHierarchy(
            sessionUser.getServiceDepId(), DepTypeHierarchy.BUSISWZ.getCode());
        String busiswzId = busiswzOrg == null ? null : busiswzOrg.getId();
        sessionUser.setBusiswzId(busiswzId);

        // 片区
        Org busiarOrg = uamUserOrgServiceClient.getParentOrgByDepHierarchy(
            sessionUser.getServiceDepId(), DepTypeHierarchy.BUSIAR.getCode());
        String busiarId = busiarOrg == null ? null : busiarOrg.getId();
        sessionUser.setBusiarId(busiarId);

        // 店组
        Org busigrpOrg = uamUserOrgServiceClient.getParentOrgByDepHierarchy(
            sessionUser.getServiceDepId(), DepTypeHierarchy.BUSIGRP.getCode());
        String busigrpId = busigrpOrg == null ? null : busigrpOrg.getId();
        sessionUser.setBusigrpId(busigrpId);

        //角色
        List<Role> roles = uamPermissionService.getRoleByJobId(sessionUser.getServiceJobId());
        if (roles != null && !roles.isEmpty()) {
            Set<String> rolesSet = new HashSet<String>();
            for (Role role : roles) {
                rolesSet.add(role.getRoleName());
                sessionUser.getPermissions().add(role.getRoleName());
            }
            sessionUser.setRoles(new ArrayList<>(rolesSet));
        }

        //获取权限集合
        List<Resource> resources = uamPermissionService
            .getResourceByJobId(sessionUser.getServiceJobId());
        if (resources != null) {
            for (Resource resource : resources) {
                sessionUser.getPermissions().add(resource.getResourceCode());
            }
        }

        //所有菜单
        /*List<Menu> menuList = uamPermissionService.getMenuByJobId(sessionUser.getServiceJobId());
        sessionUser.setMenuList(menuList);*/

        //app菜单
        Menu menu = uamPermissionService.getMenuByJobAndMenuCode(sessionUser.getServiceJobId(),
            "SALES.APP.MOBILE");
        List<Menu> appMenuList = new ArrayList<Menu>();
        appMenuList.add(menu);
        sessionUser.setMenuList(appMenuList);

        return sessionUser;

    }

    ///////////////////////////////////////////////////////////////////////////////////

    @Override
    public MobileUserVo getUserByUserId(String userId) {
        User us = uamUserOrgService.getUserById(userId);
        //		salesUserOrgService.getMobileUserById(userId,null);
        return UTMU(us);
    }

    private MobileUserVo UTMU(User us) {
        MobileUserVo user = new MobileUserVo();
        user.setEmpId(us.getId());
        user.setUserName(us.getUsername());
        user.setRealName(us.getRealName());
        user.setServiceDepId(us.getOrgId());
        user.setServiceDepName(us.getOrgName());

        user.setMobile(us.getMobile());
        user.setPassword(us.getPassword());
        user.setEmail(us.getEmail());
        user.setEmployeeCode(us.getEmployeeCode());
        user.setEmployeeType(us.getEmployeeType());
        user.setSex(us.getSex());

        List<UserOrgJob> userOrgJobList = uamUserOrgService.getUserOrgJobByUserId(us.getId());
        UserOrgJob userOrgJob = findMainUserOrgJob(userOrgJobList);
        if (userOrgJobList != null && !userOrgJobList.isEmpty()) {
            UserOrgJob mainUserOrgJob = findMainUserOrgJob(userOrgJobList);
            //设置岗位信息
            Job job = uamUserOrgService.getJobById(mainUserOrgJob.getJobId());
            user.setServiceJobId(mainUserOrgJob.getJobId());
            user.setServiceJobName(job.getJobName());
            user.setServiceJobCode(job.getJobCode());
            if (userOrgJob != null) {

                user.setServiceJobId(userOrgJob.getJobId());
                user.setServiceJobName(userOrgJob.getJobName());
                user.setServiceJobCode(userOrgJob.getJobCode());

                //设置部门信息
                Org dep = uamUserOrgService.getOrgById(userOrgJob.getOrgId());
                if (dep != null) {
                    user.setServiceDepId(userOrgJob.getOrgId());
                    user.setServiceDepName(dep.getOrgName());
                    user.setServiceDepCode(dep.getOrgCode());
                    user.setServiceDepAddress(dep.getAddress());
                    user.setServiceDepChannel(dep.getChannel());
                    user.setServiceDepFax(dep.getFax());
                    user.setServiceDepHierarchy(dep.getDepHierarchy());
                    user.setServiceDepIsOwned(dep.getIsOwned());
                    user.setServiceDepPhone(dep.getPhone());
                    user.setServiceDepType(dep.getDepType());
                    user.setServiceDepOrgType(dep.getOrgType());
                } else {
                    throw new BusinessException("service org is NOT existed!");
                }

                //设置公司信息
                Org company = uamUserOrgService.getComapnyByOrgId(dep.getId());
                if (company != null) {
                    user.setServiceCompanyId(company.getId());
                    user.setServiceCompanyName(company.getOrgName());
                    user.setServiceCompanyAddress(company.getAddress());
                    user.setServiceCompanyFax(company.getFax());
                    user.setServiceCompanyPhone(company.getPhone());
                    user.setServiceCompanyCode(company.getOrgCode());
                    user.setServiceCompanyIsOwned(company.getIsOwned());
                    if (org.springframework.util.StringUtils.isEmpty(userOrgJob.getAdminOrg())) {
                        user.setAdminOrg(company.getId());
                    } else {
                        user.setAdminOrg(userOrgJob.getAdminOrg());
                    }
                }
                List<Role> roles = uamPermissionService.getRoleByJobId(user.getServiceJobId());
                if (roles != null && !roles.isEmpty()) {
                    Set<String> rolesSet = new HashSet<String>();
                    for (Role role : roles) {
                        rolesSet.add(role.getRoleName());
                    }
                    user.setRoles(new ArrayList<>(rolesSet));
                }
            }

            //营业部
            Org busidepOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
                DepTypeHierarchy.BUSIDEP.getCode());
            String busiDepId = busidepOrg == null ? null : busidepOrg.getId();
            user.setBusiDepId(busiDepId);
            //大区
            Org busibaOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
                DepTypeHierarchy.BUSIBA.getCode());
            String busibaId = busibaOrg == null ? null : busibaOrg.getId();
            user.setBusibaId(busibaId);

            //战区
            Org busiwzOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
                DepTypeHierarchy.BUSIWZ.getCode());
            String busiwzId = busiwzOrg == null ? null : busiwzOrg.getId();
            user.setBusiwzId(busiwzId);

            //小战区
            Org busiswzOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
                DepTypeHierarchy.BUSISWZ.getCode());
            String busiswzId = busiswzOrg == null ? null : busiswzOrg.getId();
            user.setBusiswzId(busiswzId);

            //片区
            Org busiarOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
                DepTypeHierarchy.BUSIAR.getCode());
            String busiarId = busiarOrg == null ? null : busiarOrg.getId();
            user.setBusiarId(busiarId);

            //店组
            Org busigrpOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
                DepTypeHierarchy.BUSIGRP.getCode());
            String busigrpId = busigrpOrg == null ? null : busigrpOrg.getId();
            user.setBusigrpId(busigrpId);

        }
        return user;
    }

    @Override
    public MobileUserVo getUserByUsername(String userName) {
        User us = uamUserOrgService.getUserByUsername(userName);
        return UTMU(us);
    }

    /**
     * 取主岗
     * @param userOrgJobList
     * @return
     */
    private UserOrgJob findMainUserOrgJob(List<UserOrgJob> userOrgJobList) {
        if (userOrgJobList == null || userOrgJobList.isEmpty()) {
            return null;
        }
        UserOrgJob main = null;
        for (UserOrgJob uoj : userOrgJobList) {
            if ("1".equals(uoj.getIsmain())) {
                main = uoj;
                break;
            }
        }
        if (main == null) {
            main = userOrgJobList.get(0);
        }
        return main;
    }

    /** 
     * @param uamUserOrgService 要设置的 uamUserOrgService 
     */
    public void setUamUserOrgService(UamUserOrgService uamUserOrgService) {
        this.uamUserOrgService = uamUserOrgService;
    }

    /** 
     * @param uamPermissionService 要设置的 uamPermissionService 
     */
    public void setUamPermissionService(UamPermissionService uamPermissionService) {
        this.uamPermissionService = uamPermissionService;
    }

    /**
     * 
     * 检查用户是否可以变更岗位
     * 
     * @param userId
     * @param orgId
     * @param jobId
     * @return
     * @return boolean 返回类型
     */
    private boolean checkCanChange(String userId, String orgId, String jobId) {
        List<UserOrgJob> userOrgJobs = uamUserOrgServiceClient.getUserOrgJobByUserId(userId);
        boolean result = false;
        for (UserOrgJob uoj : userOrgJobs) {
            if (uoj.getJobId().equals(jobId) && uoj.getOrgId().equals(orgId)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 更新缓存的MobileUserVo
     * @param mobileUserVo
     * @param orgId
     * @param jobId
     */
    public void changSessionOrgAndJob(MobileUserVo mobileUserVo, String orgId, String jobId) {
        if (checkCanChange(mobileUserVo.getEmpId(), orgId, jobId)) {

        } else {
            throw new BusinessException("用户不具有此岗位");
        }
    }

}
