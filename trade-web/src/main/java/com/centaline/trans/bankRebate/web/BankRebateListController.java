package com.centaline.trans.bankRebate.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import com.aist.common.exception.BusinessException;
import com.aist.common.utils.excel.ImportExcel;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.service.ToBankRebateInfoService;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.bankRebate.vo.ToBankRebateVO;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.eval.entity.ToEvaSettleUpdateLog;
import com.centaline.trans.utils.DateUtil;
/**
 * Description:银行返利
 * @author wbwangxj
 *
 */
@Controller
@RequestMapping(value = "bankRebate")
public class BankRebateListController {
	
	@Autowired
	private ToBankRebateInfoService toBankRebateInfoService;
	
	@Autowired
	private ToBankRebateService toBankRebateService;
	
	@Autowired(required = true)
    UamSessionService uamSessionService;
	
	/**
	 * 初始化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bankRebateList")
	public String bankRebateList(HttpServletRequest request,Model model) {
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("user", user);
		return "bankRebate/bankRebateList";
	}
	
	/**
	 * 删除担保公司返利批次
	 * @param request
	 * @param guaranteeCompId
	 * @return
	 */
	@RequestMapping(value="/deleteCompany")
	public String deleteCompany(HttpServletRequest request,String[] guaranteeCompId) {
		
		for (String guaCompId : guaranteeCompId) {
			toBankRebateService.deleteByGuaranteeCompId(guaCompId);
			toBankRebateInfoService.deleteRebateInfoByGuaranteeCompId(guaCompId);
		}
		return "redirect:bankRebateList";
	}
	
	/**
	 * 跳转到修改页面
	 * @param request
	 * @param model
	 * @param guaranteeCompId
	 * @param pkid
	 * @return
	 */
	@RequestMapping(value="/bankRebateUpdate")
	public String bankRebateUpdate(Long pkid, Model model, HttpServletRequest request,String guaranteeCompId) {
		SessionUser user = uamSessionService.getSessionUser();
		//查询修改记录列表
		List<ToBankRebateInfo> toBankRebateInfoList = toBankRebateInfoService.selectRebateInfoByGuaranteeCompId(guaranteeCompId);
		ToBankRebate toBankRebate = toBankRebateService.selectByPrimaryKey(pkid);
		model.addAttribute("user", user);
		model.addAttribute("toBankRebate", toBankRebate);
		model.addAttribute("toBankRebateInfoList",toBankRebateInfoList);
		return "bankRebate/bankRebateUpdate";
	}
	
	
	/**
	 * 批量导入
	 * @param file
	 * @param guaranteeCompany
	 * @param rebateTotal
	 * @param companyAccount
	 * @param applyPerson
	 * @param comment
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="uploadExcelBankRebate")
	public String uploadExcelBankRebate(@RequestParam("fileupload") MultipartFile  file,  String[] guaranteeCompany,BigDecimal[] rebateTotal,
			String[] companyAccount,String[] applyPerson,String[] comment,
            HttpServletRequest request, HttpServletResponse response){
		
		 ToBankRebate toBankRebate = new ToBankRebate();
		 toBankRebate.setGuaranteeCompany(guaranteeCompany[0]);
		 toBankRebate.setRebateTotal(rebateTotal[0]);
		 toBankRebate.setApplyPerson(applyPerson[0]);
		 toBankRebate.setComment(comment[0]);
		
		 Date applyTime = new Date();
		 toBankRebate.setApplyTime(applyTime);
		 toBankRebate.setGuaranteeCompId(generateToken(applyTime));
		 toBankRebate.setStatus("0");
		 toBankRebateService.insertSelective(toBankRebate);
		ArrayList<ToBankRebateInfo> inIncomes = new ArrayList<ToBankRebateInfo>();
		String  ex_message = "导入成功";
		try {
			ImportExcel ie = new ImportExcel(file, 0, 0);
			List<ToBankRebateVO> list = ie.getDataList(ToBankRebateVO.class);
			for (int i=0;i<list.size();i++) {

                ToBankRebateInfo item = new ToBankRebateInfo();
				int rowNum = i+2;
				ToBankRebateVO vo = list.get(i);
				
				if(StringUtils.isEmpty(vo.getCcaiCode())) {
					break;
				}
				//导入成交编号
                item.setCcaiCode(vo.getCcaiCode());
                //导入银行
                item.setBankName(vo.getBankName());
                //导入返利金额
                try {
                	BigDecimal  amount = new BigDecimal(vo.getRebateMoney());
                    item.setRebateMoney(amount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入金额信息类型转换失败");
            		return "bankRebate/bankRebateList";
				}
                
                //导入权证返利金额
            	try {
            		BigDecimal  amount = new BigDecimal(vo.getRebateWarrant());
                    item.setRebateWarrant(amount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入金额信息类型转换失败");
            		return "bankRebate/bankRebateList";
				}
            	
            	//导入业务返利金额
            	try {
            		BigDecimal  amount = new BigDecimal(vo.getRebateBusiness());
                    item.setRebateBusiness(amount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入金额信息类型转换失败");
            		return "bankRebate/bankRebateList";
				}
            	//导入担保公司ID的批次唯一标识符
            	item.setGuaranteeCompId(generateToken(applyTime));
                
                inIncomes.add(item);
            }
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ex_message = "文件读取异常";
		}
		//插入数据
		for(ToBankRebateInfo toBankRebateInfo:inIncomes){
			toBankRebateInfoService.insertSelective(toBankRebateInfo);
		}
    	request.setAttribute("ex_message",ex_message );
		return "redirect:bankRebateList";
	}
	
	/* 根据录入时间毫秒值字符串，
     * 担保公司ID的批次唯一标识符
     */  
   public String generateToken(Date applyTime){  
	   StringBuilder s = new StringBuilder();
	   s.append("P");
	   s.append(DateUtil.getFormatDate(applyTime, "yyyyMMddHHmmss"));
	   return s.toString();
    }
   	
   /*public static void main(String[] args) {
	   Date applyTime = new Date();
	   StringBuilder s = new StringBuilder();
	   s.append("P");
	   s.append(DateUtil.getFormatDate(applyTime, "yyyyMMddHHmmss"));
	   System.out.println(s.toString());
   }*/
	
  
}
