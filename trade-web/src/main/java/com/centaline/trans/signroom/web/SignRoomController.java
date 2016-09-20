package com.centaline.trans.signroom.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 签约室控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping("/signroom")
public class SignRoomController {

	/**
	 * 签约室分配列表
	 * @return
	 */
	@RequestMapping("/signRoomAllotList")
	public String signRoomAllotList(){
		return "/signroom/signingallot";
	}
	
}
