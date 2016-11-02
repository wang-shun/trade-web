package com.centaline.trans.task.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToEguPricingMapper;
import com.centaline.trans.mortgage.repository.ToEvaReportMapper;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.task.service.OfflineEvaService;
import com.centaline.trans.task.vo.OfflineEvaVO;

@Service
public class OfflineEvaServiceImpl implements OfflineEvaService {

	@Autowired
	private ToEvaReportMapper toEvaReportMapper;
	@Autowired
	private TsFinOrgMapper tsFinOrgMapper;
	
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private ToMortgageMapper toMortgageMapper;
	
	@Autowired
	private ToEguPricingMapper toEguPricingMapper;
	
	@Override
	public OfflineEvaVO queryOfflineEvaVO(String processId) {
		OfflineEvaVO offlineEvaVO = new OfflineEvaVO();
		
		/*评估公司*/
		ToEvaReport evaReport = toEvaReportMapper.findByProcessId(processId);
		if(evaReport != null) {
			offlineEvaVO.setReportType(evaReport.getReportType());
			offlineEvaVO.setPkid(evaReport.getPkid());
			offlineEvaVO.setStatus(evaReport.getStatus());
			offlineEvaVO.setReportResponseTime(evaReport.getReportResponseTime());
			offlineEvaVO.setComment(evaReport.getComment());
			
			List<ToEguPricing> toEguPricingList = toEguPricingMapper.findIsFinalEguPricing(evaReport.getCaseCode());
			if(CollectionUtils.isNotEmpty(toEguPricingList)){
				offlineEvaVO.setExpectedPrice(toEguPricingList.get(0).getExpectRate()!=null?(toEguPricingList.get(0).getExpectRate()/10000):null);
			}
			/**读取物业信息*/
			ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(evaReport.getCaseCode());
			if(toPropertyInfo!=null) {
				offlineEvaVO.setPropertyAddr(toPropertyInfo.getPropertyAddr());
				offlineEvaVO.setTotalFloor(toPropertyInfo.getTotalFloor());
				offlineEvaVO.setLocateFloor(toPropertyInfo.getLocateFloor());
				offlineEvaVO.setSquare(toPropertyInfo.getSquare());
			}
			
			/*贷款信息*/
			ToMortgage mortgage = new ToMortgage();
			mortgage.setCaseCode(evaReport.getCaseCode());
			List<ToMortgage> toMortgageList = toMortgageMapper.findToMortgageByCaseCodeAndBankType(mortgage);
			if(CollectionUtils.isNotEmpty(toMortgageList)) {
				ToMortgage toMortgage = toMortgageList.get(0);
				//最终贷款银行
				if(toMortgage.getLastLoanBank() != null) {
					TsFinOrg tsFinOrg = tsFinOrgMapper.findBankByFinOrg(toMortgage.getLastLoanBank());
					offlineEvaVO.setLastLoanBank(tsFinOrg==null?"":tsFinOrg.getFinOrgName());
				}
				offlineEvaVO.setLoanerName(toMortgage.getLoanerName());
				offlineEvaVO.setLoanerPhone(toMortgage.getLoanerPhone());
				//贷款银行
				if(StringUtils.isNotEmpty(toMortgage.getFinOrgCode())){
					TsFinOrg loanBank = tsFinOrgMapper.findBankByFinOrg(evaReport.getFinOrgCode());
					offlineEvaVO.setFinOrgName(loanBank.getFinOrgName());
				}

			}
			//评估公司
			if(StringUtils.isNotEmpty(evaReport.getFinOrgCode())){
				TsFinOrg reportCom = tsFinOrgMapper.findBankByFinOrg(evaReport.getFinOrgCode());
				offlineEvaVO.setFinOrgName(reportCom.getFinOrgName());
			}
		}
		
		return offlineEvaVO;
	}

	@Override
	public Boolean saveEvaReport(ToEvaReport toEvaReport) {
		return null;
	}

}
