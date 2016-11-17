package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryCustomValueLostReasonServiceImpl implements CustomDictService{
	
	
	@Override
	@Cacheable(value="QuickQueryCustomValueLostReasonServiceImpl",key="#root.target.getTransPosition()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			String valForShow = "";		
			String val2="";
			Object status = key.get("loanlost_apply_reason_old");
			if(status!=null){
				String strLostReason[] = status.toString().split(";");
				for(int k=0; k<strLostReason.length; k++){
					if("收入、流水不足".equals(strLostReason[k])){
						valForShow = "loanlostreason1";
						val2 += valForShow+";";	
					}else if("客户资质差、征信有问题".equals(strLostReason[k])){
						valForShow = "loanlostreason2";
						val2 += valForShow+";";
					}else if("客户亲戚、朋友在银行上班".equals(strLostReason[k])){
						valForShow = "loanlostreason3";
						val2 += valForShow+";";
					}else if("客户是银行VIP".equals(strLostReason[k])){
						valForShow = "loanlostreason4";
						val2 += valForShow+";";
					}else if("客户自己找的银行优惠折扣大".equals(strLostReason[k])){
						valForShow = "loanlostreason5";
						val2 += valForShow+";";
					}else if("客户不愿意支付评估费".equals(strLostReason[k])){
						valForShow = "loanlostreason6";
						val2 += valForShow+";";
					}else if("房龄老、面积小等不予受理案件".equals(strLostReason[k])){
						valForShow = "loanlostreason7";
						val2 += valForShow+";";
					}else if("银行退单，客户自办".equals(strLostReason[k])){
						valForShow = "loanlostreason8";
						val2 += valForShow+";";
					}else if("中原无法办理案件".equals(strLostReason[k])){
						valForShow = "loanlostreason9";
						val2 += valForShow+";";
					}else if("客户坚持自己办理".equals(strLostReason[k])){
						valForShow = "loanlostreason10";
						val2 += valForShow+";";
					}else if("房东坚持客户到他指定银行办理".equals(strLostReason[k])){
						valForShow = "loanlostreason11";
						val2 += valForShow+";";
					}else if("分行原因导致案件流失".equals(strLostReason[k])){
						valForShow = "loanlostreason12";
						val2 += valForShow+";";
					}else if("其他".equals(strLostReason[k])){
						valForShow = "loanlostreason13";
						val2 += valForShow+";";
					}								
				}	
				val = val2.substring(0, val2.length());
			}
			key.put("val", val);
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
