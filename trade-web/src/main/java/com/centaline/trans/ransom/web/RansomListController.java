package com.centaline.trans.ransom.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.web.ResultNew;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;

import io.searchbox.core.Index;

/**
 * 赎楼单列表控制器
 * @author wbwumf
 *
 */
@Controller
@RequestMapping(value = "/ransomList")
public class RansomListController {
	
	@Autowired(required = true)
	private AddRansomFormService ars;
	@Autowired(required = true)
	private RansomListFormService ransom;
	
	
	@Autowired
	private UamSessionService uamSessionService;
	/**
	 * 页面跳转 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="*/{keyFlag}")
	public String caseProcess(Model model, ServletRequest request,@PathVariable String keyFlag){
		model.addAttribute("flag",keyFlag);
		return "ransom/" + keyFlag;
	}

	@RequestMapping(value="addRansom1")
	@ResponseBody
	public String addRansom1(Model model,
			@RequestParam String jsonStr,
			ServletRequest request,HttpServletResponse response){
		
		String result = "-1";
		ResultNew rs=new ResultNew();
		SessionUser user= uamSessionService.getSessionUser();
		Double num = (Math.random()*10000);
		
		try {
			int month = new Date().getMonth()+1;
			String mon = null;
			if(month < 10){
				mon = "0" + month;
			}else{
				mon = month + "";
			}
			
//			List l = new ArrayList();
			List<ToRansomFormVo> list = JSONObject.parseArray(jsonStr, ToRansomFormVo.class);
			
			for (ToRansomFormVo arf : list) {
				arf.setRansomCode("TJ-ZH-" + mon + num.intValue()); //赎楼单编号
				arf.setLoanMoney(arf.getLoanMoney() * 10000);
				arf.setRestMoney(arf.getRestMoney() * 10000);
				arf.setCreateTime(new Date());
				arf.setCreateUser(user.getRealName());
				arf.setUpdateTime(new Date());
				arf.setUpdateUser(user.getRealName());
			}
			
			ars.addRansomForm(list);
			result = "0";
			if("0".equals(result)){
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);

				ToRansomCaseVo trco = new ToRansomCaseVo();
				
				trco.setRansomCode(list.get(0).getRansomCode());
				trco.setCaseCode(list.get(0).getCaseCode());
				trco.setBorrowerName(list.get(0).getBorrowerName());
				trco.setBorroMoney(list.get(0).getBorroMoney());
				trco.setAcceptTime(list.get(0).getPlanTime());
				trco.setCreateTime(new Date());
				trco.setCreateUser(user.getRealName());
				trco.setUpdateTime(new Date());
				trco.setUpdateUser(user.getRealName());
				
				ransom.addRansomDetail(trco);
			}else{
				String message = "新增案件失败，请刷新后再次尝试！";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(message);
			}
			
			return JSONObject.toJSONString(rs);
		} catch (BusinessException ex) {
			ex.printStackTrace();
			rs.setStatus("-1");
			rs.setMessage(ex.getMessage());
			return JSONObject.toJSONString(rs);
		}
	}
	
}
