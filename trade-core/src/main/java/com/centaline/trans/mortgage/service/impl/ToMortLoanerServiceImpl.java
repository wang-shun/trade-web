package com.centaline.trans.mortgage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortLoaner;
import com.centaline.trans.mortgage.repository.ToMortLoanerMapper;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.ToMortLoanerService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
public class ToMortLoanerServiceImpl implements ToMortLoanerService {

	@Autowired
	private ToMortgageMapper toMortgageMapper;	

	@Autowired(required = true)
	private ToMortLoanerMapper toMortLoanerMapper;

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
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;

	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamTemplateService uamTemplateService;
	@Qualifier("uamMessageServiceClient")
	@Autowired
	private UamMessageService uamMessageService;

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ToPropertyInfoService toPropertyInfoService;

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:查询信贷员接单信息
	 */
	
	@Override
	public ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(String caseCode, String loanerStatus) {
		
		ToMortLoaner toMortLoaner = new ToMortLoaner();
		Map<String,String> map = new HashMap<String,String>();
		map.put("caseCode", caseCode);
		map.put("loanerStatus", loanerStatus);
		try{
			toMortLoaner  = toMortLoanerMapper.findToMortLoanerByCaseCodeAndLoanerStatus(map);

		}catch (BusinessException e) {
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
		
		if(null == toMortLoaner){
			throw new BusinessException("交易顾问从新派单请求数据异常");
		}
		
		try{
			toMortLoanerMapper.insertSelective(toMortLoaner);
		}catch(BusinessException e) {
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
		if(null == toMortLoaner){
			throw new BusinessException("信贷员接单请求数据异常");
		}
		
		try{
			toMortLoanerMapper.updateByPrimaryKeySelective(toMortLoaner);
		}catch(BusinessException e) {
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
		try{
			toMortLoaner  = toMortLoanerMapper.findToMortLoanerByCaseCode(caseCode);

		}catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}		
		return toMortLoaner;
	}


	@Override
	public ToMortLoaner getToMortLoanerByMortgageId(String mortgageId) {
		return toMortLoanerMapper.getToMortLoanerByMortgageId(mortgageId);
	}
	
	
	@Override
	public ToMortLoaner findToMortLoanerByCaseCodeAndIsMainBank(String caseCode,String isMainLoanBankProcess){
		
		ToMortLoaner toMortLoaner = new ToMortLoaner();
		Map<String,String> map = new HashMap<String,String>();
		map.put("caseCode", caseCode);
		map.put("isMainLoanBankProcess", isMainLoanBankProcess);
		try{
			toMortLoaner  = toMortLoanerMapper.findToMortLoanerByCaseCodeAndIsMainBank(map);

		}catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}		
		return toMortLoaner;
	}
	
	
	@Override
	public ToMortLoaner findToMortLoaner(ToMortLoaner toMortLoaner){
		
		if(null == toMortLoaner) throw new BusinessException("查询信贷员派单信息参数异常");
		ToMortLoaner toMortLoanerProcess = new  ToMortLoaner();
		try{
			toMortLoanerProcess  = toMortLoanerMapper.findToMortLoaner(toMortLoaner);

		}catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单信息异常");
		}		
		return toMortLoanerProcess;
	}
}
