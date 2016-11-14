package com.centaline.trans.transplan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
@MyBatisRepository
public interface TtsReturnVisitRegistrationMapper {
    int insert(TtsReturnVisitRegistration record);

    int insertSelective(TtsReturnVisitRegistration record);
}