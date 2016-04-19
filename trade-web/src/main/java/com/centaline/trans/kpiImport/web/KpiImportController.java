package com.centaline.trans.kpiImport.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aist.common.utils.excel.ImportExcel;
import com.centaline.trans.kpi.vo.KpiMonthVO;

@Controller
@RequestMapping(value = "kpi") 
public class KpiImportController {
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
	public String doMonthKpiImport(@RequestParam("fileupload") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<KpiMonthVO> list = ie.getDataList(KpiMonthVO.class);
		
		return "kpi/monthKpiImport";
	}
}
