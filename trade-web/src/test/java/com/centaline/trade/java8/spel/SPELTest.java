package com.centaline.trade.java8.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.aist.uam.userorg.remote.vo.User;

public class SPELTest {
	
	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
        User tesla = new User();
        tesla.setUsername("张三");
        StandardEvaluationContext context = new StandardEvaluationContext(tesla);
        context.setVariable("newName", "Mike Tesla");

        System.out.println(parser.parseExpression("#userName = #newName").getValue(context));
        
        Object str = parser.parseExpression("T(String)").getValue();
        System.out.println(str.getClass());// "Mike Tesla"
	}

}
