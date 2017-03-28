package com.centaline.trans.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.ToServChangeHistroty;
import com.centaline.trans.common.repository.ToServChangeHistrotyMapper;
import com.centaline.trans.common.service.ToServChangeHistrotyService;
@Service
public class ToServChangeHistrotyServiceImpl implements
		ToServChangeHistrotyService {
	
	@Autowired
	ToServChangeHistrotyMapper toServChangeHistrotyMapper;

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToServChangeHistroty record) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.insert(record);
	}

	@Override
	public int insertSelective(ToServChangeHistroty record) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.insertSelective(record);
	}

	@Override
	public ToServChangeHistroty selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToServChangeHistroty record) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(ToServChangeHistroty record) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(ToServChangeHistroty record) {
		// TODO Auto-generated method stub
		return toServChangeHistrotyMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ToServChangeHistroty> queryServChangeHistroy(String caseCode) {
		ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
		toServChangeHistroty.setCaseCode(caseCode);
		return toServChangeHistrotyMapper.findToServChangeHistroty(toServChangeHistroty);
	}

}
