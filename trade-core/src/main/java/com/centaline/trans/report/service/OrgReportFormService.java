package com.centaline.trans.report.service;

import com.aist.common.quickQuery.bo.JQGridParam;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by caoyuan7 on 2016/10/28.
 */
public interface OrgReportFormService {

    Page<Map<String,Object>> findPageForReportOrgForm(JQGridParam gp, String chcheKey);

    Page<Map<String,Object>> findPageForReportFormPage(JQGridParam gp, String chcheKey);

    Page<Map<String,Object>> findPageForReportRedCountList(JQGridParam gp, String chcheKey);

    Page<Map<String,Object>> findPageForCaseReportFormCount(JQGridParam gp, String cacheKey);
}
