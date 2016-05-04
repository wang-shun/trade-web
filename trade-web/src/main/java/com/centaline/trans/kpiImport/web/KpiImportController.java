package com.centaline.trans.kpiImport.web;

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
import com.centaline.trans.kpi.entity.TsKpiPsnMonth;
import com.centaline.trans.kpi.service.KpiSrvCaseService;
import com.centaline.trans.kpi.service.TsAwardKpiPayDetailService;
import com.centaline.trans.kpi.vo.KpiMonthVO;
import com.centaline.trans.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import com.centaline.trans.kpi.vo.KpiSrvCaseVo;
import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.entity.LoanStatusChange;

@Controller
@RequestMapping(value = "kpi")
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
	

	@RequestMapping(value = "/import")
	public String kpiImport(HttpServletRequest request) {
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		return "kpi/kpiImport";
	}
	
	@RequestMapping(value = "/bonus")
	public String bonus(HttpServletRequest request) {
		return "kpi/bonus";
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
			return "kpi/kpiImport";
		}

		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiSrvCaseVo> list = ie.getDataList(KpiSrvCaseVo.class);
		SessionUser user = uamSesstionService.getSessionUser();
		for (KpiSrvCaseVo kpiSrvCaseVo : list) {
			kpiSrvCaseVo.setCurrentMonth(currentMonth);
			kpiSrvCaseVo.setCreateBy(user.getId());
		}

		boolean res = kpiSrvCaseService.importBatch(list, currentMonth);
		if (res) {
			kpiSrvCaseService.callKpiStastic(currentMonth);
		}
		return "kpi/kpiImport";
	}

	@RequestMapping(value = "/monthKpiImport")
	public String monthKpiImport(HttpServletRequest request,
			HttpServletResponse response) {
		// 默认是当月
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		return "kpi/monthKpiImport";
	}

	@RequestMapping(value = "/doMonthKpiImport")

	public String doMonthKpiImport( String belongMonth,
			HttpServletRequest request, HttpServletResponse response)
					throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		MultipartFile file = null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			file = mRequest.getMultiFileMap().getFirst("fileupload");
		} else {
			request.setAttribute("belongM", LocalDate.now());
			request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
			return "kpi/monthKpiImport";
		}
		Date belongM = null;
		request.setAttribute("belongM", LocalDate.now());
		request.setAttribute("belongLastM", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
		// 上月
		if("0".equals(belongMonth)) {
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
		int count = tsKpiPsnMonthService.importExcelTsKpiPsnMonthList(belongM,createBy, list);
		
		tsKpiPsnMonthService.getPMonthKpiStastic(belongM);
		// uamUserOrgService.getUserOrgJobByUserIdAndJobCode(arg0, arg1)
		return "kpi/monthKpiImport";
	}

	private List<KpiMonthVO> checkImportData(List<KpiMonthVO> list) {
		List<KpiMonthVO> errorList = new ArrayList<KpiMonthVO>();
		for (KpiMonthVO kpiMonthVO : list) {
			String employeeCode = kpiMonthVO.getEmployeeCode();
			String userName = kpiMonthVO.getUserName();
			Long finOrder = kpiMonthVO.getFinOrder();

			if (StringUtils.isBlank(employeeCode) || StringUtils.isBlank(userName) || (finOrder == null)) {
				/*kpiMonthVO.setErrorMessage("数据不完整");
				errorList.add(kpiMonthVO);*/
				continue;
			}
			User user = uamUserOrgService.getUserByEmployeeCode(employeeCode);
			if(user==null || !userName.equals(user.getRealName())) {
				kpiMonthVO.setErrorMessage("员工编号与姓名不对应");
				errorList.add(kpiMonthVO);
				continue;
			}
		}
		return errorList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/stasticsAwardKpiRate")
	@ResponseBody
	public AjaxResponse stasticsAwardKpiRate(HttpServletRequest request,HttpServletResponse response) {
		AjaxResponse result = new AjaxResponse();
		// 取上一个月份
		SessionUser user = uamSesstionService.getSessionUser();
		try {
			Map map = new HashMap();
			//map.put("belongMonth", LocalDate.now().plus(-1, ChronoUnit.MONTHS));
			map.put("belongMonth", DateUtil.plusMonth(new Date(), -1));
			map.put("createBy", user.getId());
			map.put("createTime", new Date());
			
			tsAwardKpiPayDetailService.getPAwardKpiRate(map);
			//result.setContent(count);
			
			result.success("统计个人案件环节KPI成功");
		} catch (Exception e) {
			result.fail("统计个人案件环节KPI失败");
		}
		return result;
	}
}
