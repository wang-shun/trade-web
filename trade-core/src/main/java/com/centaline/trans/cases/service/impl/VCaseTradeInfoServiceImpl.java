package com.centaline.trans.cases.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.cases.repository.VCaseTradeInfoMapper;
import com.centaline.trans.cases.service.VCaseTradeInfoService;

@Service
public class VCaseTradeInfoServiceImpl implements VCaseTradeInfoService {

	@Autowired
	private VCaseTradeInfoMapper vCaseTradeInfoMapper;

	@Override
	public VCaseTradeInfo queryCaseTradeInfoByCaseCode(String caseCode) {

		// 过户审批环节添加网签环节、过户环节提交时间 add by zhuody in 20170526
		if (null == caseCode || "".equals(caseCode)) {
			throw new BusinessException("查询网签、过户提交时间时请求参数为空！");
		}
		
		VCaseTradeInfo caseTradeInfo = new VCaseTradeInfo();
		caseTradeInfo = vCaseTradeInfoMapper.queryCaseTradeInfoByCaseCode(caseCode);

		Date transSignSubTime = vCaseTradeInfoMapper.selectTransSignSubTime(caseCode);
		Date guohuSubTime = vCaseTradeInfoMapper.selectGuohuSubTime(caseCode);

		if (null != caseTradeInfo) {
			if (null != transSignSubTime) {
				caseTradeInfo.setTransSignSubTime(transSignSubTime);
			}

			if (null != guohuSubTime) {
				caseTradeInfo.setGuohuSubTime(guohuSubTime);				
			}
			
			long transSignDays = getDistanceDaysByOrder(transSignSubTime, caseTradeInfo.getRealConTime());
			caseTradeInfo.setTransSignSubAndRealTimeDiff(transSignDays);
			long guohuDays = getDistanceDaysByOrder(guohuSubTime, caseTradeInfo.getRealHtTime());
			caseTradeInfo.setGuohuSubAndRealTimeDiff(guohuDays);
		}

		return caseTradeInfo;
	}

	
    /*
     * @author:zhuody
     * 
     * @date:2017-05-27
     * 
     * @des:计算时间差，考虑时间大小
     */
	private long getDistanceDaysByOrder(Date subTime, Date realTime) {
		
		if(null == subTime || null == realTime){
			return  0;
		}
		
		long days = 0;
		long time1 = subTime.getTime();
		long time2 = realTime.getTime();
		long diff = time1 - time2;
		
		days = diff / (1000 * 60 * 60 * 24);

		return days;
	}
    /*
     * @author:zhuody
     * 
     * @date:2017-05-26
     * 
     * @des:计算时间差,不考虑时间大小
     */
	
	@SuppressWarnings("unused")
	private long getDistanceDays(Date subTime, Date realTime) {
		
		if(null == subTime || null == realTime){
			return  0;
		}
		
		long days = 0;
		long time1 = subTime.getTime();
		long time2 = realTime.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		days = diff / (1000 * 60 * 60 * 24);

		return days;
	}

}
