package com.centaline.trans.sms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.sms.service.SmsService;
import com.centaline.trans.sms.vo.SmsVo;

@Service
public class SmsServiceImpl implements SmsService {
	static final String SMS_STATUS_SCUESS = "1000";
	static final Map<String, String> TEMPLATE_MAP;
	static {
		TEMPLATE_MAP = new HashMap<>();
		TEMPLATE_MAP.put("buyer", "buyer_sms_template");
		TEMPLATE_MAP.put("seller", "seller_sms_template");
		TEMPLATE_MAP.put("agent", "agent_sms_template");
	}
	@Value("${centaline.sms.account}")
	private String smsAccount;
	@Autowired
	private UamTemplateService uamTemplateService;
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToPropertyInfoService topropertyInfoService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Resource(name = "uamMessageServiceClient")
	private UamMessageService uamMessageService;

	@Override
	public List<SmsVo> getTradeSms(SessionUser user, String caseCode,
			String serviceItem) {
		List<SmsVo> smsVos = new ArrayList<>();
		List<TgGuestInfo> guests = tgGuestInfoService
				.findTgGuestInfoByCaseCode(caseCode);

		Map<String, Object> pMap = buildTempVar(caseCode, user, guests);
		pMap.put("content", serviceItem);
		for (TgGuestInfo tgGuestInfo : guests) {
			SmsVo vo = new SmsVo();
			if (tgGuestInfo.getTransPosition().equals(
					TransPositionEnum.TKHSJ.getCode())) {
				vo.setContent(uamTemplateService.mergeTemplate(
						TEMPLATE_MAP.get("seller"), pMap));
			} else if (tgGuestInfo.getTransPosition().equals(
					TransPositionEnum.TKHXJ.getCode())) {
				vo.setContent(uamTemplateService.mergeTemplate(
						TEMPLATE_MAP.get("buyer"), pMap));
			}
			vo.setPhone((tgGuestInfo.getGuestPhone()));
			vo.setUserName(tgGuestInfo.getGuestName());
			vo.setUserFlag(TransPositionEnum.TKHSJ.getCode()
					+ tgGuestInfo.getGuestName());
			smsVos.add(vo);
		}
		if (pMap.get("agentId") != null) {
			SmsVo vo = new SmsVo();
			vo.setPhone((pMap.get("agentPhone").toString()));
			vo.setContent(uamTemplateService.mergeTemplate(
					TEMPLATE_MAP.get("agent"), pMap));
			vo.setUserName(pMap.get("agentName").toString());
			vo.setUserFlag(pMap.get("agentId").toString());
			smsVos.add(vo);

		}
		return smsVos;
	}

	@Override
	public int sendSms(SmsVo vo, String userId) {
		Message message = new Message();
		message.setContent(vo.getContent());
		message.setSenderId(userId);
		message.setType(MessageType.SMS);
		return SMS_STATUS_SCUESS.equals(uamMessageService.sendMessageByDist(
				message, vo.getPhone(), smsAccount)) ? 1 : 0;
	}

	public Map<String, Object> buildTempVar(String caseCode, SessionUser user,
			List<TgGuestInfo> guests) {
		Map<String, Object> varMap = new HashMap<>();

		ToCaseInfo caseInfo = toCaseInfoService
				.findToCaseInfoByCaseCode(caseCode);
		String buyer = null, seller = null, pInfo = null, agetnName = null;
		for (TgGuestInfo tgGuestInfo : guests) {
			if (tgGuestInfo.getTransPosition().equals(
					TransPositionEnum.TKHSJ.getCode())) {
				seller = tgGuestInfo.getGuestName();
			} else if (tgGuestInfo.getTransPosition().equals(
					TransPositionEnum.TKHXJ.getCode())) {
				buyer = tgGuestInfo.getGuestName();
			}
		}
		ToPropertyInfo peropertyInfo = topropertyInfoService
				.findToPropertyInfoByCaseCode(caseCode);
		User agentUser = uamUserOrgService.getUserById(caseInfo.getAgentCode());

		pInfo = peropertyInfo.getPropertyAddr();
		varMap.put("buyerName", buyer);
		varMap.put("sellerName", seller);
		varMap.put("property_name", pInfo);
		varMap.put("UserName", user.getRealName());
		varMap.put("orgName", user.getServiceDepName());
		varMap.put("jobName", user.getServiceJobName());
		if (agentUser != null) {
			varMap.put("agentName", caseInfo.getAgentName());
			varMap.put("agentPhone", agentUser.getMobile());
			varMap.put("agentId", agentUser.getId());
		}
		return varMap;
	}
}
