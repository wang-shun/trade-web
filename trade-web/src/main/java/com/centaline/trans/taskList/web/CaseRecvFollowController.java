package com.centaline.trans.taskList.web;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.service.CaseRecvService;
import com.centaline.trans.cases.vo.CaseRecvVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;

@Controller
@RequestMapping(value = "/task/caseRecvFollow")
public class CaseRecvFollowController {
	@Autowired
	private CaseRecvService caseRecvService;
	
	@RequestMapping(value="submit")
	public String submit(HttpServletRequest request){
		return "task/taskCaseRecvFollow";
	}
	
	@RequestMapping(value="process")
	public String toProcess(HttpServletRequest request,Model model,String caseCode){
		if(null!=caseCode&&caseCode!=""){
			CaseRecvVO caseRecvVO = caseRecvService.selectFullCaseRecvVO(caseCode);
			model.addAttribute("caseRecvVO", caseRecvVO);			
		}
		return "task/taskCaseRecvFollow";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public HashMap<String, String> saveFirstFollow(HttpServletRequest request,
			String caseCode,ToPropertyInfo toPropertyInfo,ToSign toSign,
			ToCaseRecv toCaseRecv,String payType,ToTax toTax,ToCaseComment toCaseComment,
			String content,String businessLoanWarn) {
		HashMap<String, String> hashMap = new HashMap<String,String>();
		CaseRecvVO caseRecvVO = new CaseRecvVO();
		if(null!=caseCode&&caseCode!=""){
		caseRecvVO.setPayType(payType);
		caseRecvVO.setToCaseComment(toCaseComment);
		caseRecvVO.setToCaseRecv(toCaseRecv);
		caseRecvVO.setToPropertyInfo(toPropertyInfo);
		caseRecvVO.setToSign(toSign);
		caseRecvVO.setToTax(toTax);
		BizWarnInfo bizWarnInfo = new BizWarnInfo();
		bizWarnInfo.setContent(content);
		bizWarnInfo.setStatus(businessLoanWarn);
		caseRecvVO.setBizWarnInfo(bizWarnInfo);
		//统一设置caseCode,建议设置完field之后再调用该方法来避免空指针异常,因为刚new出来时他的field都是null;
		caseRecvVO.setCaseCode(caseCode);
		}else{
			hashMap.put("message", "lackCaseCode");
		}
		caseRecvService.insertSelective(caseRecvVO);
		
		hashMap.put("message", "success");
		return hashMap;
	}
}
