/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.mgr.web;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.task.service.TsTeamScopeArService;
import com.centaline.trans.task.service.TsTeamScopeGrpService;
import com.centaline.trans.team.entity.TeamScopeAllVO;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.entity.TsTeamScopeAr;
import com.centaline.trans.team.entity.TsTeamScopeGrp;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamScopeService;
import com.centaline.trans.team.vo.CaseInfoVO;
import com.centaline.trans.team.vo.TsTeamScopeArVO;
import com.centaline.trans.team.vo.TsTeamScopePropertyVO;
import com.centaline.trans.team.vo.TsTeamScopeVO;


@Controller
@RequestMapping(value="/setting")
public class TsTeamScopeController {
	
	Logger logger = LoggerFactory.getLogger(TsTeamScopeController.class);
	
	@Autowired
	private TsTeamScopeService tsTeamScopeService;
	
	@Autowired
	private TsTeamPropertyService tsTeamPropertyService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
    @Resource(name = "quickGridService")
    private QuickGridService      quickGridService;
    
    @Autowired
    private UamSessionService     uamSessionService;
    
    @Autowired
    private QuerysParseService    querysParseService;
    
    @Autowired
    private TsTeamScopeArService    tsTeamScopeArService;
    
    @Autowired
    private TsTeamScopeGrpService    tsTeamScopeGrpService;
	
    @RequestMapping(value="unlocatedAgentTeamScope")
    public String unlocatedAgentTeamScope(){
        
    	return "manage/unlocatedAgentTeamScope";
    }
    
	
	/**
	 * 转向组别管理
	 * @return
	 */
    @RequestMapping(value="teamScope")
    public String teamScope(){
        
    	return "manage/teamScope";
    }
    
    /**
	 * 片区分配前台组和后台组
	 * 
	 * @return
	 */
    @RequestMapping(value="teamScopeAR")
    public String teamScopeAR(){
        
    	return "manage/teamScopeAR";
    }
    
    /**
     * 业务组别编码列表
     * @return
     */
    @RequestMapping(value="getAgentTeamCodeList")
    @ResponseBody
    public List<Org> getAgentTeamCodeList(){
    	List<Org> list = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.BUSIGRP);

