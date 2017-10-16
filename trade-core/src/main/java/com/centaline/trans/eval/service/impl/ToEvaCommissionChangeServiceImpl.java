package com.centaline.trans.eval.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.repository.ToEvaCommissionChangeMapper;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;

/**
 * @author xiefei1
 * @since 2017年9月22日 下午2:11:07 
 * @description 评估公司变更模块
 */
@Service
public class ToEvaCommissionChangeServiceImpl implements ToEvaCommissionChangeService {
	@Autowired
	private ToEvaCommissionChangeMapper toEvaCommissionChangeMapper;
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper; 
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		toEvaCommissionChangeMapper.deleteByPrimaryKey(pkid);
		return 0;
	}

	@Override
	public int insert(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int insert = toEvaCommissionChangeMapper.insert(record);
		return insert;
	}

	@Override
	public int insertSelective(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int insertSelective = toEvaCommissionChangeMapper.insertSelective(record);
		return insertSelective;
	}

	@Override
	public ToEvaCommissionChange selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		ToEvaCommissionChange selectByPrimaryKey = toEvaCommissionChangeMapper.selectByPrimaryKey(pkid);
		
		return selectByPrimaryKey;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int updateByPrimaryKeySelective = toEvaCommissionChangeMapper.updateByPrimaryKeySelective(record);
		return updateByPrimaryKeySelective;
	}

	@Override
	public int updateByPrimaryKey(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int updateByPrimaryKey = toEvaCommissionChangeMapper.updateByPrimaryKey(record);
		return updateByPrimaryKey;
	}

	@Override
	public int updateEvalChangeApproveRecord(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.insertSelective(toApproveRecord);
	}

//	@Override
//	public int deleteByCaseCode(String caseCode) {
//		// TODO Auto-generated method stub
//		int deleteByCaseCode = toEvaCommissionChangeMapper.deleteByCaseCode(caseCode);
//		return deleteByCaseCode;
//	}



	
	

	
}
