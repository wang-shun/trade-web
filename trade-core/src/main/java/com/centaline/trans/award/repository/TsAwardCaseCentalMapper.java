package com.centaline.trans.award.repository;

import java.util.Map;

import com.centaline.trans.award.entity.TsAwardCaseCental;
import com.centaline.trans.common.MyBatisRepository;


@MyBatisRepository
public interface TsAwardCaseCentalMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAwardCaseCental record);
    
    /*
     * 保存计件奖金池数据
     * @param TsAwardCaseCental
     * */
    int insertSelective(TsAwardCaseCental record);

    TsAwardCaseCental selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAwardCaseCental record);

    int updateByPrimaryKey(TsAwardCaseCental record);
    
    /*
     * 执行存储过程 计算环节占比
     * @param Map
     * */
	void callCreateAwardBaseInfo(Map<String, Object> map);
}