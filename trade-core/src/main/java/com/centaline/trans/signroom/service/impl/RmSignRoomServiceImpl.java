package com.centaline.trans.signroom.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.signroom.entity.RmRoomScheStragegy;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.repository.RmRoomScheStragegyMapper;
import com.centaline.trans.signroom.repository.RmRoomScheduleMapper;
import com.centaline.trans.signroom.repository.RmSignRoomMapper;
import com.centaline.trans.signroom.repository.TradeCenterMapper;
import com.centaline.trans.signroom.service.RmSignRoomService;

/**
 * 签约室业务类
 * @author zhoujp7
 *
 */
@Service
public class RmSignRoomServiceImpl implements RmSignRoomService {

	@Autowired
	private UamSessionService uamSessionService;
	@Resource
	RmSignRoomMapper rmSignRoomMapper;
	@Resource
	RmRoomScheduleMapper rmRoomScheduleMapper;
	@Resource
	TradeCenterMapper tradeCenterMapper;
	@Resource
	RmRoomScheStragegyMapper rmRoomScheStragegyMapper;
	

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


	@Override
	public List<TradeCenter> getTradeCenters() {
		return tradeCenterMapper.getTradeCenterList();
	}


	@Override
	public AjaxResponse<List<RmSignRoom>> signRoomShedualList(Map map) {
		AjaxResponse<List<RmSignRoom>> response = new AjaxResponse<List<RmSignRoom>>();
		List<RmSignRoom> rmSignRooms = rmSignRoomMapper.getRmSignRoomAndStragegy(map);
		try{
			if(rmSignRooms!=null && !rmSignRooms.isEmpty()){
				for(RmSignRoom rmSignRoom:rmSignRooms){
					generateSchedule(rmSignRoom);
				}
			}
			response.setCode("400");
			response.setMessage("签约室配置管理列表查询成功！");
			response.setContent(rmSignRooms);
			response.setSuccess(true);
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("签约室配置管理列表查询失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	/**
	 * 根据策略值生成星期几
	 * @param rmSignRoom
	 * @return
	 */
	public RmSignRoom generateSchedule(RmSignRoom rmSignRoom){
		
		Long stragegyWeekVal = rmSignRoom.getStragegyWeekVal();
		
		Map map = new HashMap();
		if((stragegyWeekVal&(int)Math.pow(2, 1))>0){
			map.put("1", true);
		}else{
			map.put("1", false);
		}
		if((stragegyWeekVal&(int)Math.pow(2, 2))>0){
			map.put("2", true);
		}else{
			map.put("2", false);
		}
		if((stragegyWeekVal&(int)Math.pow(2, 3))>0){
			map.put("3", true);
		}else{
			map.put("3", false);
		}
		if((stragegyWeekVal&(int)Math.pow(2, 4))>0){
			map.put("4", true);
		}else{
			map.put("4", false);
		}
		if((stragegyWeekVal&(int)Math.pow(2, 5))>0){
			map.put("5", true);
		}else{
			map.put("5", false);
		}
		if((stragegyWeekVal&(int)Math.pow(2, 6))>0){
			map.put("6", true);
		}else{
			map.put("6", false);
		}
		if((stragegyWeekVal&(int)Math.pow(2, 7))>0){
			map.put("7", true);
		}else{
			map.put("7", false);
		}
		rmSignRoom.setWeeks(map);
		return rmSignRoom;
	}


	@Override
	public void saveOrUpdateSignRoomSchedual(RmSignRoom rmSignRoom) {
		SessionUser user= uamSessionService.getSessionUser();
		Long pkid = rmSignRoom.getPkid();
		Map map = new HashMap();
		map.put("orgId", rmSignRoom.getOrgId());
		TradeCenter  tradeCenter  =tradeCenterMapper.getTradeCenter(map);//获取签约中心信息
		if(tradeCenter!=null){
			rmSignRoom.setTradeCenter(tradeCenter.getCenterName());
			rmSignRoom.setTradeCenterId(tradeCenter.getPkid());
		}
		
		if(pkid!=null){//更新
			rmSignRoom.setUpdateBy(user.getId());
			rmSignRoom.setUpdateTime(Calendar.getInstance().getTime());
			rmSignRoomMapper.updateRmSignRoom(rmSignRoom);
			RmRoomScheStragegy rmRoomScheStragegy = new RmRoomScheStragegy();
			rmRoomScheStragegy.setRoomId(pkid);
			rmRoomScheStragegy.setStragegyWeekVal(rmSignRoom.getStragegyWeekVal());
			rmRoomScheStragegyMapper.updateRmRoomScheStragegy(rmRoomScheStragegy);
			
		}else{//新增
			rmSignRoom.setCreateBy(user.getId());
			rmSignRoom.setUpdateBy(user.getId());
			rmSignRoom.setCreateTime(Calendar.getInstance().getTime());
			rmSignRoom.setUpdateTime(Calendar.getInstance().getTime());
			rmSignRoomMapper.addRmSignRoom(rmSignRoom);
			RmRoomScheStragegy rmRoomScheStragegy = new RmRoomScheStragegy();
			rmRoomScheStragegy.setRoomId(rmSignRoom.getPkid());
			rmRoomScheStragegy.setStragegyWeekVal(rmSignRoom.getStragegyWeekVal());
			rmRoomScheStragegyMapper.insertSelective(rmRoomScheStragegy);
		}
		
		
	}


	@Override
	public void deleteSignRoom(RmSignRoom rmSignRoom) {
		//rmRoomScheStragegyMapper.deleteRmRoomScheStragegyByPkid(rmSignRoom.getStragegyPkid());
		rmSignRoomMapper.deleteRmSignRoomById(rmSignRoom.getPkid());
		
		
	}

}
