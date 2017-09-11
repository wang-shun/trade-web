package com.centaline.trans.taskList.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/task/caseRecvFollow")
public class CaseRecvFollowController {

	
	
	@RequestMapping(value="process")
	//@ResponseBody
	public String toProcess(HttpServletRequest request){
		return "task/taskCaseRecvFollow";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public HashMap<String, String> saveFirstFollow(HttpServletRequest request,String numberOfBuyHouse,String payType) {
		System.out.println("numberOfBuyHouse:"+numberOfBuyHouse);
		System.out.println("payType:"+payType);
		HashMap<String, String> hashMap = new HashMap<String,String>();
		hashMap.put("message", "success");
		return hashMap;
	}
}
