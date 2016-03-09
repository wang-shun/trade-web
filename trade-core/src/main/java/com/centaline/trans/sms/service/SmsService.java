package com.centaline.trans.sms.service;

import java.util.List;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.sms.vo.SmsVo;

public interface SmsService {
	List<SmsVo> getTradeSms(SessionUser user, String caseCode,
			String serviceItem);

	int sendSms(SmsVo vo, String userId);
}
