package com.centaline.trans.ransom.web;

import java.math.BigDecimal;
import java.util.Calendar;
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
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.web.ResultNew;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomListFormService;

/**
 * 赎楼单列表控制器
 * @author wbwumf
 *
 */
@Controller
@RequestMapping(value = "/ransomList")
public class RansomListController {
	
	@Autowired(required = true)
	private AddRansomFormService addRansomFormService;
	@Autowired(required = true)
	private RansomListFormService ransomListFormService;
	
	
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

	@RequestMapping(value="addRansom")
	@ResponseBody
	public String addRansom(Model model,
			@RequestParam String jsonStr,
			ServletRequest request,HttpServletResponse response){
		
		String result = "-1";
		ResultNew rs=new ResultNew();
		SessionUser user= uamSessionService.getSessionUser();
//		Double num = (Math.random()*10000);
		
		try {
			Calendar ca = Calendar.getInstance();
			int month = ca.get(Calendar.MONTH);// 获取月份
			int day = ca.get(Calendar.DATE);// 获取日
			int minute = ca.get(Calendar.MINUTE);// 分
			int hour = ca.get(Calendar.HOUR);// 小时
			int second=ca.get(Calendar.SECOND);//秒
		      
			List<ToRansomFormVo> list = JSONObject.parseArray(jsonStr, ToRansomFormVo.class);
			
			for (ToRansomFormVo arf : list) {
				arf.setRansomCode("TJ-ZH-" + month + day + minute + hour + second); //赎楼单编号
				arf.setLoanMoney(arf.getLoanMoney() * 10000);
				arf.setRestMoney(new BigDecimal(arf.getRestMoney().doubleValue() * 1000));
				arf.setCreateTime(new Date());
				arf.setCreateUser(user.getRealName());
				arf.setUpdateTime(new Date());
				arf.setUpdateUser(user.getRealName());
			}
			
			addRansomFormService.addRansomForm(list);
			result = "0";
			if("0".equals(result)){
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);

				ToRansomCaseVo trco = new ToRansomCaseVo();
				//赎楼列表单插入数据
				trco.setRansomCode(list.get(0).getRansomCode());
				trco.setCaseCode(list.get(0).getCaseCode());
				trco.setBorrowerName(list.get(0).getBorrowerName());
				trco.setBorroMoney(list.get(0).getBorroMoney());
				trco.setAcceptTime(list.get(0).getPlanTime());
				trco.setCreateTime(new Date());
				trco.setCreateUser(user.getRealName());
				trco.setUpdateTime(new Date());
				trco.setUpdateUser(user.getRealName());
				
				ransomListFormService.addRansomDetail(trco);
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
	
	@RequestMapping(value="queryRansomCode")
	@ResponseBody
	public String queryRansomByCasecode(String caseCode) {
		
		ResultNew rs=new ResultNew();
		try {
			ToRansomCaseVo trco = new ToRansomCaseVo();
			trco = ransomListFormService.getRansomCase(caseCode);
			String result = "-1";
			//如果赎楼信息不为空说明已有案件编号与赎楼编号相关联
			if(trco != null) {
				String message = "案件关联已被关联，请重新选择！";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(message);
			}else {
				result = "0";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);
			}
			return JSONObject.toJSONString(rs);
//			return "true";
		} catch (BusinessException ex) {
			ex.printStackTrace();
			rs.setStatus("0");
			rs.setMessage(ex.getMessage());
			return JSONObject.toJSONString(rs);
		}
	}
}
