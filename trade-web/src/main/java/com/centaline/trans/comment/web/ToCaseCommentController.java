package com.centaline.trans.comment.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;

@Controller
@RequestMapping(value = "/caseComment")
public class ToCaseCommentController {
	
	@Autowired
	private ToCaseCommentService toCaseCommentService;
	
	@RequestMapping(value = "/insertToCaseComment")
	@ResponseBody
	public AjaxResponse<?> insertToCaseComment(Model model,ToCaseComment record) {
	    int insertCount = toCaseCommentService.insertToCaseComment(record);
	    if(insertCount <= 0) {
	    	return AjaxResponse.fail();
	    } else {
	    	return AjaxResponse.success();
	    }
	}

}
