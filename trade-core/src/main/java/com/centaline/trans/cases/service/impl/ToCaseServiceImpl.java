package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
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
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.vo.AgentManagerInfo;
import com.centaline.trans.common.vo.BuyerSellerInfo;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.ToTransPlanService;

@Service
@Transactional
public class ToCaseServiceImpl implements ToCaseService {

	@Autowired
	private ToCaseMapper toCaseMapper;

	@Autowired
	private ToSpvService toSpvService;

	@Autowired
	private ToCaseInfoService toCaseInfoService;

	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired
	private WorkFlowManager workflowManager;
	@Autowired
	private ToCloseService toCloseService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	ToPropertyService toPropertyService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	private ToTransPlanService toTransPlanService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired(required=true)
	private UamTemplateService uamTemplateService;
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TlTaskReassigntLogService taskReassingtLogService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
	@Autowired
	private UamSessionService uamSessionService;
	@Override
	public int updateByPrimaryKey(ToCase record) {
		// TODO Auto-generated method stub
		return toCaseMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToCase findToCaseByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toCaseMapper.findToCaseByCaseCode(caseCode);
	}

	@Override
	public ToCase selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toCaseMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public ToCaseInfoCountVo queryConutCase(ToCase toCase) {
		return toCaseMapper.queryConutCase(toCase);
	}

	@Override
	public int updateByCaseCodeSelective(ToCase record) {
		return toCaseMapper.updateByCaseCodeSelective(record);
	}

	@Override
	public ToCaseInfoCountVo queryConutCaseByOrg(ToCase toCase) {
		return toCaseMapper.queryConutCaseByOrg(toCase);
	}

	@Override
	public List<ToCase> getOrgId() {
		return toCaseMapper.getOrgId();
	}

	@Override
	public List<ToCaseInfoCountVo> getCaseCount() {
		// int jds = 0;
		// int qys = 0;
		// int ghs = 0;
		// int jas = 0;
		// String createTime = "";
		String orgId = null;
		// List<ToCaseInfoCountVo> countList = new ArrayList<>();
		// ToCaseInfoCountVo toCaseInfoCount = new ToCaseInfoCountVo();
		// List<List<ToCaseInfoCountVo>> toCaseInfoCountVoLists =
		// getToCaseInfoCountListByOrgId(orgId);
		// model.addAttribute("toCaseInfoCountVoList", toCaseInfoCountVoLists);
		// for (List<ToCaseInfoCountVo> list : toCaseInfoCountVoLists) {
		// for (ToCaseInfoCountVo toCaseInfoCountVo : list) {
		// if(null != toCaseInfoCountVo.getCountJDS()){
		// jds = toCaseInfoCountVo.getCountJDS();
		// createTime = toCaseInfoCountVo.getCreateTime();
		// }else if(null != toCaseInfoCountVo.getCountQYS()){
		// qys = toCaseInfoCountVo.getCountQYS();
		// }else if(null != toCaseInfoCountVo.getCountGHS()){
		// ghs = toCaseInfoCountVo.getCountGHS();
		// }else if(null != toCaseInfoCountVo.getCountJAS()){
		// jas = toCaseInfoCountVo.getCountJAS();
		// }
		// }
		// }

		// toCaseInfoCount.setCountJDS(jds);
		// toCaseInfoCount.setCountQYS(qys);
		// toCaseInfoCount.setCountGHS(ghs);
		// toCaseInfoCount.setCountJAS(jas);
		// toCaseInfoCount.setCreateTime(createTime);
		// countList.add(toCaseInfoCount);

		// 接单数
		List<ToCaseInfoCountVo> toCaseInfoCountList = toCaseInfoService.countToCaseInfoListByOrgId(orgId);

		// ,签约
		List<ToCaseInfoCountVo> toSignCountList = toSpvService.countToSignListByOrgId(orgId);
		// ,过户
		List<ToCaseInfoCountVo> toHouseTransferCountList = toHouseTransferService
				.countToHouseTransferListByOrgId(orgId);
		// ,结案
		List<ToCaseInfoCountVo> toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);

