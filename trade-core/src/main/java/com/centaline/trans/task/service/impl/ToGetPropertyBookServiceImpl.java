package com.centaline.trans.task.service.impl;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToGetPropertyBook;
import com.centaline.trans.task.repository.ToGetPropertyBookMapper;
import com.centaline.trans.task.service.ToGetPropertyBookService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToGetPropertyBookServiceImpl implements ToGetPropertyBookService {

	@Autowired
	private ToGetPropertyBookMapper toGetPropertyBookMapper;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Override
	public Boolean saveToGetPropertyBook(ToGetPropertyBook toGetPropertyBook) {
		if(StringUtils.isBlank(toGetPropertyBook.getCaseCode())) {
			return false;
		}
		if(toGetPropertyBook.getPkid() != null) {
			if(toGetPropertyBookMapper.updateByPrimaryKeySelective(toGetPropertyBook) > 0) {
				return true;
			};
		} else {
			if(toGetPropertyBookMapper.findGetPropertyBookByCaseCode(toGetPropertyBook.getCaseCode()) == null) {
				if(toGetPropertyBookMapper.insertSelective(toGetPropertyBook) > 0) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public ToGetPropertyBook queryToGetPropertyBook(String caseCode) {
		return toGetPropertyBookMapper.findGetPropertyBookByCaseCode(caseCode);
	}

	
	@Override
	public ToGetPropertyBook findGetPropertyBookByCaseCode(String caseCode) {
		ToGetPropertyBook Book=toGetPropertyBookMapper.findGetPropertyBookByCaseCode(caseCode);
		return Book;
	}

	@Override
	public AjaxResponse saveAndSubmitPropertyBook(ToGetPropertyBook toGetPropertyBook, String taskId, String processInstanceId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		Boolean saveFlag =  saveToGetPropertyBook(toGetPropertyBook);
		if(saveFlag){
			List<RestVariable> variables = new ArrayList<RestVariable>();
			ToCase toCase = toCaseService.findToCaseByCaseCode(toGetPropertyBook.getCaseCode());
			workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(),toGetPropertyBook.getCaseCode());
			toCase.setStatus("30001005");	/* 修改案件状态 */
			toCaseService.updateByCaseCodeSelective(toCase);
			ajaxResponse.setSuccess(true);
		} else {
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("保存领证出错");
		}
		return ajaxResponse;
	}

}
