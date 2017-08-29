package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface ToCaseParticipantMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCaseParticipant record);

    int insertSelective(ToCaseParticipant record);

    ToCaseParticipant selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCaseParticipant record);

    int updateByPrimaryKey(ToCaseParticipant record);
    /**
     * 根据CCAI编号查询案件参与人
     * @param ccaiCode
     * @return
     */
    List<ToCaseParticipant> selectByCcaiCode(String ccaiCode);
    /**
     * 根据案件编号查询案件参与人
     * @param caseCode
     * @return
     */
    List<ToCaseParticipant> selectByCaseCode(String caseCode);
}