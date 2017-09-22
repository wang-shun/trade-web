package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.vo.FlowFeedBack;
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
 * 流程反馈接口
 * @author yinchao
 * @date 2017/9/21
 */
@Api(description = "通用审批反馈结果接口", tags = {"flowFeedBack"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class FlowFeedBackController extends AbstractBaseController {

	@ApiOperation(value = "审批结果反馈", notes = "CCAI中的审批任务环节，审批后将结果反馈到交易系统", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/flow/feedback",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult feedBack(
			@ApiParam(name = "审批结果信息", value = "审批环节，审批结果信息", required = true)
			@Valid @RequestBody FlowFeedBack info, Errors errors, HttpServletRequest request){
		CcaiServiceResult result = buildErrorResult(errors);
		if(result.isSuccess()){
			//TODO 联调时增加业务代码
			System.out.println(info);
			result.setSuccess(true);
			result.setMessage("do noting.");
			result.setCode("00");
		}
		// writeLog(ApiLogModuleEnum.FLOW_FEEDBACK,"/api/ccai/v1/flow/feedback",info,result,request);
		return result;

	}
}
