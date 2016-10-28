package com.centaline.trans.auth.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.aist.common.auth.annotation.RequireDataAuth;
import com.aist.common.auth.service.DataAuthService;
import com.aist.common.utils.SpringUtils;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

@Component
@Aspect
public class AuthAspectAopInterceptor {

    @Autowired
    private UamSessionService uamSessionService;

    /**
     * Pointcut 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(public * com.centaline.trans..*Controller.*(..)) ")
    private void aspectjAuthMethod() {
    };

    @Around(value = "aspectjAuthMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        // 拦截的实体类
        Object target = point.getTarget();
        // 拦截的方法名称
        String methodName = point.getSignature().getName();

        // 拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod()
                .getParameterTypes();

        Method method = null;
        boolean needProcess = false;
        try {
            // 通过反射获得拦截的method
            method = target.getClass().getMethod(methodName, parameterTypes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        RequireDataAuth dataAuth = method.getAnnotation(RequireDataAuth.class);
        if (!method.isAnnotationPresent(RequireDataAuth.class)) {
            return point.proceed();
        }

        Object[] argus = point.getArgs();
        final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] paramArray = paramNameDiscoverer.getParameterNames(method);

        getDataAuth(dataAuth, argus, paramArray);

        return point.proceed();
    }

    private Boolean getDataAuth(RequireDataAuth auth, Object[] argus, String[] argusNames) {
        SessionUser user = uamSessionService.getSessionUser();
        String pLevel = auth.pLevel();
        String code = getCode(auth.code(), argus, argusNames);
        Class cl = auth.authClass();

        DataAuthService authService = (DataAuthService) SpringUtils.getBean(cl);
        authService.checkDataAuth(user, pLevel, code);

        return true;
    }

    private String getCode(String expressionString, Object[] argus, String[] argusNames) {
        if (null == argusNames) {
            return null;
        }
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < argusNames.length; i++) {
            String arguName = argusNames[i];
            Object arguValue = argus[i];
            context.setVariable(arguName, arguValue);
        }
        String code = parser.parseExpression(expressionString).getValue(context, String.class);
        return code;
    }
}
