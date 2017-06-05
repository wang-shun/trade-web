package com.centaline.trans.cases.service;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONArray;

/**
 * Created by caoyuan7 on 2017/4/28.
 */
public interface TransplanService {
     AjaxResponse saveTransPlans(JSONArray fileListArray);
}
