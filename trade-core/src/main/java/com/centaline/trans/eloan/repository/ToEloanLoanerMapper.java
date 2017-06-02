package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToEloanLoaner;

@MyBatisRepository
public interface ToEloanLoanerMapper
{
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEloanLoaner record);

    int insertSelective(ToEloanLoaner record);

    ToEloanLoaner selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEloanLoaner record);

    int updateByPrimaryKey(ToEloanLoaner record);

    /**
     * 根据E+金融编号更新E+接收记录信息
     * 
     * @param record
     *            E+接收信息
     * @return 返回0,更新失败;返回1,更新成功。
     */
    int updateEloanLoanerByELoanCode(ToEloanLoaner record);

    /**
     * 根据E+金融编号获取E+案件接收信息
     * 
     * @param eLoanCode
     *            金融编号
     * @return E+案件接收信息
     */
    public ToEloanLoaner getToEloanLoanerByELoanCode(String eLoanCode);

}