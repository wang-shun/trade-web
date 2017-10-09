package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.ToCaseParticipantService;

/**
 * @Description:案件参与人相关服务实现
 * @author：jinwl6
 * @date:2017年9月15日
 * @version:
 */
@Service
public class ToCaseParticipantServiceImpl implements ToCaseParticipantService {
    
	@Autowired
	ToCaseParticipantMapper toCaseParticipantMapper;
	
	@Override
	public List<ToCaseParticipant> findToCaseParticipantByCondition(ToCaseParticipant toCaseParticipant) {
		return toCaseParticipantMapper.selectByCondition(toCaseParticipant);
	}

}
