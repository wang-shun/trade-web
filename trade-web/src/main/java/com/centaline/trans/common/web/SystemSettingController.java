package com.centaline.trans.common.web;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.common.entity.LampRule;
import com.centaline.trans.common.entity.ToReminderList;
import com.centaline.trans.common.entity.TsTaskPlanSet;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.common.service.LampRuleService;
import com.centaline.trans.common.service.ToReminderListService;
import com.centaline.trans.common.service.TsTaskPlanSetService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;

@Controller
@RequestMapping(value="/setting")
public class SystemSettingController {
	@Autowired
	ToReminderListService toReminderListService;
	@Autowired
	TsTeamPropertyService tsTeamPropertyService;
	@Autowired
	TsTaskPlanSetService tsTaskPlanSetService;
	@Autowired
	LampRuleService lampRuleService;
	/**
	 * 提醒项 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="reminderListSet")
	public String reminderListSet(Model model, ServletRequest request){
		return "system/reminder_list";
	}
	/**
	 * 组别配置 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="teamProperty")
	public String teamProperty(Model model, ServletRequest request){
		return "system/teamProperty_list";
	}
	/**
	 * 
	 * 更新提醒项
	 * 
	 * @param toReminderList
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/saveReminderItem")
	public String saveReminderItem(ToReminderList toReminderList, HttpServletRequest request) {
		
		if(toReminderList.getPkid()==null){
			toReminderListService.insertSelective(toReminderList);
		}else{
			toReminderListService.updateByPrimaryKeySelective(toReminderList);
		}
		
		return "system/reminder_list";
	}
	/**
	 * 
	 * 删除提醒项
	 * 
	 * @param toReminderList
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/delReminderItem")
	@ResponseBody
	public AjaxResponse<?> delReminderItem(ToReminderList toReminderList, HttpServletRequest request) {
		if(toReminderList.getPkid()!=null){
			int i = toReminderListService.deleteByPrimaryKey(toReminderList.getPkid());
			if(i!=1)return AjaxResponse.fail("删除失败");
		}
		return AjaxResponse.success("删除成功");
	}
	/**
	 * 
	 * 更新组别配置
	 * 
	 * @param tsTeamProperty
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/saveTeamPropertyItem")
	public String saveTeamPropertyItem(TsTeamProperty tsTeamProperty, HttpServletRequest request) {
		
		if(tsTeamProperty.getPkid()==null){
			TsTeamProperty haveItemProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(tsTeamProperty.getYuTeamCode());
			if(haveItemProperty!=null && haveItemProperty.getPkid()!=null){
				request.setAttribute("saveMsg", "已存在组别信息，无法配置多条！");
			}else{
				tsTeamPropertyService.insertSelective(tsTeamProperty);
			}
		}else{
			tsTeamPropertyService.updateByPrimaryKeySelective(tsTeamProperty);
		}
		
		return "system/teamProperty_list";
	}
	/**
	 * 
	 * 删除组别配置
	 * 
	 * @param tsTeamProperty
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/delTeamPropertyItem")
	@ResponseBody
	public AjaxResponse<?> delTeamPropertyItem(TsTeamProperty tsTeamProperty, HttpServletRequest request) {
		if(tsTeamProperty.getPkid()!=null){
			int i = tsTeamPropertyService.deleteByPrimaryKey(tsTeamProperty.getPkid());
			if(i!=1)return AjaxResponse.fail("删除失败");
		}
		return AjaxResponse.success("删除成功");
	}
	
	/****
	 *   任务项天数配置
	 *   
	 *   @param model
	 *   @param request
	 * 
	 */
	@RequestMapping(value="taskPlanSet")
	public String reminderListSet(Model model, HttpServletRequest request){
		return "system/taskPlanSet";
	}
	
	/****
	 *   保存任务项天数配置
	 *   
	 *   @param model
	 *   @param request
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="saveTaskPlanSet")
	@ResponseBody
	public AjaxResponse<String> saveTaskPlanSet(Model model, HttpServletRequest request,TsTaskPlanSet taskPlanSet){
		if(taskPlanSet.getPkid() == null) {
			// 新增
			TsTaskPlanSet researchParam  = new TsTaskPlanSet();
			researchParam.setPartCode(taskPlanSet.getPartCode());
			int count = tsTaskPlanSetService.getTsTaskPlanSetCountByProperty(researchParam);
			
			if(count > 0) {
				return AjaxResponse.fail("该任务项已经存在配置,不能再次新增!");
			} else {
				taskPlanSet.setCreateTime(new Date());
				int addCount = tsTaskPlanSetService.addTsTaskPlanSet(taskPlanSet);
				if(addCount == 0) {
					return AjaxResponse.fail("新增失败，请刷新后重试!");
				}
				return AjaxResponse.success("新增配置成功");
			}
		} else {
			// 修改
			taskPlanSet.setCreateTime(new Date());
			int updateCount = tsTaskPlanSetService.updateByPrimaryKeySelective(taskPlanSet);
			if(updateCount == 0) {
				return AjaxResponse.fail("修改失败，请刷新后重试!");
			}
			return AjaxResponse.success("修改配置成功");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="saveLampRule")
	@ResponseBody
	public AjaxResponse<String> saveLampRule(Model model, HttpServletRequest request,LampRule lampRule){
		LampRule property = new LampRule();
		property.setColor(lampRule.getColor());
		lampRuleService.deleteLampRuleByProperty(property);
		int count = lampRuleService.addLampRule(lampRule);
		if(count == 0) {
			return AjaxResponse.fail("新增失败，请刷新后重试!");
		}
		return AjaxResponse.success("新增配置成功");
	}
	
	/**
	 * 
	 * 删除任务项配置
	 * 
	 * @param delTaskPlanSet
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/delTaskPlanSet")
	@ResponseBody
	public AjaxResponse<?> delTaskPlanSet(Model model, HttpServletRequest request,Long pkid) {
		int count = tsTaskPlanSetService.deleteByPrimaryKey(pkid);
		if(count == 0) {
			return AjaxResponse.success("删除失败");
		} else {
			return AjaxResponse.success("删除成功");
		}
	}
	
}
