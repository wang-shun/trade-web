package com.centaline.trans.bankRebate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.repository.ToBankRebateInfoMapper;
import com.centaline.trans.bankRebate.service.ToBankRebateInfoService;

@Service
public class ToBankRebateInfoServiceImpl implements ToBankRebateInfoService {
	
	@Autowired(required = true)
	private ToBankRebateInfoMapper toBankRebateInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.insertSelective(record);
	}

	@Override
	public ToBankRebateInfo selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteRebateInfoByGuaranteeCompId(String guaCompId) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.deleteRebateInfoByGuaranteeCompId(guaCompId);
	}

	@Override
	public List<ToBankRebateInfo> selectRebateInfoByGuaranteeCompId(String guaranteeCompId) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.selectRebateInfoByGuaranteeCompId(guaranteeCompId);
	}
	
	
}
