package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.*;

/**
 * Created by caoyuan7 on 2016/11/28.
 */
public class QuickQueryGetCaseInfoServiceImpl implements CustomDictService {

    @Override
    public Boolean isCacheable() {
        return false;
    }

    @Autowired
    protected QuickQueryJdbcTemplate jdbcTemplate;

    @Autowired
    UamUserOrgService uamUserOrgService;


    private String dictType;


    @Override
    @Cacheable(value="QuickQueryGetCaseInfoServiceImpl",key="#root.targetClass + #root.methodName")
    public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
        StringBuilder sql = new StringBuilder();
        if("caseInfo".equals(dictType)){
            sql.append("Select GRP_NAME as v,AGENT_NAME as v2,CTM_CODE as v3,AGENT_PHONE as v4,(SELECT ORG_ID FROM SCTRANS.SYS_USER WHERE ID=AGENT_CODE) AS v5 from sctrans.T_TO_CASE_INFO  where CASE_CODE=:code");
        }else if("getCaseId".equals(dictType)){
            sql.append("select PKID as v from sctrans.T_TO_CASE where CASE_CODE=:code");
        }else if("getCasePropertyInfo".equals(dictType)){
            sql.append("select PROPERTY_ADDR as v from sctrans.T_TO_PROPERTY_INFO where CASE_CODE=:code");
        }else if("findTranPlan".equals(dictType)){
            sql.append("select RED_LOCK as v1,replace(CONVERT(varchar(12) ,EST_PART_TIME, 111 ),'/','-') as v2,DATEDIFF(DAY, EST_PART_TIME, GETDATE()) AS v3 from sctrans.T_TO_TRANS_PLAN  where CASE_CODE =:code and PART_CODE = :code2");
        }else if("wf".equals(dictType)){
            sql.append("select WFE_NAME as v from sctrans.SYS_WFE_TEMPLATE where WFE_CODE=:code");
        }
        else{
            sql.append("");
        }
        for(Map<String, Object> keyer:keys){
            Map<String, Object> param = new HashMap<String, Object>();
            if(keyer!=null){
                if("findTranPlan".equals(dictType)){
                    param.put("code", keyer.get("CASE_CODE"));
                    param.put("code2", keyer.get("PART_CODE"));
                }else{
                    param.put("code", keyer.values().iterator().next());
                }
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
                if(list.size()!=0){
                    if("caseInfo".equals(dictType)){
                        keyer.put("AGENT_ORG_NAME",list.get(0).get("v"));
                        keyer.put("AGENT_NAME",list.get(0).get("v2"));
                        keyer.put("CTM_CODE", list.get(0).get("v3"));
                        keyer.put("MOBILE", list.get(0).get("v4"));
                        String key =String.valueOf(list.get(0).get("v5"));
                        Map<String,Object> map = new HashMap<String,Object>();
                        List<User> managerInfoList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(key,"JFHJL");
                        User  user = null;
                        if(managerInfoList.size()>0){
                            user=managerInfoList.get(0);
                        }
                        keyer.put("ORG_ID", key);
                        keyer.put(CustomDictService.DICTVALCOL, JSONObject.toJSON(user));

                    }
                    if("getCaseId".equals(dictType)){
                        keyer.put("val",String.valueOf(list.get(0).get("v")));
                    }
                    if("getCasePropertyInfo".equals(dictType)){
                        keyer.put("val",list.get(0).get("v"));
                    }
                    if("findTranPlan".equals(dictType)){
                        keyer.put("RED_LOCK",list.get(0).get("v1"));
                        keyer.put("EST_PART_TIME",list.get(0).get("v2"));
                        keyer.put("DATELAMP",list.get(0).get("v3"));
                    }
                    if("wf".equals(dictType)){
                        keyer.put("val",String.valueOf(list.get(0).get("v")));
                    }
                }
            }
        }
        return keys;
    }

    @Override
    public Boolean getIsBatch() {
        return true;
    }

    public String getDictType() {return dictType;}

    public void setDictType(String dictType) {this.dictType = dictType;}
}
