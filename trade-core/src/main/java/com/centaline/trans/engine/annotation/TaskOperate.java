/**
 * 
 */
package com.centaline.trans.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jjm
 *
 */
@Target(value = { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskOperate {
	public String value() default "taskId";

	public String assignee() default "assignee";

	public Action action() default Action.SUBMIT;

	public String submitVal() default "";
}
