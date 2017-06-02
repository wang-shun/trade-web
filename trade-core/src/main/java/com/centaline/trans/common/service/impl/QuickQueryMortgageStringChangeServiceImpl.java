package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickQueryMortgageStringChangeServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	private String dictType;


	@Override
	@Cacheable(value="QuickQueryMortgageStringChangeServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		StringBuilder sql = new StringBuilder();
		if("isLost".equals(dictType)){
			sql.append("select TOP 1 M.MORT_TYPE as v,M.IS_DELEGATE_YUCUI as v2 from SCTRANS.T_TO_MORTGAGE M where m.CASE_CODE=:code and  M.IS_ACTIVE=1");
		}
		else{
			sql.append("");
		}

		for(Map<String, Object> keyer:keys){
			String val = "";
			String val2 = "";

			if(keyer!=null){
				if("mortgageAmount".equals(dictType)){
					BigDecimal bigDecimal = new BigDecimal(10000);
					if(keyer.get("mortTotalAmount")!=null){
						BigDecimal mortTotalAmountDouble = (BigDecimal)keyer.get("mortTotalAmount");
						BigDecimal divideBg = mortTotalAmountDouble.divide(bigDecimal,8,BigDecimal.ROUND_HALF_EVEN);
						keyer.put("MORT_TOTAL_AMOUNT", divideBg);
					}else{
						keyer.put("MORT_TOTAL_AMOUNT",0);
					}
					if(keyer.get("comAmount")!=null){
						BigDecimal comAmountDouble = (BigDecimal)keyer.get("comAmount");
						BigDecimal divideBg = comAmountDouble.divide(bigDecimal,8,BigDecimal.ROUND_HALF_EVEN);
						keyer.put("COM_AMOUNT", divideBg);
					}else{
						keyer.put("COM_AMOUNT", 0);
					}
					if(keyer.get("prfAmount")!=null){
						BigDecimal prfAmountDouble = (BigDecimal)keyer.get("prfAmount");
						BigDecimal divideBg = prfAmountDouble.divide(bigDecimal,8,BigDecimal.ROUND_HALF_EVEN);
						keyer.put("PRF_AMOUNT",divideBg);
					}else{
						keyer.put("PRF_AMOUNT", 0);
					}
					if(keyer.get("conPrice")!=null){
						BigDecimal prfAmountDouble = (BigDecimal)keyer.get("conPrice");
						BigDecimal divideBg = prfAmountDouble.divide(bigDecimal,8,BigDecimal.ROUND_HALF_EVEN);
						keyer.put("CON_PRICE",divideBg);
					}else{
						keyer.put("CON_PRICE", 0);
					}

					if(keyer.get("isTempBank")!=null){
						if("1".equals(keyer.get("isTempBank"))){
							keyer.put("IS_TMP_BANK", "是");
						}
						if("0".equals(keyer.get("isTempBank"))){
							keyer.put("IS_TMP_BANK", "否");
						}
					}
				}else{
					Object key = keyer.values().iterator().next();
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("code", key);
					List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
					if(list.size()!=0){
						val = String.valueOf(list.get(0).get("v"));
						val2 = String.valueOf(list.get(0).get("v2"));
						if("30016001".equals(val)||"30016002".equals(val)){
							if("1".equals(val2)){
								keyer.put("SDSTATUS", "否");
								keyer.put("LOANTYPE", "1");
							}
							if("0".equals(val2)){
								keyer.put("SDSTATUS", "是");
								keyer.put("LOANTYPE", "3");
							}
						}
						if("30016003".equals(val)){
							if("1".equals(val2)){
								keyer.put("SDSTATUS", "公");
								keyer.put("LOANTYPE", "2");
							}
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
