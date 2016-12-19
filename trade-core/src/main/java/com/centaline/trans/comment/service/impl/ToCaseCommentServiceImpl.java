package com.centaline.trans.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;

@Service
@Transactional(readOnly = true)
public class ToCaseCommentServiceImpl implements ToCaseCommentService {

    @Autowired
    private ToCaseCommentMapper toCaseCommentMapper;

    private ToMortgageMapper    toMortgageMapper;

    @Override
    public List<ToCaseComment> getToCaseCommentList(ToCaseComment record) {
        return toCaseCommentMapper.getToCaseCommentList(record);
    }

    @Override
    public int insertToCaseComment(ToCaseComment record) {
        return toCaseCommentMapper.insertSelective(record);
    }

    @Override
    public int addComment4Par(ToCaseComment record) {
        return toCaseCommentMapper.insertSelective(record);
    }

}
