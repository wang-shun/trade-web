package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.EvalInvoiceImport;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
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
 * 评估发票相关接口
 * @author yinchao
 * @date 2017/10/16
 */
@Api(description = "评估发票相关接口", tags = {"evalInvoice"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class EvalInvoiceController extends AbstractBaseController {

	@ApiOperation(value = "评估发票申请同步", notes = "CCAI中，将事总审批通过的评估费发票申请调用该接口，同步到交易系统中，由权证做后续流程", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eval/invoice/sync",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult invoiceSync(
			@ApiParam(name = "评估费发票申请信息", value = "评估费发票申请信息", required = true)
			@Valid @RequestBody EvalInvoiceImport info, Errors errors){
		CcaiServiceResult result = buildErrorResult(errors);
		if(result.isSuccess()){
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("do noting.");
			result.setCode(SUCCESS_CODE);
		}
		// writeLog(ApiLogModuleEnum.EVA_REBATE_SYNC,"/api/ccai/v1/eva/rebate/sync",info,result,request);
		return result;
	}

	@ApiOperation(value = "评估发票申请驳回同步", notes = "交易系统中，权证经理驳回的发票申请，CCAI中调整完成后调用该接口，同步到交易系统中，由权证继续做审批", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eval/invoice/sync",method = RequestMethod.PUT,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSyncUpdate(
			@ApiParam(name = "评估费发票申请修改信息", value = "评估费发票申请修改信息", required = true)
			@Valid @RequestBody EvalInvoiceImport info, Errors errors){
		CcaiServiceResult result = buildErrorResult(errors);
		if(result.isSuccess()){
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("update do noting.");
			result.setCode(SUCCESS_CODE);
		}
		// writeLog(ApiLogModuleEnum.EVA_REBATE_SYNC,"/api/ccai/v1/eva/rebate/sync",info,result,request);
		return result;
	}

}
