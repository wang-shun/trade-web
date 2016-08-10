package com.centaline.trans.award.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aist.common.utils.excel.ImportExcel;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.kpi.service.TsKpiPsnMonthService;
import com.centaline.trans.kpi.entity.TsAwardKpiPay;
import com.centaline.trans.kpi.entity.TsKpiPsnMonth;
import com.centaline.trans.kpi.service.KpiSrvCaseService;
import com.centaline.trans.kpi.service.TsAwardKpiPayDetailService;
import com.centaline.trans.kpi.service.TsAwardKpiPayService;
import com.centaline.trans.kpi.vo.KpiMonthVO;
import com.centaline.trans.utils.DateUtil;
import com.centaline.trans.utils.NumberUtil;

import org.apache.commons.lang3.StringUtils;
import com.centaline.trans.kpi.vo.KpiSrvCaseVo;

@Controller
@RequestMapping(value = "/award")
public class KpiImportController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private TsKpiPsnMonthService tsKpiPsnMonthService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSesstionService;
	@Autowired
	private KpiSrvCaseService kpiSrvCaseService;
	@Autowired
	private TsAwardKpiPayDetailService tsAwardKpiPayDetailService;
	@Autowired
	private TsAwardKpiPayService tsAwardKpiPayService;
	
	@RequestMapping(value = "/personBonus")
	public String personBonus(HttpServletRequest request) {
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		return "award/personBonus";
	}

	@RequestMapping(value = "/import")
	public String kpiImport(HttpServletRequest request) {
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		return "award/kpiImport";
	}
	
	@RequestMapping(value = "/bonus")
	public String bonus(HttpServletRequest request) {
		return "award/bonus";
	}
	
	@RequestMapping(value = "/testbonus")
	public String testbonus(HttpServletRequest request) {
		return "award/testbonus";
	}

	@RequestMapping(value = "/doImport")
	public String doKpiImport(HttpServletRequest request, HttpServletResponse response, Boolean currentMonth)
			throws InstantiationException, IllegalAccessException, InvalidFormatException, IOException {
		MultipartFile file = null;
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			file = mRequest.getMultiFileMap().getFirst("fileupload");
		} else {
			return "award/kpiImport";
		}

		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiSrvCaseVo> list = ie.getDataList(KpiSrvCaseVo.class);
		SessionUser user = uamSesstionService.getSessionUser();
		for (KpiSrvCaseVo kpiSrvCaseVo : list) {
			kpiSrvCaseVo.setCurrentMonth(currentMonth);
			kpiSrvCaseVo.setCreateBy(user.getId());
		}
		List<KpiSrvCaseVo> fList = kpiSrvCaseService.importBatch(list, currentMonth);
		boolean res = fList == null;
		if (res) {
			kpiSrvCaseService.callKpiStastic(currentMonth);
		} else {
			request.setAttribute("fList", fList);
		}
		return "award/kpiImport";
	}

	@RequestMapping(value = "/monthKpiImport")
	public String monthKpiImport(HttpServletRequest request, HttpServletResponse response) {
		// 默认是当月
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		return "award/monthKpiImport";
	}

	@RequestMapping(value = "/doMonthKpiImport")

	public String doMonthKpiImport(String belongMonth, HttpServletRequest request, HttpServletResponse response)
			throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		MultipartFile file = null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			file = mRequest.getMultiFileMap().getFirst("fileupload");
		} else {
			request.setAttribute("belongM", LocalDate.now());
			request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
			return "award/monthKpiImport";
		}
		Date belongM = null;
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		// 上月
		if ("0".equals(belongMonth)) {
			belongM = DateUtil.plusMonth(new Date(), -1);
		} else {
			belongM = new Date();
		}
		TsKpiPsnMonth record = new TsKpiPsnMonth();
		record.setBelongMonth(belongM);
		tsKpiPsnMonthService.deleteTsKpiPsnMonthByProperty(record);

		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiMonthVO> list = ie.getDataList(KpiMonthVO.class);
		List<KpiMonthVO> errorList = checkImportData(list);
		if (errorList != null && errorList.size() > 0) {
			request.setAttribute("errorList", errorList);
			return "kpi/monthKpiImport";
		}
		String createBy = uamSessionService.getSessionUser().getId();
		int count = tsKpiPsnMonthService.importExcelTsKpiPsnMonthList(belongM, createBy, list);
		
		staticMoneyKpi(belongM);
		
		// eg : 四月份月度kpi修改，则需要重新统计五月份所有的未提交的数据
		TsKpiPsnMonth record2 = new TsKpiPsnMonth();
		record2.setBelongMonth(DateUtil.plusMonth(belongM, 1));
		List<TsKpiPsnMonth> mList = tsKpiPsnMonthService.getTsKpiPsnMonthListByPro(record2);
		
		TsAwardKpiPay record3 = new TsAwardKpiPay();
		record3.setStatus("0");
		record3.setBelongMonth(DateUtil.plusMonth(belongM, 1));
		List<TsAwardKpiPay> tsAwardKpiPayList = tsAwardKpiPayService.getTsAwardKpiPayByProperty(record3);
		
		if(CollectionUtils.isNotEmpty(mList) && CollectionUtils.isNotEmpty(tsAwardKpiPayList)) {
			staticMoneyKpi(DateUtil.plusMonth(belongM, 1));
		}
		
		return "award/monthKpiImport";
	}
	
	// 统计该月份的绩效奖金相关数据
	private void staticMoneyKpi(Date belongM) {
	
		tsKpiPsnMonthService.getPMonthKpiStastic(belongM);
		
		//统计AwardKpiRate
		Map map = new HashMap();
		map.put("belongMonth", belongM);
		map.put("createBy", uamSesstionService.getSessionUser().getId());
		map.put("createTime", new Date());
		tsAwardKpiPayDetailService.getPAwardKpiRate(map);
	}

	private List<KpiMonthVO> checkImportData(List<KpiMonthVO> list) {
		List<KpiMonthVO> errorList = new ArrayList<KpiMonthVO>();
		for (KpiMonthVO kpiMonthVO : list) {
			String employeeCode = kpiMonthVO.getEmployeeCode();
			String userName = kpiMonthVO.getUserName();
			String finOrder = kpiMonthVO.getFinOrder();

			if (StringUtils.isBlank(employeeCode) || StringUtils.isBlank(userName) || (finOrder == null)) {
				/*
				 * kpiMonthVO.setErrorMessage("数据不完整");
				 * errorList.add(kpiMonthVO);
				 */
				continue;
			}
			User user = uamUserOrgService.getUserByEmployeeCode(employeeCode);
			if (user == null || !userName.equals(user.getRealName())) {
				kpiMonthVO.setErrorMessage("员工编号与姓名不对应");
				errorList.add(kpiMonthVO);
				continue;
			}
			if(!NumberUtil.isMatchesRegex(finOrder, "^[\\d]*$")) {
				kpiMonthVO.setErrorMessage("金融产品需输入正整数");
				errorList.add(kpiMonthVO);
				continue;
			}
		}
		return errorList;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getTsAwardKpiPayByProperty")
	@ResponseBody
	public AjaxResponse getTsAwardKpiPayByProperty(HttpServletRequest request,HttpServletResponse response,String belongMonth) {
		AjaxResponse result = new AjaxResponse();
		try {
			TsAwardKpiPay record = new TsAwardKpiPay();
			record.setBelongMonth(DateUtil.strToFullDate(belongMonth));
			List<TsAwardKpiPay> tsAwardKpiPayList = tsAwardKpiPayService.getTsAwardKpiPayByProperty(record);
			if(CollectionUtils.isNotEmpty(tsAwardKpiPayList)) {
				result.setContent(tsAwardKpiPayList.get(0));
			}
			result.success("查询当前奖金总数成功");
		} catch (Exception e) {
			result.fail("查询当前奖金总数失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/getTsAwardKpiPayByStatus")
	@ResponseBody
	public AjaxResponse getTsAwardKpiPayByStatus(HttpServletRequest request,HttpServletResponse response,String belongMonth) {
		AjaxResponse result = new AjaxResponse();
		try {
			TsAwardKpiPay record = new TsAwardKpiPay();
			record.setStatus("1");
			record.setBelongMonth(DateUtil.strToFullDate(belongMonth));
			List<TsAwardKpiPay> tsAwardKpiPayList = tsAwardKpiPayService.getTsAwardKpiPayByProperty(record);
			if(CollectionUtils.isNotEmpty(tsAwardKpiPayList)) {
				result.setContent(tsAwardKpiPayList.get(0));
			}
			result.success("查询已经提交奖金成功");
		} catch (Exception e) {
			result.fail("查询已经提交奖金失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/updateTsAwardKpiPayStatus")
	@ResponseBody
	public AjaxResponse updateTsAwardKpiPayStatus(HttpServletRequest request,HttpServletResponse response,String belongMonth) {
		AjaxResponse result = new AjaxResponse();
		try {
			TsAwardKpiPay record = new TsAwardKpiPay();
			// 确认状态
			record.setStatus("1");
			record.setBelongMonth(DateUtil.strToFullDate(belongMonth));
			int count = tsAwardKpiPayService.updateTsAwardKpiPayStatus(record);
		    if(count >0 ) {
		    	result.success("奖金提交成功");
		    } else {
		    	result.fail("奖金提交失败");
		    }
		} catch (Exception e) {
			result.fail("奖金提交失败");
		}
		return result;
	}
}
