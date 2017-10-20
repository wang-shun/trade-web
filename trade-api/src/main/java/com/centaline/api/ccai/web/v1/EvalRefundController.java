package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.service.CcaiService;
import com.centaline.api.ccai.vo.EvalRefundImport;
import com.centaline.api.common.enums.ApiLogModuleEnum;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 评估退费 相关接口
 * @author yinchao
 * @date 2017/9/22
 */
@Api(description = "评估退费流程接口", tags = {"evalRefund"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class EvalRefundController extends AbstractBaseController {
	
	@Autowired
	private CcaiService ccaiService;
	

	@ApiOperation(value = "评估退费申请同步", notes = "CCAI发起的评估费退费流程，经过部门逐级审批同意后，调用该接口将信息同步至交易系统，由权证进行后续处理", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/eval/refund/sync",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult rebateSync(
			@ApiParam(name = "评估退费申请信息", value = "评估退费申请信息", required = true)
			@Valid @RequestBody EvalRefundImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = buildErrorResult(errors);
		ObjectMapper mapper = new ObjectMapper();
		if(result.isSuccess()) {
			try {
				result = ccaiService.importEvalRefund(info);
			} catch (Exception e) {
				result.setSuccess(false);
				result.setCode(FAILURE_CODE);
				result.setMessage(e.getMessage());
			}
		}
		//写入日志
		writeLog(ApiLogModuleEnum.EVAL_REFUND_SYNC,"/api/ccai/v1/eva/refund/sync",info,result,request);
		return result;
	}
}
