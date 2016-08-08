package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.DocPrintJob;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.FirstFollowService;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.FirstFollowVO;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamScopeService;
import com.centaline.trans.team.vo.CooperativeOrganizationVO;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/task/firstFollow")
public class FirstFollowController {

	@Autowired(required = true)
	
	private UamSessionService uamSessionService;/* 用户信息 */
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;/* 用户组织信息 */
	@Autowired
	private UamBasedataService uamBasedataService;/* 字典 */

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Autowired
	private ToCaseInfoService toCaseInfoService;/* 案件信息 */
	@Autowired
	private TsTeamScopeService tsTeamScopeService;/* 组别作业范围 */
	@Autowired
	private TsTeamPropertyService tsTeamPropertyService;/* 组别属性表 */
	@Autowired
	private FirstFollowService firstFollowService;
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("ctmCode", caseBaseVO.getToCase().getCtmCode());
    	request.setAttribute("firstFollow", firstFollowService.queryFirstFollow(caseCode));
		request.setAttribute("approveType", "0");
		request.setAttribute("operator", user != null ? user.getId():"");
		return "task/taskFirstFollow";
	}
	
	@RequestMapping(value = "saveFirstFollow")
	public String saveFirstFollow(HttpServletRequest request, FirstFollowVO firstFollowVO) {
		SessionUser user = uamSessionService.getSessionUser();

		firstFollowVO.setUserId(user.getId());
		firstFollowVO.setUserOrgId(getOrgId(user.getId()));
		firstFollowService.saveFirstFollow(firstFollowVO);
		return "task/task" + firstFollowVO.getPartCode();
	}

	@RequestMapping(value = "queryMortageService")
	@ResponseBody
	public Map<String, Object> queryMortageService(HttpServletRequest request, String value, String caseCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		/* 获得基础服务项 */
		String zbkServices = "";
		String serviceView = "";
		// 获取当前用户
		SessionUser user = uamSessionService.getSessionUser();
		String depCode = user.getServiceDepCode();
		TsTeamProperty tsTeamProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(depCode);
		Set<String>myService=new HashSet<String>();//
		if (tsTeamProperty != null && tsTeamProperty.getTeamProperty() != null) {
			Dict dict = uamBasedataService.findDictByType(tsTeamProperty.getTeamProperty());
			if (dict != null) {
				List<Dict> list = dict.getChildren();
				if (list != null && list.size() != 0) {
					for (Dict d : list) {
						if (d.getCode().equals("3000400101")) {
							if ((value.equals("1") || value.equals("3"))) {/* 贷款需要选择贷款才会显示 */
								zbkServices += d.getCode() + ",";
								serviceView += d.getName() + ",";
								myService.add(d.getCode());
							}
						} else if (d.getCode().equals("3000400201")) {/* 公积金需要选择中原代办公积金才会显示 */
							if (value.equals("2")) {
								zbkServices += d.getCode() + ",";
								serviceView += d.getName() + ",";
								myService.add(d.getCode());
								break;
							}
						} else {
							zbkServices += d.getCode() + ",";
							serviceView += d.getName() + ",";
							myService.add(d.getCode());
						}
					}
					if (zbkServices.length() > 0) {
						zbkServices = zbkServices.substring(0, zbkServices.length() - 1);
					}
					if (serviceView.length() > 0) {
						serviceView = serviceView.substring(0, serviceView.length() - 1);
					}
				}
			}
		}
		map.put("servicesView", serviceView);
		map.put("zbkServices", zbkServices);

		/**
		 * 功能：根据当前登录人去找orgid -> 在去找合作组的code -> 在去找对应的顾问
		 */
		/* 获得合作组别及合作项目 */
		/* 获取经纪人id */
		/*
		 * ToCaseInfo toCaseInfo =
		 * toCaseInfoService.findToCaseInfoByCaseCode(caseCode); String
		 * agentCode = toCaseInfo.getAgentCode(); 根据经纪人id查询经纪人详情，获得其组织id User
		 * agentUser = uamUserOrgService.getUserById(agentCode);
		 * List<CooperativeOrganizationVO> coList = new
		 * ArrayList<CooperativeOrganizationVO>(); if(agentUser == null) {
		 * map.put("coList", coList); return map; } String orgId =
		 * agentUser.getOrgId(); 根据经纪人组织id获得经纪人组织 Org jjrOrg =
		 * uamUserOrgService.getComapnyByOrgId(orgId); if(jjrOrg == null) {
		 * map.put("coList", coList); return map; } TsTeamScope tsTeamScope =
		 * new TsTeamScope();
		 * tsTeamScope.setYuAgentTeamCode(jjrOrg.getOrgCode());
		 * tsTeamScope.setYuTeamCode(tsTeamProperty.getYuTeamCode());
		 * List<TsTeamScope> tsTeamScopes =
		 * tsTeamScopeService.selectCooperativeOrganization(tsTeamScope);
		 */

		// --------------------------- start
		// --------------------------------------

		// 1 获取当前用户id
		// String loginId=null;
		String orgCode = null;
		SessionUser us = uamSessionService.getSessionUser();
		if (null != us) {
			// loginId=us.getId();
			orgCode = us.getServiceDepCode(); // 得到 orgCode
		}

		// 2 根据当前用户id 获取对应的orgid
		/*
		 * String orgId=null; User uu=uamUserOrgService.getUserById(loginId);
		 * if(null!=uu){ orgId=uu.getOrgId(); }
		 * 
		 * // 3 根据orgId 去查询对应的orgCode String orgCode=null; Org
		 * oo=uamUserOrgService.getOrgById(orgId); if(null!=oo){
		 * orgCode=oo.getOrgCode(); }
		 */

		// 4 根据orgCode 到 T_TS_TEAM_SCOPE 表中去查询合作组的 orgCode
		List<TsTeamScope> tsTeamScopes = tsTeamScopeService.selectByOrgCode(orgCode);

		// --------------------------- end
		// --------------------------------------

		Map<String, List<String>> serviceOrgMap = new HashMap<>();// 哪些服务由哪里组来做
		Map<String, List<String>> orgUserMap = new HashMap<>();// 对应组相关人员

		/* 遍历合作组列表   封装ServiceOrgMap*/
		for (int i = 0; i < tsTeamScopes.size(); i++) {
			TsTeamScope ts = tsTeamScopes.get(i);
			/* 组织查询条件根据tsTeamScope.getYuTeamCode(),IsResponseTeam 判断合作组是否符合条件 */
			TsTeamProperty tp = new TsTeamProperty();
			tp.setYuTeamCode(ts.getYuTeamCode());
			tp.setIsResponseTeam("0"); /* 合作组必须为非主办组 */
			TsTeamProperty ttps = tsTeamPropertyService.findTeamPropertyCooperation(tp);
			if (ttps != null) {/* 有符合条件的合作组 */

				List<Dict> dictList = getDictList(ttps.getTeamProperty());
				if (dictList != null && dictList.size() > 0) {
					for (Dict d : dictList) {
						if (d.getCode().equals("3000400201")) {/* 公积金需要选择中原代办公积金才会显示 */
							if (!value.equals("2")) {
								continue;
							}
						} else if (d.getCode().equals("3000400101")) {/* 贷款需要选择贷款才会显示 */
							if (!(value.equals("1") || value.equals("3"))) {
								continue;
							}
						}
						if(myService.contains(d.getCode())){
							continue;
						}
						List<String> tList = serviceOrgMap.get(d.getCode());
						if (tList == null) {
							tList = new ArrayList<>();
							serviceOrgMap.put(d.getCode(), tList);
						}
						tList.add(ttps.getYuTeamCode());
						
					}
				}

			}
		}
		Map<String, List<JSONObject>> allUserData = new HashMap<>();
		//查询所有用户相关用户数据
		for (String service : serviceOrgMap.keySet()) {
			List<String> orgs = serviceOrgMap.get(service);
			for (String orgStr : orgs) {
				if (orgUserMap.get(orgStr) == null) {
					Org org = uamUserOrgService.getOrgByCode(orgStr);
					List<User> list = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
							TransJobs.TJYGW.getCode());
					List<JSONObject> jsonList = new ArrayList<>();
					for (User user3 : list) {
						int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user3.getId());
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("count", userCaseUnTransCount);
						jsonObject.put("realName", user3.getRealName());
						jsonObject.put("orgName", user3.getOrgName());
						jsonObject.put("id", user3.getId());
						jsonList.add(jsonObject);
					}
					allUserData.put(orgStr, jsonList);
				}
			}
		}
		
		//**合作项目
		List<Dict> dics = getDictList("task_service");//
		Iterator<Dict> it = dics.iterator();
		while (it.hasNext()) {
			Dict d = it.next();
			if (d.getCode().equals("3000400201")) {/* 公积金需要选择中原代办公积金才会显示 */
				if (!value.equals("2")) {
					it.remove();
				}
			} else if (d.getCode().equals("3000400101")) {/* 贷款需要选择贷款才会显示 */
				if (!(value.equals("1") || value.equals("3"))) {
					it.remove();
				}
			}
			if(myService.contains(d.getCode())){
				it.remove();
			}
		}
		//将合作项目与人员相关数据合并
		JSONArray jsonArray = new JSONArray();
		for (Dict dict : dics) {
			JSONObject dicJson = new JSONObject();
			dicJson.put("dicCode", dict.getCode());
			dicJson.put("dicName", dict.getName());
			List<String> orgs = serviceOrgMap.get(dict.getCode());
			List<JSONObject> users = new ArrayList<>();
			if (orgs != null) {
				for (String string : orgs) {
					List<JSONObject> orgUsers = allUserData.get(string);
					users.addAll(orgUsers);
				}
			}
			dicJson.put("users", users);
			jsonArray.add(dicJson);
		}

		map.put("coList", jsonArray);
		return map;
	}
	@RequestMapping(value = "queryMortageServiceByServiceCode")
	@ResponseBody
	public Map<String,Object> queryMortageServiceByServiceCode(HttpServletRequest request, String serviceCode) {
		String orgCode = null;
		Map<String,Object>result=new HashMap<>();
		
		Dict dict= uamBasedataService.findDictByTypeAndCode("yu_all", serviceCode);
		SessionUser us = uamSessionService.getSessionUser();
		if (null != us) {
			orgCode = us.getServiceDepCode(); // 得到 orgCode
		}
		List<JSONObject> jsonList = new ArrayList<>();
		List<TsTeamScope> tsTeamScopes = tsTeamScopeService.selectByOrgCode(orgCode);
		Set<String>orgs=new HashSet<String>();
		/* 遍历合作组列表   封装ServiceOrgMap*/
		for (int i = 0; i < tsTeamScopes.size(); i++) {
			TsTeamScope ts = tsTeamScopes.get(i);
			/* 组织查询条件根据tsTeamScope.getYuTeamCode(),IsResponseTeam 判断合作组是否符合条件 */
			TsTeamProperty tp = new TsTeamProperty();
			tp.setYuTeamCode(ts.getYuTeamCode());
			/*tp.setIsResponseTeam("0");  合作组必须为非主办组 */
			TsTeamProperty ttps = tsTeamPropertyService.findTeamPropertyCooperation(tp);
			
			if (ttps != null) {/* 有符合条件的合作组 */
				List<Dict> dictList = getDictList(ttps.getTeamProperty());
				if (dictList != null && dictList.size() > 0) {
					for (Dict d : dictList) {
						if(serviceCode.equals(d.getCode())){
							orgs.add(ts.getYuTeamCode());
							if(dict==null)dict=d;
							break;
						}
					}
				}
			}
		}
		for (String orgStr : orgs) {
				Org org = uamUserOrgService.getOrgByCode(orgStr);
				List<User> list = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
						TransJobs.TJYGW.getCode());
				for (User user3 : list) {
					int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user3.getId());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("count", userCaseUnTransCount);
					jsonObject.put("realName", user3.getRealName());
					jsonObject.put("orgName", user3.getOrgName());
					jsonObject.put("id", user3.getId());
					jsonList.add(jsonObject);
				}
		}
		result.put("dic", dict);
		result.put("users", jsonList);
		

		return result;
	}
	/**
	 * 根据字典类型，获得相应字典数据
	 * 
	 * @param dictByType
	 * @return
	 */
	private List<Dict> getDictList(String dictByType) {
		List<Dict> list = new ArrayList<Dict>();
		Dict dict = uamBasedataService.findDictByType(dictByType);
		if (dict != null) {
			list = dict.getChildren();
		}
		return list;
	}

	/*获取跨区合作的选项*/
	@RequestMapping("getCrossAeraCooperationItems")
	@ResponseBody
	public Map<String,Object> getCrossAeraCooperationItems(HttpServletRequest request)
	{
		Map<String,Object>result=new HashMap<String,Object>();
		SessionUser us = uamSessionService.getSessionUser();
		
		//获取所有的贵宾服务部
		List<ToOrgVo> orgIdList = toCaseService.getOrgIdAllByDep(DepTypeEnum.TYCQY.getCode());
		Org myDistrict = uamUserOrgService.getParentOrgByDepHierarchy(us.getServiceDepId(), DepTypeEnum.TYCQY.getCode()); //获取用户的所在的贵宾服务部
		
		//获取下拉的贵宾服务组
		List<JSONObject> jsonList1 = new ArrayList<JSONObject>();
		if (orgIdList != null && orgIdList.size() > 0 && myDistrict != null)
		{
			for (ToOrgVo toOrgVo : orgIdList) 
			{
				Org district = uamUserOrgService.getOrgById(toOrgVo.getId());
				if(!myDistrict.getId().equals(district.getId())&&!"b4c490edc38c431a8dfd7dba98c73fe5".equals(district.getId())&&!"8a8493d4538a517a01539d47b51c1b02".equals(district.getId()))
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("districtId", district.getId());
					jsonObject.put("districtName", district.getOrgName());
					jsonList1.add(jsonObject);
					
					//获取该贵宾服务部下的后台组
					List<Org> orgList = uamUserOrgService.getOrgByParentId(district.getId());
					List<JSONObject> jsonList2 = new ArrayList<JSONObject>();
					if (orgList != null && orgList.size() > 0) {
						for (Org org : orgList) {
							TsTeamProperty tsTeamProperty =tsTeamPropertyService.findTeamPropertyByTeamCode(org.getOrgCode());
							if(tsTeamProperty!=null){
								if("yu_all".equals(tsTeamProperty.getTeamProperty())||"yu_back".equals(tsTeamProperty.getTeamProperty()))
								{
									JSONObject subJsonObj = new JSONObject();
									subJsonObj.put("orgId", org.getId());
									subJsonObj.put("orgName", org.getOrgName());
									jsonList2.add(subJsonObj);
									
									//获取交易顾问
									List<User> list = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),TransJobs.TJYGW.getCode());
									List<JSONObject> jsonList3 = new ArrayList<JSONObject>();
									if (list != null && list.size() > 0)
									{
										for (User user : list) 
										{
											JSONObject userJsonObj = new JSONObject();
											int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user.getId());
											userJsonObj.put("id", user.getId());
											userJsonObj.put("realName", user.getRealName());
											userJsonObj.put("count", userCaseUnTransCount);
											jsonList3.add(userJsonObj);
										}
									}
									subJsonObj.put("userItems", jsonList3);
								}
							}
						}
					}
					jsonObject.put("orgs", jsonList2);
				}
			}
		}
		
		result.put("cross", jsonList1);
		return result;
	}
	
	
	@RequestMapping(value = "submit")
	@ResponseBody
	public boolean submit(HttpServletRequest request, FirstFollowVO firstFollowVO, String operator,
			String approveType) {
		SessionUser user = uamSessionService.getSessionUser();
		firstFollowVO.setUserId(user.getId());
		firstFollowVO.setUserOrgId(getOrgId(user.getId()));
		firstFollowService.saveFirstFollow(firstFollowVO);

		/* 无效案件保存到审批记录表 */
		if (firstFollowVO.getCaseProperty().equals("30003001")) {
			saveToApproveRecord(firstFollowVO, operator, approveType);
		}

		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable restVariable = new RestVariable();
		restVariable.setName("isvalid");
		restVariable.setValue(firstFollowVO.getCaseProperty().equals("30003001"));
		variables.add(restVariable);
		if (firstFollowVO.getCaseProperty().equals("30003001")) {
			if (!StringUtils.isBlank(firstFollowVO.getInvalid_reason())) {
				RestVariable restVariable6 = new RestVariable();
				restVariable6.setName("invalid_reason");
				restVariable6.setValue(firstFollowVO.getInvalid_reason());
				variables.add(restVariable6);
			}
		} else {
			RestVariable restVariable3 = new RestVariable();/* 限购 */
			restVariable3.setName("PurLimitCheckNeed");
			RestVariable restVariable4 = new RestVariable();/* 抵押 */
			restVariable4.setName("LoanCloseNeed");

			restVariable3.setValue(firstFollowVO.getChaxiangou().equals("true"));
			restVariable4.setValue(firstFollowVO.getDiya().equals("true"));

			variables.add(restVariable3);
			variables.add(restVariable4);

//			variables = editRestVariables(variables, firstFollowVO.getMortageService());
		}

		ToCase toCase = toCaseService.findToCaseByCaseCode(firstFollowVO.getCaseCode());
		return workFlowManager.submitTask(variables, firstFollowVO.getTaskId(), firstFollowVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(), firstFollowVO.getCaseCode());
		// return false;
	}

	/**
	 * 编辑贷款相关参数
	 * 
	 * @param variables
	 * @param loanTyby
	 * @return
	 */
	private List<RestVariable> editRestVariables(List<RestVariable> variables, String loanTyby) {

		RestVariable restVariable1 = new RestVariable();
		restVariable1.setName("ComLoanNeed");
		RestVariable restVariable2 = new RestVariable();
		restVariable2.setName("PSFLoanNeed");
		RestVariable restVariable5 = new RestVariable();
		restVariable5.setName("SelfLoanNeed");

		if (loanTyby.equals("1")) {/* 中原 组合贷 */
			restVariable1.setValue(true);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		} else if (loanTyby.equals("2")) {/* 中原 公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(true);
			restVariable5.setValue(false);
		} else if (loanTyby.equals("3")) {/* 自办 组合贷 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else if (loanTyby.equals("4")) {/* 自办 公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else {/* 无贷款 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		}

		variables.add(restVariable1);
		variables.add(restVariable2);
		variables.add(restVariable5);

		return variables;
	}

	/**
	 * 获得合作人的orgid
	 * 
	 * @param userId
	 * @return
	 */
	private String getOrgId(String userId) {
		if (!StringUtils.isBlank(userId)) {
			User user = uamUserOrgService.getUserById(userId);
			if (user != null) {
				return user.getOrgId();
			}
		}
		return null;
	}

	/**
	 * 保存审核记录
	 * 
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(FirstFollowVO firstFollowVO, String operator, String approveType) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		// toApproveRecord.setPkid(loanlostApproveVO.getLapPkid());
		toApproveRecord.setProcessInstance(firstFollowVO.getProcessInstanceId());
		toApproveRecord.setPartCode(firstFollowVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(approveType);
		toApproveRecord.setCaseCode(firstFollowVO.getCaseCode());
		toApproveRecord.setContent(firstFollowVO.getInvalid_reason());
		toApproveRecord.setOperator(operator);
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}

}
