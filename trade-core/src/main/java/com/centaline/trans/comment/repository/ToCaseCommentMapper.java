package com.centaline.trans.comment.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToCaseCommentMapper
{
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCaseComment record);

    int insertSelective(ToCaseComment record);

    ToCaseComment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCaseComment record);

    int updateByPrimaryKey(ToCaseComment record);

    List<ToCaseComment> getToCaseCommentList(ToCaseComment record);
    

    /**
     * 
     * @since:2017年9月4日 下午7:38:20
     * @description:修复原来方法没有过滤条件的问题
     * @author:xiefei1
     * @param caseCode
     * @return List<ToCaseComment>
     */
    List<ToCaseComment> getToCaseCommentListByCaseCode(String caseCode);

    ToCaseComment getCommentParentByBizCode(String bizCode);

    ToCaseComment getCommentById(Long pkid);

    /**
     * 根据案件编号和类型删除案件备注信息
     * 
     * @param caseCode
     *            案件编号
     * @param type
     *            类型
     * @return 返回1,删除成功,返回0,删除失败。
     */
    public int deleteByCasecodeAndType(Map<String, Object> map);
}