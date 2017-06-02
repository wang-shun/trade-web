package com.centaline.parportal.mobile.taskflow.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;

@Controller
@RequestMapping(value = "/approve")
public class ApproveHistoryController {
	
	@Autowired
	private QuickGridService quickGridService;
	
	@Value("${img.sh.saleweb.url:http://aimg.sh.centanet.com/salesweb/image/}")
	private String imgUrl;
	
	@RequestMapping(value = "findApproveList")
	@ResponseBody
	public Object findApproveList(@RequestParam(required = true)String caseCode,
			Integer approveType,@RequestParam(required = true)String processInstanceId) {
		JQGridParam gp = new JQGridParam("queryLoanlostApproveList",false);
		gp.put("caseCode", caseCode).put("approveType", approveType).put("processInstanceId", processInstanceId);
        Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
        
//		toApproveRecord.setContent((b ? "通过" : "不通过")
//				+ (c ? ",没有审批意见。" : ",审批意见为：" + loanLost_response));
        for (Map<String, Object> map : pages) {
        	Object avatarObj = map.get("AVATAR");
        	if(avatarObj != null) {
        		map.put("AVATAR", imgUrl+ String.valueOf(avatarObj) + ".jpg");
        	}
		}
        
        return pages.getContent();
	}
}
