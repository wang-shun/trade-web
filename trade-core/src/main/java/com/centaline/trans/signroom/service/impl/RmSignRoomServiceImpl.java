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

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.entity.RmRoomScheStragegy;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.repository.ReservationMapper;
import com.centaline.trans.signroom.repository.RmRoomScheStragegyMapper;
import com.centaline.trans.signroom.repository.RmRoomScheduleMapper;
import com.centaline.trans.signroom.repository.RmSignRoomMapper;
import com.centaline.trans.signroom.repository.TradeCenterMapper;
import com.centaline.trans.signroom.service.RmSignRoomService;
import com.centaline.trans.signroom.vo.FreeRoomVo;
import com.centaline.trans.signroom.vo.ReservationInfoVo;
import org.springframework.data.domain.Page;
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
	@Autowired
	private ReservationMapper reservationMapper;
	
	@Autowired
	private QuickGridService quickGridService;
	

	@Override
	public AjaxResponse<Map> generatePageDate(JQGridParam gp) {
		AjaxResponse<Map> response = new AjaxResponse<Map>();
		try{
			
			gp.setCountOnly(false);
			gp.setPagination(false);
			gp.setQueryId("querySignRoomAllotList");
			/*List<RmSignRoom> signRooms =  rmSignRoomMapper.getSignRoomInfos(map);
			List<RmRoomSchedule> rmRoomSchedules = rmRoomScheduleMapper.getRmRoomSchedules(map);
*/			
			Page<Map<String, Object>> room = quickGridService.findPageForSqlServer(gp);
			List<Map<String, Object>> rooms = room.getContent();//签约室信息
			gp.setQueryId("queryRmRoomSchedualList");
			Page<Map<String, Object>> schedual = quickGridService.findPageForSqlServer(gp);
			List<Map<String, Object>> scheduals = schedual.getContent();//排期信息
			
			
			List<RmSignRoom> signRooms = new ArrayList<RmSignRoom>();
			List<RmRoomSchedule> rmRoomSchedules = new ArrayList<RmRoomSchedule>();//map转object
			if(rooms!=null && ! rooms.isEmpty()){
				for(Map<String, Object> rm:rooms){
					RmSignRoom sr = (RmSignRoom) mapToObject(rm,RmSignRoom.class);
					signRooms.add(sr);
				}
			}
			if(scheduals!=null && ! scheduals.isEmpty()){
				for(Map<String, Object> sd:scheduals){
					RmRoomSchedule sr = (RmRoomSchedule) mapToObject(sd,RmRoomSchedule.class);
					rmRoomSchedules.add(sr);
				}
			}
			
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
	public AjaxResponse<List<RmSignRoom>> signRoomShedualList(JQGridParam gp) {
		AjaxResponse<List<RmSignRoom>> response = new AjaxResponse<List<RmSignRoom>>();
		/*List<RmSignRoom> rmSignRooms = rmSignRoomMapper.getRmSignRoomAndStragegy(map);*/
		gp.setCountOnly(false);
		gp.setPagination(false);
		gp.setQueryId("queryRmSignRoomAndStragegy");
		Page<Map<String, Object>> rs = quickGridService.findPageForSqlServer(gp);
		List<Map<String, Object>> rss = rs.getContent();
		List<RmSignRoom> rmSignRooms = null;
		try{
			if(rss!=null && ! rss.isEmpty()){
				rmSignRooms = new ArrayList<RmSignRoom>();
				for(Map<String, Object> rm:rss){
					RmSignRoom sr = (RmSignRoom) mapToObject(rm,RmSignRoom.class);
					rmSignRooms.add(sr);
				}
			}
			
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

	@Override
	public void addReservation(ReservationInfoVo reservationInfoVo) {
		SessionUser currentUser = uamSessionService.getSessionUser();
		Reservation reservation = new Reservation();
		if(reservationInfoVo!=null){
			reservation.setResNo(reservationInfoVo.getResNo());
			reservation.setResType(reservationInfoVo.getResType());
			reservation.setResPersonOrgId(reservationInfoVo.getResPersonOrgId());
			reservation.setResPersonId(reservationInfoVo.getResPersonId());
			reservation.setResOrgId(reservationInfoVo.getResOrgId());
			reservation.setResStatus(reservationInfoVo.getResStatus());
			reservation.setScheduleId(reservationInfoVo.getScheduleId());
			reservation.setCaseCode(reservationInfoVo.getCaseCode());
			reservation.setPropertyAddress(reservationInfoVo.getPropertyAddress());
			reservation.setSigningCenter(reservationInfoVo.getSigningCenter());
			reservation.setNumberOfParticipants(reservationInfoVo.getNumberOfParticipants());
			reservation.setTransactItemCode(reservationInfoVo.getTransactItemCode());
			reservation.setCreateTime(Calendar.getInstance().getTime());
			reservation.setCreateBy(currentUser.getId());
			reservation.setUpdateTime(Calendar.getInstance().getTime());
			reservation.setUpdateBy(currentUser.getId());
			reservationMapper.insertSelective(reservation);//插入临时分配的信息
			
			FreeRoomVo freeRoomVo = new FreeRoomVo();
			freeRoomVo.setResId(reservation.getPkid());
			freeRoomVo.setScheduleId(reservationInfoVo.getScheduleId());
			rmRoomScheduleMapper.updateFreeRoomStatus(freeRoomVo); // 更新闲置房间的使用状态
		}
		
	}
	
	//map转object
	public  Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
  
        Object obj = beanClass.newInstance();  
  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
  
        return obj;  
    }

}
