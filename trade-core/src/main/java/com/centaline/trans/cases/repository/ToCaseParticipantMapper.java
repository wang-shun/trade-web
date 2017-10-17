package com.centaline.trans.cases.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aist.uam.userorg.remote.vo.User;
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
    /**
     * 
     * @since:2017年9月8日 下午7:27:47
     * @description:增加条件查询ToCaseParticipant
     * @author:xiefei1
     * @param record
     * @return
     */
    List<ToCaseParticipant> selectByCondition(ToCaseParticipant record);
    
    /**
     * 案件分配信息更新(责任人变更)
     * @param caseCode
     * @param user
     * @param manager
     * @return
     */
    int updateCaseParticipant(@Param("caseCode")String caseCode, @Param("user")User user, @Param("manager")User manager);
}