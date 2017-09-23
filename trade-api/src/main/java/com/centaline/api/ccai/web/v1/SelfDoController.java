package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.EvalRefundImport;
import com.centaline.api.ccai.vo.SelfDoImport;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 自办贷款
 * 自办评估接口
 * @author yinchao
 * @date 2017/9/23
 */
@Api(description = "变更自办申请接口", tags = {"selfDo"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class SelfDoController extends AbstractBaseController {

	@ApiOperation(value = "变更为自办申请同步", notes = "CCAI发起的自办贷款/自办评估流程，经过部门逐级审批同意后，调用该接口将信息同步至交易系统，由权证进行后续处理", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/selfdo/sync",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult selfDoSync(
			@ApiParam(name = "变更自办申请信息", value = "自办评估/自办贷款申请信息", required = true)
			@Valid @RequestBody SelfDoImport info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = buildErrorResult(errors);
		ObjectMapper mapper = new ObjectMapper();
		if(result.isSuccess()) {
			//Hibernate Validator 注解校验
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			StringBuilder msg = new StringBuilder();
			//校验审批环节信息是否正确
			buildErrorMessage(validator.validate(info.getTasks()),msg,"");
			if(StringUtils.isNotBlank(msg.toString())){
				result.setSuccess(false);
				result.setMessage(msg.toString());
				result.setCode(FAILURE_CODE);
			}else{
				//TODO 联调时增加业务代码
				System.out.println(info);
				result.setSuccess(true);
				result.setMessage("do noting.");
				result.setCode(SUCCESS_CODE);
			}
		}
		//写入日志
		// writeLog(ApiLogModuleEnum.EVAL_REFUND_SYNC,"/api/ccai/v1/eva/refund/sync",info,result,request);
		return result;
	}
}
