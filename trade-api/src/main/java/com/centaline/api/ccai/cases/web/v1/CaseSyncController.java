package com.centaline.api.ccai.cases.web.v1;

import com.aist.common.exception.BusinessException;
import com.centaline.api.ccai.cases.service.CcaiService;
import com.centaline.api.ccai.cases.vo.CcaiImportAttachment;
import com.centaline.api.ccai.cases.vo.CcaiImportCase;
import com.centaline.api.ccai.cases.vo.CcaiImportCaseGuest;
import com.centaline.api.ccai.cases.vo.CcaiImportParticipant;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.enums.CaseSyncParticipantEnum;
import com.centaline.api.validate.group.NormalGroup;
import com.centaline.trans.apilog.service.ApiLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * 案件同步相关Controller
 * 返回json的数据接口 请求路径结尾需要加json
 * 否则会不转换JSON，返回406错误
 *
 * @author yinchao
 */
//tags不要起中文名 否则可能会导致展开接口方法失效
@Api(description = "案件同步相关接口", tags = {"caseSync"})
@RestController
@RequestMapping(value = "/api/ccai/v1")
public class CaseSyncController {
	private Logger logger = LoggerFactory.getLogger(CaseSyncController.class);
	//记录日志 模块类型
	private static final String SYNC_MODULE = "CASESYNC";//案件新增同步
	private static final String UPDATE_MODULE = "CASEUPDATE";//案件修改同步
	//修改案件信息类型
	private static final String UPDATE_TYPE_NORMAL = "normal";//普通类型 只修改可修改的信息 不做其他处理
	private static final String UPDATE_TYPE_FLOW = "flow";//流程相关修改 修改信息后，会触发流程的变动

	@Autowired
	private CcaiService ccaiService;

	@Autowired
	private ApiLogService apiLogService;

