package com.centaline.trans.kpiImport.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
