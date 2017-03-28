package com.centaline.trans.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.LabelValService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.vo.LabelVal;

@Controller
@RequestMapping(value="/labelVal")
public class LabelValController {

	@Autowired
	private LabelValService labelValService;
	
	@RequestMapping(value="queryUserInfo")
	@ResponseBody
	public List<LabelVal> queryGuestInfo(String keyword) {
		List<LabelVal> list = labelValService.queryUserInfo(keyword);
		return list;
	}
	@RequestMapping(value="queryOrgInfo")
	@ResponseBody
	public List<LabelVal> queryOrgInfo(String keyword) {
		List<LabelVal> list = labelValService.queryOrgInfo(keyword);
		return list;
	}
}
