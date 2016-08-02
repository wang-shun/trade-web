package com.centaline.trans.engine.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.handle.TaskOperateHandle;

@Component
@Aspect
public class TaskOperateAspectAopInterceptor {
	@Autowired
	private TaskOperateHandle handle;

	@Pointcut("@annotation(com.centaline.trans.engine.annotation.TaskOperate)")
	private void aspectMethod() {
	};

	@Around(value = "aspectMethod()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object returnObj = null;
		returnObj = point.proceed();
		// 拦截的实体类
		Object target = point.getTarget();
		// 拦截的方法名称
		String methodName = point.getSignature().getName();

		// 拦截的放参数类型
		@SuppressWarnings("rawtypes")
		Class[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();

		Method method = null;

		try {
			// 通过反射获得拦截的method
			method = target.getClass().getMethod(methodName, parameterTypes);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/** index as the same(i hope) **/
		Object[] args = point.getArgs();
		final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();
		String[] parameterNames = paramNameDiscoverer.getParameterNames(method);

		TaskOperate wfAnnotation = method.getAnnotation(TaskOperate.class);
		handle.handle(target, args, parameterNames, wfAnnotation);
		return returnObj;
	}
}
