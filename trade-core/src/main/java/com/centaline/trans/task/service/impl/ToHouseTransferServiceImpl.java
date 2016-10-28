package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.repository.ToHouseTransferMapper;
import com.centaline.trans.task.service.AwardBaseService;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;

@Service
public class ToHouseTransferServiceImpl implements ToHouseTransferService {

	@Autowired
	private ToHouseTransferMapper toHouseTransferMapper;
	@Autowired
	private ToMortgageService toMortgageService;
	
	@Autowired(required = true)
	private ToCaseService toCaseService;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	@Autowired
	private AwardBaseService awardBaseService;
	
	@Override
	public boolean saveToHouseTransfer(ToHouseTransfer toHouseTransfer) {
		if(StringUtils.isBlank(toHouseTransfer.getCaseCode())) {
			return false;
		}
		if(toHouseTransfer.getPkid() != null) {
			if(toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer) > 0) {
				return true;
			}
		} else {
			if(toHouseTransferMapper.findToGuoHuByCaseCode(toHouseTransfer.getCaseCode()) == null) {
				if(toHouseTransferMapper.insertSelective(toHouseTransfer) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void saveToHouseTransferAndMort(ToHouseTransfer toHouseTransfer,ToMortgage toMortgage) {

		toHouseTransfer.setBusinessTax(toHouseTransfer.getBusinessTax()!=null?toHouseTransfer.getBusinessTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setContractTax(toHouseTransfer.getContractTax()!=null?toHouseTransfer.getContractTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setHouseHodingTax(toHouseTransfer.getHouseHodingTax()!=null?toHouseTransfer.getHouseHodingTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setLandIncrementTax(toHouseTransfer.getLandIncrementTax()!=null?toHouseTransfer.getLandIncrementTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setPersonalIncomeTax(toHouseTransfer.getPersonalIncomeTax()!=null?toHouseTransfer.getPersonalIncomeTax().multiply(new BigDecimal(10000)):null);
		if(toHouseTransfer.getPkid() != null) {
			toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer);
			
		} else {
			if(toHouseTransferMapper.findToGuoHuByCaseCode(toHouseTransfer.getCaseCode()) == null) {
				toHouseTransferMapper.insertSelective(toHouseTransfer);
				
			}
		}
		ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(toHouseTransfer.getCaseCode());
		if(mortgage != null){
			mortgage.setMortType(toMortgage.getMortType());
			mortgage.setMortTotalAmount(toMortgage.getMortTotalAmount()!=null?toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)):null);
			mortgage.setComAmount(toMortgage.getComAmount() != null?toMortgage.getComAmount().multiply(new BigDecimal(10000)):null);
			mortgage.setComYear(toMortgage.getComYear());
			mortgage.setComDiscount(toMortgage.getComDiscount());
			mortgage.setPrfAmount(toMortgage.getPrfAmount()!=null?toMortgage.getPrfAmount().multiply(new BigDecimal(10000)):null);
			mortgage.setPrfYear(toMortgage.getPrfYear());
			toMortgageService.saveToMortgage(mortgage);
		}
	}
	@Override
	public ToHouseTransfer findToGuoHuByCaseCode(String caseCode) {
		ToHouseTransfer toHouseTransfer = toHouseTransferMapper.findToGuoHuByCaseCode(caseCode);
		if(null!=toHouseTransfer){
			toHouseTransfer.setBusinessTax(toHouseTransfer.getBusinessTax()!=null?toHouseTransfer.getBusinessTax().divide(new BigDecimal(10000)):null);
			toHouseTransfer.setContractTax(toHouseTransfer.getContractTax()!=null?toHouseTransfer.getContractTax().divide(new BigDecimal(10000)):null);
			toHouseTransfer.setHouseHodingTax(toHouseTransfer.getHouseHodingTax()!=null?toHouseTransfer.getHouseHodingTax().divide(new BigDecimal(10000)):null);
			toHouseTransfer.setLandIncrementTax(toHouseTransfer.getLandIncrementTax()!=null?toHouseTransfer.getLandIncrementTax().divide(new BigDecimal(10000)):null);		
			toHouseTransfer.setPersonalIncomeTax(toHouseTransfer.getPersonalIncomeTax()!=null?toHouseTransfer.getPersonalIncomeTax().divide(new BigDecimal(10000)):null);
		}
		return toHouseTransfer;
	}

	@Override
	public ToCaseInfoCountVo countToHouseTransferById(String userId) {
		return toHouseTransferMapper.countToHouseTransferById(userId);
	}

	@Override
	public ToCaseInfoCountVo queryCountToHouseTransferById(ToCase toCase) {
		
		return toHouseTransferMapper.queryCountToHouseTransferById(toCase);
	}

	@Override
	public ToCaseInfoCountVo countToHouseTransferByOrgId(String orgId,String startDate,String endDate) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setStartDate(startDate);
		toCase.setEndDate(endDate);
		return toHouseTransferMapper.countToHouseTransferByOrgId(toCase);
	}

	@Override
	public ToCaseInfoCountVo queryCountToHouseTransferByOrg(ToCase toCase) {
	
		return toHouseTransferMapper.queryCountToHouseTransferByOrg(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToHouseTransferListByOrgId(String orgId) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		return toHouseTransferMapper.countToHouseTransferListByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToHouseTransferListByOrgList(
			List<String> orgList) {
		return toHouseTransferMapper.countToHouseTransferListByOrgList(orgList);
	}

	@Override
	public int countToHouseTransferByOrgList(List<String> strList,
			String startDate, String endDate) {
		
		return toHouseTransferMapper.countToHouseTransferByOrgList(strList,startDate,endDate);
	}

	@Override
	public int initCountToHouseTransferByOrgId(String orgId,String createTime) {
		ToCase toCase = new ToCase();
		toCase.setOrgId(orgId);
		toCase.setTime(createTime);
		return toHouseTransferMapper.initCountToHouseTransferByOrgId(toCase);
	}

	@Override
	public List<ToCaseInfoCountVo> countToHouseTransferListByIdList(
			List<String> idList) {
		return toHouseTransferMapper.countToHouseTransferListByIdList(idList);
	}

	@Override
	public int countToHouseTransferByIdList(List<String> idList,
			String startDate, String endDate) {
		return toHouseTransferMapper.countToHouseTransferByIdList(idList, startDate, endDate);
	}
	@Override
	public String submitToHouseTransfer(ToHouseTransfer toHouseTransfer, ToMortgage toMortgage,
			LoanlostApproveVO loanlostApproveVO, String taskId, String processInstanceId) {
		
		// 2 执行交易系统代码
		saveToHouseTransferAndMort(toHouseTransfer, toMortgage);
		/*保存过户申请*/
		saveToApproveRecord(toHouseTransfer, processInstanceId, loanlostApproveVO);
		/*佣金分配*/
		awardBaseService.doAwardCalculate(toHouseTransfer, processInstanceId);
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());	
		workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toHouseTransfer.getCaseCode());

		/*修改案件状态*/
		toCase.setStatus("30001004");
		toCaseService.updateByCaseCodeSelective(toCase);
		
		return null;
	}
	/**
	 * 保存审核记录
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(ToHouseTransfer toHouseTransfer,String processInstanceId, LoanlostApproveVO loanlostApproveVO) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceId);
		toApproveRecord.setPartCode(toHouseTransfer.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(toHouseTransfer.getCaseCode());
		toApproveRecord.setContent("过户申请");
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}
}
