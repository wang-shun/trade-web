package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.Brokerage2EvalFee;
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

import javax.validation.Valid;

/**
 * 中介费调整相关接口
 * 中介费折评估费
 * @author yinchao
 * @date 2017/10/20
 */
@Api(description = "调佣相关流程接口", tags = {"brokerageChange"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class BrokerageChangeController extends AbstractBaseController {

	@ApiOperation(value = "代理费折评估费申请同步", notes = "CCAI将发起的代理费折评估费申请同步到交易系统，由权证进行审核和调佣", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/brokerage/toeval/sync",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult invoiceSync(
			@ApiParam(name = "代理费折评估费申请信息", value = "代理费折评估费申请信息", required = true)
			@Valid @RequestBody Brokerage2EvalFee info, Errors errors){
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
