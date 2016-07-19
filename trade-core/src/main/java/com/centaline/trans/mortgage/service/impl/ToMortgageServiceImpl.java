package com.centaline.trans.mortgage.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Service
public class ToMortgageServiceImpl implements ToMortgageService {

	@Autowired
	private ToMortgageMapper toMortgageMapper;

	@Autowired
	private ToSupDocuService toSupDocuService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	MessageService messageService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;

	@Override
	public ToMortgage saveToMortgage(ToMortgage toMortgage) {

		if (toMortgage.getPkid() != null) {
				toMortgageMapper.update(toMortgage);
		} else {
				toMortgageMapper.insertSelective(toMortgage);
		}
		if("1".equals(toMortgage.getFormCommLoan())&&StringUtils.isNotBlank(toMortgage.getLastLoanBank())){
			toMortgageMapper.restSetLastLoanBank(toMortgage);	
		}
		
		if(null!=toMortgage.getCustCode()){
			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
			if(guest!=null){
				guest.setWorkUnit(toMortgage.getCustCompany());
				guest.setGuestName(toMortgage.getCustName());
				tgGuestInfoService.updateByPrimaryKeySelective(guest);
			}
		}
		return toMortgage;
	}

	@Override
	public void saveToMortgageAndSupDocu(ToMortgage toMortgage) {
		ToMortgage mortgage=null;
		ToMortgage condition=new ToMortgage();//用这三个条件确定一条商贷的贷款信息,防止前台重复提交数据或者加载数据出问题时数据重复
		condition.setCaseCode(toMortgage.getCaseCode());
		condition.setIsMainLoanBank(toMortgage.getIsMainLoanBank());
		condition.setIsDelegateYucui("1");
		List<ToMortgage>list= toMortgageMapper.findToMortgageByConditionWithCommLoan(condition);
		if(list!=null&&!list.isEmpty()){
			mortgage=list.get(0);
		}
		if (mortgage != null) {
				toMortgage.setPkid(mortgage.getPkid());
				toMortgageMapper.update(toMortgage);
		} else {
			toMortgage.setIsDelegateYucui("1");
			toMortgageMapper.insertSelective(toMortgage);
		}
		if("1".equals(toMortgage.getFormCommLoan())&&StringUtils.isNotBlank(toMortgage.getLastLoanBank())){
			toMortgageMapper.restSetLastLoanBank(toMortgage);	
		}
		ToSupDocu toSupDocu = toMortgage.getToSupDocu();
		ToSupDocu supDocu = toSupDocuService.findByCaseCode(toMortgage
				.getCaseCode());

		if (supDocu != null) {
			supDocu.setSupContent(toSupDocu.getSupContent());
			supDocu.setRemindTime(toSupDocu.getRemindTime());
			toSupDocuService.updateToSupDocu(supDocu);
		} else {
			if (StringUtils.isNotBlank(toSupDocu.getSupContent())) {
				toSupDocu.setCaseCode(toMortgage.getCaseCode());
				toSupDocu.setPartCode("ComLoanProcess");
				toSupDocuService.saveToSupDocu(toSupDocu);
			}
		}
		if(null!=toMortgage.getCustCode()){
			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
			if(guest!=null){
				guest.setWorkUnit(toMortgage.getCustCompany());
				guest.setGuestName(toMortgage.getCustName());
				tgGuestInfoService.updateByPrimaryKeySelective(guest);
			}
		}
	}

	@Override
	public ToMortgage findToMortgageById(Long id) {
		return toMortgageMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateToMortgage(ToMortgage toMortgage) {
		toMortgageMapper.update(toMortgage);

	}

	@Override
	public ToMortgage findToMortgageByCaseCode(String caseCode) {
		ToMortgage toMortgage = toMortgageMapper
				.findToMortgageByCaseCode(caseCode);
		if (toMortgage != null) {
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(caseCode);
			toMortgage.setToSupDocu(toSupDocu);
			toMortgage
					.setComAmount(toMortgage.getComAmount() != null ? toMortgage
							.getComAmount().divide(new BigDecimal(10000))
							: null);
			toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount()!=null?toMortgage.getMortTotalAmount()
					.divide(new BigDecimal(10000)):null);
			toMortgage
					.setPrfAmount(toMortgage.getPrfAmount() != null ? toMortgage
							.getPrfAmount().divide(new BigDecimal(10000))
							: null);
			toMortgage.setToSupDocu(toSupDocu);
		}
		return toMortgage;
	}

