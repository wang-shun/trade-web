package com.centaline.trans.report.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONArray;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.common.entity.Pic;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.service.ToHouseTransferService;
/**
 * 报表
 * @author aisliahail
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value="/report")
public class ReportMtnCortroller {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	
	@Autowired(required=true)
	private ToSpvService toSpvService;
	
	@Autowired(required=true)
	private ToCaseInfoService toCaseInfoService;
	
	@Autowired(required=true)
	private ToHouseTransferService toHouseTransferService;
	
	@Autowired(required=true)
	private ToCloseService toCloseService;
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
	/**
	 * 总部(区域查询)
	 * @param model
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value="headquarter/getCaseCount")
	public AjaxResponse getCaseCount(Model model, ServletRequest request,String orgId){
		//List<ToCaseInfoCountVo> countList = toCaseService.getCaseCount();
		List<ToCaseInfoCountVo> toCaseInfoCountList = null;
		List<ToCaseInfoCountVo> toSignCountList = null;
		List<ToCaseInfoCountVo> toHouseTransferCountList = null;
		List<ToCaseInfoCountVo> toCloseCountList = null;
		if(orgId == ""){
			orgId = null;
			toCaseInfoCountList  =  toCaseInfoService.countToCaseInfoListByOrgId(orgId);
			//,签约
			toSignCountList = toSpvService.countToSignListByOrgId(orgId);
			//,过户
			 toHouseTransferCountList = toHouseTransferService.countToHouseTransferListByOrgId(orgId);
			//,结案
			toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);
		}
		List<Org> orgParentList = null;
		if(StringUtils.isNotBlank(orgId)) {
			orgParentList = uamUserOrgService.getOrgByParentId(orgId);
		}
		List<String> orgList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(orgParentList)){
			for (Org org : orgParentList) {
				 orgList.add(org.getId());
			}
			//接单数
			//String orgId = null;
			toCaseInfoCountList  =  toCaseInfoService.countToCaseInfoListByOrgList(orgList);
			//,签约
			toSignCountList = toSpvService.countToSignListByOrgList(orgList);
			//,过户
			 toHouseTransferCountList = toHouseTransferService.countToHouseTransferListByOrgList(orgList);
			//,结案
			toCloseCountList = toCloseService.countToCloseListByOrgList(orgList);
			
		}
		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
		List<ToCaseInfoCountVo> voList = new ArrayList<>();
		for (ToCaseInfoCountVo toCaseInfoCount : toCaseInfoCountList) {
			ToCaseInfoCountVo vo = new ToCaseInfoCountVo();
			String createTime = toCaseInfoCount.getCreateTime();
			int countJDS = toCaseInfoCount.getCountJDS();
			vo.setCountJDS(countJDS);
			vo.setCreateTime(createTime);
			//签约数
			for (ToCaseInfoCountVo toSignCount : toSignCountList) {
				if(createTime.equals(toSignCount.getCreateTime()) && toSignCount.getCountQYS()!=null){
					int toSignQYS = toSignCount.getCountQYS();
					vo.setCountQYS(toSignQYS);
				}
			}
			//过户数
			for (ToCaseInfoCountVo toHouseTransferCount : toHouseTransferCountList) {
				if(createTime.equals(toHouseTransferCount.getCreateTime())&& toHouseTransferCount.getCountGHS()!=null){
					int toHouseTransferGHS = toHouseTransferCount.getCountGHS();
					vo.setCountGHS(toHouseTransferGHS);
				}
			}
			//结案数
			for (ToCaseInfoCountVo toCloseCount : toCloseCountList) {
				if(createTime.equals(toCloseCount.getCreateTime())&& toCloseCount.getCountJAS()!=null){
					int toCloseJAS = toCloseCount.getCountJAS();
					vo.setCountJAS(toCloseJAS);
				}
			}
			voList.add(vo);	
		}
    	model.addAttribute("voList", voList);
   
    	result.setSuccess(true);
		return result;
	}
	
	/**
	 * 区域(组别查询)
	 * @param model
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value="district/getCaseCount")
	public AjaxResponse dGetCaseCount(Model model, ServletRequest request,String orgId){
		List<ToCaseInfoCountVo> toCaseInfoCountList = new ArrayList<ToCaseInfoCountVo>();;
		List<ToCaseInfoCountVo> toSignCountList = new ArrayList<ToCaseInfoCountVo>();
		List<ToCaseInfoCountVo> toHouseTransferCountList = new ArrayList<ToCaseInfoCountVo>();;
		List<ToCaseInfoCountVo> toCloseCountList = new ArrayList<ToCaseInfoCountVo>();;
		List<String> orgList = new ArrayList<>();
		if(orgId == ""){
			orgId = null;
			toCaseInfoCountList  =  toCaseInfoService.countToCaseInfoListByOrgId(orgId);
			//,签约
			toSignCountList = toSpvService.countToSignListByOrgId(orgId);
			//,过户
			toHouseTransferCountList = toHouseTransferService.countToHouseTransferListByOrgId(orgId);
			//,结案
			toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);
		}else{
			orgList.add(orgId);
			toCaseInfoCountList  =  toCaseInfoService.countToCaseInfoListByOrgList(orgList);
			//,签约
			toSignCountList = toSpvService.countToSignListByOrgList(orgList);
			//,过户
			toHouseTransferCountList = toHouseTransferService.countToHouseTransferListByOrgList(orgList);
			//,结案
			toCloseCountList = toCloseService.countToCloseListByOrgList(orgList);
			
			
		}
		List<ToCaseInfoCountVo> voList = new ArrayList<>();
		for (ToCaseInfoCountVo toCaseInfoCount : toCaseInfoCountList) {
			ToCaseInfoCountVo vo = new ToCaseInfoCountVo();
			String createTime = toCaseInfoCount.getCreateTime();
			int countJDS = toCaseInfoCount.getCountJDS();
			vo.setCountJDS(countJDS);
			vo.setCreateTime(createTime);
			//签约数
			for (ToCaseInfoCountVo toSignCount : toSignCountList) {
				if(createTime.equals(toSignCount.getCreateTime())){
					int toSignQYS = toSignCount.getCountQYS();
					vo.setCountQYS(toSignQYS);
				}
			}
			//过户数
			for (ToCaseInfoCountVo toHouseTransferCount : toHouseTransferCountList) {
				if(createTime.equals(toHouseTransferCount.getCreateTime())){
					int toHouseTransferGHS = toHouseTransferCount.getCountGHS();
					vo.setCountGHS(toHouseTransferGHS);
				}
			}
			//结案数
			for (ToCaseInfoCountVo toCloseCount : toCloseCountList) {
				if(createTime.equals(toCloseCount.getCreateTime())){
					if(toCloseCount.getCountJAS()!= null) {
						int toCloseJAS = toCloseCount.getCountJAS();
						vo.setCountJAS(toCloseJAS);
					}
				}
			}
			voList.add(vo);	
		}
		
		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
		model.addAttribute("voList", voList);
		
		result.setSuccess(true);
		return result;
	}
	/**
	 * 组别(个人查询)
	 * @param model
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value="team/getCaseCount")
	public AjaxResponse tGetCaseCount(Model model, ServletRequest request,String id){
		List<ToCaseInfoCountVo> toCaseInfoCountList = null;
		List<ToCaseInfoCountVo> toSignCountList = null;
		List<ToCaseInfoCountVo> toHouseTransferCountList = null;
		List<ToCaseInfoCountVo> toCloseCountList = null;
		List<String> orgList = new ArrayList<>();
		List<String> IdList = new ArrayList<>();
		if(id == "" || id == null){
			SessionUser user = uamSessionService.getSessionUser();
			Org teamOrg = uamUserOrgService.getOrgById(user.getServiceDepId());
			orgList.add(teamOrg.getId());
			toCaseInfoCountList  =  toCaseInfoService.countToCaseInfoListByOrgList(orgList);
			//,签约
			toSignCountList = toSpvService.countToSignListByOrgList(orgList);
			//,过户
			toHouseTransferCountList = toHouseTransferService.countToHouseTransferListByOrgList(orgList);
			//,结案
			toCloseCountList = toCloseService.countToCloseListByOrgList(orgList);
		}else{
			IdList.add(id);
			toCaseInfoCountList  =  toCaseInfoService.countToCaseInfoListByIdList(IdList);
			//,签约
			toSignCountList = toSpvService.countToSignListByIdList(IdList);
			//,过户
			toHouseTransferCountList = toHouseTransferService.countToHouseTransferListByIdList(IdList);
			//,结案
			toCloseCountList = toCloseService.countToCloseListByIdList(IdList);
		}
		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
		List<ToCaseInfoCountVo> voList = new ArrayList<>();
		for (ToCaseInfoCountVo toCaseInfoCount : toCaseInfoCountList) {
			ToCaseInfoCountVo vo = new ToCaseInfoCountVo();
			String createTime = toCaseInfoCount.getCreateTime();
			int countJDS = toCaseInfoCount.getCountJDS();
			vo.setCountJDS(countJDS);
			vo.setCreateTime(createTime);
			//签约数
			for (ToCaseInfoCountVo toSignCount : toSignCountList) {
				if(createTime.equals(toSignCount.getCreateTime())){
					int toSignQYS = toSignCount.getCountQYS();
					vo.setCountQYS(toSignQYS);
				}
			}
			//过户数
			for (ToCaseInfoCountVo toHouseTransferCount : toHouseTransferCountList) {
				if(createTime.equals(toHouseTransferCount.getCreateTime())){
					int toHouseTransferGHS = toHouseTransferCount.getCountGHS();
					vo.setCountGHS(toHouseTransferGHS);
				}
			}
			//结案数
			for (ToCaseInfoCountVo toCloseCount : toCloseCountList) {
				if(createTime.equals(toCloseCount.getCreateTime())){
					int toCloseJAS = toCloseCount.getCountJAS();
					vo.setCountJAS(toCloseJAS);
				}
			}
			voList.add(vo);	
		}
    	model.addAttribute("voList", voList);
		
		result.setSuccess(true);
		return result;
	}
	
    /**
     * 总部(区域查询)
     * @param model
     * @param request
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value="headquarter/countData")
    @ResponseBody
    public List<ToCaseInfoCountVo> countData(Model model, ServletRequest request,String startDate,String endDate){
    	List<ToCaseInfoCountVo> voList  = new ArrayList<>();
    	List<String> orgIdList = new ArrayList<>();
    	List<ToOrgVo> toOrgVoList = new ArrayList<>();
    	if(startDate == "")startDate = null;
    	if(endDate== "")endDate = null;
    	String orgName = "";
    	List<ToOrgVo> orgParentList = toCaseService.getOrgIdAllByDep(DepTypeEnum.TYCQY.getCode());
    	for (ToOrgVo toOrgVo : orgParentList) {
    		List<Org> orgList = uamUserOrgService.getOrgByParentId(toOrgVo.getId());
    		if(CollectionUtils.isNotEmpty(orgList)){
	    		for (Org org : orgList) {
					orgIdList.add(org.getId());
				}
	    		//接单数
	        	int toCaseInfoJDS = toCaseInfoService.countToCaseInfoByOrgList(orgIdList, startDate, endDate);
	        	//签约数
	        	int toSignQYS = toSpvService.countToSignByOrgList(orgIdList, startDate, endDate);
	        	//过户数
	        	int toHouseTransferGHS = toHouseTransferService.countToHouseTransferByOrgList(orgIdList, startDate, endDate);
	        	//结案数
	        	int toCloseJAS = toCloseService.countToCloseByOrgList(orgIdList, startDate, endDate);
	        	
	        	ToCaseInfoCountVo toCaseInfoVo = new ToCaseInfoCountVo();
	        	
	        	toCaseInfoVo.setCountJDS(toCaseInfoJDS+toCloseJAS);
	        	toCaseInfoVo.setCountQYS(toSignQYS);
	        	toCaseInfoVo.setCountGHS(toHouseTransferGHS);
	        	toCaseInfoVo.setCountJAS(toCloseJAS);
	        	
	        	if(!orgName.equals(toOrgVo.getOrgName())){
	        		orgName = toOrgVo.getOrgName();
	        		toCaseInfoVo.setOrgName(toOrgVo.getOrgName());
	        	}
	        	voList.add(toCaseInfoVo);
	        }
		}
   
    	return voList;
    }
    /**
     * 区域(组别查询)
     * @param model
     * @param request
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value="district/countData")
    @ResponseBody
    public List<ToCaseInfoCountVo> dCountData(Model model, ServletRequest request,String startDate,String endDate){
    	List<ToCaseInfoCountVo> voList  = new ArrayList<>();
    	List<String> orgIdList = new ArrayList<>();
    	List<ToOrgVo> toOrgVoList = new ArrayList<>();
    	if(startDate == "")startDate = null;
    	if(endDate== "")endDate = null;
    	String orgName = "";
    	List<ToOrgVo> orgIdLists = toCaseService.getOrgIdAllByDep(DepTypeEnum.TYCTEAM.getCode());
    	  for (ToOrgVo orgVo : orgIdLists) {
    		  	orgIdList.add(orgVo.getId());
    			//接单数
	        	int toCaseInfoJDS = toCaseInfoService.countToCaseInfoByOrgList(orgIdList, startDate, endDate);
	        	//签约数 
	        	int toSignQYS = toSpvService.countToSignByOrgList(orgIdList, startDate, endDate);
	        	//过户数
	        	int toHouseTransferGHS = toHouseTransferService.countToHouseTransferByOrgList(orgIdList, startDate, endDate);
	        	//结案数
	        	int toCloseJAS = toCloseService.countToCloseByOrgList(orgIdList, startDate, endDate);
	        	
	        	ToCaseInfoCountVo toCaseInfoVo = new ToCaseInfoCountVo();
	        	
	        	toCaseInfoVo.setCountJDS(toCaseInfoJDS+toCloseJAS);
	        	toCaseInfoVo.setCountQYS(toSignQYS);
	        	toCaseInfoVo.setCountGHS(toHouseTransferGHS);
	        	toCaseInfoVo.setCountJAS(toCloseJAS);
	        	toCaseInfoVo.setOrgName(orgVo.getOrgName());
    		  
	        	voList.add(toCaseInfoVo);
	        	orgIdList.remove(0);
    	  }
    	
    	return voList;
    }
    /**
     * 组别(个人查询)
     * @param model
     * @param request
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value="team/countData")
    @ResponseBody
    public List<ToCaseInfoCountVo> tCountData(Model model, ServletRequest request,String startDate,String endDate){
    	List<ToCaseInfoCountVo> voList  = new ArrayList<>();
    	List<String> orgIdList = new ArrayList<>();
    	List<ToOrgVo> toOrgVoList = new ArrayList<>();
    	List<String> idList = new ArrayList<>();
    	if(startDate == "")startDate = null;
    	if(endDate== "")endDate = null;
    	String orgName = "";
    	SessionUser user = uamSessionService.getSessionUser();
    	Org team = this.uamUserOrgService.getOrgById(user.getServiceDepId());
		List<User> teamUserList = uamUserOrgService.getUserByOrgIdAndJobCode(team.getId(),TransJobs.TJYGW.getCode());
    	for (User teamUser : teamUserList) {
    		idList.add(teamUser.getId());
    		//接单数
    		int toCaseInfoJDS = toCaseInfoService.countToCaseInfoByIdList(idList, startDate, endDate);
    		//签约数
    		int toSignQYS = toSpvService.countToSignByIdList(idList, startDate, endDate);
    		//过户数
    		int toHouseTransferGHS = toHouseTransferService.countToHouseTransferByIdList(idList, startDate, endDate);
    		//结案数
    		int toCloseJAS = toCloseService.countToCloseByIdList(idList, startDate, endDate);
			
    		ToCaseInfoCountVo toCaseInfoVo = new ToCaseInfoCountVo();
    		
    		toCaseInfoVo.setCountJDS(toCaseInfoJDS+toCloseJAS);
    		toCaseInfoVo.setCountQYS(toSignQYS);
    		toCaseInfoVo.setCountGHS(toHouseTransferGHS);
    		toCaseInfoVo.setCountJAS(toCloseJAS);
    		toCaseInfoVo.setOrgName(teamUser.getRealName());
    		
    		voList.add(toCaseInfoVo);
    		idList.remove(0);
    	}
    	
    	return voList;
    }

    /**
     * 红灯数报表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="headquarter/getRedcountList")
    @ResponseBody
    public List<ToOrgVo> getRedcountList(Model model, ServletRequest request,String strNum,String endNum){
    	List<String> orgIdList = new ArrayList<>();
    	List<ToOrgVo> toOrgVoList = new ArrayList<>();
    	if(strNum == "")strNum = null;
    	if(endNum == "")endNum = null;
    	List<ToOrgVo> orgParentList = toCaseService.getOrgIdAllByDep(DepTypeEnum.TYCQY.getCode());
    	for (ToOrgVo toOrgVo : orgParentList) {
    		List<Org> orgList = uamUserOrgService.getOrgByParentId(toOrgVo.getId());
    		if(CollectionUtils.isNotEmpty(orgList)){
	    		for (Org org : orgList) {
					orgIdList.add(org.getId());
				}
	    		int redNum = toCaseService.getRedcountByOrgList(orgIdList,strNum,endNum);
	    		ToOrgVo orgVo = new ToOrgVo();
	    		orgVo.setOrgName(toOrgVo.getOrgName());
	    		orgVo.setRedNum(redNum);
	    		toOrgVoList.add(orgVo);
	    	}
		}
    	return toOrgVoList;
    }
    /**
     * 红灯数报表(组别)
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="district/getRedcountList")
    @ResponseBody
    public List<ToOrgVo> dGetRedcountList(Model model, ServletRequest request,String strNum,String endNum){
    	List<String> orgIdList = new ArrayList<>();
    	List<ToOrgVo> toOrgVoList = new ArrayList<>();
    	if(strNum == "")strNum = null;
    	if(endNum == "")endNum = null;
    	List<ToOrgVo> orgIdLists = toCaseService.getOrgIdAllByDep(DepTypeEnum.TYCTEAM.getCode());
    	for (ToOrgVo orgVos : orgIdLists) {
    		Org org = uamUserOrgService.getOrgById(orgVos.getId());
    		orgIdList.add(org.getId());
			int redNum = toCaseService.getRedcountByOrgList(orgIdList,strNum,endNum);
			ToOrgVo orgVo = new ToOrgVo();
			orgVo.setOrgName(org.getOrgName());
			orgVo.setRedNum(redNum);
			toOrgVoList.add(orgVo);
 			orgIdList.remove(0);
    	}
    	return toOrgVoList;
    }
    /**
     *  组别红灯数报表(个人查询)
     * @param model
     * @param request
     * @param strNum
     * @param endNum
     * @return
     */
    @RequestMapping(value="team/getRedcountList")
    @ResponseBody
    public List<ToOrgVo> tGetRedcountList(Model model, ServletRequest request,String strNum,String endNum){
    	List<String> idList = new ArrayList<>();
    	List<ToOrgVo> toOrgVoList = new ArrayList<>();
    	if(strNum == "")strNum = null;
    	if(endNum == "")endNum = null;
    	SessionUser user = uamSessionService.getSessionUser();
    	Org team = this.uamUserOrgService.getOrgById(user.getServiceDepId());
		List<User> teamUserList = uamUserOrgService.getUserByOrgIdAndJobCode(team.getId(),TransJobs.TJYGW.getCode());
    	for (User teamUser : teamUserList) {
    		idList.add(teamUser.getId());
    		int redNum = toCaseService.getRedcountByIdList(idList,strNum,endNum);
    		ToOrgVo orgVo = new ToOrgVo();
    		orgVo.setOrgName(teamUser.getRealName());
    		orgVo.setRedNum(redNum);
    		toOrgVoList.add(orgVo);
    		idList.remove(0);
    	}
    	return toOrgVoList;
    }
    /*跟据进行查询*/
	private ToCaseInfoCountVo getToCaseInfoCountByOrgId(String orgId,String startDate,String endDate) {
		//接单数
		ToCaseInfoCountVo toCaseInfoCount = toCaseInfoService.countToCaseInfoByOrgId(orgId, startDate, endDate);
		//,签约
		ToCaseInfoCountVo toSignCount = toSpvService.countToSignByOrgId(orgId, startDate, endDate);
		//,过户
		ToCaseInfoCountVo toHouseTransferCount = toHouseTransferService.countToHouseTransferByOrgId(orgId, startDate, endDate);
		//,结案
		ToCaseInfoCountVo toCloseCount = toCloseService.countToCloseByOrgId(orgId, startDate, endDate);
		
		toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS()+toCloseCount.getCountJAS());
		toCaseInfoCount.setCountQYS(toSignCount.getCountQYS());
		toCaseInfoCount.setCountGHS(toHouseTransferCount.getCountGHS());
		toCaseInfoCount.setCountJAS(toCloseCount.getCountJAS());
	
		return toCaseInfoCount;
    }
	
	/*跟进userid进行查询*/
	private ToCaseInfoCountVo getToCaseInfoCount(String userId) {// 查询统计 接单
		// 接单数
		ToCaseInfoCountVo toCaseInfoCount = toCaseInfoService.countToCaseInfoById(userId);
		// ,签约
		ToCaseInfoCountVo toSignCount = toSpvService.countToSignById(userId);
		// ,过户
		ToCaseInfoCountVo toHouseTransferCount = toHouseTransferService.countToHouseTransferById(userId);
		// ,结案
		ToCaseInfoCountVo toCloseCount = toCloseService.countToCloseById(userId);

		toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS()
				+ toCloseCount.getCountJAS());
		toCaseInfoCount.setCountQYS(toSignCount.getCountQYS());
		toCaseInfoCount.setCountGHS(toHouseTransferCount.getCountGHS());
		toCaseInfoCount.setCountJAS(toCloseCount.getCountJAS());

		return toCaseInfoCount;
	}
}