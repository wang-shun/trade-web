package com.centaline.trans.common.service.impl;


import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.userorg.remote.UamUserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.text.DecimalFormat;
import java.util.HashMap;
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

		double TOTAL_MORT_COM_COUNT = 0;//商贷案件总和
		double TOTAL_CASE_COUNT=0;
		double TOTAL_E_CARD_COUNT=0;
		double TOTAL_CARD_COUNT=0;
		double TOTAL_E_AMOUNT=0;
		double TOTAL_EVA_COUNT=0;
		double TOTAL_MORT_COM_AMOUNT = 0;//商贷金额总和
		double TOTAL_CASE_CON_PRICE = 0;//合同价总和
		double TOTAL_LOST_COUNT = 0;//流失案件总和
		double TOTAL_CASE_LOST_COUNT_PERCENT =0;//案件流失率
		double TOTAL_LOST_AMOUNT =0;//流失金额总和
		double TOTAL_CASE_LOST_AMOUNT_PERCENT =0;//金额流失率

		double TOTAL_PING_GU_COUNT_PERCENT = 0;//评估转化率
		double TOTAL_EVA_FEE=0;//评估费实收
		double TOTAL_E_COUNT_PERCENT=0;//e卡转化率
		double TOTAL_CARD_COUNT_PERCENT=0;//刷卡率
		double TOTAL_E_AMOUNT_PERCENT=0;//e贷转化率
		double TOTAL_IS_DELEGATE_CUSTOMER_COUNT=0;//客户办理案件数
		double TOTAL_IS_DELEGATE_YUCUI_COUNT=0;//公司办理案件数
		String TOTAL_CASE_PART="合计";

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
		if("qqGetCountPercentForMath".equals(dictType)){
			for(Map<String, Object> keyer:keys){
				if(keyer.get("CASE_COUNT")!=null){
						case_total_count+=Double.valueOf(keyer.get("CASE_COUNT").toString());
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
				if("qqGetCountPercentForMath".equals(dictType)){
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
					}else{
						keyer.put("val",0);
					}
				}
				if("qqGetLostCountPercent".equals(dictType)){
					double lost_count = Double.valueOf(keyer.get("LOST_COUNT").toString());
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_count!=0){
						keyer.put("val", Double.valueOf(df.format(lost_count/case_count)));
					}else{
						keyer.put("val",0);
					}
				}
				if("qqGetLostAmountPercent".equals(dictType)){
					double lost_amount = Double.valueOf(keyer.get("LOST_AMOUNT").toString());
					double com_amount = Double.valueOf(keyer.get("MORT_COM_AMOUNT").toString());
					if(com_amount!=0){
						keyer.put("val", Double.valueOf(df.format(lost_amount/com_amount)));
					}else{
						keyer.put("val",0);
					}
				}
				if("qqGetPingGuCountPercent".equals(dictType)){
					double eva_count = Double.valueOf(keyer.get("EVA_COUNT").toString());
					double com_count = Double.valueOf(keyer.get("MORT_COM_COUNT").toString());
					if(com_count!=0){
						keyer.put("val", Double.valueOf(df.format(eva_count/com_count)));
					}else{
						keyer.put("val",0);
					}
				}
				if("qqGetECountPercent".equals(dictType)){
					double e_count = Double.valueOf(keyer.get("E_CARD_COUNT").toString());
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_count!=0){
						keyer.put("val", Double.valueOf(df.format(e_count/case_count)));
					}else{
						keyer.put("val",0);
					}
				}
				if("qqGetCardCountPercent".equals(dictType)){
					double card_count_count = Double.valueOf(keyer.get("CARD_COUNT").toString());
					double case_count = Double.valueOf(keyer.get("CASE_COUNT").toString());
					if(case_count!=0){
						keyer.put("val", Double.valueOf(df.format(card_count_count/case_count)));
					}else{
						keyer.put("val",0);
					}
				}
				if("qqGetEAMountPercent".equals(dictType)){
					double e_amount = Double.valueOf(keyer.get("E_AMOUNT").toString());
					double con_price = Double.valueOf(keyer.get("CASE_CON_PRICE").toString());
					if(con_price!=0){
						keyer.put("val", Double.valueOf(df.format(e_amount/con_price)));
					}else{
						keyer.put("val",0);
					}
				}

				if("qqGetTotalForConsultant".equals(dictType)){
					if(keyer.get("MORT_COM_COUNT")!=null){
						TOTAL_MORT_COM_COUNT += Double.valueOf(keyer.get("MORT_COM_COUNT").toString());
					}
					if(keyer.get("MORT_COM_AMOUNT")!=null){
						TOTAL_MORT_COM_AMOUNT+= Double.valueOf(keyer.get("MORT_COM_AMOUNT").toString());
					}
					if(keyer.get("LOST_COUNT")!=null){
						TOTAL_LOST_COUNT += Double.valueOf(keyer.get("LOST_COUNT").toString());
					}
					if(keyer.get("LOST_AMOUNT")!=null){
						TOTAL_LOST_AMOUNT+= Double.valueOf(keyer.get("LOST_AMOUNT").toString());
					}
					if(keyer.get("EVA_FEE")!=null){
						TOTAL_EVA_FEE+= Double.valueOf(keyer.get("EVA_FEE").toString());
					}
					if(keyer.get("CASE_COUNT")!=null){
						TOTAL_CASE_COUNT+= Double.valueOf(keyer.get("CASE_COUNT").toString());
					}
					if(keyer.get("E_CARD_COUNT")!=null){
						TOTAL_E_CARD_COUNT+= Double.valueOf(keyer.get("E_CARD_COUNT").toString());
					}
					if(keyer.get("CARD_COUNT")!=null){
						TOTAL_CARD_COUNT+= Double.valueOf(keyer.get("CARD_COUNT").toString());
					}
					if(keyer.get("E_AMOUNT")!=null){
						TOTAL_E_AMOUNT+= Double.valueOf(keyer.get("E_AMOUNT").toString());
					}
					if(keyer.get("CASE_CON_PRICE")!=null){
						TOTAL_CASE_CON_PRICE+= Double.valueOf(keyer.get("CASE_CON_PRICE").toString());
					}
					if(keyer.get("EVA_COUNT")!=null){
						TOTAL_EVA_COUNT+= Double.valueOf(keyer.get("EVA_COUNT").toString());
					}

					if(keyer.get("IS_DELEGATE_CUSTOMER_COUNT")!=null){
						TOTAL_IS_DELEGATE_CUSTOMER_COUNT+= Double.valueOf(keyer.get("IS_DELEGATE_CUSTOMER_COUNT").toString());
					}
					if(keyer.get("IS_DELEGATE_YUCUI_COUNT")!=null){
						TOTAL_IS_DELEGATE_YUCUI_COUNT+= Double.valueOf(keyer.get("IS_DELEGATE_YUCUI_COUNT").toString());
					}

					if(TOTAL_MORT_COM_COUNT!=0){
						TOTAL_PING_GU_COUNT_PERCENT = Double.valueOf(df.format(TOTAL_EVA_COUNT/TOTAL_MORT_COM_COUNT));
					}
					if(TOTAL_CASE_COUNT!=0){
						TOTAL_CASE_LOST_COUNT_PERCENT = Double.valueOf(df.format(TOTAL_LOST_COUNT/TOTAL_CASE_COUNT));
						TOTAL_E_COUNT_PERCENT =  Double.valueOf(df.format(TOTAL_E_CARD_COUNT/TOTAL_CASE_COUNT));
						TOTAL_CARD_COUNT_PERCENT = Double.valueOf(df.format(TOTAL_CARD_COUNT/TOTAL_CASE_COUNT));
					}
					if(TOTAL_MORT_COM_AMOUNT!=0){
						TOTAL_CASE_LOST_AMOUNT_PERCENT = Double.valueOf(df.format(TOTAL_LOST_AMOUNT/TOTAL_MORT_COM_AMOUNT));
					}
					if(TOTAL_CASE_CON_PRICE!=0){
						TOTAL_E_AMOUNT_PERCENT = Double.valueOf(df.format(TOTAL_E_AMOUNT/TOTAL_CASE_CON_PRICE));
					}

					keyer.put("TOTAL_CASE_PART", TOTAL_CASE_PART);
					keyer.put("TOTAL_MORT_COM_COUNT", TOTAL_MORT_COM_COUNT);
					keyer.put("TOTAL_MORT_COM_AMOUNT", TOTAL_MORT_COM_AMOUNT);
					keyer.put("TOTAL_CASE_CON_PRICE", TOTAL_CASE_CON_PRICE);
					keyer.put("TOTAL_LOST_COUNT", TOTAL_LOST_COUNT);
					keyer.put("TOTAL_CASE_LOST_COUNT_PERCENT", TOTAL_CASE_LOST_COUNT_PERCENT);
					keyer.put("TOTAL_LOST_AMOUNT", TOTAL_LOST_AMOUNT);
					keyer.put("TOTAL_CASE_LOST_AMOUNT_PERCENT", TOTAL_CASE_LOST_AMOUNT_PERCENT);
					keyer.put("TOTAL_PING_GU_COUNT_PERCENT", TOTAL_PING_GU_COUNT_PERCENT);
					keyer.put("TOTAL_EVA_FEE", TOTAL_EVA_FEE);
					keyer.put("TOTAL_E_COUNT_PERCENT", TOTAL_E_COUNT_PERCENT);
					keyer.put("TOTAL_CARD_COUNT_PERCENT", TOTAL_CARD_COUNT_PERCENT);
					keyer.put("TOTAL_E_AMOUNT_PERCENT", TOTAL_E_AMOUNT_PERCENT);
					keyer.put("TOTAL_CASE_COUNT", TOTAL_CASE_COUNT);
					keyer.put("TOTAL_IS_DELEGATE_YUCUI_COUNT", TOTAL_IS_DELEGATE_YUCUI_COUNT);
					keyer.put("TOTAL_IS_DELEGATE_CUSTOMER_COUNT", TOTAL_IS_DELEGATE_CUSTOMER_COUNT);

				}
				if("qqGetRealNameForLeader".equals(dictType)){
					if("TOTAL".equals(keyer.get("MORT_TYPE"))){
						keyer.put("val", "总计");
					}else{
						StringBuilder sql = new StringBuilder();
						sql.append(" SELECT t2.real_name as v FROM sctrans.SYS_USER_ORG_JOB t1 left join sctrans.SYS_USER t2 on t1.USER_ID=t2.id")
								.append(" left join sctrans.SYS_JOB t3 on t3.id=t1.JOB_ID where t3.job_code =:JOB_CODE and  t1.ORG_ID=:ORGANIZATION_ID and IS_LEADER='1' and ismain='1' ");
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("ORGANIZATION_ID", keyer.get("ORGANIZATION_ID"));
						param.put("JOB_CODE", keyer.get("JOB_CODE"));
						List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
						if(list.size()!=0){
							StringBuilder name = new StringBuilder();
							for(int i = 0;i<list.size();i++){
								Map<String, Object> map = list.get(i);
								if(map.get("v")!=null){
									if(i==0){
										name.append(map.get("v"));
									}else{
										name.append(",").append(map.get("v"));
									}
								}
							}
							keyer.put("val", name);
						}

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