	@Override
	public ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage) {
		List<ToMortgage> list = toMortgageMapper
				.findToMortgageByCaseCodeAndBankType(toMortgage);
		if (CollectionUtils.isNotEmpty(list)) {
			ToMortgage mort = null;
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage
					.getCaseCode());

			if (list.size() == 1) {
				mort = list.get(0);
			} else {
				for (ToMortgage mortgage : list) {
					if (StringUtils.isNotBlank(mortgage.getLastLoanBank())) {
						mort = mortgage;
						break;
					}
				}
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() != null ?mort.getMortTotalAmount().divide(
					new BigDecimal(10000)):null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setToSupDocu(toSupDocu);

			return mort;
		}
		return null;
	}
	@Override
	public ToMortgage findToMortgageByCaseCodeWithCommLoan(ToMortgage toMortgage) {
		List<ToMortgage> list = toMortgageMapper
				.findToMortgageByConditionWithCommLoan(toMortgage);
		if (CollectionUtils.isNotEmpty(list)) {
			ToMortgage mort = null;
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage
					.getCaseCode());

			if (list.size() == 1) {
				mort = list.get(0);
			} else {
				for (ToMortgage mortgage : list) {
					if (StringUtils.isNotBlank(mortgage.getLastLoanBank())) {
						mort = mortgage;
						break;
					}
				}
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() != null ?mort.getMortTotalAmount().divide(
					new BigDecimal(10000)):null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setToSupDocu(toSupDocu);

			return mort;
		}
		return null;
	}

	@Override
	public ToMortgage findToMortgageByCaseCode2(String caseCode) {

		List<ToMortgage> toMortgageList = toMortgageMapper
				.findToMortgageByCaseCodeNoBlank(caseCode);
		if (CollectionUtils.isNotEmpty(toMortgageList)) {
			ToMortgage mort = null;
			if (toMortgageList.size() == 1) {
				mort = toMortgageList.get(0);
			} else {
				for (ToMortgage toMortgage : toMortgageList) {
					if (StringUtils.isNotEmpty(toMortgage.getLastLoanBank())) {
						mort = toMortgage;
					}
				}
			}
			if (mort == null) {
				return mort;
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() !=null ?mort.getMortTotalAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			return mort;
		}
		return null;
	}
	

	@Override
	public ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode, String mortType) {
		ToMortgage toMortgage=new ToMortgage();
		toMortgage.setCaseCode(caseCode);
		toMortgage.setMortType(mortType);
		toMortgage.setIsDelegateYucui("1");
		List<ToMortgage> list=toMortgageMapper.findToMortgageByCondition(toMortgage);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ToMortgage findToSelfLoanMortgage(String caseCode) {
		ToMortgage toMortgage=new ToMortgage();
		toMortgage.setCaseCode(caseCode);
		toMortgage.setIsDelegateYucui("0");
		List<ToMortgage> list=toMortgageMapper.findToMortgageByCondition(toMortgage);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void inActiveMortageByCaseCode(String caseCode) {
		toMortgageMapper.inActiveMortageByCaseCode(caseCode);
	}

	@Override
	public void submitMortgage(ToMortgage toMortgage, List<RestVariable> variables, String taskId,
			String processInstanceId) {
		
		// 保存贷款信息
		ToMortgage mortgage= findToMortgageById(toMortgage.getPkid());
		mortgage.setApprDate(toMortgage.getApprDate());
		mortgage.setRemark(toMortgage.getRemark());
		saveToMortgage(mortgage);
		
		// 提交任务
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
		workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
		
		// 发送消息
		ToWorkFlow wf=new ToWorkFlow();
		wf.setCaseCode(toMortgage.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if(wordkFlowDB!=null) {
			messageService.sendMortgageFinishMsgByIntermi(wordkFlowDB.getInstCode());
		}
		
		// 结束当前流程
		ToWorkFlow workFlowOld =new ToWorkFlow();
		// 流程结束状态
		workFlowOld.setStatus("4");
		workFlowOld.setInstCode(processInstanceId);
		toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
		
	}

}
