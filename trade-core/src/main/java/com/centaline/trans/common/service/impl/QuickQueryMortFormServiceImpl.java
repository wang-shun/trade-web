package com.centaline.trans.common.service.impl;


import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.userorg.remote.UamUserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


public class QuickQueryMortFormServiceImpl implements CustomDictService{
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
	@Cacheable(value="QuickQueryMortFormServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		double case_count_percent=0;//案件占比
		double case_total_count=0;//总案件数

	/*	int TOTAL_MORT_COM_COUNT = 0;//商贷案件总和
		double TOTAL_MORT_COM_AMOUNT = 0;//商贷金额总和
		double TOTAL_CASE_CON_PRICE = 0;//合同价总和
		int TOTAL_LOST_COUNT = 0;//流失案件总和
		double TOTAL_CASE_LOST_COUNT_PERCENT =0;//案件流失率
		double TOTAL_LOST_AMOUNT =0;//流失金额总和
		double TOTAL_CASE_LOST_AMOUNT_PERCENT =0;//金额流失率

		double TOTAL_PING_GU_COUNT_PERCENT = 0;//

		,,,,,,,TOTAL_PING_GU_COUNT_PERCENT,
				TOTAL_EVA_FEE,TOTAL_E_COUNT_PERCENT,TOTAL_CARD_COUNT_PERCENT,TOTAL_E_AMOUNT_PERCENT,TOTAL_CASE_PART*/


		DecimalFormat df = new DecimalFormat("0.00");
		if("qqGetCountPercent".equals(dictType)){
			for(Map<String, Object> keyer:keys){
				if(keyer.get("MORT_TYPE")!=null){
					if("TOTAL".equals(keyer.get("MORT_TYPE").toString())){
						case_total_count=Double.valueOf(keyer.get("CASE_COUNT").toString());
					}
				}
			}
		}
		for(Map<String, Object> keyer:keys){
			if(keyer!=null){
				if("qqGetCountPercent".equals(dictType)){
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_total_count!=0){
						case_count_percent = Double.valueOf(df.format(case_count/case_total_count));
					}
					keyer.put("val", case_count_percent);
				}
				if("qqGetMortPercent".equals(dictType)){
					double mort_com = Double.valueOf(keyer.get("MORT_COM_AMOUNT").toString());
					double mort_prf = Double.valueOf(keyer.get("MORT_PRF_AMOUNT").toString());
					double con_price = Double.valueOf(keyer.get("CASE_CON_PRICE").toString());
					if(con_price!=0){
						keyer.put("val", Double.valueOf(df.format((mort_com+mort_prf)/con_price)));
					}
				}
				if("qqGetLostCountPercent".equals(dictType)){
					double lost_count = Double.valueOf(keyer.get("LOST_COUNT").toString());
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_count!=0){
						keyer.put("val", Double.valueOf(df.format(lost_count/case_count)));
					}
				}
				if("qqGetLostAmountPercent".equals(dictType)){
					double lost_amount = Double.valueOf(keyer.get("LOST_AMOUNT").toString());
					double com_amount = Double.valueOf(keyer.get("MORT_COM_AMOUNT").toString());
					if(com_amount!=0){
						keyer.put("val", Double.valueOf(df.format(lost_amount/com_amount)));
					}
				}
				if("qqGetPingGuCountPercent".equals(dictType)){
					double eva_count = Double.valueOf(keyer.get("EVA_COUNT").toString());
					double com_count = Double.valueOf(keyer.get("MORT_COM_COUNT").toString());
					if(com_count!=0){
						keyer.put("val", Double.valueOf(df.format(eva_count/com_count)));
					}
				}
				if("qqGetECountPercent".equals(dictType)){
					double e_count = Double.valueOf(keyer.get("E_CARD_COUNT").toString());
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_count!=0){
						keyer.put("val", Double.valueOf(df.format(e_count/case_count)));
					}
				}
				if("qqGetCardCountPercent".equals(dictType)){
					double card_count_count = Double.valueOf(keyer.get("CARD_COUNT").toString());
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_count!=0){
						keyer.put("val", Double.valueOf(df.format(card_count_count/case_count)));
					}
				}
				if("qqGetEAMountPercent".equals(dictType)){
					double e_amount = Double.valueOf(keyer.get("E_AMOUNT").toString());
					double com_amount = Double.valueOf(keyer.get("MORT_COM_AMOUNT").toString());
					if(com_amount!=0){
						keyer.put("val", Double.valueOf(df.format(e_amount/com_amount)));
					}
				}

				if("qqGetTotalForConsultant".equals(dictType)){

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
