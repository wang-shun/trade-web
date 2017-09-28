package com.centaline.trans.ransom.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <font color=red>流程引擎 任务处理</font>
 * @author wbwumf
 */
@Controller("engine.ransomController")
@RequestMapping("/engine/ransom")
public class RansomDiscontinueController {

	@RequestMapping("{taskId}/process")
	public String process(@PathVariable String taskId, String source, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes attr){
				
		return null;
	}
}
