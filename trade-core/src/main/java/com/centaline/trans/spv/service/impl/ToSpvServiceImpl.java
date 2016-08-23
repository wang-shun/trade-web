package com.centaline.trans.spv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAccount;
import com.centaline.trans.spv.entity.ToSpvCust;
import com.centaline.trans.spv.entity.ToSpvDe;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeDetail;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.entity.ToSpvProperty;
import com.centaline.trans.spv.enums.CashDirectionEnum;
import com.centaline.trans.spv.enums.SpvExceptionEnum;
import com.centaline.trans.spv.enums.SpvTypeEnum;
import com.centaline.trans.spv.repository.ToCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvAccountMapper;
import com.centaline.trans.spv.repository.ToSpvCustMapper;
import com.centaline.trans.spv.repository.ToSpvDeCondMapper;
import com.centaline.trans.spv.repository.ToSpvDeDetailMapper;
import com.centaline.trans.spv.repository.ToSpvDeMapper;
import com.centaline.trans.spv.repository.ToSpvDeRecMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.repository.ToSpvPropertyMapper;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.spv.vo.SpvVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Service
public class ToSpvServiceImpl implements ToSpvService {

    @Autowired
    private ToSpvMapper toSpvMapper;
    @Autowired
    private ToCashFlowMapper toCashFlowMapper;
    
    @Autowired
    private ToSpvDeCondMapper toSpvDeCondMapper;
    
    @Autowired
    private ToSpvDeRecMapper toSpvDeRecMapper;
    
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
    
    @Autowired
    private ToCaseService toCaseService;
    
    @Autowired
    private WorkFlowManager workFlowManager;
    
    @Autowired
    private ToWorkFlowService toWorkFlowService;
    
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	
	@Autowired
	private ProcessInstanceService processInstanceService; 
	@Autowired
	private TaskService taskService; 
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private ToSpvCustMapper toSpvCustMapper;
	@Autowired
	private ToSpvAccountMapper toSpvAccountMapper;
	@Autowired
	private ToSpvDeMapper toSpvDeMapper;
	@Autowired
	private ToSpvDeDetailMapper toSpvDeDetailMapper;
	@Autowired
	private ToSpvPropertyMapper toSpvPropertyMapper;
	
    /**
     * 房款监管签约记录
     */
	@Override
	public ToSpv queryToSpvByCaseCode(String caseCode) {
		return toSpvMapper.queryToSpvByCaseCode(caseCode);
	}

    /**
     * 房款进出账
     */
	@Override
	public List<ToCashFlow> queryCashFlowsByCaseCode(String caseCode) {
		return toCashFlowMapper.queryCashFlowsByCaseCode(caseCode);
	}
	
