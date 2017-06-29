package com.centaline.trans.award.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.award.entity.TsAwardCaseCental;
import com.centaline.trans.award.entity.TsAwardKpiPay;
import com.centaline.trans.award.entity.TsKpiSrvCase;
import com.centaline.trans.award.repository.TsAwardCaseCentalMapper;
import com.centaline.trans.award.repository.TsAwardKpiPayMapper;
import com.centaline.trans.award.repository.TsKpiSrvCaseMapper;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.AwardStatusEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.utils.DateUtil;

@Service
public class TsAwardCaseCentalServiceImpl implements TsAwardCaseCentalService {

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;

	@Autowired
	private TsAwardCaseCentalMapper tsAwardCaseCentalMapper;
	
	@Autowired
	TsAwardKpiPayMapper tsAwardKpiPayMapper;
	
	@Autowired
	TsKpiSrvCaseMapper tsKpiSrvCaseMapper;
	
	@Autowired(required = true)
	private UamSessionService uamSessionService;	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-18
	 * 
	 * @desc:案件过户审批通过时保存信息进计件奖金池
	 */
	public void saveAwardCaseInfo(TsAwardCaseCental tsAwardCaseCental) {
		if (null == tsAwardCaseCental) {
			throw new BusinessException("保存数据至计件奖金池，请求参数有误！");
		}

		// 过户审批通过时需要设置 审批人的userId、过户审批通过时间以及caseCode
		try {
			TsAwardCaseCental awardCaseCentalInfo = new TsAwardCaseCental();
			String caseCode = tsAwardCaseCental.getCaseCode() == null ? "" : tsAwardCaseCental.getCaseCode();

			TgServItemAndProcessor tsp = new TgServItemAndProcessor();
			tsp.setCaseCode(caseCode);
			tsp.setSrvCat("30004010");

			TgServItemAndProcessor tgServItemAndProcessor = new TgServItemAndProcessor();
			// 取前台组交易顾问信息
			tsp.setSrvCode("3000401001");
			tgServItemAndProcessor = getTgServItemAndProcessorInfo(tsp);
			// 设置计件奖金和前台组相关的信息
			awardCaseCentalInfo = setAwardCaseCentalInfo(tsAwardCaseCental, tgServItemAndProcessor, "Front");

			// 取后台组交易顾问信息
			tsp.setSrvCode("3000401002");
			tgServItemAndProcessor = getTgServItemAndProcessorInfo(tsp);
			// 设置计件奖金和后台组相关的信息
			awardCaseCentalInfo = setAwardCaseCentalInfo(awardCaseCentalInfo, tgServItemAndProcessor, "Back");

			Date date = awardCaseCentalInfo.getGuohuApproveTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);// 设置当前时间
			calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置当月1号

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// 保存计件奖金池数据
			awardCaseCentalInfo.setAwardStatus(AwardStatusEnum.WEIFAFANG.getCode());
			//awardCaseCentalInfo.setAwardMonth(DateUtil.strToFullDate(format.format(calendar.getTime())));
			
			TsAwardCaseCental  caseInfo = tsAwardCaseCentalMapper.selectByCaseCode(tsAwardCaseCental);
			if(null == caseInfo){
				tsAwardCaseCentalMapper.insertSelective(awardCaseCentalInfo);
			}else{
				throw new BusinessException("此案件已经过户审批通过，无法保存至计件奖金池！");
			}			

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("belongMonth", DateUtil.strToFullDate(format.format(calendar.getTime())));
			map.put("caseCode", awardCaseCentalInfo.getCaseCode());

			// 判断当前的案子是否是浦东的案件(过户审批时 后台组主办是否是浦东组织)
			if ("8a8493d450af62ed0150c32bba961167".equals(awardCaseCentalInfo.getBackOrgId()) || "8a8493d5508aecbb0150936d1e3c55d2".equals(awardCaseCentalInfo.getBackOrgId())) {
				map.put("isPuDongCaseFlag", "isPudongCase");
			} else {
				map.put("isPuDongCaseFlag", "notPudongCase");
			}

			// 按是否浦东案子执行存储过程，计算环节占比
			tsAwardCaseCentalMapper.callCreateAwardBaseInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("保存数据至计件奖金池数据异常！");
			
		}

	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-18
	 * 
	 * @desc:获取案件前后台组主办信息
	 */
	private TgServItemAndProcessor getTgServItemAndProcessorInfo(TgServItemAndProcessor tsp) {
		TgServItemAndProcessor tgServItemAndProcessor = new TgServItemAndProcessor();

		if (null != tsp) {
			tgServItemAndProcessor = tgServItemAndProcessorService.findTgServItemAndProcessor(tsp);
		} else {
			throw new BusinessException("查询案件前后台组主办信息参数异常！");
		}
		return tgServItemAndProcessor;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-18
	 * 
	 * @desc:设置计件奖金池前后台相关人员信息
	 */

	private TsAwardCaseCental setAwardCaseCentalInfo(TsAwardCaseCental tsAwardCaseCental, TgServItemAndProcessor tgServItemAndProcessor, String Flag) {
		if (null == tgServItemAndProcessor) {
			throw new BusinessException("查询案件前后台组主办信息异常！");
		}

		Org org = uamUserOrgService.getOrgById(tgServItemAndProcessor.getOrgId());// 组织
		User manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(), "Manager");// 主管
		
		User seniorManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(), "Senior_Manager"); // 高级主管		
		User teamAssistant = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(), "TeamAssistant");// 助理
		
		User director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(org.getParentId(), "director");// 总监
		Org orgParent = uamUserOrgService.getOrgById(org.getParentId());// 组织
		User generalManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgParent.getParentId(), "GeneralManager");// 总经理
		// 运维经理暂时没有配置
		// User operationsManager =
		// uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(),
		// "operationsManager");//运维经理
		if ("Front".equals(Flag)) {
			// 前台组交易顾问
			tsAwardCaseCental.setFrontLeadingProcess(tgServItemAndProcessor.getProcessorId());
			tsAwardCaseCental.setFrontOrgId(tgServItemAndProcessor.getOrgId());
			if (null != org) {
				tsAwardCaseCental.setFrontDistrictId(org.getParentId());
			}
			// 主管
			if (null != manager) {
				tsAwardCaseCental.setFrontManager(manager.getId());
				tsAwardCaseCental.setFrontManagerOrgId(tgServItemAndProcessor.getOrgId());
				//tsAwardCaseCental.setFrontManagerOrgId(manager.getOrgId());
			}
			// 高级主管
			if (null != seniorManager) {
				tsAwardCaseCental.setFrontSeniorManager(seniorManager.getId());
				tsAwardCaseCental.setFrontSeniorManagerOrgId(tgServItemAndProcessor.getOrgId());
				//tsAwardCaseCental.setFrontSeniorManagerOrgId(seniorManager.getOrgId());
			}
			// 总监
			if (null != director) {
				tsAwardCaseCental.setFrontDirector(director.getId());
				tsAwardCaseCental.setFrontDirectorOrgId(org.getParentId());
				//tsAwardCaseCental.setFrontDirectorOrgId(director.getOrgId());
			}
			// 助理
			if (null != teamAssistant) {
				tsAwardCaseCental.setFrontTeamAssistant(teamAssistant.getId());
				tsAwardCaseCental.setFrontTeamAssistantOrgId(teamAssistant.getOrgId());
			}
			// 总经理
			if (null != generalManager) {
				tsAwardCaseCental.setFrontGeneralManager(generalManager.getId());
				tsAwardCaseCental.setFrontGeneralManagerOrgId(orgParent.getParentId());
				//tsAwardCaseCental.setFrontGeneralManagerOrgId(generalManager.getOrgId());
			}
			// 运维经理
			tsAwardCaseCental.setFrontOperationsManager("8a8493d54ff83966014ffd95ca0901e6");
			tsAwardCaseCental.setFrontOperationsManagerOrgId("ff8080814f459a78014f45a73d820006");
		} else if ("Back".equals(Flag)) {
			// 后台交易顾问
			tsAwardCaseCental.setBackLeadingProcess(tgServItemAndProcessor.getProcessorId());
			tsAwardCaseCental.setBackOrgId(tgServItemAndProcessor.getOrgId());
			if (null != org) {
				tsAwardCaseCental.setBackDistrictId(org.getParentId());
			}
			// 主管
			if (null != manager) {
				tsAwardCaseCental.setBackManager(manager.getId());
				tsAwardCaseCental.setBackManagerOrgId(tgServItemAndProcessor.getOrgId());
				//tsAwardCaseCental.setBackManagerOrgId(manager.getOrgId());
			}
			// 高级主管
			if (null != seniorManager) {
				tsAwardCaseCental.setBackSeniorManager(seniorManager.getId());
				tsAwardCaseCental.setBackSeniorManagerOrgId(tgServItemAndProcessor.getOrgId());
				//tsAwardCaseCental.setBackSeniorManagerOrgId(seniorManager.getOrgId());
			}
			// 总监
			if (null != director) {
				tsAwardCaseCental.setBackDirector(director.getId());
				tsAwardCaseCental.setBackDirectorOrgId(org.getParentId());
				//tsAwardCaseCental.setBackDirectorOrgId(director.getOrgId());
			}
			// 助理
			if (null != teamAssistant) {
				tsAwardCaseCental.setBackTeamAssistant(teamAssistant.getId());
				tsAwardCaseCental.setBackTeamAssistantOrgId(teamAssistant.getOrgId());
			}
			// 总经理
			if (null != generalManager) {
				tsAwardCaseCental.setBackGeneralManager(generalManager.getId());				
				tsAwardCaseCental.setBackGeneralManagerOrgId(orgParent.getParentId());
				//tsAwardCaseCental.setBackGeneralManagerOrgId(generalManager.getOrgId());
			}
			// 运维经理
			tsAwardCaseCental.setBackOperationsManager("8a8493d54ff83966014ffd95ca0901e6");
			tsAwardCaseCental.setBackOperationsManagerOrgId("ff8080814f459a78014f45a73d820006");
		}
		return tsAwardCaseCental;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:页面跳转至计件奖金明细
	 */
	public void jumpToNewBonusJsp(HttpServletRequest request){
		
			getCurrentDate(request);
			getCurrentLoginUserInfo(request);
	}
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:String 转  Date 格式指定
	 */
	@SuppressWarnings("unused")
	private Date convertToDate(String date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:获取当前系统时间，初始化页面计件年月
	 */
	private void getCurrentDate(HttpServletRequest  request){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化对象 --yyyy-MM-dd
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(date);//设置当前时间
		calendar.set(Calendar.DAY_OF_MONTH, -1);
		//calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		
		request.setAttribute("belongMonth", sdf.format(calendar.getTime()));
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:获取当前系统时间，初始化页面计件年月
	 */
	private void getCurrentLoginUserInfo(HttpServletRequest  request){
		
		SessionUser user = uamSessionService.getSessionUser();
		
		if(null == user){
			throw new BusinessException("用户未登录！");
		}
		String userJob = user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;
		int userJobCode = -1;

        StringBuffer reBuffer = new StringBuffer();
        //如果不是交易顾问
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
				//誉萃3个层级
				queryOrgFlag = true; //不是交易顾问说明对应的用户下面有组织
				String depString = user.getServiceDepHierarchy();
				String userOrgIdString = user.getServiceDepId();
				if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){ //组别
					reBuffer.append(userOrgIdString);
					userJobCode = 2;
				}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){ //区域 下面对应多个组别
					List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
					for(Org org:orgList){
						reBuffer.append(org.getId());
						reBuffer.append(",");
					}
					reBuffer.deleteCharAt(reBuffer.length()-1);
					userJobCode = 1;
				}else{
					isAdminFlag = true; //领导层 誉萃总部
					userJobCode = 0;
				}
		} else {
			userJobCode = 3;
		}
		request.setAttribute("queryOrgs", reBuffer.toString()); //所有区域组织id拼接
		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);	
		request.setAttribute("userJobCode", userJobCode);
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
	}

	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-05
	 * 
	 * @desc:获取初始化页面信息
	 */
	@Override
	public TsAwardKpiPay getInitPage(HttpServletRequest request, TsAwardKpiPay tsAwardKpiPay) {
		
		 SessionUser user = uamSessionService.getSessionUser();
		if(null == tsAwardKpiPay){
			throw new BusinessException("获取初始化页面信息请求参数有误！");
		}
		
		TsAwardKpiPay awardKpiPay = getTsAwardKpiPayInfo(tsAwardKpiPay);
		//如果查询结果为空，初始化值
		if(awardKpiPay == null){
			tsAwardKpiPay.setCreateBy(user.getId());
			tsAwardKpiPay.setCreateTime(new Date());
			tsAwardKpiPay.setUpdateBy(user.getId());
			tsAwardKpiPay.setUpdateTime(new Date());
			tsAwardKpiPay.setAwardStep("0");
			tsAwardKpiPayMapper.insertSelective(tsAwardKpiPay);
		}
		return awardKpiPay;
	}
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-08
	 * 
	 * @desc:更新计件奖金自动化步骤数
	 */
	@Override
	public int updateAwardStep(HttpServletRequest request, TsAwardKpiPay tsAwardKpiPay) {
			
		if(null == tsAwardKpiPay){
			throw new BusinessException("更新计件奖金自动化进行步骤请求参数有误！");
		}
		int k = 0;
		TsAwardKpiPay awardKpiPay = getTsAwardKpiPayInfo(tsAwardKpiPay);
		if(awardKpiPay != null){
			tsAwardKpiPay.setPkid(awardKpiPay.getPkid());
			k = tsAwardKpiPayMapper.updateByPrimaryKeySelective(tsAwardKpiPay);
		}
		
		return k;
	}
	
	
	private  TsAwardKpiPay  getTsAwardKpiPayInfo(TsAwardKpiPay tsAwardKpiPay){
		
		TsAwardKpiPay awardKpiPay = null;	
		List<TsAwardKpiPay>  kpiPayList = new ArrayList<TsAwardKpiPay>();
		kpiPayList = tsAwardKpiPayMapper.getTsAwardKpiPayByProperty(tsAwardKpiPay);
		
		if(null != kpiPayList  && kpiPayList.size() > 0){
			awardKpiPay = kpiPayList.get(0);
		}
		
		return awardKpiPay;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-09
	 * 
	 * @desc:判断是否已经同步满意度
	 */
	@Override
	public int isSycnSatis(HttpServletRequest request, TsKpiSrvCase tsKpiSrvCase) {
		if(null == tsKpiSrvCase){
			throw new BusinessException("查询是否同步满意度数据请求参数有误！");
		}
		int k = 0;
		k = tsKpiSrvCaseMapper.countSatis(tsKpiSrvCase);		
		
		return k;
	}
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-09
	 * 
	 * @desc:删除计件奖金池未发奖金的案件信息
	 */
	@Override
	public int deleteNobonusCase(String caseCode) {		
		
		if(null == caseCode || "".equals(caseCode)){
			throw new BusinessException("删除计件奖金池重启案件请求参数有误！");
		}
		
		TsAwardCaseCental tsAwardCaseCental =  new TsAwardCaseCental();
		tsAwardCaseCental.setCaseCode(caseCode);
		tsAwardCaseCental.setAwardStatus(AwardStatusEnum.WEIFAFANG.getCode());
		
		TsAwardCaseCental  caseInfo = tsAwardCaseCentalMapper.selectByCaseCodeAndStatus(tsAwardCaseCental);
		int k = 0;
		if(null != caseInfo){
			k = tsAwardCaseCentalMapper.deleteByPrimaryKey(caseInfo.getPkid());		
		}		
		return k;
	}	
	

}
