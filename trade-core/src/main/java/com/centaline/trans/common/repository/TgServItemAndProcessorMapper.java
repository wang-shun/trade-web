package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TgServItemAndProcessor;

@MyBatisRepository
public interface TgServItemAndProcessorMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TgServItemAndProcessor record);

    int insertSelective(TgServItemAndProcessor record);

    TgServItemAndProcessor selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TgServItemAndProcessor record);

    int updateByPrimaryKey(TgServItemAndProcessor record);

    int updateByCaseCode(TgServItemAndProcessor record);
    List<TgServItemAndProcessor> findTgServItemAndProcessorByUserId(String processorId);
    List<TgServItemAndProcessor> findTgServItemAndProcessorByUserIdAndCaseCode(String processorId,String caseCode);
    List<TgServItemAndProcessor> findTgServItemAndProcessorByCaseCode(String caseCode);
    List<String> findCaseCodesByUserId(	String processorId) ;
    List<TgServItemAndProcessor> findCaseCodesByCaseCode(String caseCode);
    List<String> findSrvsByCaseCode(String caseCode);
    List<String> findSrvCatsByCaseCode(String caseCode);
    
    int deleteByPrimaryCaseCode(String caseCode);
    public List<String> findProcessorsByCaseCode(TgServItemAndProcessor record);
    
    int deleteByUser(TgServItemAndProcessor record);
    
    int deleteByCaseCode(String caseCode);
    
    int deleteMortageServItem(String caseCode);
    
    TgServItemAndProcessor findTgServItemAndProcessor(TgServItemAndProcessor record);
    
    
    // 变更合作对象
    List<TgServItemAndProcessor> selectBycasecodeandProcessorid(TgServItemAndProcessor itemprocess);
    
    int updateCoope(TgServItemAndProcessor pro);
    
    /**
	 * 功能：收益
	 * 作者：zhangxb16
	 */
    TgServItemAndProcessor selectServItem(TgServItemAndProcessor tp);
    /**
     * 功能：根据案件编号，查询案件交易顾问
     * 作者：hejf
     * 时间：2017-3-16 17:50:26
     */
    TgServItemAndProcessor selectServItemandName(TgServItemAndProcessor tp);
    /**
     * 功能：根据案件编号，查询案件交易顾问
     * 作者：hejf
     * 时间：2017-3-16 17:50:26
     */
    TgServItemAndProcessor selectServItemandNameF(TgServItemAndProcessor tp);
   
    /**
     * 查询审核结果
     * @author hejf10
     * @param caseCode
     * @return
     */
    String findGuohuApproveTypeByCaseCode(String caseCode);
    
    /**
     * 查询审核结果
     * @author hejf10
     * @param caseCode
     * @return
     */
    int selectAtt(String caseCode);
    
    /**
     * 查询审核结果
     * @author hejf10
     * @param caseCode
     * @return
     */
    int selectTask(String caseCode);
    
}