package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.common.MyBatisRepository;

/**
 * 案件时效管理mapper
 * 
 * @author yinjf2
 *
 */
@MyBatisRepository
public interface TsCaseEfficientMapper
{

    int insert(TsCaseEfficient record);

    int insertSelective(TsCaseEfficient record);

    /**
     * 根据案件编号查询案件时效记录数
     * 
     * @param caseCode
     *            案件编号
     * @return 案件时效记录数
     */
    public int getCaseEffCountByCaseCode(String caseCode);

    /**
     * 根据案件编号更新案件时效信息
     * 
     * @param tsCaseEfficient
     *            案件时效信息
     * @return 返回1,更新成功;返回0,更新失败。
     */
    public int updateTsCaseEffInfo(TsCaseEfficient tsCaseEfficient);

    /**
     * 根据案件编号获取案件时效信息
     * 
     * @param caseCode
     *            案件编号
     * @return 案件时效信息
     */
    public TsCaseEfficient getCaseEffInfoByCasecode(String caseCode);
}