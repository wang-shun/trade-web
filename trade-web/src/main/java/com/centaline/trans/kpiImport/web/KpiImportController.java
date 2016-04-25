package com.centaline.trans.kpiImport.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aist.common.utils.excel.ImportExcel;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.kpi.service.TsKpiPsnMonthService;
import com.centaline.trans.kpi.vo.KpiMonthVO;
import com.centaline.trans.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping(value = "kpi") 
public class KpiImportController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private TsKpiPsnMonthService tsKpiPsnMonthService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@RequestMapping(value = "/import")
	public String kpiImport() {
		return "kpi/kpiInport";
	}

	@RequestMapping(value = "/doImport")
	public String doKpiImport() {
		return "kpi/kpiImport";
	}
	
	@RequestMapping(value = "/monthKpiImport")
	public String monthKpiImport() {
		return "kpi/monthKpiImport";
	}

	@RequestMapping(value = "/doMonthKpiImport")
	public String doMonthKpiImport(@RequestParam("fileupload") MultipartFile file,String belongMonth,HttpServletRequest request,
			HttpServletResponse response) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiMonthVO> list = ie.getDataList(KpiMonthVO.class);
		List<KpiMonthVO> errorList = checkImportData(list);
		if(errorList != null && errorList.size() > 0) {
			request.setAttribute("errorList", errorList);
			return "kpi/monthKpiImport";
		}
		String createBy = uamSessionService.getSessionUser().getId();
		int count = tsKpiPsnMonthService.importExcelTsKpiPsnMonthList(createBy, list);
		// 上月
		if("0".equals(belongMonth)) {
			
			tsKpiPsnMonthService.getPMonthKpiStastic(DateUtil.plusMonth(new Date(), -1));
		} else {
			tsKpiPsnMonthService.getPMonthKpiStastic(new Date());
		}
	
		return "kpi/monthKpiImport";
	}
	
	private List<KpiMonthVO> checkImportData(List<KpiMonthVO> list) {
		List<KpiMonthVO> errorList = new ArrayList<KpiMonthVO>(); 
		for(KpiMonthVO kpiMonthVO : list) {
			String employeeCode = kpiMonthVO.getEmployeeCode();
			String userName = kpiMonthVO.getUserName();
			Long finOrder = kpiMonthVO.getFinOrder();
			
			if(StringUtils.isBlank(employeeCode) || StringUtils.isBlank(userName) || (finOrder == null)){
				kpiMonthVO.setErrorMessage("数据不完整");
				errorList.add(kpiMonthVO);
				continue;
			}
			User user = uamUserOrgService.getUserByEmployeeCode(employeeCode);
			if(!user.getRealName().equals(userName)) {
				kpiMonthVO.setErrorMessage("员工编号与姓名不对应");
				errorList.add(kpiMonthVO);
				continue;
			}
		}
		return errorList;
	}
}
