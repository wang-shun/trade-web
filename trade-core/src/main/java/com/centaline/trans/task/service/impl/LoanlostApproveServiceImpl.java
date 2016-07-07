package com.centaline.trans.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
import com.centaline.trans.task.service.LoanlostApproveService;

@Service
public class LoanlostApproveServiceImpl implements LoanlostApproveService {
	
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper;
	@Autowired
	private ToMortgageMapper toMortgageMapper;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private TsFinOrgMapper tsFinOrgMapper;

	@Override
	public Boolean saveLoanlostApprove(ToApproveRecord toApproveRecord) {
		if(StringUtils.isBlank(toApproveRecord.getCaseCode())) {
			return false;
		}
		if(toApproveRecord.getPkid() != null) {
			toApproveRecordMapper.updateByPrimaryKeySelective(toApproveRecord);
		} else {
			toApproveRecordMapper.insertSelective(toApproveRecord);
		}
		return true;
	}

	@Override
	public List<String> findApproveRecordByList(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByList(toApproveRecord);
	}
	
	@Override
	public ToApproveRecord findLastApproveRecord(ToApproveRecord toApproveRecord){
		return toApproveRecordMapper.findLastApproveRecord(toApproveRecord);
	}
	@Override
	public Map<String, Object> queryCaseInfo(String caseCode, String partCode,String instCode) {
		ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCode(caseCode);
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		Map<String, Object> caseDetail = new HashMap<String, Object>();
		caseDetail.put("caseCode", caseCode);
		if(toMortgage != null) {
			TsFinOrg tsFinOrg = tsFinOrgMapper.findBankByFinOrg(toMortgage.getLastLoanBank());
			if(tsFinOrg != null)
			caseDetail.put("lastLoanBank", tsFinOrg.getFinOrgName());
			caseDetail.put("mortTotalAmount", toMortgage.getMortTotalAmount());
			caseDetail.put("content", toMortgage.getSelfDelReason());
		} 
		if(toPropertyInfo != null) {
			caseDetail.put("propertyAddr", toPropertyInfo.getPropertyAddr());
		}
		
		return caseDetail;
	}

}
