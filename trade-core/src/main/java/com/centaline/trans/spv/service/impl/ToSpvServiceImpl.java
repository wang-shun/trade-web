package com.centaline.trans.spv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.enums.CashDirectionEnum;
import com.centaline.trans.spv.enums.SpvExceptionEnum;
import com.centaline.trans.spv.enums.SpvTypeEnum;
import com.centaline.trans.spv.repository.ToCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvDeCondMapper;
import com.centaline.trans.spv.repository.ToSpvDeRecMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.ToSpvService;
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
    private PropertyUtilsService propertyUtilsService;
    
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
				entity.setFlowAmount(entity.getFlowAmount()!=null?entity.getFlowAmount().multiply(new BigDecimal(10000)):null);
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
		processInstance.setBusinessKey(WorkFlowEnum.SPV_OUT.getCode());
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

}
