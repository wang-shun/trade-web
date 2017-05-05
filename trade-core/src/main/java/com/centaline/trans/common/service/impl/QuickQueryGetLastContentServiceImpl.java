package com.centaline.trans.common.service.impl;

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
import com.centaline.trans.utils.CollectionUtils;
import com.centaline.trans.utils.CollectionUtils.Converter;


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
			//准备条件
			//将xml配置的参数进行封装，rs参数，参数二xml传递参数的分隔符，参数三即xml配置的参数名，referencecol属性
			StringBuilder[] conditions = CollectionUtils.join(rs,new String[]{"','"},new String[]{"CASE_CODE"});
			//查询结果
			Map<String,Object> paramMap = new HashMap<String,Object>();
			String sql = CASE_SQL.replace(":caseCode","'"+conditions[0]+"'");
			List<Map<String, Object>>  recordInfos = jdbcTemplate.queryForList(sql, paramMap);
			//组合数据
			//结果集recordInfos，封装到rs，返回到xml进行显示；参数三new String[]{"CASE_CODE"}结果进行映射的比对参数；参数四即xml中需要展示的字段
			CollectionUtils.merge(recordInfos, rs, new String[]{"CASE_CODE"});
			//converters对需要展示字段进行封装转换
			CollectionUtils.convert(rs, converters);
			//返回结果
			return rs;
		}
	},100);
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

	private Map<String,Converter<String,Object>> converters = new HashMap<String,Converter<String,Object>>();
	public QuickQueryGetLastContentServiceImpl() {
		//转换操作人员
		converters.put("ASSESSOR", new Converter<String,Object>(){//ASSESSOR 具体展示内容
			//new Converter<String,Object>(){} 查询结果集，具体转化操作
			public Object convert(Map<String,Object> to) {
				Object operator = to.get("OPERATOR");
				if(operator==null){
					return null;
				}
				String userId = operator.toString();
				User user = uamUserOrgService.getUserById(userId);
				return (user==null)?null:user.getRealName();
			};
		});
		//转换审批不通过内容
		converters.put("LAST_CONTENT", new Converter<String,Object>(){
			public Object convert(Map<String,Object> to) {
				String notApprove = to.get("NOT_APPROVE")==null?"":to.get("NOT_APPROVE").toString();
				String content = to.get("CONTENT")==null?"":to.get("CONTENT").toString();	
				
				StringBuilder lastContent = new StringBuilder();
				if(!"".equals(notApprove)){
					String[] reasons =  notApprove.toString().split(",");
					for(String reason : reasons){											
						Dict dict = uamBasedataService.findDictByTypeAndCode("guohu_not_approve",reason);
						if(dict != null){
							if(lastContent.length()>0){
								lastContent.append(";");
							}
							lastContent.append(dict.getName());
						}
					}
					lastContent.append("。");
				}
				//添加备注框内容
				if(!"".equals(content)){
					lastContent.append(content.toString());
				}
				return lastContent.toString();
			};
		});
	}
}
