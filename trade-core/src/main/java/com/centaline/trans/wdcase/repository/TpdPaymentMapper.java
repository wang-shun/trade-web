package com.centaline.trans.wdcase.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.wdcase.entity.TpdPayment;
@MyBatisRepository
public interface TpdPaymentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TpdPayment record);

    int insertSelective(TpdPayment record);

    TpdPayment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TpdPayment record);

    int updateByPrimaryKey(TpdPayment record);
    /**
     * 查询应收流水
     * @author hejf10
     * @date 2017年4月26日18:32:02
     * @param caseCode
     * @return
     */
    List<TpdPayment> selectByCaseCode(String caseCode);
}