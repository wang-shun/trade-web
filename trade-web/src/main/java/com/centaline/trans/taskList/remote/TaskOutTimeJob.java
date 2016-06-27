package com.centaline.trans.taskList.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.ToOutTimeTask;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.service.ToTransPlanService;

@Component
public class TaskOutTimeJob implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	UamMessageService uamMessageService;
	
	@Autowired(required=true)
	UamTemplateService uamTemplateService;
	@Autowired(required=true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required=true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required=true)
	ToTransPlanService toTransPlanService;
	@Autowired
	UamPermissionService  uamPermissionService;

	@Override
	public synchronized void execute(JobExecutionContext context) {
		// TODO Auto-generated method stub
//		 String content = uamTemplateService.mergeTemplate(ResourceCode, map);//拼接发送的字符串 
//	        message.setContent(content);
		StringBuffer msgContent = new StringBuffer();
        msgContent.append("红灯提醒：");
		List<ToOutTimeTask> taskList = toWorkFlowService.queryOutTimeTaskList();
		if(taskList!=null && taskList.size()!=0){
			for(ToOutTimeTask task:taskList){
				Integer dateLamp = task.getDateLamp();
				//红黄灯
				if(dateLamp!=null ){
					String lampStatus = LampEnum.GREEN.getName();
					String redLock = "0";
					if(dateLamp>-1){
						String resourceCode = MsgLampEnum.YELLOW.getCode();
						String title = MsgLampEnum.YELLOW.getName();
						lampStatus = LampEnum.YELLOW.getName();
						if(dateLamp>3){
							resourceCode = MsgLampEnum.RED.getCode();
							title = MsgLampEnum.RED.getName();
							lampStatus = LampEnum.RED.getName();
							redLock = "1";
						}
						Map<String, Object> params = new HashMap<String, Object>();//创建map
						params.put("property", task.getPropertyAddr());//放入参数
					    params.put("task",task.getPartName());
						String content = uamTemplateService.mergeTemplate(resourceCode, params);//拼接发送的字符串
						
						//交易顾问 提醒
						Message message= new Message();
						message.setTitle(title);//消息标题
						message.setType(MessageType.SITE);//消息类型  
						message.setMsgCatagory(MsgCatagoryEnum.STOPLOSS.getCode());
						message.setContent(content);
						message.setSenderId(uamUserOrgService.getUserByUsername("admin").getId());
						try{
							uamMessageService.sendMessageByDist(message, task.getUserId());
						}catch(Exception e){
							User sendToUser = uamUserOrgService.getUserById(task.getUserId());
							msgContent.append(sendToUser.getRealName()).append("、");
		                    logger.warn(e.getMessage(),e);
						}
						
						
						
					}
					//更新交易计划红绿灯属性
					ToTransPlan toTransPlan = new ToTransPlan();
					ToTransPlan oldTransPlan = toTransPlanService.selectByPrimaryKey(task.getPlanId());
					
					toTransPlan.setPkid(task.getPlanId());
					toTransPlan.setLampStatus(lampStatus);
					if(StringUtils.isEmpty(oldTransPlan.getRedLock()) || oldTransPlan.getRedLock().equals("0")){
						toTransPlan.setRedLock(redLock);
					}
					toTransPlanService.updateByPrimaryKeySelective(toTransPlan);
					
				}
			}
			;
			List<User> managerList = uamUserOrgService.getUserByJobId(uamUserOrgService.getJobByCode(TransJobs.TJYZG.getCode()).getId());

			//经理 红灯提醒
			for(User manager:managerList){
				String resourceCode = MsgLampEnum.REDMANAGER.getCode();
				String title = MsgLampEnum.REDMANAGER.getName();
				Map<String, Object> params = new HashMap<String, Object>();//创建map
				StringBuffer inStr = new StringBuffer();
				
				int taskCount = 0;
				for(ToOutTimeTask task:taskList){
					Integer dateLamp = task.getDateLamp();
						//红灯
						if(dateLamp>3){
							if(manager.getId().equals(task.getManagerId()) || manager.getId().equals(task.getServManagerId())){
								inStr.append(task.getPropertyAddr());
								inStr.append("(");
								inStr.append(task.getUserRealName()+",");
								inStr.append(task.getPartName());
								inStr.append(")/");
								
								++taskCount;
								if(taskCount == 5) {
									App app = uamPermissionService.getAppByAppName("trade-web");
							        String hrefAdd = app.genAbsoluteUrl()+"/workspace/ryLightList?color=0/";
							        String hrefString = hrefAdd;
									inStr.append(hrefString);
									break;
								}
							}
						}
				}
				if(inStr.length()>0){
					inStr.deleteCharAt(inStr.length()-1);
					params.put("add_task_consultant", inStr.toString());
					String content = uamTemplateService.mergeTemplate(resourceCode, params);
					logger.debug(inStr.toString());
					//发送消息
					Message message= new Message();
					message.setTitle(title);//消息标题
					message.setType(MessageType.SITE);//消息类型  
					message.setMsgCatagory(MsgCatagoryEnum.STOPLOSS.getCode());
					message.setContent(content);
					message.setSenderId(uamUserOrgService.getUserByUsername("admin").getId());
					try{
						uamMessageService.sendMessageByDist(message,manager.getId());
					}catch(Exception e){
						User sendToUser = uamUserOrgService.getUserById(manager.getId());
						msgContent.append(sendToUser.getRealName()).append("、");
	                    logger.warn(e.getMessage(),e);
					}
				}
				
			}
		}
		
		
		 /**
         * 发送失败消息
         */
        if(msgContent.length()>5){
            msgContent.append("红绿灯提醒消息发送失败。");
            Message message = new Message();
            message.setContent(msgContent.toString());
            message.setTitle("红绿灯提醒消息发送失败");
            message.setMsgCatagory(MsgCatagoryEnum.LOSS.getCode());
            message.setType(MessageType.SITE);
            message.setSenderId("SYSTEM");
            message.setSendTime(new Date());
            uamMessageService.sendMessageByUser(message,uamUserOrgService.getUserByUsername("admin").getId());
        }
	}
	
}
