package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ReportOperateData;
import com.centaline.trans.task.repository.ReportOperateDataMapper;
import com.centaline.trans.task.service.ReportOperateDataService;

@Service
public class ReportOperateDataServiceImpl implements ReportOperateDataService {

	@Autowired
	private ReportOperateDataMapper reportOperateDataMapper;

	@Override
	public List<List<ReportOperateData>> getReportOperateDataOne(int year) {

		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();
		List<ReportOperateData> transferList = reportOperateDataMapper.getReportOperateData(year);/** 过户数据（签贷款）*/
		List<ReportOperateData> loanList = reportOperateDataMapper.getReportOperateDataToMortSignDate(year);/**贷款签约数据**/
		reportOperateDataList.add(0,transferList);
		reportOperateDataList.add(1,loanList);
		return reportOperateDataList;
	}
	
	@Override
	public List<List<ReportOperateData>> getReportOperateDataTwo(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 过户数据*/
		List<ReportOperateData> transferList = reportOperateDataMapper.getReportOperateDataTwo(year);
		reportOperateDataList.add(0,transferList);
		return reportOperateDataList;
	}
	
	@Override
	public List<List<ReportOperateData>> getReportOperateDataThree(int year) {
		
		List<List<ReportOperateData>> reportOperateDataList = new ArrayList<List<ReportOperateData>>();/** 签贷款数据*/
		List<ReportOperateData> transferList = reportOperateDataMapper.getReportOperateDataThree(year);
		reportOperateDataList.add(0,transferList);
		return reportOperateDataList;
	}
}
