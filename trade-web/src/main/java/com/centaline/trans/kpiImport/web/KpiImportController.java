package com.centaline.trans.kpiImport.web;

import java.io.IOException;
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
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.kpi.service.KpiSrvCaseService;
import com.centaline.trans.kpi.vo.KpiMonthVO;
import com.centaline.trans.kpi.vo.KpiSrvCaseVo;

@Controller
@RequestMapping(value = "kpi")
public class KpiImportController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSesstionService;
	@Autowired
	private KpiSrvCaseService kpiSrvCaseService;

	@RequestMapping(value = "/import")
	public String kpiImport() {
		return "kpi/kpiImport";
	}

	@RequestMapping(value = "/doImport")
	public String doKpiImport(@RequestParam("fileupload") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response,Boolean currentMonth)
					throws InstantiationException, IllegalAccessException, InvalidFormatException, IOException {
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiSrvCaseVo> list = ie.getDataList(KpiSrvCaseVo.class);
		SessionUser user= uamSesstionService.getSessionUser();
		for (KpiSrvCaseVo kpiSrvCaseVo : list) {
			kpiSrvCaseVo.setCurrentMonth(currentMonth);
			kpiSrvCaseVo.setCreateBy(user.getId());
		}
	
		kpiSrvCaseService.importBatch(list,currentMonth);
		return "kpi/kpiImport";
	}

	@RequestMapping(value = "/monthKpiImport")
	public String monthKpiImport() {
		return "kpi/monthKpiImport";
	}

	@RequestMapping(value = "/doMonthKpiImport")
	public String doMonthKpiImport(@RequestParam("fileupload") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response)
					throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiMonthVO> list = ie.getDataList(KpiMonthVO.class);

		uamUserOrgService.getUserByEmployeeCode("");

		// uamUserOrgService.getUserOrgJobByUserIdAndJobCode(arg0, arg1)

		return "kpi/monthKpiImport";
	}
}
