package com.centaline.trans.bankRebate.web;

import IceInternal.Ex;
import com.aist.common.exception.BusinessException;
import com.aist.common.utils.excel.ImportExcel;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;
import com.centaline.trans.bankRebate.vo.ToBankRebateVO;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.BankRebateStatusEnum;
import com.centaline.trans.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
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
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Autowired
	private UamBasedataService uamBasedataService;
	
	/**
	 * 初始化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bankRebateList")
	public String bankRebateList(Model model) {
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
	public String newBankRebate(Model model) {
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
	public AjaxResponse<String> saveNewBankRebate(ToBankRebateInfoVO info) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			Date applyTime = new Date();
			info.getToBankRebate().setApplyTime(applyTime);
			info.getToBankRebate().setGuaranteeCompId(generateToken(applyTime));
			info.getToBankRebate().setStatus(BankRebateStatusEnum.NOT_SUBMIT.getCode());//未提交
			toBankRebateService.insertBankRebate(info);
		} catch (Exception e) {
			response.setSuccess(false);
    		response.setMessage(e.getMessage());
			logger.error("银行返利新增",e);
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
		} catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("银行返利修改",e);
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
			logger.error("银行返利删除",e);
			return new AjaxResponse<>(false,e.getMessage());
		}
	}

	/**
	 * 导入银行返利
	 * @param file
	 * @param record
	 * @return
	 */
	@RequestMapping(value="uploadExcelBankRebate")
	@ResponseBody
	public AjaxResponse<String> uploadExcelBankRebate(@RequestParam("fileupload") MultipartFile  file,ToBankRebate record){
		if(file==null||file.isEmpty()) new AjaxResponse<String>(false,"未获取到导入文件");
		Date applyTime = new Date();
		String compId = generateToken(applyTime);
		try {
			ToBankRebateInfoVO vo = checkImport(compId,file);
			record.setApplyTime(applyTime);
			record.setGuaranteeCompId(compId);
			record.setStatus(BankRebateStatusEnum.NOT_SUBMIT.getCode());
			vo.setToBankRebate(record);
			toBankRebateService.insertBankRebate(vo);
			return new AjaxResponse<String>();
		} catch (Exception e) {
			logger.error("银行返利导入",e);
			return new AjaxResponse<String>(false,e.getMessage());
		}
	}
	
	/**
	 * 返利导入模板下载
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportMatrixLeaderSheet")  
	public String exportMatrixLeaderSheet(HttpServletResponse response) throws IOException{  
	    response.setHeader("Content-Disposition","attachment; filename="+new String(("银行返利导入模板").getBytes("gb2312"),"ISO-8859-1")+".xls");
	    OutputStream out = response.getOutputStream();
		toBankRebateService.exportMatrixLeaderSheet(out);
		out.flush();
	    out.close();  
	    return null;  
	}

	/**
	 * 提交银行返利申请至CCAI由财务审批
	 * @param guaranteeCompId 申请批次号
	 * @return
	 */
	@RequestMapping(value = "/submitCcai")
	@ResponseBody
	public AjaxResponse<String> submitCcai(String guaranteeCompId){
		ToBankRebateInfoVO vo = toBankRebateService.selectRebateByGuaranteeCompId(guaranteeCompId);
		String msg = toBankRebateService.checkBankRebate(vo);
		if("success".equals(msg)){
			try {
				toBankRebateService.submitCcai(vo);
				return new AjaxResponse<>();
			}catch (Exception e){
				logger.error("提交银行返利申请",e);
				return new AjaxResponse<>(false,e.getMessage());
			}
		}else{
			return new AjaxResponse<>(false,msg);
		}
	}

	/**
	 * 银行返利详情查看
	 * @param guaranteeCompId  批次号
	 * @return
	 */
	@RequestMapping(value = "/details")
	public String submitCcai(String guaranteeCompId,Model model){
		ToBankRebateInfoVO vo = toBankRebateService.selectRebateByGuaranteeCompId(guaranteeCompId);
		model.addAttribute("org",uamUserOrgService.getOrgById(vo.getToBankRebate().getDeptId()));
		model.addAttribute("rebate",vo);
		return "bankRebate/bankRebateDetails";
	}

	
	/* 根据录入时间毫秒值字符串，
     * 担保公司ID的批次唯一标识符
     */  
   private String generateToken(Date applyTime){
	   StringBuilder s = new StringBuilder();
	   s.append("P");
	   s.append(DateUtil.getFormatDate(applyTime, "yyyyMMddHHmmss"));
	   return s.toString();
    }


	private ToBankRebateInfoVO checkImport(String compId,MultipartFile file) throws Exception{
		StringBuilder msg = new StringBuilder();
		ToBankRebateInfoVO ret = new ToBankRebateInfoVO();//返回对象
		//转换导入信息
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<ToBankRebateVO> list = ie.getDataList(ToBankRebateVO.class);
		Row firstRow = ie.getRow(0);
		if(firstRow.getLastCellNum()<5) throw new BusinessException("请上传正确的导入模板!");
		Workbook wb = firstRow.getSheet().getWorkbook();
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();//公式求值器
		List<ToBankRebateInfo> infos  = new ArrayList<>();
		//对转换后的信息进行校验和转换set
		for (int i=0;i<list.size();i++) {
			ToBankRebateInfo item = new ToBankRebateInfo();
			ToBankRebateVO vo = list.get(i);
			Row row = ie.getRow(i+1);
			Cell warrant = row.getCell(3);
			int rowNum = i + 2;//用于返回给客户提示使用
			//单元格格式为公式时  重新计算返利金额
			if(Cell.CELL_TYPE_FORMULA == warrant.getCellType()){
				item.setRebateWarrant(new BigDecimal(evaluator.evaluate(warrant).getNumberValue()));
			}else{
				if(StringUtils.isNumeric(vo.getRebateWarrant())){
					item.setRebateWarrant(new BigDecimal(vo.getRebateWarrant()));
				}else{
					msg.append("第"+rowNum+"行，权证返利金额["+vo.getRebateWarrant()+"]不正确<br/>");
				}
			}
			Cell business = row.getCell(4);
			if(Cell.CELL_TYPE_FORMULA == business.getCellType()){
				item.setRebateBusiness(new BigDecimal(evaluator.evaluate(business).getNumberValue()));
			}else{
				if(StringUtils.isNumeric(vo.getRebateBusiness())){
					item.setRebateBusiness(new BigDecimal(vo.getRebateBusiness()));
				}else{
					msg.append("第"+rowNum+"行，业务返利金额["+vo.getRebateBusiness()+"]不正确<br/>");
				}
			}
			//返利金额
			if(StringUtils.isNumeric(vo.getRebateMoney())){
				item.setRebateMoney(new BigDecimal(vo.getRebateMoney()));
			}else{
				msg.append("第"+rowNum+"行，返利金额["+vo.getRebateMoney()+"]不正确<br/>");
			}
			//比对案件编号是否存在
			ToCaseInfo caseInfo = toCaseInfoService.findToCaseInfoByCaseCode(vo.getCaseCode());
			if(caseInfo!=null && StringUtils.isNotBlank(caseInfo.getCcaiCode())){
				item.setCaseCode(caseInfo.getCaseCode());
				item.setCcaiCode(caseInfo.getCcaiCode());
			}else{
				msg.append("第"+rowNum+"行，案件编号["+vo.getCaseCode()+"]未获取到对应案件或成交报告编号<br/>");
			}
			//银行名称检查
			Dict bank = uamBasedataService.findDictByTypeAndName("bank_rebate_bank",vo.getBankName());
			if(bank!=null){
				item.setBankName(bank.getCode());
			}else{
				msg.append("第"+rowNum+"行，银行名称["+vo.getBankName()+"]未在系统中找到<br/>");
			}
			//导入担保公司ID的批次唯一标识符
			item.setGuaranteeCompId(compId);
			infos.add(item);
		}
		if(msg.length()>0) throw new BusinessException(msg.toString());
		ret.setToBankRebateInfoList(infos);
		return ret;
	}

}
