package com.centaline.trans.income.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aist.common.exception.BusinessException;
import com.aist.common.utils.excel.ImportExcel;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.income.entity.TsAwardDispatch;
import com.centaline.trans.income.entity.TsIncomeStatistics;
import com.centaline.trans.income.entity.TsMonthlyPerformCheck;
import com.centaline.trans.income.service.TsAwardDispatchService;
import com.centaline.trans.income.service.TsIncomeStatisticsService;
import com.centaline.trans.income.service.TsMonthlyPerformCheckService;
import com.centaline.trans.income.vo.BaseAmountVO;
import com.centaline.trans.income.vo.ScoreVO;
import com.centaline.trans.income.vo.TsIncomeStatisticsVO;
import com.centaline.trans.income.vo.TsMonthlyPerformCheckVO;

/**
 * 
 * <p>Project: 佣金管理</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015</p>
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value="/income")
public class IncomeListController {
	@Autowired
	ToCaseService toCaseService;
	@Autowired
	ToPropertyInfoService toPropertyInfoService;
	@Autowired
	TsIncomeStatisticsService tsIncomeStatisticsService;
	@Autowired
	TsMonthlyPerformCheckService tsMonthlyPerformCheckService;
	@Autowired
	UamBasedataService uamBasedataService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private TsAwardDispatchService tsAwardDispatchService;
	/**
	 * 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit")
	public String incomeList(Model model, ServletRequest request){
		
		return "income/income_edit";
	}
	
	/**
	 * Excel导入-收益导入
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="uploadExcelIncomeStatistics")
	public String uploadExcelIncomeStatistics(@RequestParam("fileupload") MultipartFile  file,  
            HttpServletRequest request, HttpServletResponse response){
		//int inType= Integer.parseInt(request.getParameter("inType"));
		ArrayList<TsIncomeStatistics> inIncomes = new ArrayList<TsIncomeStatistics>();
		String  ex_message = "导入成功";
		try {
			ImportExcel ie = new ImportExcel(file, 0, 0);
			List<TsIncomeStatisticsVO> list = ie.getDataList(TsIncomeStatisticsVO.class);
			for (int i=0;i<list.size();i++) {

                TsIncomeStatistics item = new TsIncomeStatistics();
				int rowNum = i+2;
				TsIncomeStatisticsVO vo = list.get(i);
				if(StringUtils.isEmpty(vo.getCaseCode()) ||
						StringUtils.isEmpty(vo.getProAddr()) ||
						StringUtils.isEmpty(vo.getIncomeItem()) ||
						StringUtils.isEmpty(vo.getIncomeAmount())){
					request.setAttribute("ex_message", "文件第"+rowNum+"行存在空值");
            		return "income/income_edit";
				}
				//案件编号
                ToCase toCase = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
                if(toCase==null ||toCase.getPkid()==null){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行无法找到案件信息,请检查案件编码");
            		return "income/income_edit";
                }
                item.setCaseCode(vo.getCaseCode());
                //案件地址
				ToPropertyInfo record = new ToPropertyInfo();
				record.setCaseCode(vo.getCaseCode());
				record.setPropertyAddr(vo.getProAddr());
				ToPropertyInfo info = toPropertyInfoService.findToPropertyInfoByCaseCodeAndAddr(record);
                if(info == null || info.getPkid() == null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行案件地址信息无法找到");
            		return "income/income_edit";
                }
                //导入类型
				Dict dict = uamBasedataService.findDictByTypeAndName(TransDictEnum.TFWBM.getCode(), vo.getIncomeItem());
				if(dict==null){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行收益类别信息无法找到");
            		return "income/income_edit";
				}
                item.setIncomeItem(dict.getCode());
                Dict parentDict = uamBasedataService.findDictById(dict.getParentId());
                item.setIncomeCat(parentDict.getCode());
                //导入金额
            	try {
            		BigDecimal  amount = new BigDecimal(vo.getIncomeAmount());
                    item.setIncomeAmount(amount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入金额信息类型转换失败");
            		return "income/income_edit";
				}
                //导入时间
                item.setImportTime(new Date());
                //记佣归属日
                if(CasePropertyEnum.TPJA.getCode().equals(toCase.getCaseProperty())){
                	item.setIncomeBelongDay(new Date());
                }
                //变更单据
                int monthCount = tsIncomeStatisticsService.queryMonthCountByCaseCode(vo.getCaseCode());
                item.setIsChange("1");
                if(monthCount==0)item.setIsChange("0");
                
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
		for(TsIncomeStatistics income:inIncomes){
			//导入批次
            int maxNo = tsIncomeStatisticsService.queryMaxNoByCaseCode(income);
            income.setImportNo(maxNo+1);
			tsIncomeStatisticsService.insertSelective(income);
		}
    	request.setAttribute("ex_message",ex_message );
		return "income/income_edit";
	}
	
	/**
	 * Excel导入-月度金融产品完成单数导入
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="uploadExcelUserMonthCount")
	public String uploadExcelUserMonthCount(@RequestParam("fileupload") MultipartFile  file,  
            HttpServletRequest request, HttpServletResponse response){
		//int inType= Integer.parseInt(request.getParameter("inType"));
		ArrayList<TsMonthlyPerformCheck> inIncomes = new ArrayList<TsMonthlyPerformCheck>();
		String  ex_message = "导入成功";
		try {
			ImportExcel ie = new ImportExcel(file, 0, 0);
			List<TsMonthlyPerformCheckVO> list = ie.getDataList(TsMonthlyPerformCheckVO.class);
			for (int i=0;i<list.size();i++) {

				TsMonthlyPerformCheck item = new TsMonthlyPerformCheck();
				int rowNum = i+2;
				TsMonthlyPerformCheckVO vo = list.get(i);
				if(StringUtils.isEmpty(vo.getUserCode()) ||
						StringUtils.isEmpty(vo.getMonthCount())){
					request.setAttribute("ex_message", "文件第"+rowNum+"行存在空值");
            		return "income/income_edit";
				}
				//员工编号
				User user = uamUserOrgService.getUserByEmployeeCode(vo.getUserCode());
                if(user==null ||user.getId()==null){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行无法找到员工信息,请检查员工编号");
            		return "income/income_edit";
                }
                item.setParticipantId(user.getId());
                item.setOrgId(user.getOrgId());
                
                //当月金融产品完成单数（包含外单）
            	try {
            		int   monthCount = new Integer(vo.getMonthCount());
                    item.setEplusCaseNoAll(monthCount);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("ex_message", "文件第"+rowNum+"行收入当月金融产品完成单数（包含外单）类型转换失败");
            		return "income/income_edit";
				}
                //导入时间
                item.setBelongMonth(new Date());
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
		for(TsMonthlyPerformCheck income:inIncomes){
			tsMonthlyPerformCheckService.insertSelective(income);
		}
    	request.setAttribute("ex_message",ex_message );
		return "income/income_edit";
	}
	
	/**
	 * 评分导入
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="importScore")
	public String importScore(@RequestParam("fileupload") MultipartFile  file,  
            HttpServletRequest request, HttpServletResponse response){
		ArrayList<TsAwardDispatch> awardDispatchs = new ArrayList<TsAwardDispatch>();
		String  ex_message = "导入成功";
		try {
			ImportExcel ie = new ImportExcel(file, 0, 0);
			List<ScoreVO> list = ie.getDataList(ScoreVO.class);
			for (int i=0;i<list.size();i++) {
				
				TsAwardDispatch item = new TsAwardDispatch();
				int rowNum = i+2;
				ScoreVO vo = list.get(i);
				if(StringUtils.isEmpty(vo.getEmployeeCode()) && StringUtils.isEmpty(vo.getCaseCode())){
					continue;
				}
				if(StringUtils.isEmpty(vo.getCaseCode()) ||
						StringUtils.isEmpty(vo.getEmployeeCode()) ||
						vo.getSatisfyRating() == null ||
						StringUtils.isEmpty(vo.getPhoneAccuracy()) ||
						StringUtils.isEmpty(vo.getManagerName()) ||
						StringUtils.isEmpty(vo.getMasterPhoneAccuracy()) ||
						StringUtils.isEmpty(vo.getChiefName()) ||
						StringUtils.isEmpty(vo.getChiefPhoneAccuracy())||
						StringUtils.isEmpty(vo.getManagerName())||
						StringUtils.isEmpty(vo.getManagerPhoneAccuracy())){
					request.setAttribute("ex_message", "文件第"+rowNum+"行存在空值");
            		return "income/income_edit";
				}
				//案件编号
                ToCase toCase = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
                if(toCase==null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行无法找到案件信息,请检查案件编码");
            		return "income/income_edit";
                }
                item.setCaseCode(vo.getCaseCode());
                //案件地址
				ToPropertyInfo record = new ToPropertyInfo();
				record.setCaseCode(vo.getCaseCode());
				record.setPropertyAddr(vo.getPropertyAddr());
				ToPropertyInfo info = toPropertyInfoService.findToPropertyInfoByCaseCodeAndAddr(record);
                if(info == null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行案件地址信息无法找到");
            		return "income/income_edit";
                }
                User user = uamUserOrgService.getUserByEmployeeCode(vo.getEmployeeCode());
                if(user == null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行员工信息无法找到");
            		return "income/income_edit";
                }
                item.setParticipant(user.getId());
                item.setPhoneAccuracy(Double.parseDouble(vo.getPhoneAccuracy()));
                item.setSatisfyRating(vo.getSatisfyRating());
                item.setCaseCode(vo.getCaseCode());
                awardDispatchs.add(item);
                User masterUser = getParentUser(user.getOrgId(),vo.getMasterName());
                if(masterUser == null){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行主管信息无法找到");
            		return "income/income_edit";
                }
                item = new TsAwardDispatch();
                item.setParticipant(masterUser.getId());
                item.setPhoneAccuracy(Double.parseDouble(vo.getManagerPhoneAccuracy()));
                item.setSatisfyRating(vo.getSatisfyRating());
                item.setCaseCode(vo.getCaseCode());
                awardDispatchs.add(item);
                
                Org masterOrg = uamUserOrgService.getOrgById(masterUser.getOrgId());
                Org chiefOrg = uamUserOrgService.getOrgById(masterOrg.getParentId());
                User chiefUser = getParentUser(chiefOrg.getId(),vo.getChiefName());
                if(chiefUser == null){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行总监信息无法找到");
            		return "income/income_edit";
                }
                item = new TsAwardDispatch();
                item.setParticipant(chiefUser.getId());
                item.setPhoneAccuracy(Double.parseDouble(vo.getChiefPhoneAccuracy()));
                item.setSatisfyRating(vo.getSatisfyRating());
                item.setCaseCode(vo.getCaseCode());
                awardDispatchs.add(item);

                User managerUser = getParentUser(chiefOrg.getParentId(),vo.getManagerName());
                if(managerUser == null){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行总经理信息无法找到");
            		return "income/income_edit";   
                }
                item = new TsAwardDispatch();
                item.setParticipant(managerUser.getId());
                item.setPhoneAccuracy(Double.parseDouble(vo.getManagerPhoneAccuracy()));
                item.setSatisfyRating(vo.getSatisfyRating());
                item.setCaseCode(vo.getCaseCode());
                awardDispatchs.add(item);
            }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (IOException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (InstantiationException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		}
		try{
			//插入数据
			tsAwardDispatchService.saveTsAwardDispatchBatch(awardDispatchs);
		}catch(BusinessException e1){
			e1.printStackTrace();
			ex_message = e1.getMessage();
		}catch(Exception e){
			e.printStackTrace();
			ex_message = "插入数据异常";
		}
    	request.setAttribute("ex_message",ex_message );
		return "income/income_edit";
	}
	
	private User getParentUser(String orgId,String realName){
		User returnUser = null;
        List<User> list = uamUserOrgService.getUserByBelongOrgId(orgId);
        if(CollectionUtils.isNotEmpty(list)){
        	for(User u : list){
        		if(StringUtils.equals(u.getRealName(),realName)){
        			returnUser = u;
        			break;
        		}
        	}
        }
        return returnUser;
	}
	
	/**
	 * 导入计件工资
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="importBaseAmount")
	public String importBaseAmount(@RequestParam("fileupload") MultipartFile  file,  
            HttpServletRequest request, HttpServletResponse response){
		ArrayList<TsAwardDispatch> awardDispatchs = new ArrayList<TsAwardDispatch>();
		String  ex_message = "导入成功";
		try {
			ImportExcel ie = new ImportExcel(file, 0, 0);
			List<BaseAmountVO> list = ie.getDataList(BaseAmountVO.class);
			for (int i=0;i<list.size();i++) {
				
				TsAwardDispatch item = new TsAwardDispatch();
				int rowNum = i+2;
				BaseAmountVO vo = list.get(i);

				if(StringUtils.isEmpty(vo.getCaseCode()) ||
						StringUtils.isEmpty(vo.getEmployeeCode()) ||
						(vo.getBaseAmount()==null)){
					request.setAttribute("ex_message", "文件第"+rowNum+"行存在空值");
            		return "income/income_edit";
				}
				//案件编号
                ToCase toCase = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
                if(toCase==null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行无法找到案件信息,请检查案件编码");
            		return "income/income_edit";
                }
                item.setCaseCode(vo.getCaseCode());
                //案件地址
				ToPropertyInfo record = new ToPropertyInfo();
				record.setCaseCode(vo.getCaseCode());
				record.setPropertyAddr(vo.getPropertyAddr());
				ToPropertyInfo info = toPropertyInfoService.findToPropertyInfoByCaseCodeAndAddr(record);
                if(info == null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行案件地址信息无法找到");
            		return "income/income_edit";
                }
                
                User user = uamUserOrgService.getUserByEmployeeCode(vo.getEmployeeCode());
                if(user == null ){
                	request.setAttribute("ex_message", "文件第"+rowNum+"行员工信息无法找到");
            		return "income/income_edit";
                }
                item.setParticipant(user.getId());
                item.setBaseAmount(new BigDecimal(vo.getBaseAmount()));
                item.setCaseCode(vo.getCaseCode());
                awardDispatchs.add(item);
               
            }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (IOException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (InstantiationException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			ex_message = "文件读取异常";
		}
		try{
			//插入数据
			tsAwardDispatchService.saveTsAwardDispatchBatch(awardDispatchs);
		}catch(BusinessException e1){
			e1.printStackTrace();
			ex_message = e1.getMessage();
		}catch(Exception e){
			e.printStackTrace();
			ex_message = "插入数据异常";
		}
    	request.setAttribute("ex_message",ex_message );
		return "income/income_edit";
	}
	/**
	 * 佣金查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value="read")
	public String incomeRead(Model model){
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("orOwnverId", user.getId());
		return "income/incomeList";
	}
	
}
