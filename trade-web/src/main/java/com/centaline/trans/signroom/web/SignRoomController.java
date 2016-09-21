package com.centaline.trans.signroom.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
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
		String busigrpId  = user.getBusigrpId();//店组
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		model.addAttribute("curDate", sdf.format(cd.getTime()));
		model.addAttribute("busigrpId", busigrpId);
		return "/signroom/signingallot";
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
	
}
