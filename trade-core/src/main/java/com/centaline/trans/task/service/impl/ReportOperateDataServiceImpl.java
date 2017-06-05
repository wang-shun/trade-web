package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.vo.SpvCaseFlowOutInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.task.entity.ReportOperateData;
import com.centaline.trans.task.repository.ReportOperateDataMapper;
import com.centaline.trans.task.service.ReportOperateDataService;

@Service
public class ReportOperateDataServiceImpl implements ReportOperateDataService {

	@Autowired
	private ReportOperateDataMapper reportOperateDataMapper;

	/**
	 * 
	 * @author hejf10 [贷款签约与过户对比]
	 * @param year参数年（根据年查询一年的大数据分析）	
	 * @date 2017年3月1日14:54:52
	 * @return reportOperateDataList
	 */
	@Override
	public List<List<ReportOperateData>> getReportOperateDataOne(int year) {
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();
		List<ReportOperateData> transferList = reportOperateDataMapper.getReportOperateData(year,returnDateType(year),retYea(year));/** 过户数据（签贷款）*/
		List<ReportOperateData> loanList = reportOperateDataMapper.getReportOperateDataToMortSignDate(year,returnDateType(year),retYea(year));/**贷款签约数据**/
		
		for(ReportOperateData transfer:transferList){
			transfer.setMortComAmount(null == transfer.getMortComAmount() ? new BigDecimal(0):transfer.getMortComAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
			transfer.setMortPrfAmount(null == transfer.getMortPrfAmount() ? new BigDecimal(0):transfer.getMortPrfAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
		}
		for(ReportOperateData transfer:loanList){
			transfer.setDkmortComAmount(null == transfer.getDkmortComAmount() ? new BigDecimal(0):transfer.getDkmortComAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
			transfer.setDkmortPrfAmount(null == transfer.getDkmortPrfAmount() ? new BigDecimal(0):transfer.getDkmortPrfAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
		}
		
		reportOperateDataList.add(0,transferList);
		reportOperateDataList.add(1,loanList);
		return reportOperateDataList;
	}
	/**
	 * 
	 * @author hejf10 [过户数据]
	 * @param year参数年（根据年查询一年的大数据分析）	
	 * @date 2017年3月1日14:54:52
	 * @return reportOperateDataList
	 */
	@Override
	public List<List<ReportOperateData>> getReportOperateDataTwo(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 过户数据*/
		List<ReportOperateData> transferList = reportOperateDataMapper.getReportOperateDataTwo(year,returnDateType(year),retYea(year));
	/*	for(ReportOperateData transfer:transferList){
			if(null != transfer.getMortComAmount() && null != transfer.getMortPrfAmount()){
				transfer.setLeverage(((transfer.getMortComAmount().add(transfer.getMortPrfAmount())).divide(transfer.getAllRealPrice(), 0, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)));
			}else{
				transfer.setLeverage(new BigDecimal(0));
			}
			transfer.setSdAmount(null == transfer.getSdAmount()?new BigDecimal(0):transfer.getSdAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
			transfer.setMortComAmount(null == transfer.getMortComAmount() ? new BigDecimal(0):transfer.getMortComAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
			transfer.setMortPrfAmount(null == transfer.getMortPrfAmount()? new BigDecimal(0) :transfer.getMortPrfAmount().divide(new BigDecimal(10000), 0, RoundingMode.HALF_UP));
			
		}*/
		reportOperateDataList.add(0,transferList);
		return reportOperateDataList;
	}
	/**
	 * 
	 * @author hejf10 [签贷款数据]
	 * @param year参数年（根据年查询一年的大数据分析）	
	 * @date 2017年3月1日14:54:52
	 * @return reportOperateDataList
	 */
	@Override
	public List<List<ReportOperateData>> getReportOperateDataThree(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 签贷款数据*/
		List<ReportOperateData> transferList = reportOperateDataMapper.getReportOperateDataThree(year,returnDateType(year),retYea(year));
		
		reportOperateDataList.add(0,transferList);
		return reportOperateDataList;
	}
	/**
	 * 
	 * @author hejf10 [签贷款数据]
	 * @param year参数年（根据年查询一年的大数据分析）	
	 * @date 2017年3月1日14:54:52
	 * @return reportOperateDataList
	 */
	@Override
	public List<List<ReportOperateData>> getReportOperateDataToPdl(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 签贷款数据*/
		List<ReportOperateData> transferToPdlList = reportOperateDataMapper.getReportOperateDataToPdl(year,returnDateType(year),retYea(year));
		
		reportOperateDataList.add(0,transferToPdlList);
		return reportOperateDataList;
	}
	/**
	 * 
	 * @author hejf10 [签贷款数据]
	 * @param year参数年（根据年查询一年的大数据分析）	
	 * @date 2017年3月1日14:54:52
	 * @return reportOperateDataList
	 */
	@Override
	public List<List<ReportOperateData>> getReportOperateDataToCyl(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 签贷款数据*/
		List<ReportOperateData> transferToCylList = reportOperateDataMapper.getReportOperateDataToCyl(year,returnDateType(year),retYea(year));
		
		reportOperateDataList.add(0,transferToCylList);
		return reportOperateDataList;
	}
	/**
	 * 
	 * @author hejf10 [签贷款数据]
	 * @param year参数年（根据年查询一年的大数据分析）	
	 * @date 2017年3月1日14:54:52
	 * @return reportOperateDataList
	 */
	@Override
	public List<List<ReportOperateData>> getReportOperateDataToghl(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 签贷款数据*/
		List<ReportOperateData> transferToghlList = reportOperateDataMapper.getReportOperateDataToghl(year,returnDateType(year),retYea(year));
		
		reportOperateDataList.add(0,transferToghlList);
		return reportOperateDataList;
	}
	/**
	 * 
	 * @author hejf10 [得到当前年月]
	 * @param type参数 month year	
	 * @date 2017年3月13日14:54:52
	 * @return 当前年月
	 */
	public String returnDateType(int year) {
		Calendar cal = Calendar.getInstance();
	    int month = cal.get(Calendar.MONTH)+1 ;
	    int years = cal.get(Calendar.YEAR);
	    if(year>=2017 && years>=2017 && month >=2){
	    return "Y";}else{
	    	return null;}
	}
	/**
	 * 
	 * @author hejf10 [得到当前年月]
	 * @param type参数 month year	
	 * @date 2017年3月13日14:54:52
	 * @return 当前年月
	 */
	public String retYea(int year) {
		if(year==2017){
			return "Y";}else{
				return null;}
	}
	/**
	 * 除万操作
	 * @param 
	 */
	private void divideTenThousand(SpvChargeInfoVO spvChargeInfoVO){
		List<SpvCaseFlowOutInfoVO> spvCaseFlowOutInfoVOList = spvChargeInfoVO.getSpvCaseFlowOutInfoVOList();
		if(spvCaseFlowOutInfoVOList != null && !spvCaseFlowOutInfoVOList.isEmpty()){
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvCaseFlowOutInfoVOList){
				ToSpvCashFlow toSpvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
				toSpvCashFlow.setAmount(toSpvCashFlow.getAmount() == null?null:toSpvCashFlow.getAmount().divide(new BigDecimal(10000)));
			}		
		}
	}
}
