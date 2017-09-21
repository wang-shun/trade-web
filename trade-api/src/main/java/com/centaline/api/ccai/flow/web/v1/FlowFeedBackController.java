package com.centaline.api.ccai.flow.web.v1;

import com.centaline.api.ccai.flow.vo.FlowFeedBack;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
import com.centaline.trans.apilog.service.ApiLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * 流程反馈接口
 * @author yinchao
 * @date 2017/9/21
 */
@Api(description = "通用审批反馈结果接口", tags = {"flowFeedBack"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class FlowFeedBackController extends AbstractBaseController {

	private Logger logger = LoggerFactory.getLogger(FlowFeedBackController.class);

	@Autowired
	private ApiLogService apiLogService;
	@ApiOperation(value = "审批结果反馈", notes = "CCAI中的审批任务环节，审批后将结果反馈到交易系统", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/flow/feedback",method = RequestMethod.POST,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult feedBack(
			@ApiParam(name = "审批结果信息", value = "审批环节，审批结果信息", required = true)
			@Valid @RequestBody FlowFeedBack info, Errors errors, HttpServletRequest request){
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
