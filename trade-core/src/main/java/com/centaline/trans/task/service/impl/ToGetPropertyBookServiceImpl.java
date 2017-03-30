package com.centaline.trans.task.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToGetPropertyBook;
import com.centaline.trans.task.repository.ToGetPropertyBookMapper;
import com.centaline.trans.task.service.ToGetPropertyBookService;

@Service
public class ToGetPropertyBookServiceImpl implements ToGetPropertyBookService {

	@Autowired
	private ToGetPropertyBookMapper toGetPropertyBookMapper;
	
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

}
