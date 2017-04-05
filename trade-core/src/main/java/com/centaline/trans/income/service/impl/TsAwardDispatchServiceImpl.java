package com.centaline.trans.income.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.income.entity.TsAwardDispatch;
import com.centaline.trans.income.repository.TsAwardDispatchMapper;
import com.centaline.trans.income.service.TsAwardDispatchService;

@Service
public class TsAwardDispatchServiceImpl implements TsAwardDispatchService{

	@Autowired
	private TsAwardDispatchMapper tsAwardDispatchMapper;

	@Override
	public void saveTsAwardDispatch(TsAwardDispatch tsAwardDispatch) {
		TsAwardDispatch awardDispatch = tsAwardDispatchMapper.findTsAwardDispatchByParticipant(tsAwardDispatch);
		if(awardDispatch != null){
			//计件工资导入判断
			if(tsAwardDispatch.getBaseAmount() != null && awardDispatch.getBaseAmount() != null){
				throw new BusinessException("案件编号："+tsAwardDispatch.getCaseCode()+"的计件工资已导入过！");
			}
			if(tsAwardDispatch.getPhoneAccuracy() != null && tsAwardDispatch.getSatisfyRating() != null && awardDispatch.getPhoneAccuracy()!=null){
				throw new BusinessException("案件编号："+tsAwardDispatch.getCaseCode()+"的评分已导入过！");
			}
			awardDispatch.setBaseAmount(tsAwardDispatch.getBaseAmount());
			awardDispatch.setPhoneAccuracy(tsAwardDispatch.getPhoneAccuracy());
			awardDispatch.setSatisfyRating(tsAwardDispatch.getSatisfyRating());
			tsAwardDispatchMapper.updateByPrimaryKeySelective(awardDispatch);
		}else{
			tsAwardDispatchMapper.insertSelective(tsAwardDispatch);
		}
	}
	
	@Override
	public void saveTsAwardDispatchBatch(List<TsAwardDispatch> tsAwardDispatchList){
		if(CollectionUtils.isNotEmpty(tsAwardDispatchList)){
			for(TsAwardDispatch tsAwardDispatch : tsAwardDispatchList){
				saveTsAwardDispatch(tsAwardDispatch);
			}
		}
	}

}
