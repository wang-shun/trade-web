package com.centaline.trans.cases.web;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.OrgNameEnum;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.service.TsPrResearchMapService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamTransfer;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamTransferService;
import com.centaline.trans.team.vo.TeamTransferVO;
import com.centaline.trans.utils.URLAvailability;

@Controller
@RequestMapping(value="/case")
/**
 * 
 * <p>Project: 案件分配</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015</p>
 * @author wanggh</a>
 */
public class CaseDistributeController {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	WorkFlowManager workFlowManager;

	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	ToTransPlanService toTransPlanService;
	
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	UamMessageService uamMessageService;
	@Autowired(required=true)
	UamTemplateService uamTemplateService;
	@Autowired(required = true)
	PropertyUtilsService propertyUtilsService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	TsTeamPropertyService tsTeamPropertyService;
	@Autowired(required = true)
	TsTeamTransferService tsTeamTransferService;
	@Autowired(required = true)
	TsPrResearchMapService tsPrResearchMapService;
	
	/**
	 * 页面初始化
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value="caseDistribute")
	public String caseDistribute(Model model, ServletRequest request){
		//TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		String queryUserId = user.getId();
		String queryOrgId = user.getServiceDepId();
		if(userJob.equals(TransJobs.TJYZL.getCode())){
			List<User> userList = uamUserOrgService.getUserByOrgIdAndJobCode(user.getServiceDepId(), TransJobs.TJYZG.getCode());
			if(userList!=null && userList.size()>0){
			    queryUserId = userList.get(0).getId();
			}
		}
		request.setAttribute("queryUserId", queryUserId);
		request.setAttribute("queryOrgId", queryOrgId);
		return "case/caseDistribute";
	}

	/**
	 * 页面初始化
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value="unlocatedCase")
	public String unlocatedCase(Model model, ServletRequest request){
		Org o= uamUserOrgService.getOrgByCode("033F275");
		model.addAttribute("nonBusinessOrg", o);
		return "case/unlocatedCase";
	}

	/**
	 * 用户机构交易顾问查询
	 * @return
	 * @throws ParseException 
	 */
    @RequestMapping(value="/getUserOrgCpUserList")
    @ResponseBody
	public List<VCaseDistributeUserVO>  getUserOrgCpUserList(HttpServletRequest request) throws ParseException{
    	List<VCaseDistributeUserVO> res=new ArrayList<VCaseDistributeUserVO>();
    	//获取当前用户
    	SessionUser sessionUser = uamSessionService.getSessionUser();
    	//获取机构交易顾问列表
    	List<User> userList= uamUserOrgService.getUserByOrgIdAndJobCode(sessionUser.getServiceDepId(), TransJobs.TJYGW.getCode());
    	
    	for(int i=0;i<userList.size();i++){
    		VCaseDistributeUserVO vo = new VCaseDistributeUserVO();
    		User user = userList.get(i);
            vo.setId(user.getId());
            vo.setMobile(user.getMobile());
            vo.setRealName(user.getRealName());
            int userCaseCount = toCaseInfoService.queryCountCasesByUserId(user.getId());
            int userCaseMonthCount = toCaseInfoService.queryCountMonthCasesByUserId(user.getId());
            int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user.getId());
            
            vo.setUserCaseCount(userCaseCount);
            vo.setUserCaseMonthCount(userCaseMonthCount);
            vo.setUserCaseUnTransCount(userCaseUnTransCount);
            String url = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+user.getEmployeeCode()+".jpg";
           
            
            vo.setImgUrl(url);
            
            res.add(vo);
    	}
    	
