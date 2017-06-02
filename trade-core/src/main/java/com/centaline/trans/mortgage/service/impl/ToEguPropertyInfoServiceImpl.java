package com.centaline.trans.mortgage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;
import com.centaline.trans.mortgage.repository.ToEguPropertyInfoMapper;
import com.centaline.trans.mortgage.service.ToEguPropertyInfoService;

@Service
public class ToEguPropertyInfoServiceImpl implements ToEguPropertyInfoService {

	@Autowired
	private ToEguPropertyInfoMapper toEguPropertyInfoMapper;
	
	@Override
	public ToEguPropertyInfo saveToEguPropertyInfo(ToEguPropertyInfo toEguPropertyInfo) {
		if(toEguPropertyInfo.getPkid() == null){
			toEguPropertyInfoMapper.insertSelective(toEguPropertyInfo);
		}else{
			toEguPropertyInfoMapper.updateByPrimaryKeySelective(toEguPropertyInfo);
		}
		return toEguPropertyInfo;
	}

	@Override
	public ToEguPropertyInfo findByEvaCode(String evaCode) {
		
		return toEguPropertyInfoMapper.findByEvaCode(evaCode);
	}

}