	public List<ToCashFlow> findCashFlowByCaseCodeAndDirection(String caseCode,String direction) {
		return toCashFlowMapper.findCashFlowByCaseCodeAndDirection(caseCode,direction);
	}
	@Override
	public void saveToSpv(SpvVo spvVo) {
		ToSpv toSpv = spvVo.getToSpv();
		ToSpv spv = toSpvMapper.findToSpvBySpvCode(toSpv.getSpvCode());
		
		if(toSpv.getPkid() != null){
			toSpv.setAmount(toSpv.getAmount()!=null?toSpv.getAmount().multiply(new BigDecimal(10000)):null);
			toSpvMapper.updateByPrimaryKeyWithBLOBs(toSpv);
		}else{
			if(spv != null){
				throw new BusinessException(SpvExceptionEnum.SPV_CODE_EXIST.getValue());
			}
			toSpv.setAmount(toSpv.getAmount()!=null?toSpv.getAmount().multiply(new BigDecimal(10000)):null);
			toSpv.setSpvType(SpvTypeEnum.ANXINBAO.getCode());
			toSpvMapper.insertSelective(toSpv);
		}
		//保存解除条件
		List<ToSpvDeCond> toSpvDeCondList = spvVo.getToSpvDeCondList();
		if(CollectionUtils.isNotEmpty(toSpvDeCondList)){
			for(ToSpvDeCond entity : toSpvDeCondList){
				entity.setDeAmount(entity.getDeAmount()!=null?entity.getDeAmount().multiply(new BigDecimal(10000)):null);
				entity.setSpvCode(toSpv.getSpvCode());
				if(entity.getPkid() != null){
					toSpvDeCondMapper.updateByPrimaryKeySelective(entity);
				}else{
					toSpvDeCondMapper.insertSelective(entity);
				}
			}
		}
		//删除解除条件
		String[] delIds = spvVo.getDelIds();
		if(delIds != null && delIds.length > 0){
			for(String delId : delIds){
				toSpvDeCondMapper.deleteByPrimaryKey(Long.parseLong(delId));
			}
		}
		
		//保存进账信息
		ToCashFlow toCashFlow = spvVo.getToCashFlow();
		if(toCashFlow != null){
			toCashFlow.setFlowAmount(toCashFlow.getFlowAmount()!=null?toCashFlow.getFlowAmount().multiply(new BigDecimal(10000)):null);
			if(toCashFlow.getPkid() != null){
				toCashFlowMapper.updateByPrimaryKeySelective(toCashFlow);
			}else{
				toCashFlow.setFlowDirection("0");
				toCashFlow.setCaseCode(toSpv.getCaseCode());
				toCashFlow.setCashFlowType(SpvTypeEnum.ANXINBAO.getCode());
				toCashFlowMapper.insertSelective(toCashFlow);
			}
		}
		
		//保存出账信息
		List<ToCashFlow> toCashFlowList = spvVo.getToCashFlowList();
		if(CollectionUtils.isNotEmpty(toCashFlowList)){
			for(ToCashFlow entity : toCashFlowList){
				entity.setFlowAmount(entity.getFlowAmount()!=null?entity.getFlowAmount().multiply(new BigDecimal(10000)):new BigDecimal(0));
				entity.setCaseCode(toSpv.getCaseCode());
				if(entity.getPkid() != null){
					toCashFlowMapper.updateByPrimaryKeySelective(entity);
				}else{
					entity.setCaseCode(toSpv.getCaseCode());
					entity.setCashFlowType(SpvTypeEnum.ANXINBAO.getCode());
					entity.setFlowDirection("1");
					toCashFlowMapper.insertSelective(entity);
				}
			}
		}
		//删除出账信息
		String[] delFlowIds = spvVo.getDelFlowIds();
		if(delFlowIds != null && delFlowIds.length > 0){
			for(String delId : delFlowIds){
				toCashFlowMapper.deleteByPrimaryKey(Long.parseLong(delId));
			}
		}
	}

	@Override
	public SpvVo findByCaseCode(String caseCode) {
		SpvVo spvVo = new SpvVo();
		ToSpv toSpv = toSpvMapper.queryToSpvByCaseCode(caseCode);
		if(toSpv == null){
			return null;
		}
		toSpv.setAmount(toSpv.getAmount()!=null?toSpv.getAmount().divide(new BigDecimal(10000)):null);
		spvVo.setToSpv(toSpv);
		List<ToSpvDeCond> toSpvDeCondList = toSpvDeCondMapper.findBySpvCode(toSpv.getSpvCode());
		if(CollectionUtils.isNotEmpty(toSpvDeCondList)){
			for(ToSpvDeCond toSpvDeCond : toSpvDeCondList){
				toSpvDeCond.setDeAmount(toSpvDeCond.getDeAmount()!=null?toSpvDeCond.getDeAmount().divide(new BigDecimal(10000)):null);
			}
		}
		spvVo.setToSpvDeCondList(toSpvDeCondList);
		
		//查询出账信息
		List<ToCashFlow> toCashFlowList = findCashFlowByCaseCodeAndDirection(caseCode,CashDirectionEnum.OUT.getCode());
		if(CollectionUtils.isNotEmpty(toCashFlowList)){
			for(ToCashFlow toCashFlow : toCashFlowList){
				toCashFlow.setFlowAmount(toCashFlow.getFlowAmount()!=null?toCashFlow.getFlowAmount().divide(new BigDecimal(10000)):null);
			}
		}
		spvVo.setToCashFlowList(toCashFlowList);
		
		//查询进账信息
		List<ToCashFlow> inCashFlow = findCashFlowByCaseCodeAndDirection(caseCode,CashDirectionEnum.IN.getCode());
		if(CollectionUtils.isNotEmpty(inCashFlow)){
			inCashFlow.get(0).setFlowAmount(inCashFlow.get(0).getFlowAmount()!=null?inCashFlow.get(0).getFlowAmount().divide(new BigDecimal(10000)):null);
			spvVo.setToCashFlow(inCashFlow.get(0));
		}
		return spvVo;
	}