    	return list;
    }
    
    /**
     * 业务片区编码列表
     * @return
     */
    @RequestMapping(value="getAgentArCodeList")
    @ResponseBody
    public List<Org> getAgentArCodeList(){
    	List<Org> list = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.BUSIAR);

    	return list;
    }
    /**
     * 誉萃组别编码列表
     * @return
     */
    @RequestMapping(value="getTeamCodeList")
    @ResponseBody
    public List<Org> getTeamCodeList(){
    	List<Org> list = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.YU_TEAM);

    	return list;
    }
    /**
     * 保存誉萃组别
     * @param tsTeamScope
     * @return
     */
    @RequestMapping(value="saveTeamScope")
    @ResponseBody
    public AjaxResponse<String> saveTeamScope(TsTeamScope tsTeamScope){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsTeamScopeService.saveTsTeamScope(tsTeamScope);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    		e.printStackTrace();
    	}
    	return response;
    }
    
   @RequestMapping(value="saveTeamScopeVO")
    @ResponseBody
    public AjaxResponse<String> saveTeamScope(TsTeamScopeVO tsTeamScopeVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsTeamScopeService.saveTsTeamScopeVo(tsTeamScopeVO);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    		e.printStackTrace();
    	}
    	return response;
    }
   
   @RequestMapping(value="saveTsTeamScopeArVO")
   @ResponseBody
   public AjaxResponse<String> saveTsTeamScopeArVO(@RequestBody TsTeamScopeArVO tsTeamScopeArVO){
	   	AjaxResponse<String> response = new AjaxResponse<String>();
	   	try{
	   		TsTeamScopeAr tsTeamScopeArNew = new TsTeamScopeAr();
	   		tsTeamScopeArNew.setArCode(tsTeamScopeArVO.getArCode());
	   		tsTeamScopeArService.delTsTeamScopeArByProperty(tsTeamScopeArNew);
	   		
	   		String arCode = tsTeamScopeArVO.getArCode();
	   		String arName = tsTeamScopeArVO.getArName();
	   		List<TsTeamProperty> tsTeamPropertyList = tsTeamScopeArVO.getTsTeamPropertyList();
	   		for(TsTeamProperty tsTeamProperty : tsTeamPropertyList) {
	   			
	   			TsTeamScopeGrp tsTeamScopeGrp = new TsTeamScopeGrp();
	   			tsTeamScopeGrp.setArCode(arCode);
	   			tsTeamScopeGrp.setYuTeamCode(tsTeamProperty.getYuTeamCode());
	   			List<TsTeamScopeGrp> tsTeamScopeGrpList = tsTeamScopeGrpService.getTsTeamScopeGrpListByProperty(tsTeamScopeGrp);
	   			// 如果已经存在组别配置，则此条记录跳过,事物不会滚
	   			if(tsTeamScopeGrpList != null && tsTeamScopeGrpList.size()>0) {
	   				response.setSuccess(false);
	   				response.setMessage(arName+"已经存在组别配置");
	   				
	   				continue;
	   			}
	   			
	   			TsTeamScopeAr tsTeamScopeAr = new TsTeamScopeAr();
	   			tsTeamScopeAr.setArCode(arCode);
	   			tsTeamScopeAr.setArName(arName);
	   			tsTeamScopeAr.setYuTeamCode(tsTeamProperty.getYuTeamCode());
	   			tsTeamScopeAr.setYuTeamName(tsTeamProperty.getYuTeamName());
	   			tsTeamScopeAr.setIsResponseTeam(tsTeamProperty.getIsResponseTeam());
	   			tsTeamScopeAr.setUpdateTime(new Date());
	   			
	   			tsTeamScopeArService.saveTsTeamScopeAr(tsTeamScopeAr);
	   		}
	   	}catch(Exception e){
	   		response.setSuccess(false);
	   		response.setMessage(e.getMessage());
	   		logger.error("保存失败！"+e.getCause());
	   		e.printStackTrace();
	   	}
	   	return response;
   }
    
    @RequestMapping(value="saveTeamScopeVOByOrg")
    @ResponseBody
    public AjaxResponse<String> saveTeamScopeVOByOrg(@RequestBody List<TsTeamScopeVO> tsTeamScopeVOList){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		for(TsTeamScopeVO tsTeamScopeVO : tsTeamScopeVOList) {
    			tsTeamScopeService.saveTsTeamScopeVo(tsTeamScopeVO);
    		}
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    		e.printStackTrace();
    	}
    	return response;
    }

    /**
     * 查询誉萃组别信息
     * @param pkid
     * @return
     */
    @RequestMapping(value="getTeamScopeInfo")
    @ResponseBody
    public AjaxResponse<TsTeamScope> getTeamScopeInfo(Long pkid){
        
    	AjaxResponse<TsTeamScope> response = new AjaxResponse<TsTeamScope>();
    	try{
    		TsTeamScope tsTeamScope = tsTeamScopeService.findById(pkid);
    		response.setContent(tsTeamScope);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询组别信息失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    
    @RequestMapping(value="getTeamScopeListByCode")
    @ResponseBody
    public AjaxResponse<TeamScopeAllVO> getTeamScopeListByCode(String agentTeamCode){
        
    	AjaxResponse<TeamScopeAllVO> response = new AjaxResponse<TeamScopeAllVO>();
    	try{
    		TsTeamScope tsTeamScope = new TsTeamScope();
    		tsTeamScope.setYuAgentTeamCode(agentTeamCode);
    		List<TsTeamScopePropertyVO> tsTeamScopePropertyVOList = tsTeamScopeService.getTeamScopeListByProperty(tsTeamScope);
    		
    		List<TsTeamProperty> tsTeamPropertyList = tsTeamPropertyService.getTsTeamPropertyListByProperty(new TsTeamProperty());
    		
    		for(TsTeamProperty tsTeamProperty : tsTeamPropertyList) {
    			tsTeamProperty.setIsSelect("0");
    			for(TsTeamScopePropertyVO tsTeamScopePropertyVO : tsTeamScopePropertyVOList) {
    				//String isResponseTeam = tsTeamScopePropertyVO.getTsTeamProperty().getIsResponseTeam();
        			String yuTeamCode = tsTeamScopePropertyVO.getTsTeamScope().getYuTeamCode();
        			if(tsTeamProperty.getYuTeamCode().equals(yuTeamCode)) {
						// 被选中
						tsTeamProperty.setIsSelect("1");
						 break;
					}
    			}
    		}
    		TeamScopeAllVO teamScopeAllVO = new TeamScopeAllVO();
    		teamScopeAllVO.setTsTeamPropertyList(tsTeamPropertyList);
    		teamScopeAllVO.setTsTeamScopePropertyVOList(tsTeamScopePropertyVOList);
    		response.setContent(teamScopeAllVO);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询组别列表失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    
    @RequestMapping(value="getTeamScopeArListByArCode")
    @ResponseBody
    public AjaxResponse<List<TsTeamProperty>> getTeamScopeArListByArCode(String arCode){
    	
    	AjaxResponse<List<TsTeamProperty>> response = new AjaxResponse<List<TsTeamProperty>>();
    	try{
    		// 查询出所有的组别
        	List<TsTeamProperty> tsTeamPropertyList = tsTeamPropertyService.getTsTeamPropertyListByProperty(new TsTeamProperty());
        	
        	
        	// 查询出已经被选择的组别
        	TsTeamScopeAr tsTeamScopeArNew = new TsTeamScopeAr();
        	tsTeamScopeArNew.setArCode(arCode);
        	List<TsTeamScopeAr> tsTeamScopeArList = tsTeamScopeArService.getTsTeamScopeArListByProperty(tsTeamScopeArNew);
        	
        	for(TsTeamProperty tsTeamProperty : tsTeamPropertyList) {
        		String teamCode =  tsTeamProperty.getYuTeamCode();
        		for(TsTeamScopeAr tsTeamScopeAr : tsTeamScopeArList) {
        			if(teamCode.equals(tsTeamScopeAr.getYuTeamCode())) {
        				// 被选择
        				tsTeamProperty.setIsSelect("1");
        				tsTeamProperty.setIsResponseTeam(tsTeamScopeAr.getIsResponseTeam());
        				break;
        			}
        		}
        	}
    		response.setContent(tsTeamPropertyList);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询组别列表失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    
    @RequestMapping(value="recoveryTeamScope")
    @ResponseBody
    public AjaxResponse<List<CaseInfoVO>> recoveryTeamScope(){
        
    	AjaxResponse<List<CaseInfoVO>> response = new AjaxResponse<List<CaseInfoVO>>();
    	try{
    		List<CaseInfoVO> caseInfoList = tsTeamPropertyService.recoveryTeamScope();
    		//logger.info("caseInfoList="+caseInfoList);
    		response.setContent(caseInfoList);
    		response.setMessage("案件恢复成功!");
    	}catch(Exception e){
    		e.printStackTrace();
    		response.setSuccess(false);
    		response.setMessage("恢复案件列表失败！");
    		logger.error("恢复案件列表失败！"+e.getCause());
    	}
    	return response;
    }
    
    @RequestMapping(value="getYuCuiTeamList")
    @ResponseBody
    public AjaxResponse<List<TsTeamProperty>> getYuCuiTeamList(){
        
    	AjaxResponse<List<TsTeamProperty>> response = new AjaxResponse<List<TsTeamProperty>>();
    	try{
    		List<TsTeamProperty> tsTeamPropertyList = tsTeamPropertyService.getTsTeamPropertyListByProperty(new TsTeamProperty());
    		response.setContent(tsTeamPropertyList);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("查询前台组或者后台组失败！");
    		logger.error("查询失败！"+e.getCause());
    	}
    	return response;
    }
    
    
    
    /**
     * 删除誉萃组别
     * @param pkid
     * @return
     */
    @RequestMapping(value="delTeamScope")
    @ResponseBody
    public AjaxResponse<String> delTeamScope(Long pkid){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		tsTeamScopeService.deleteTsTeamScope(pkid);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("删除组别信息失败！");
    		logger.error("删除组别失败！"+e.getCause());
    	}
    	return response;
    }
    
    @RequestMapping(value="delTeamScopeAr")
    @ResponseBody
    public AjaxResponse<String> delTeamScopeAr(Long pkid){
        
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		TsTeamScopeAr tsTeamScopeAr = new TsTeamScopeAr();
    		tsTeamScopeAr.setPkid(pkid);
    		tsTeamScopeArService.delTsTeamScopeArByProperty(tsTeamScopeAr);
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage("删除组别信息失败！");
    		logger.error("删除组别失败！"+e.getCause());
    	}
    	return response;
    }
}



