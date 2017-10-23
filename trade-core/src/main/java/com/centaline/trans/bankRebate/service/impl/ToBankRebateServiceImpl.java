package com.centaline.trans.bankRebate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.repository.ToBankRebateMapper;
import com.centaline.trans.bankRebate.service.ToBankRebateService;

@Service
public class ToBankRebateServiceImpl implements ToBankRebateService {
	
	@Autowired(required = true)
	ToBankRebateMapper toBankRebateMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToBankRebate record) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.insert(record);
	}

	@Override
	public int insertSelective(ToBankRebate record) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.insertSelective(record);
	}

	@Override
	public ToBankRebate selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToBankRebate record) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToBankRebate record) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByGuaranteeCompId(String guaCompId) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.deleteByGuaranteeCompId(guaCompId);
	}

	@Override
	public int updateByGuaranteeCompId(ToBankRebate toBankRebate) {
		// TODO Auto-generated method stub
		return toBankRebateMapper.updateByGuaranteeCompId(toBankRebate);
	}

}
