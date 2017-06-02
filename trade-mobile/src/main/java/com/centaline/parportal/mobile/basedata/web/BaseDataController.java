/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.basedata.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.plugin.web.controller.entity.ZTree;
import com.aist.common.quickQuery.formatter.convertImpl.JSONObjectConvert;
import com.aist.common.web.controller.BaseController;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;

/**
 * 
 * @author ouyq
 * @version $Id: BaseDataRest.java, v 0.1 2016年1月25日 下午2:39:55 ouyq Exp $
 */
@Controller
@RequestMapping("/baseData")
public class BaseDataController {

	@Autowired
	private UamBasedataService dictService;

	@RequestMapping("/getDictListByDictType")
	@ResponseBody
	public JSONObject getDictListByDictType(@RequestParam(required = true) String dictType) {
		Dict dict = dictService.findDictByType(dictType);
		JSONObject result = new JSONObject();
		JSONArray dicts = null;
		if (dict != null) {
			dicts = new JSONArray();
			for (Dict d : dict.getChildren()) {
				JSONObject dictJson = new JSONObject();
				dictJson.put("code", d.getCode());
				dictJson.put("name", d.getName());
				dictJson.put("tag", d.getTag());
				dictJson.put("type", d.getType());
				dictJson.put("isDefault", d.getIsDefault());
				dictJson.put("orderby", d.getOrderby());
				dicts.add(dictJson);
			}
		}
		result.put("dicts", dicts);
		return result;
	}
}
