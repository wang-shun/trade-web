package com.centaline.trans.report.service.impl;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.report.service.OrgReportFormService;
import com.centaline.trans.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by caoyuan7 on 2016/10/28.
 */
@Service
public class OrgReportFormServiceImpl implements OrgReportFormService {
    @Autowired
    private QuickGridService quickGridService;

    public List<ToCaseInfoCountVo> findPageForReportOrgForm(JQGridParam gp) {
        gp.setQueryId("queryOrgIdListForReportCount");
        Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, null);
        List<Map<String,Object>> list = pages.getContent();
        return  getFormCount(list);
    }

    public Page<Map<String, Object>> findPageForReportFormPage(JQGridParam gp, String chcheKey) {
        return quickGridService.findPageForSqlServer(gp, null);
    }

    public Page<Map<String, Object>> findPageForReportRedCountList(JQGridParam gp) {
        return quickGridService.findPageForSqlServer(gp, null);
    }

    public List<ToCaseInfoCountVo>  findPageForCaseReportFormCount(JQGridParam gp) {

        List<ToCaseInfoCountVo> voList = new LinkedList<ToCaseInfoCountVo>();
        gp.setQueryId("queryFormJds");//�ӵ���
        Page<Map<String, Object>> queryFormJds =  quickGridService.findPageForSqlServer(gp, null);
        List<Map<String,Object>> listJds = queryFormJds.getContent();

        gp.setQueryId("queryFormJas");//�᰸��
        Page<Map<String, Object>> queryFormJas =  quickGridService.findPageForSqlServer(gp, null);
        List<Map<String,Object>> listJas = queryFormJas.getContent();

        gp.setQueryId("queryFormQys");//ǩԼ��
        Page<Map<String, Object>> queryFormQys =  quickGridService.findPageForSqlServer(gp, null);
        List<Map<String,Object>> listQys = queryFormQys.getContent();

        gp.setQueryId("queryFormGhs");//ǩԼ��
        Page<Map<String, Object>> queryFormGhs =  quickGridService.findPageForSqlServer(gp, null);
        List<Map<String,Object>> listGhs = queryFormGhs.getContent();

        String year = DateUtil.getYearForString();
        String allYear[] ={year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
        for(int i=0;i<allYear.length;i++){
            ToCaseInfoCountVo vo = new ToCaseInfoCountVo();
            for(Map<String, Object> map : listJds){
                if(map!=null){
                    if(allYear[i].equals(map.get("createTime"))){
                        vo.setCountJDS((int)map.get("countJDS"));
                    }
                }
            }
            for(Map<String, Object> map : listJas){
                if(map!=null){
                    if(allYear[i].equals(map.get("createTime"))){
                        vo.setCountJAS((int) map.get("countJAS"));
                    }
                }
            }
            for(Map<String, Object> map : listQys){
                if(map!=null){
                    if(allYear[i].equals(map.get("createTime"))){
                        vo.setCountQYS((int) map.get("countQys"));
                    }
                }
            }
            for(Map<String, Object> map : listGhs){
                if(map!=null){
                    if(allYear[i].equals(map.get("createTime"))){
                        vo.setCountGHS((int) map.get("countGHS"));
                    }
                }
            }

            vo.setCreateTime(allYear[i]);
            voList.add(vo);
        }
     return voList;
    }

    public Page<Map<String, Object>> queryRedcountListForPeople(JQGridParam gp) {
        return quickGridService.findPageForSqlServer(gp, null);
    }

    public Page<Map<String, Object>> queryCountForAllRedLock(JQGridParam gp) {
        return quickGridService.findPageForSqlServer(gp, null);
    }

    public List<ToCaseInfoCountVo> findDataCountForDistrict(JQGridParam gp) {
        Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, null);
        List<Map<String,Object>> list = pages.getContent();
        return  getFormCount(list);
    }

    private  List<ToCaseInfoCountVo> getFormCount(List<Map<String,Object>> list){
        List<ToCaseInfoCountVo> voList = new LinkedList<ToCaseInfoCountVo>();
        for(Map<String,Object> map : list){
            if(map!=null){
                if(map.get("orgId")!=null&&map.get("orgName")!=null){
                    int toCaseInfoJDS = (int)map.get("countJDS");
                    int toSignQYS =  (int)map.get("countQYS");
                    int toHouseTransferGHS =  (int)map.get("countGHS");
                    int toCloseJAS =  (int)map.get("countJAS");
                    ToCaseInfoCountVo toCaseInfoVo = new ToCaseInfoCountVo();
                    toCaseInfoVo.setCountJDS(toCaseInfoJDS);
                    toCaseInfoVo.setCountQYS(toSignQYS);
                    toCaseInfoVo.setCountGHS(toHouseTransferGHS);
                    toCaseInfoVo.setCountJAS(toCloseJAS);
                    toCaseInfoVo.setOrgName((String)map.get("orgName"));
                    voList.add(toCaseInfoVo);
                }
            }
        }
        return voList;
    }
}
