package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.BankRebeatFeedBack;
import com.centaline.api.ccai.vo.EvalRebeatImport;
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
 * 银行返利审批接口
 * @author yinchao
 * @date 2017/10/16
 */
@Api(description = "银行返利相关接口", tags = {"bankRebate"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class BankRebateController extends AbstractBaseController {

	@ApiOperation(value = "银行返利，财务审批反馈接口", notes = "财务在CCAI审批通过后，将新生成的返利单编号、流程ID、原成交报告单号对应关系，同步到交易系统中", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/bank/rebate/feedback",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSync(
			@ApiParam(name = "银行返利审批反馈信息", value = "财务审批信息，以及审批通过后的对应信息", required = true)
			@Valid @RequestBody BankRebeatFeedBack info, Errors errors, HttpServletRequest request){
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
}
