package com.centaline.trans.signroom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.repository.RmRoomScheduleMapper;
import com.centaline.trans.signroom.repository.RmSignRoomMapper;
import com.centaline.trans.signroom.service.RmSignRoomService;

/**
 * 签约室业务类
 * @author zhoujp7
 *
 */
@Service
public class RmSignRoomServiceImpl implements RmSignRoomService {

	@Resource
	RmSignRoomMapper rmSignRoomMapper;
	@Resource
	RmRoomScheduleMapper rmRoomScheduleMapper;
	

	@Override
	public AjaxResponse<Map> generatePageDate(Map map) {
		AjaxResponse<Map> response = new AjaxResponse<Map>();
		try{
			List<RmSignRoom> signRooms =  rmSignRoomMapper.getSignRoomInfos(map);//签约室信息
			List<RmRoomSchedule> rmRoomSchedules = rmRoomScheduleMapper.getRmRoomSchedules(map);//排期信息
			List<RmRoomSchedule> rrs = null;
			if(signRooms!=null && ! signRooms.isEmpty() && rmRoomSchedules!=null && !rmRoomSchedules.isEmpty()){
				for(RmSignRoom signRoom:signRooms){
					rrs = new ArrayList<RmRoomSchedule>();
					for(RmRoomSchedule rmRoomSchedule:rmRoomSchedules){
						if(signRoom.getPkid().equals(rmRoomSchedule.getRoomId())){
							rrs.add(rmRoomSchedule);
						}
					}
					signRoom.setRmRoomSchedules(rrs);
				}
				
			}
			
			String timeslot = rmSignRoomMapper.getTimeSlots();//获取时间段
			String[]  timeslots = null;
			if(timeslot!=null && timeslot.length()>0){
				timeslots = StringUtils.split(timeslot,",");
			}
			Map res = new HashMap();
			res.put("signRooms", signRooms);
			res.put("timeslots", timeslots);
			
			response.setContent(res);
			response.setCode("400");
			response.setMessage("查询成功！");
			response.setSuccess(true);
			
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("查询失败！");
			response.setSuccess(false);
		}
		
		return response;
	}

}
