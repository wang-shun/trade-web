package com.centaline.trans.eval.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.enums.EvalWaitAccountEnum;
import com.centaline.trans.common.enums.FeeChangeTypeEnum;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.repository.ToEvalSettleMapper;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalSettleService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;

@Service
public class ToEvalSettleServiceImpl implements ToEvalSettleService {
	
	
	@Autowired
	private ToEvalSettleMapper toEvalSettleMapper; 
	
	@Autowired
	private TsFinOrgMapper tsFinOrgMapper; 
	
	@Autowired(required = true)
	ToEvalSettleService toEvalSettleService;
	
	@Autowired(required = true)
	ToEvalRebateService toEvalRebateService;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvalSettle record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvalSettle record) {
		return toEvalSettleMapper.insertSelective(record);
	}

	@Override
	public ToEvalSettle selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalSettle toEvalSettle) {
		return toEvalSettleMapper.updateByPrimaryKeySelective(toEvalSettle);
	}

	@Override
	public int updateByPrimaryKey(ToEvalSettle record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateByCaseCode(record);
	}
	
	@Override
	public int updateSettleTimeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateSettleTimeByCaseCode(record);
	}
	
	@Override
	public ToEvalSettle findToCaseByCaseCode(String caseCode) {
		return toEvalSettleMapper.findToCaseByCaseCode(caseCode);
	}

	@Override
	public int updateSettleFeeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateSettleFeeByCaseCode(record);
	}
	
	@Override
	public int newSettleFeeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.newSettleFeeByCaseCode(record);
	}

	@Override
	public List<ToEvalSettle> findCaseCodesByStauts() {
		// TODO Auto-generated method stub
		return toEvalSettleMapper.findCaseCodesByStauts();
	}

	@Override
	public List<String> waitApproCaseCodes() {
		List<String> recordList = new ArrayList<>();
		List<ToEvalSettle> settleList =  toEvalSettleService.findCaseCodesByStauts();
		for (ToEvalSettle toEvalSettle : settleList) {
			recordList.add(toEvalSettle.getCaseCode());
		}
		return recordList;
	}
	
	//根据评估公司编号查询评估公司
	@Override
	public TsFinOrg findTsFinOrgByfinOrgCode(String finOrgCode) {
		// TODO Auto-generated method stub
		return tsFinOrgMapper.findBankByFinOrg(finOrgCode);
	}

	@Override
	public int insertWaitAccount(String caseCode,String evaCode, String feeChangeReason) {
		ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
		if(feeChangeReason.equals(FeeChangeTypeEnum.FPSD.getCode())) {//发票税点
				ToEvalSettle record = new ToEvalSettle();
				record.setCaseCode(caseCode);
				record.setEvaCode(evaCode);
				record.setFeeChangeReason(FeeChangeTypeEnum.getName(feeChangeReason));
				record.setStatus(EvalWaitAccountEnum.WTJ.getCode());//未提交
				//初始化结算费用
				if(toEvalRebate.getEvaComAmount()!= null) {
					record.setSettleFee(toEvalRebate.getEvaComAmount());
				}
				toEvalSettleMapper.insertSelective(record);
		}else {
				ToEvalSettle record = new ToEvalSettle();
				record.setCaseCode(caseCode);
				record.setEvaCode(evaCode);
				record.setFeeChangeReason(FeeChangeTypeEnum.getName(feeChangeReason));
				record.setStatus(EvalWaitAccountEnum.WXJS.getCode());//无需结算
				//初始化结算费用
				if(toEvalRebate.getEvaComAmount()!= null) {
					record.setSettleFee(toEvalRebate.getEvaComAmount());
				}
				if(feeChangeReason.equals(FeeChangeTypeEnum.WD.getCode())) {
					record.setSettleNotReason("爆单");
				}else {
					record.setSettleNotReason(FeeChangeTypeEnum.getName(feeChangeReason));
				}
				toEvalSettleMapper.insertSelective(record);
		}
		return 0;
	}
	
	

}
