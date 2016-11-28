package com.centaline.trans.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.common.vo.AccsoryListVO;

@Controller
@RequestMapping(value="/setting")
public class PrefileListSetController {

	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	@RequestMapping(value="prefileListSet")
	public String prefileListSetManage(HttpServletRequest request) {
		return "manage/prefileListSet";
	}
	
	@RequestMapping(value="queryToAccsoryList")
	@ResponseBody
	public List<ToAccesoryList> queryToAccsoryList(HttpServletRequest request, String partCode) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(partCode);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		return list;
	}
	
	@RequestMapping(value="saveToAccsoryList")
	@ResponseBody
	public Boolean saveToAccsoryList(HttpServletRequest request, AccsoryListVO accsoryListVO) {
		if(toAccesoryListService.saveAccesoryList(accsoryListVO)) {
			return true;
		} else {
			return false;
		}
	}
	
}
