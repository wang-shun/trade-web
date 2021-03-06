package com.centaline.trans.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.comment.service.ToCaseCommentService;

@Service
@Transactional
public class ToCaseCommentServiceImpl implements ToCaseCommentService {

    @Autowired
    private ToCaseCommentMapper toCaseCommentMapper;

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
    //
    //
    //	@Override
    //	public int insertToCaseComment(ToCaseComment record) {
    //		if (StringUtils.isNotBlank(record.getParentId())) {
    //			ToCaseComment pComment = toCaseCommentMapper.getCommentById(Long.valueOf(record.getParentId()));
    //			if (pComment != null) {
    //				pComment.setPkid(null);
    //				BeanUtils.copyProperties(record, pComment);
    //				return toCaseCommentMapper.insertSelective(pComment);
    //			}
    //		}
    //		return toCaseCommentMapper.insertSelective(record);
    //	}

    @Override
    public ToCaseComment getCommentParentByBizCode(String bizCode) {
        return toCaseCommentMapper.getCommentParentByBizCode(bizCode);
    }

    @Override
    public ToCaseComment findCommentById(Long pkid) {
        return toCaseCommentMapper.getCommentById(pkid);
    }
}
