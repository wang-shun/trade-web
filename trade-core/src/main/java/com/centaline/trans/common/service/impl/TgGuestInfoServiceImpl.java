package com.centaline.trans.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.task.entity.TsMsgSendHistory;
import com.centaline.trans.task.repository.TsMsgSendHistoryMapper;
import org.springframework.beans.factory.annotation.Value;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.utils.wechat.ParamesAPI;


@Service
public class TgGuestInfoServiceImpl implements TgGuestInfoService {

	@Autowired
	TgGuestInfoMapper tgGuestInfoMapper;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired(required=true)
    private UamTemplateService uamTemplateService;
	
	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
	
	static final String SMS_STATUS_SCUESS = "1000";
	
	@Value("${centaline.sms.account}")
	private String smsAccount;
	
	@Value("${centaline.sms.switchsms}")
	private String switchsms;
	
	@Value("${centaline.sms.switchweixin}")
	private String switchweixin;
	
	@Autowired
    private UamBasedataService uambasedataService;
	
	@Autowired
	private ToPropertyInfoMapper topropertyInfoMapper;
	
	@Autowired
	private TsMsgSendHistoryMapper tsmsgSendHistoryMapper;
	
	@Autowired
	private ToCaseInfoMapper tocaseInfoMapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Override
	public List<TgGuestInfo> findTgGuestInfoByCaseCode(String caseCode) {
		return tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
	}