		// return countList;
		return null;
	}

	// 跟进orgId查询统计数据
	private List<List<ToCaseInfoCountVo>> getToCaseInfoCountListByOrgId(String orgId) {

		// 接单数
		List<ToCaseInfoCountVo> toCaseInfoCountList = toCaseInfoService.countToCaseInfoListByOrgId(orgId);

		// ,签约
		List<ToCaseInfoCountVo> toSignCountList = toSpvService.countToSignListByOrgId(orgId);
		// ,过户
		List<ToCaseInfoCountVo> toHouseTransferCountList = toHouseTransferService
				.countToHouseTransferListByOrgId(orgId);
		// ,结案
		List<ToCaseInfoCountVo> toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);

		List<List<ToCaseInfoCountVo>> toCaseInfoCountLists = new ArrayList<>();
		toCaseInfoCountLists.add(toCaseInfoCountList);
		toCaseInfoCountLists.add(toSignCountList);
		toCaseInfoCountLists.add(toHouseTransferCountList);
		toCaseInfoCountLists.add(toCloseCountList);

		return toCaseInfoCountLists;
	}

	@Override
	public int insertSelective(ToCase record) {
		// TODO Auto-generated method stub
		return toCaseMapper.insertSelective(record);
	}

	@Override
	public List<ToOrgVo> getOrgIdAllByDep(String dep) {

		return toCaseMapper.getOrgIdAll(dep);
	}

	@Override
	public int getRedcountByOrgList(List<String> orgIdList, String strNum, String endNum) {
		return toCaseMapper.getRedcountByOrgList(orgIdList, strNum, endNum);
	}

	@Override
	public int getRedcountByIdList(List<String> idList, String strNum, String endNum) {
		return toCaseMapper.getRedcountByIdList(idList, strNum, endNum);
	}

	@Override
	public int updateByPrimaryKeySelective(ToCase record) {
		// TODO Auto-generated method stub
		return toCaseMapper.updateByPrimaryKeySelective(record);
	}

	private CaseBaseVO getCaseBaseVO(ToCase toCase){
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());
        // 物业地址
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		// 买卖双方信息
		BuyerSellerInfo  buyerSellerInfo = tgGuestInfoService.getBuerSellerInfoByCaseCode(toCase.getCaseCode());
		
		// 经纪人信息,分行经理信息,交易顾问信息
		AgentManagerInfo agentManagerInfo = new AgentManagerInfo();
		agentManagerInfo.setAgentName(toCaseInfo.getAgentName());
		agentManagerInfo.setAgentPhone(toCaseInfo.getAgentPhone());
		agentManagerInfo.setGrpName(toCaseInfo.getGrpName());
		User agentUser = null;
		if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
			agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		}
		if (agentUser != null) {
			// 分行经理
			List<User> mcList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(agentUser.getOrgId(),
					TransJobs.TFHJL.getCode());
			if (mcList != null && mcList.size() > 0) {

				User mcUser = mcList.get(0);
				agentManagerInfo.setMcId(mcUser.getId());
				agentManagerInfo.setMcName(mcUser.getRealName());
				agentManagerInfo.setMcMobile(mcUser.getMobile());
			}
		}
		
		// 交易顾问
		User consultUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		if (consultUser != null) {
			agentManagerInfo.setCpId(consultUser.getId());
			agentManagerInfo.setCpName(consultUser.getRealName());
			agentManagerInfo.setCpMobile(consultUser.getMobile());
		}
		
		// 助理
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(consultUser.getOrgId(),
				TransJobs.TJYZL.getCode());
		if (asList != null && asList.size() > 0) {
			User assistUser = asList.get(0);
			agentManagerInfo.setAsId(assistUser.getId());
			agentManagerInfo.setAsName(assistUser.getRealName());
			agentManagerInfo.setAsMobile(assistUser.getMobile());
		}
		
		// 合作顾问
		List<CaseDetailProcessorVO> proList = new ArrayList<CaseDetailProcessorVO>();
		TgServItemAndProcessor inProcessor = new TgServItemAndProcessor();
		inProcessor.setCaseCode(toCase.getCaseCode());
		inProcessor.setProcessorId(toCase.getLeadingProcessId());
		List<String> tgproList = tgServItemAndProcessorService.findProcessorsByCaseCode(inProcessor);
		
		for (String sp : tgproList) {
			if (StringUtils.isEmpty(sp) || "-1".equals(sp))
				continue;
			CaseDetailProcessorVO proVo = new CaseDetailProcessorVO();
			User processor = uamUserOrgService.getUserById(sp);
			proVo.setProcessorId(processor.getId());
			proVo.setProcessorName(processor.getRealName());
			proVo.setProcessorMobile(processor.getMobile());
			proList.add(proVo);
		}
		agentManagerInfo.setProList(proList);
		
		CaseBaseVO caseBaseVO = new CaseBaseVO();
		caseBaseVO.setBuyerSellerInfo(buyerSellerInfo);
		caseBaseVO.setToCase(toCase);
		caseBaseVO.setToCaseInfo(toCaseInfo);
		caseBaseVO.setToPropertyInfo(toPropertyInfo);
		caseBaseVO.setAgentManagerInfo(agentManagerInfo);
		return caseBaseVO;
	}
	

	@Override
	public CaseBaseVO getCaseBaseVO(Long caseId) {
		// 基本信息
		ToCase toCase = selectByPrimaryKey(caseId);
		return getCaseBaseVO(toCase);
	}
	@Override
	public CaseBaseVO getCaseBaseVO(String caseCode) {
		// 基本信息
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
		return getCaseBaseVO(toCase);
	}
	@Override
	public void caseAssign(String caseCode,String userId,SessionUser sessionUser){
		String orgId = sessionUser.getServiceDepId();
		Org org = uamUserOrgService.getOrgById(orgId);
		//案件信息更新
		ToCase toCase = findToCaseByCaseCode(caseCode);
		
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if("1".equals(toCaseInfo.getIsResponsed())){
			throw new BusinessException("数据已经被修改！");
		}
    	toCaseInfo.setIsResponsed("1");
    	toCaseInfo.setRequireProcessorId(sessionUser.getId());
    	toCaseInfo.setResDate(new Date());
    	int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
    	if(reToCaseInfo == 0)throw new BusinessException( "案件信息表更新失败！");

		// 如果是无主案件分配,需要维护案件负责人
		if(toCase == null) {
			toCase = new ToCase();
			toCase.setCaseCode(caseCode);
			toCase.setCtmCode(toCaseInfo.getCtmCode());
			toCase.setCaseProperty(CasePropertyEnum.TPZT.getCode());
			toCase.setStatus(CaseStatusEnum.YFD.getCode());
			toCase.setCreateTime(new Date());
			toCase.setLeadingProcessId(userId);
			//填写誉翠组
			toCase.setOrgId(orgId);
			//填写贵宾服务部
			toCase.setDistrictId(org==null?null:org.getParentId());
			int caseCount = insertSelective(toCase);
			if(caseCount == 0)throw new BusinessException("无主案件基本表新增失败！");
		} else {
    		toCase.setLeadingProcessId(userId);
    		//填写誉翠组
    		toCase.setOrgId(orgId);
    		//填写贵宾服务部
			toCase.setDistrictId(org==null?null:org.getParentId());
    		if(!CaseStatusEnum.WFD.getCode().equals(toCase.getStatus())){
    			throw new BusinessException( "数据已经被修改！");
    		}
    		toCase.setStatus(CaseStatusEnum.YFD.getCode());
    		int reToCase = updateByPrimaryKey(toCase);
    		if(reToCase == 0)throw new BusinessException( "案件基本表更新失败！");
		}
		ToTransPlan record = new ToTransPlan();
		record.setCaseCode(caseCode);
		record.setPartCode(ToAttachmentEnum.FIRSTFOLLOW.getCode());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 2);
		record.setEstPartTime(cal.getTime());
		toTransPlanService.insertSelective(record);
		
		ToWorkFlow toWorkFlow = new ToWorkFlow();

		//启动流程引擎
    	ProcessInstance process = new ProcessInstance();
    	process.setBusinessKey(caseCode);
    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
    	//流程引擎相关
    	Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
    	User user = uamUserOrgService.getUserById(userId);
    	defValsMap.put("caseOwner", user.getUsername());
        StartProcessInstanceVo pIVo = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()), caseCode, defValsMap);

    	toWorkFlow.setInstCode(pIVo.getId());
    	toWorkFlow.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
    	toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
    	toWorkFlow.setProcessOwner(userId);
    	toWorkFlow.setCaseCode(toCase.getCaseCode());
    	toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
    	
    	toWorkFlowService.insertSelective(toWorkFlow);
		
	}
	@Override
	public void sendcaseAssignMsg(String caseCode,String userId,SessionUser sessionUser){
		String propAddrString = "";
		String agentMobile = "";
		String agentName = "";
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
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
	}

	@Override
	public void changeTaskAssignee(String caseCode, String taskId, String userId) {
		TaskVo task= workflowManager.getTask(taskId);
		/* 浦东合作顾问选中台纯公积金贷款流程除第一个环节外其他环节不需要重新派单 */
		SessionUser user = uamSessionService.getSessionUser();
		Org myDistrict = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode()); //获取执行任务变更的人所在的贵宾服务部
		User applyUser = uamUserOrgService.getUserById(userId);//下个纯公积金申请人信息
		
		/* 浦东合作顾问选中台纯公积金贷款流程除第一个环节外其他环节不需要重新派单  start */
		String taskDefinitionKey = task.getTaskDefinitionKey();
		if("FF5BC56E0E4B45289DAA5721A494C7C5".equals(myDistrict.getId())){
			if("PSFApply".equals(taskDefinitionKey)){
				/*更新纯公积金服务项目和经办人*/
				TgServItemAndProcessor tsiap = new TgServItemAndProcessor();
				tsiap.setCaseCode(caseCode);
				tsiap.setSrvCode("3000400201");
				tsiap = tgServItemAndProcessorService.findTgServItemAndProcessor(tsiap);
				if (tsiap != null) {
					tsiap.setProcessorId(applyUser.getId());
					tsiap.setOrgId(applyUser.getOrgId());
					tgServItemAndProcessorMapper.updateByPrimaryKey(tsiap);
				}
			}
			/*else{
				更新纯公积金服务项目和经办人
				TgServItemAndProcessor tsiap = new TgServItemAndProcessor();
				tsiap.setCaseCode(caseCode);
				tsiap.setSrvCode("3000401002");//交易过户（除签约外）
				tsiap = tgServItemAndProcessorService.findTgServItemAndProcessor(tsiap);
				if (tsiap != null) {
					tsiap.setProcessorId(applyUser.getId());
					tsiap.setOrgId(applyUser.getOrgId());
					tgServItemAndProcessorMapper.updateByPrimaryKey(tsiap);
				}
			}*/
		}/* end*/
		
		String username=uamUserOrgService.getUserById(userId).getUsername();
		if(StringUtils.isBlank(task.getAssignee())){
			workflowManager.doOptTaskPlan(task.getTaskDefinitionKey(), caseCode);
			TaskOperate opt=new TaskOperate(taskId, "claim"); 
			opt.setAssignee(username);
			workflowManager.operaterTask(opt);
		}else{
			taskReassingtLogService.record(task, username, caseCode);
			task.setAssignee(username);
			workflowManager.updateTask(task);
		}
	}
}