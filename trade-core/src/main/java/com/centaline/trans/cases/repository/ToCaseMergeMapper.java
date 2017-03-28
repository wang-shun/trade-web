package com.centaline.trans.cases.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.cases.entity.ToCaseMerge;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface ToCaseMergeMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCaseMerge record);

    int insertSelective(ToCaseMerge record);

    ToCaseMerge selectByPrimaryKey(Long pkid);
    
    List<ToCaseMerge> selectByPrimaryPropertyCode(String propertyCode);
    
    int updateByPrimaryKeySelective(ToCaseMerge record);

    int updateByPrimaryKey(ToCaseMerge record);
    /**
     * @author hejf10 2016-12-26 11:24:23
     * @param proCode 地址code
     * @return 返回相同案件list
     */
    Map getMergeInfoList(String proCode );
    /**
     * 查询合流案件信息
     * @author hejf10
     * @param caseCode
     * @return
     */
    List<ToCaseMerge> selectByPrimaryCaseCode(String caseCode);
}