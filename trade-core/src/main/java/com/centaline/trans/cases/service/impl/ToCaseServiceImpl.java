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
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.repository.ToAttachmentMapper;
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
import com.centaline.trans.cases.vo.CaseResetVo;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.common.entity.CaseMergerParameter;
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
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.ConstantsUtil;
import com.centaline.trans.wdcase.entity.TdmPaidSubs;
import com.centaline.trans.wdcase.entity.TpdPayment;
import com.centaline.trans.wdcase.repository.TdmPaidSubsMapper;
import com.centaline.trans.wdcase.repository.TpdPaymentMapper;

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
	private ToCaseMergeMapper toCaseMergeMapper;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	@Autowired
	private ToWorkFlowService workflowService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToCaseCommentMapper toCaseCommentMapper;
	@Autowired
	private TgServItemAndProcessorMapper tgservItemAndProcessorMapper;
	@Autowired
	private TpdPaymentMapper tpdPaymentMapper;
	@Autowired
	private TdmPaidSubsMapper tdmPaidSubsMapper; 

	@Autowired
	private ToAttachmentMapper toAttachmentMapper; 
	@Autowired
	private ToSignMapper signMapper;
	
	@Override
	public int updateByPrimaryKey(ToCase record) {
		return toCaseMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToCase findToCaseByCaseCode(String caseCode) {
		return toCaseMapper.findToCaseByCaseCode(caseCode);
	}
	
	@Override
	public List<ToCase> findToCaseByStatus(String status){
		return toCaseMapper.findToCaseByStatus(status);
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
		
		// 交易助理
//		User user = uamUserOrgService.getUserById(toCase.getAssistantId());
//		if(user != null){
//			agentManagerInfo.setAsId(user.getId());
//			agentManagerInfo.setAsName(user.getRealName());
//			agentManagerInfo.setAsMobile(user.getMobile());
//		}
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(toCase.getOrgId(),
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
		
		ToSign sign= signMapper.findToSignByCaseCode(toCase.getCaseCode());
		
		CaseBaseVO caseBaseVO = new CaseBaseVO();
		caseBaseVO.setSign(sign);
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
    	toCaseInfo.setDispatchTime(new Date());
    	int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
    	if(reToCaseInfo == 0)throw new BusinessException( "案件信息表更新失败！");
    	
    	ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
    	
    	List<ToCaseMerge>  toCaseMergeList = null;
    	if(null != toPropertyInfo && !StringUtils.isBlank(toPropertyInfo.getPropertyCode())){
    		toCaseMergeList = toCaseMergeMapper.selectByPrimaryPropertyCode(toPropertyInfo.getPropertyCode());
    	}
    	String caseCode_=null;
    	if(null != toCaseMergeList)
    	for(ToCaseMerge toCaseMerge:toCaseMergeList){
    		if(null != toCase){
    			if(StringUtils.equals(toCaseMerge.getApplyStatus(),"1") ){
    				if(StringUtils.equals(toCaseMerge.getCaseCode(),caseCode)){
	    				caseCode_ = toCaseMerge.getcCaseCode();
	    				break;
    				}else{
    					caseCode_ = toCaseMerge.getCaseCode();
    					break;
    				}
    			}
    		}
    	}
    	
    	
		// 如果是无主案件分配,需要维护案件负责人
		if(toCase == null) {
			toCase = new ToCase();
			toCase.setCaseCode(caseCode);
			toCase.setCtmCode(toCaseInfo.getCtmCode());
			toCase.setCaseProperty(CasePropertyEnum.TPZT.getCode());
			toCase.setStatus(CaseStatusEnum.YJD.getCode());
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
    		if(!CaseStatusEnum.WJD.getCode().equals(toCase.getStatus())){
    			throw new BusinessException( "数据已经被修改！");
    		}
    		toCase.setStatus(CaseStatusEnum.YJD.getCode());
    		int reToCase = updateByPrimaryKey(toCase);
    		if(reToCase == 0)throw new BusinessException( "案件基本表更新失败！");
    		if(null !=caseCode_){
    			ToCase toCase_ = findToCaseByCaseCode(caseCode_);
    			if(null != toCase_){
	    			toCase_.setLeadingProcessId(userId);
	    			toCase_.setOrgId(orgId);
	    			int reToCase_ = updateByPrimaryKey(toCase_);
	        		if(reToCase_ == 0)throw new BusinessException( "案件基本表更新失败！");
    			}
    		}
    		
    		
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
		
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(toCase.getCaseCode());
		ToCaseInfo ctmtoCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(ctmToCase.getCaseCode());
		if(null != toCaseInfo){}else{throw new BusinessException("没有查询到自建案件详细信息!"); }
		if(null != ctmtoCaseInfo){}else{throw new BusinessException("没有查询到导入案件详细信息!"); }
		
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		ToPropertyInfo ctmtoPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(ctmToCase.getCaseCode());
		if(null != toPropertyInfo){}else{throw new BusinessException("没有查询到自建案件地址详细信息!"); }
		if(null != ctmtoPropertyInfo){}else{throw new BusinessException("没有查询到导入案件地址详细信息!"); }
		
		/**1.更新表t_to_case**/
		toCaseMapper.updateByPrimaryKeySelective(setUpdateToCase(user,toCase,ctmToCase,toCaseMerge));
		toCaseMapper.updateByPrimaryKeySelective(setUpdateCTMToCase(user,ctmToCase));
		/**2.更新表T_TO_CASE_INFO**/
		toCaseInfoMapper.updateByPrimaryKey(copyToCaseInfo(user,toCaseInfo,ctmtoCaseInfo));
		/**3.更新表T_TO_PROPERTY_INFO**/
		toPropertyInfoMapper.updateByPrimaryKeySelective(copyToPropertyInfo(user,toPropertyInfo,ctmtoPropertyInfo));
		/**4.更新T_TO_CASE_MERGE**/
		toCaseMergeMapper.updateByPrimaryKeySelective(setUpdateToCaseMerges(user,toCaseMerge));
		/**5.删除流程数据**/
		CaseResetVo vo = new CaseResetVo();vo.setCaseCode(ctmToCase.getCaseCode());
		reset(vo);
		/**6.ctm合流案件添加无效备注  T_TO_CASE_COMMENT **/
		if(StringUtils.equals(caseMergerParameter.getInputType(), "CTM")){
			toCaseCommentMapper.insertSelective(setToCaseComment(user,ctmToCase.getCaseCode()));
		}
	
	}
	
	/**
	 * 驳回合流申请
	 * @author hejf10
	 * @param caseMergerParameter
	 * @return
	 */
	public void turnMergeCase(SessionUser user,CaseMergerParameter caseMergerParameter) throws Exception{
		
		ToCaseMerge toCaseMerge = toCaseMergeMapper.selectByPrimaryKey(Long.valueOf(caseMergerParameter.getId()));
		
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(toCaseMerge.getCaseCode());
		ToCase ctmToCase = toCaseMapper.findToCaseByCaseCode(toCaseMerge.getcCaseCode());
		if(null != toCase){}else{throw new BusinessException("没有查询到自建案件信息!"); }
		if(null != ctmToCase){}else{throw new BusinessException("没有查询到导入案件信息!"); }
		if(null != toCaseMerge){}else{throw new BusinessException("没有查询到合流申请信息!"); }
		/** 1.更新合流表 **/
		toCaseMerge.setApplyStatus(CaseMergeStatusEnum.APPLYSTATUS2.getCode());
		toCaseMerge.setUpdateBy(user.getId());
		toCaseMerge.setUpdateTime(new Date());
		toCaseMergeMapper.updateByPrimaryKeySelective(toCaseMerge);
		/** 2.更新案件表 **/
		toCaseMapper.updateByPrimaryKeySelective(setToCaseturn(user,toCase,CaseMergeStatusEnum.INPUT.getCode()));
		toCaseMapper.updateByPrimaryKeySelective(setToCaseturn(user,ctmToCase,CaseMergeStatusEnum.CTM.getCode()));
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
		if(ctmToCase.getCreateTime().before(toCase.getCreateTime())){throw new BusinessException("导入案件创建时间必须在自建案件之后，请将此案件做无效处理!"); }
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
		toCaseMapper.updateByPrimaryKeySelective(setToCases(user,toCase));
		toCaseMapper.updateByPrimaryKeySelective(setToCases(user,ctmToCase));
		/**3.更新表T_TO_CASE_INFO**/
		ToCaseMerge toCaseMerge = setToCaseMerges(user,toCase,ctmToCase,toCaseInfo,ctmtoCaseInfo,toPropertyInfo,ctmtoPropertyInfo,caseMergerParameter.getInputType());
		if(null != toCaseMerge){toCaseMergeMapper.insertSelective(toCaseMerge);}
		if(StringUtils.equals(ctmToCase.getStatus(),CaseStatusEnum.WJD.getCode())||StringUtils.equals(caseMergerParameter.getInputType(), "CTM")){
			caseMergerParameter.setId(toCaseMerge.getPkid().toString());
			agreeMergeCase(user,caseMergerParameter);
		}
		
	}
	/**
	 * 拷贝T_TO_PROPERTY_INFO
	 * @param toPropertyInfo
	 * @param ctmtoPropertyInfo
	 * @return
	 */
	public ToPropertyInfo copyToPropertyInfo(SessionUser user,ToPropertyInfo toPropertyInfo,ToPropertyInfo ctmtoPropertyInfo){
		toPropertyInfo.setCtmCode(ctmtoPropertyInfo.getCtmCode());
		toPropertyInfo.setCtmAddr(ctmtoPropertyInfo.getCtmAddr());
		toPropertyInfo.setPropertyAgentId(ctmtoPropertyInfo.getPropertyAgentId());
		toPropertyInfo.setUpdateBy(user.getId());
		toPropertyInfo.setUpdateTime(new Date());
		return toPropertyInfo;
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
		toCaseInfo.setAgentUserName(ctmtoCaseInfo.getAgentUsername());
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
		if(!StringUtils.isBlank(ctmtoCaseInfo.getReferConsultantId()))
		toCaseInfo.setReferConsultantId(ctmtoCaseInfo.getReferConsultantId());
		if(!StringUtils.isBlank(ctmtoCaseInfo.getReferConsultantRealname()))
		toCaseInfo.setReferConsultantRealname(ctmtoCaseInfo.getReferConsultantRealname());
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
	public ToCaseMerge setToCaseMerges(SessionUser user,ToCase toCase,ToCase ctmToCase,ToCaseInfo toCaseInfo,ToCaseInfo ctmtoCaseInfo,ToPropertyInfo toPropertyInfo,ToPropertyInfo ctmtoPropertyInfo,String inputType){
		
		ToCaseMerge toCaseMerge = new ToCaseMerge();
		
		toCaseMerge.setCaseCode(toCase.getCaseCode());
		toCaseMerge.setcCaseCode(ctmToCase.getCaseCode());/**ctmcasecode**/
	    toCaseMerge.setCtmCode(ctmToCase.getCtmCode());
		toCaseMerge.setPropertyCode(ctmtoPropertyInfo.getPropertyCode());
		toCaseMerge.setPropertyAddr(ctmtoPropertyInfo.getPropertyAddr());
		toCaseMerge.setcAgentCode(ctmtoCaseInfo.getAgentCode());/**ctm**/
		toCaseMerge.setcAgentName(ctmtoCaseInfo.getAgentName());
		toCaseMerge.setcAgentPhone(ctmtoCaseInfo.getAgentPhone());
		toCaseMerge.setcAgentUsername(ctmtoCaseInfo.getAgentUsername());
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
		toCaseMerge.setAgentUsername(toCaseInfo.getAgentUsername());
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
		if(StringUtils.equals(inputType, "CTM")){
			toCaseMerge.setApplyDirection(CaseMergeStatusEnum.APPLY_DIRECTION1.getCode());
		}
		if(StringUtils.equals(inputType, "INPUT")){
			toCaseMerge.setApplyDirection(CaseMergeStatusEnum.APPLY_DIRECTION0.getCode());
		}
		toCaseMerge.setCreateBy(user.getId());
		toCaseMerge.setCreateTime(new Date());
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
		ctmToCase.setStatus(CaseStatusEnum.YZZ.getCode());
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
			if(StringUtils.isBlank(toCaseInfo.getReferConsultantId())){return null;}
			
			Map user = toCaseMapper.getUserIsMain(toCaseInfo.getReferConsultantId());
			if(null != user){ getVCaseDistributeUserVO(user,null,"map",vCaseDistributeUserVO,"CTM");/**CTM意向顾问**/
			}else{
				User user1 = uamUserOrgService.getUserById(toCaseInfo.getReferConsultantId()); 
				getVCaseDistributeUserVO(user,user1,"user",vCaseDistributeUserVO,"CTM");/**CTM意向顾问**/
			}
		}
		if(StringUtils.equals(toCase.getCaseOrigin(), "MERGE")){
			ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
			if(StringUtils.isBlank(toCaseInfo.getReferConsultantId())){return null;}
			
			Map user = toCaseMapper.getUserIsMain(toCaseInfo.getReferConsultantId());
			if(null != user){ getVCaseDistributeUserVO(user,null,"map",vCaseDistributeUserVO,"MERGE");/**MERGE意向顾问**/
			}else{
				User user1 = uamUserOrgService.getUserById(toCaseInfo.getReferConsultantId()); 
				getVCaseDistributeUserVO(user,user1,"user",vCaseDistributeUserVO,"MERGE");/**MERGE意向顾问**/
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
	
	/**
	 * 查询是否有可以合流的案件
	 * @author hejf10 2016-12-26 11:10:54
	 * @param propertyCodeList地址codeList
	 * @return
	 */
	@Override
	public String getMergeInfoList(List<ToPropertyInfo> toPropertyInfos) {
		String mergeInfoList = null;
		if(null != toPropertyInfos)
		for(ToPropertyInfo toPropertyInfo:toPropertyInfos){
			if(!StringUtils.isBlank(toPropertyInfo.getPropertyCode())){
				 Map cu = toCaseMergeMapper.getMergeInfoList(toPropertyInfo.getPropertyCode());/** 案件propertyCode **/
				 mergeInfoList ="true";
			}
		}
		return mergeInfoList;
	}
	/**
	 * 终止案件
	 * @author hejf10 2016-12-26 19:40:54
	 * @param caseResetVo 终止案件caseCode
	 * @param vo
	 */
	public void reset(CaseResetVo vo) {
		/**更新Workflow表为终止状态**/
		ToWorkFlow tf = new ToWorkFlow();
		tf.setCaseCode(vo.getCaseCode());
		List<ToWorkFlow> tfs = workflowService.queryActiveToWorkFlowByCaseCode(tf);
		if (null != tfs) {
			for (ToWorkFlow toWorkFlow : tfs) {
				toWorkFlow.setStatus(WorkFlowStatus.TERMINATE.getCode());/**流程终止状态(非正常结束)**/
				workflowService.updateByPrimaryKeySelective(toWorkFlow);
			}
		}
		/**将交易计划表的数据转移到交易计划历史表并删除交易计划表**/
		transplanServiceFacade.processRestartOrResetOperate(vo.getCaseCode(), ConstantsUtil.PROCESS_RESET);
		/**删除服务表**/
		tgServItemAndProcessorService.deleteByPrimaryCaseCode(vo.getCaseCode());
		/**无效掉表单数据**/
		workflowService.inActiveForm(vo.getCaseCode());
		/**删除流程引擎**/
		if(null != tfs){
			for (ToWorkFlow toWorkFlow : tfs) {
				try {
					unlocatedTaskService.deleteByInstCode(toWorkFlow.getInstCode());
					workflowManager.deleteProcess(toWorkFlow.getInstCode());
				} catch (WorkFlowException e) {
					if (!e.getMessage().contains("statusCode[404]")) throw e;
				}
			}
		}
		/**删除商贷流失预警信息**/
		/**bizWarnInfoService.deleteByCaseCode(vo.getCaseCode());**/ 
		/**流程重启更改掉案件临时银行的状态**/
		/**ToMortgage toMortgage = toMortgageService.getMortgageByCaseCode(vo.getCaseCode());
		if (toMortgage != null) { toMortgageService.updateTmpBankStatus(vo.getCaseCode()); }**/
	}
	
	/**
	 * 更新ToCase表值
	 * @author hejf10 2016-12-27 15:59:42
	 * @param user
	 * @param toCase
	 * @return
	 */
	public ToCase setToCases(SessionUser user,ToCase toCase){
		toCase.setCaseOrigin(CaseMergeStatusEnum.PROCESS.getCode());
		toCase.setUpdateBy(user.getId());
		toCase.setUpdateTime(new Date());
		return toCase;
	}
	/**
	 * 更新ToCase表值
	 * @author hejf10 2016-12-27 15:59:42
	 * @param user
	 * @param toCase
	 * @return
	 */
	public ToCase setToCaseturn(SessionUser user,ToCase toCase,String caseOriginType){
		toCase.setCaseOrigin(caseOriginType);
		toCase.setUpdateBy(user.getId());
		toCase.setUpdateTime(new Date());
		return toCase;
	}
	
	/**
	 * 拆分合流案件
	 * @author hejf10
	 * @param caseMergerParameter
	 * @return
	 */
	public void qfMergeCase(CaseMergerParameter caseMergerParameter) throws Exception{
		SessionUser user = uamSessionService.getSessionUser();
		if(null != user){}else{throw new BusinessException("没能获得当前用户信息!");}
		if(null != caseMergerParameter){
		if(StringUtils.isBlank(caseMergerParameter.getPkId())){throw new BusinessException("自建案件Pkid为空!"); }}
		
		String pkId =caseMergerParameter.getPkId();
		ToCase toCase = toCaseMapper.selectByPrimaryKey(Long.valueOf(pkId));
		
		if(null != toCase){}else{throw new BusinessException("没有查询到自建案件信息!"); }
		List<ToCaseMerge> toCaseMerges = toCaseMergeMapper.selectByPrimaryCaseCode(toCase.getCaseCode());
		
		if(null == toCaseMerges){throw new BusinessException("没有查询到合流案件信息!");}
		ToCaseMerge oldtoCaseMerge = toCaseMerges.get(0);
		if(StringUtils.isBlank(oldtoCaseMerge.getcCaseCode())){throw new BusinessException("ctm案件信息为空!");}
		ToCase ctmToCase = toCaseMapper.findToCaseByCaseCode(oldtoCaseMerge.getcCaseCode());
		
		if(null != ctmToCase){}else{throw new BusinessException("没有查询到导入案件信息!"); }
		
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(toCase.getCaseCode());
		ToCaseInfo ctmtoCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(ctmToCase.getCaseCode());
		
		if(null != toPropertyInfo){}else{throw new BusinessException("没有查询到案件地址信息!"); }
		
		/** 1.更新t_to_case_merge **/
		ToCaseMerge toCaseMerge = setToCaseMergesToQf(user,oldtoCaseMerge);
		if(null != toCaseMerge){toCaseMergeMapper.insertSelective(toCaseMerge);}
		
		/**2.更新t_to_case**/
		toCaseMapper.updateByPrimaryKeySelective(setToCasestoQf(user,toCase));
		toCaseMapper.updateByPrimaryKeySelective(setCToCasestoQf(user,ctmToCase));
		/**3.更新表T_TO_CASE_INFO**/
		toCaseInfoMapper.updateByPrimaryKey(copyToCaseInfotoQf(user,toCaseInfo,oldtoCaseMerge));
		toCaseInfoMapper.updateByPrimaryKey(copyCtmToCaseInfotoQf(user,ctmtoCaseInfo,oldtoCaseMerge));
		/**4.更新表T_TO_PROPERTY_INFO**/
		toPropertyInfoMapper.updateByPrimaryKeySelective(copyToPropertyInfotoQf(user,toPropertyInfo));
		
	}
	
	
	/**
	 * 设置ToCaseMerge表值(拆分)
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
	public ToCaseMerge setToCaseMergesToQf(SessionUser user,ToCaseMerge oldToCaseMerge){
		
		ToCaseMerge toCaseMerge = new ToCaseMerge();
		
		toCaseMerge.setCaseCode(oldToCaseMerge.getCaseCode());
		toCaseMerge.setcCaseCode(oldToCaseMerge.getcCaseCode());
	    toCaseMerge.setCtmCode(oldToCaseMerge.getCtmCode());
		toCaseMerge.setPropertyCode(oldToCaseMerge.getPropertyCode());
		toCaseMerge.setPropertyAddr(oldToCaseMerge.getPropertyAddr());
		toCaseMerge.setcAgentCode(oldToCaseMerge.getcAgentCode());
		toCaseMerge.setcAgentName(oldToCaseMerge.getcAgentName());
		toCaseMerge.setcAgentPhone(oldToCaseMerge.getcAgentPhone());
		toCaseMerge.setcAgentUsername(oldToCaseMerge.getcAgentUsername());
		toCaseMerge.setcAgentGrpCode(oldToCaseMerge.getcAgentGrpCode());
		toCaseMerge.setcAgentGrpName(oldToCaseMerge.getcAgentGrpName());
		toCaseMerge.setcAgentQyjlName(oldToCaseMerge.getcAgentQyjlName());
		toCaseMerge.setcAgentQydsName(oldToCaseMerge.getcAgentQydsName());
		toCaseMerge.setcAgentQyzjName(oldToCaseMerge.getcAgentQyzjName());
		toCaseMerge.setcAgentArCode(oldToCaseMerge.getcAgentArCode());
		toCaseMerge.setcAgentArName(oldToCaseMerge.getcAgentArName());
		toCaseMerge.setcAgentSwzCode(oldToCaseMerge.getcAgentSwzCode());
		toCaseMerge.setcAgentSwzName(oldToCaseMerge.getcAgentSwzName());
		toCaseMerge.setcAgentWzCode(oldToCaseMerge.getcAgentWzCode());
		toCaseMerge.setcAgentWzName(oldToCaseMerge.getcAgentWzName());
		toCaseMerge.setcAgentBaCode(oldToCaseMerge.getcAgentBaCode());
		toCaseMerge.setcAgentBaName(oldToCaseMerge.getcAgentBaName());
		toCaseMerge.setAgentCode(oldToCaseMerge.getAgentCode());//自建
		toCaseMerge.setAgentName(oldToCaseMerge.getAgentName());
		toCaseMerge.setAgentPhone(oldToCaseMerge.getAgentPhone());
		toCaseMerge.setAgentUsername(oldToCaseMerge.getAgentUsername());
		toCaseMerge.setAgentGrpCode(oldToCaseMerge.getAgentGrpCode());
		toCaseMerge.setAgentGrpName(oldToCaseMerge.getAgentGrpName());
		toCaseMerge.setAgentQyjlName(oldToCaseMerge.getAgentQyjlName());
		toCaseMerge.setAgentQydsName(oldToCaseMerge.getAgentQydsName());
		toCaseMerge.setAgentQyzjName(oldToCaseMerge.getAgentQyzjName());
		toCaseMerge.setAgentArCode(oldToCaseMerge.getAgentArCode());
		toCaseMerge.setAgentArName(oldToCaseMerge.getAgentArName());
		toCaseMerge.setAgentSwzCode(oldToCaseMerge.getAgentSwzCode());
		toCaseMerge.setAgentSwzName(oldToCaseMerge.getAgentSwzName());
		toCaseMerge.setAgentWzCode(oldToCaseMerge.getAgentWzCode());
		toCaseMerge.setAgentWzName(oldToCaseMerge.getAgentWzName());
		toCaseMerge.setAgentBaCode(oldToCaseMerge.getAgentBaCode());
		toCaseMerge.setAgentBaName(oldToCaseMerge.getAgentBaName());
		toCaseMerge.setApplierId(user.getId());
		toCaseMerge.setApplierOrgId(user.getServiceDepId());;
		toCaseMerge.setOperatorTime(new Date());
		toCaseMerge.setOperator(CaseMergeStatusEnum.OPERATOR2.getCode());
		toCaseMerge.setApplyStatus(CaseMergeStatusEnum.APPLYSTATUS1.getCode());
		toCaseMerge.setCreateBy(user.getId());
		toCaseMerge.setCreateTime(new Date());
		return toCaseMerge;
	}
	
	
	/**
	 * 更新ToCase表值(拆分)
	 * @author hejf10 2016-12-27 15:59:42
	 * @param user
	 * @param toCase
	 * @return
	 */
	public ToCase setToCasestoQf(SessionUser user,ToCase toCase){
		toCase.setCaseOrigin(CaseMergeStatusEnum.INPUT.getCode());
		toCase.setCtmCode("");
		toCase.setCaseProperty(CasePropertyEnum.TPZT.getCode()); 
		toCase.setUpdateBy(user.getId());
		toCase.setUpdateTime(new Date());
		return toCase;
	}
	/**
	 * 更新ToCase表值(拆分)
	 * @author hejf10 2016-12-27 15:59:42
	 * @param user
	 * @param toCase
	 * @return
	 */
	public ToCase setCToCasestoQf(SessionUser user,ToCase ctmtoCase){
		ctmtoCase.setCaseOrigin(CaseMergeStatusEnum.CTM.getCode());
		ctmtoCase.setStatus(CaseStatusEnum.WJD.getCode());
		ctmtoCase.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		ctmtoCase.setUpdateBy(user.getId());
		ctmtoCase.setUpdateTime(new Date());
		ctmtoCase.setLoanReq("");
		ctmtoCase.setLeadingProcessId("");
		ctmtoCase.setOrgId("");
		return ctmtoCase;
	}
	/**
	 * 拷贝ToCaseInfo(拆分)
	 * @param toCaseInfo
	 * @param ctmtoCaseInfo
	 * @return
	 */
	public ToCaseInfo copyToCaseInfotoQf(SessionUser user,ToCaseInfo toCaseInfo,ToCaseMerge toCaseMerge){
		toCaseInfo.setCtmCode("");
		toCaseInfo.setAgentCode(toCaseMerge.getAgentCode());
		toCaseInfo.setAgentName(toCaseMerge.getAgentName());
		toCaseInfo.setAgentPhone(toCaseMerge.getAgentPhone());
		toCaseInfo.setAgentUserName(toCaseMerge.getAgentUsername());
		toCaseInfo.setGrpCode(toCaseMerge.getAgentGrpCode());
		toCaseInfo.setGrpName(toCaseMerge.getAgentGrpName());
		toCaseInfo.setArCode(toCaseMerge.getAgentArCode());
		toCaseInfo.setArName(toCaseMerge.getAgentArName());
		toCaseInfo.setQjdsName(toCaseMerge.getAgentQydsName());
		toCaseInfo.setQyjlName(toCaseMerge.getAgentQyjlName());
		toCaseInfo.setQyzjName(toCaseMerge.getAgentQyzjName());
		toCaseInfo.setSwzCode(toCaseMerge.getAgentSwzCode());
		toCaseInfo.setSwzName(toCaseMerge.getAgentSwzName());
		toCaseInfo.setWzCode(toCaseMerge.getcAgentWzCode());
		toCaseInfo.setWzName(toCaseMerge.getAgentWzName());
		toCaseInfo.setBaCode(toCaseMerge.getAgentBaCode());
		toCaseInfo.setBaName(toCaseMerge.getAgentBaName());
		toCaseInfo.setUpdateby(user.getId());
		toCaseInfo.setUpdateTime(new Date());
		toCaseInfo.setReferConsultantId("");
		toCaseInfo.setReferConsultantRealname("");
		return toCaseInfo;
	}
	/**
	 * 拷贝ToCaseInfo(拆分)
	 * @param toCaseInfo
	 * @param ctmtoCaseInfo
	 * @return
	 */
	public ToCaseInfo copyCtmToCaseInfotoQf(SessionUser user,ToCaseInfo toCaseInfo,ToCaseMerge toCaseMerge){
		toCaseInfo.setUpdateby(user.getId());
		toCaseInfo.setUpdateTime(new Date());
		return toCaseInfo;
	}
	/**
	 * 拷贝T_TO_PROPERTY_INFO(拆分)
	 * @param toPropertyInfo
	 * @param ctmtoPropertyInfo
	 * @return
	 */
	public ToPropertyInfo copyToPropertyInfotoQf(SessionUser user,ToPropertyInfo toPropertyInfo){
		toPropertyInfo.setCtmCode("");
		toPropertyInfo.setCtmAddr("");
		toPropertyInfo.setPropertyAgentId("");
		toPropertyInfo.setUpdateBy(user.getId());
		toPropertyInfo.setUpdateTime(new Date());
		return toPropertyInfo;
	}
	@Override
	public String getManagerByCaseOwner(String caseCode) {
		return toCaseMapper.getManagerByCaseOwner(caseCode);
	}
	/**
	 * 设置ToCaseComment表值
	 * @author hejf10 2017-1-3 15:59:42
	 * @param user
	 * @param caseCode
	 * @return
	 */
	public ToCaseComment setToCaseComment(SessionUser user,String caseCode){
		ToCaseComment toCaseComment = new ToCaseComment();
		toCaseComment.setCaseCode(caseCode);
		toCaseComment.setComment("合流案件");
		toCaseComment.setCreateTime(new Date());
		toCaseComment.setCreateBy(user.getId());
		toCaseComment.setCreatorOrgId(user.getServiceDepId());
		return toCaseComment;
	}
	/**
	 * 服务编码[srv_code]和案件编号[case_code]到服务表[T_TG_SERV_ITEM_AND_PROCESSOR]中去查询交易顾问id[processor_id]
	 * @author hejf10 2017-3-16 13:55:58
	 * @param srvCode
	 * @param caseCode  
	 * @date 2017-3-16 13:55:58
	 * @return
	 */
	public String selectServItem(String caseCode,String srvCode){
		TgServItemAndProcessor tp = new TgServItemAndProcessor();
		String st = null;
		tp.setCaseCode(caseCode);
		tp.setSrvCode(srvCode);
		TgServItemAndProcessor tsip = tgservItemAndProcessorMapper.selectServItemandName(tp);
		if(null != tsip && !StringUtils.isBlank(tsip.getSrvName())){
			st = tsip.getSrvName();
		}
		return st;
	}
	/**
	 * 查询贷款流失申请书有没有提交
	 * @author hejf10 2017-3-16 13:55:58
	 * @param srvCode
	 * @param caseCode  
	 * @date 2017-3-16 13:55:58
	 * @return
	 */
	@Override
	public String selectAtt(String caseCode){
		
		int attCu = tgservItemAndProcessorMapper.selectAtt(caseCode);
		int mgCu = tgservItemAndProcessorMapper.selectMg(caseCode);
		if(mgCu > 0){
			if(attCu < 1){
				return "Y";
			}
		}
		return "N";
	}
	/**
	 * 删除流水
	 * @author hejf10
	 * @date 2017年6月9日18:08:03
	 * @param pkid
	 * @return
	 */
	@Override
	public void delLiushui(Long pkid) throws Exception{

		SessionUser user = uamSessionService.getSessionUser();
		
		TpdPayment tpdPayment = tpdPaymentMapper.selectByPrimaryKey(pkid);
		if(null == tpdPayment){new BusinessException("没有查询到相应的流水记录！");}
		
		TdmPaidSubs tdmPaidSubs = tdmPaidSubsMapper.selectByPaymentCode(tpdPayment.getPkid().toString());
		if(null == tdmPaidSubs){new BusinessException("没有查询到相应的流水记录！");}
		
		if(!StringUtils.equals(user.getId(), tpdPayment.getCreateBy())){ throw new BusinessException("只有创建人才能删除相应的流水记录！");}
		if(!StringUtils.equals(user.getId(), tdmPaidSubs.getCreateBy())){ throw new BusinessException("只有创建人才能删除相应的流水记录！");}
		
		tpdPayment.setIsDeleted(1);
		tpdPaymentMapper.updateByPrimaryKeySelective(tpdPayment);
		
		tdmPaidSubs.setIsDeleted(1);
		tdmPaidSubsMapper.updateByPrimaryKeySelective(tdmPaidSubs);
		
		if(!StringUtils.isEmpty(tdmPaidSubs.getReceiptPic())){
			String[] newstr = tdmPaidSubs.getReceiptPic().split(",");
			for(int i =0;i<newstr.length;i++){
				ToAttachment record = toAttachmentMapper.findToAttachmentByAdres(newstr[i]);
				if(null != record){
				toAttachmentMapper.updateToAttachmentForCaseCodeByAdres("N", newstr[i]);
				}
			}
		}
		
	}

	@Override
	public int updateAssistant(ToCase tocase) {
		return toCaseMapper.updateAssistant(tocase);
	}

}