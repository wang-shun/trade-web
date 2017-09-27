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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.web.ResultNew;
import com.centaline.trans.ransom.entity.AddRansomForm;
import com.centaline.trans.ransom.service.AddRansomFormService;

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

	/**
	 * 新建案件
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addRansom")
	public String addRansom(Model model, AddRansomForm ransom,ServletRequest request,HttpServletResponse response){
		ResultNew rs=new ResultNew();
		
		try{
			
			String status = ars.insert(ransom) + "";
			if("0".equals(status)){
				rs.setStatus(status);
				rs.setCode(status);
				rs.setMessage(status);
			}else{
				String message = "新增案件失败，请刷新后再次尝试！";
				rs.setStatus(status);
				rs.setCode(status);
				rs.setMessage(message);
			}
		}catch(BusinessException ex){
			rs.setStatus("-1");
			rs.setMessage(ex.getMessage());
			return JSONObject.toJSONString(rs);
		}
		
		return JSONObject.toJSONString(rs);
	}
	
	@RequestMapping(value="addRansom1")
	@ResponseBody
	public String addRansom1(Model model,
			@RequestParam("jsonStr") String jsonStr,
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
			
			List list = new ArrayList();

			/*if(jsonStr != null && jsonStr.length > 1 && jsonStr instanceof String[]){
				String[] str = (String[])jsonStr;
				for (int i = 0 ; i < jsonStr.length ; i++) {
					
					AddRansomForm arf = new AddRansomForm();
					arf = JSON.parseObject(jsonStr[i], AddRansomForm.class);
					arf.setRansomCode("TJ-ZH-" + mon + num.intValue()); //赎楼单编号
					arf.setCreateTime(new Date());
					arf.setCreateUser(user.getRealName());
					arf.setUpdateTime(new Date());
					arf.setUpdateUser(user.getRealName());
					list.add(arf);
				}
			}else if(jsonStr != null  && jsonStr.length == 1){
				AddRansomForm arf = new AddRansomForm();
				arf = JSON.parseObject(jsonStr[0], AddRansomForm.class);
				arf.setRansomCode("TJ-ZH-" + mon + num.intValue()); //赎楼单编号
				arf.setCreateTime(new Date());
				arf.setCreateUser(user.getRealName());
				arf.setUpdateTime(new Date());
				arf.setUpdateUser(user.getRealName());
				list.add(arf);
			}else{
				String message = "新增案件失败，请刷新后再次尝试！";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(message);
			}*/
//			
			ars.addRansomForm(list);
			result = "0";
			if("0".equals(result)){
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);
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
	
//	
//	/**
//	 * 跳转修改赎楼单详情 
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="ransomDetailUpdate")
//	public String ransomDetailUpdate(Model model, ServletRequest request){
//		return "/ransom/ransomDetailUpdate";
//	}
//	
//	/**
//	 * 赎楼单详情修改跳转赎楼详情页
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="detailUpdate")
//	public String detailUpdate(Model model, ServletRequest request){
//		return "/ransom/ransomDetail";
//	}
}
