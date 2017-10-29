package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caoyuan7 on 2017/6/14.
 */
public class QuickQueryGetTranPlanServiceImpl implements CustomDictService {

    @Autowired
    private QuickQueryJdbcTemplate jdbcTemplate;
    private static String sql = "select CASE_CODE,RED_LOCK,CONVERT(varchar(10) ,EST_PART_TIME, 120 ) as EST_PART_TIME,DATEDIFF(DAY, EST_PART_TIME, GETDATE()) AS DATELAMP from sctrans.T_TO_TRANS_PLAN  where CASE_CODE =:code and PART_CODE = :code2";

    @Override
    public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

        for (Map<String, Object> key : keys) {
            if(key==null){
                continue;
            }
            Object case_code = key.get("CASE_CODE");
            Object part_code = key.get("PART_CODE");
            if (!StringUtils.isBlank((String) case_code)&&!StringUtils.isBlank((String) part_code)) {
                Map paramMap = new HashMap();
                paramMap.put("code", (String) case_code);
                paramMap.put("code2", (String) part_code);
                List<Map<String, Object>>  transPlan = jdbcTemplate.queryForList(sql,paramMap);
                if(transPlan.size()!=0){
                    key.put("RED_LOCK_CASE", transPlan.get(0).get("FONT_NAME"));
                    key.put("EST_PART_TIME_CASE", transPlan.get(0).get("EST_PART_TIME"));
                    key.put("DATELAMP_CASE", transPlan.get(0).get("DATELAMP"));
                }

            }
        }
        return keys;
    }

    @Override
    public Boolean getIsBatch() {
        return true;
    }
    public Boolean isCacheable(){
        return false;
    }
}
