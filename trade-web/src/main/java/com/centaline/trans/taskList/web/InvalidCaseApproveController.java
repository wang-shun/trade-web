package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.task.service.InvalidCaseApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value = "/task/invalidCaseApprove")
public class InvalidCaseApproveController {

	@Autowired
	private InvalidCaseApproveService invalidCaseApproveService;

	@RequestMapping(value = "invalidCaseApprove")
	@ResponseBody
	public Boolean invalidCaseApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String InvalidCaseApprove, String InvalidCaseApprove_response) {
		return invalidCaseApproveService.invalidCaseApprove(processInstanceVO, loanlostApproveVO, InvalidCaseApprove,
				InvalidCaseApprove_response);
	}

}
