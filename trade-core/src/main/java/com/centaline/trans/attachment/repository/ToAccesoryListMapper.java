package com.centaline.trans.attachment.repository;

import java.util.List;

import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToAccesoryListMapper {
    int deleteByPrimaryKey(Long pkid);

	ToAccesoryList findAccesory(ToAttachment toAttachment);

    int insert(ToAccesoryList record);

    int insertSelective(ToAccesoryList record);

    ToAccesoryList selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToAccesoryList record);

    int updateByPrimaryKey(ToAccesoryList record);
    
    List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList record);
    
	public String findAccessoryNameByCode(String accessoryCode);
	
	public ToAccesoryList findAccesoryNameByPartCode(ToAccesoryList toAccesoryList);
}