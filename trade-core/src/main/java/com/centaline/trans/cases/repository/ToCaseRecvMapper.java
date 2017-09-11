package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.common.MyBatisRepository;

/**
 * @author xiefei1
 * @since 2017年9月4日 下午1:50:41 
 * @description 接单跟进
 */
@MyBatisRepository
public interface ToCaseRecvMapper {
    int deleteByPrimaryKey(String caseCode);

    int insertSelective(ToCaseRecv record);

    ToCaseRecv selectByPrimaryKey(String caseCode);

    int updateByPrimaryKeySelective(ToCaseRecv record);

    int updateByPrimaryKey(ToCaseRecv record);
}