package com.centaline.trans.award.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.award.entity.TsAwardCaseCental;
import com.centaline.trans.award.repository.TsAwardCaseCentalMapper;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.AwardStatusEnum;
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
			awardCaseCentalInfo.setAwardMonth(convertToDate(format.format(calendar.getTime())));
			tsAwardCaseCentalMapper.insertSelective(awardCaseCentalInfo);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("belongMonth", convertToDate(format.format(calendar.getTime())));
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
		User director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(), "director");// 总监
		User teamAssistant = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(), "TeamAssistant");// 助理
		User generalManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(tgServItemAndProcessor.getOrgId(), "GeneralManager");// 总经理
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
				tsAwardCaseCental.setFrontManagerOrgId(manager.getOrgId());
			}
			// 高级主管
			if (null != seniorManager) {
				tsAwardCaseCental.setFrontSeniorManager(seniorManager.getId());
				tsAwardCaseCental.setFrontSeniorManagerOrgId(seniorManager.getOrgId());
			}
			// 总监
			if (null != director) {
				tsAwardCaseCental.setFrontDirector(director.getId());
				tsAwardCaseCental.setFrontDirectorOrgId(director.getOrgId());
			}
			// 助理
			if (null != teamAssistant) {
				tsAwardCaseCental.setFrontTeamAssistant(teamAssistant.getId());
				tsAwardCaseCental.setFrontTeamAssistantOrgId(teamAssistant.getOrgId());
			}
			// 总经理
			if (null != generalManager) {
				tsAwardCaseCental.setFrontGeneralManager(generalManager.getId());
				tsAwardCaseCental.setBackGeneralManagerOrgId(generalManager.getOrgId());
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
				tsAwardCaseCental.setBackManagerOrgId(manager.getOrgId());
			}
			// 高级主管
			if (null != seniorManager) {
				tsAwardCaseCental.setBackSeniorManager(seniorManager.getId());
				tsAwardCaseCental.setBackSeniorManagerOrgId(seniorManager.getOrgId());
			}
			// 总监
			if (null != director) {
				tsAwardCaseCental.setBackDirector(director.getId());
				tsAwardCaseCental.setBackDirectorOrgId(director.getOrgId());
			}
			// 助理
			if (null != teamAssistant) {
				tsAwardCaseCental.setBackTeamAssistant(teamAssistant.getId());
				tsAwardCaseCental.setBackTeamAssistantOrgId(teamAssistant.getOrgId());
			}
			// 总经理
			if (null != generalManager) {
				tsAwardCaseCental.setBackGeneralManager(generalManager.getId());
				tsAwardCaseCental.setBackGeneralManagerOrgId(generalManager.getOrgId());
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
	 * @desc:String 转  Date 格式指定
	 */
	private Date convertToDate(String date) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
