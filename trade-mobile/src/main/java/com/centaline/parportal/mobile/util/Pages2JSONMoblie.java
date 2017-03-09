package com.centaline.parportal.mobile.util;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class Pages2JSONMoblie {

	public static JSONObject pages2JsonMoblie(Page<Map<String, Object>> pages){
        JSONObject result = new JSONObject();
        if(pages == null){
            result.put("page", 0);
            result.put("total", 0);
            result.put("pageSize", 0);
            result.put("rows", new JSONArray());
            return result;
        }
        result.put("page", pages.getNumber());
        result.put("total", pages.getTotalPages());
        result.put("pageSize", pages.getSize());
        result.put("rows", pages.getContent());
        return result;
	}
}
