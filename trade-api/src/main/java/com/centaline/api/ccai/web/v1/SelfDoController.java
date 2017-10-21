package com.centaline.api.ccai.web.v1;

import com.centaline.api.ccai.service.CcaiEvalService;
import com.centaline.api.ccai.service.CcaiService;
import com.centaline.api.ccai.vo.EvalRefundImport;
import com.centaline.api.ccai.vo.SelfDoImport;
import com.centaline.api.common.enums.ApiLogModuleEnum;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.common.web.AbstractBaseController;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.utils.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	ToSelfAppInfoService toSelfAppInfoService;
	
	@Autowired
	private CcaiEvalService  ccaiEvalService;

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
				try {
					result = ccaiEvalService.importSelfDo(info);
				} catch (Exception e) {
					result.setSuccess(false);
					result.setCode(FAILURE_CODE);
					result.setMessage(e.getMessage());
				}
			}
		}
		//写入日志
		 writeLog(ApiLogModuleEnum.SELF_DO_DYNC,"/api/ccai/v1/eva/selfdo/sync",info,result,request);
		return result;
	}
	
	@ApiOperation(value = "变更为自办驳回同步", notes = "权证驳回自办申请同步，由CCAI修改后，调用该接口信息同步至交易系统，再次有权证经理确认", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value="/selfdo/sync",method = RequestMethod.PUT,produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult selfDoSyncUpdate(
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
				try {
					result = ccaiEvalService.updateSelfDo(info);
				} catch (Exception e) {
					result.setSuccess(false);
					result.setCode(FAILURE_CODE);
					result.setMessage(e.getMessage());
				}
			}
		}
		//写入日志
		 writeLog(ApiLogModuleEnum.SELF_DO_DYNC_UPDATE,"/api/ccai/v1/eva/selfdo/sync",info,result,request);
		return result;
	}
}
