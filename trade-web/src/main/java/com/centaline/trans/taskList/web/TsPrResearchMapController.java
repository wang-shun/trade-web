/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.taskList.web;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.service.TsPrResearchMapService;


@Controller
@RequestMapping(value="/setting")
public class TsPrResearchMapController {
	
	Logger logger = LoggerFactory.getLogger(TsPrResearchMapController.class);
	
	@Autowired
	private TsPrResearchMapService tsPrResearchMapService;
	
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
    @RequestMapping(value="prRegion")
    public String prRegion(){
        
    	return "task/prResearchMapSet";
    }
    
    @RequestMapping(value="saveTsPrResearchMap")
    @ResponseBody
    public AjaxResponse<String> saveTsPrResearchMap(TsPrResearchMap tsPrResearchMap){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsPrResearchMapService.saveTsPrResearchMap(tsPrResearchMap);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    	}
    	return response;
    }
    

    @RequestMapping(value="getTsPrResearchMap")
    @ResponseBody
    public AjaxResponse<TsPrResearchMap> getTsPrResearchMap(Long pkid){
        
    	AjaxResponse<TsPrResearchMap> response = new AjaxResponse<TsPrResearchMap>();
    	try{
    		TsPrResearchMap tsPrResearchMap = tsPrResearchMapService.findById(pkid);
    		response.setContent(tsPrResearchMap);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    

    @RequestMapping(value="delTsPrResearchMap")
    @ResponseBody
    public AjaxResponse<String> delTsPrResearchMap(Long pkid){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsPrResearchMapService.deleteTsPrResearchMap(pkid);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("删除失败！");
    		logger.error("删除失败！"+e.getCause());
    	}
    	return response;
    }
    
    @RequestMapping(value="queryDistList")
    @ResponseBody
    public List<Dict> queryDistList(){
        
    	Dict dict = uamBasedataService.findDictByType(Consts.YU_SH_DISTRICT);
    	if(dict != null){
    		return dict.getChildren();
    	}
    	return null;
    }
    @RequestMapping(value="getYuDistList")
    @ResponseBody
    public List<Org> getYuDistList(){
    	List<Org> list = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.YU_DISTRICT);

    	return list;
    }
}



