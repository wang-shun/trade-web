package com.centaline.trans.report.service.impl;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.centaline.trans.report.service.OrgReportFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by caoyuan7 on 2016/10/28.
 */
@Service
public class OrgReportFormServiceImpl implements OrgReportFormService {
    @Autowired
    private QuickGridService quickGridService;

    public Page<Map<String, Object>> findPageForReportOrgForm(JQGridParam gp, String chcheKey) {
            return quickGridService.findPageForSqlServer(gp, null);
    }

    public Page<Map<String, Object>> findPageForReportFormPage(JQGridParam gp, String chcheKey) {
        return quickGridService.findPageForSqlServer(gp, null);
    }

    public Page<Map<String, Object>> findPageForReportRedCountList(JQGridParam gp, String chcheKey) {
        return quickGridService.findPageForSqlServer(gp, null);
    }

    public Page<Map<String, Object>> findPageForCaseReportFormCount(JQGridParam gp, String cacheKey) {
        return quickGridService.findPageForSqlServer(gp, null);
    }
}
