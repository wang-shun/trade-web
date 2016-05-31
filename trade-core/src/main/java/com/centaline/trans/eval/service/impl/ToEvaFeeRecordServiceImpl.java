/**
 * 
 */
package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.repository.ToEvaFeeRecordMapper;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;

/**
 * <p>Project: Credo's Base</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 LionCredo.All Rights Reserved.</p>
 * @author <a href="zhaoqianjava@foxmail.com">LionCredo</a>
 */

@Service
public class ToEvaFeeRecordServiceImpl implements ToEvaFeeRecordService {

	@Autowired
	private ToEvaFeeRecordMapper toEvaFeeRecordMapper; 
	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaFeeRecordService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaFeeRecordMapper.deleteByPrimaryKey(pkid);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaFeeRecordService#insert(com.centaline.trans.eval.entity.ToEvaFeeRecord)
	 */
	@Override
	public int insert(ToEvaFeeRecord record) {
		// TODO Auto-generated method stub
		return toEvaFeeRecordMapper.insert(record);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaFeeRecordService#insertSelective(com.centaline.trans.eval.entity.ToEvaFeeRecord)
	 */
	@Override
	public int insertSelective(ToEvaFeeRecord record) {
		// TODO Auto-generated method stub
		return toEvaFeeRecordMapper.insertSelective(record);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaFeeRecordService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public ToEvaFeeRecord selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaFeeRecordMapper.selectByPrimaryKey(pkid);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaFeeRecordService#updateByPrimaryKeySelective(com.centaline.trans.eval.entity.ToEvaFeeRecord)
	 */
	@Override
	public int updateByPrimaryKeySelective(ToEvaFeeRecord record) {
		// TODO Auto-generated method stub
		return toEvaFeeRecordMapper.updateByPrimaryKeySelective(record);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaFeeRecordService#updateByPrimaryKey(com.centaline.trans.eval.entity.ToEvaFeeRecord)
	 */
	@Override
	public int updateByPrimaryKey(ToEvaFeeRecord record) {
		// TODO Auto-generated method stub
		return toEvaFeeRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public ToEvaFeeRecord findToEvaFeeRecordByCaseCode(String caseCode) {
		return toEvaFeeRecordMapper.findToEvaFeeRecordByCaseCode(caseCode);
	}

}
