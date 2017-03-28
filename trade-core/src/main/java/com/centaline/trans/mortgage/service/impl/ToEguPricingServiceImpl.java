package com.centaline.trans.mortgage.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.repository.ToEguPricingMapper;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEvaReportService;

@Service
public class ToEguPricingServiceImpl implements ToEguPricingService{

	@Autowired
	private ToEguPricingMapper toEguPricingMapper;
	
	@Autowired
	private ToEvaReportService toEvaReportService;
	
	@Override
	public void saveToEguPricing(ToEguPricing toEguPricing) {
		if(toEguPricing.getPkid() == null){
			toEguPricingMapper.insert(toEguPricing);
		}else{
			toEguPricingMapper.update(toEguPricing);
		}
	}

	@Override
	public void updateToEguPricing(ToEguPricing toEguPricing) {
		toEguPricingMapper.update(toEguPricing);
		
	}

	@Override
	public List<ToEguPricing> findToEguPricingByEvaCode(String evaCode) {
		return toEguPricingMapper.findToEguPricingByEvaCode(evaCode);
	}

	@Override
	public ToEguPricing findById(Long pkid) {
		
		return toEguPricingMapper.findById(pkid);
	}

	@Override
	public ToEguPricing findIsFinalEguPricing(String caseCode) {
		List<ToEguPricing> list = toEguPricingMapper.findIsFinalEguPricing(caseCode);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void bindEvaCode(ToEguPricing toEguPricing) {
		List<ToEguPricing> list = findToEguPricingByEvaCode(toEguPricing.getEvaCode());
		if(CollectionUtils.isEmpty(list)){
			throw new BusinessException("没有找到这个评估编号对应记录！");
		}
		ToEguPricing eguPricing = list.get(0);
		eguPricing.setCaseCode(toEguPricing.getCaseCode());
		eguPricing.setIsMainLoanBank(toEguPricing.getIsMainLoanBank());
		if(StringUtils.isNotEmpty(toEguPricing.getIsFinal())){
			eguPricing.setIsFinal(toEguPricing.getIsFinal());
		}
		saveToEguPricing(eguPricing);	
		ToEvaReport toEvaReport = new ToEvaReport();
		toEvaReport.setEvaCode(toEguPricing.getEvaCode());
		List<ToEvaReport> reportList = toEvaReportService.findToEvaReportByEvaCode(toEvaReport);
		//手机端发的报告申请，绑定案件编号
		if(CollectionUtils.isNotEmpty(reportList)){
			for(ToEvaReport report : reportList){
				report.setCaseCode(toEguPricing.getCaseCode());
				toEvaReportService.updateToEvaReport(report);
			}
		}
	}
}
