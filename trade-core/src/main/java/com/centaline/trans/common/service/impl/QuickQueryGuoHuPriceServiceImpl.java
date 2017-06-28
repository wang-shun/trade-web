package com.centaline.trans.common.service.impl;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UamBasedataService uamBasedataService;/* 字典 */
	
/*	private static String toPropertyInfoSQL = "SELECT * FROM SCTRANS.T_TO_PROPERTY_INFO  WHERE CASE_CODE in (:caseCode)";
	private static String toSignSQL = "SELECT * FROM SCTRANS.T_TO_SIGN  WHERE CASE_CODE in (:caseCode)";
	private static String toHouseTransferSQL = "SELECT * FROM SCTRANS.T_TO_HOUSE_TRANSFER  WHERE CASE_CODE in (:caseCode)";
	private static String toEvaFeeRecordSQL = "SELECT * FROM SCTRANS.T_TO_EVA_FEE_RECORD  WHERE CASE_CODE in (:caseCode)";
	private static String tsPrResearchMapSQL = "SELECT * FROM SCTRANS.T_TS_PR_RESEARCH_MAP  WHERE CASE_CODE in (:caseCode)";
		*/
	
	
/*	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		return batchWarpper.batchWarp(keys);
	}
	
	
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery<Map<String, Object>>(){*/
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {		   
					
			
		DecimalFormat de = new DecimalFormat("0.00");	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
/*		
		List<String> caseCode = new ArrayList<String>();
		if(keys!=null && !keys.isEmpty()){
			//获取传入的参数  casecode
			for (Map<String, Object> key : keys) {
				Object case_code   = key.get("CASE_CODE");				
				if(case_code!=null){
					caseCode.add(case_code.toString());
				}				
			}
			
			List<Map<String, Object>>  toPropertyInfoList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  toSignList  = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  toHouseTransferList  = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  tsPrResearchList  = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  toEvaFeeRecordList  = new ArrayList<Map<String, Object>>();
			
			Map<String,Object> paramMap = new HashMap<String,Object>();			
			paramMap.put("caseCode", caseCode);
			toPropertyInfoList = jdbcTemplate.queryForList(toPropertyInfoSQL, paramMap);
			toSignList = jdbcTemplate.queryForList(toSignSQL, paramMap);
			toHouseTransferList = jdbcTemplate.queryForList(toHouseTransferSQL, paramMap);
			tsPrResearchList = jdbcTemplate.queryForList(toEvaFeeRecordSQL, paramMap);
			toEvaFeeRecordList = jdbcTemplate.queryForList(tsPrResearchMapSQL, paramMap);
			
			for (Map<String, Object> key : rs) {
				Object case_code   = key.get("CASE_CODE");
				if(toPropertyInfoList.size() > 0 && toSignList.size() > 0){					
					for(Map<String, Object> toPropertyInfo : toPropertyInfoList ){						
						if(case_code.equals(toPropertyInfo.get("CASE_CODE"))  &&  case_code.equals(obj)){							
							double square = toPropertyInfo.getSquare() == null ? 0:toPropertyInfo.getSquare();
							double realPrice = toSign.getRealPrice() == null ? 0: toSign.getRealPrice().doubleValue();					
							double conPrice = toSign.getConPrice() == null ? 0: toSign.getConPrice().doubleValue();	
							if( square > 0 ){
								key.put("GUOHUDJ",  de.format((realPrice/square)*10000));
							}else{
								key.put("GUOHUDJ", 0.00);
							}
							continue;
						}
					}
				}			
			
			}
				
		}*/
		
		for (Map<String, Object> key : keys) {			
			Object caseCode = key.get("CASE_CODE");
			if(caseCode != null){
				
				ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode.toString());
				ToSign  toSign = signService.findToSignByCaseCode(caseCode.toString());
				ToHouseTransfer toHouseTransfer = toHouseTransferService.findToGuoHuByCaseCode(caseCode.toString());

				if(toPropertyInfo != null && toSign != null){
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
						if(tsPrResearchMap!=null && tsPrResearchMap.getDistName() != null){
							key.put("DISTNAME", tsPrResearchMap.getDistName());
						}else{
							key.put("DISTNAME","");
						}
					}
					
					key.put("PROPERTY_ADDR", toPropertyInfo.getPropertyAddr() == null ? "":toPropertyInfo.getPropertyAddr());
					key.put("CON_PRICE", de.format(conPrice));
					
				}
				
				if(null != toHouseTransfer){
					String accompany="";
					StringBuilder sb = new StringBuilder();
					key.put("REAL_HT_TIME", toHouseTransfer.getRealHtTime() == null ? "" : formatter.format(toHouseTransfer.getRealHtTime()));
					if("1".equals(toHouseTransfer.getAccompany())){
						accompany="陪同";
					}
					if("0".equals(toHouseTransfer.getAccompany())){
						accompany="不陪同";
					}
					key.put("ACCOMPANY",accompany);
					if(!StringUtils.isEmpty(toHouseTransfer.getAccompanyReason())){
						String reason[]=toHouseTransfer.getAccompanyReason().split(";");
						for(int i = 0; i<reason.length;i++){
							Dict dict = uamBasedataService.findDictByTypeAndCode("accompany_reason",reason[i]);
							if(reason!=null){
								if(dict!=null){
									sb.append(dict.getName()).append(";");
								}
							}
						}
					}
					if(!StringUtils.isEmpty(toHouseTransfer.getAccompanyOthersReason())){
						sb.append(toHouseTransfer.getAccompanyOthersReason());
					}
					key.put("ACCOMPANY_REASON",sb);
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
