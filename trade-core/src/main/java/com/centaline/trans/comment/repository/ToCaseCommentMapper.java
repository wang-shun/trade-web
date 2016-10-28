package com.centaline.trans.comment.repository;

import java.util.List;
import com.centaline.trans.comment.entity.ToCaseComment;
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