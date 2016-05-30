/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.mgr.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.mgr.entity.TsAttendance;
import com.centaline.trans.mgr.service.TsAttendanceService;


@Controller
@RequestMapping(value="/attendCheck")
public class TsAttendanceController {
	
	Logger logger = LoggerFactory.getLogger(TsAttendanceController.class);
	
	@Autowired
	private TsAttendanceService tsAttendanceService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	/**
	 * 移动端考勤界面
	 * @param model
	 * @return
	 */
    @RequestMapping("/mobile/attendManage")
    public String attendanceManage(Model model){
        SessionUser user = uamSessionService.getSessionUser();
        model.addAttribute("userId", user.getId()); 
    	return "mobile/attendcheck/attendanceManage";
    }
    
    /**
     * 保存考勤信息
     * @param tsAttendance
     * @return
     */
    @RequestMapping(value="saveAttendance")
    @ResponseBody
    public AjaxResponse<String> saveAttendance(TsAttendance tsAttendance){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsAttendanceService.saveTsAttendance(tsAttendance);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("操作失败！");
    		logger.info("操作失败！"+e.getMessage());
    		e.printStackTrace();
    	}
    	return response;
    }
    
    /**
     * 转向考勤列表界面
     * @param model
     * @return
     */
    @RequestMapping("")
    public String attendanceList(Model model){
        SessionUser user = uamSessionService.getSessionUser();
        model.addAttribute("orgId", user.getServiceDepId()); 
    	return "attendcheck/attendanceList";
    }
    /**
     * 转向考勤列表界面
     * @param model
     * @return
     */
    @RequestMapping("/mobile/attendList")
    public String attendanceMobileList(Model model){
        SessionUser user = uamSessionService.getSessionUser();
        model.addAttribute("userId", user.getId()); 
    	return "mobile/attendcheck/attendanceList";
    }
}



