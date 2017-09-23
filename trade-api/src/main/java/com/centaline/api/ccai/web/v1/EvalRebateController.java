package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.EvalRebeatImport;
import com.centaline.api.ccai.vo.EvalRebeatReportImport;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.Errors;
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
@Api(description = "评估返利流程接口", tags = {"evalRebate"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class EvalRebateController extends AbstractBaseController {

	@ApiOperation(value = "评估费返利报告申请同步", notes = "CCAI发起的评估费返利报告，经过门店经理审批同意后，调用该接口将信息同步至交易系统，由内勤进行确认", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eval/rebate/sync",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSync(
			@ApiParam(name = "返利报告申请信息", value = "返利报告申请信息", required = true)
			@Valid @RequestBody EvalRebeatImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = buildErrorResult(errors);
		if(result.isSuccess()){
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("do noting.");
			result.setCode("00");
		}
		// writeLog(ApiLogModuleEnum.EVA_REBATE_SYNC,"/api/ccai/v1/eva/rebate/sync",info,result,request);
		return result;
	}

	@ApiOperation(value = "评估费返利报告申请驳回同步", notes = "内勤驳回的评估返利报告申请，由秘书修改后，经过门店经理审批同意，调用该接口将信息同步至交易系统，再次由内勤进行确认", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eval/rebate/sync",method = RequestMethod.PUT,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSyncUpdate(
			@ApiParam(name = "返利报告申请信息", value = "修改后的返利报告申请信息", required = true)
			@Valid @RequestBody EvalRebeatImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = buildErrorResult(errors);
		if(result.isSuccess()){
			//TODO 联调时增加业务代码 校验是否存在对应的同步
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("update do noting.");
			result.setCode("00");
		}
		// writeLog(ApiLogModuleEnum.EVA_REBATE_SYNC_UPDATE,"/api/ccai/v1/eva/rebate/sync",info,result,request);
		return result;
	}

	@ApiOperation(value = "评估费返利报告同步", notes = "内勤确认的评估返利报告申请，由CCAI生产返利报告后，将报告信息进行同步", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eval/rebate/report",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateReport(
			@ApiParam(name = "返利报告信息", value = "CCAI生成的返利报告信息", required = true)
			@Valid @RequestBody EvalRebeatReportImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = buildErrorResult(errors);
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder msg = new StringBuilder();
		if(result.isSuccess()){
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("do noting.");
			result.setCode("00");
		}
		// writeLog(ApiLogModuleEnum.EVA_REBATE_REPORT,"/api/ccai/v1/eva/rebate/report",info,result,request);
		return result;
	}

}
