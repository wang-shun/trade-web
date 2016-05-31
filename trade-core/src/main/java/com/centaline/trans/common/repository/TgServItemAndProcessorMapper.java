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
    
    TgServItemAndProcessor findTgServItemAndProcessor(TgServItemAndProcessor record);
    
    
    // 变更合作对象
    List<TgServItemAndProcessor> selectBycasecodeandProcessorid(TgServItemAndProcessor itemprocess);
    
    
    int updateCoope(TgServItemAndProcessor pro);
    
    /**
	 * 功能：收益
	 * 作者：zhangxb16
	 */
    TgServItemAndProcessor selectServItem(TgServItemAndProcessor tp);
    
}