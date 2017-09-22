/**
 * 
 */
package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.repository.ToEvalRebateMapper;
import com.centaline.trans.eval.service.ToEvalRebateService;

/**
 * <p>Project: Credo's Base</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 LionCredo.All Rights Reserved.</p>
 * @author <a href="zhaoqianjava@foxmail.com">LionCredo</a>
 */

@Service
public class ToEvalRebateServiceImpl implements ToEvalRebateService {

	@Autowired
	private ToEvalRebateMapper ToEvalRebateMapper; 
	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalRebateService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return ToEvalRebateMapper.deleteByPrimaryKey(pkid);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalRebateService#insert(com.centaline.trans.eval.entity.ToEvalRebate)
	 */
	@Override
	public int insert(ToEvalRebate record) {
		// TODO Auto-generated method stub
		return ToEvalRebateMapper.insert(record);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalRebateService#insertSelective(com.centaline.trans.eval.entity.ToEvalRebate)
	 */
	@Override
	public int insertSelective(ToEvalRebate record) {
		// TODO Auto-generated method stub
		return ToEvalRebateMapper.insertSelective(record);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalRebateService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public ToEvalRebate selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return ToEvalRebateMapper.selectByPrimaryKey(pkid);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalRebateService#updateByPrimaryKeySelective(com.centaline.trans.eval.entity.ToEvalRebate)
	 */
	@Override
	public int updateByPrimaryKeySelective(ToEvalRebate record) {
		// TODO Auto-generated method stub
		return ToEvalRebateMapper.updateByPrimaryKeySelective(record);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalRebateService#updateByPrimaryKey(com.centaline.trans.eval.entity.ToEvalRebate)
	 */
	@Override
	public int updateByPrimaryKey(ToEvalRebate record) {
		// TODO Auto-generated method stub
		return ToEvalRebateMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToEvalRebate findToEvalRebateByCaseCode(String caseCode) {
		return ToEvalRebateMapper.findToEvalRebateByCaseCode(caseCode);
	}


	

}
