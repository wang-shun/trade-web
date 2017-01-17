package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caoyuan7 on 2016/11/28.
 */
public class QuickQueryGetMonthReportFormServiceImpl implements CustomDictService {

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
    @Cacheable(value="QuickQueryGetMonthReportFormServiceImpl",key="#root.targetClass + #root.methodName")
    public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
        StringBuilder sql = new StringBuilder();
        if("mortComCount".equals(dictType)){
            sql.append("select count(1) as v from sctrans.T_RPT_HISTORY_CASE_BASE_INFO tb2 where tb2.district_id = :code and HOURSE_TRANSFER_AGREE_STATUS=1 and convert(varchar(7),HOURSE_TRANSFER_APPROVE_DATE,120)=:code2 and (tb2.MORTGAGE_LOAN_TYPE='30016001' or tb2.MORTGAGE_LOAN_TYPE='30016002')");
        }else if("mortPrfCount".equals(dictType)){
            sql.append("select count(1) as v from sctrans.T_RPT_HISTORY_CASE_BASE_INFO tb2 where tb2.district_id = :code and HOURSE_TRANSFER_AGREE_STATUS=1 and convert(varchar(7),HOURSE_TRANSFER_APPROVE_DATE,120)=:code2 and (tb2.MORTGAGE_LOAN_TYPE='30016003' or tb2.MORTGAGE_LOAN_TYPE='30016002')");
        }else if("noMortCount".equals(dictType)){
            sql.append("select count(1) as v from sctrans.T_RPT_HISTORY_CASE_BASE_INFO tb2 where tb2.district_id = :code and tb2.LOAN_REQ=0 and HOURSE_TRANSFER_AGREE_STATUS=1 and convert(varchar(7),HOURSE_TRANSFER_APPROVE_DATE,120)=:code2");
        }else if("lostCount".equals(dictType)){
            sql.append("select count(1) as v from sctrans.T_RPT_HISTORY_CASE_BASE_INFO tb2 where tb2.district_id=:code and tb2.IS_LOSE=1 and HOURSE_TRANSFER_AGREE_STATUS=1 and convert(varchar(7),HOURSE_TRANSFER_APPROVE_DATE,120)=:code2");
        }else if("lostMorTotalAmount".equals(dictType)){
            sql.append("select isnull(sum(MORTGAGET_TOTAL_AMOUNT),0) as v from sctrans.T_RPT_HISTORY_CASE_BASE_INFO tb2 where tb2.district_id=:code and tb2.IS_LOSE=1 and HOURSE_TRANSFER_AGREE_STATUS=1 and convert(varchar(7),HOURSE_TRANSFER_APPROVE_DATE,120)= :code2");
        }
        else{
            sql.append("");
        }
        for(Map<String, Object> keyer:keys){
            Map<String, Object> param = new HashMap<String, Object>();
            if(keyer!=null){
                param.put("code", keyer.get("DISTRICT_ID"));
                param.put("code2", keyer.get("CHOICE_MONTH"));

                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
                if(list.size()!=0){
                    keyer.put("val",list.get(0).get("v"));
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
