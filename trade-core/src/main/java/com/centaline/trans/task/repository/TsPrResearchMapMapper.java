package com.centaline.trans.task.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TsPrResearchMap;

@MyBatisRepository
public interface TsPrResearchMapMapper {
	List<TsPrResearchMap> selectByDistCode(String distCode);
	
    int deleteByPrimaryKey(Long pkid);

    int insertSelective(TsPrResearchMap record);

    TsPrResearchMap selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsPrResearchMap record);
    
	List<TsPrResearchMap> findByDistCodeAndYuDistCode(@Param("pkid") Long pkid,@Param("distCode") String distCode,@Param("yuDistCode") String yuDistCode);
	
	List<TsPrResearchMap> getTsPrResearchMapByProperty(TsPrResearchMap record);

}
