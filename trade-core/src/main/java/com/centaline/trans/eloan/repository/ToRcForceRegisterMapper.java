package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcForceRegister;
import com.centaline.trans.eloan.entity.ToRcMortgage;
@MyBatisRepository
public interface ToRcForceRegisterMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcForceRegister record);

    int insertSelective(ToRcForceRegister record);

    ToRcForceRegister selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcForceRegister record);

    int updateByPrimaryKey(ToRcForceRegister record);
    
    int updateRcForceRegisterByRcId(ToRcForceRegister record);
    
    List<ToRcForceRegister> getRcForceRegisterByProperty(RcRiskControl record);
}