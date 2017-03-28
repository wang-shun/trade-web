package com.centaline.trans.report.web;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.centaline.trans.report.enums.DateCountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.report.service.OrgReportFormService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.workspace.entity.CacheGridParam;
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

	@Autowired
	private OrgReportFormService quickGridService;
	/**
	 * 总部(区域查询)
	 * @param model
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value="headquarter/getCaseCount")
	public AjaxResponse getCaseCount(Model model, ServletRequest request,String orgId){
		orgId = "".equals(orgId) ? null : orgId;
		JQGridParam gp = new CacheGridParam();
		gp.put("districtSearchId", orgId);
		gp.setPagination(false);
		List<ToCaseInfoCountVo> voList  = quickGridService.findPageForCaseReportFormCount(gp);

		AjaxResponse<ToCaseInfoCountVo> result = new AjaxResponse<>();
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
		orgId = "".equals(orgId) ? null : orgId;
		JQGridParam gp = new CacheGridParam();
		gp.put("orgSearchId", orgId);
		gp.setPagination(false);
		List<ToCaseInfoCountVo> voList  = quickGridService.findPageForCaseReportFormCount(gp);

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
		List<String> orgIdList = new ArrayList<>();
		List<ToOrgVo> toOrgVoList = new ArrayList<>();
		if(startDate == "")startDate = null;
		if(endDate== "")endDate = null;
		JQGridParam gp = new CacheGridParam();
		gp.setQueryId("queryDataCountForDistrict");
		gp.put("depType", DepTypeEnum.TYCQY.getCode());
		gp.put("startDate", startDate);
		gp.put("endDate", endDate);
		gp.setPagination(false);
		List<ToCaseInfoCountVo> voList= quickGridService.findDataCountForDistrict(gp);
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
		List<String> orgIdList = new ArrayList<>();
		List<ToOrgVo> toOrgVoList = new ArrayList<>();
		if(startDate == "")startDate = null;
		if(endDate== "")endDate = null;
		JQGridParam gp = new CacheGridParam();

		gp.put("depType", DepTypeEnum.TYCTEAM.getCode());
		gp.put("startDate", startDate);
		gp.put("endDate", endDate);
		gp.setPagination(false);
		List<ToCaseInfoCountVo> voList= quickGridService.findPageForReportOrgForm(gp);
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
		List<User> teamUserList = uamUserOrgService.getUserByOrgIdAndJobCode(team.getId(), TransJobs.TJYGW.getCode());
		JQGridParam gp = new CacheGridParam();	//查询组织列表改为快速查询

		gp.setQueryId("queryCountDataForCountForm");
		gp.put("paraOrgId", team.getId().trim());
		gp.put("startDate", startDate);
		gp.put("endDate", endDate);
		gp.setPagination(false);
		Page<Map<String, Object>> pages = quickGridService.queryRedcountListForPeople(gp);
		List<Map<String,Object>> list = pages.getContent();

		for (User teamUser : teamUserList) {
			ToCaseInfoCountVo toCaseInfoVo = new ToCaseInfoCountVo();
			for(Map<String,Object> map : list){
				if(teamUser.getId().equals(map.get("PROCESSOR"))){
					switch (DateCountType.from((String)map.get("TYPE"))){
						case JDS_TYPE : toCaseInfoVo.setCountJDS((int)map.get("DATA_COUNT")); break;
						case QYS_TYPE : toCaseInfoVo.setCountQYS((int) map.get("DATA_COUNT")); break;
						case GHS_TYPE : toCaseInfoVo.setCountGHS((int) map.get("DATA_COUNT")); break;
						case JAS_TYPE : toCaseInfoVo.setCountJAS((int)map.get("DATA_COUNT")); break;
					}
					toCaseInfoVo.setOrgName(teamUser.getRealName());
					voList.add(toCaseInfoVo);
				}
			}
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
		JQGridParam gp = new CacheGridParam();	//查询组织列表改为快速查询

		gp.setQueryId("queryCountForAllRedLock");
		gp.put("departType", DepTypeEnum.TYCQY.getCode());
		gp.put("strNum", strNum);
		gp.put("endNum", endNum);
		gp.setPagination(false);
		Page<Map<String, Object>> pages = quickGridService.queryCountForAllRedLock(gp);
		List<Map<String,Object>> list = pages.getContent();
		for(Map<String,Object> map : list){
			if(map!=null){
				if(map.get("org_name")!=null&&map.get("redCount")!=null){
					ToOrgVo orgVo = new ToOrgVo();
					orgVo.setOrgName((String) map.get("org_name"));
					try{
						orgVo.setRedNum((int)map.get("redCount"));
					}catch (Exception e){
						e.printStackTrace();
					}
					toOrgVoList.add(orgVo);
				}
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

		JQGridParam gp = new CacheGridParam();	//查询组织列表改为快速查询
		gp.setQueryId("queryOrgIdListFortRedcountList");
		gp.put("depType", DepTypeEnum.TYCTEAM.getCode());
		gp.put("strNum", strNum);
		gp.put("endNum", endNum);
		gp.setPagination(false);
		Page<Map<String, Object>> pages = quickGridService.findPageForReportRedCountList(gp);
		List<Map<String,Object>> list = pages.getContent();
		for(Map<String,Object> map : list){
			if(map!=null){
				if(map.get("orgId")!=null&&map.get("orgName")!=null){
					int redNum = (int)map.get("redCount");
					ToOrgVo orgVo = new ToOrgVo();
					orgVo.setOrgName((String) map.get("orgName"));
					orgVo.setRedNum(redNum);
					toOrgVoList.add(orgVo);
				}
			}
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
		List<User> teamUserList = uamUserOrgService.getUserByOrgIdAndJobCode(team.getId(), TransJobs.TJYGW.getCode());

		JQGridParam gp = new CacheGridParam();	//查询组织列表改为快速查询
		gp.setQueryId("queryRedcountListForPeople");
		List<String> teamUserIds = new LinkedList<String>();
		for (int i = 0; i < teamUserList.size(); i++) {
			teamUserIds.add(teamUserList.get(i).getId());
		}
		gp.put("teamUserIds", teamUserIds);
		gp.put("strNum", strNum);
		gp.put("endNum", endNum);
		gp.setPagination(false);
		Page<Map<String, Object>> pages = quickGridService.queryRedcountListForPeople(gp);
		List<Map<String,Object>> list = pages.getContent();
		for(Map<String,Object> map : list){
			if(map!=null){
				if(map.get("processorId")!=null&&map.get("redLightCount")!=null){
					int redNum = (int)map.get("redLightCount");
					for (User teamUser : teamUserList) {
						ToOrgVo orgVo = new ToOrgVo();
						if(teamUser.getId().equals((String)map.get("processorId"))){
							orgVo.setOrgName(teamUser.getRealName());
							orgVo.setRedNum(redNum);
							toOrgVoList.add(orgVo);
							break;
						}
					}
				}
			}
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
		
		toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS() + toCloseCount.getCountJAS());
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