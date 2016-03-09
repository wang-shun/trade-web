package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.vo.AccsoryListVO;

public interface ToAccesoryListService {
	public List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList toAccesoryList);
	public String findAccesoryNameByCode(String accessoryCode);
	public ToAccesoryList findAccesoryByCode(String accessoryCode);
	public boolean saveAccesoryList(AccsoryListVO accsoryListVO);
}
