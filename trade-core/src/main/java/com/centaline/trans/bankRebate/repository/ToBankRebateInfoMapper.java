package com.centaline.trans.bankRebate.repository;

import java.util.List;

import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.common.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToBankRebateInfoMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToBankRebateInfo record);

    int insertSelective(ToBankRebateInfo record);

    ToBankRebateInfo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToBankRebateInfo record);

    int updateByPrimaryKey(ToBankRebateInfo record);
    
    int deleteRebateInfoByGuaranteeCompId(String guaCompId);

	List<ToBankRebateInfo> selectRebateInfoByGuaranteeCompId(String guaranteeCompId);

    /**
     * 根据案件编号 申请批次号
     * 获取 银行返利案件信息
     * @param caseCode
     * @param compId
     * @return
     */
    ToBankRebateInfo selectToRebateInfoByCaseCodeAndCompId(@Param("caseCode") String caseCode,@Param("compId") String compId);
}