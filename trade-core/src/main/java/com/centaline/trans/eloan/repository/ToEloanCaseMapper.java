package com.centaline.trans.eloan.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToEloanCase;

@MyBatisRepository
public interface ToEloanCaseMapper
{
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEloanCase record);

    int insertSelective(ToEloanCase record);

    ToEloanCase selectByPrimaryKey(Long pkid);

    ToEloanCase selectByEloanCode(String eloanCode);

    int selectBackKaCountByTime(Integer endWeekDay);

    int selectBackAppCountByTime(Integer endWeekDay);

    int updateByPrimaryKeySelective(ToEloanCase record);

    int updateByPrimaryKey(ToEloanCase record);

    List<ToEloanCase> getToEloanCaseListByProperty(ToEloanCase record);

    int updateEloanCaseByEloanCode(ToEloanCase record);

    int updateApplyEloanCaseByEloanCode(ToEloanCase record);

    List<String> validateEloanApply(ToEloanCase record);

    void eloanInfoForUpdate(ToEloanCase toEloanCase);

    int updateByCaseCodeAndExcutor(@Param("userId") String userId, @Param("tEloanCase") ToEloanCase tEloanCase);

}