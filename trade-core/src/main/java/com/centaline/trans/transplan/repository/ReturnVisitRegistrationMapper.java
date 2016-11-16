package com.centaline.trans.transplan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.vo.CaseReturnVisitRegistrationVO;

@MyBatisRepository
public interface ReturnVisitRegistrationMapper {
	/**
	 * 跟进交易变更历史批次表id查询回访跟进信息
	 * @param historyId
	 * @return
	 */
	List<TtsReturnVisitRegistration> queryReturnVisitRegistrations(Long batchId);
	/**
	 * 新增回访跟进信息
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	int insertReturnVisitRegistration(TtsReturnVisitRegistration ttsReturnVisitRegistration);
}
