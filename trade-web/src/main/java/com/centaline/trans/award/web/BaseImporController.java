package com.centaline.trans.award.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aist.common.utils.excel.ImportExcel;
import com.centaline.trans.award.service.BaseImportService;
import com.centaline.trans.award.vo.BaseImportVo;
  //jjm
@Controller
@RequestMapping(value = "/award")
public class BaseImporController {
	@Autowired
	private BaseImportService baseImportService;
	@RequestMapping(value = "baseImport")
	public String baseImport(){
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
			try{
				boolean isDone=baseImportService.importOne(eplusVo);
				if(!isDone){
					fList.add(eplusVo);
				}
			}catch(Exception e){
				fList.add(eplusVo);
				e.printStackTrace();
			}
		}
		if(fList.isEmpty()){
			request.setAttribute("ex_message", "导入成功");
		}else{
			request.setAttribute("fList", fList);
		}

		return "award/baseImport";
	}
	private boolean BaseImportVoCheck(BaseImportVo vo){
		if(StringUtils.isBlank(vo.getEmployeeCode())){
			return false;
		}
		if(StringUtils.isBlank(vo.getCaseCode())){
			return false;
		}
		if(StringUtils.isBlank(vo.getJobName())){
			return false;
		}
		if(StringUtils.isBlank(vo.getOrgName())){
			return false;
		}
		if(StringUtils.isBlank(vo.getUserName())){
			return false;
		}
		if(vo.getAmount()==null||vo.getBelongMonth()==null){
			return false;
		}	
		return true;
	}
}
