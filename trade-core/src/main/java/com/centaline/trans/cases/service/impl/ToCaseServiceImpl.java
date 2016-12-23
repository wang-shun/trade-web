package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.redisson.liveobject.resolver.LongGenerator;
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
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToCaseMerge;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseMergeMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseMergeStatusEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.vo.AgentManagerInfo;
import com.centaline.trans.common.vo.BuyerSellerInfo;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.DateUtil;

import net.sf.jsqlparser.expression.LongValue;

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
	private TransplanServiceFacade transplanServiceFacade;
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
	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper;
	@Autowired
	private ToCaseMergeMapper toCaseMergeMapper;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	@Override
	public int updateByPrimaryKey(ToCase record) {
		return toCaseMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToCase findToCaseByCaseCode(String caseCode) {
		return toCaseMapper.findToCaseByCaseCode(caseCode);
	}
	@Override
	public int findToLoanAgentByCaseCode(String caseCode) {
		return toCaseMapper.findToLoanAgentByCaseCode(caseCode);
	}

	@Override
	public ToCase selectByPrimaryKey(Long pkid) {
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
		
		String orgId = null;
		// 接单数
		List<ToCaseInfoCountVo> toCaseInfoCountList = toCaseInfoService.countToCaseInfoListByOrgId(orgId);

		// ,签约
		List<ToCaseInfoCountVo> toSignCountList = toSpvService.countToSignListByOrgId(orgId);
		// ,过户
		List<ToCaseInfoCountVo> toHouseTransferCountList = toHouseTransferService
				.countToHouseTransferListByOrgId(orgId);
		// ,结案
		List<ToCaseInfoCountVo> toCloseCountList = toCloseService.countToCloseListByOrgId(orgId);

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
    	toCaseInfo.setTargetCode(org.getOrgCode());
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
		transplanServiceFacade.insertSelective(record);
		
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
    	toWorkFlow.setBizCode(toCase.getCaseCode());
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
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
		User oldUser =  uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		
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
					tsiap.setPreProcessorId(oldUser.getId());
					tsiap.setPreOrgId(oldUser.getOrgId());
					tsiap.setProcessorId(applyUser.getId());
					tsiap.setOrgId(applyUser.getOrgId());
					tgServItemAndProcessorMapper.updateByPrimaryKey(tsiap);
				}
			}
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
	/**
	 * 合流
	 * @author hejf10
	 * @param caseMergerParameter
	 * @return
	 */
	@Override
	public void updateMergeCase(CaseMergerParameter caseMergerParameter) throws Exception{
		SessionUser user = uamSessionService.getSessionUser();
		if(null != caseMergerParameter){}else{new BusinessException("合流案件记录为空!");}
		if(StringUtils.isBlank(caseMergerParameter.getId())){throw new BusinessException("合流记录id为空!"); }
		if(StringUtils.isBlank(caseMergerParameter.getType())){ throw new BusinessException("操作状态为空!"); }
		/**1确认0驳回**/
		if(StringUtils.equals(caseMergerParameter.getType(),CaseMergeStatusEnum.TYPE1.getCode())){ agreeMergeCase(user,caseMergerParameter); }
		if(StringUtils.equals(caseMergerParameter.getType(),CaseMergeStatusEnum.TYPE0.getCode())){ turnMergeCase(user,caseMergerParameter); }
	}
	
	/**
	 * 合流
	 * @author hejf10
	 * @param caseMergerParameter
	 * @return
	 */
	public void agreeMergeCase(SessionUser user,CaseMergerParameter caseMergerParameter) throws Exception{

		ToCaseMerge toCaseMerge = toCaseMergeMapper.selectByPrimaryKey(Long.valueOf(caseMergerParameter.getId()));
		if(null != toCaseMerge){}else{new BusinessException("合流案件记录为空!");}
		if(StringUtils.isBlank(toCaseMerge.getCaseCode())){throw new BusinessException("自建案件CaseCode为空!"); }
		if(StringUtils.isBlank(toCaseMerge.getcCaseCode())){throw new BusinessException("ctm案件CaseCode为空!"); }
		
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(toCaseMerge.getCaseCode());
		ToCase ctmToCase = toCaseMapper.findToCaseByCaseCode(toCaseMerge.getcCaseCode());
		if(null != toCase){}else{throw new BusinessException("没有查询到自建案件信息!"); }
		if(null != ctmToCase){}else{throw new BusinessException("没有查询到导入案件信息!"); }
		if(StringUtils.equals(toCase.getCaseCode(),ctmToCase.getCaseCode())){throw new BusinessException("同一案件不能进行合并!"); }
		if(StringUtils.equals(ctmToCase.getCaseProperty(),CasePropertyEnum.TPWX.getCode())){throw new BusinessException("无效案件不能进行合并!"); }
		
		//List<TgGuestInfo> toCaseTgList = tgGuestInfoMapper.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		//List<TgGuestInfo> ctmToCaseTgList = tgGuestInfoMapper.findTgGuestInfoByCaseCode(ctmToCase.getCaseCode());
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(toCase.getCaseCode());
		ToCaseInfo ctmtoCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(ctmToCase.getCaseCode());
		if(null != toCaseInfo){}else{throw new BusinessException("没有查询到自建案件详细信息!"); }
		if(null != ctmtoCaseInfo){}else{throw new BusinessException("没有查询到导入案件详细信息!"); }
		/**1.更新表t_to_case**/
		toCaseMapper.updateByPrimaryKeySelective(setUpdateToCase(user,toCase,ctmToCase,toCaseMerge));
		toCaseMapper.updateByPrimaryKeySelective(setUpdateCTMToCase(user,ctmToCase));
		/**2.更新表T_TO_CASE_INFO**/
		toCaseInfoMapper.updateByPrimaryKey(copyToCaseInfo(user,toCaseInfo,ctmtoCaseInfo));
		/**3.更新T_TO_CASE_MERGE**/
		toCaseMergeMapper.updateByPrimaryKeySelective(setUpdateToCaseMerges(user,toCaseMerge));
	}
	
	/**
	 * 驳回合流申请
	 * @author hejf10
	 * @param caseMergerParameter
	 * @return
	 */
	public void turnMergeCase(SessionUser user,CaseMergerParameter caseMergerParameter) throws Exception{
		
		ToCaseMerge toCaseMerge = toCaseMergeMapper.selectByPrimaryKey(Long.valueOf(caseMergerParameter.getId()));
		if(null != toCaseMerge){}else{throw new BusinessException("没有查询到合流申请信息!"); }
		toCaseMerge.setApplyStatus(CaseMergeStatusEnum.APPLYSTATUS2.getCode());
		toCaseMerge.setUpdateBy(user.getId());
		toCaseMerge.setUpdateTime(new Date());
		toCaseMergeMapper.updateByPrimaryKeySelective(toCaseMerge);
	}
	
	/**
	 * 设置合流申请
	 * @author hejf10
	 * @param caseMergerParameter
	 * @return
	 */
	public void mergeCase(CaseMergerParameter caseMergerParameter) throws Exception{
		SessionUser user = uamSessionService.getSessionUser();
		if(null != user){}else{throw new BusinessException("没能获得当前用户信息!");}
		if(null != caseMergerParameter){
		if(StringUtils.isBlank(caseMergerParameter.getPkId())){throw new BusinessException("自建案件Pkid为空!"); }
		if(StringUtils.isBlank(caseMergerParameter.getMergePkid())){throw new BusinessException("ctm案件PkId为空!"); } }else{new BusinessException("合流案件为空!");}
		
		String pkId =caseMergerParameter.getPkId();
		String ctmPkId =caseMergerParameter.getMergePkid();
		ToCase toCase = toCaseMapper.selectByPrimaryKey(Long.valueOf(pkId));
		ToCase ctmToCase = toCaseMapper.selectByPrimaryKey(Long.valueOf(ctmPkId));
		
		if(null != toCase){}else{throw new BusinessException("没有查询到自建案件信息!"); }
		if(null != ctmToCase){}else{throw new BusinessException("没有查询到导入案件信息!"); }
		if(null != toCase.getCreateTime()){}else{throw new BusinessException("自建案件创建时间为空，不支持合流!"); }
		if(null != ctmToCase.getCreateTime()){}else{throw new BusinessException("导入案件创建时间为空，不支持合流!"); }
		if(ctmToCase.getCreateTime().before(toCase.getCreateTime())){throw new BusinessException("导入案件创建时间必须在自建案件之后!"); }
		if(StringUtils.isBlank(ctmToCase.getCaseCode())){throw new BusinessException("ctm案件CaseCode为空!"); }
		if(StringUtils.isBlank(toCase.getCaseCode())){throw new BusinessException("自建案件CaseCode为空!"); }
		if(StringUtils.equals(toCase.getCaseCode(),ctmToCase.getCaseCode())){throw new BusinessException("同一案件不能进行合并!"); }
		
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		ToPropertyInfo ctmtoPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(ctmToCase.getCaseCode());
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(toCase.getCaseCode());
		ToCaseInfo ctmtoCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(ctmToCase.getCaseCode());
		
		if(null != toPropertyInfo){}else{throw new BusinessException("没有查询到自建案件地址信息!"); }
		if(null != ctmtoPropertyInfo){}else{throw new BusinessException("没有查询到导入案件地址信息!"); }
		if(null != toCaseInfo){}else{throw new BusinessException("没有查询到自建案件详细信息!"); }
		if(null != ctmtoCaseInfo){}else{throw new BusinessException("没有查询到导入案件详细信息!"); }
		if(!StringUtils.equals(ctmtoPropertyInfo.getPropertyCode(),toPropertyInfo.getPropertyCode())){throw new BusinessException("PropertyCode不相同的案件不能合流！"); }
		if(StringUtils.equals(toCase.getCaseOrigin(), CaseMergeStatusEnum.PROCESS.getCode())){throw new BusinessException("已经在申请中的自建案件不能再次申请！"); }
		if(StringUtils.equals(ctmToCase.getCaseOrigin(), CaseMergeStatusEnum.PROCESS.getCode())){throw new BusinessException("已经在申请中的ctm案件不能再次申请！"); }
		if(StringUtils.isBlank(toCase.getCaseCode())){throw new BusinessException("自建案件CaseCode为空!"); }
		if(StringUtils.isBlank(ctmToCase.getCtmCode())){throw new BusinessException("ctm案件CtmCode为空!"); }
		if(StringUtils.isBlank(ctmtoPropertyInfo.getPropertyCode())){throw new BusinessException("ctm案件PropertyCode为空!"); }
		if(StringUtils.isBlank(ctmtoPropertyInfo.getPropertyAddr())){throw new BusinessException("ctm案件PropertyAddr为空!"); }
		/**2.更新t_to_case**/
		toCase.setCaseOrigin(CaseMergeStatusEnum.PROCESS.getCode());
		toCaseMapper.updateByPrimaryKeySelective(toCase);
		/**3.更新表T_TO_CASE_INFO**/
		ToCaseMerge toCaseMerge = setToCaseMerges(user,toCase,ctmToCase,toCaseInfo,ctmtoCaseInfo,toPropertyInfo,ctmtoPropertyInfo);
		if(null != toCaseMerge){toCaseMergeMapper.insertSelective(toCaseMerge);}
		if(StringUtils.equals(ctmToCase.getStatus(),CaseStatusEnum.WFD.getCode())){
			caseMergerParameter.setId(toCaseMerge.getPkid().toString());
			agreeMergeCase(user,caseMergerParameter);
		}
		
	}
	/**
	 * 拷贝ToCaseInfo
	 * @param toCaseInfo
	 * @param ctmtoCaseInfo
	 * @return
	 */
	public ToCaseInfo copyToCaseInfo(SessionUser user,ToCaseInfo toCaseInfo,ToCaseInfo ctmtoCaseInfo){
		toCaseInfo.setCtmCode(ctmtoCaseInfo.getCtmCode());
		toCaseInfo.setAgentCode(ctmtoCaseInfo.getAgentCode());
		toCaseInfo.setAgentName(ctmtoCaseInfo.getAgentName());
		toCaseInfo.setAgentPhone(ctmtoCaseInfo.getAgentPhone());
		toCaseInfo.setAgentUserName(ctmtoCaseInfo.getAgentUserName());
		toCaseInfo.setGrpCode(ctmtoCaseInfo.getGrpCode());
		toCaseInfo.setGrpName(ctmtoCaseInfo.getGrpName());
		toCaseInfo.setArCode(ctmtoCaseInfo.getArCode());
		toCaseInfo.setArName(ctmtoCaseInfo.getArName());
		toCaseInfo.setQjdsName(ctmtoCaseInfo.getQjdsName());
		toCaseInfo.setQyjlName(ctmtoCaseInfo.getQyjlName());
		toCaseInfo.setQyzjName(ctmtoCaseInfo.getQyzjName());
		toCaseInfo.setSwzCode(ctmtoCaseInfo.getSwzCode());
		toCaseInfo.setSwzName(ctmtoCaseInfo.getSwzName());
		toCaseInfo.setWzCode(ctmtoCaseInfo.getWzCode());
		toCaseInfo.setWzName(ctmtoCaseInfo.getWzName());
		toCaseInfo.setBaCode(ctmtoCaseInfo.getBaCode());
		toCaseInfo.setBaName(ctmtoCaseInfo.getBaName());
		toCaseInfo.setUpdateby(user.getId());
		toCaseInfo.setUpdateTime(new Date());
		return toCaseInfo;
	}
	
	/**
	 * 设置ToCaseMerge表值
	 * @author hejf10
	 * @param user
	 * @param toCase
	 * @param ctmToCase
	 * @param toCaseInfo
	 * @param ctmtoCaseInfo
	 * @param toPropertyInfo
	 * @param ctmtoPropertyInfo
	 * @return
	 */
	public ToCaseMerge setToCaseMerges(SessionUser user,ToCase toCase,ToCase ctmToCase,ToCaseInfo toCaseInfo,ToCaseInfo ctmtoCaseInfo,ToPropertyInfo toPropertyInfo,ToPropertyInfo ctmtoPropertyInfo){
		
		ToCaseMerge toCaseMerge = new ToCaseMerge();
		
		toCaseMerge.setCaseCode(toCase.getCaseCode());
		toCaseMerge.setcCaseCode(ctmToCase.getCaseCode());/**ctmcasecode**/
	    toCaseMerge.setCtmCode(ctmToCase.getCtmCode());
		toCaseMerge.setPropertyCode(ctmtoPropertyInfo.getPropertyCode());
		toCaseMerge.setPropertyAddr(ctmtoPropertyInfo.getPropertyAddr());
		toCaseMerge.setcAgentCode(ctmtoCaseInfo.getAgentCode());/**ctm**/
		toCaseMerge.setcAgentName(ctmtoCaseInfo.getAgentName());
		toCaseMerge.setcAgentPhone(ctmtoCaseInfo.getAgentPhone());
		toCaseMerge.setcAgentUsername(ctmtoCaseInfo.getAgentUserName());
		toCaseMerge.setcAgentGrpCode(ctmtoCaseInfo.getGrpCode());
		toCaseMerge.setcAgentGrpName(ctmtoCaseInfo.getGrpName());
		toCaseMerge.setcAgentQyjlName(ctmtoCaseInfo.getQyjlName());
		toCaseMerge.setcAgentQydsName(ctmtoCaseInfo.getQjdsName());
		toCaseMerge.setcAgentQyzjName(ctmtoCaseInfo.getQyzjName());
		toCaseMerge.setcAgentArCode(ctmtoCaseInfo.getArCode());
		toCaseMerge.setcAgentArName(ctmtoCaseInfo.getAgentName());
		toCaseMerge.setcAgentSwzCode(ctmtoCaseInfo.getSwzCode());
		toCaseMerge.setcAgentSwzName(ctmtoCaseInfo.getSwzName());
		toCaseMerge.setcAgentWzCode(ctmtoCaseInfo.getWzCode());
		toCaseMerge.setcAgentWzName(ctmtoCaseInfo.getWzName());
		toCaseMerge.setcAgentBaCode(ctmtoCaseInfo.getBaCode());
		toCaseMerge.setcAgentBaName(ctmtoCaseInfo.getBaName());
		toCaseMerge.setAgentCode(toCaseInfo.getAgentCode());//自建
		toCaseMerge.setAgentName(toCaseInfo.getAgentName());
		toCaseMerge.setAgentPhone(toCaseInfo.getAgentPhone());
		toCaseMerge.setAgentUsername(toCaseInfo.getAgentUserName());
		toCaseMerge.setAgentGrpCode(toCaseInfo.getGrpCode());
		toCaseMerge.setAgentGrpName(toCaseInfo.getGrpName());
		toCaseMerge.setAgentQyjlName(toCaseInfo.getQyjlName());
		toCaseMerge.setAgentQydsName(toCaseInfo.getQjdsName());
		toCaseMerge.setAgentQyzjName(toCaseInfo.getQyzjName());
		toCaseMerge.setAgentArCode(toCaseInfo.getArCode());
		toCaseMerge.setAgentArName(toCaseInfo.getAgentName());
		toCaseMerge.setAgentSwzCode(toCaseInfo.getSwzCode());
		toCaseMerge.setAgentSwzName(toCaseInfo.getSwzName());
		toCaseMerge.setAgentWzCode(toCaseInfo.getWzCode());
		toCaseMerge.setAgentWzName(toCaseInfo.getWzName());
		toCaseMerge.setAgentBaCode(toCaseInfo.getBaCode());
		toCaseMerge.setAgentBaName(toCaseInfo.getBaName());
		toCaseMerge.setApplierId(user.getId());
		toCaseMerge.setApplierOrgId(user.getServiceDepId());;
		toCaseMerge.setOperatorTime(new Date());
		toCaseMerge.setOperator(CaseMergeStatusEnum.OPERATOR1.getCode());
		toCaseMerge.setApplyStatus(CaseMergeStatusEnum.APPLYSTATUS0.getCode());
		/**toCaseMerge.setConfirmorId("");**/
		/**toCaseMerge.setConfirmorOrgId("");**/
		/**toCaseMerge.setConfirmorTime(new Date);**/
		toCaseMerge.setApplyDirection(CaseMergeStatusEnum.APPLY_DIRECTION0.getCode());
		toCaseMerge.setCreateBy(user.getId());
		toCaseMerge.setCreateTime(new Date());
		/**toCaseMerge.setUpdateBy("");**/
		/**toCaseMerge.setUpdateTime("");**/
		return toCaseMerge;
	}
	/**
	 * 设置ToCase表值
	 * @author hejf10
	 * @return
	 */
	public ToCase setUpdateToCase(SessionUser user,ToCase toCase,ToCase ctmToCase,ToCaseMerge toCaseMerge){
		toCase.setCaseOrigin(CaseMergeStatusEnum.MERGE.getCode());
		toCase.setCtmCode(toCaseMerge.getCtmCode());
		toCase.setCaseProperty(CaseMergeStatusEnum.CASE_PROPERTY3.getCode());
		toCase.setLeadingProcessId(ctmToCase.getLeadingProcessId());
		toCase.setOrgId(ctmToCase.getOrgId());
		toCase.setUpdateBy(user.getId());
		toCase.setUpdateTime(new Date());
		return toCase;
	}
	/**
	 * 设置ToCase表值
	 * @author hejf10
	 * @return
	 */
	public ToCase setUpdateCTMToCase(SessionUser user,ToCase ctmToCase){
		ctmToCase.setCaseOrigin(CaseMergeStatusEnum.MERGE.getCode());
		ctmToCase.setCaseProperty(CasePropertyEnum.TPWX.getCode());
		ctmToCase.setUpdateBy(user.getId());
		ctmToCase.setUpdateTime(new Date());
		return ctmToCase;
	}
	/**
	 * 设置ToCaseMerge表值
	 * @author hejf10
	 * @return
	 */
	public ToCaseMerge setUpdateToCaseMerges(SessionUser user,ToCaseMerge toCaseMerge){
		toCaseMerge.setApplyStatus(CaseMergeStatusEnum.APPLYSTATUS1.getCode());
		toCaseMerge.setUpdateBy(user.getId());
		toCaseMerge.setUpdateTime(new Date());
		toCaseMerge.setConfirmorId(user.getId());
		toCaseMerge.setConfirmorOrgId(user.getServiceDepId());
		toCaseMerge.setConfirmorTime(new Date());
		toCaseMerge.setApplyDirection(CaseMergeStatusEnum.APPLY_DIRECTION0.getCode());
		return toCaseMerge;
	}
	/**
	 * 查询GetToCase vCaseDistributeUserVO中Type为all在页面中用来显示全部交易顾问 INPUT创建人和CTM意向顾问
	 * @author hejf10
	 * @return
	 */
	@Override
	public VCaseDistributeUserVO getVCaseDistributeUserVO(String caseCode) {
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
		VCaseDistributeUserVO vCaseDistributeUserVO = new VCaseDistributeUserVO();
		if(null != toCase){}else{return null;}
		if(StringUtils.equals(toCase.getCaseOrigin(), "INPUT")){
			if(StringUtils.isBlank(toCase.getCreateBy())){return null;}
			Map user = toCaseMapper.getUserIsMain(toCase.getCreateBy());
			if(null != user){ getVCaseDistributeUserVO(user,null,"map",vCaseDistributeUserVO,"INPUT");/**INPUT创建人**/
			}else{
				User user1 = uamUserOrgService.getUserById(toCase.getCreateBy()); 
				getVCaseDistributeUserVO(user,user1,"user",vCaseDistributeUserVO,"CTM");/**INPUT创建人**/
			}
		}    
		if(StringUtils.equals(toCase.getCaseOrigin(), "CTM")){
			ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
			if(StringUtils.isBlank(toCaseInfo.getRequireProcessorId())){return null;}
			
			Map user = toCaseMapper.getUserIsMain(toCaseInfo.getRequireProcessorId());
			if(null != user){ getVCaseDistributeUserVO(user,null,"map",vCaseDistributeUserVO,"CTM");/**CTM意向顾问**/
			}else{
				User user1 = uamUserOrgService.getUserById(toCaseInfo.getRequireProcessorId()); 
				getVCaseDistributeUserVO(user,user1,"user",vCaseDistributeUserVO,"CTM");/**CTM意向顾问**/
			}
		}
		return vCaseDistributeUserVO;
	}
	/**
	 * 返回VCaseDistributeUserVO dataType为user时设置VCaseDistributeUserVO的值根据查询出来的用户信息进行设置，
	 * 如果datatype为map时设置VCaseDistributeUserVO的信息用Map设置
	 * @author hejf10
	 * @return
	 */
	public VCaseDistributeUserVO getVCaseDistributeUserVO(Map user,User user1,String dataType,VCaseDistributeUserVO vCaseDistributeUserVO,String type) {
		vCaseDistributeUserVO.setType(type);
		
		if(StringUtils.equals(dataType,"user")){
			if(null != user1){
				if(!StringUtils.isBlank(user1.getMobile())) vCaseDistributeUserVO.setMobile(user1.getMobile());
				if(!StringUtils.isBlank(user1.getRealName())) vCaseDistributeUserVO.setRealName(user1.getRealName());
				if(!StringUtils.isBlank(user1.getEmployeeCode())){
					String url = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+user1.getEmployeeCode()+".jpg";
					vCaseDistributeUserVO.setImgUrl(url);
				}
				vCaseDistributeUserVO.setJobName("");
				vCaseDistributeUserVO.setOrgName("");
			}
		}else{
			vCaseDistributeUserVO.setOrgName(user.get("ORG_NAME").toString());
			vCaseDistributeUserVO.setJobName(user.get("JOB_NAME").toString());
			vCaseDistributeUserVO.setMobile(user.get("MOBILE").toString());
			vCaseDistributeUserVO.setRealName(user.get("REAL_NAME").toString());
			String url = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+user.get("EMPLOYEE_CODE").toString()+".jpg";
			vCaseDistributeUserVO.setImgUrl(url);
		}
		return vCaseDistributeUserVO;
	}
	
}








