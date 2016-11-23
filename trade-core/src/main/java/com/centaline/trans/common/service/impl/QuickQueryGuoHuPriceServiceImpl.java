package com.centaline.trans.common.service.impl;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.TsPrResearchMapService;



public class QuickQueryGuoHuPriceServiceImpl implements CustomDictService{
	@Autowired
	private SignService signService;
	
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	
	@Autowired
	private ToHouseTransferService toHouseTransferService;
	
	@Autowired
	private TsPrResearchMapService tsPrResearchMapService;
	
	@Autowired
	private ToEvaFeeRecordService toEvaFeeRecordService;

	@Override
	@Cacheable(value="QuickQueryGuoHuPriceServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {		   
		DecimalFormat de = new DecimalFormat("0.00");	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> key : keys) {			
		
			
			Object caseCode = key.get("CASE_CODE");
			if(caseCode != null){
				ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode.toString());
				ToSign  toSign = signService.findToSignByCaseCode(caseCode.toString());
				ToHouseTransfer toHouseTransfer = toHouseTransferService.findToGuoHuByCaseCode(caseCode.toString());
				if(toPropertyInfo != null && toSign != null && toHouseTransfer != null){
					double square = toPropertyInfo.getSquare() == null ? 0:toPropertyInfo.getSquare();
					double realPrice = toSign.getRealPrice() == null ? 0: toSign.getRealPrice().doubleValue();					
					double conPrice = toSign.getConPrice() == null ? 0: toSign.getConPrice().doubleValue();					
					if( square > 0 ){
						key.put("GUOHUDJ",  de.format((realPrice/square)*10000));
					}else{
						key.put("GUOHUDJ", 0.00);
					}					
					if(toPropertyInfo.getDistCode() != null){
						TsPrResearchMap tsPrResearchMap = tsPrResearchMapService.findByDistCode(toPropertyInfo.getDistCode());
						key.put("DISTNAME", tsPrResearchMap.getDistName() == null ? "":tsPrResearchMap.getDistName());
						
					}
					
					key.put("PROPERTY_ADDR", toPropertyInfo.getPropertyAddr() == null ? "":toPropertyInfo.getPropertyAddr());
					key.put("CON_PRICE", de.format(conPrice));
					key.put("REAL_HT_TIME", toHouseTransfer.getRealHtTime() == null ? "":formatter.format(toHouseTransfer.getRealHtTime()));
				}
				
				ToEvaFeeRecord toEvaFeeRecord = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(caseCode.toString());
				if(toEvaFeeRecord != null){
					key.put("EVAL_FEE", toEvaFeeRecord.getEvalFee() == null ? "0.00":de.format(toEvaFeeRecord.getEvalFee()));
				}
			}
		}		
		return keys;
	}	
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}


	
}
