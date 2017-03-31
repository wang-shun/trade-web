package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickQueryPropertyServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	private String dictType;


	@Override
	@Cacheable(value="QuickQueryPropertyServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		StringBuilder sql = new StringBuilder();
		if("mortgageAddr".equals(dictType)){
			sql.append("select PROPERTY_ADDR as v from sctrans.T_TO_PROPERTY_INFO  where CASE_CODE=:code");
		}else if("mortgageFinOrg".equals(dictType)){
			sql.append("Select FIN_ORG_NAME_YC as v from sctrans.T_TS_FIN_ORG  where FIN_ORG_CODE=:code");
		}else if("fenHang".equals(dictType)){
			sql.append("select TOP 1 FIN_ORG_NAME_YC as v from SCTRANS.T_TS_FIN_ORG where FIN_ORG_CODE in (select distinct FA_FIN_ORG_CODE  from SCTRANS.T_TS_FIN_ORG where FIN_ORG_CODE in(select FIN_ORG_CODE from SCTRANS.T_TS_SUP ts where ts.SUP_CAT='0' and ts.FIN_ORG_CODE=:code))");
		}else if("getRealName".equals(dictType)){
			sql.append("SELECT top 1 uj.real_name as v from SCTRANS.V_USER_ORG_JOB uj where uj.USER_ID=:code");
		}else if("lostPassTime".equals(dictType)){
			sql.append("select MAX(END_TIME_) as v from(")
					.append("SELECT END_TIME_,W.CASE_CODE")
					.append(" FROM sctrans.T_HI_WORKFLOW W")
					.append(" INNER JOIN sctrans.ACT_HI_TASKINST taskInst ON taskInst.PROC_INST_ID_ = W.INST_CODE")
					.append(" AND taskInst.task_def_key_ in ('LoanlostApproveManager','LoanlostApproveDirector') ")
					.append(" AND taskInst.ID_ = (SELECT MAX(ID_) FROM sctrans.ACT_HI_TASKINST WHERE PROC_INST_ID_ = taskInst.PROC_INST_ID_  and   DELETE_REASON_='completed'")
					.append(" AND task_def_key_ in ('LoanlostApproveManager','LoanlostApproveDirector'))")
					.append(")WK WHERE WK.CASE_CODE = :code");
		}else if("htTime".equals(dictType)){
			sql.append("select REAL_HT_TIME as v from sctrans.T_TO_HOUSE_TRANSFER where CASE_CODE=:code");
		}else if("signPrice".equals(dictType)){
			sql.append("select ISNULL(S.CON_PRICE,0)/10000 as v from sctrans.T_TO_SIGN S where CASE_CODE=:code");
		}else if("evalFee".equals(dictType)){
			sql.append("select ISNULL(S.EVAL_FEE,0)/10000 as v from sctrans.T_TO_EVA_FEE_RECORD S where S.CASE_CODE=:code");
		}
		else{
			sql.append("");
		}

		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			
			if(key!=null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", key);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
				if(list.size()!=0){
					val = list.get(0).get("v") == null?"":String.valueOf(list.get(0).get("v"));
					if("htTime".equals(dictType)){
						if(val.indexOf(":")!=-1){
							val=val.substring(0,val.indexOf(" "));
						}
					}
				}
			}
			
			keyer.put("val", val);
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