	@Override
	public List<ToSpvDeCond> findToSpvCondBySpvCode(String spvCode) {
		return toSpvDeCondMapper.findBySpvCode(spvCode);
		
	}
	@Override
	public ProcessInstanceVO startSpvOutApplyProcess( ProcessInstanceVO processInstanceVO) {
	
		//新增放款监管解除记录启动审批流程
		SessionUser user = uamSessionService.getSessionUser();
		ProcessInstance processInstance = new ProcessInstance();
		processInstance.setBusinessKey(processInstanceVO.getCaseCode());
		processInstance.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SPV_OUT.getCode()));
    	
    	StartProcessInstanceVo pIVo = workFlowManager.startCaseWorkFlow(processInstance, user.getUsername(),processInstanceVO.getCaseCode());
    	if(pIVo==null||pIVo.getId()==null){
    		throw new BusinessException("流程启动失败！");
    	}
    	//保存流程数据
		ToWorkFlow toWorkFlow = new ToWorkFlow();
		toWorkFlow.setCaseCode(processInstanceVO.getCaseCode());
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.SPV_OUT.getCode());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlowService.insertSelective(toWorkFlow);
		
		TaskQuery tq = new TaskQuery(pIVo.getId(), true);
		PageableVo pageableVo = workFlowManager.listTasks(tq);
		List<T> taskList = pageableVo.getData();
		if(CollectionUtils.isNotEmpty(taskList)){
			for(int i = 0;i < taskList.size();i++){
				Object object = taskList.get(0);
				TaskVo taskVo = (TaskVo) object;
				processInstanceVO.setTaskId(String.valueOf(taskVo.getId()));
				break;
			}	
		}
		processInstanceVO.setProcessInstanceId(pIVo.getId());
		return processInstanceVO;
	}
	
	@Override
	public void saveToSpvDeRec(ToSpvDeRec toSpvDeRec, String processInstanceId) {
				
		ToSpvDeRec rec = toSpvDeRecMapper.findByProcessInstanceId(processInstanceId);
		if(rec == null){
			SessionUser user = uamSessionService.getSessionUser();        	
			toSpvDeRec.setDeAmount(toSpvDeRec.getDeAmount()!=null?toSpvDeRec.getDeAmount().multiply(new BigDecimal(10000)):null);
    		toSpvDeRec.setOperId(user.getId());
    		toSpvDeRec.setProcessInstanceId(processInstanceId);
			toSpvDeRecMapper.insertSelective(toSpvDeRec);
			
		}else{
			toSpvDeRec.setPkid(rec.getPkid());
			toSpvDeRec.setDeAmount(toSpvDeRec.getDeAmount()!=null?toSpvDeRec.getDeAmount().multiply(new BigDecimal(10000)):null);
			toSpvDeRecMapper.updateByPrimaryKeySelective(toSpvDeRec);
		}
	}

	@Override
	public void submitToSpvDeRec(ToSpvDeRec toSpvDeRec,
			ProcessInstanceVO processInstanceVO) {
		
		saveToSpvDeRec(toSpvDeRec,processInstanceVO.getProcessInstanceId());
		SessionUser user = uamSessionService.getSessionUser();        	

		//保存审批记录
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		toApproveRecord.setContent(Consts.SPV_OUT_APPLY_CONTENT);
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setPartCode(Consts.PART_CODE_SPV_OUT_APPLY);
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setApproveType("6");
		toApproveRecordService.insertToApproveRecord(toApproveRecord);
		
		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		
		
	}

	@Override
	public ToSpvDeRec findSpvDeRecBySpvCodeAndCondId(ToSpvDeRec toSpvDeRec){
		ToSpvDeRec spvDeRec = toSpvDeRecMapper.findBySpvCodeAndCondId(toSpvDeRec);
		spvDeRec.setDeAmount(spvDeRec.getDeAmount()!=null?spvDeRec.getDeAmount().divide(new BigDecimal(10000)):null);
		return toSpvDeRec;
	}

	@Override
	public ToSpv findToSpvBySpvCode(String spvCode) {
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
		toSpv.setAmount(toSpv.getAmount()!=null?toSpv.getAmount().divide(new BigDecimal(10000)):null);
		return toSpv;
	}

	@Override
	public ToCaseInfoCountVo countToSignById(String userId) {
		
		return toSpvMapper.countToSignById(userId);
	}

	@Override
	public ToCaseInfoCountVo queryCountToSignById(ToCase toCase) {
		return toSpvMapper.queryCountToSignById(toCase);
	}

	@Override
	public ToCaseInfoCountVo countToSignByOrgId(String orgId,String startDate,String endDate) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setStartDate(startDate);
		toCase.setEndDate(endDate);
		return toSpvMapper.countToSignByOrgId(toCase);
	}

	@Override
	public ToCaseInfoCountVo queryCountToSignByOrg(ToCase toCase) {
		
		return toSpvMapper.queryCountToSignByOrg(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToSignListByOrgId(String orgId) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		return toSpvMapper.countToSignListByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToSignListByOrgList(List<String> orgList) {
		
		return toSpvMapper.countToSignListByOrgList(orgList);
	}

	@Override
	public SpvDeRecVo findByProcessInstanceId(String processInstanceId){
		ToSpvDeRec toSpvDeRec = toSpvDeRecMapper.findByProcessInstanceId(processInstanceId);
		SpvDeRecVo spvDeRecVo = new SpvDeRecVo();
		if(toSpvDeRec != null){
			ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(toSpvDeRec.getSpvCode());
			spvDeRecVo.setCaseCode(toSpv.getCaseCode());
			spvDeRecVo.setDeAmount(toSpvDeRec.getDeAmount().divide(new BigDecimal(10000)).doubleValue());
			spvDeRecVo.setDeCondition(toSpvDeRec.getDeCond());
			spvDeRecVo.setSpvCode(toSpvDeRec.getSpvCode());
			spvDeRecVo.setRemark(toSpvDeRec.getRemark());
			spvDeRecVo.setCondId(toSpvDeRec.getCondId());
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toSpv.getCaseCode());
			if(toPropertyInfo != null){
				spvDeRecVo.setAddress(toPropertyInfo.getPropertyAddr());
			}
		}
		return spvDeRecVo;
	}
	@Override
	public int countToSignByOrgList(List<String> strArrayList,
			String startDate, String endDate) {
		
		return toSpvMapper.countToSignByOrgList(strArrayList,startDate,endDate);
	}

	@Override
	public int initCountToSignByOrgId(String orgId,String createTime) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setTime(createTime);
		return toSpvMapper.initCountToSignByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToSignListByIdList(List<String> idList) {
		return toSpvMapper.countToSignListByIdList(idList);
	}

	@Override
	public int countToSignByIdList(List<String> idList, String startDate,
			String endDate) {
	
		return toSpvMapper.countToSignByIdList(idList,startDate,endDate);
	}

	@Override
	public void saveNewSpv(SpvBaseInfoVO spvBaseInfoVO,SessionUser user) {
		createSpvCode(spvBaseInfoVO.getToSpv());
		//保存相关信息
		// come here ...
		/**1.保存到‘资金监管合约’表*/
		if(spvBaseInfoVO.getToSpv() != null){
			if(spvBaseInfoVO.getToSpv().getPkid() != null){
				toSpvMapper.updateByPrimaryKeySelective(spvBaseInfoVO.getToSpv());
			}else{
				toSpvMapper.insertSelective(spvBaseInfoVO.getToSpv());
			}		
		}
		/**2.保存到‘客户信息’表*/
		List<ToSpvCust> spvCustList = spvBaseInfoVO.getSpvCustList();
		if(spvCustList != null && !spvCustList.isEmpty()){
			for(ToSpvCust toSpvCust:spvCustList){
				if(toSpvCust.getPkid() != null){
					toSpvCustMapper.updateByPrimaryKeySelective(toSpvCust);
				}else{			
					toSpvCustMapper.insertSelective(toSpvCust);
				}
			}
		}
		/**3.保存到‘资金监管出款方案’表*/
		ToSpvDe toSpvDe = spvBaseInfoVO.getToSpvDe();
		if(toSpvDe != null){
			if(toSpvDe.getPkid() != null){
				toSpvDeMapper.updateByPrimaryKeySelective(toSpvDe);
			}else{
				toSpvDeMapper.insertSelective(toSpvDe);
			}
		}
		/**4.保存到‘监管资金出入账约定’表*/
		List<ToSpvDeDetail> toSpvDeDetailList = spvBaseInfoVO.getToSpvDeDetailList();
		if(toSpvDeDetailList != null && !toSpvDeDetailList.isEmpty()){
			for(ToSpvDeDetail toSpvDeDetail:toSpvDeDetailList){
				if(toSpvDeDetail.getPkid() != null){
					toSpvDeDetailMapper.updateByPrimaryKeySelective(toSpvDeDetail);
				}else{				
					toSpvDeDetailMapper.insertSelective(toSpvDeDetail);
				}
			}
		}	
		/**5.保存到‘资金监管账户信息’表*/
		List<ToSpvAccount> toSpvAccountList = spvBaseInfoVO.getToSpvAccountList();
		if(toSpvAccountList != null && !toSpvAccountList.isEmpty()){
			for(ToSpvAccount toSpvAccount:toSpvAccountList){
				if(toSpvAccount.getPkid() != null){
					toSpvAccountMapper.updateByPrimaryKeySelective(toSpvAccount);
				}else{
					toSpvAccountMapper.insertSelective(toSpvAccount);
				}
			}
		}	
		/**6.保存到‘资金监管房屋信息’表*/
		ToSpvProperty toSpvProperty = spvBaseInfoVO.getToSpvProperty();
		if(toSpvProperty != null){
			if(toSpvProperty.getPkid() != null){
				toSpvPropertyMapper.updateByPrimaryKeySelective(toSpvProperty);
			}else{
				toSpvPropertyMapper.insertSelective(toSpvProperty);
			}
		}
		
		// 查询风控总监
	  	//User manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "");
    	Map<String, Object> vars = new HashMap<String,Object>();
    	vars.put("RiskControlOfficer", user.getUsername());
    	vars.put("RiskControlDirector", user.getUsername());
    	
    	StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getSpvProcessDfKey(),spvBaseInfoVO.getToSpv().getSpvCode(),vars);
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		workFlow.setBusinessKey(WorkFlowEnum.SPV_BUSSKEY.getCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);
    	
    	PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
    	List<TaskVo> taskList = pageableVo.getData();
    	for(TaskVo task : taskList) {
    		taskService.complete(task.getId()+"");
    	}
	}
	
	private void createSpvCode(ToSpv toSpv) {
		toSpv.setSpvCode(uamBasedataService.nextSeqVal("SPV_CODE",new Date()));
	}
	
	/**
	 * 
	 */
	public SpvBaseInfoVO findSpvBaseInfoVOByCaseCode(String caseCode){
		/**查询拼接spvBaseInfoVO*/
		SpvBaseInfoVO spvBaseInfoVO = new SpvBaseInfoVO();
		//TEST
	/*	caseCode = "ZY-AJ-201601-2889";*/
		/**1.toSpv*/
		ToSpv toSpv = toSpvMapper.queryToSpvByCaseCode(caseCode);
		String spvCode = toSpv.getSpvCode();
		/**2.spvCustList*/
		List<ToSpvCust> spvCustList = toSpvCustMapper.selectBySpvCode(spvCode);
		//排序：买方->卖方->监管账户->资金方
		for(ToSpvCust toSpvCust:spvCustList){
			if("BUYER".equals(toSpvCust.getTradePosition())){
				spvCustList.set(0, toSpvCust);
			}else if("SELLER".equals(toSpvCust.getTradePosition())){
				spvCustList.set(1, toSpvCust);
			}else if("SPV".equals(toSpvCust.getTradePosition())){
				spvCustList.set(2, toSpvCust);
			}else if("FUND".equals(toSpvCust.getTradePosition())){
				spvCustList.set(3, toSpvCust);
			}
		}
		/**3.toSpvDe*/
		ToSpvDe toSpvDe = toSpvDeMapper.selectBySpvCode(spvCode);
		/**4.toSpvDeDetailList*/
		List<ToSpvDeDetail> toSpvDeDetailList = toSpvDeDetailMapper.selectByDeId(String.valueOf(toSpvDe.getPkid()));
		/**5.toSpvAccountList*/
		List<ToSpvAccount> toSpvAccountList = toSpvAccountMapper.selectBySpvCode(spvCode);
		//排序：买方->卖方->监管账户->资金方
		for(ToSpvAccount toSpvAccount:toSpvAccountList){
			if("BUYER".equals(toSpvAccount.getAccountType())){
				toSpvAccountList.set(0, toSpvAccount);
			}else if("SELLER".equals(toSpvAccount.getAccountType())){
				toSpvAccountList.set(1, toSpvAccount);
			}else if("SPV".equals(toSpvAccount.getAccountType())){
				toSpvAccountList.set(2, toSpvAccount);
			}else if("FUND".equals(toSpvAccount.getAccountType())){
				toSpvAccountList.set(3, toSpvAccount);
			}
		}
		/**6.toSpvProperty*/
		ToSpvProperty toSpvProperty = toSpvPropertyMapper.selectBySpvCode(spvCode);
		
		/**装载属性*/
		spvBaseInfoVO.setToSpv(toSpv);
		spvBaseInfoVO.setSpvCustList(spvCustList);
		spvBaseInfoVO.setToSpvDe(toSpvDe);
		spvBaseInfoVO.setToSpvDeDetailList(toSpvDeDetailList);
		spvBaseInfoVO.setToSpvAccountList(toSpvAccountList);
		spvBaseInfoVO.setToSpvProperty(toSpvProperty);
		
		return spvBaseInfoVO;	
	}

	@Override
	public ToSpv selectByPrimaryKey(long pkid) {
		return toSpvMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public List<ToSpvCust> findCustBySpvCode(String spvCode) {
		return toSpvCustMapper.selectBySpvCode(spvCode);
	}

	@Override
	public ToSpvProperty findPropertyBySpvCode(String spvCode) {
		return toSpvPropertyMapper.selectBySpvCode(spvCode);
	}

	@Override
	public List<ToSpvAccount> findAccountBySpvCode(String spvCode) {
		return toSpvAccountMapper.selectBySpvCode(spvCode);
	}

	@Override
	public ToSpvDe findSpvDeBySpvCode(String spvcode) {
		return toSpvDeMapper.selectBySpvCode(spvcode);
	}

	@Override
	public List<ToSpvDeDetail> findDeDetailByDeId(String deId) {
		return toSpvDeDetailMapper.selectByDeId(deId);
	}

}
