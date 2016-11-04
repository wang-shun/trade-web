package com.centaline.trans.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;


@Controller
@RequestMapping(value="/api/custom")
public class CustomerInfoBackController {
	
	@Autowired
	private TgGuestInfoService tgguestInfoService;
	

	/**
	 * 功能：成交客户回写
	 * @param guestCode[客户编码]
	 * 描述：根据 guest_code[客户编码] 为条件去查询 name[姓名], mobile[移动电话], cert_type[证件类型], cert_code[证件编号], work_location[工作单位]
	 * @author zhangxb16
	 */
	@RequestMapping(value="info", method={RequestMethod.POST})
	@ResponseBody
	public AjaxResponse dealList(HttpServletRequest request, HttpServletResponse response, Model model, String guestCode){
		
		try{
			if("".equals(guestCode) || null==guestCode){
				throw new BusinessException("客户编码为必填字段！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			return AjaxResponse.fail(ex.getMessage());
		}
		
		TgGuestInfo guestinfo=null;
		try{
			guestinfo=tgguestInfoService.findTgGuestInfoByGuestCode(guestCode);
			if(null != guestinfo){
				String data="{[\"name\":"+guestinfo.getGuestName()+",\"mobile\":"+guestinfo.getGuestPhone()+", \"cert_type\":"+guestinfo.getIdentifyType()+", \"cert_code\":"+guestinfo.getIdentifyNumber()+", \"work_location\":"+guestinfo.getWorkUnit()+"]}";
				return AjaxResponse.successContent(data);
			}else{
				return AjaxResponse.fail("根据该客户编码未查询到结果！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			return AjaxResponse.fail("查询失败,请刷新后再次尝试！");
		}
	}

	
	// 成交客户回写
	@RequestMapping(value="deal")
	public String tiaozhuan4(){
		
		return "common/deal";
	}
	
	
}