    	return res;
    }

	/**
	 * 用户同级别机构查询
	 * @return
	 * @throws ParseException 
	 */
    @RequestMapping(value="/getOrgTeamList")
    @ResponseBody
	public List<Org>  getOrgTeamList(HttpServletRequest request) throws ParseException{
    	List<Org> res=new ArrayList<Org>();
    	//获取当前用户
    	SessionUser sessionUser = uamSessionService.getSessionUser();
    	//获取机构交易顾问列表
    	Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(sessionUser.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
    	//是否主办组
    	List<Org> noResponseTeamList=new ArrayList<Org>();
    	res = uamUserOrgService.getOrgByDepHierarchy(parentOrg.getId(), DepTypeEnum.TYCTEAM.getCode());
    	for(Org org:res){

    		if(org.getId().equals(sessionUser.getServiceDepId())){
    			noResponseTeamList.add(org);
    			continue;
    		}
    		TsTeamProperty tsTeamProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(org.getOrgCode());
    		if(tsTeamProperty==null 
    				|| tsTeamProperty.getIsResponseTeam()==null 
    				||!tsTeamProperty.getIsResponseTeam().equals("1")){
    			noResponseTeamList.add(org);
    		}
    	}

    	res.removeAll(noResponseTeamList);
    	return res;
    }
    /**
	 * 机构查询
	 * @return
	 * @throws ParseException 
	 */
    @RequestMapping(value="/getAllTeamList")
    @ResponseBody
	public List<Org>  getAllTeamList(HttpServletRequest request) throws ParseException{
    	List<Org> res=new ArrayList<Org>();
    	//获取机构交易顾问列表
    	Org parentOrg = uamUserOrgService.getOrgByCode(OrgNameEnum.T_FATHER_ORG.getCode());
    	res = uamUserOrgService.getOrgByDepHierarchy(parentOrg.getId(), DepTypeEnum.TYCTEAM.getCode());
    	//是否主办组
    	List<Org> noResponseTeamList=new ArrayList<Org>();
    	for(Org org:res){
    		TsTeamProperty tsTeamProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(org.getOrgCode());
    		if(tsTeamProperty==null 
    				|| tsTeamProperty.getIsResponseTeam()==null 
    				||!tsTeamProperty.getIsResponseTeam().equals("1")){
    			noResponseTeamList.add(org);
    		}
    	}
    	res.removeAll(noResponseTeamList);
    	return res;
    }
    
	/**
	 * 案件是否转给了其他服务区域
	 * 
	 * @return
	 * @throws  
	 */
    @RequestMapping(value="/isTransferOtherDistrict")
    @ResponseBody
	public AjaxResponse<Boolean>  isTransferOtherDistrict(String[] caseCodes ,String userId,HttpServletRequest request) {
    	boolean isTransferOther = false;
    	
    	// 查找转给的用户能够服务的区域
    	SessionUser distributeUser = uamSessionService.getSessionUserById(userId);
    	Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(distributeUser.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
    	TsPrResearchMap param = new TsPrResearchMap();
    	param.setYuDistCode(parentOrg.getOrgCode());
    	List<TsPrResearchMap> tsPrResearchMapList = tsPrResearchMapService.getTsPrResearchMapByProperty(param);
    	
    	for(String caseCode : caseCodes){
    		
    		ToPropertyInfo propertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
    		//房源编号
    		String delCode = propertyInfo.getPropertyAgentId();
    		if(StringUtils.isBlank(delCode)){
    			continue;
    		}
    		// 当前案件属于的区域
    		boolean isExistNoPatter = true ;
    		String districtCode = "";
    		ViHouseDelBaseVo housevo = toPropertyInfoService.getHouseBaseByHoudelCode(delCode);
    		if(housevo != null && StringUtils.isNotBlank(housevo.getDISTRICT_CODE())) {
    			districtCode = housevo.getDISTRICT_CODE();
    			for(TsPrResearchMap tsPrResearchMap :tsPrResearchMapList) {
    				if(districtCode.equals(tsPrResearchMap.getDistCode())) {
    					isExistNoPatter = false;
    					continue;
    				}
    			}
    		}
    		
    		if(isExistNoPatter) {
    			isTransferOther = true;
    			break;
    		}
    	}
    	AjaxResponse ajaxResponse = new AjaxResponse();
    	ajaxResponse.setContent(isTransferOther);
    	return ajaxResponse;
    }
    
	/**
	 * 分配案件
	 * @return
	 * @throws ParseException 
	 */
    @RequestMapping(value="/bindCaseDist")
    @ResponseBody
	public AjaxResponse<?>  bindCaseDist(String[] caseCodes ,String userId,HttpServletRequest request) {
    	SessionUser sessionUser = uamSessionService.getSessionUser();
    	for(String caseCode:caseCodes){	    
        	//案件信息更新
    		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
    		
    		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
    		if("1".equals(toCaseInfo.getIsResponsed())){
    			return AjaxResponse.fail("数据已经被修改！");
    		}
	    	toCaseInfo.setIsResponsed("1");
	    	toCaseInfo.setRequireProcessorId(sessionUser.getId());
	    	toCaseInfo.setResDate(new Date());
	    	int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
	    	if(reToCaseInfo == 0)return AjaxResponse.fail("案件信息表更新失败！");
    		
    		
    		// 如果是无主案件分配,需要维护案件负责人
    		if(toCase == null) {
    			toCase = new ToCase();
    			toCase.setCaseCode(caseCode);
    			toCase.setCtmCode(toCaseInfo.getCtmCode());
    			toCase.setCaseProperty(CasePropertyEnum.TPZT.getCode());
    			toCase.setStatus(CaseStatusEnum.YFD.getCode());
    			toCase.setCreateTime(new Date());
    			toCase.setLeadingProcessId(userId);
    			
    			SessionUser user = uamSessionService.getSessionUserById(userId);
    			toCase.setOrgId(user.getServiceDepId());
    			int caseCount = toCaseService.insertSelective(toCase);
    			if(caseCount == 0)return AjaxResponse.fail("无主案件基本表新增失败！");
    		} else {
        		toCase.setLeadingProcessId(userId);
        		if(!CaseStatusEnum.WFD.getCode().equals(toCase.getStatus())){
        			return AjaxResponse.fail("数据已经被修改！");
        		}
        		toCase.setStatus(CaseStatusEnum.YFD.getCode());
        		int reToCase = toCaseService.updateByPrimaryKey(toCase);
        		if(reToCase == 0)return AjaxResponse.fail("案件基本表更新失败！");
    		}

    		
    		
    		ToWorkFlow toWorkFlow = new ToWorkFlow();

    		//启动流程引擎
        	ProcessInstance process = new ProcessInstance();
        	process.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
        	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
        	//流程引擎相关
        	Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
    		List<RestVariable> variables = new ArrayList<RestVariable>();
    	    Iterator it = defValsMap.keySet().iterator();  
    	    while (it.hasNext()) {  
	            String key = it.next().toString();  
	    		RestVariable restVariable = new RestVariable();
	    		restVariable.setName(key); 
	    		restVariable.setValue(defValsMap.get(key));
	    		variables.add(restVariable);
		    }
        	process.setVariables(variables);
        	User user = uamUserOrgService.getUserById(userId);
        	try {
            	StartProcessInstanceVo pIVo = workFlowManager.startCaseWorkFlow(process, user.getUsername(),caseCode);

	    		toWorkFlow.setInstCode(pIVo.getId());
	    		toWorkFlow.setBusinessKey(pIVo.getBusinessKey());
	    		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
	    		toWorkFlow.setProcessOwner(userId);
	    		
	    		String propAddrString = "";
	    		String agentMobile = "";
	    		String agentName = "";

	    		//物业信息
	    		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
	    		if(toPropertyInfo!=null){
	    			if(!StringUtils.isEmpty(toPropertyInfo.getPropertyAddr())){
	        			propAddrString = toPropertyInfo.getPropertyAddr();
	    			}
	    		}
	    		//经纪人
				if(!StringUtils.isEmpty(toCaseInfo.getAgentCode())){
		    		User agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		    		if(agentUser!=null){
		    			if(!StringUtils.isEmpty(agentUser.getMobile())){
		    				agentMobile = agentUser.getMobile();
		    				agentName = agentUser.getRealName();
		    			}
		    		}
				}
	    		Message message= new Message();
				String resourceCode = MsgLampEnum.MFOLLOW.getCode();
	    		String title = MsgLampEnum.MFOLLOW.getName();
	    		Map<String, Object> params = new HashMap<String, Object>();//创建map
				params.put("case_code", caseCode);//放入参数
			    params.put("property_address",propAddrString);
			    params.put("agent_name",agentName);
			    params.put("agent_phone",agentMobile);
			    
				String content = uamTemplateService.mergeTemplate(resourceCode, params);//拼接发送的字符串
				message.setTitle(title);//消息标题
				message.setType(MessageType.SITE);//消息类型  
				message.setMsgCatagory(MsgCatagoryEnum.WORK.getCode());
				message.setContent(content);
				message.setSenderId(sessionUser.getId());
				uamMessageService.sendMessageByDist(message, userId);
			} catch (WorkFlowException e) {
				// TODO: handle exception
				return AjaxResponse.fail(e.getMessage());
			}
        	
	    	toWorkFlow.setCaseCode(toCase.getCaseCode());
	    		
	    	int reToWorkFlow = toWorkFlowService.insertSelective(toWorkFlow);
    		if(reToWorkFlow == 0)return AjaxResponse.fail("案件工作流表插入失败！");

			ToTransPlan record = new ToTransPlan();
			record.setCaseCode(caseCode);
			record.setPartCode(ToAttachmentEnum.FIRSTFOLLOW.getCode());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 2);
			record.setEstPartTime(cal.getTime());
			toTransPlanService.insertSelective(record);
    	}
    	return AjaxResponse.success("案件信息绑定成功！");
    }
    
    /****
     *  案件转组日志
     * 
     *  @param caseCode
     *  @return
     */
    private int addTeamTransferLog(ToCaseInfo toCaseInfo,String orgId)  {
    		TsTeamTransfer teamTransfer = new TsTeamTransfer();
        	//未被删除,更新状态
        	teamTransfer.setIsDelete("0");
        	teamTransfer.setCaseCode(toCaseInfo.getCaseCode());
        	teamTransfer.setTeamOrigin(toCaseInfo.getGrpCode());
        	TsTeamTransfer tsTeamTransferOld =  tsTeamTransferService.getTsTeamTransferByProperty(teamTransfer);
        	if(tsTeamTransferOld != null) {
        	  	tsTeamTransferOld.setIsDelete("1");
            	int updateCount = tsTeamTransferService.updateByPrimaryKeySelective(tsTeamTransferOld);
        	}
        	Org org = uamUserOrgService.getOrgById(orgId);
        	TsTeamTransfer teamTransferNew = new TsTeamTransfer();
        	teamTransferNew.setCaseCode(toCaseInfo.getCaseCode());
        	teamTransferNew.setIsDelete("0");
        	teamTransferNew.setTeamOrigin(toCaseInfo.getGrpCode());
        	teamTransferNew.setTeamNow(org.getOrgCode());
        	int addCount = tsTeamTransferService.insertSelective(teamTransferNew);
    	
    	    return addCount;
    }
    /**
	 * 分配组别
	 * @return
	 * @throws ParseException 
	 */
    @RequestMapping(value="/bindCaseTeam")
    @ResponseBody
	public AjaxResponse<?>  bindCaseTeam(@RequestBody TeamTransferVO teamTransferVO,HttpServletRequest request) {
    	
    	List<User> managerUsers = uamUserOrgService.getUserByOrgIdAndJobCode(teamTransferVO.getOrgId(), TransJobs.TJYZG.getCode());
    	if(managerUsers.size()==0||managerUsers==null)return AjaxResponse.fail("未找到交易主管！");
    	User managerUser= managerUsers.get(0);
    	List<ToCaseInfo> caseInfoList  = teamTransferVO.getCaseInfoList();
    	for(ToCaseInfo toCaseInfoNew:caseInfoList){	    
    		int addTeamTrasLogCount = addTeamTransferLog(toCaseInfoNew, teamTransferVO.getOrgId());
    		if(addTeamTrasLogCount == 0)return AjaxResponse.fail("案件信息转组记录日志失败！");
        	//案件信息更新
    		ToCase toCase = toCaseService.findToCaseByCaseCode(toCaseInfoNew.getCaseCode());
    		if(toCase != null) {
    			toCase.setLeadingProcessId(managerUser.getId());
        		toCase.setOrgId(teamTransferVO.getOrgId());
        		int reToCase = toCaseService.updateByPrimaryKey(toCase);
        		if(reToCase == 0)return AjaxResponse.fail("案件基本表更新失败！");
    		}
    		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCaseInfoNew.getCaseCode());
    		toCaseInfo.setRequireProcessorId(managerUser.getId());
	    	int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
    		if(reToCaseInfo == 0)return AjaxResponse.fail("案件信息表更新失败！");
    	}
    	return AjaxResponse.success("案件信息绑定成功！");
    }
    
   /* @RequestMapping(value="/bindCaseTeam")
    @ResponseBody
	public AjaxResponse<?>  bindCaseTeam(String[] caseCodes ,String orgId,HttpServletRequest request) {
    	
    	List<User> managerUsers = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, TransJobs.TJYZG.getCode());
    	if(managerUsers.size()==0)return AjaxResponse.fail("未找到交易主管！");
    	User managerUser= managerUsers.get(0);
    	for(String caseCode:caseCodes){	    
    		
        	//案件信息更新
    		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
    		toCase.setLeadingProcessId(managerUser.getId());
    		toCase.setOrgId(orgId);
    		int reToCase = toCaseService.updateByPrimaryKey(toCase);
    		if(reToCase == 0)return AjaxResponse.fail("案件基本表更新失败！");
	    		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
	    		toCaseInfo.setRequireProcessorId(managerUser.getId());
	    	int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
    		if(reToCaseInfo == 0)return AjaxResponse.fail("案件信息表更新失败！");
    	}
    	return AjaxResponse.success("案件信息绑定成功！");
    }*/
    /**
 	 * 分配组别
 	 * @return
 	 * @throws ParseException 
 	 */
     @RequestMapping(value="/orgChange")
     @ResponseBody
 	public AjaxResponse<?>  orgChange(String caseCode ,String orgId) {
    	int r= toCaseService.orgChange(caseCode, orgId);
    	if(r<1){
    		return AjaxResponse.fail("案件转组成功！");
    	}
     	return AjaxResponse.success("案件转组成功！");
     }
    
    /**
   	 * 分配组别
   	 * @return
   	 * @throws ParseException 
   	 */
       @RequestMapping(value="/bindUnLocatedCaseTeam")
       @ResponseBody
   	public AjaxResponse<?>  bindUnLocatedCaseTeam(String[] caseCodes ,String orgId,String orgName,HttpServletRequest request) {
    	 if(orgId==null){
    		 return AjaxResponse.fail("请选择一个片区！");
    	 }  
    	Org org= uamUserOrgService.getOrgById(orgId);
       	if(org!=null&&DepTypeEnum.BUSIAR.getCode().equals(org.getDepHierarchy())){
	       	for(String caseCode:caseCodes){	    
		    	ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		    	toCaseInfo.setArCode(org.getOrgCode());
		    	toCaseInfo.setArName(org.getOrgName());
	   	    	toCaseInfo.setDispatchTime(new Date());
	   	    	int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
	       		if(reToCaseInfo == 0)return AjaxResponse.fail("案件信息表更新失败！");
	       		
	       	}
	       	return AjaxResponse.success("案件信息绑定成功！");
       	}else{
       		return AjaxResponse.fail("请选择一个片区！");
       	}
    }
}









