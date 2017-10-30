package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToRatePayment;
import com.centaline.trans.task.repository.ToRatePaymentMapper;
import com.centaline.trans.task.service.ToRatePaymentService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 缴税实现类
 * @author wbzhouht
 *
 */
@Service
public class ToRatePaymentServiceImpl implements ToRatePaymentService {
	@Autowired
	private ToRatePaymentMapper toRatePaymentMapper;
	@Autowired(required=true)
	private ToCaseService toCaseService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@RequestMapping(value="process")
	/**
	 * 保存缴税信息
	 */
	@Override
	public boolean saveRatePayment(ToRatePayment toRatePayment) {
		if (toRatePayment == null || toRatePayment.getCaseCode() == null
				|| toRatePayment.getCaseCode().intern().isEmpty()) {
			return false;
		}
		toRatePayment
				.setBusinessTax(toRatePayment.getBusinessTax() != null?toRatePayment
						.getBusinessTax().multiply(new BigDecimal(10000))
						: null);
		toRatePayment
				.setContractTax(toRatePayment.getContractTax() != null?toRatePayment
						.getContractTax().multiply(new BigDecimal(10000))
						: null);
		toRatePayment
				.setPersonalIncomeTax(toRatePayment.getPersonalIncomeTax() != null ? toRatePayment
						.getPersonalIncomeTax().multiply(new BigDecimal(10000))
						: null);
		toRatePayment
				.setLandIncrementTax(toRatePayment.getLandIncrementTax() != null ? toRatePayment
						.getLandIncrementTax().multiply(new BigDecimal(10000))
						: null);
		toRatePayment
				.setPricingTax(toRatePayment.getPricingTax() != null ? toRatePayment
						.getPricingTax().multiply(new BigDecimal(10000))
						: null);
		ToRatePayment toRatePayment2=toRatePaymentMapper.findToRatePaymentByCaseCode(toRatePayment
				.getCaseCode());
		if (toRatePayment2!= null) {
			toRatePayment.setPkid(toRatePayment2.getPkid());
			if (toRatePaymentMapper.updateByPrimaryKeySelective(toRatePayment)>0) {
				return true;
			}
		}else {
			if (toRatePayment2 == null) {
				if (toRatePaymentMapper.insertSelective(toRatePayment) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据案件编号查询缴费信息
	 */
	@Override
	public ToRatePayment qureyToRatePayment(String caseCode) {
		ToRatePayment toRatePayment=toRatePaymentMapper.findToRatePaymentByCaseCode(caseCode);
		if (toRatePayment!=null) {
			toRatePayment
					.setBusinessTax(toRatePayment.getBusinessTax() != null ? toRatePayment
							.getBusinessTax().divide(new BigDecimal(10000))
							: null);
			toRatePayment
					.setContractTax(toRatePayment.getContractTax() != null ? toRatePayment
							.getContractTax().divide(new BigDecimal(10000))
							: null);
			toRatePayment.setPersonalIncomeTax(toRatePayment
					.getPersonalIncomeTax() != null ? toRatePayment
					.getPersonalIncomeTax().divide(new BigDecimal(10000))
					: null);
			toRatePayment
					.setLandIncrementTax(toRatePayment.getLandIncrementTax() != null ? toRatePayment
							.getLandIncrementTax()
							.divide(new BigDecimal(10000)) : null);
			toRatePayment
					.setPricingTax(toRatePayment.getPricingTax()!=null?toRatePayment
							.getPricingTax()
							.divide(new BigDecimal(10000)):null);
		}
		return toRatePayment;
	}

	@Override
	public Boolean sumbitRatePayment(HttpServletRequest request,ToRatePayment toRatePayment, String taskId, String processInstanceId) throws Exception{
		saveRatePayment(toRatePayment);
		List<RestVariable> restVariables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toRatePayment
				.getCaseCode());
		return workFlowManager.submitTask(restVariables, taskId, processInstanceId,
				toCase.getLeadingProcessId(), toRatePayment.getCaseCode());
	}

}
