package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.EvaRebeatImport;
import com.centaline.api.ccai.vo.EvaRebeatReportImport;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
import com.centaline.trans.apilog.service.ApiLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 评估返利 相关接口
 * @author yinchao
 * @date 2017/9/22
 */
@Api(description = "评估返利流程接口", tags = {"evaRebate"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class EvaRebateController extends AbstractBaseController {

	private Logger logger = LoggerFactory.getLogger(EvaRebateController.class);

	@Autowired
	private ApiLogService apiLogService;
	@ApiOperation(value = "评估费返利报告申请同步", notes = "CCAI起的评估费返利报告，经过门店经理审批同意后，调用该接口将信息同步至交易系统，由内勤进行确认", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eva/rebate/sync",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSync(
			@ApiParam(name = "返利报告申请信息", value = "返利报告申请信息", required = true)
			@Valid @RequestBody EvaRebeatImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = new CcaiServiceResult();
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder msg = new StringBuilder();
		if (errors.hasErrors()) {
			for (ObjectError err : errors.getAllErrors()) {
				msg.append(err.getDefaultMessage()).append("\r\n");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
			result.setCode("99");
		} else {
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("do noting.");
			result.setCode("00");
		}
		// try {
		// 	String data = mapper.writeValueAsString(info);
		// 	logger.debug("feedback get data:" + data);
		// 	apiLogService.apiLog("FEEDBACK", "/api/ccai/v1/flow/feedback", data, mapper.writeValueAsString(result)
		// 			, result.isSuccess() ? "0" : "1", getHost(request));
		// } catch (JsonProcessingException e) {}
		return result;
	}

	@ApiOperation(value = "评估费返利报告申请驳回同步", notes = "内勤驳回的评估返利秘书修改后，经过门店经理审批同意后，调用该接口将信息同步至交易系统，由内勤进行确认", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eva/rebate/sync",method = RequestMethod.PUT,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSyncUpdate(
			@ApiParam(name = "返利报告申请信息", value = "修改后的返利报告申请信息", required = true)
			@Valid @RequestBody EvaRebeatImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = new CcaiServiceResult();
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder msg = new StringBuilder();
		if (errors.hasErrors()) {
			for (ObjectError err : errors.getAllErrors()) {
				msg.append(err.getDefaultMessage()).append("\r\n");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
			result.setCode("99");
		} else {
			//TODO 联调时增加业务代码 校验是否存在对应的同步
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("update do noting.");
			result.setCode("00");
		}
		// try {
		// 	String data = mapper.writeValueAsString(info);
		// 	logger.debug("feedback get data:" + data);
		// 	apiLogService.apiLog("FEEDBACK", "/api/ccai/v1/flow/feedback", data, mapper.writeValueAsString(result)
		// 			, result.isSuccess() ? "0" : "1", getHost(request));
		// } catch (JsonProcessingException e) {}
		return result;
	}

	@ApiOperation(value = "评估费返利报告同步", notes = "内勤确认的评估返利报告申请，由CCAI生产返利报告后，将报告信息进行同步", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eva/rebate/report",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateReport(
			@ApiParam(name = "返利报告信息", value = "最终的生产的返利报告信息", required = true)
			@Valid @RequestBody EvaRebeatReportImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = new CcaiServiceResult();
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder msg = new StringBuilder();
		if (errors.hasErrors()) {
			for (ObjectError err : errors.getAllErrors()) {
				msg.append(err.getDefaultMessage()).append("\r\n");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
			result.setCode("99");
		} else {
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("do noting.");
			result.setCode("00");
		}
		// try {
		// 	String data = mapper.writeValueAsString(info);
		// 	logger.debug("feedback get data:" + data);
		// 	apiLogService.apiLog("FEEDBACK", "/api/ccai/v1/flow/feedback", data, mapper.writeValueAsString(result)
		// 			, result.isSuccess() ? "0" : "1", getHost(request));
		// } catch (JsonProcessingException e) {}
		return result;
	}

}
