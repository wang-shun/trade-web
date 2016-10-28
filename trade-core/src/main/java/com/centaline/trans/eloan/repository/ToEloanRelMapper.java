package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToEloanRel;

@MyBatisRepository
public interface ToEloanRelMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEloanRel record);

    int insertSelective(ToEloanRel record);

    ToEloanRel selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEloanRel record);

    int updateByPrimaryKey(ToEloanRel record);
    
    List<ToEloanRel> getEloanRelByEloanCode(String eloanCode);
    
    int updateEloanRelByEloanCode(ToEloanRel record);
    int deleteByEloanCode(String eloanCode);
    
    Double getSumReleaseAmountByEloanCode(String eloanCode);

	void updateEloanRelByEloanCodeForModify(List<ToEloanRel> eloanRelList);

	List<ToEloanRel> getEloanRelByEloanCodeAndConfirmStatus(String eloanCode);
}