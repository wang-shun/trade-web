package com.centaline.parportal.mobile.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.parportal.mobile.login.service.TokenService;
import com.centaline.parportal.mobile.login.vo.MobileHolder;
import com.centaline.parportal.mobile.login.vo.TokenVo;

@Component
@Aspect
public class TokenAutoUpdateAop {
    @Resource
    private TokenService      tokenService;

    @Autowired
    private UamUserOrgService uamUserOrgService;

    /**
     * Pointcut 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* com.centaline.sales.mobile..*Controller.*(..))")
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
                if (MobileHolder.getMobileUser() == null) {// 没有登录 情况下自行处理返回消息格式
                    return returnValue;
                }
                if (method.isAnnotationPresent(RefreshTokenAutoAop.class)) { //刷新oldToken换newToken的情况下自行处理返回消息格式 
                    return returnValue;
                }

                Object obj = JSONObject.parse(returnValue.toString());

                JSONObject returnObj = new JSONObject();
                if (obj instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) obj;
                    returnObj.put("content", jsonObj);
                } else {
                    JSONArray jsonObj = (JSONArray) obj;
                    returnObj.put("content", jsonObj);
                }

                String newToken = doAutoUpdateToken(MobileHolder.getToken());
                if (newToken != null) {
                    returnObj.put("token", newToken);
                } else {
                    returnObj.put("token", MobileHolder.getToken().getToken());
                }
                returnObj.put("empId", MobileHolder.getMobileUser().getId());
                returnObj.put("msg", "处理成功");
                returnObj.put("isSuccess", true);
                return returnObj.toJSONString();
            }
            return returnValue;
        } catch (Throwable e) {
            return handlException(e);
        }
    }

    private String handlException(Throwable e) {
        String showExcMsg = "未知异常";
        e.printStackTrace();
        if (e instanceof BusinessException) {
            showExcMsg = e.getMessage();
        } else {
            System.out.println("未知异常详情：" + e.getMessage());
            e.printStackTrace();
        }

        JSONObject returnObj = new JSONObject();
        TokenVo tokenvo = MobileHolder.getToken();
        SessionUser sessionUser = MobileHolder.getMobileUser();
        returnObj.put("content", "");
        returnObj.put("empId", sessionUser != null ? sessionUser.getId() : "");
        returnObj.put("msg", showExcMsg);
        returnObj.put("isSuccess", false);
        returnObj.put("token", tokenvo != null ? tokenvo.getToken() : "");
        return returnObj.toJSONString();
    }

    private String doAutoUpdateToken(TokenVo token) throws Exception {

        User user = uamUserOrgService.getUserById(token.getSessionUser().getId());
        if (user == null || !"1".equals(user.getAvailable())) {
            throw new BusinessException("离职员工");
        }
        /*if (Integer.valueOf(0).equals(token.getIsExp())) {
        	tokenService.tokenExpAccessCount(token.getToken());
        	return token.getToken();
        }*/
        if (token.getExpDate().getTime() <= new Date().getTime()) {// 原Token已经过期
            TokenVo newToken = tokenService.expToken(token);
            return newToken.getToken();
        }
        return null;
    }

}