	@Override
	public List<TgGuestInfo> findTgGuestInfosByCaseCode(TgGuestInfo tgGuestInfo) {
		List<TgGuestInfo> list = tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfo);
		return list;
	}

	@Override
	public TgGuestInfo findTgGuestInfoByGuestCode(String guestCode) {
		return tgGuestInfoMapper.findTgGuestInfoByGuestCode(guestCode);
	}

	@Override
	public TgGuestInfo findTgGuestInfoById(Long pkid) {
		return tgGuestInfoMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int removeGuestInfo(Long pkid) {
		return tgGuestInfoMapper.deleteByPrimaryKey(pkid);
	}
	
	
	/**
	 * 功能: 给客户发送短信
	 * 作者：zhangxb16
	 */
	public int sendAssignMsg(String username, String phone, String propertyAddress, String partCode){
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("username", username);  // 用户姓名
		params.put("property_address", propertyAddress);  // 地址
		params.put("part_code", partCode);  // 环节
		
		Message message=new Message();
		String content=uamTemplateService.mergeTemplate("process_notice_custom", params);  // 拼接发送的字符串
		message.setContent(content);
		message.setType(MessageType.SMS);
		return SMS_STATUS_SCUESS.equals(uamMessageService.sendMessageByDist(message, phone, smsAccount)) ? 1 : 0;
    }
	
	
	/**
	 * 功能：给经纪人发送微信
	 * 作者：zhangxb16
	 */
	public int sendWeixinMessage(String agentCode, String username, String phone,
			String propertyAddress, String partCode) {
		
		int weixin=1; // 1代表成功, 0代表失败
		try{
			String senderId = uamSessionService.getSessionUser().getId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", username); // 用户姓名R
			params.put("property_address", propertyAddress); // 地址
			params.put("part_code", partCode); // 环节

			Message message = new Message();
			String content = uamTemplateService.mergeTemplate("process_notice_custom", params);
			message.setType(MessageType.SITE);
			message.setChanel(ParamesAPI.NEW_AGENCE);
			message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode()); // 设置提醒列别
			message.setContent(content);
			message.setSenderId(senderId); // 发送人
			uamMessageService.sendMessageByUser(message, agentCode);  // 接收人[经纪人]
		}catch(Exception ex){
			ex.printStackTrace();
			weixin=0;
		}
		
		return weixin;
	}
	
	
	/**
	 * 功能: 给客户发送短信
	 * 作者：zhangxb16
	 */
	public int sendMsgHistory(String caseCode, String partCode){
		
		int result=0;
		
		// 控制是否发送短信的开关[1代表开, 0代表关]
		if(switchsms.equals("1")){
			try{
				// 1 根据 caseCode 到客户表中去查下客户的姓名, 电话[电话不为空的] 
				String address=null;
				String partcode=null;
				Date date=new Date();
				SessionUser user=uamSessionService.getSessionUser();
				// 将partCode 转为 name
				Dict dt=uambasedataService.findDictByTypeAndCode("part_code", partCode);
				if(null!=dt){
					partcode=dt.getName();
				}
				List<TgGuestInfo> guestList=tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
				// 2 根据 caseCode 到物业表[T_TO_PROPERTY_INFO]中去查询地址
				ToPropertyInfo proinfo=topropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
				if(null!=proinfo){
					address=proinfo.getPropertyAddr();
				}
				if(null!=guestList && guestList.size()>0){
					for(int k=0; k<guestList.size(); k++){
						// 2 发送短信
						String phone=guestList.get(k).getGuestPhone();
						if(null!=phone){
							result=sendAssignMsg(guestList.get(k).getGuestName(), phone, address, partcode); // 发送短信
							
							// 根据 caseCode 到T_TS_MSG_SEND_HISTORY 表中去查询, 如果已经发过了则不在发送, 如果没有则发送
							TsMsgSendHistory msghist=new TsMsgSendHistory();
							msghist.setCaseCode(caseCode);
							msghist.setReceiverName(guestList.get(k).getGuestName());
							msghist.setPartCode(partcode);
							int countMsg=tsmsgSendHistoryMapper.countMsghistory(msghist);
							if(countMsg==0){
								// 往 T_TS_MSG_SEND_HISTORY 表中写入数据
								TsMsgSendHistory msgsend=new TsMsgSendHistory();
								msgsend.setCaseCode(caseCode);  // 案件编号
								msgsend.setPartCode(partCode);  // 环节编码
								msgsend.setSendTime(date);  // 发送时间
								msgsend.setMsgCat("SMS");  // 消息类型
								msgsend.setSenderId(user.getId());  // 发送人[当前系统登录人的userid]
								msgsend.setReceiverName(guestList.get(k).getGuestName());  // 接收人
								msgsend.setReceiverPhone(phone);
								msgsend.setReceiverCat(guestList.get(k).getTransPosition());  // 接收人类型
								msgsend.setIsSuccess(Integer.toString(result)); // 是否成功[1代表成功, 0代表失败]
								int msghistory=tsmsgSendHistoryMapper.insert(msgsend);
							}
							if(result>0){
							}else{
								throw new BusinessException("短信发送失败, 请您线下手工再次发送！");
							}
						}
					}
				}
			}catch(BusinessException ex){
				ex.getMessage();
			}
		}else{
			result=1; // 主要是为了不让result 为0, 因为如果为0 则或alert 发送失败的消息提示
		}
		
		
		// 控制是否发送微信的开关[1代表开, 0代表关]
		if(switchweixin.equals("1")){
			// 1 根据 caseCode 到客户表中去查下客户的姓名, 电话[电话不为空的] 
			String address=null;
			String partcode=null;
			Date date=new Date();
			SessionUser user=uamSessionService.getSessionUser();
			// 将partCode 转为 name
			Dict dt=uambasedataService.findDictByTypeAndCode("part_code", partCode);
			if(null!=dt){
				partcode=dt.getName();
			}
			
			// 2 根据 caseCode 到物业表[T_TO_PROPERTY_INFO]中去查询地址
			ToPropertyInfo proinfo=topropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
			if(null!=proinfo){
				address=proinfo.getPropertyAddr();
			}
			
			// 下面为发送微信, 并往T_TS_MSG_SEND_HISTORY表中写入记录，根据caseCode 去到 T_TO_CASE_INFO 表中查询出 经纪人
			String agentCode=null;
			int weixin=0;
			ToCaseInfo caseinfo=tocaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
			if(null!=caseinfo){
				agentCode=caseinfo.getAgentCode();
			}
			if(agentCode != null) {
				User uu=uamUserOrgService.getUserById(agentCode);
				if(null!=uu){
					weixin=sendWeixinMessage(agentCode, uu.getRealName(), uu.getMobile(), address, partcode);  // 发送微信
					// 根据 caseCode 到T_TS_MSG_SEND_HISTORY 表中去查询, 如果已经发过了则不在发送, 如果没有则发送
					TsMsgSendHistory msghist=new TsMsgSendHistory();
					msghist.setCaseCode(caseCode);
					msghist.setReceiverName(uu.getRealName());
					msghist.setPartCode(partcode);
					int countMsg=tsmsgSendHistoryMapper.countMsghistory(msghist);
					if(countMsg==0){
						// 往 T_TS_MSG_SEND_HISTORY 表中写入数据
						TsMsgSendHistory msgsend=new TsMsgSendHistory();
						msgsend.setCaseCode(caseCode);  // 案件编号
						msgsend.setPartCode(partCode);  // 环节编码
						msgsend.setSendTime(date);  // 发送时间
						msgsend.setMsgCat("SITE");  // 消息类型
						msgsend.setSenderId(user.getId());  // 发送人[当前系统登录人的userid]
						msgsend.setReceiverName(uu.getRealName());  // 接收人
						msgsend.setReceiverPhone(uu.getMobile());
						msgsend.setReceiverCat("30006003");  // 接收人类型
						msgsend.setIsSuccess(Integer.toString(weixin)); // 是否成功[1代表成功, 0代表失败]
						int msghistory=tsmsgSendHistoryMapper.insert(msgsend);
					}
				}
			}
		}
		
		return result;
	}

	
	@Override
	public TgGuestInfo selectByPrimaryKey(Long pkid) {
		TgGuestInfo tg=tgGuestInfoMapper.selectByPrimaryKey(pkid);
		return tg;
	}

	@Override
	public int updateByPrimaryKeySelective(TgGuestInfo tgGuestInfo) {
		return tgGuestInfoMapper.updateByPrimaryKey(tgGuestInfo);
	}
	
	
}
