package com.centaline.trans.bankRebate.web;

import com.aist.common.utils.excel.ImportExcel;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;
import com.centaline.trans.bankRebate.vo.ToBankRebateVO;
import com.centaline.trans.utils.DateUtil;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Description:银行返利
 * @author wbwangxj
 *
 */
@Controller
@RequestMapping(value = "bankRebate")
public class BankRebateListController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ToBankRebateService toBankRebateService;
	
	@Autowired(required = true)
    private UamSessionService uamSessionService;

	@Autowired
	private UamUserOrgService uamUserOrgService;
	
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
	public String deleteCompany(String[] guaranteeCompId) {

		toBankRebateService.deleteByGuaranteeCompId(guaranteeCompId);

		return "redirect:bankRebateList";
	}

	/**
	 * 新增页面初始化化
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/newBankRebate")
	public String newBankRebate(HttpServletRequest request,Model model) {
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("user", user);
		return "bankRebate/newBankRebate";
	}
	
	/**
	 * 保存新增银行返利批次
	 * @param request
	 * @param model
	 * @param info
	 * @param toBankRebate
	 * @return
	 */
	@RequestMapping(value="/saveNewBankRebate")
	@ResponseBody
	public AjaxResponse<String> saveNewBankRebate(HttpServletRequest request,Model model,ToBankRebateInfoVO info) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			Date applyTime = new Date();
			info.getToBankRebate().setApplyTime(applyTime);
			info.getToBankRebate().setGuaranteeCompId(generateToken(applyTime));
			info.getToBankRebate().setStatus("0");//未提交
			toBankRebateService.insertBankRebate(info);
		} catch (Exception e) {
			response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
		}
		return response;
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
	public String bankRebateUpdate(Model model,String guaranteeCompId) {
		ToBankRebateInfoVO vo = toBankRebateService.selectRebateByGuaranteeCompId(guaranteeCompId);
		model.addAttribute("org",uamUserOrgService.getOrgById(vo.getToBankRebate().getDeptId()));
		model.addAttribute("rebate",vo);
		return "bankRebate/bankRebateUpdate";
	}
	
	/**
	 * 保存修改记录
	 * @param toBankRebateInfoVO
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "submitChangeBankRebate")
	@ResponseBody
	public AjaxResponse<String> submitChangeBankRebate(ToBankRebateInfoVO toBankRebateInfoVO) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toBankRebateService.updateBankRebate(toBankRebateInfoVO);
		}
		catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    	}
		return response;
	}

	/**
	 * 删除担保公司返利案件
	 * @param request
	 * @param guaranteeCompId
	 * @return
	 */
	@RequestMapping(value="/deleteBankRebateInfo",method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse<String> deleteToBankRebateInfo(long pkid) {
		try {
			toBankRebateService.deleteTobankRebateInfo(pkid);
			return new AjaxResponse<>();
		}catch (Exception e){
			return new AjaxResponse<>(false,e.getMessage());
		}
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
	public String uploadExcelBankRebate(@RequestParam("fileupload") MultipartFile  file,ToBankRebate record,
            HttpServletRequest request, HttpServletResponse response){
		
		 ToBankRebate toBankRebate = new ToBankRebate();
		 toBankRebate.setGuaranteeCompany(record.getGuaranteeCompany());
		 toBankRebate.setRebateTotal(record.getRebateTotal());
		 toBankRebate.setApplyPerson(record.getApplyPerson());
		 toBankRebate.setComment(record.getComment());
		
		 Date applyTime = new Date();
		 toBankRebate.setApplyTime(applyTime);
		 toBankRebate.setGuaranteeCompId(generateToken(applyTime));
		 toBankRebate.setStatus("0");
		 // toBankRebateService.insertSelective(toBankRebate);
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
            		return "redirect:bankRebateList";
				}
                
                //导入权证返利金额
            	try {
            		BigDecimal  amount = new BigDecimal(vo.getRebateWarrant());
                    item.setRebateWarrant(amount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入金额信息类型转换失败");
            		return "redirect:bankRebateList";
				}
            	
            	//导入业务返利金额
            	try {
            		BigDecimal  amount = new BigDecimal(vo.getRebateBusiness());
                    item.setRebateBusiness(amount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入金额信息类型转换失败");
            		return "redirect:bankRebateList";
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
		// for(ToBankRebateInfo toBankRebateInfo:inIncomes){
		// 	toBankRebateInfoService.insertSelective(toBankRebateInfo);
		// }
    	request.setAttribute("ex_message",ex_message );
		return "redirect:bankRebateList";
	}
	
	/**
	 * 返利导入模板下载
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportMatrixLeaderSheet")  
	public String exportMatrixLeaderSheet(HttpServletResponse response) throws IOException{  
	    response.setHeader("Content-Disposition","attachment; filename="+new String(("返利导入格式").getBytes("gb2312"),"ISO-8859-1")+".xls");  
	    OutputStream out = response.getOutputStream();  
	    // toBankRebateInfoService.exportMatrixLeaderSheet(out);
	    out.close();  
	    return null;  
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
