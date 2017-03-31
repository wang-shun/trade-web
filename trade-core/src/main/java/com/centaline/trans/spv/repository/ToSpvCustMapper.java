package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCust;
import com.centaline.trans.spv.vo.ToSpvAccountVO;

@MyBatisRepository
public interface ToSpvCustMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCust record);

    int insertSelective(ToSpvCust record);

    ToSpvCust selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvCust record);

    int updateByPrimaryKeyWithBLOBs(ToSpvCust record);

    int updateByPrimaryKey(ToSpvCust record);
    
    List<ToSpvCust> selectBySpvCode(String spvCode);
    List<ToSpvAccountVO> inCaseFlowApple(String spvCode);
}