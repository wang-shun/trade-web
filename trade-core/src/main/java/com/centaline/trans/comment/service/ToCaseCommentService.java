package com.centaline.trans.comment.service;

import java.util.List;

import com.centaline.trans.comment.entity.ToCaseComment;

public interface ToCaseCommentService {
    List<ToCaseComment> getToCaseCommentList(ToCaseComment record);

    /**
     * 合作机构增加备注信息
     * 
     * @param record
     * @return
     */
    int addComment4Par(ToCaseComment record);

    /**
     * 合作机构增加补件信息
     * 
     * @param record
     * @return
     */
    //    boolean addBuJian4Par(ToCaseComment record);

    /**
     * 合作机构退回案件
     * 
     * @param record
     * @return
     */
    //    boolean rejectCase4Par(ToCaseComment record);

    ToCaseComment getCommentParentByBizCode(String bizCode);

    /**
     * 
     * @param record
     * @return
     */
    int insertToCaseComment(ToCaseComment record);
}
