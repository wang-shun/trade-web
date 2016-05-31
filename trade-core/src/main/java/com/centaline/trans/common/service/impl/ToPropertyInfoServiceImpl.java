package com.centaline.trans.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.ToPropertyInfoService;

@Service
public class ToPropertyInfoServiceImpl implements ToPropertyInfoService {

	@Autowired
	ToPropertyInfoMapper toPropertyInfoMapper;

	@Override
	public ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
	}

	@Override
	public List<ToPropertyInfo> getPropertyInfoByUserId(String userId) {
		return toPropertyInfoMapper.getPropertyInfoByUserId(userId);
	}

	@Override
	public int insertSelective(ToPropertyInfo record) {
		return toPropertyInfoMapper.insertSelective(record);
	}

	@Override
	public ToPropertyInfo findToPropertyInfoByCaseCodeAndAddr(
			ToPropertyInfo record) {
		// TODO Auto-generated method stub
		return toPropertyInfoMapper.findToPropertyInfoByCaseCodeAndAddr(record);
	}

	@Override
	public ViHouseDelBaseVo getHouseBaseByHoudelCode(String delCode) {
		return toPropertyInfoMapper.selectByHoudelCode(delCode);
	}

}
