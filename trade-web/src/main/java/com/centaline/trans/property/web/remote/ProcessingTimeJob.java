package com.centaline.trans.property.web.remote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.task.entity.ToPropertyResearch;

public class ProcessingTimeJob implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	UamMessageService uamMessageService;
	
	@Autowired(required=true)
	UamTemplateService uamTemplateService;
	
	@Autowired(required=true)
	UamUserOrgService uamUserOrgService;
	
	@Autowired(required = true)
	ToPropertyService toPropertytService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	@Override
	public synchronized void execute(JobExecutionContext context) {
		
		// 获取所有待处理产调
		List<ToPropertyResearch> propertys =  toPropertytService.queryAllProperty("1");
		
		StringBuffer msgContent = new StringBuffer();
		msgContent.append("产调超时提醒：");
		
		if(propertys != null && propertys.size() > 0){
			for (ToPropertyResearch property : propertys) {
				try {
					if(!judgmentDate(sdf.format(property.getPrAccpetTime()), sdf.format(new Date()))){
						
						Map<String, Object> params = new HashMap<String, Object>(); // 创建map
						params.put("prAddress", property.getPrAddress());
						params.put("prStatus", "已受理");
						String content = uamTemplateService.mergeTemplate(MsgLampEnum.PROPERTY_OUTTIME_REMINDER.getCode(), params);//拼接发送的字符串
						
						Message message= new Message();
						message.setTitle(MsgLampEnum.PROPERTY_OUTTIME_REMINDER.getName()); // 消息标题
						message.setType(MessageType.SITE); // 消息类型  
						message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());
						message.setContent(content);
						message.setSenderId(property.getPrAppliant());
						
						// 获取贵宾服务部区域总监信息
						String orgId = uamUserOrgService.getUserById(property.getPrAppliant()).getOrgId();
						Org org = uamUserOrgService.getParentOrgByDepHierarchy(orgId, DepTypeEnum.TYCQY.getCode());
						if(org != null){
							User user = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(org.getId(), TransJobs.TZJ.getCode());
							if(user != null){
								try{
									uamMessageService.sendMessageByDist(message, user.getId());
								}catch(Exception e){
									msgContent.append(user.getRealName()).append("、");
									logger.warn(e.getMessage(),e);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
		
		/**
         * 发送失败消息
         */
        if(msgContent.length() > 7){
            msgContent.append("产调超时提醒消息发送失败。");
            Message message = new Message();
            message.setContent(msgContent.toString());
            message.setTitle("产调超时提醒消息发送失败");
            message.setMsgCatagory(MsgCatagoryEnum.LOSS.getCode());
            message.setType(MessageType.SITE);
            message.setSenderId("SYSTEM");
            message.setSendTime(new Date());
            uamMessageService.sendMessageByUser(message, uamUserOrgService.getUserByUsername("admin").getId());
        }
	}
	
	private boolean judgmentDate(String date1, String date2) throws Exception { 
        Date start = sdf.parse(date1); 
        Date end = sdf.parse(date2); 
        long cha = end.getTime() - start.getTime(); 
        if(cha < 0){
          return false; 
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result <= 24){ 
        	return true; 
        }
        return false; 
    }
	
}
