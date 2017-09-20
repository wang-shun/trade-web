package com.centaline.trans.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
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
		return "manage/prefileListSetTest";
	}
	
	@RequestMapping(value="queryToAccsoryList")
	@ResponseBody
	public List<ToAccesoryList> queryToAccsoryList(HttpServletRequest request, String partCode) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(partCode);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		return list;
	}

	/**
	 * 原备件修改(可废弃)
	 * @author wbzhouht
	 * @param request
	 * @param accsoryListVO
	 * @return
	 */
	@RequestMapping(value="saveToAccsoryList1")
	@ResponseBody
	public Boolean saveToAccsoryList1(HttpServletRequest request, AccsoryListVO accsoryListVO) {
		if(toAccesoryListService.saveAccesoryList(accsoryListVO)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 新增备件修改返回ajax
	 * @param request
	 * @author wbzhouht
	 * @param accsoryListVO
	 * @return
	 */
	@RequestMapping(value="saveToAccsoryList")
	@ResponseBody
	public AjaxResponse<?> saveToAccsoryList(HttpServletRequest request,AccsoryListVO accsoryListVO){
		if (toAccesoryListService.saveAccesoryList(accsoryListVO)){
			return AjaxResponse.success("操作成功！");
		}else {
			return AjaxResponse.fail("操作失败！");
		}
	}

	/**
	 * @author wbzhouht
	 * 删除备件信息
	 * @param accesoryList
	 * @param request
	 * @return
	 */

	@RequestMapping(value="deleteToAccsoryList")
	@ResponseBody
	public  AjaxResponse<?>deleteToAccsoryList(ToAccesoryList accesoryList,HttpServletRequest request){
		boolean boo=false;
		if (accesoryList.getPkid()!=null){
			boo=toAccesoryListService.deleteAccesoryLists(accesoryList.getPkid());
		}
		if (boo){
			return AjaxResponse.success("删除成功！");
		}else {
			return AjaxResponse.fail("删除失败！");
		}
	}
	
}
