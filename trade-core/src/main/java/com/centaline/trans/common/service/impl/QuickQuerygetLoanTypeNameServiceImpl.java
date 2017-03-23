package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;
import com.centaline.trans.signroom.vo.ResFlowupVo;
import com.centaline.trans.utils.CollectionUtils;
import com.centaline.trans.utils.CollectionUtils.Converter;

/**
 * 获取e+产品信息
 * @author zhoujp7
 *
 */
public class QuickQuerygetLoanTypeNameServiceImpl implements CustomDictService{

	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;
	@Autowired
	private UamBasedataService uamBasedataService;

	
	/**
	 * 新建批量查询-1批次1000条
	 */
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(
		new BatchQuery<Map<String, Object>>() {
			public List<Map<String, Object>> query(List<Map<String, Object>> keys) {
				CollectionUtils.convert(keys,converters);
				return keys;
			}

		}, 1000);

	private Map<String,Converter<String,Object>> converters = new HashMap<String,Converter<String,Object>>();
	public QuickQuerygetLoanTypeNameServiceImpl() {
		converters.put("val", new Converter<String,Object>(){
			public Object convert(Map<String,Object> to) {
				Object code = to.get("LOAN_SRV_CODE");
				if (code == null) {
					return null;
				}
				return uamBasedataService.getDictValue("yu_serv_cat_code_tree", code.toString());
			};
		});
		
	}
	
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		return batchWarpper.batchWarp(keys);
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	@Override
	public Boolean isCacheable() {
		return false;
	}
}
