package com.centaline.trans.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;

@Controller
@RequestMapping(value="/guestInfo")
public class GuestInfoController {

	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@RequestMapping(value="/removeGuest")
	@ResponseBody
	public Boolean removeGuest(HttpServletRequest request, Long pkid) {
		tgGuestInfoService.removeGuestInfo(pkid);
		return true;
	}
	
	@RequestMapping(value="queryGuestInfo")
	@ResponseBody
	public List<TgGuestInfo> queryGuestInfo(String caseCode, String transPosition) {
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		tgGuestInfo.setCaseCode(caseCode);
		tgGuestInfo.setTransPosition(transPosition);
		List<TgGuestInfo> list = tgGuestInfoService.findTgGuestInfosByCaseCode(tgGuestInfo);
		return list;
	}
}
