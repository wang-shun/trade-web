package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToAccesoryList;

@MyBatisRepository
public interface ToAccesoryListMapper {
    int deleteByPrimaryKey(Long pkid);

	ToAccesoryList findAccesoryByCode(String accessoryCode);

    int insert(ToAccesoryList record);

    int insertSelective(ToAccesoryList record);

    ToAccesoryList selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToAccesoryList record);

    int updateByPrimaryKey(ToAccesoryList record);
    
    List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList record);
    
	public String findAccessoryNameByCode(String accessoryCode);
}