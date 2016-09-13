package com.centaline.trans.spv.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.SpvStatusEnum;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.TgGuestInfoService;
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
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;

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

	public List<ToCashFlow> findCashFlowByCaseCodeAndDirection(String caseCode, String direction) {
		return toCashFlowMapper.findCashFlowByCaseCodeAndDirection(caseCode, direction);
	}

	@Override
	public void saveToSpv(SpvVo spvVo) {
		ToSpv toSpv = spvVo.getToSpv();
		ToSpv spv = toSpvMapper.findToSpvBySpvCode(toSpv.getSpvCode());

		if (toSpv.getPkid() != null) {
			toSpv.setAmount(toSpv.getAmount() != null ? toSpv.getAmount().multiply(new BigDecimal(10000)) : null);
			toSpvMapper.updateByPrimaryKeyWithBLOBs(toSpv);
		} else {
			if (spv != null) {
				throw new BusinessException(SpvExceptionEnum.SPV_CODE_EXIST.getValue());
			}
			toSpv.setAmount(toSpv.getAmount() != null ? toSpv.getAmount().multiply(new BigDecimal(10000)) : null);
			toSpv.setSpvType(SpvTypeEnum.ANXINBAO.getCode());
			toSpvMapper.insertSelective(toSpv);
		}
		// 保存解除条件
		List<ToSpvDeCond> toSpvDeCondList = spvVo.getToSpvDeCondList();
		if (CollectionUtils.isNotEmpty(toSpvDeCondList)) {
			for (ToSpvDeCond entity : toSpvDeCondList) {
				entity.setDeAmount(
						entity.getDeAmount() != null ? entity.getDeAmount().multiply(new BigDecimal(10000)) : null);
				entity.setSpvCode(toSpv.getSpvCode());
				if (entity.getPkid() != null) {
					toSpvDeCondMapper.updateByPrimaryKeySelective(entity);
				} else {
					toSpvDeCondMapper.insertSelective(entity);
				}
			}
		}
		// 删除解除条件
		String[] delIds = spvVo.getDelIds();
		if (delIds != null && delIds.length > 0) {
			for (String delId : delIds) {
				toSpvDeCondMapper.deleteByPrimaryKey(Long.parseLong(delId));
			}
		}

		// 保存进账信息
		ToCashFlow toCashFlow = spvVo.getToCashFlow();
		if (toCashFlow != null) {
			toCashFlow.setFlowAmount(toCashFlow.getFlowAmount() != null
					? toCashFlow.getFlowAmount().multiply(new BigDecimal(10000)) : null);
			if (toCashFlow.getPkid() != null) {
				toCashFlowMapper.updateByPrimaryKeySelective(toCashFlow);
			} else {
				toCashFlow.setFlowDirection("0");
				toCashFlow.setCaseCode(toSpv.getCaseCode());
				toCashFlow.setCashFlowType(SpvTypeEnum.ANXINBAO.getCode());
				toCashFlowMapper.insertSelective(toCashFlow);
			}
		}

		// 保存出账信息
		List<ToCashFlow> toCashFlowList = spvVo.getToCashFlowList();
		if (CollectionUtils.isNotEmpty(toCashFlowList)) {
			for (ToCashFlow entity : toCashFlowList) {
				entity.setFlowAmount(entity.getFlowAmount() != null
						? entity.getFlowAmount().multiply(new BigDecimal(10000)) : new BigDecimal(0));
				entity.setCaseCode(toSpv.getCaseCode());
				if (entity.getPkid() != null) {
					toCashFlowMapper.updateByPrimaryKeySelective(entity);
				} else {
					entity.setCaseCode(toSpv.getCaseCode());
					entity.setCashFlowType(SpvTypeEnum.ANXINBAO.getCode());
					entity.setFlowDirection("1");
					toCashFlowMapper.insertSelective(entity);
				}
			}
		}
		// 删除出账信息
		String[] delFlowIds = spvVo.getDelFlowIds();
		if (delFlowIds != null && delFlowIds.length > 0) {
			for (String delId : delFlowIds) {
				toCashFlowMapper.deleteByPrimaryKey(Long.parseLong(delId));
			}
		}
	}

	@Override
	public SpvVo findByCaseCode(String caseCode) {
		SpvVo spvVo = new SpvVo();
		ToSpv toSpv = toSpvMapper.queryToSpvByCaseCode(caseCode);
		if (toSpv == null) {
			return null;
		}
		toSpv.setAmount(toSpv.getAmount() != null ? toSpv.getAmount().divide(new BigDecimal(10000)) : null);
		spvVo.setToSpv(toSpv);
		List<ToSpvDeCond> toSpvDeCondList = toSpvDeCondMapper.findBySpvCode(toSpv.getSpvCode());
		if (CollectionUtils.isNotEmpty(toSpvDeCondList)) {
			for (ToSpvDeCond toSpvDeCond : toSpvDeCondList) {
				toSpvDeCond.setDeAmount(toSpvDeCond.getDeAmount() != null
						? toSpvDeCond.getDeAmount().divide(new BigDecimal(10000)) : null);
			}
		}
		spvVo.setToSpvDeCondList(toSpvDeCondList);

		// 查询出账信息
		List<ToCashFlow> toCashFlowList = findCashFlowByCaseCodeAndDirection(caseCode, CashDirectionEnum.OUT.getCode());
		if (CollectionUtils.isNotEmpty(toCashFlowList)) {
			for (ToCashFlow toCashFlow : toCashFlowList) {
				toCashFlow.setFlowAmount(toCashFlow.getFlowAmount() != null
						? toCashFlow.getFlowAmount().divide(new BigDecimal(10000)) : null);
			}
		}
		spvVo.setToCashFlowList(toCashFlowList);

		// 查询进账信息
		List<ToCashFlow> inCashFlow = findCashFlowByCaseCodeAndDirection(caseCode, CashDirectionEnum.IN.getCode());
		if (CollectionUtils.isNotEmpty(inCashFlow)) {
			inCashFlow.get(0).setFlowAmount(inCashFlow.get(0).getFlowAmount() != null
					? inCashFlow.get(0).getFlowAmount().divide(new BigDecimal(10000)) : null);
			spvVo.setToCashFlow(inCashFlow.get(0));
		}
		return spvVo;
	}

	@Override
	public List<ToSpvDeCond> findToSpvCondBySpvCode(String spvCode) {
		return toSpvDeCondMapper.findBySpvCode(spvCode);

	}

	@Override
	public ProcessInstanceVO startSpvOutApplyProcess(ProcessInstanceVO processInstanceVO) {

		// 新增放款监管解除记录启动审批流程
		SessionUser user = uamSessionService.getSessionUser();
		ProcessInstance processInstance = new ProcessInstance();
		processInstance.setBusinessKey(processInstanceVO.getCaseCode());
		processInstance.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SPV_OUT.getCode()));

		StartProcessInstanceVo pIVo = workFlowManager.startCaseWorkFlow(processInstance, user.getUsername(),
				processInstanceVO.getCaseCode());
		if (pIVo == null || pIVo.getId() == null) {
			throw new BusinessException("流程启动失败！");
		}
		// 保存流程数据
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
		if (CollectionUtils.isNotEmpty(taskList)) {
			for (int i = 0; i < taskList.size(); i++) {
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
		if (rec == null) {
			SessionUser user = uamSessionService.getSessionUser();
			toSpvDeRec.setDeAmount(
					toSpvDeRec.getDeAmount() != null ? toSpvDeRec.getDeAmount().multiply(new BigDecimal(10000)) : null);
			toSpvDeRec.setOperId(user.getId());
			toSpvDeRec.setProcessInstanceId(processInstanceId);
			toSpvDeRecMapper.insertSelective(toSpvDeRec);

		} else {
			toSpvDeRec.setPkid(rec.getPkid());
			toSpvDeRec.setDeAmount(
					toSpvDeRec.getDeAmount() != null ? toSpvDeRec.getDeAmount().multiply(new BigDecimal(10000)) : null);
			toSpvDeRecMapper.updateByPrimaryKeySelective(toSpvDeRec);
		}
	}

	@Override
	public void submitToSpvDeRec(ToSpvDeRec toSpvDeRec, ProcessInstanceVO processInstanceVO) {

		saveToSpvDeRec(toSpvDeRec, processInstanceVO.getProcessInstanceId());
		SessionUser user = uamSessionService.getSessionUser();

		// 保存审批记录
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
	public ToSpvDeRec findSpvDeRecBySpvCodeAndCondId(ToSpvDeRec toSpvDeRec) {
		ToSpvDeRec spvDeRec = toSpvDeRecMapper.findBySpvCodeAndCondId(toSpvDeRec);
		spvDeRec.setDeAmount(
				spvDeRec.getDeAmount() != null ? spvDeRec.getDeAmount().divide(new BigDecimal(10000)) : null);
		return toSpvDeRec;
	}

	@Override
	public ToSpv findToSpvBySpvCode(String spvCode) {
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
		toSpv.setAmount(toSpv.getAmount() != null ? toSpv.getAmount().divide(new BigDecimal(10000)) : null);
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
	public ToCaseInfoCountVo countToSignByOrgId(String orgId, String startDate, String endDate) {
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
	public SpvDeRecVo findByProcessInstanceId(String processInstanceId) {
		ToSpvDeRec toSpvDeRec = toSpvDeRecMapper.findByProcessInstanceId(processInstanceId);
		SpvDeRecVo spvDeRecVo = new SpvDeRecVo();
		if (toSpvDeRec != null) {
			ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(toSpvDeRec.getSpvCode());
			spvDeRecVo.setCaseCode(toSpv.getCaseCode());
			spvDeRecVo.setDeAmount(toSpvDeRec.getDeAmount().divide(new BigDecimal(10000)).doubleValue());
			spvDeRecVo.setDeCondition(toSpvDeRec.getDeCond());
			spvDeRecVo.setSpvCode(toSpvDeRec.getSpvCode());
			spvDeRecVo.setRemark(toSpvDeRec.getRemark());
			spvDeRecVo.setCondId(toSpvDeRec.getCondId());
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toSpv.getCaseCode());
			if (toPropertyInfo != null) {
				spvDeRecVo.setAddress(toPropertyInfo.getPropertyAddr());
			}
		}
		return spvDeRecVo;
	}

	@Override
	public int countToSignByOrgList(List<String> strArrayList, String startDate, String endDate) {

		return toSpvMapper.countToSignByOrgList(strArrayList, startDate, endDate);
	}

	@Override
	public int initCountToSignByOrgId(String orgId, String createTime) {
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
	public int countToSignByIdList(List<String> idList, String startDate, String endDate) {

		return toSpvMapper.countToSignByIdList(idList, startDate, endDate);
	}

	@Override
	public void saveNewSpv(SpvBaseInfoVO spvBaseInfoVO, SessionUser user) {

		if (spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getPkid() == null) {
			String caseCode_ = spvBaseInfoVO.getToSpv().getCaseCode();
			ToSpv toSpv = toSpvMapper.queryToSpvByCaseCode(caseCode_);
			if(toSpv != null){
				throw new BusinessException("保存失败：已存在改案件的资金监管信息！");
			}
		}
		
		// 生成spvCode
		String spvCode = createSpvCode();
		ToSpv toSpv = spvBaseInfoVO.getToSpv();
		List<ToSpvDeDetail> toSpvDeDetailList = spvBaseInfoVO.getToSpvDeDetailList();
		ToSpvProperty toSpvProperty = spvBaseInfoVO.getToSpvProperty();
		// 保存相关信息
		/** 乘万处理 */
		toSpv.setAmount(toSpv.getAmount() != null ? toSpv.getAmount().multiply(new BigDecimal(10000)) : null);
		toSpv.setAmountMort(
				toSpv.getAmountMort() != null ? toSpv.getAmountMort().multiply(new BigDecimal(10000)) : null);
		toSpv.setAmountMortCom(
				toSpv.getAmountMortCom() != null ? toSpv.getAmountMortCom().multiply(new BigDecimal(10000)) : null);
		toSpv.setAmountMortPsf(
				toSpv.getAmountMortPsf() != null ? toSpv.getAmountMortPsf().multiply(new BigDecimal(10000)) : null);
		toSpv.setAmountOwn(toSpv.getAmountOwn() != null ? toSpv.getAmountOwn().multiply(new BigDecimal(10000)) : null);

		toSpvProperty.setLeftAmount(toSpvProperty.getLeftAmount() != null
				? toSpvProperty.getLeftAmount().multiply(new BigDecimal(10000)) : null);
		toSpvProperty.setSignAmount(toSpvProperty.getSignAmount() != null
				? toSpvProperty.getSignAmount().multiply(new BigDecimal(10000)) : null);

		for (ToSpvDeDetail toSpvDeDetail : toSpvDeDetailList) {
			toSpvDeDetail.setDeAmount(toSpvDeDetail.getDeAmount() != null
					? toSpvDeDetail.getDeAmount().multiply(new BigDecimal(10000)) : null);
		}

		/** 1.保存到‘资金监管合约’表 */
		// ToSpv toSpv = spvBaseInfoVO.getToSpv();
		if (toSpv != null) {
			if (toSpv.getPkid() != null) {
				toSpv.setUpdateBy(user.getId());
				toSpv.setUpdateTime(new Date());
				toSpvMapper.updateByPrimaryKeySelective(toSpv);
			} else {
				toSpv.setCreateBy(user.getId());
				toSpv.setCreateTime(new Date());
				toSpv.setSpvCode(spvCode);
				toSpv.setIsDeleted("0");
				toSpv.setStatus("0");
				toSpvMapper.insertSelective(toSpv);
			}
		}
		/** 2.保存到‘客户信息’表 */
		List<ToSpvCust> spvCustList = spvBaseInfoVO.getSpvCustList();
		if (spvCustList != null && !spvCustList.isEmpty()) {
			for (ToSpvCust toSpvCust : spvCustList) {
				if (toSpvCust != null) {
					if (toSpvCust.getPkid() != null) {
						toSpvCust.setUpdateBy(user.getId());
						toSpvCust.setUpdateTime(new Date());
						toSpvCustMapper.updateByPrimaryKeySelective(toSpvCust);
					} else {
						toSpvCust.setCreateBy(user.getId());
						toSpvCust.setCreateTime(new Date());
						toSpvCust.setSpvCode(spvCode);
						toSpvCust.setIsDeleted("0");
						toSpvCustMapper.insertSelective(toSpvCust);
					}
				}
			}
		}

		/** 3.保存到‘资金监管账户信息’表 */
		List<ToSpvAccount> toSpvAccountList = spvBaseInfoVO.getToSpvAccountList();
		if (toSpvAccountList != null && !toSpvAccountList.isEmpty()) {
			for (ToSpvAccount toSpvAccount : toSpvAccountList) {
				if (toSpvAccount.getPkid() != null) {
					toSpvAccount.setUpdateBy(user.getId());
					toSpvAccount.setUpdateTime(new Date());
					toSpvAccountMapper.updateByPrimaryKeySelective(toSpvAccount);
				} else {
					toSpvAccount.setCreateBy(user.getId());
					toSpvAccount.setCreateTime(new Date());
					toSpvAccount.setSpvCode(spvCode);
					toSpvAccount.setIsDeleted("0");
					toSpvAccountMapper.insertSelective(toSpvAccount);
				}
			}
		}

		/** 4.保存到‘资金监管出款方案’表 */
		ToSpvDe toSpvDe = spvBaseInfoVO.getToSpvDe();
		if (toSpvDe != null) {
			if (toSpvDe.getPkid() != null) {
				toSpvDe.setUpdateBy(user.getId());
				toSpvDe.setUpdateTime(new Date());
				toSpvDeMapper.updateByPrimaryKeySelective(toSpvDe);
			} else {
				toSpvDe.setCreateBy(user.getId());
				toSpvDe.setCreateTime(new Date());
				toSpvDe.setSpvCode(spvCode);
				toSpvDe.setIsDeleted("0");
				toSpvDeMapper.insertSelective(toSpvDe);
			}
		}
		/** 5.保存到‘监管资金出入账约定’表 */
		// List<ToSpvDeDetail> toSpvDeDetailList =
		// spvBaseInfoVO.getToSpvDeDetailList();
			/** 清空所有deid记录 */
/*			if (toSpvDe != null && toSpvDe.getPkid() != null) {
				toSpvDeDetailMapper.deleteByDeId(toSpvDe.getPkid());
			}*/

			//重构toSpvDeDetailList
			List<ToSpvDeDetail> toSpvDeDetailNewList = new ArrayList<ToSpvDeDetail>();
			if (toSpvDeDetailList != null && !toSpvDeDetailList.isEmpty()) {
				for(ToSpvDeDetail toSpvDeDetail : toSpvDeDetailList){
					if(!(toSpvDeDetail == null || toSpvDeDetail.getDeCondCode()==null ||toSpvDeDetail.getPayeeAccountType()==null ||toSpvDeDetail.getDeAmount()==null ||toSpvDeDetail.getDeAddition()==null)){
						toSpvDeDetailNewList.add(toSpvDeDetail);
					}
				}
			}
			List<ToSpvDeDetail> deIdDetailList = toSpvDeDetailMapper.selectByDeId(toSpvDe.getPkid());
			for(ToSpvDeDetail toSpvDeDetail : deIdDetailList){
				toSpvDeDetail.setUpdateBy(user.getId());
				toSpvDeDetail.setUpdateTime(new Date());
				toSpvDeDetail.setIsDeleted("1");
				toSpvDeDetailMapper.updateByPrimaryKeySelective(toSpvDeDetail);
			}
			if (toSpvDeDetailNewList != null && !toSpvDeDetailNewList.isEmpty()) {
				// PayeeAccountType -> PayeeAccountId
				for (ToSpvDeDetail toSpvDeDetail : toSpvDeDetailNewList) {
					if (toSpvAccountList != null && !toSpvAccountList.isEmpty()) {
						for (ToSpvAccount toSpvAccount : toSpvAccountList) {
							if (toSpvAccount.getAccountType().equals(toSpvDeDetail.getPayeeAccountType())) {
								toSpvDeDetail.setPayeeAccountId(toSpvAccount.getPkid());
							}
						}
					}
					
					if(toSpvDeDetail.getPkid() != null){
						toSpvDeDetail.setUpdateBy(user.getId());
						toSpvDeDetail.setUpdateTime(new Date());
						toSpvDeDetail.setIsDeleted("0");
						toSpvDeDetailMapper.updateByPrimaryKeySelective(toSpvDeDetail);
					}else{
						toSpvDeDetail.setCreateBy(user.getId());
						toSpvDeDetail.setCreateTime(new Date());
						toSpvDeDetail.setDeId(toSpvDe.getPkid());
						toSpvDeDetail.setIsDeleted("0");
						toSpvDeDetailMapper.insertSelective(toSpvDeDetail);
					}
				}
			}

		/** 6.保存到‘资金监管房屋信息’表 */
		// ToSpvProperty toSpvProperty = spvBaseInfoVO.getToSpvProperty();
		if (toSpvProperty != null) {
			if (toSpvProperty.getPkid() != null) {
				toSpvProperty.setUpdateBy(user.getId());
				toSpvProperty.setUpdateTime(new Date());
				toSpvPropertyMapper.updateByPrimaryKeySelective(toSpvProperty);
			} else {
				toSpvProperty.setCreateBy(user.getId());
				toSpvProperty.setCreateTime(new Date());
				toSpvProperty.setSpvCode(spvCode);
				toSpvProperty.setIsDeleted("0");
				toSpvPropertyMapper.insertSelective(toSpvProperty);
			}
		}

	}

	@Override
	public void submitNewSpv(SpvBaseInfoVO spvBaseInfoVO, SessionUser user) {

		// 先查询流程是否已经开启，若开启则提示用户不能再次开启
		// if(spvBaseInfoVO.getToSpv() != null){
		// ToSpv row =
		// toSpvMapper.selectByPrimaryKey(spvBaseInfoVO.getToSpv().getPkid());
		// if(row != null &&
		// (SpvStatusEnum.INPROGRESS.getCode().equals(row.getStatus()) ||
		// SpvStatusEnum.COMPLETE.getCode().equals(row.getStatus()))){
		// throw new BusinessException("启动失败：流程已经启动或结束！");
		// }
		// }
	
		ToWorkFlow twf = new ToWorkFlow();
		twf.setBusinessKey(WorkFlowEnum.SPV_BUSSKEY.getCode());
		twf.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);

		if (record != null) {
			throw new BusinessException("流程启动失败：该案件的流程已开启！");
		}
		
		saveNewSpv(spvBaseInfoVO, user);

		// ToCase
		// te=toCaseService.findToCaseByCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		// String orgId = te.getOrgId();
		// 查询风控专员和总监
		Map<String, Object> vars = new HashMap<String, Object>();
		String orgId = "81E586DCB7354D438A4C38C7EAFBF53E";
		String spvPkid = spvBaseInfoVO.getToSpv().getPkid().toString();
		vars.put("spvPkid", spvPkid);
		vars.put("RiskControlOfficer",
				uamUserOrgService.getUserByOrgIdAndJobCode(orgId, "JYFKZY").get(0).getUsername());
		vars.put("RiskControlDirector",
				uamUserOrgService.getUserByOrgIdAndJobCode(orgId, "JYFKZJ").get(0).getUsername());

		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSpvProcessDfKey(), spvBaseInfoVO.getToSpv().getCaseCode(), vars);

		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("SpvApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}

		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		workFlow.setBusinessKey(WorkFlowEnum.SPV_BUSSKEY.getCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);

		// 首次开启流程时间为申请时间
		if (spvBaseInfoVO.getToSpv().getApplyTime() == null) {
			spvBaseInfoVO.getToSpv().setApplyTime(new Date());
		}
		spvBaseInfoVO.getToSpv().setStatus(SpvStatusEnum.INPROGRESS.getCode());
		toSpvMapper.updateByPrimaryKeySelective(spvBaseInfoVO.getToSpv());

	}

	private String createSpvCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yyyyMM").format(new Date()));
	}

	@Override
	public void setAttribute(ServletRequest request, String caseCode) {

		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());
		// 物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		User agentUser = null;
		// 经纪人
		if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
			agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		}
		// 交易顾问
		User consultUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		// 上下家
		List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		StringBuffer seller = new StringBuffer();
		StringBuffer sellerMobil = new StringBuffer();
		StringBuffer buyer = new StringBuffer();
		StringBuffer buyerMobil = new StringBuffer();
		for (TgGuestInfo guest : guestList) {
			if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
				seller.append(guest.getGuestName());
				sellerMobil.append(guest.getGuestPhone());
				seller.append("/");
				sellerMobil.append("/");
			} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
				buyer.append(guest.getGuestName());
				buyerMobil.append(guest.getGuestPhone());
				buyer.append("/");
				buyerMobil.append("/");
			}
		}

		if (guestList.size() > 0) {
			if (seller.length() > 1) {
				seller.deleteCharAt(seller.length() - 1);
				sellerMobil.deleteCharAt(sellerMobil.length() - 1);
			}

			if (buyer.length() > 1) {
				buyer.deleteCharAt(buyer.length() - 1);
				buyerMobil.deleteCharAt(buyerMobil.length() - 1);
			}
		}
		request.setAttribute("caseCode", toCase.getCaseCode());
		request.setAttribute("propertyAddr", toPropertyInfo.getPropertyAddr());
		request.setAttribute("propertySquare", toPropertyInfo.getSquare());
		request.setAttribute("processorName", consultUser == null ? "" : consultUser.getRealName());
		request.setAttribute("agentName", agentUser == null ? "" : agentUser.getRealName());
		request.setAttribute("sellerName", seller.indexOf("/") == -1?seller:seller.substring(0, seller.indexOf("/")));
		request.setAttribute("sellerMobil", sellerMobil.indexOf("/") == -1?sellerMobil:sellerMobil.substring(0, sellerMobil.indexOf("/")));
		request.setAttribute("buyerName", buyer.indexOf("/") == -1?buyer:buyer.substring(0, buyer.indexOf("/")));
		request.setAttribute("buyerMobil", buyerMobil.indexOf("/") == -1?buyerMobil:buyerMobil.substring(0, buyerMobil.indexOf("/")));
	}

	/**
	 * 获取流程变量并查询拼接spvBaseInfoVO
	 */
	public SpvBaseInfoVO findSpvBaseInfoVOByInstCode(HttpServletRequest request, String instCode) {
		Long pkid = Long.parseLong(workFlowManager.getVar(instCode, "spvPkid").getValue().toString());
		return findSpvBaseInfoVOByPkid(request, pkid);
	}

	@Override
	public void findSpvBaseInfoVOAndSetAttr(HttpServletRequest request, Long pkid, String caseCode) {
		SpvBaseInfoVO spvBaseInfoVO = findSpvBaseInfoVOByPkid(request, pkid);
		/** 查询案件相关信息 */
		if (!StringUtils.isEmpty(caseCode)) {
				setAttribute(request, caseCode);
		} else {
			if (spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null) {
				setAttribute(request, spvBaseInfoVO.getToSpv().getCaseCode());
			}
		}

		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
	}

	/**
	 * 查询拼接spvBaseInfoVO
	 */
	public SpvBaseInfoVO findSpvBaseInfoVOByPkid(ServletRequest request, Long pkid) {

		SpvBaseInfoVO spvBaseInfoVO = new SpvBaseInfoVO();

		if (StringUtils.isBlank(String.valueOf(pkid)) || "null".equals(String.valueOf(pkid))) {
			return spvBaseInfoVO;
		}

		/** 1.toSpv */
		ToSpv toSpv = selectByPrimaryKey(pkid);
		if (toSpv == null || toSpv.getSpvCode() == null) {
			return spvBaseInfoVO;
		}

		String spvCode = toSpv.getSpvCode();
		/** 2.spvCustList */
		List<ToSpvCust> spvCustList = toSpvCustMapper.selectBySpvCode(spvCode);
		List<ToSpvCust> spvNewCustList = Arrays.asList(null, null, null, null);
		// 排序：买方->卖方->监管账户->资金方
		for (ToSpvCust toSpvCust : spvCustList) {
			if ("BUYER".equals(toSpvCust.getTradePosition())) {
				spvNewCustList.set(0, toSpvCust);
			} else if ("SELLER".equals(toSpvCust.getTradePosition())) {
				spvNewCustList.set(1, toSpvCust);
			} else if ("SPV".equals(toSpvCust.getTradePosition())) {
				spvNewCustList.set(2, toSpvCust);
			} else if ("FUND".equals(toSpvCust.getTradePosition())) {
				spvNewCustList.set(3, toSpvCust);
			}
		}
		/** 3.toSpvDe */
		ToSpvDe toSpvDe = toSpvDeMapper.selectBySpvCode(spvCode);
		/** 4.toSpvDeDetailList */
		List<ToSpvDeDetail> toSpvDeDetailList = toSpvDeDetailMapper.selectByDeId(toSpvDe.getPkid());
		/** 5.toSpvAccountList */
		List<ToSpvAccount> toSpvAccountList = toSpvAccountMapper.selectBySpvCode(spvCode);
		List<ToSpvAccount> toSpvNewAccountList = Arrays.asList(null, null, null, null);
		// 排序：买方->卖方->监管账户->资金方
		for (ToSpvAccount toSpvAccount : toSpvAccountList) {
			if ("BUYER".equals(toSpvAccount.getAccountType())) {
				toSpvNewAccountList.set(0, toSpvAccount);
			} else if ("SELLER".equals(toSpvAccount.getAccountType())) {
				toSpvNewAccountList.set(1, toSpvAccount);
			} else if ("SPV".equals(toSpvAccount.getAccountType())) {
				toSpvNewAccountList.set(2, toSpvAccount);
			} else if ("FUND".equals(toSpvAccount.getAccountType())) {
				toSpvNewAccountList.set(3, toSpvAccount);
			}
		}
		// PayeeAccountId -> PayeeAccountType
		for (ToSpvDeDetail detail : toSpvDeDetailList) {
			if (toSpvAccountList != null && !toSpvAccountList.isEmpty()) {
				for (ToSpvAccount toSpvAccount : toSpvAccountList) {
					if (toSpvAccount.getPkid().equals(detail.getPayeeAccountId())) {
						detail.setPayeeAccountType(toSpvAccount.getAccountType());
					}
				}
			}
		}

		/** 6.toSpvProperty */
		ToSpvProperty toSpvProperty = toSpvPropertyMapper.selectBySpvCode(spvCode);

		/** 除万处理 */
		toSpv.setAmount(toSpv.getAmount() != null ? toSpv.getAmount().divide(new BigDecimal(10000)) : null);
		toSpv.setAmountMort(toSpv.getAmountMort() != null ? toSpv.getAmountMort().divide(new BigDecimal(10000)) : null);
		toSpv.setAmountMortCom(
				toSpv.getAmountMortCom() != null ? toSpv.getAmountMortCom().divide(new BigDecimal(10000)) : null);
		toSpv.setAmountMortPsf(
				toSpv.getAmountMortPsf() != null ? toSpv.getAmountMortPsf().divide(new BigDecimal(10000)) : null);
		toSpv.setAmountOwn(toSpv.getAmountOwn() != null ? toSpv.getAmountOwn().divide(new BigDecimal(10000)) : null);

		toSpvProperty.setLeftAmount(toSpvProperty.getLeftAmount() != null
				? toSpvProperty.getLeftAmount().divide(new BigDecimal(10000)) : null);
		toSpvProperty.setSignAmount(toSpvProperty.getSignAmount() != null
				? toSpvProperty.getSignAmount().divide(new BigDecimal(10000)) : null);

		for (ToSpvDeDetail toSpvDeDetail : toSpvDeDetailList) {
			toSpvDeDetail.setDeAmount(toSpvDeDetail.getDeAmount() != null
					? toSpvDeDetail.getDeAmount().divide(new BigDecimal(10000)) : null);
		}
		/** 装载属性 */
		spvBaseInfoVO.setToSpv(toSpv);
		spvBaseInfoVO.setSpvCustList(spvNewCustList);
		spvBaseInfoVO.setToSpvDe(toSpvDe);
		spvBaseInfoVO.setToSpvDeDetailList(toSpvDeDetailList);
		spvBaseInfoVO.setToSpvAccountList(toSpvNewAccountList);
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
	public List<ToSpvDeDetail> findDeDetailByDeId(Long deId) {
		return toSpvDeDetailMapper.selectByDeId(deId);
	}

	@Override
	public int updateByPrimaryKey(ToSpv record) {
		return toSpvMapper.updateByPrimaryKey(record);
	}

}
