package com.centaline.trans.mortgage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.mortgage.entity.ToMortLoaner;
import com.centaline.trans.mortgage.repository.ToMortLoanerMapper;
import com.centaline.trans.mortgage.service.ToMortLoanerService;


@Service
public class ToMortLoanerServiceImpl implements ToMortLoanerService {



	@Autowired(required = true)
	private ToMortLoanerMapper toMortLoanerMapper;

	@Autowired
	MessageService messageService;
	
	@Autowired(required = true)
	UamSessionService uamSessionService;

	@Qualifier("uamMessageServiceClient")
	@Autowired
	private UamMessageService uamMessageService;
	@Qualifier("qqcdDictLoanerStatus")
	@Autowired
	private CustomDictService qqcdDictLoanerStatus;

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:查询信贷员接单信息
	 */

	@Override
	public ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(
			String caseCode, String loanerStatus) {

		ToMortLoaner toMortLoaner = new ToMortLoaner();
		Map<String, String> map = new HashMap<String, String>();
		map.put("caseCode", caseCode);
		map.put("loanerStatus", loanerStatus);
		try {
			toMortLoaner = toMortLoanerMapper
					.findToMortLoanerByCaseCodeAndLoanerStatus(map);

		} catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}
		return toMortLoaner;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:ToMortLoaner添加
	 */

	@Override
	public void insertByToMortLoaner(ToMortLoaner toMortLoaner) {

		if (null == toMortLoaner) {
			throw new BusinessException("交易顾问从新派单请求数据异常");
		}

		try {
			toMortLoanerMapper.insertSelective(toMortLoaner);
		} catch (BusinessException e) {
			throw new BusinessException("交易顾问从新派单保存数据异常");
		}

	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:ToMortLoaner更新
	 */
	@Override
	public void updateByPrimaryKeySelective(ToMortLoaner toMortLoaner) {
		if (null == toMortLoaner) {
			throw new BusinessException("信贷员接单请求数据异常");
		}

		try {
			toMortLoanerMapper.updateByPrimaryKeySelective(toMortLoaner);
		} catch (BusinessException e) {
			throw new BusinessException("信贷员接单更新数据异常");
		}

	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-14
	 * 
	 * @des:查询信贷员接单信息（特殊情况 信贷员驳回、银行驳回时）
	 */

	@Override
	public ToMortLoaner findToMortLoanerByCaseCode(String caseCode) {

		ToMortLoaner toMortLoaner = new ToMortLoaner();
		try {
			toMortLoaner = toMortLoanerMapper
					.findToMortLoanerByCaseCode(caseCode);

		} catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}
		return toMortLoaner;
	}

	@Override
	public ToMortLoaner getToMortLoanerById(Long id) {
		return toMortLoanerMapper.getToMortLoanerById(id);
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-19
	 * 
	 * @des:根据caseCode和是否主贷流程查询信贷员派单情况
	 */
	@Override
	public List<ToMortLoaner> findToMortLoanerByCaseCodeAndIsMainBank(
			String caseCode, String isMainLoanBankProcess) {

		List<ToMortLoaner> toMortLoanerList = new ArrayList<ToMortLoaner>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("caseCode", caseCode);
		map.put("isMainLoanBankProcess", isMainLoanBankProcess);
		try {
			toMortLoanerList = toMortLoanerMapper
					.findToMortLoanerByCaseCodeAndIsMainBank(map);

		} catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}
		return toMortLoanerList;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-19
	 * 
	 * @des:根据caseCode和派单状态查询信贷员派单是否可以派单
	 */
	@Override
	public List<ToMortLoaner> findToMortLoaner(ToMortLoaner toMortLoaner) {

		if (null == toMortLoaner)
			throw new BusinessException("查询信贷员派单信息参数异常");
		List<ToMortLoaner> toMortLoanerProcessList = new ArrayList<ToMortLoaner>();
		try {
			toMortLoanerProcessList = toMortLoanerMapper.findToMortLoaner(toMortLoaner);

		} catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}
		return toMortLoanerProcessList;
	}

	@Override
	public ToMortLoaner findActiveToMortLoaner(String caseCode, String isMainLoanBankProcess) {
		return toMortLoanerMapper.findActiveToMortLoaner(caseCode,isMainLoanBankProcess);
	}
	@Override
	public ToMortLoaner findLastToMortLoaner(String caseCode, String isMainLoanBankProcess) {
		ToMortLoaner loaner= toMortLoanerMapper.findLastToMortLoaner(caseCode,isMainLoanBankProcess);
		if(loaner!=null){
			loaner.setLoanerStatusStr(qqcdDictLoanerStatus.getValue(loaner.getLoanerStatus()));
		}
		return loaner;
	}
	
}
