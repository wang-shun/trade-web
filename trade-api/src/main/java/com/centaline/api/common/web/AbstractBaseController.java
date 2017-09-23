package com.centaline.api.common.web;

import com.alibaba.fastjson.JSONObject;
import com.centaline.api.common.enums.ApiLogModuleEnum;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.trans.apilog.service.ApiLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * 基础Controller
 * 提供一些公用的方法
 *
 * @author yinchao
 * @date 2017/9/21
 */
@Component
public abstract class AbstractBaseController {
	private Logger logger = LoggerFactory.getLogger(AbstractBaseController.class);
	@Autowired
	private ApiLogService apiLogService;

	/**
	 * 根据请求 获取正确的客户端地址
	 *
	 * @param request
	 * @return
	 */
	protected String getHost(HttpServletRequest request) {
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

	/**
	 * 通用构建Hibernate Validator接收数据 校验产生的错误信息
	 *
	 * @param errors
	 * @return
	 */
	protected CcaiServiceResult buildErrorResult(Errors errors) {
		CcaiServiceResult result = new CcaiServiceResult();
		if (errors != null && errors.hasErrors()) {
			StringBuilder msg = new StringBuilder();
			for (ObjectError err : errors.getAllErrors()) {
				msg.append(err.getDefaultMessage()).append("\r\n");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
			result.setCode("99");
		} else {
			result.setSuccess(true);
			result.setMessage("no errors");
			result.setCode("buildError");//特殊标记，记录为该处生成的结果 并且没经过修改
		}
		return result;
	}

	/**
	 * 通用记录日志 方法
	 *
	 * @param module  API模块
	 * @param url     API地址
	 * @param param   参数
	 * @param result  返回结果
	 * @param request API的HTTP请求
	 */
	protected final void writeLog(ApiLogModuleEnum module, String url, Object param, CcaiServiceResult result, HttpServletRequest request) {
		String data = JSONObject.toJSONString(param);
		logger.debug("get data:" + data);
		apiLogService.apiLog(module.getCode(), url, data, JSONObject.toJSONString(result)
				, result.isSuccess() ? "0" : "1", getHost(request));
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
	protected <T> void buildErrorMessage(Set<ConstraintViolation<T>> validates, StringBuilder msgBuilder, String appendBefore) {
		for (ConstraintViolation constraintViolation : validates) {
			msgBuilder.append(appendBefore).append(constraintViolation.getMessage()).append("\r\n");
		}
	}
}
