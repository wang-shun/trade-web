package com.centaline.trans.signroom.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
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
	public AjaxResponse<Map> generatePageDate(Model model){
		SessionUser user= uamSessionService.getSessionUser();
		String busigrpId  = user.getBusigrpId();//店组
		Map map = new HashMap();
		map.put("busigrpId", busigrpId);
		map.put("curDate", "2016-10-3");
		AjaxResponse<Map> response =  rmSignRoomService.generatePageDate(map);
		
		return response;
	}
	
}