	/**
	 * ccai导入案件接口
	 *
	 * @param acase json格式案件信息(由Spring MVC 自动转换成对象)
	 * @return
	 */
	@ApiOperation(value = "案件同步API", notes = "提供给CCAI将成交报告同步到交易系统", produces = "application/json,application/json;charset=UTF-8")
	@RequestMapping(value = "/case/sync", method = RequestMethod.POST, produces = {"application/json", "application/json;charset=UTF-8"})
	public CcaiServiceResult caseImport(
			@ApiParam(name = "案件信息", value = "需要同步的案件信息", required = true)
			@Valid @RequestBody CcaiImportCase acase, Errors errors, HttpServletRequest request) {

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
			//做其他的校验
			result = validateData(acase);
			//校验通过 进行数据同步
			if (result.isSuccess()) {
				try {
					result = ccaiService.importCase(acase);//数据同步
				} catch (BusinessException e) {
					result.setSuccess(false);
					result.setCode("99");
					result.setMessage(e.getMessage());
				}
			}
		}
		try {
			String data = mapper.writeValueAsString(acase);
			logger.debug("sync get data:" + data);
			apiLogService.apiLog(SYNC_MODULE, "/api/ccai/v1/caseSync.json", data, mapper.writeValueAsString(result)
					, result.isSuccess() ? "0" : "1", getHost(request));
		} catch (JsonProcessingException e) {
		}
		return result;
	}

	/**
	 * ccai修改案件信息接口
	 * type 为normal 则为普通的修改
	 * type 为flow 则触发流程到权证审批
	 *
	 * @param acase json格式案件信息(由Spring MVC 自动转换成对象)
	 * @return
	 */
	@ApiOperation(value = "案件修改API", notes = "提供给CCAI将被驳回的成交报告和修改的成交报告进行再次同步,type为normal-普通修改不影响流程,为flow则触发流程到权证审批", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "本次修改的类型",
					allowableValues = "normal,flow", paramType = "path", dataType = "string", required = true),
	})
	@RequestMapping(value = "/case/{type}", method = RequestMethod.PUT, produces = {"application/json", "application/json;charset=UTF-8"})
	@ResponseBody
	public CcaiServiceResult caseUpdate(@PathVariable String type,
										@ApiParam(name = "案件信息", value = "本次修改的案件信息", required = true)
										@RequestBody CcaiImportCase ucase, HttpServletRequest request) {
		CcaiServiceResult result = new CcaiServiceResult();
		ObjectMapper mapper = new ObjectMapper();
		if (ucase == null || StringUtils.isBlank(ucase.getCcaiCode())
				|| StringUtils.isBlank(ucase.getCity())) {
			result.setSuccess(false);
			result.setCode("99");
			result.setMessage("未获取到要修改的案件信息或成交报告编号或城市!");
		} else {
			try {
				if (UPDATE_TYPE_NORMAL.equals(type)) {
					//普通修改同步案件信息
					result = ccaiService.updateCase(ucase);
				} else if (UPDATE_TYPE_FLOW.equals(type)) {
					//修改案件信息 同时触发流程到权证经理审核环节
					result = ccaiService.updateCaseAndFlow(ucase);
				} else {
					result.setSuccess(false);
					result.setCode("99");
					result.setMessage("无法识别的修改类型!");
				}
			} catch (BusinessException e) {
				result.setSuccess(false);
				result.setCode("99");
				result.setMessage(e.getMessage());
			}
		}
		try {
			String data = mapper.writeValueAsString(ucase);
			logger.debug("sync get data:" + data);
			apiLogService.apiLog(UPDATE_MODULE, "/api/ccai/v1/caseUpdate/" + type + ".json", data, mapper.writeValueAsString(result)
					, result.isSuccess() ? "0" : "1", getHost(request));
		} catch (JsonProcessingException e) {
		}
		return result;
	}

	/**
	 * 校验导入的案件信息是否准
	 *
	 * @param acase
	 * @return
	 */
	private CcaiServiceResult validateData(CcaiImportCase acase) {
		CcaiServiceResult result = new CcaiServiceResult();
		if (acase == null) {
			result.setSuccess(false);
			result.setCode("99");
			result.setMessage("数据格式不正确");
		} else {
			//Hibernate Validator 注解校验
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			StringBuilder msg = new StringBuilder();//拼接错误信息
			//基本信息校验
			cityCodeCheck(acase.getCity(), msg, "城市编码不正确");
			if (ccaiService.isExistCcaiCode(acase.getCcaiCode())) {
				msg.append("成交报告[").append(acase.getCcaiCode()).append("]编号已存在!\r\n");
			}
			boolean hasAgent = false, hasWarrant = false, hasSecretary = false;
			if (acase.getParticipants() == null || acase.getParticipants().size() < 3) {
				msg.append("案件参与人信息不完整，请查证!\r\n");
			} else {
				//案件参与人校验
				for (CcaiImportParticipant pa : acase.getParticipants()) {
					if (CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())) {
						//经纪人校验
						buildErrorMessage(validator.validate(pa, NormalGroup.class, Default.class), msg, "经纪人");
						// participantCheck(pa,msg,"经纪人");
						hasAgent = true;
					} else if (CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
						//过户权证校验
						buildErrorMessage(validator.validate(pa, NormalGroup.class, Default.class), msg, "过户权证");
						// participantCheck(pa,msg,"过户权证");
						hasWarrant = true;
					} else if (CaseSyncParticipantEnum.LOAN.getCode().equals(pa.getPosition())) {
						//贷款权证
						buildErrorMessage(validator.validate(pa, NormalGroup.class, Default.class), msg, "贷款权证");
					} else if (CaseSyncParticipantEnum.SECRETARY.getCode().equals(pa.getPosition())) {
						//秘书校验
						buildErrorMessage(validator.validate(pa, Default.class), msg, "秘书");
						// participantCheck(pa,msg,"秘书");
						hasSecretary = true;
					}
				}
				if (!hasAgent || !hasWarrant || !hasSecretary) {
					msg.append("至少要有经纪人、过户权证和秘书信息!\r\n");
				}
			}

			//房源信息校验
			buildErrorMessage(validator.validate(acase.getProperty()), msg, "");
			cityCodeCheck(acase.getProperty().getDistrict(), msg, "房源所属区域编码不正确");
			hasAgent = false;//业主
			hasWarrant = false;//买家
			//客户信息校验
			for (CcaiImportCaseGuest guest : acase.getGuests()) {
				if ("30006001".equals(guest.getPosition())) {
					//业主
					hasAgent = true;
					buildErrorMessage(validator.validate(guest), msg, "卖家");
				} else if ("30006002".equals(guest.getPosition())) {
					//买家
					hasWarrant = true;
					buildErrorMessage(validator.validate(guest), msg, "买家");
				}
			}
			if (!hasAgent || !hasWarrant) {
				msg.append("至少要有一个买家和卖家信息!\r\n");
			}
			if (msg.length() > 0) {
				result.setSuccess(false);
				result.setCode("99");
				result.setMessage(msg.toString());
			} else {
				result.setSuccess(true);
				result.setCode("00");
			}
		}
		return result;
	}

	/**
	 * 城市编码 行政区划校验规则
	 *
	 * @param code
	 * @param msg
	 * @param message
	 */
	private void cityCodeCheck(String code, StringBuilder msg, String message) {
		if (StringUtils.isBlank(code) || code.length() != 6 || !StringUtils.isNumeric(code)) {
			msg.append(message);
			msg.append("\r\n");
		}
	}

	/**
	 * 根据将校验信息的结果拼接到传入的msgBuilder中，
	 * 如果appendBefore不为null，则拼接到每个错误消息前
	 *
	 * @param validate
	 * @param msgBuilder
	 * @param appendBefore
	 * @param <T>
	 */
	private <T> void buildErrorMessage(Set<ConstraintViolation<T>> validates, StringBuilder msgBuilder, String appendBefore) {
		for (ConstraintViolation constraintViolation : validates) {
			msgBuilder.append(appendBefore).append(constraintViolation.getMessage()).append("\r\n");
		}
	}

	/**
	 * 根据请求 获取正确的客户端地址
	 * 有
	 *
	 * @param request
	 * @return
	 */
	private String getHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		return SecurityUtils.getSubject().getSession().getHost();
	}

}