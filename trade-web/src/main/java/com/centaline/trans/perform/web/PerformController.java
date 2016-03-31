package com.centaline.trans.perform.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aist.common.utils.excel.ImportExcel;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.perform.service.EplusService;
import com.centaline.trans.perform.service.GustFollowService;
import com.centaline.trans.perform.vo.EplusVo;
import com.centaline.trans.perform.vo.GustFollowVo;

@Controller
@RequestMapping(value = "/perform")
public class PerformController {

	private Logger logger = LoggerFactory.getLogger(PerformController.class);
	@Autowired
	private EplusService eplusService;
	@Autowired
	private GustFollowService gustFollowService;
	/**
	 * 转向组别管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "eplus")
	public String eplus() {
		return "perform/eplus";
	}
	@RequestMapping(value="gustFollow")
	public String gustFollow(){
		return "perform/gustFollow";
	}
	@RequestMapping(value="uploadGustFollow")
	public String gustFollow(@RequestParam("fileupload") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response)throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException{
		List<GustFollowVo> inIncomes = new ArrayList<GustFollowVo>();
		List<GustFollowVo> fList = new ArrayList<>();
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<GustFollowVo> list = ie.getDataList(GustFollowVo.class);
		for (int i = 0; i < list.size(); i++) {
			GustFollowVo vo = list.get(i);
			if (gustFollowCheck(vo)) {
				inIncomes.add(vo);
			} else {
				fList.add(vo);
			}
		}
		for (GustFollowVo eplusVo : inIncomes) {
			try{
				boolean isDone=gustFollowService.importOne(eplusVo);
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
		
		return "perform/gustFollow";
	}
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "doCalculated")
	public AjaxResponse doCalculated(Date belongMonth){
		if(eplusService.doCalculated(belongMonth)){
			return AjaxResponse.success();
		}else{
			return AjaxResponse.fail();
		}
	}
	
	@RequestMapping(value = "uploadEplus")
	public String uploadEplus(@RequestParam("fileupload") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response)
					throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		List<EplusVo> inIncomes = new ArrayList<EplusVo>();
		List<EplusVo> fList = new ArrayList<>();
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<EplusVo> list = ie.getDataList(EplusVo.class);
		for (int i = 0; i < list.size(); i++) {
			EplusVo vo = list.get(i);
			if (eplusVoCheck(vo)) {
				inIncomes.add(vo);
			} else {
				fList.add(vo);
			}
		}
		for (EplusVo eplusVo : inIncomes) {
			try{
				boolean isDone=eplusService.importOne(eplusVo);
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

		return "perform/eplus";
	}
	private boolean gustFollowCheck(GustFollowVo vo){
		if(StringUtils.isBlank(vo.getCaseCode())){
			return false;
		}
		if(StringUtils.isBlank(vo.getUserName())){
			return false;
		}
		if(StringUtils.isBlank(vo.getDirector())){
			return false;
		}
		if(StringUtils.isBlank(vo.getGeneralManager())){
			return false;
		}
		if(StringUtils.isBlank(vo.getJobName())){
			return false;
		}
		return true;
	}

	private boolean eplusVoCheck(EplusVo vo) {
		if (StringUtils.isBlank(vo.getEmployeeCode())) {
			return false;
		}
		if (StringUtils.isBlank(vo.getJobName())) {
			return false;
		}
		if (vo.getOrders() == null || vo.getOrders().intValue() <= 0) {
			return false;
		}
		if (vo.getBelongMonth() == null) {
			return false;
		}
		return true;
	}

}
