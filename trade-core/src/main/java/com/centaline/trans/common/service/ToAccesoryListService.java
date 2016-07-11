package com.centaline.trans.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.vo.AccsoryListVO;

public interface ToAccesoryListService {
	public List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList toAccesoryList);
	public String findAccesoryNameByCode(String accessoryCode);
	public ToAccesoryList findAccesoryByCode(String accessoryCode);
	public boolean saveAccesoryList(AccsoryListVO accsoryListVO);
	void getAccesoryListCaseClose(HttpServletRequest request, String caseCode);
	void getAccesoryList(HttpServletRequest request, String taskitem);
	void getAccesoryListLingZheng(HttpServletRequest request, String taskitem, boolean psf, boolean self, boolean com);
	void getAccesoryListGuoHu(HttpServletRequest request, String taskitem, String caseCode);
}
