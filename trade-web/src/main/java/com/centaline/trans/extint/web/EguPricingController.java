package com.centaline.trans.extint.web;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.extint.vo.AssessResult;
import com.centaline.trans.extint.vo.ReportApplyStatus;
import com.centaline.trans.extint.web.vo.ResponseVo;
import com.centaline.trans.mgr.enums.EvaCompanyEnum;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.enums.ConfirmCode;
import com.centaline.trans.mortgage.enums.ReportType;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.remote.Const;

/**
 * e估推送询价结果和报告结果接口
 * @author zxf
 *
 */
@Controller
@RequestMapping(value="/api/egu")
public class EguPricingController {

	Logger logger = LoggerFactory.getLogger(EguPricingController.class);
	
	@Autowired
	private ToEguPricingService toEguPricingService;
	
	@Autowired
	private ToEvaReportService toEvaReportService;
	
	@Autowired
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	
	@Autowired(required=true)
	private UamTemplateService uamTemplateService;
	
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	
	@Autowired
	private ToCaseService toCaseService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	/**
	 * E估推送询价结果信息
	 * @param assessResult
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/assess_result",method = RequestMethod.GET)  
	@ResponseBody
    public ResponseVo assessResult(AssessResult assessResult, HttpServletRequest request,@PathVariable String code) {

		ResponseVo returnMsg = new ResponseVo();
		try{
			ToEguPricing toEguPricing = null;
			List<ToEguPricing> toEguPricingList = toEguPricingService.findToEguPricingByEvaCode(code);
			if(CollectionUtils.isNotEmpty(toEguPricingList)){
				toEguPricing = toEguPricingList.get(0);
			}
			
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(assessResult.getCase_id());
			/** 1.根据评估编号查询，如果没有查到记录，为手机端发送的
				2.已经存在询价信息，修改询价信息
			**/
			if(toEguPricing == null){
				ToEguPricing eguPricing = new ToEguPricing();
				eguPricing.setAriseTime(new Date());
				eguPricing.setCaseCode(toPropertyInfo==null?null:assessResult.getCase_id());
				eguPricing.setEvaCode(code);
				eguPricing.setApplyCode(assessResult.getApply_code());
				eguPricing.setResponseTime(new Date());
				eguPricing.setResult(assessResult.getConfirm_code());
				if(assessResult.getAss_status() != null){
					eguPricing.setStatus(assessResult.getAss_status().toString());
				}
				if(assessResult.getTotal_price() == null) {
					eguPricing.setTotalPrice(0d);
				} else {
					eguPricing.setTotalPrice(assessResult.getTotal_price()*10000);
				}
				eguPricing.setUnitPrice(assessResult.getUnit_price());
				User user = uamUserOrgService.getUserByUsername(assessResult.getUn());
				if(user != null){
					eguPricing.setAriserId(user.getId());
				}
				toEguPricingService.saveToEguPricing(eguPricing);
			}else{
				toEguPricing.setApplyCode(assessResult.getApply_code());
				toEguPricing.setResponseTime(new Date());
				toEguPricing.setResult(assessResult.getConfirm_code());
				if(assessResult.getAss_status() != null){
					toEguPricing.setStatus(assessResult.getAss_status().toString());
				}
				if(assessResult.getTotal_price() == null) {
					toEguPricing.setTotalPrice(0d);
				} else {
					toEguPricing.setTotalPrice(assessResult.getTotal_price()*10000);
				}
				toEguPricing.setUnitPrice(assessResult.getUnit_price());
				toEguPricingService.updateToEguPricing(toEguPricing);
			}
			//推送过来的案件Id为空不发送提醒
			if(StringUtils.isNotEmpty(assessResult.getCase_id()) && toPropertyInfo!=null){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("eva_code", code);
				param.put("property", toPropertyInfo.getPropertyAddr());
				
				String content = uamTemplateService.mergeTemplate(MsgLampEnum.PRICING.getCode(), param);
				Message message = new Message();
				message.setSenderId(uamUserOrgService.getUserByUsername("admin").getId());
				message.setSendTime(new Date());
				message.setContent(content);
				message.setType(MessageType.SITE);
				message.setTitle(MsgLampEnum.PRICING.getName());
				message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
				User user = uamUserOrgService.getUserByUsername(assessResult.getUn());
				if(user != null){
					uamMessageService.sendMessageByDist(message, user.getId());
				}
			}
			returnMsg.setSc(Const.SUCCESS);
		}catch(Exception e){
			returnMsg.setSc(Const.FAIL);
			logger.error("推送Egu询价结果失败！"+e.getMessage(),e);
		}
        return returnMsg;
    }
	
	/**
	 * E估推送预估单申请状态返回结果
	 * @param reportApplyStatus
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}/report",method = RequestMethod.GET)  
	@ResponseBody
    public ResponseVo report(ReportApplyStatus reportApplyStatus, HttpServletRequest request,@PathVariable String code) {

		ResponseVo returnMsg = new ResponseVo();
		try{
			String caseCode = null;
			ToEvaReport report = new ToEvaReport();
			report.setEvaCode(code);
			report.setReportType(reportApplyStatus.getReport_type());
			List<ToEvaReport> toEvaReportList = toEvaReportService.findToEvaReportByEvaCode(report);
			List<ToEguPricing> toEguPricingList = toEguPricingService.findToEguPricingByEvaCode(code);
			//如果没有询价记录，不做处理
			if(CollectionUtils.isEmpty(toEguPricingList)){
				returnMsg.setSc(Const.SUCCESS);
				return returnMsg;
			}
			//手机端发送的评估申请，存在询价记录，保存
			if(CollectionUtils.isNotEmpty(toEguPricingList) && CollectionUtils.isEmpty(toEvaReportList)){
				ToEguPricing toEguPricing = toEguPricingList.get(0);
				ToEvaReport evaReport = new ToEvaReport();
				evaReport.setFeedback(reportApplyStatus.getStatus_text());
				if(reportApplyStatus.getStatus_date() != null && reportApplyStatus.getStatus_time() != null){
					evaReport.setReportResponseTime(DateUtils.parseDate(reportApplyStatus.getStatus_date()+" "+reportApplyStatus.getStatus_time(),"yyyy-MM-dd HH:mm"));
				}
				evaReport.setSerialNumber(reportApplyStatus.getSerial_no());
				evaReport.setReportType(reportApplyStatus.getReport_type());
				evaReport.setStatus(reportApplyStatus.getStatus_code());
				evaReport.setEvaCode(code);
				evaReport.setFinOrgCode(EvaCompanyEnum.EGU.getCode());
				evaReport.setCaseCode(toEguPricing.getCaseCode());
				evaReport.setReportAriseTime(new Date());
				evaReport.setReportResponseTime(new Date());
				evaReport.setIsFinalReport("0");
				toEvaReportService.saveToEvaReport(evaReport);
			}else{
				caseCode = toEvaReportList.get(0).getCaseCode();
				for(ToEvaReport toEvaReport : toEvaReportList){
					if(StringUtils.equals(toEvaReport.getReportType(),reportApplyStatus.getReport_type())){
						toEvaReport.setFeedback(reportApplyStatus.getStatus_text());
						if(reportApplyStatus.getStatus_date() != null && reportApplyStatus.getStatus_time() != null){
							toEvaReport.setReportResponseTime(DateUtils.parseDate(reportApplyStatus.getStatus_date()+" "+reportApplyStatus.getStatus_time(),"yyyy-MM-dd HH:mm"));
						}
						toEvaReport.setSerialNumber(reportApplyStatus.getSerial_no());
						toEvaReport.setReportType(reportApplyStatus.getReport_type());
						toEvaReport.setStatus(reportApplyStatus.getStatus_code());
						toEvaReportService.updateToEvaReport(toEvaReport);
					}
				}
			}
			//案件编号为空，不发消息
			if(StringUtils.isNotEmpty(caseCode)){
				ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("property", toPropertyInfo.getPropertyAddr());
				param.put("report_type", ReportType.getValueByCode(reportApplyStatus.getReport_type()));
				param.put("status", reportApplyStatus.getStatus_text());
				
				String content = uamTemplateService.mergeTemplate(MsgLampEnum.REPORT.getCode(), param);
				Message message = new Message();
				User user = uamUserOrgService.getUserByUsername("admin");
				message.setSenderId(user.getId());
				message.setSendTime(new Date());
				message.setContent(content);
				message.setType(MessageType.SITE);
				message.setTitle(MsgLampEnum.REPORT.getName());
				message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
	
				User caseMaster = uamUserOrgService.getUserByUsername(reportApplyStatus.getUn());
				if(caseMaster != null){
					uamMessageService.sendMessageByDist(message, caseMaster.getId());
				}
			}
			returnMsg.setSc(Const.SUCCESS);
		}catch(Exception e){
			returnMsg.setSc(Const.FAIL);
			logger.error("推送Egu预估单申请状态返回结果失败!"+e.getMessage());
		}
        return returnMsg;
  
    }
	

}
