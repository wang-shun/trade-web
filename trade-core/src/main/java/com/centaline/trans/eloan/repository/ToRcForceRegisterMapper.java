package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToRcForceRegister;
@MyBatisRepository
public interface ToRcForceRegisterMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcForceRegister record);

    int insertSelective(ToRcForceRegister record);

    ToRcForceRegister selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcForceRegister record);

    int updateByPrimaryKey(ToRcForceRegister record);
}