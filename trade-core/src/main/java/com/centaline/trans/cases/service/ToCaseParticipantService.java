package com.centaline.trans.cases.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCaseParticipant;

/**
 * @Description:案件参与人相关服务
 * @author：jinwl6
 * @date:2017年9月15日
 * @version:
 */
public interface ToCaseParticipantService {
	
	/**
	 * 根据 条件查询案件参与人信息
	 * @param caseCode
	 * @return
	 * @author jinwl6
	 */
	List<ToCaseParticipant> findToCaseParticipantByCondition(ToCaseParticipant toCaseParticipant);
}
