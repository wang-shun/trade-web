package com.centaline.trans.attachment.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.common.vo.AccsoryListVO;

public interface ToAccesoryListService {
	public List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList toAccesoryList);
	public List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList toAccesoryList,String caseCode);
	public String findAccesoryNameByCode(String accessoryCode);
	public ToAccesoryList findAccesory(ToAttachment toAttachment);
	public ToAccesoryList findAccesoryNameByPartCode(ToAccesoryList toAccesoryList);
	public boolean saveAccesoryList1(AccsoryListVO accsoryListVO);
	public boolean saveAccesoryList(AccsoryListVO accsoryListVO);
	void getAccesoryListCaseClose(HttpServletRequest request, String caseCode);
	void getAccesoryList(HttpServletRequest request, String taskitem);
	void getAccesoryListLingZheng(HttpServletRequest request, String taskitem, boolean psf, boolean self, boolean com);
	void getAccesoryListGuoHu(HttpServletRequest request, String taskitem, String caseCode);
	void getAccesoryLists(HttpServletRequest request, String taskitem);

	boolean deleteAccesoryLists(Long pkid);
}
