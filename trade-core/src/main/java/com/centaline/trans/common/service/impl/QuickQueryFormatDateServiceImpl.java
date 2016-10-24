/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class QuickQueryFormatDateServiceImpl implements CustomDictService {

	private String format;
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			String val = "";
			String str=key.values().toString();
			   SimpleDateFormat formatter = new SimpleDateFormat(format);
			   Date date;
			try {
				date = formatter.parse(str.substring(str.indexOf("[")+1,str.indexOf("]")));
				val=formatter.format(date);
				key.put("val", val);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
