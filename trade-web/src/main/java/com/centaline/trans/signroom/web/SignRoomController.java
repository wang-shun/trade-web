package com.centaline.trans.signroom.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.service.RmSignRoomService;
import com.centaline.trans.signroom.vo.DateWeekVo;
import com.centaline.trans.signroom.vo.ReservationInfoVo;
import com.centaline.trans.signroom.vo.TransactItemVo;
import com.centaline.trans.workspace.entity.CacheGridParam;
/**
 * 签约室控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping("/signroom")
public class SignRoomController {
	
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private RmSignRoomService rmSignRoomService;
	@Resource
	ReservationService reservationService;

	/**
	 * 签约室分配列表
	 * @return
	 */
	@RequestMapping("/signRoomAllotList")
	public String signRoomAllotList(Model model){
		SessionUser user= uamSessionService.getSessionUser();
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<TransactItemVo> transactItemVoList = reservationService
				.getTransactItemList();
		model.addAttribute("transactItemVoList", transactItemVoList);
		model.addAttribute("curDate", sdf.format(cd.getTime()));
		return "/signroom/signingallot";
	}
	
	/**
	 * 签约室配置管理列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/signingManage")
	public String signingManage(Model model){
		SessionUser user= uamSessionService.getSessionUser();
		List<TradeCenter> tradeCenters = rmSignRoomService.getTradeCenters();//获取 交易中心信息
		model.addAttribute("tradeCenters", tradeCenters);
		return "/signroom/signingmanage";
	}
	
	/**
	 * 签约室分配页面数据生成
	 * @return
	 */
	@RequestMapping("/generatePageDate")
	@ResponseBody
	public AjaxResponse<Map> generatePageDate(Model model,HttpServletRequest requst){
		SessionUser user= uamSessionService.getSessionUser();
		String depId = user.getServiceDepId();//获取登陆用户的组别id
		
		String roomType = requst.getParameter("roomType");//房间类型
		String useStatus = requst.getParameter("useStatus");//使用状态
		String curDate = requst.getParameter("curDate");//预约日期
		
		JQGridParam gp = new CacheGridParam();
		if(!StringUtil.isBlank(curDate)){
			gp.put("curDate", curDate);
		}
		if(!StringUtil.isBlank(roomType)){
			gp.put("roomType", roomType);
		}
		if(!StringUtil.isBlank(useStatus)){
			gp.put("useStatus", useStatus);
		}
		if(!StringUtil.isBlank(depId)){
			gp.put("depId", depId);
		}
		
		AjaxResponse<Map> response =  rmSignRoomService.generatePageDate(gp);
		
		return response;
	}
	
	/**
	 * 签约室配置管理列表
	 * @return
	 */
	@RequestMapping("/signRoomShedualList")
	@ResponseBody
	public AjaxResponse<List<RmSignRoom>> signRoomShedualList(Model model,HttpServletRequest requst){
		
		String centerId = requst.getParameter("centerId");//签约中心Id
		String roomType = requst.getParameter("roomType");//房间类型
		String roomStatus = requst.getParameter("roomStatus");//房间状态
	
		
		JQGridParam gp = new CacheGridParam();
		if(!StringUtil.isBlank(roomType)){
			gp.put("roomType", roomType);
		}
		if(!StringUtil.isBlank(roomStatus)){
			gp.put("roomStatus", roomStatus);
		}
		if(!StringUtil.isBlank(centerId)){
			gp.put("centerId", centerId);
		}
		
		
		AjaxResponse<List<RmSignRoom>> response =  rmSignRoomService.signRoomShedualList(gp);
		
		return response;
	}
	
	
	/**
	 * 添加或修改签约室
	 * @return
	 */
	@RequestMapping("/addOrUpdateSignRoom")
	@ResponseBody
	public AjaxResponse<T> addOrUpdateSignRoom(Model model,HttpServletRequest requst,RmSignRoom rmSignRoom){
		AjaxResponse<T> response = new AjaxResponse<T>();
		boolean isExist = false;
		if (rmSignRoom.getPkid() == null) {
			isExist = rmSignRoomService.isExist(rmSignRoom);
			if(isExist){
				response.setCode("500");
				response.setMessage("该签约室已存在！");
				response.setSuccess(false);
				return response;
			}
		}
		
		try{
			rmSignRoomService.saveOrUpdateSignRoomSchedual(rmSignRoom);
			response.setCode("400");
			response.setMessage("保存成功！");
			response.setSuccess(true);
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("保存失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	/**
	 * 删除 签约室
	 * @return
	 */
	@RequestMapping("/deleteSignRoom")
	@ResponseBody
	public AjaxResponse<T> deleteSignRoom(Model model,HttpServletRequest requst,RmSignRoom rmSignRoom){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			
			boolean isCanDel = rmSignRoomService.isCanDelSignRoom(rmSignRoom);
			if(isCanDel){
				response.setCode("500");
				response.setMessage("该签约室存在预约信息，不能删除！");
				response.setSuccess(false);
			}else{
				rmSignRoomService.deleteSignRoom(rmSignRoom);
				response.setCode("400");
				response.setMessage("删除成功！");
				response.setSuccess(true);
			}
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("删除失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	/**
	 * 临时分配签约室
	 * @return
	 */
	@RequestMapping("/addReservation")
	@ResponseBody
	public AjaxResponse<T> addReservation(Model model,HttpServletRequest requst,ReservationInfoVo reservationInfoVo){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			//先判断房间是否已经被预约
			boolean isUsed = rmSignRoomService.isUsedByRmRoomSchedule(reservationInfoVo);
			if(isUsed==true){//未被预约
				rmSignRoomService.addReservation(reservationInfoVo);
				response.setCode("400");
				response.setMessage("分配成功！");
				response.setSuccess(true);
			}else{
				response.setCode("500");
				response.setMessage("预约失败，该时间段已被人预定！");
				response.setSuccess(true);
			}
			
		}catch(Exception e){
			response.setCode("500");
			response.setMessage("分配失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	/**
	 * 签约室排班
	 * @return
	 */
	@RequestMapping("/signscheduling")
	public String signscheduling(Model model){
		SessionUser user= uamSessionService.getSessionUser();
		List<TradeCenter> tradeCenters = rmSignRoomService.getTradeCenters();//获取 交易中心信息
		model.addAttribute("tradeCenters", tradeCenters);
		return "/signroom/signscheduling";
	}
	/**
	 * 签约室排班数据
	 * @return
	 */
	@RequestMapping("/showSchedulingData")
	@ResponseBody
	public AjaxResponse<List<DateWeekVo>> showSchedulingData(Model model,int centerId,String date){
		AjaxResponse<List<DateWeekVo>> response = new AjaxResponse<List<DateWeekVo>>();
		Map map = new HashMap();
		map.put("centerId", centerId);
		map.put("date", date);
		try {
			List<DateWeekVo> dwvs = rmSignRoomService.showSchedulingData(map);
			response.setContent(dwvs);
			response.setCode("400");
			response.setMessage("查询成功！");
			response.setSuccess(true);
		} catch (ParseException e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("查询失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	
}
