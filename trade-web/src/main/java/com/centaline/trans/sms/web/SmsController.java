package com.centaline.trans.sms.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.sms.service.SmsService;
import com.centaline.trans.sms.vo.SmsVo;

@Controller
@RequestMapping("/sms")
public class SmsController {

	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private SmsService smsService;

	@RequestMapping("/getTradeSms")
	@ResponseBody
	public List<SmsVo> getTradeSms(String caseCode, String serviceItem) {
		SessionUser user = uamSessionService.getSessionUser();
		return smsService.getTradeSms(user, caseCode, serviceItem);
	}

	@RequestMapping("/sendSms")
	@ResponseBody
	public AjaxResponse sendSms(SmsVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		int r = smsService.sendSms(vo, user.getId());
		return new AjaxResponse<>(r == 1);
	}
}
