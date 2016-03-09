package com.centaline.trans.property.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.enums.FeedbackAlertEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.PropertyFeedbackEnum;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;
import com.centaline.trans.task.repository.ToPropertyResearchMapper;
import com.centaline.trans.utils.wechat.ParamesAPI;

/**
 * 产调service
 * 
 * @author aisliaohl
 */
@Service
public class ToPropertyServiceImpl implements ToPropertyService {

	/* 发送消息 */
	@Autowired(required = true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;

	@Autowired(required = true)
	private UamTemplateService uamTemplateService;

	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;

	@Autowired(required = true)
	private UamSessionService uamSessionService;

	@Autowired(required = true)
	private ToPropertyResearchMapper toPropertyResearchMapper;

	
	@Override
	public List<ToPropertyResearch> getUnProcessListByDistrict(String district){
		return toPropertyResearchMapper.getUnProcessListByDistrict(district);
	}
	@Override
	public int updateProcessWaitListStatus(String district) {
		int proCount =toPropertyResearchMapper.processWaitListByDistrict(district);
		return proCount;
	}
	@Transactional(readOnly=true)
	@Override
	public void sendMessage(List<ToPropertyResearch>list){
		for (int i = 0; i < list.size(); i++) {
			ToPropertyResearch tpr=list.get(i);
			try {
				/* 消息模版code */
				String resourceCode = PropertyFeedbackEnum.PR_PROCESS
						.getCode();
				sendMessage(Long.parseLong(tpr.getPkid()+""), resourceCode);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送产调提醒信息
	 * 
	 * @param pkid
	 */
	private void sendMessage(Long pkid, String resourceCode) {
		/* 消息标题 */
		String title = "";
		ToPropertyResearch property = toPropertyResearchMapper
				.findToPropertyResearchById(pkid);
		String chanel = null;
		if (resourceCode.equals("PR_process")) {
			if ("1".equals(property.getPrChannel())) {
				resourceCode = PropertyFeedbackEnum.AGENT_PR_ACCEPT_NOTIFICATION
						.getCode();
				chanel = ParamesAPI.NEW_AGENCE;
			}
			title = FeedbackAlertEnum.PROPERTY_PROCESS.getName();// 受理
		} else {
			if ("1".equals(property.getPrChannel())) {
				resourceCode = PropertyFeedbackEnum.AGENT_PR_FINISH_NOTIFICATION
						.getCode();
				chanel = ParamesAPI.NEW_AGENCE;
			}
			title = FeedbackAlertEnum.PROPERTY_SUCCESS.getName();// 完成
		}
		// 创建map放入消息参数
		Map<String, Object> params = new HashMap<String, Object>();
		// 放入参数

		// 查询物业地址
		ToPropertyResearchVo propertyVo = toPropertyResearchMapper
				.findToPropertyResearchAddressById(pkid);
		if (null != propertyVo) {
			params.put("property_address", propertyVo.getPropertyAddr());
		} else {
			params.put("property_address", "无");
		}

		params.put("is_success",
				Integer.valueOf(1).equals(property.getIsSuccess()) ? "成功"
						: "失败");
		params.put("prcode", property.getPrCode());
		// 拼接发送的字符串
		String content = uamTemplateService.mergeTemplate(resourceCode, params);

		Message message = new Message();
		message.setChanel(chanel);
		// 消息标题
		message.setTitle(title);
		// 消息类型
		message.setType(MessageType.SITE);
		/* 设置提醒列别 */
		if ("1".equals(property.getPrChannel())) {
			message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());
		} else {
			message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
		}

		/* 内容 */
		message.setContent(content);
		/* 发送人 */
		String senderId = uamSessionService.getSessionUser().getId();
		/* 设置发送人 */
		message.setSenderId(senderId);
		/* 发送 给产调申请人 */
		List<String> users = new ArrayList<>();
		users.add(property.getPrAppliant());
		uamMessageService.sendMessageByUser(message, users);
	}

	@Override
	public int updateProcessingListStatus(String[] pkidArr,String userId) {
		int proCount = 0;
		for (int i = 0; i < pkidArr.length; i++) {
			toPropertyResearchMapper.updatePropertyToComplete(pkidArr[i],userId);
			// 发送消息
			try {
				/* 消息模版code */
				String resourceCode = PropertyFeedbackEnum.PR_RESPONSE
						.getCode();
				sendMessage(Long.parseLong(pkidArr[i]), resourceCode);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return proCount;
	}


	@Override
	public int insert(ToPropertyResearch record) {
		// TODO Auto-generated method stub
		return toPropertyResearchMapper.insert(record);
	}

	@Override
	public int insertSelective(ToPropertyResearch record) {
		// TODO Auto-generated method stub
		return toPropertyResearchMapper.insertSelective(record);
	}

	@Override
	public List<ToPropertyResearch> queryUnClosePropertyResearchsByCaseCode(
			String caseCode) {
		// TODO Auto-generated method stub
		return toPropertyResearchMapper
				.queryUnClosePropertyResearchsByCaseCode(caseCode);
	}

	@Override
	public int nullityTag(ToPropertyResearch property) {

		return toPropertyResearchMapper.nullityTag(property);
	}

	@Override
	public ToPropertyResearch findToPropertyResearchsByCaseCode(String prCode) {
		ToPropertyResearch toPropertyResearch = new ToPropertyResearch();
		toPropertyResearch.setPrCode(prCode);
		toPropertyResearch.setIsSuccess(0);
		return toPropertyResearchMapper
				.findToPropertyResearchsByCaseCode(toPropertyResearch);
	}

	@Override
	public ToPropertyResearch getBasePRConsult(String caseCode) {
		// TODO Auto-generated method stub
		return toPropertyResearchMapper.getBasePRConsult(caseCode);
	}

	@Override
	public List<ToPropertyResearch> getBasePRAgent() {
		// TODO Auto-generated method stub
		return toPropertyResearchMapper.getBasePRAgent();
	}
}
