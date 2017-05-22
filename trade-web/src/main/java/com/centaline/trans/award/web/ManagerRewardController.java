package com.centaline.trans.award.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.award.entity.TsManagementAwardBaseConfig;
import com.centaline.trans.award.service.TsManagementAwardBaseConfigService;

@Controller
@RequestMapping(value = "/award")
public class ManagerRewardController {
	
	@Autowired
	private TsManagementAwardBaseConfigService tsManagementAwardBaseConfigService;


	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@RequestMapping(value = "/managerRewardList")
	public String ManagerRewardList(Model model){
		
		Calendar cd = Calendar.getInstance();
		
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM");
		String belongMonth = s.format(cd.getTime());  //当前年月
		model.addAttribute("belongMonth", belongMonth);
		return "award/managerReward";
	}
	
	@RequestMapping(value = "/managerPiecework")
	public String managerPiecework(Model model){
		return "award/managerPiecework";
	}
	
	@RequestMapping(value = "/saveManagerReward")
	@ResponseBody
	public AjaxResponse<T> saveManagerReward(TsManagementAwardBaseConfig tsManagementAwardBaseConfig){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			
			tsManagementAwardBaseConfigService.insertSelective(tsManagementAwardBaseConfig);
			response.setCode("400");
			response.setMessage("保存成功！");
			response.setSuccess(true);
		}catch(Exception e){
			logger.error("新增管理人员信息保存失败:", e);
			response.setCode("500");
			response.setMessage("保存失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(value = "/delManagerReward")
	@ResponseBody
	public AjaxResponse<T> delManagerReward(Long pkid){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			
			tsManagementAwardBaseConfigService.deleteByPrimaryKey(pkid);
			response.setCode("400");
			response.setMessage("删除成功！");
			response.setSuccess(true);
		}catch(Exception e){
			logger.error("删除失败:", e);
			response.setCode("500");
			response.setMessage("删除失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(value = "/modifyManagerReward")
	@ResponseBody
	public AjaxResponse<T> modifyManagerReward(TsManagementAwardBaseConfig tsManagementAwardBaseConfig){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			tsManagementAwardBaseConfigService.updateByPrimaryKeySelective(tsManagementAwardBaseConfig);
			response.setCode("400");
			response.setMessage("更新成功！");
			response.setSuccess(true);
		}catch(Exception e){
			logger.error("更新失败:", e);
			response.setCode("500");
			response.setMessage("更新失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	
}
