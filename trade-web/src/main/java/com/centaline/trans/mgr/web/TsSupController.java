/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.mgr.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.entity.TsSup;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mgr.service.TsSupService;
import com.centaline.trans.mgr.vo.TsSupVo;


@Controller
@RequestMapping(value="/setting")
public class TsSupController {
	
	Logger logger = LoggerFactory.getLogger(TsSupController.class);
	
	@Autowired
	private TsSupService tsSupService;
	
	@Autowired
	private TsFinOrgService tsFinOrgService;
	
	/**
	 * 转向供应商管理界面
	 * @return
	 */
    @RequestMapping(value="supplierSet")
    public String supplierSet(){
        
    	return "manage/supplierSetting";
    }
    
    /**
     * 保存供应商信息
     * @param tsSupVo
     * @return
     */
    @RequestMapping(value="saveTsSup")
    @ResponseBody
    public AjaxResponse<String> saveTsSup(TsSupVo tsSupVo){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsSupService.saveTsSup(tsSupVo);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    		e.printStackTrace();
    	}
    	return response;
    }
    
    /**
     * 查询供应商信息
     * @param pkid
     * @return
     */
    @RequestMapping(value="getTsSupInfo")
    @ResponseBody
    public AjaxResponse<TsSupVo> getTsSupInfo(Long pkid){
        
    	AjaxResponse<TsSupVo> response = new AjaxResponse<TsSupVo>();
    	try{
    		TsSupVo tsSupVo = tsSupService.findTsSupById(pkid);
    		response.setContent(tsSupVo);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询供应商信息失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    
    /**
     * 删除供应商
     * @param pkid
     * @return
     */
    @RequestMapping(value="delTsSup")
    @ResponseBody
    public AjaxResponse<String> delTsSup(Long pkid){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsSupService.deleteTsSup(pkid);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("删除供应商信息失败！");
    		logger.error("删除供应商失败！"+e.getCause());
    	}
    	return response;
    }
    
    /**
     * 转向金融机构管理页面
     * @return
     */
    @RequestMapping(value="finOrgSet")
    public String finOrgSet(){
        
    	return "manage/finOrgSet";
    }
    
    /**
     * 保存金融机构
     * @param tsFinOrg
     * @return
     */
    @RequestMapping(value="saveFinOrg")
    @ResponseBody
    public AjaxResponse<String> saveFinOrg(TsFinOrg tsFinOrg){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsFinOrgService.saveTsFinOrg(tsFinOrg);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    		e.printStackTrace();
    	}
    	return response;
    }
    
    /**
     * 查询金融机构信息
     * @param pkid
     * @return
     */
    @RequestMapping(value="getFinOrgInfo")
    @ResponseBody
    public AjaxResponse<TsFinOrg> getFinOrgInfo(Long pkid){
        
    	AjaxResponse<TsFinOrg> response = new AjaxResponse<TsFinOrg>();
    	try{
    		TsFinOrg tsFinOrg = tsFinOrgService.findById(pkid);
    		response.setContent(tsFinOrg);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询金融机构信息失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    
    /**
     * 删除金融机构
     * @param pkid
     * @return
     */
    @RequestMapping(value="delFinOrg")
    @ResponseBody
    public AjaxResponse<String> delFinOrg(Long pkid){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsFinOrgService.deleteTsFinOrg(pkid);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("删除金融机构失败！"+e.getMessage());
    	}
    	return response;
    }
}



