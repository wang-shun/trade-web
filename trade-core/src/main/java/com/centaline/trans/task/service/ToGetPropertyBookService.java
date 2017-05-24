package com.centaline.trans.task.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.task.entity.ToGetPropertyBook;

public interface ToGetPropertyBookService {

	public Boolean saveToGetPropertyBook(ToGetPropertyBook toGetPropertyBook);
	
	public ToGetPropertyBook queryToGetPropertyBook(String caseCode);
	
	ToGetPropertyBook findGetPropertyBookByCaseCode(String caseCode);

}
