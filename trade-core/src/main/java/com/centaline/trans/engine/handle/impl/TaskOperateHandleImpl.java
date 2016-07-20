package com.centaline.trans.engine.handle.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.centaline.trans.engine.annotation.Action;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.handle.TaskOperateHandle;
import com.centaline.trans.engine.service.TaskService;

@Component
public class TaskOperateHandleImpl implements TaskOperateHandle {
	@Autowired
	private TaskService taskService;

	@SuppressWarnings("unchecked")
	@Override
	public void handle(Object target, Object[] args, String[] parameterNames, TaskOperate taskOperate) {
		String taskId = null;
		Map<String, Object> vars = null;
		String assignee = null;
		Object taskIdObj = getParameterVal(taskOperate.value(), args, parameterNames);
		if (taskIdObj == null) {
			throw new WorkFlowException("Task Id 不能为NULL");
		}
		taskId = String.valueOf(taskIdObj);
		if (StringUtils.isNotBlank(taskOperate.submitVal())) {
			vars = (Map<String, Object>) getParameterVal(taskOperate.submitVal(), args, parameterNames);
		}
		if (taskOperate.action() == Action.CLAIM || taskOperate.action() == Action.DELEGATE) {
			if (StringUtils.isNotBlank(taskOperate.assignee())) {
				assignee = String.valueOf(getParameterVal(taskOperate.assignee(), args, parameterNames));
			}
		}
		switch (taskOperate.action()) {
		case SUBMIT:
			taskService.submitTask(taskId, vars);
			break;
		case CLAIM:
			taskService.claim(taskId, assignee, vars);
			break;
		case DELEGATE:
			taskService.delegate(taskId, assignee, vars);
			break;
		case COMPLETE:
			taskService.complete(taskId, vars);
			break;
		case RESOLVE:
			taskService.resolve(taskId, vars);
			break;
		}
	}

	private Object getParameterVal(String expressionString, Object[] args, String[] parameterNames) {
		if (StringUtils.isBlank(expressionString) || null == parameterNames) {
			return null;
		}
		boolean isExpression = expressionString.startsWith("#");
		if (!isExpression) {
			for (int i = 0; i < parameterNames.length; i++) {
				if (expressionString.equals(parameterNames[i])) {
					return parameterNames[i];
				}
			}
			return null;
		}
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < parameterNames.length; i++) {
			String arguName = parameterNames[i];
			Object arguValue = args[i];
			context.setVariable(arguName, arguValue);
		}
		Object parameterVal = parser.parseExpression(expressionString).getValue(context, Object.class);
		return parameterVal;
	}

}
