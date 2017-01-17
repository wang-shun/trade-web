package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;


public class QuickQueryGetLastContentServiceImpl implements CustomDictService{
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	private static String CASE_SQL = "SELECT CASE_CODE,CONTENT,NOT_APPROVE,OPERATOR FROM  SCTRANS.T_TO_APPROVE_RECORD AR3 WHERE  AR3.PART_CODE='GuohuApprove' AND AR3.CASE_CODE IN (:caseCode)  AND AR3.PKID = (select max(IAR.PKID) from SCTRANS.T_TO_APPROVE_RECORD IAR where IAR.PART_CODE='GuohuApprove' and AR3.CASE_CODE=IAR.CASE_CODE)";
	
	
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		return batchWarpper.batchWarp(keys);
	}
  
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery<Map<String, Object>>(){
		public List<Map<String, Object>> query(List<Map<String, Object>> rs) {			   
				List<String> caseCodeList = new ArrayList<String>();
				if(rs!=null && !rs.isEmpty()){
					for (Map<String, Object> key : rs) {
						Object caseCode   = key.get("CASE_CODE");					
						if(caseCode!=null){
							caseCodeList.add(caseCode.toString());
						}

					}
					Map<String,Object> paramMap = new HashMap<String,Object>();
					
					List<Map<String, Object>>  recordInfos = new ArrayList<Map<String, Object>>();
					paramMap.put("caseCode", caseCodeList);
					recordInfos = jdbcTemplate.queryForList(CASE_SQL, paramMap);
					
					for (Map<String, Object> key : rs) {
						Object caseCode   = key.get("CASE_CODE");
						String lastContent = "";
						for(Map<String, Object> recordInfo : recordInfos){
							if(recordInfo.get("CASE_CODE") !=null && caseCode != null){
								if(caseCode.equals(recordInfo.get("CASE_CODE"))){									
									String notApprove = recordInfo.get("NOT_APPROVE")==null?"":recordInfo.get("NOT_APPROVE").toString();
									String content = recordInfo.get("CONTENT")==null?"":recordInfo.get("CONTENT").toString();								
									
									if(notApprove!=null && !"".equals(notApprove)){
										String[] reasons =  notApprove.toString().split(",");
										for(String reason : reasons){											
											Dict dict = uamBasedataService.findDictByTypeAndCode("guohu_not_approve",reason);
											if(dict != null){
												if(lastContent.length()>0){
													lastContent += ";";
												}
												lastContent += dict.getName();
											}
										}
									}
									//添加复选框选择的原因句号
									if(lastContent.length()>0){
										lastContent+="。";
									}
									//添加备注框内容
									if(content !=null && !"".equals(content)){
										lastContent+=content.toString();
									}
									
									
									String operator = recordInfo.get("OPERATOR").toString();
									User user = uamUserOrgService.getUserById(operator);
									if(null  != user){
										key.put("ASSESSOR", user.getRealName());		
									}
									key.put("LAST_CONTENT", lastContent);
									key.put("NOT_APPROVE", recordInfo.get("NOT_APPROVE")==null?"无":recordInfo.get("NOT_APPROVE").toString());
									continue;
								}
							}
						}					
					}
				}
				return rs;
			}
	
	},100);
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
