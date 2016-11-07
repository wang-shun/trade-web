package com.centaline.trans.signroom.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.entity.RmRoomScheStragegy;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.entity.TradeCenterSchedule;
import com.centaline.trans.signroom.repository.ReservationMapper;
import com.centaline.trans.signroom.repository.RmRoomScheStragegyMapper;
import com.centaline.trans.signroom.repository.RmRoomScheduleMapper;
import com.centaline.trans.signroom.repository.RmSignRoomMapper;
import com.centaline.trans.signroom.repository.TradeCenterMapper;
import com.centaline.trans.signroom.repository.TradeCenterScheduleMapper;
import com.centaline.trans.signroom.service.RmSignRoomService;
import com.centaline.trans.signroom.vo.DateWeekVo;
import com.centaline.trans.signroom.vo.FreeRoomVo;
import com.centaline.trans.signroom.vo.ReservationInfoVo;
import com.centaline.trans.utils.BeanToMapUtils;
import com.centaline.trans.utils.ConstantsUtil;
import com.centaline.trans.utils.DateUtil;

/**
 * 签约室业务类
 * 
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
	@Autowired
	private UamBasedataService uamBasedataService;
	@Resource
	TradeCenterScheduleMapper tradeCenterScheduleMapper;

	@Override
	public AjaxResponse<Map> generatePageDate(JQGridParam gp) {
		AjaxResponse<Map> response = new AjaxResponse<Map>();
		try {

			gp.setCountOnly(false);
			gp.setPagination(false);
			gp.setQueryId("querySignRoomAllotList");
			/*
			 * List<RmSignRoom> signRooms =
			 * rmSignRoomMapper.getSignRoomInfos(map); List<RmRoomSchedule>
			 * rmRoomSchedules = rmRoomScheduleMapper.getRmRoomSchedules(map);
			 */
			Page<Map<String, Object>> room = quickGridService.findPageForSqlServer(gp);
			List<Map<String, Object>> rooms = room.getContent();// 签约室信息
			gp.setQueryId("queryRmRoomSchedualList");
			Page<Map<String, Object>> schedual = quickGridService.findPageForSqlServer(gp);
			List<Map<String, Object>> scheduals = schedual.getContent();// 排期信息

			List<RmSignRoom> signRooms = new ArrayList<RmSignRoom>();
			List<RmRoomSchedule> rmRoomSchedules = new ArrayList<RmRoomSchedule>();// map转object
			if (rooms != null && !rooms.isEmpty()) {
				for (Map<String, Object> rm : rooms) {
					RmSignRoom sr = (RmSignRoom) BeanToMapUtils.convertMap(RmSignRoom.class, rm);
					signRooms.add(sr);
				}
			}
			if (scheduals != null && !scheduals.isEmpty()) {
				for (Map<String, Object> sd : scheduals) {
					RmRoomSchedule sr = (RmRoomSchedule) BeanToMapUtils.convertMap(RmRoomSchedule.class, sd);
					rmRoomSchedules.add(sr);
				}
			}

			List<RmRoomSchedule> rrs = null;
			if (signRooms != null && !signRooms.isEmpty()
					&& rmRoomSchedules != null && !rmRoomSchedules.isEmpty()) {
				for (RmSignRoom signRoom : signRooms) {
					rrs = new ArrayList<RmRoomSchedule>();
					for (RmRoomSchedule rmRoomSchedule : rmRoomSchedules) {
						Long startTime = rmRoomSchedule.getStartDate().getTime();//该时间段的开始时间
						Long createTime = null;
						if(rmRoomSchedule.getCreateTime()!=null){
							createTime = rmRoomSchedule.getCreateTime().getTime();//真实预约时间
						}
						Long curTime = new Date().getTime();//当前时间
						Long endTime = rmRoomSchedule.getEndDate().getTime();//该时间段的结束时间
						if (signRoom.getPkid().equals(rmRoomSchedule.getRoomId())) {
							if(rmRoomSchedule.getResStatus()==null){
								rmRoomSchedule.setUseStatus("N");
							}else if(rmRoomSchedule.getResStatus()!=null && "4".equals(rmRoomSchedule.getResStatus().trim())){//预约已取消状态为空置
								rmRoomSchedule.setUseStatus("N");
							}else{
								if(rmRoomSchedule.getCheckInTime()!=null){//是否已签到
									if(rmRoomSchedule.getCheckOutTime()!=null){//是否已签退
										rmRoomSchedule.setUseStatus("N");
										
									}else{
										rmRoomSchedule.setUseStatus("1");//使用中
									}
								}else{//未签到的话就判断是否已超出时间段开始时间半个小时
									Long second = null;
									if(createTime!=null && (createTime>startTime)){
										second = (curTime-createTime)/1000/60;//取得两者时间查转成分钟
										if(second>30){//超过三十分钟的话状态就为空置
											rmRoomSchedule.setUseStatus("N");
										}else{
											rmRoomSchedule.setUseStatus("0");
										}
									}else{
										second = (curTime-startTime)/1000/60;//取得两者时间查转成分钟
										if(second>30){//超过三十分钟的话状态就为空置
											rmRoomSchedule.setUseStatus("N");
										}else{
											rmRoomSchedule.setUseStatus("0");
										}
									}
									
								}
							}
							if("N".equals(rmRoomSchedule.getUseStatus())){//当空置状态在判断当前时间与结束时间段比较小于结束时间页面不置灰，大于结束页面置灰，其他情况页面都置灰
								if(curTime<endTime){
									rmRoomSchedule.setZhiHui(false);
								}else if(curTime>=endTime){
									rmRoomSchedule.setZhiHui(true);
								}
							}else{
								rmRoomSchedule.setZhiHui(true);
							}
							rrs.add(rmRoomSchedule);
						}
					}
					signRoom.setRmRoomSchedules(rrs);
				}

			}

			String timeslot = rmSignRoomMapper.getTimeSlots();// 获取时间段
			String[] timeslots = null;
			if (timeslot != null && timeslot.length() > 0) {
				timeslots = StringUtils.split(timeslot, ",");
			}
			Map res = new HashMap();
			res.put("signRooms", signRooms);
			res.put("timeslots", timeslots);

			response.setContent(res);
			response.setCode("400");
			response.setMessage("查询成功！");
			response.setSuccess(true);

		} catch (Exception e) {
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
	public List<TradeCenter> getTradeCenters(Map map) {
		return tradeCenterMapper.getTradeCenterListByDistrictId(map);
	}

	@Override
	public AjaxResponse<List<RmSignRoom>> signRoomShedualList(JQGridParam gp) {
		AjaxResponse<List<RmSignRoom>> response = new AjaxResponse<List<RmSignRoom>>();
		/*
		 * List<RmSignRoom> rmSignRooms =
		 * rmSignRoomMapper.getRmSignRoomAndStragegy(map);
		 */
		gp.setCountOnly(false);
		gp.setPagination(false);
		gp.setQueryId("queryRmSignRoomAndStragegy");
		Page<Map<String, Object>> rs = quickGridService.findPageForSqlServer(gp);
		List<Map<String, Object>> rss = rs.getContent();
		List<RmSignRoom> rmSignRooms = null;
		try {
			if (rss != null && !rss.isEmpty()) {
				rmSignRooms = new ArrayList<RmSignRoom>();
				for (Map<String, Object> rm : rss) {
					RmSignRoom sr = (RmSignRoom) BeanToMapUtils.convertMap(RmSignRoom.class, rm);
					rmSignRooms.add(sr);
				}
			}

			if (rmSignRooms != null && !rmSignRooms.isEmpty()) {
				for (RmSignRoom rmSignRoom : rmSignRooms) {
					generateSchedule(rmSignRoom);
				}
			}
			response.setCode("400");
			response.setMessage("签约室配置管理列表查询成功！");
			response.setContent(rmSignRooms);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode("500");
			response.setMessage("签约室配置管理列表查询失败！");
			response.setSuccess(false);
		}
		return response;
	}

	/**
	 * 根据策略值生成星期几
	 * 
	 * @param rmSignRoom
	 * @return
	 */
	public RmSignRoom generateSchedule(RmSignRoom rmSignRoom) {

		Long stragegyWeekVal = rmSignRoom.getStragegyWeekVal();

		Map map = new HashMap();
		if ((stragegyWeekVal & (int) Math.pow(2, 1)) > 0) {
			map.put("1", true);
		} else {
			map.put("1", false);
		}
		if ((stragegyWeekVal & (int) Math.pow(2, 2)) > 0) {
			map.put("2", true);
		} else {
			map.put("2", false);
		}
		if ((stragegyWeekVal & (int) Math.pow(2, 3)) > 0) {
			map.put("3", true);
		} else {
			map.put("3", false);
		}
		if ((stragegyWeekVal & (int) Math.pow(2, 4)) > 0) {
			map.put("4", true);
		} else {
			map.put("4", false);
		}
		if ((stragegyWeekVal & (int) Math.pow(2, 5)) > 0) {
			map.put("5", true);
		} else {
			map.put("5", false);
		}
		if ((stragegyWeekVal & (int) Math.pow(2, 6)) > 0) {
			map.put("6", true);
		} else {
			map.put("6", false);
		}
		if ((stragegyWeekVal & (int) Math.pow(2, 7)) > 0) {
			map.put("7", true);
		} else {
			map.put("7", false);
		}
		rmSignRoom.setWeeks(map);
		return rmSignRoom;
	}

	@Override
	public void saveOrUpdateSignRoomSchedual(RmSignRoom rmSignRoom) {
		SessionUser user = uamSessionService.getSessionUser();
		Long pkid = rmSignRoom.getPkid();

		if (pkid != null) {// 更新
			rmSignRoom.setUpdateBy(user.getId());
			rmSignRoom.setUpdateTime(Calendar.getInstance().getTime());
			rmSignRoomMapper.updateRmSignRoom(rmSignRoom);
			RmRoomScheStragegy rmRoomScheStragegy = new RmRoomScheStragegy();
			rmRoomScheStragegy.setRoomId(pkid);
			rmRoomScheStragegy.setStragegyWeekVal(rmSignRoom.getStragegyWeekVal());
			rmRoomScheStragegyMapper.updateRmRoomScheStragegy(rmRoomScheStragegy);

		} else {// 新增
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
		// rmRoomScheStragegyMapper.deleteRmRoomScheStragegyByPkid(rmSignRoom.getStragegyPkid());
		rmSignRoomMapper.deleteRmSignRoomById(rmSignRoom.getPkid());
		rmRoomScheduleMapper.deleteRmRoomScheduleByRoomId(rmSignRoom.getPkid());
	}

	@Override
	public void addReservation(ReservationInfoVo reservationInfoVo) {
		SessionUser currentUser = uamSessionService.getSessionUser();
		Reservation reservation = new Reservation();

		String dateStr = DateUtil.getFormatDate(new Date(), "yyMMdd");
		String resNo = uamBasedataService.nextSeqVal("QYSYY_CODE", dateStr);

		if (reservationInfoVo != null) {
			Long startDate = reservationInfoVo.getStartDate();
			reservation.setResNo(resNo);
			reservation.setResType(reservationInfoVo.getResType());
			reservation.setResPersonOrgId(reservationInfoVo.getResPersonOrgId());
			reservation.setResPersonId(reservationInfoVo.getResPersonId());
			reservation.setScheduleId(reservationInfoVo.getScheduleId());
			reservation.setCaseCode(reservationInfoVo.getCaseCode());
			reservation.setPropertyAddress(reservationInfoVo.getPropertyAddress());
			reservation.setSigningCenter(reservationInfoVo.getSigningCenter());
			reservation.setSigningCenterId(reservationInfoVo.getSigningCenterId());
			reservation.setNumberOfParticipants(reservationInfoVo.getNumberOfParticipants());
			reservation.setNumberOfPeople(reservationInfoVo.getNumberOfPeople());
			reservation.setTransactItemCode(reservationInfoVo.getTransactItemCode());
			reservation.setCreateTime(Calendar.getInstance().getTime());
			reservation.setCreateBy(currentUser.getId());
			reservation.setUpdateTime(Calendar.getInstance().getTime());
			reservation.setUpdateBy(currentUser.getId());
			if(startDate!=null && startDate>(new Date().getTime())){//预约房间
				reservation.setResStatus("0");
			}else{//临时分配房间
				reservation.setResStatus(reservationInfoVo.getResStatus());
				reservation.setCheckInTime(Calendar.getInstance().getTime());
			}
			reservationMapper.insertSelective(reservation);// 插入临时分配的信息
			FreeRoomVo freeRoomVo = new FreeRoomVo();
			freeRoomVo.setResId(reservation.getPkid());
			freeRoomVo.setScheduleId(reservationInfoVo.getScheduleId());
			rmRoomScheduleMapper.updateFreeRoomStatus(freeRoomVo); // 更新闲置房间的使用状态
			
		}

	}

	// map转object
	public Object mapToObject(Map<String, Object> map, Class<?> beanClass)
			throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		org.apache.commons.beanutils.BeanUtils.populate(obj, map);

		return obj;
	}

	@Override
	public boolean isExist(RmSignRoom rmSignRoom) {
		
		int num = rmSignRoomMapper.getRmSignRoomCount(rmSignRoom);
		if(num>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isUsedByRmRoomSchedule(ReservationInfoVo reservationInfoVo) {
		RmRoomSchedule rmRoomSchedule = rmRoomScheduleMapper.getRmRoomScheduleByPkid(Long.valueOf(reservationInfoVo.getScheduleId()));
		
		Long startTime = rmRoomSchedule.getStartDate().getTime();//该时间段的开始时间
		Long createTime = null;
		if(rmRoomSchedule.getCreateTime()!=null){
			createTime = rmRoomSchedule.getCreateTime().getTime();//真实预约时间
		}
		Long curTime = new Date().getTime();//当前时间
		if(rmRoomSchedule.getResStatus()==null){
			return true;
		}else if(rmRoomSchedule.getResStatus()!=null && "4".equals(rmRoomSchedule.getResStatus().trim())){//预约已取消状态为空置
			return true;
		}else{
			if(rmRoomSchedule.getCheckInTime()!=null){//是否已签到
				if(rmRoomSchedule.getCheckOutTime()!=null){//是否已签退
					return true;
				}else{
					return false;//使用中
				}
			}else{//未签到的话就判断是否已超出时间段开始时间半个小时
				Long second = null;
				if(createTime!=null && (createTime>startTime)){
					second = (curTime-createTime)/1000/60;//取得两者时间查转成分钟
					if(second>30){//超过三十分钟的话状态就为空置
						return true;
					}else{
						return false;//预约中
					}
				}else{
					second = (curTime-startTime)/1000/60;//取得两者时间查转成分钟
					if(second>30){//超过三十分钟的话状态就为空置
						return true;
					}else{
						rmRoomSchedule.setUseStatus("0");
						return false;//预约中
					}
				}
			}
		}
	}

	@Override
	public boolean isCanDelSignRoom(RmSignRoom rmSignRoom) {
		int num = reservationMapper.getReservationNotCancleCount(rmSignRoom);
		if(num>0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<List<DateWeekVo>> showSchedulingData(Map map) throws ParseException {
		int centerId= (int) map.get("centerId");
		String date = (String) map.get("date");
		List<DateWeekVo> dwvs = getMonthSchedules(date);
		String dutyDateStart=null;
		String dutyDateEnd = null;
		Map params = new HashMap();
		if(dwvs!=null && dwvs.size()>0){
			dutyDateStart = dwvs.get(0).getDate();
			dutyDateEnd = dwvs.get(dwvs.size()-1).getDate();
		}
		params.put("dutyDateStart", dutyDateStart);
		params.put("dutyDateEnd", dutyDateEnd);
		params.put("centerId", centerId);
		List<TradeCenterSchedule> tcss = tradeCenterScheduleMapper.queryTradeCenterSchedules(params);//日历范围内的所有值班数据
		if(dwvs!=null && dwvs.size()>0 && tcss!=null && tcss.size()>0){//给每个日期分配值班人员
			List<TradeCenterSchedule> tcs = null;
			for(DateWeekVo dv:dwvs){
				tcs = new ArrayList<TradeCenterSchedule>();
				for(int i=0;i<tcss.size();i++){
					if(dv.getDate().equals(tcss.get(i).getDutyDate())){
						tcs.add(tcss.get(i));
					}
				}
				dv.setTcs(tcs);
			}
		}
		tcss = null;
		List<List<DateWeekVo>> lists = new ArrayList<List<DateWeekVo>>();
		List<DateWeekVo> dw = new ArrayList<DateWeekVo>();
		for(int i=0;i<dwvs.size();i++){
			dw.add(dwvs.get(i));
			if(dw.size()==7){
				lists.add(dw);
				dw = new ArrayList<DateWeekVo>();
			}
		}
		
		return lists;
	}
	
	/**
	 * 排班页面日历数据
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public List<DateWeekVo> getMonthSchedules(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
		List<DateWeekVo> dates = new ArrayList<DateWeekVo>();
		DateWeekVo dw = null;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(sdf.parse(date));
		c1.set(Calendar.DAY_OF_MONTH,1);//指定日期的月份的第一天
		int firstDay = c1.get(Calendar.DAY_OF_WEEK);//第一天是星期几
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));//指定日期月份的最后一天
		//int lastDay = c1.get(Calendar.DAY_OF_WEEK);//最后一天是星期几
		int days = c1.getActualMaximum(Calendar.DATE);//该月份总共多少天
		
		Calendar c2 = Calendar.getInstance();
		String  curDate = sdf.format(Calendar.getInstance().getTime());
		if(firstDay>1){//星期日外国定为1  取指定月份前一个月的最后几天
			int headWeekNum = firstDay-1;
			for(int i=headWeekNum-1;i>=0;i--){
				
				c2.setTime(sdf.parse(date));
				c2.add(Calendar.MONTH, 0);
				c2.set(Calendar.DAY_OF_MONTH,-(i));
				dw = new DateWeekVo();
				dw.setDate(sdf.format(c2.getTime()));
				dw.setWeek(c2.get(Calendar.DAY_OF_WEEK));
				if(sdf.parse(sdf.format(c2.getTime())).getTime()>=sdf.parse(curDate).getTime()){//当日之后可编辑
					dw.setEdit(true);
				}
				dw.setDay(Integer.valueOf(sdf1.format(c2.getTime())));
				dates.add(dw);
			}
		}
		int midWeekNum = days;
		
		for(int j=1;j<=midWeekNum;j++){
			c1.add(Calendar.MONTH, 0);
			c1.set(Calendar.DAY_OF_MONTH,j);
			dw = new DateWeekVo();
			dw.setDate(sdf.format(c1.getTime()));
			dw.setWeek(c1.get(Calendar.DAY_OF_WEEK));
			if(sdf.parse(sdf.format(c1.getTime())).getTime()>=sdf.parse(curDate).getTime()){//当日之后可编辑
				dw.setEdit(true);
			}
			dw.setDay(Integer.valueOf(sdf1.format(c1.getTime())));
			if(sdf.format(c1.getTime()).equals(sdf.format(Calendar.getInstance().getTime()))){//当天高亮
				dw.setLight(true);
			}
			dates.add(dw);
		}
		if(ConstantsUtil.SHOW_DAYS>dates.size()){//取指定月份下一个月份的前几天数据
			int lastWeekNum = ConstantsUtil.SHOW_DAYS-dates.size();
			for(int i=1;i<=lastWeekNum;i++){
				c2.setTime(sdf.parse(date));
				c2.add(Calendar.MONTH, 1);
				c2.set(Calendar.DAY_OF_MONTH, i);
				dw = new DateWeekVo();
				dw.setDate(sdf.format(c2.getTime()));
				dw.setWeek(c2.get(Calendar.DAY_OF_WEEK));
				if(sdf.parse(sdf.format(c2.getTime())).getTime()>=sdf.parse(curDate).getTime()){//当日之后可编辑
					dw.setEdit(true);
				}
				dw.setDay(Integer.valueOf(sdf1.format(c2.getTime())));
				if(sdf.format(c2.getTime()).equals(sdf.format(Calendar.getInstance().getTime()))){//当天高亮
					dw.setLight(true);
				}
				dates.add(dw);
			}
		}
		return dates;
	}

	@Override
	public int addOrUpdateTradeCenterSchedule(TradeCenterSchedule tradeCenterSchedule) {
		if(tradeCenterSchedule.isChanged()){//更新值班表
			return tradeCenterScheduleMapper.updateTradeCenterSchedule(tradeCenterSchedule);
		}else{//新增值班信息
			return tradeCenterScheduleMapper.addTradeCenterSchedule(tradeCenterSchedule);
		}
		
	}

	@Override
	public int deleteTradeCenterSchedule(TradeCenterSchedule tradeCenterSchedule) {
		return tradeCenterScheduleMapper.deleteTradeCenterSchedule(tradeCenterSchedule);
	}

	@Override
	public List<TradeCenterSchedule> queryTradeCenterSchedules(Map map) {
		return tradeCenterScheduleMapper.queryTradeCenterSchedules(map);
	}

}
