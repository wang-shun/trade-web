package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToCaseComment;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface ToCaseCommentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCaseComment record);

    int insertSelective(ToCaseComment record);

    ToCaseComment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCaseComment record);

    int updateByPrimaryKey(ToCaseComment record);
    
    List<ToCaseComment> getToCaseCommentList(ToCaseComment record);
}