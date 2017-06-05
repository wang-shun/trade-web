package com.centaline.trans.auth.service.impl;

import com.aist.common.auth.service.DataAuthService;
import com.aist.common.exception.PermissionException;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by linjiarong on 2015/9/14.
 */
@Service("orgHierarchyAuthServiceImpl")
public class OrgHierarchyAuthServiceImpl implements DataAuthService{

    @Autowired
    private UamUserOrgService uamUserOrgService;

    @Override
    public Boolean hasDataAuth(SessionUser user, String pLevel, String code) {
        String rootOrgId = user.getServiceDepId();
        return uamUserOrgService.hasChildOrg(rootOrgId, code);
    }

    @Override
    public Boolean hasDataAuth(SessionUser user, String pLevel, String[] codes) {
        String rootOrgId = user.getServiceDepId();
        for(String code : codes){
            if(!uamUserOrgService.hasChildOrg(rootOrgId, code)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean hasDataAuth(String userId, String pLevel, String code) {
        List<UserOrgJob> uojList = uamUserOrgService.getUserOrgJobByUserId(userId);
        for(UserOrgJob uoj : uojList){
            String rootOrgId= uoj.getOrgId();
            if(uamUserOrgService.hasChildOrg(rootOrgId, code)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkDataAuth(SessionUser user, String pLevel, String code) throws PermissionException {
        if(!this.hasDataAuth(user, pLevel, code)){
            throw new PermissionException("user can't access resource: " + code);
        }
    }
}
