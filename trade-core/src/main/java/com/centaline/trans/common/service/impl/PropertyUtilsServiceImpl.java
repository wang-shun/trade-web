package com.centaline.trans.common.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.service.PropertyUtilsService;

@Service
public class PropertyUtilsServiceImpl implements PropertyUtilsService {

	public static final String PROCESS_DF_KEY = "process.df.key.";
	public static final String PROCESS_DEF_VALS = "process.def.vals.";
	// 值分割
	public static final String VAL_FIX = "=";
	// 字段分割
	public static final String FIELD_FIX = ",";

	@Value("${process.df.key.OfflineEva}")
	private String offlineEvaDfKey;
	
	@Value("${process.df.key.service_change}")
	private String serviceChange;
	@Value("${process.df.key.serviceRestart}")
	private String serviceRestart;
	
	@Value("${process.df.key.spv_out}")
	private String spvOut;

	@Value("${process.df.key.operation_process}")
	private String processOperationDfKey;
	@Value("${process.def.vals.operation_process}")
	private String processDefVals;
	
	@Value("${process.df.key.tempBankAudit}")
	private String processTmpBankAuditDfKey;

	public String getProcessTmpBankAuditDfKey() {
		return processTmpBankAuditDfKey;
	}

	private Map<String, String> processOperationDfKeyMap;
	private Map<String, Map<String, Object>> processDefValsMap;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	private void init() {
		processOperationDfKeyMap = new HashMap<String, String>();
		processDefValsMap = new HashMap<>();
		Field[] fs = this.getClass().getDeclaredFields();
		for (Field field : fs) {
			if (field.isAnnotationPresent(Value.class)) {
				putProperty(field);
			}
		}
	}

	/**
	 * 设置property
	 * 
	 * @param field
	 */
	private void putProperty(Field field) {
		Value val = field.getAnnotation(Value.class);
		String valStr = val.value();
		if (StringUtils.isBlank(valStr)) {
			return;
		}
		valStr = valStr.substring("${".length(), valStr.lastIndexOf("}"));
		try {
			if (valStr.startsWith(PROCESS_DF_KEY)) {
				String key = valStr.substring(PROCESS_DF_KEY.length());
				Object obj = field.get(this);
				putDfKey(key, obj == null ? null : field.get(this).toString());
			} else if (valStr.startsWith(PROCESS_DEF_VALS)) {
				String key = valStr.substring(PROCESS_DEF_VALS.length());
				putDefVals(key, field.get(this));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * put def values
	 * 
	 * @param key
	 * @param value
	 */
	private void putDefVals(String key, Object value) {
		String valStr = String.valueOf(value);
		String[] defValStr = valStr.split(FIELD_FIX);
		Map<String, Object> defValMap = new HashMap<String, Object>();
		for (String string : defValStr) {
			String[] val = string.split(VAL_FIX);
			defValMap.put(val[0], Boolean.valueOf(val[1]));
		}
		processDefValsMap.put(key, defValMap);
	}

	/**
	 * put dfKey
	 * 
	 * @param key
	 * @param value
	 */
	private void putDfKey(String key, String value) {
		processOperationDfKeyMap.put(key, value);
	}

	@Override
	public String getProcessDfId(String process) {
		if (processOperationDfKeyMap == null) {
			init();
		}
		return processOperationDfKeyMap.get(process);
	}

	@Override
	public Map<String, Object> getProcessDefVals(String process) {
		if (processDefValsMap == null) {
			init();
		}
		return processDefValsMap.get(process);
	}

	public String getOfflineEvaDfKey() {
		return offlineEvaDfKey;
	}

	public void setOfflineEvaDfKey(String offlineEvaDfKey) {
		this.offlineEvaDfKey = offlineEvaDfKey;
	}
	
	public String getServiceChange() {
		return serviceChange;
	}

	public void setServiceChange(String serviceChange) {
		this.serviceChange = serviceChange;
	}

	public String getSpvOut() {
		return spvOut;
	}

	public void setSpvOut(String spvOut) {
		this.spvOut = spvOut;
	}
	
}
