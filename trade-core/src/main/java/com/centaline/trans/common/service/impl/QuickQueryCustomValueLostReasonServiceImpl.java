package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryCustomValueLostReasonServiceImpl implements CustomDictService{
	
	
	@Override
	@Cacheable(value="QuickQueryCustomValueLostReasonServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			String valForShow = "";
			Object status = key.get("loanlost_apply_reason_old");
			if(status!=null){
				String strLostReason[] = status.toString().split(";");
				for(int k=0; k<strLostReason.length; k++){
					if("收入、流水不足".equals(strLostReason[k])){
						val = "loanlostreason1";
					}else if("客户资质差、征信有问题".equals(strLostReason[k])){
						val = "loanlostreason2";
					}else if("客户亲戚、朋友在银行上班".equals(strLostReason[k])){
						val = "loanlostreason3";
					}else if("客户是银行VIP".equals(strLostReason[k])){
						val = "loanlostreason4";
					}else if("客户自己找的银行优惠折扣大".equals(strLostReason[k])){
						val = "loanlostreason5";
					}else if("客户不愿意支付评估费".equals(strLostReason[k])){
						val = "loanlostreason6";
					}else if("房龄老、面积小等不予受理案件".equals(strLostReason[k])){
						val = "loanlostreason7";
					}else if("银行退单，客户自办".equals(strLostReason[k])){
						val = "loanlostreason8";
					}else if("中原无法办理案件".equals(strLostReason[k])){
						val = "loanlostreason9";
					}else if("客户坚持自己办理".equals(strLostReason[k])){
						val = "loanlostreason10";
					}else if("房东坚持客户到他指定银行办理".equals(strLostReason[k])){
						val = "loanlostreason11";
					}else if("分行原因导致案件流失".equals(strLostReason[k])){
						val = "loanlostreason12";
					}else if("其他".equals(strLostReason[k])){
						val = "loanlostreason13";
					}
					valForShow=valForShow+";";					
				}
				val = valForShow.substring(0, valForShow.length());
				key.put("val", val);
				
			}
			
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
