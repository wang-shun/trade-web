package com.centaline.trans.signroom.web;

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

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.service.RmSignRoomService;
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
		
		model.addAttribute("curDate", sdf.format(cd.getTime()));
		return "/signroom/signingallot";
	}
	
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
		String busigrpId  = user.getBusigrpId();//店组
		
		String orgId = reservationService.getOrgIdByGrpcode(busigrpId);//返回贵宾服务部id
		orgId = "d5878adf8b0c4032aeae895c701ed693";
		String roomType = requst.getParameter("roomType");//房间类型
		String useStatus = requst.getParameter("useStatus");//使用状态
		Map map = new HashMap();
		map.put("curDate", requst.getParameter("curDate"));//当前日期
		if(!StringUtil.isBlank(roomType)){
			map.put("roomType",roomType);
		}
		if(!StringUtil.isBlank(useStatus)){
			map.put("useStatus", useStatus);
		}
		if(!StringUtil.isBlank(orgId)){
			map.put("orgId", orgId);
		}
		AjaxResponse<Map> response =  rmSignRoomService.generatePageDate(map);
		
		return response;
	}
	
	/**
	 * 签约室分配页面数据生成
	 * @return
	 */
	@RequestMapping("/signRoomShedualList")
	@ResponseBody
	public AjaxResponse<List<RmSignRoom>> signRoomShedualList(Model model,HttpServletRequest requst){
		SessionUser user= uamSessionService.getSessionUser();
		
		String orgId = requst.getParameter("orgId");//房间类型
		String roomType = requst.getParameter("roomType");//房间类型
		String roomStatus = requst.getParameter("roomStatus");//房间状态
		Map map = new HashMap();
		if(!StringUtil.isBlank(roomType)){
			map.put("roomType",roomType);
		}
		if(!StringUtil.isBlank(roomStatus)){
			map.put("roomStatus", roomStatus);
		}
		if(!StringUtil.isBlank(orgId)){
			map.put("orgId", orgId);
		}
		AjaxResponse<List<RmSignRoom>> response =  rmSignRoomService.signRoomShedualList(map);
		
		return response;
	}
	
	
	/**
	 * 添加或修改签约室
	 * @return
	 */
	@RequestMapping("/addOrUpdateSignRoom")
	@ResponseBody
	public AjaxResponse<T> addOrUpdateSignRoom(Model model,HttpServletRequest requst,RmSignRoom rmSignRoom){
		SessionUser user= uamSessionService.getSessionUser();
		AjaxResponse<T> response = new AjaxResponse<T>();
		
		return response;
	}
	
}
