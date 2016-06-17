package com.centaline.trans.award.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.utils.excel.ImportExcel;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.award.service.BaseImportService;
import com.centaline.trans.award.vo.BaseImportVo;
import com.centaline.trans.common.enums.TransJobs;

//jjm budf
@Controller
@RequestMapping(value = "/award")
public class BaseImporController {

	@Autowired
	private BaseImportService baseImportService;

	@Autowired
	private UamSessionService uamSessionService;

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@RequestMapping(value = "baseImport")
	public String baseImport() {
		return "award/baseImport";
	}

	@RequestMapping(value = "uploadBaseImport")
	public String uploadEplus(@RequestParam("fileupload") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response)
			throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		List<BaseImportVo> inIncomes = new ArrayList<BaseImportVo>();
		List<BaseImportVo> fList = new ArrayList<>();
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<BaseImportVo> list = ie.getDataList(BaseImportVo.class);
		for (int i = 0; i < list.size(); i++) {
			BaseImportVo vo = list.get(i);
			if (BaseImportVoCheck(vo)) {
				inIncomes.add(vo);
			} else {
				fList.add(vo);
			}
		}
		for (BaseImportVo eplusVo : inIncomes) {
			try {
				boolean isDone = baseImportService.importOne(eplusVo);
				if (!isDone) {
					fList.add(eplusVo);
				}
			} catch (Exception e) {
				fList.add(eplusVo);
				e.printStackTrace();
			}
		}
		if (fList.isEmpty()) {
			request.setAttribute("ex_message", "导入成功");
		} else {
			request.setAttribute("fList", fList);
		}

		return "award/baseImport";
	}

	private boolean BaseImportVoCheck(BaseImportVo vo) {
		if (StringUtils.isBlank(vo.getEmployeeCode())) {
			return false;
		}
		if (StringUtils.isBlank(vo.getCaseCode())) {
			return false;
		}
		if (StringUtils.isBlank(vo.getJobName())) {
			return false;
		}
		if (StringUtils.isBlank(vo.getOrgName())) {
			return false;
		}
		if (StringUtils.isBlank(vo.getUserName())) {
			return false;
		}
		if (vo.getAmount() == null || vo.getBelongMonth() == null) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "baseAward")
	public ModelAndView baseAward() {

		SessionUser sesssionUser = uamSessionService.getSessionUser();

		String countMsg = "";
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);

		if (TransJobs.TZJ.getCode().equals(sesssionUser.getServiceJobCode())) {
			gp.setQueryId("generalManagerCount");
			Page<Map<String, Object>> result = quickGridService.findPageForSqlServer(gp);
			
			Object obj = result.getContent().get(0).get("CASE_CODE_COUNT");
			String caseCodeCount = null == obj ? "0" : String.valueOf(obj);
			
			countMsg = "交易单数: " + caseCodeCount;
		} else if (TransJobs.TZJL.getCode().equals(sesssionUser.getServiceJobCode())) {
			gp.setQueryId("directorCount");
			Page<Map<String, Object>> result = quickGridService.findPageForSqlServer(gp);
			
			Object srvPartInObj = result.getContent().get(0).get("SRV_PART_IN_COUNT");
			String srvPartInCount = null == srvPartInObj ? "0" : String.format("%.2f ",srvPartInObj);
			Object srvPartRatioObj = result.getContent().get(0).get("SRV_PART_RATIO_COUNT");
			String srvPartRatioCount = null == srvPartRatioObj ? "0" : String.format("%.2f ",srvPartRatioObj);

			countMsg = "环节总数: " + srvPartInCount + ", 交易单数: " + srvPartRatioCount;
		} else {
			gp.setQueryId("otherRoleCount");
			Page<Map<String, Object>> result = quickGridService.findPageForSqlServer(gp);
			Object srvPartInObj = result.getContent().get(0).get("SRV_PART_IN_COUNT");
			String srvPartInCount = null == srvPartInObj ? "0" : String.format("%.2f ",srvPartInObj);
			countMsg = " 环节总数: " + srvPartInCount;
		}

		Map<String, String> model = new HashMap<String, String>();
		model.put("countMsg", countMsg);
		return new ModelAndView("award/baseAward", model);
	}
}
