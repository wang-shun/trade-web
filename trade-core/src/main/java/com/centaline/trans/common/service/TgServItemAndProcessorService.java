package com.centaline.trans.common.service;

import java.util.List;
import java.util.Map;

import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.common.entity.TgServItemAndProcessor;

public interface TgServItemAndProcessorService {
	List<TgServItemAndProcessor> findTgServItemAndProcessorByUserIdAndCaseCode(String processorId,String caseCode);
    List<TgServItemAndProcessor> findTgServItemAndProcessorByCaseCode(String caseCode);
    List<String> findProcessorsByCaseCode(TgServItemAndProcessor record);
    List<String> findCaseCodesByUserId(	String processorId) ;
    List<String> findSrvsByCaseCode(String caseCode);
    List<String> findSrvCatsByCaseCode(String caseCode);

    int insertSelective(TgServItemAndProcessor record);
    int updateByCaseCode(TgServItemAndProcessor record);
    int deleteByPrimaryCaseCode(String caseCode);
    Map<String, String> findServiceMap(String caseCode);
    
    int deleteByUser(TgServItemAndProcessor record);
    
    
    // 变更合作对象
    List<TgServItemAndProcessor> selectBycasecodeandProcessorid(String caseCode);
    
    // 
    List<Dict> selectOwnService(String caseCode);
    
    // 变更合作对象[修改功能]
    int updateCoope(TgServItemAndProcessor pro);

    TgServItemAndProcessor findTgServItemAndProcessor(TgServItemAndProcessor record);

    
}
