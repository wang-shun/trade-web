package com.centaline.trans.engine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.vo.PageableVo;

/**
 * 
 * @author jimmy
 *
 */
public class WorkFlowUtils {
	public static List<RestVariable> mapToVarList(Map<String, Object> vars) {
		if (null == vars || vars.isEmpty()) {
			return null;
		}
		List<RestVariable> returnVars = new ArrayList<>();
		for (String key : vars.keySet()) {
			RestVariable v = new RestVariable();
			v.setName(key);
			v.setValue(vars.get(key));
			returnVars.add(v);
		}
		return returnVars;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void convertPageableData(PageableVo vo, Class cls) {
		if (vo == null || vo.getData() == null || vo.getData().isEmpty()) {
			return;
		}
		List tempList = new ArrayList<>();
		for (Object obj : vo.getData()) {
			if (obj instanceof JSONObject) {
				tempList.add(JSONObject.parseObject(((JSONObject) obj).toJSONString(), cls));
			}
		}
		vo.setData(tempList);
	}
}
