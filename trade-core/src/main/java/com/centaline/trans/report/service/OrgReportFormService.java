package com.centaline.trans.report.service;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by caoyuan7 on 2016/10/28.
 */
public interface OrgReportFormService {

    List<ToCaseInfoCountVo>  findPageForReportOrgForm(JQGridParam gp);

    Page<Map<String,Object>> findPageForReportFormPage(JQGridParam gp, String chcheKey);

    Page<Map<String,Object>> findPageForReportRedCountList(JQGridParam gp);

    List<ToCaseInfoCountVo>  findPageForCaseReportFormCount(JQGridParam gp);

    Page<Map<String, Object>> queryRedcountListForPeople(JQGridParam gp);

    Page<Map<String,Object>> queryCountForAllRedLock(JQGridParam gp);

    List<ToCaseInfoCountVo> findDataCountForDistrict(JQGridParam gp);
}
