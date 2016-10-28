package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvAccount;
import com.centaline.trans.spv.vo.ToSpvAccountVO;

@MyBatisRepository
public interface ToSpvAccountMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvAccount record);

    int insertSelective(ToSpvAccount record);

    ToSpvAccount selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvAccount record);

    int updateByPrimaryKey(ToSpvAccount record);
    
    List<ToSpvAccount> selectBySpvCode(String spvCode);
    List<ToSpvAccount> selectBySpvCodeinCaseFlowApple(String spvCode);
}