package com.centaline.trans.award.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.award.entity.TsAwardCaseCental;
import com.centaline.trans.award.repository.TsAwardCaseCentalMapper;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.service.TgServItemAndProcessorService;

@Service
public class TsAwardCaseCentalServiceImpl implements TsAwardCaseCentalService {

	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	
	@Autowired
	private TsAwardCaseCentalMapper tsAwardCaseCentalMapper;
	
	/*
	 * @author:zhuody
	 * @date:2017-05-18
	 * @desc:案件过户审批通过时保存信息进计件奖金池
	 * 
	 * */
	public void saveAwardCaseInfo(TsAwardCaseCental tsAwardCaseCental){		
		if(null == tsAwardCaseCental){
			 throw new BusinessException("保存数据至计件奖金池，请求参数有误！");
		}
		
		try{
			TsAwardCaseCental awardCaseCentalInfo = new TsAwardCaseCental();
			String caseCode = tsAwardCaseCental.getCaseCode() == null ? "":tsAwardCaseCental.getCaseCode();
			
			TgServItemAndProcessor tsp = new TgServItemAndProcessor();
			tsp.setCaseCode(caseCode);		
			tsp.setSrvCat("30004010");		
			
			TgServItemAndProcessor tgServItemAndProcessor = new TgServItemAndProcessor();		
			//取前台组交易顾问信息	
			tsp.setSrvCode("3000401001");
			tgServItemAndProcessor = getTgServItemAndProcessorInfo(tsp);
			//设置计件奖金和前台组相关的信息
			awardCaseCentalInfo = setAwardCaseCentalInfo(tsAwardCaseCental,tgServItemAndProcessor,"Front");
			
			//取后台组交易顾问信息
			tsp.setSrvCode("3000401002");
			tgServItemAndProcessor = getTgServItemAndProcessorInfo(tsp);
			//设置计件奖金和后台组相关的信息
			awardCaseCentalInfo = setAwardCaseCentalInfo(awardCaseCentalInfo,tgServItemAndProcessor,"Back");
			
			//保存计件奖金池数据
			tsAwardCaseCentalMapper.insertSelective(awardCaseCentalInfo);
		}catch(Exception e){
			throw new BusinessException("保存数据至计件奖金池数据异常！");
		}
		
	} 
	
	
	/*
	 * @author:zhuody
	 * @date:2017-05-18
	 * @desc:获取案件前后台组主办信息
	 * 
	 * */
	private TgServItemAndProcessor  getTgServItemAndProcessorInfo(TgServItemAndProcessor tsp){
		TgServItemAndProcessor  tgServItemAndProcessor =  new  TgServItemAndProcessor();
		
		if(null != tsp){
			tgServItemAndProcessor = tgServItemAndProcessorService.findTgServItemAndProcessor(tsp);
		}else{
			throw new BusinessException("查询案件前后台组主办信息参数异常！");
		}		
		return tgServItemAndProcessor;		
	}		
	
	
	/*
	 * @author:zhuody
	 * @date:2017-05-18
	 * @desc:设置计件奖金池前后台相关人员信息
	 * 
	 * */
	
	 private  TsAwardCaseCental  setAwardCaseCentalInfo(TsAwardCaseCental tsAwardCaseCental,TgServItemAndProcessor tgServItemAndProcessor,String Flag){
		 if(null == tgServItemAndProcessor){
			 throw new BusinessException("查询案件前后台组主办信息异常！");
		 }
		 if("Front".equals(Flag)){
			 //交易顾问
			 tsAwardCaseCental.setFrontLeadingProcess(tgServItemAndProcessor.getProcessorId());
			 tsAwardCaseCental.setFrontOrgId(tgServItemAndProcessor.getOrgId());
			 tsAwardCaseCental.setFrontDistrictId("");
			 //主管
			 tsAwardCaseCental.setFrontManager("");
			 //高级主管
			 tsAwardCaseCental.setFrontSeniorManager("");
			 //总监
			 tsAwardCaseCental.setFrontDirector("");
			 //助理
			 tsAwardCaseCental.setFrontTeamAssistant("");
			 //总经理
			 tsAwardCaseCental.setFrontGeneralManager("");
			 //运维经理
			 tsAwardCaseCental.setFrontOperationsManager("");
		 }else if("Back".equals(Flag)){
			 //交易顾问
			 tsAwardCaseCental.setFrontLeadingProcess(tgServItemAndProcessor.getProcessorId());
			 tsAwardCaseCental.setFrontOrgId(tgServItemAndProcessor.getOrgId());
			 tsAwardCaseCental.setFrontDistrictId("");
			 //主管
			 tsAwardCaseCental.setFrontManager("");
			 //高级主管
			 tsAwardCaseCental.setFrontSeniorManager("");
			 //总监
			 tsAwardCaseCental.setFrontDirector("");
			 //助理
			 tsAwardCaseCental.setFrontTeamAssistant("");
			 //总经理
			 tsAwardCaseCental.setFrontGeneralManager("");
			 //运维经理
			 tsAwardCaseCental.setFrontOperationsManager("");
		 }		 
		 return tsAwardCaseCental;
	 }
}
