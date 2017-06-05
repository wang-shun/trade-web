package com.centaline.parportal.mobile.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
@Aspect
public class ContentWarpAop {

	@Autowired
	private UamSessionService sessionService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Pointcut 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* com.centaline.parportal.mobile..*Controller.*(..))")
    private void aspectjMethod() {
    };

    @Around(value = "aspectjMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object returnValue = null;
        try {
            returnValue = point.proceed();
            // 拦截的实体类
            Object target = point.getTarget();
            // 拦截的方法名称
            String methodName = point.getSignature().getName();

            // 拦截的放参数类型
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod()
                .getParameterTypes();

            boolean needProcess = false;
            Method method = null;
            for (Class<?> clazz = target.getClass(); clazz != Object.class; clazz = clazz
                .getSuperclass()) {

                try {
                    // 通过反射获得拦截的method
                    method = clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (method != null) {
                    break;
                }
            }

            if (method.isAnnotationPresent(ResponseBody.class)) {
                needProcess = true;
            }

            if (needProcess) {
            	SessionUser user = sessionService.getSessionUser();
                if (user == null) {// 没有登录 情况下自行处理返回消息格式
                    return returnValue;
                }
                
                JSONObject returnObj = new JSONObject();
                returnObj.put("content",returnValue);
                String newToken = getToken();
                returnObj.put("token", newToken);
                returnObj.put("empId", user.getId());
                returnObj.put("msg", "处理成功");
                returnObj.put("isSuccess", true);
                return returnObj;
            }
            return returnValue;
        } catch (Throwable e) {
            return handlException(e);
        }
    }
    
    private String getToken(){
    	String token = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter("token");
        return token;
    }

    private String handlException(Throwable e) {
        String showExcMsg = "未知异常";
        e.printStackTrace();
        if (e instanceof BusinessException) {
            showExcMsg = e.getMessage();
        } else {
        	logger.error("未知异常详情：" + e.getMessage());
            e.printStackTrace();
        }

        JSONObject returnObj = new JSONObject();
        String token = getToken();
        SessionUser sessionUser = sessionService.getSessionUser();
        returnObj.put("content", "");
        returnObj.put("empId", sessionUser != null ? sessionUser.getId() : "");
        returnObj.put("msg", showExcMsg);
        returnObj.put("isSuccess", false);
        returnObj.put("token", token);
        return returnObj.toJSONString();
    }
}
