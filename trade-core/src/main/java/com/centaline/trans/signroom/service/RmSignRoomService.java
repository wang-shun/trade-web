package com.centaline.trans.signroom.service;

import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.aist.common.web.validate.AjaxResponse;

public interface RmSignRoomService {
	/**
     * 获取某天某个贵宾服务部下的房间信息
     * @param map
     * @return
     */
	AjaxResponse<Map> generatePageDate(Map map);

}
